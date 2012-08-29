package com.maddox.il2.game;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Target;
import com.maddox.il2.ai.TargetsGuard;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ZutiTargetsSupportMethods;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIBriefing;
import com.maddox.il2.gui.GUIBriefing.TargetPoint;
import com.maddox.il2.gui.GUIPad;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.NetAircraft.AircraftNet;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.il2.objects.trains.Train;
import com.maddox.il2.objects.vehicles.artillery.AAA;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
import com.maddox.rts.LDRres;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

public class ZutiSupportMethods
{
  private static String ZUTI_LOADOUT_NONE = "";
  private static String ZUTI_LOADOUT_NULL = "";
  private static String[] ZUTI_TIP = new String[3];
  private static boolean ZUTI_TARGETS_LOADED = false;
  public static int ZUTI_KIA_COUNTER = 0;
  public static boolean ZUTI_KIA_DELAY_CLEARED = false;
  public static List ZUTI_BANNED_PILOTS;
  public static List ZUTI_DEAD_TARGETS;
  public static long BASE_CAPRUTING_LAST_CHECK = 0L;
  public static int BASE_CAPTURING_INTERVAL = 2000;

  public static void clear()
  {
    if (ZUTI_BANNED_PILOTS != null)
      ZUTI_BANNED_PILOTS.clear();
    if (ZUTI_DEAD_TARGETS != null)
      ZUTI_DEAD_TARGETS.clear();
  }

  public static void setTargetsLoaded(boolean paramBoolean)
  {
    ZUTI_TARGETS_LOADED = paramBoolean;
  }

  public static void fillAirInterval(GUIPad paramGUIPad)
  {
    Mission localMission = Main.cur().mission;
    if (localMission != null) localMission.getClass(); else {
      return;
    }
    try
    {
      Actor localActor = null;
      ZutiPadObject localZutiPadObject = null;

      Aircraft localAircraft = World.getPlayerAircraft();
      boolean bool = localMission.zutiRadar_RefreshInterval > 0;

      List localList = Engine.targets();
      int i = localList.size();
      for (int j = 0; j < i; j++)
      {
        localActor = (Actor)localList.get(j);

        if (localActor.equals(localAircraft)) {
          continue;
        }
        if ((!(localActor instanceof Aircraft)) || (localActor.getDiedFlag()) || (paramGUIPad.zutiPadObjects.containsKey(new Integer(localActor.hashCode()))))
          continue;
        localZutiPadObject = new ZutiPadObject(localActor, bool);
        localZutiPadObject.type = 0;
        paramGUIPad.zutiPadObjects.put(new Integer(localZutiPadObject.hashCode()), localZutiPadObject);
      }

    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static void fillGroundChiefsArray(GUIPad paramGUIPad)
  {
    Mission localMission = Main.cur().mission;

    if (localMission != null) localMission.getClass(); else {
      return;
    }
    boolean bool = localMission.zutiRadar_RefreshInterval > 0;

    ZutiPadObject localZutiPadObject = null;

    HashMapExt localHashMapExt = Engine.name2Actor();
    Actor localActor = null;

    for (Map.Entry localEntry = localHashMapExt.nextEntry(null); localEntry != null; localEntry = localHashMapExt.nextEntry(localEntry))
    {
      localActor = (Actor)localEntry.getValue();
      if (GUI.pad.zutiPadObjects.containsKey(new Integer(localActor.hashCode()))) {
        continue;
      }
      if ((!(localActor instanceof Chief)) || (localActor.getDiedFlag()))
        continue;
      if ((localActor instanceof TankGeneric))
      {
        localZutiPadObject = new ZutiPadObject(localActor, bool);
        localZutiPadObject.type = 1;

        paramGUIPad.zutiPadObjects.put(new Integer(localZutiPadObject.hashCode()), localZutiPadObject);
      }
      else if ((localActor instanceof Train))
      {
        localZutiPadObject = new ZutiPadObject(localActor, bool);
        localZutiPadObject.type = 5;

        paramGUIPad.zutiPadObjects.put(new Integer(localZutiPadObject.hashCode()), localZutiPadObject);
      }
      else if ((localActor instanceof AAA))
      {
        if (localActor.getDiedFlag())
          continue;
        localZutiPadObject = new ZutiPadObject(localActor, bool);
        localZutiPadObject.type = 2;

        paramGUIPad.zutiPadObjects.put(new Integer(localZutiPadObject.hashCode()), localZutiPadObject);
      }
      else if ((localActor instanceof ChiefGround))
      {
        localZutiPadObject = new ZutiPadObject(localActor, bool);
        localZutiPadObject.type = 5;

        paramGUIPad.zutiPadObjects.put(new Integer(localZutiPadObject.hashCode()), localZutiPadObject);
      } else {
        if ((!(localActor instanceof BigshipGeneric)) && (!(localActor instanceof ShipGeneric)))
          continue;
        if (localActor.getDiedFlag())
          continue;
        localZutiPadObject = new ZutiPadObject(localActor, bool);
        localZutiPadObject.type = 4;

        paramGUIPad.zutiPadObjects.put(new Integer(localZutiPadObject.hashCode()), localZutiPadObject);
      }
    }
  }

  public static void fillNeutralHomeBases(ArrayList paramArrayList)
  {
    if (paramArrayList == null)
      paramArrayList = new ArrayList();
    else {
      paramArrayList.clear();
    }
    ArrayList localArrayList = World.cur().bornPlaces;

    if (localArrayList == null) {
      return;
    }
    int i = localArrayList.size();
    BornPlace localBornPlace = null;

    for (int j = 0; j < i; j++)
    {
      localBornPlace = (BornPlace)localArrayList.get(j);

      if ((localBornPlace.army != 1) && (localBornPlace.army != 2))
        paramArrayList.add(localBornPlace);
    }
  }

  public static String getCountryFromNetRegiment(String paramString)
  {
    ResourceBundle localResourceBundle = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());

    List localList = Regiment.getAll();
    int i = localList.size();
    for (int j = 0; j < i; j++)
    {
      Regiment localRegiment = (Regiment)localList.get(j);
      String str = localResourceBundle.getString(localRegiment.branch());
      if (localRegiment.name().equals(paramString)) {
        return str;
      }
    }
    return "NONE";
  }

  public static String getUserCfgRegiment(String paramString)
  {
    ResourceBundle localResourceBundle = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());

    List localList = Regiment.getAll();
    int i = localList.size();
    for (int j = 0; j < i; j++)
    {
      Regiment localRegiment = (Regiment)localList.get(j);
      String str = localResourceBundle.getString(localRegiment.branch());
      if (str.equals(paramString)) {
        return localRegiment.name();
      }
    }
    return "NoNe";
  }

  public static String getHomeBaseFirstCountry(BornPlace paramBornPlace)
  {
    ArrayList localArrayList = paramBornPlace.zutiHomeBaseCountries;

    if ((localArrayList == null) || (localArrayList.size() == 0)) {
      return "None";
    }
    return (String)localArrayList.get(0);
  }

  public static boolean isRegimentValidForSelectedHB(String paramString, BornPlace paramBornPlace)
  {
    String str1 = getCountryFromNetRegiment(paramString);

    if (paramBornPlace.zutiHomeBaseCountries == null) {
      return false;
    }
    for (int i = 0; i < paramBornPlace.zutiHomeBaseCountries.size(); i++)
    {
      String str2 = (String)paramBornPlace.zutiHomeBaseCountries.get(i);
      if (str2.equals(str1))
      {
        return true;
      }
    }

    return false;
  }

  public static int getPlayerArmy()
  {
    if (Mission.isDogfight()) {
      return ((NetUser)NetEnv.host()).getArmy();
    }
    return World.getPlayerArmy();
  }

  public static void removeTarget(String paramString)
  {
    try
    {
      Object localObject = null;

      Iterator localIterator = GUIBriefing.ZUTI_TARGETS.iterator();
      GUIBriefing.TargetPoint localTargetPoint = null;

      while (localIterator.hasNext())
      {
        localTargetPoint = (GUIBriefing.TargetPoint)localIterator.next();

        if ((localTargetPoint.nameTargetOrig == null) || 
          (localTargetPoint.nameTargetOrig.indexOf(paramString.trim()) <= -1))
          continue;
        localObject = localTargetPoint;
      }

      if (localObject != null)
      {
        GUIBriefing.ZUTI_TARGETS.remove(localObject);
      }
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }

  public static boolean removeTarget(double paramDouble1, double paramDouble2)
  {
    Object localObject = null;
    GUIBriefing.TargetPoint localTargetPoint = null;
    try
    {
      Iterator localIterator = GUIBriefing.ZUTI_TARGETS.iterator();
      while (localIterator.hasNext())
      {
        localTargetPoint = (GUIBriefing.TargetPoint)localIterator.next();

        if ((localTargetPoint.x != paramDouble1) || (localTargetPoint.y != paramDouble2))
          continue;
        localObject = localTargetPoint;
      }

      if (localObject != null)
      {
        GUIBriefing.ZUTI_TARGETS.remove(localObject);
        return true;
      }
    } catch (Exception localException) {
      localException.printStackTrace();
    }
    return false;
  }

  public static void changeTargetIconDescription(Target paramTarget)
  {
    try
    {
      GUIBriefing.TargetPoint localTargetPoint = null;
      Iterator localIterator = GUIBriefing.ZUTI_TARGETS.iterator();
      while (localIterator.hasNext())
      {
        localTargetPoint = (GUIBriefing.TargetPoint)localIterator.next();

        if (((int)(localTargetPoint.x - paramTarget.pos.getAbsPoint().x) != 0) || ((int)(localTargetPoint.y - paramTarget.pos.getAbsPoint().y) != 0))
          continue;
        if (localTargetPoint.type == 1)
        {
          localTargetPoint.type = 6;
          localTargetPoint.icon = IconDraw.get("icons/tdefenceground.mat");
          localTargetPoint.iconOArmy = IconDraw.get("icons/tdestroyground.mat");
        } else {
          if (localTargetPoint.type != 6)
            break;
          localTargetPoint.type = 1;
          localTargetPoint.icon = IconDraw.get("icons/tdestroyground.mat");
          localTargetPoint.iconOArmy = IconDraw.get("icons/tdefenceground.mat");
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static Point3d getNearestTarget(Point3d paramPoint3d, boolean paramBoolean)
  {
    GUIBriefing.TargetPoint localTargetPoint = null;
    Point3d localPoint3d = null;
    double d1 = 100000.0D;
    Iterator localIterator = GUIBriefing.ZUTI_TARGETS.iterator();
    while (localIterator.hasNext())
    {
      localTargetPoint = (GUIBriefing.TargetPoint)localIterator.next();

      if ((paramBoolean) && ((localTargetPoint.type == 0) || (localTargetPoint.type == 1) || (localTargetPoint.type == 2) || (localTargetPoint.type == 4)))
      {
        d2 = Math.sqrt(Math.pow(paramPoint3d.x - localTargetPoint.x, 2.0D) + Math.pow(paramPoint3d.y - localTargetPoint.y, 2.0D));

        if (d2 < d1)
        {
          d1 = d2;
          localPoint3d = new Point3d(localTargetPoint.x, localTargetPoint.y, 0.0D);
        }
      }

      if ((paramBoolean) || ((localTargetPoint.type != 4) && (localTargetPoint.type != 5) && (localTargetPoint.type != 6) && (localTargetPoint.type != 7)))
        continue;
      double d2 = Math.sqrt(Math.pow(paramPoint3d.x - localTargetPoint.x, 2.0D) + Math.pow(paramPoint3d.y - localTargetPoint.y, 2.0D));

      if (d2 < d1)
      {
        d1 = d2;
        localPoint3d = new Point3d(localTargetPoint.x, localTargetPoint.y, 0.0D);
      }

    }

    return localPoint3d;
  }

  public static void assignTargetActor(GUIBriefing.TargetPoint paramTargetPoint)
  {
    if (paramTargetPoint == null) {
      return;
    }

    Main.cur().mission.getClass();
    try
    {
      paramTargetPoint.actor = Actor.getByName(paramTargetPoint.nameTarget);
      int j;
      if ((paramTargetPoint.actor != null) && ((paramTargetPoint.actor instanceof Wing)))
      {
        Wing localWing = (Wing)paramTargetPoint.actor;
        paramTargetPoint.isBaseActorWing = true;
        paramTargetPoint.wing = localWing;
        j = localWing.airc.length;
        for (int k = 0; k < j; k++)
        {
          if ((localWing.airc[k] == null) || (localWing.airc[k].getDiedFlag()))
            continue;
          paramTargetPoint.actor = localWing.airc[k];
          break;
        }

      }
      else if ((paramTargetPoint.isBaseActorWing) && (paramTargetPoint.wing != null))
      {
        int i = paramTargetPoint.wing.airc.length;
        for (j = 0; j < i; j++)
        {
          if ((paramTargetPoint.wing.airc[j] == null) || (paramTargetPoint.wing.airc[j].getDiedFlag()))
            continue;
          paramTargetPoint.actor = paramTargetPoint.wing.airc[j];
          break;
        }
      }
    }
    catch (Exception localException) {
      localException.printStackTrace();
    }
  }

  public static void fillTargets(SectFile paramSectFile)
  {
    if (ZUTI_TARGETS_LOADED) {
      return;
    }
    GUIBriefing.ZUTI_TARGETS.clear();

    int i = paramSectFile.sectionIndex("Target");
    Object localObject1;
    if (i >= 0)
    {
      int j = paramSectFile.vars(i);
      for (k = 0; k < j; k++)
      {
        localObject1 = new NumberTokenizer(paramSectFile.line(i, k));
        int m = ((NumberTokenizer)localObject1).next(0, 0, 7);
        int n = ((NumberTokenizer)localObject1).next(0, 0, 2);
        if (n == 2)
          continue;
        GUIBriefing.TargetPoint localTargetPoint = new GUIBriefing.TargetPoint();
        localTargetPoint.type = m;
        localTargetPoint.importance = n;
        ((NumberTokenizer)localObject1).next(0);
        ((NumberTokenizer)localObject1).next(0, 0, 720);
        ((NumberTokenizer)localObject1).next(0);
        localTargetPoint.x = ((NumberTokenizer)localObject1).next(0);
        localTargetPoint.y = ((NumberTokenizer)localObject1).next(0);
        int i1 = ((NumberTokenizer)localObject1).next(0);
        if ((localTargetPoint.type == 3) || (localTargetPoint.type == 6) || (localTargetPoint.type == 1))
        {
          if (i1 < 50) i1 = 50;
          if (i1 > 3000) i1 = 3000;
        }
        localTargetPoint.r = i1;
        ((NumberTokenizer)localObject1).next(0);
        localTargetPoint.nameTarget = ((NumberTokenizer)localObject1).next((String)null);

        if ((localTargetPoint.nameTarget != null) && (localTargetPoint.nameTarget.startsWith("Bridge")))
        {
          localTargetPoint.nameTargetOrig = localTargetPoint.nameTarget;
          localTargetPoint.nameTarget = null;
        }
        int i2 = ((NumberTokenizer)localObject1).next(0);
        int i3 = ((NumberTokenizer)localObject1).next(0);
        if ((i2 != 0) && (i3 != 0))
        {
          localTargetPoint.x = i2;
          localTargetPoint.y = i3;
        }

        switch (localTargetPoint.type)
        {
        case 0:
          localTargetPoint.icon = IconDraw.get("icons/tdestroyair.mat");
          localTargetPoint.iconOArmy = IconDraw.get("icons/tdefence.mat");
          if ((localTargetPoint.nameTarget != null) && (paramSectFile.exist("Chiefs", localTargetPoint.nameTarget)))
          {
            localTargetPoint.icon = IconDraw.get("icons/tdestroychief.mat");
            localTargetPoint.iconOArmy = IconDraw.get("icons/tdefence.mat");
          }

          assignTargetActor(localTargetPoint);
          break;
        case 1:
          localTargetPoint.icon = IconDraw.get("icons/tdestroyground.mat");
          localTargetPoint.iconOArmy = IconDraw.get("icons/tdefenceground.mat");
          break;
        case 2:
          localTargetPoint.icon = IconDraw.get("icons/tdestroybridge.mat");
          localTargetPoint.iconOArmy = IconDraw.get("icons/tdefencebridge.mat");
          localTargetPoint.nameTarget = null;
          break;
        case 3:
          localTargetPoint.icon = IconDraw.get("icons/tinspect.mat");
          localTargetPoint.iconOArmy = IconDraw.get("icons/tdefence.mat");

          assignTargetActor(localTargetPoint);
          break;
        case 4:
          localTargetPoint.icon = IconDraw.get("icons/tescort.mat");
          localTargetPoint.iconOArmy = IconDraw.get("icons/tdestroychief.mat");

          assignTargetActor(localTargetPoint);
          break;
        case 5:
          localTargetPoint.icon = IconDraw.get("icons/tdefence.mat");
          localTargetPoint.iconOArmy = IconDraw.get("icons/tdestroychief.mat");

          assignTargetActor(localTargetPoint);
          break;
        case 6:
          localTargetPoint.icon = IconDraw.get("icons/tdefenceground.mat");
          localTargetPoint.iconOArmy = IconDraw.get("icons/tdestroyground.mat");
          break;
        case 7:
          localTargetPoint.icon = IconDraw.get("icons/tdefencebridge.mat");
          localTargetPoint.iconOArmy = IconDraw.get("icons/tdestroybridge.mat");
          localTargetPoint.nameTarget = null;
        }

        if (localTargetPoint.nameTarget != null) {
          localTargetPoint.nameTargetOrig = localTargetPoint.nameTarget;
        }
        else
        {
          localTargetPoint.nameTarget = (new Float(localTargetPoint.x).toString() + new Float(localTargetPoint.y).toString());
          if (localTargetPoint.nameTargetOrig == null) {
            localTargetPoint.nameTargetOrig = localTargetPoint.nameTarget;
          }
        }
        if (localTargetPoint.nameTarget != null)
        {
          Object localObject2;
          if (paramSectFile.exist("Chiefs", localTargetPoint.nameTarget))
          {
            try
            {
              StringTokenizer localStringTokenizer = new StringTokenizer(paramSectFile.get("Chiefs", localTargetPoint.nameTarget, (String)null));
              localObject2 = localStringTokenizer.nextToken();
              int i4 = ((String)localObject2).indexOf(".");
              localTargetPoint.nameTarget = (I18N.technic(((String)localObject2).substring(0, i4)) + " " + I18N.technic(((String)localObject2).substring(i4 + 1)));
            }
            catch (Exception localException1)
            {
              localTargetPoint.nameTarget = null;
            }
          }
          else if (paramSectFile.sectionIndex(localTargetPoint.nameTarget) >= 0)
          {
            try
            {
              String str = paramSectFile.get(localTargetPoint.nameTarget, "Class", (String)null);
              localObject2 = ObjIO.classForName(str);
              localTargetPoint.nameTarget = Property.stringValue(localObject2, "iconFar_shortClassName", null);
            }
            catch (Exception localException2)
            {
              localTargetPoint.nameTarget = null;
            }
          }
          else {
            localTargetPoint.nameTarget = null;
          }
        }

        GUIBriefing.ZUTI_TARGETS.add(localTargetPoint);
      }

    }

    ZutiTargetsSupportMethods.checkForDeactivatedTargets();

    ArrayList localArrayList = World.cur().targetsGuard.zutiTargetNamesToRemove;
    i = localArrayList.size();
    for (int k = 0; k < i; k++)
    {
      removeTarget((String)localArrayList.get(k));
    }
    World.cur().targetsGuard.zutiTargetNamesToRemove.clear();

    localArrayList = World.cur().targetsGuard.zutiTargetPosToRemove;
    i = localArrayList.size();
    for (k = 0; k < i; k++)
    {
      localObject1 = (Target)localArrayList.get(k);
      removeTarget(((Target)localObject1).pos.getAbs().getX(), ((Target)localObject1).pos.getAbs().getY());
    }
    World.cur().targetsGuard.zutiTargetPosToRemove.clear();

    ZUTI_TARGETS_LOADED = true;
  }

  private static void drawTargets(GUIRenders paramGUIRenders, TTFont paramTTFont, Mat paramMat, CameraOrtho2D paramCameraOrtho2D, Set paramSet, int paramInt, boolean paramBoolean)
  {
    Mission localMission = Main.cur().mission;

    localMission.getClass();
    try
    {
      if (paramSet.size() != 0)
      {
        GPoint localGPoint = paramGUIRenders.getMouseXY();
        float f1 = localGPoint.x;
        float f2 = paramGUIRenders.win.dy - 1.0F - localGPoint.y;
        float f3 = IconDraw.scrSizeX() / 2;
        float f4 = f1;
        float f5 = f2;
        IconDraw.setColor(-16711681);

        GUIBriefing.TargetPoint localTargetPoint1 = null;
        GUIBriefing.TargetPoint localTargetPoint2 = null;
        Iterator localIterator = paramSet.iterator();

        while (localIterator.hasNext())
        {
          localTargetPoint1 = (GUIBriefing.TargetPoint)localIterator.next();
          if (localTargetPoint1.icon == null)
          {
            continue;
          }

          if ((localTargetPoint1.isBaseActorWing) && ((localTargetPoint1.actor == null) || (localTargetPoint1.actor.getDiedFlag()))) {
            assignTargetActor(localTargetPoint1);
          }

          float f6 = (float)((localTargetPoint1.x - paramCameraOrtho2D.worldXOffset) * paramCameraOrtho2D.worldScale);
          float f8 = (float)((localTargetPoint1.y - paramCameraOrtho2D.worldYOffset) * paramCameraOrtho2D.worldScale);

          localMission.getClass();

          if (paramBoolean)
            IconDraw.render(localTargetPoint1.icon, f6, f8);
          else {
            IconDraw.render(localTargetPoint1.iconOArmy, f6, f8);
          }
          if ((f6 >= f1 - f3) && (f6 <= f1 + f3) && (f8 >= f2 - f3) && (f8 <= f2 + f3))
          {
            localTargetPoint2 = localTargetPoint1;
            f4 = f6;
            f5 = f8;
          }

        }

        if (localTargetPoint2 != null)
        {
          for (int i = 0; i < 3; i++) {
            ZUTI_TIP[i] = null;
          }
          if (localTargetPoint2.importance == 0)
            ZUTI_TIP[0] = I18N.gui("brief.Primary");
          else {
            ZUTI_TIP[0] = I18N.gui("brief.Secondary");
          }
          if (paramBoolean)
          {
            switch (localTargetPoint2.type)
            {
            case 0:
              ZUTI_TIP[1] = I18N.gui("brief.Destroy");
              break;
            case 1:
              ZUTI_TIP[1] = I18N.gui("brief.DestroyGround");
              break;
            case 2:
              ZUTI_TIP[1] = I18N.gui("brief.DestroyBridge");
              break;
            case 3:
              ZUTI_TIP[1] = I18N.gui("brief.Inspect");
              break;
            case 4:
              ZUTI_TIP[1] = I18N.gui("brief.Escort");
              break;
            case 5:
              ZUTI_TIP[1] = I18N.gui("brief.Defence");
              break;
            case 6:
              ZUTI_TIP[1] = I18N.gui("brief.DefenceGround");
              break;
            case 7:
              ZUTI_TIP[1] = I18N.gui("brief.DefenceBridge");
            }

          }
          else
          {
            switch (localTargetPoint2.type)
            {
            case 0:
              ZUTI_TIP[1] = I18N.gui("brief.Defence");
              break;
            case 1:
              ZUTI_TIP[1] = I18N.gui("brief.DefenceGround");
              break;
            case 2:
              ZUTI_TIP[1] = I18N.gui("brief.DefenceBridge");
              break;
            case 3:
              ZUTI_TIP[1] = I18N.gui("brief.Defence");
              break;
            case 4:
              ZUTI_TIP[1] = I18N.gui("brief.Destroy");
              break;
            case 5:
              ZUTI_TIP[1] = I18N.gui("brief.Destroy");
              break;
            case 6:
              ZUTI_TIP[1] = I18N.gui("brief.DestroyGround");
              break;
            case 7:
              ZUTI_TIP[1] = I18N.gui("brief.DestroyBridge");
            }
          }

          if (localTargetPoint2.nameTarget != null)
            ZUTI_TIP[2] = localTargetPoint2.nameTarget;
          float f7 = paramTTFont.width(ZUTI_TIP[0]);
          int j = 1;
          for (int k = 1; k < 3; k++)
          {
            if (ZUTI_TIP[k] == null)
              break;
            j = k;
            f10 = paramTTFont.width(ZUTI_TIP[k]);
            if (f7 < f10)
              f7 = f10;
          }
          float f9 = -paramTTFont.descender();
          float f10 = paramTTFont.height() + f9;
          f7 += 2.0F * f9;
          float f11 = f10 * (j + 1) + 2.0F * f9;
          float f12 = f4 - f7 / 2.0F;
          float f13 = f5 + f3;
          if (f12 + f7 > paramGUIRenders.win.dx)
            f12 = paramGUIRenders.win.dx - f7;
          if (f13 + f11 > paramGUIRenders.win.dy)
            f13 = paramGUIRenders.win.dy - f11;
          if (f12 < 0.0F)
            f12 = 0.0F;
          if (f13 < 0.0F)
            f13 = 0.0F;
          Render.drawTile(f12, f13, f7, f11, 0.0F, paramMat, -813694977, 0.0F, 0.0F, 1.0F, 1.0F);
          Render.drawEnd();
          for (int m = 0; m <= j; m++)
            paramTTFont.output(-16777216, f12 + f9, f13 + f9 + (j - m) * f10 + f9, 0.0F, ZUTI_TIP[m]);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static void drawTargets(GUIRenders paramGUIRenders, TTFont paramTTFont, Mat paramMat, CameraOrtho2D paramCameraOrtho2D)
  {
    try
    {
      int i = getPlayerArmy();

      if (i < 1) {
        return;
      }
      if (i == World.getMissionArmy())
      {
        drawTargets(paramGUIRenders, paramTTFont, paramMat, paramCameraOrtho2D, GUIBriefing.ZUTI_TARGETS, i, true);
      }
      else
      {
        drawTargets(paramGUIRenders, paramTTFont, paramMat, paramCameraOrtho2D, GUIBriefing.ZUTI_TARGETS, i, false);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static ArrayList getAirNames(ArrayList paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();

    if (paramArrayList == null) {
      return localArrayList;
    }
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      ZutiAircraft localZutiAircraft = (ZutiAircraft)paramArrayList.get(i);
      localArrayList.add(localZutiAircraft.getAcName());
    }

    return localArrayList;
  }

  public static Airport getAirport(double paramDouble1, double paramDouble2)
  {
    ArrayList localArrayList = new ArrayList();
    World.getAirports(localArrayList);

    double d1 = 1000000.0D;
    Object localObject = null;
    try
    {
      int i = localArrayList.size();
      for (int j = 0; j < i; j++)
      {
        Airport localAirport = (Airport)localArrayList.get(j);
        Point3d localPoint3d = localAirport.pos.getAbsPoint();

        double d2 = Math.sqrt(Math.pow(paramDouble1 - localPoint3d.x, 2.0D) + Math.pow(paramDouble2 - localPoint3d.y, 2.0D));
        if (d2 >= d1)
          continue;
        d1 = d2;
        localObject = localAirport;
      }
    }
    catch (Exception localException)
    {
    }
    return localObject;
  }

  public static BornPlace getPlayerBornPlace(Point3d paramPoint3d, int paramInt)
  {
    NetUser localNetUser = (NetUser)NetEnv.host();
    int i = localNetUser.getBornPlace();
    BornPlace localBornPlace = (BornPlace)World.cur().bornPlaces.get(i);

    if (paramPoint3d == null) {
      return localBornPlace;
    }
    if (i == -1) {
      localBornPlace = getNearestBornPlace(paramPoint3d.x, paramPoint3d.y, paramInt);
    }
    return localBornPlace;
  }

  public static String getACSelectedLoadoutName(List paramList, String paramString, int paramInt, boolean paramBoolean)
  {
    try
    {
      if (paramList == null) {
        return ZUTI_LOADOUT_NONE;
      }
      int i = paramList.size();
      for (int j = 0; j < i; j++)
      {
        ZutiAircraft localZutiAircraft = (ZutiAircraft)paramList.get(j);
        if (!localZutiAircraft.getAcName().equals(paramString))
          continue;
        String str = localZutiAircraft.getLoadoutById(paramInt);
        if (str != null)
        {
          if (paramBoolean)
            return localZutiAircraft.getWeaponI18NName(localZutiAircraft.getLoadoutById(paramInt));
          return localZutiAircraft.getLoadoutById(paramInt);
        }

        return ZUTI_LOADOUT_NULL;
      }
    }
    catch (Exception localException) {
      System.out.println("BornPlace error, ID_02: " + localException.toString());
    }return ZUTI_LOADOUT_NONE;
  }

  public static BornPlace getNearestBornPlace_AnyArmy(double paramDouble1, double paramDouble2)
  {
    ArrayList localArrayList = World.cur().bornPlaces;
    double d1 = 1000000.0D;
    Object localObject = null;
    try
    {
      int i = localArrayList.size();
      for (int j = 0; j < i; j++)
      {
        BornPlace localBornPlace = (BornPlace)localArrayList.get(j);
        double d2 = Math.sqrt(Math.pow(paramDouble1 - localBornPlace.place.x, 2.0D) + Math.pow(paramDouble2 - localBornPlace.place.y, 2.0D));
        if (d2 >= d1)
          continue;
        d1 = d2;
        localObject = localBornPlace;
      }
    }
    catch (Exception localException)
    {
    }

    if ((localObject == null) || (d1 > localObject.r)) {
      return null;
    }
    return localObject;
  }

  public static BornPlace getNearestBornPlace(double paramDouble1, double paramDouble2, int paramInt)
  {
    ArrayList localArrayList = World.cur().bornPlaces;
    double d1 = 1000000.0D;
    Object localObject = null;
    try
    {
      int i = localArrayList.size();
      for (int j = 0; j < i; j++)
      {
        BornPlace localBornPlace = (BornPlace)localArrayList.get(j);
        if (localBornPlace.army != paramInt)
          continue;
        double d2 = Math.sqrt(Math.pow(paramDouble1 - localBornPlace.place.x, 2.0D) + Math.pow(paramDouble2 - localBornPlace.place.y, 2.0D));

        if (d2 >= d1)
          continue;
        d1 = d2;
        localObject = localBornPlace;
      }
    }
    catch (Exception localException)
    {
    }

    return localObject;
  }

  public static List getUnavailableAircraftList(BornPlace paramBornPlace)
  {
    ArrayList localArrayList1 = new ArrayList();

    if (paramBornPlace == null) {
      return localArrayList1;
    }
    ArrayList localArrayList2 = paramBornPlace.zutiGetNotAvailablePlanesList();
    StringBuffer localStringBuffer = new StringBuffer();

    if (localArrayList2 != null)
    {
      for (int i = 0; i < localArrayList2.size(); i++)
      {
        if (localStringBuffer.toString().length() < 200)
        {
          localStringBuffer.append((String)localArrayList2.get(i));
          localStringBuffer.append(" ");
        }
        else
        {
          localArrayList1.add(localStringBuffer.toString().trim());

          localStringBuffer = new StringBuffer();
          localStringBuffer.append((String)localArrayList2.get(i));
          localStringBuffer.append(" ");
        }
      }

      localArrayList1.add(localStringBuffer.toString().trim());
    }

    return localArrayList1;
  }

  public static void setAircraftAvailabilityForHomeBase(ArrayList paramArrayList, double paramDouble1, double paramDouble2)
  {
    if ((World.cur() == null) || (Main.cur().netServerParams == null) || (Main.cur().netServerParams.isMaster()))
    {
      return;
    }

    ArrayList localArrayList = World.cur().bornPlaces;

    if (localArrayList == null)
    {
      return;
    }

    for (int i = 0; i < localArrayList.size(); i++)
    {
      BornPlace localBornPlace = (BornPlace)localArrayList.get(i);
      if ((localBornPlace.place.x == paramDouble1) && (localBornPlace.place.y == paramDouble2))
        localBornPlace.zutiActivatePlanes(paramArrayList);
    }
  }

  public static NetUser getNetUser(String paramString)
  {
    List localList = NetEnv.hosts();
    NetUser localNetUser = null;

    localNetUser = (NetUser)NetEnv.host();
    if (localNetUser.uniqueName().equals(paramString)) {
      return localNetUser;
    }
    for (int i = 0; i < localList.size(); i++)
    {
      localNetUser = (NetUser)localList.get(i);

      if (localNetUser.uniqueName().equals(paramString)) {
        return localNetUser;
      }
    }
    return localNetUser;
  }

  public static void setPlayerBanDuration(long paramLong)
  {
    NetUser localNetUser = (NetUser)NetEnv.host();
    String str1 = localNetUser.uniqueName();
    String str2 = "127.0.0.1";
    try { str2 = localNetUser.masterChannel().remoteAddress().getHostAddress().toString();
    }
    catch (Exception localException)
    {
    }
    Object localObject = null;
    ZutiBannedUser localZutiBannedUser = null;

    if (ZUTI_BANNED_PILOTS != null)
    {
      int i = ZUTI_BANNED_PILOTS.size();
      for (int j = 0; j < i; j++)
      {
        localZutiBannedUser = (ZutiBannedUser)ZUTI_BANNED_PILOTS.get(j);
        if (!localZutiBannedUser.isMatch(str1, str2))
          continue;
        localObject = localZutiBannedUser;
        break;
      }
    }

    if (localObject != null)
      localObject.setDuration(Time.current() + paramLong * 1000L);
  }

  public static boolean isPlayerBanned(String paramString1, String paramString2)
  {
    if (Main.cur().mission == null) {
      return false;
    }
    Object localObject = null;
    if (ZUTI_BANNED_PILOTS != null)
    {
      int i = ZUTI_BANNED_PILOTS.size();
      for (int j = 0; j < i; j++)
      {
        ZutiBannedUser localZutiBannedUser = (ZutiBannedUser)ZUTI_BANNED_PILOTS.get(j);
        if (!localZutiBannedUser.isMatch(paramString1, paramString2))
          continue;
        localObject = localZutiBannedUser;
        break;
      }
    }

    if (localObject == null)
    {
      localObject = new ZutiBannedUser();
      ((ZutiBannedUser)localObject).setName(paramString1);
      ((ZutiBannedUser)localObject).setIP(paramString2);
      ((ZutiBannedUser)localObject).setDuration(0L);
      ZUTI_BANNED_PILOTS.add(localObject);
    }
    else
    {
      if ((Main.cur().mission.zutiMisc_EnableReflyOnlyIfBailedOrDied) && (((ZutiBannedUser)localObject).isBanned()))
        return true;
      if (Main.cur().mission.zutiMisc_DisableReflyForMissionDuration) {
        return true;
      }
    }
    return false;
  }

  public static void managePilotBornPlacePlaneCounter(NetAircraft paramNetAircraft, boolean paramBoolean)
  {
    if (paramNetAircraft == null) {
      return;
    }
    if (paramNetAircraft.net == null) {
      return;
    }
    NetUser localNetUser = ((NetAircraft.AircraftNet)paramNetAircraft.net).netUser;

    if (localNetUser == null) {
      return;
    }

    String str = Property.stringValue(((Aircraft)paramNetAircraft).getClass(), "keyName");

    BornPlace localBornPlace = (BornPlace)World.cur().bornPlaces.get(localNetUser.getBornPlace());
    if (localBornPlace != null)
    {
      localBornPlace.zutiReleaseAircraft(paramNetAircraft.FM, str, ZutiAircraft.isPlaneUsable(paramNetAircraft.FM), false, paramBoolean);
    }
  }

  public static Point3d getWingTakeoffLocation(SectFile paramSectFile, String paramString)
  {
    String str1 = paramString + "_Way";

    int i = paramSectFile.sectionIndex(str1);
    if (i < 0) {
      return null;
    }
    int j = paramSectFile.vars(i);
    if (j < 0) {
      return null;
    }
    String str2 = paramSectFile.var(i, 0);
    if (str2.equalsIgnoreCase("TAKEOFF"))
    {
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.value(i, 0));
      Point3d localPoint3d = new Point3d();
      localPoint3d.x = localNumberTokenizer.next(0.0F, -1000000.0F, 1000000.0F);
      localPoint3d.y = localNumberTokenizer.next(0.0F, -1000000.0F, 1000000.0F);

      return localPoint3d;
    }

    return null;
  }

  public static boolean isPlaneStationary(FlightModel paramFlightModel)
  {
    return (paramFlightModel.getSpeedKMH() < 1.0D) && (paramFlightModel.getVertSpeed() < 1.0D);
  }

  public static boolean isStaticActor(Actor paramActor)
  {
    if (paramActor.getArmy() == 0)
      return false;
    if ((paramActor instanceof ShipGeneric))
      return ((ShipGeneric)paramActor).zutiIsStatic();
    if ((paramActor instanceof BigshipGeneric))
      return ((BigshipGeneric)paramActor).zutiIsStatic();
    if ((paramActor instanceof Aircraft))
      return false;
    if ((paramActor instanceof Chief))
      return false;
    if ((paramActor instanceof Wing))
      return false;
    return (!Actor.isValid(paramActor.getOwner())) || (!(paramActor.getOwner() instanceof Chief));
  }

  public static void removeBornPlace(BornPlace paramBornPlace)
  {
    disconnectPilotsFromBornPlace(paramBornPlace);
    int i = paramBornPlace.zutiBpStayPoints.size();
    ZutiStayPoint localZutiStayPoint = null;

    for (int j = 0; j < i; j++)
    {
      try
      {
        localZutiStayPoint = (ZutiStayPoint)paramBornPlace.zutiBpStayPoints.get(j);
        localZutiStayPoint.pointStay.set(-1000000.0F, -1000000.0F);
      } catch (Exception localException) {
      }
    }
    World.cur().bornPlaces.remove(paramBornPlace);

    paramBornPlace = null;
  }

  public static void disconnectPilotsFromBornPlace(BornPlace paramBornPlace)
  {
    ArrayList localArrayList = World.cur().bornPlaces;
    int i = localArrayList.size();
    NetUser localNetUser = null;
    List localList = null;

    for (int j = 0; j < i; j++)
    {
      try
      {
        if ((BornPlace)localArrayList.get(j) == paramBornPlace)
        {
          localNetUser = (NetUser)NetEnv.host();
          if (localNetUser.getBornPlace() == j) {
            localNetUser.setBornPlace(-1);
          }
          localList = NetEnv.hosts();
          int k = localList.size();
          for (int m = 0; m < k; m++)
          {
            localNetUser = (NetUser)localList.get(m);
            if (localNetUser.getBornPlace() == j)
              localNetUser.setBornPlace(-1);
          }
          break;
        }
      }
      catch (Exception localException)
      {
      }
    }
  }

  public static BornPlace getBornPlace(double paramDouble1, double paramDouble2)
  {
    World localWorld = World.cur();

    if ((localWorld == null) || (localWorld.bornPlaces == null)) {
      return null;
    }
    ArrayList localArrayList = localWorld.bornPlaces;
    BornPlace localBornPlace = null;

    for (int i = 0; i < localArrayList.size(); i++)
    {
      localBornPlace = (BornPlace)localArrayList.get(i);
      if ((localBornPlace.place.x == paramDouble1) && (localBornPlace.place.y == paramDouble2)) {
        return localBornPlace;
      }
    }
    return null;
  }
}