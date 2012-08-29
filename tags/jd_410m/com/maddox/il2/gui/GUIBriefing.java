package com.maddox.il2.gui;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ZutiTargetsSupportMethods;
import com.maddox.il2.ai.air.CellAirField;
import com.maddox.il2.ai.air.CellAirPlane;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiNetSendMethods;
import com.maddox.il2.game.ZutiPadObject;
import com.maddox.il2.game.ZutiRadarRefresh;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserRegiment;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.vehicles.artillery.RocketryRocket;
import com.maddox.il2.objects.vehicles.radios.Beacon;
import com.maddox.il2.objects.vehicles.radios.Beacon.LorenzBLBeacon;
import com.maddox.il2.objects.vehicles.radios.Beacon.LorenzBLBeacon_AAIAS;
import com.maddox.il2.objects.vehicles.radios.Beacon.LorenzBLBeacon_LongRunway;
import com.maddox.il2.objects.vehicles.radios.Beacon.RadioBeacon;
import com.maddox.il2.objects.vehicles.radios.Beacon.RadioBeaconLowVis;
import com.maddox.il2.objects.vehicles.radios.Beacon.YGBeacon;
import com.maddox.il2.objects.vehicles.radios.TypeHasBeacon;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class GUIBriefing extends GUIBriefingGeneric
{
  protected String playerName;
  protected ArrayList playerPath = new ArrayList();
  protected ArrayList targets = new ArrayList();
  protected ArrayList beacons = new ArrayList();
  protected Mat iconBornPlace;
  protected Mat iconPlayer;
  private double lastScale = 0.0D;

  public static Set ZUTI_TARGETS = new HashSet();

  public static boolean ZUTI_IS_BRIEFING_ACTIVE = false;

  private static String[] tip = new String[3];

  private float[] lineNXYZ = new float[6];
  private int lineNCounter;
  protected boolean bSelectBorn = false;

  public void _enter()
  {
    this.playerPath.clear();
    this.playerName = null;
    super._enter();

    ZutiRadarRefresh.findRadars(ZutiSupportMethods.getPlayerArmy());

    ZutiRadarRefresh.resetStartTimes();

    ZUTI_IS_BRIEFING_ACTIVE = true;

    ZutiNetSendMethods.requestUnavailableAircraftList();

    ZutiNetSendMethods.requestCompletedReconList();

    ZutiTargetsSupportMethods.checkForDeactivatedTargets();

    NetAircraft.ZUTI_REFLY_OWERRIDE = false;

    fillBeacons();
  }

  public void _leave()
  {
    super._leave();

    ZUTI_IS_BRIEFING_ACTIVE = false;
  }

  private void drawBornPlaces() {
    if (this.iconBornPlace == null)
      return;
    ArrayList localArrayList = World.cur().bornPlaces;
    if ((localArrayList == null) || (localArrayList.size() == 0))
      return;
    int i = localArrayList.size();

    for (int j = 0; j < i; j++) {
      BornPlace localBornPlace1 = (BornPlace)localArrayList.get(j);
      localBornPlace1.tmpForBrief = 0;
    }

    NetUser localNetUser = (NetUser)NetEnv.host();
    int k = localNetUser.getBornPlace();
    if ((k >= 0) && (k < i)) {
      localObject1 = (BornPlace)localArrayList.get(k);
      ((BornPlace)localObject1).tmpForBrief = 1;
    }
    Object localObject1 = NetEnv.hosts();
    Object localObject2;
    for (int m = 0; m < ((List)localObject1).size(); m++) {
      localObject2 = (NetUser)((List)localObject1).get(m);
      int n = ((NetUser)localObject2).getBornPlace();
      if ((n >= 0) && (n < i)) {
        BornPlace localBornPlace2 = (BornPlace)localArrayList.get(n);
        localBornPlace2.tmpForBrief += 1;
      }
    }

    for (m = 0; m < i; m++) {
      localObject2 = (BornPlace)localArrayList.get(m);
      IconDraw.setColor(Army.color(((BornPlace)localObject2).army));
      float f1 = (float)((((BornPlace)localObject2).place.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
      float f2 = (float)((((BornPlace)localObject2).place.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);

      if (((BornPlace)localObject2).zutiStaticPositionOnly)
      {
        f1 = (float)((((BornPlace)localObject2).zutiOriginalX - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
        f2 = (float)((((BornPlace)localObject2).zutiOriginalY - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
      }

      IconDraw.render(this.iconBornPlace, f1, f2);
      if ((m == k) && (this.iconPlayer != null)) {
        Render.drawTile(f1, f2, IconDraw.scrSizeX(), IconDraw.scrSizeY(), 0.0F, this.iconPlayer, Army.color(((BornPlace)localObject2).army), 0.0F, 1.0F, 1.0F, -1.0F);
      }

      if ((((BornPlace)localObject2).tmpForBrief > 0) && (!Main.cur().mission.zutiMisc_HidePlayersCountOnHomeBase))
        this.gridFont.output(Army.color(((BornPlace)localObject2).army), (int)f1 + IconDraw.scrSizeX() / 2 + 2, (int)f2 - IconDraw.scrSizeY() / 2 - 2, 0.0F, "" + ((BornPlace)localObject2).tmpForBrief);
    }
  }

  private void fillBeacons()
  {
    SectFile localSectFile = Main.cur().currentMissionFile;
    int i = -1;
    if (Mission.isDogfight())
      i = ((NetUser)NetEnv.host()).getArmy();
    else {
      i = localSectFile.get("MAIN", "army", 0);
    }

    this.beacons.clear();
    int j = localSectFile.sectionIndex("NStationary");
    if (j < 0)
      return;
    int k = localSectFile.vars(j);
    for (int m = 0; m < k; m++)
    {
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(localSectFile.line(j, m));
      BeaconPoint localBeaconPoint = loadbeacon(i, localNumberTokenizer.next(""), localNumberTokenizer.next(""), localNumberTokenizer.next(0), localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0D));
      if (localBeaconPoint != null)
        this.beacons.add(localBeaconPoint);
    }
  }

  private BeaconPoint loadbeacon(int paramInt1, String paramString1, String paramString2, int paramInt2, double paramDouble1, double paramDouble2)
  {
    if (paramInt1 != paramInt2) {
      return null;
    }
    Class localClass = null;
    try
    {
      localClass = ObjIO.classForName(paramString2);
    }
    catch (Exception localException)
    {
      System.out.println("Mission: class '" + paramString2 + "' not found");
      return null;
    }

    if (TypeHasBeacon.class.isAssignableFrom(localClass))
    {
      BeaconPoint localBeaconPoint = new BeaconPoint();
      localBeaconPoint.x = (float)paramDouble1;
      localBeaconPoint.y = (float)paramDouble2;
      localBeaconPoint.army = paramInt2;

      if (Beacon.RadioBeacon.class.isAssignableFrom(localClass))
      {
        localBeaconPoint.icon = IconDraw.get("icons/beacon.mat");
      }
      else if (Beacon.RadioBeaconLowVis.class.isAssignableFrom(localClass))
      {
        localBeaconPoint.icon = IconDraw.get("icons/beacon.mat");
      }
      else if (Beacon.YGBeacon.class.isAssignableFrom(localClass))
      {
        localBeaconPoint.icon = IconDraw.get("icons/beaconYG.mat");
      }
      else if (Beacon.LorenzBLBeacon.class.isAssignableFrom(localClass))
      {
        localBeaconPoint.icon = IconDraw.get("icons/ILS.mat");
      }
      else if (Beacon.LorenzBLBeacon_LongRunway.class.isAssignableFrom(localClass))
      {
        localBeaconPoint.icon = IconDraw.get("icons/ILS.mat");
      }
      else if (Beacon.LorenzBLBeacon_AAIAS.class.isAssignableFrom(localClass))
      {
        localBeaconPoint.icon = IconDraw.get("icons/ILS.mat");
      }
      else
        return null;
      String str = Beacon.getBeaconID(this.beacons.size());
      localBeaconPoint.id = str;
      return localBeaconPoint;
    }

    return null;
  }

  public static void fillTargets(SectFile paramSectFile, ArrayList paramArrayList)
  {
    paramArrayList.clear();
    int i = paramSectFile.sectionIndex("Target");
    if (i >= 0) {
      int j = paramSectFile.vars(i);
      for (int k = 0; k < j; k++) {
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
        int m = localNumberTokenizer.next(0, 0, 7);
        int n = localNumberTokenizer.next(0, 0, 2);
        if (n == 2)
          continue;
        TargetPoint localTargetPoint = new TargetPoint();
        localTargetPoint.type = m;
        localTargetPoint.importance = n;
        int i1 = localNumberTokenizer.next(0) == 1 ? 1 : 0;
        int i2 = localNumberTokenizer.next(0, 0, 720);
        int i3 = localNumberTokenizer.next(0) == 1 ? 1 : 0;
        localTargetPoint.x = localNumberTokenizer.next(0);
        localTargetPoint.y = localNumberTokenizer.next(0);
        int i4 = localNumberTokenizer.next(0);
        if ((localTargetPoint.type == 3) || (localTargetPoint.type == 6) || (localTargetPoint.type == 1))
        {
          if (i4 < 50) i4 = 50;
          if (i4 > 3000) i4 = 3000;
        }
        localTargetPoint.r = i4;
        int i5 = localNumberTokenizer.next(0);
        localTargetPoint.nameTarget = localNumberTokenizer.next(null);
        if ((localTargetPoint.nameTarget != null) && (localTargetPoint.nameTarget.startsWith("Bridge")))
          localTargetPoint.nameTarget = null;
        int i6 = localNumberTokenizer.next(0);
        int i7 = localNumberTokenizer.next(0);
        if ((i6 != 0) && (i7 != 0)) {
          localTargetPoint.x = i6; localTargetPoint.y = i7;
        }
        switch (localTargetPoint.type) { case 0:
          localTargetPoint.icon = IconDraw.get("icons/tdestroyair.mat");
          if ((localTargetPoint.nameTarget == null) || 
            (!paramSectFile.exist("Chiefs", localTargetPoint.nameTarget))) break;
          localTargetPoint.icon = IconDraw.get("icons/tdestroychief.mat"); break;
        case 1:
          localTargetPoint.icon = IconDraw.get("icons/tdestroyground.mat"); break;
        case 2:
          localTargetPoint.icon = IconDraw.get("icons/tdestroybridge.mat");
          localTargetPoint.nameTarget = null;
          break;
        case 3:
          localTargetPoint.icon = IconDraw.get("icons/tinspect.mat"); break;
        case 4:
          localTargetPoint.icon = IconDraw.get("icons/tescort.mat"); break;
        case 5:
          localTargetPoint.icon = IconDraw.get("icons/tdefence.mat"); break;
        case 6:
          localTargetPoint.icon = IconDraw.get("icons/tdefenceground.mat"); break;
        case 7:
          localTargetPoint.icon = IconDraw.get("icons/tdefencebridge.mat");
          localTargetPoint.nameTarget = null;
        }

        if (localTargetPoint.nameTarget != null)
        {
          Object localObject;
          if (paramSectFile.exist("Chiefs", localTargetPoint.nameTarget)) {
            try {
              StringTokenizer localStringTokenizer = new StringTokenizer(paramSectFile.get("Chiefs", localTargetPoint.nameTarget, (String)null));
              localObject = localStringTokenizer.nextToken();
              int i8 = ((String)localObject).indexOf(".");
              localTargetPoint.nameTarget = (I18N.technic(((String)localObject).substring(0, i8)) + " " + I18N.technic(((String)localObject).substring(i8 + 1)));
            }
            catch (Exception localException1)
            {
              localTargetPoint.nameTarget = null;
            }
          }
          else if (paramSectFile.sectionIndex(localTargetPoint.nameTarget) >= 0)
            try {
              String str = paramSectFile.get(localTargetPoint.nameTarget, "Class", (String)null);
              localObject = ObjIO.classForName(str);
              localTargetPoint.nameTarget = Property.stringValue(localObject, "iconFar_shortClassName", null);
            } catch (Exception localException2) {
              localTargetPoint.nameTarget = null;
            }
          else {
            localTargetPoint.nameTarget = null;
          }
        }
        paramArrayList.add(localTargetPoint);
      }
    }
  }

  public static void drawTargets(GUIRenders paramGUIRenders, TTFont paramTTFont, Mat paramMat, CameraOrtho2D paramCameraOrtho2D, ArrayList paramArrayList)
  {
    int i = paramArrayList.size();
    if (i == 0) return;
    GPoint localGPoint = paramGUIRenders.getMouseXY();
    int j = -1;
    float f1 = localGPoint.x;
    float f2 = paramGUIRenders.win.dy - 1.0F - localGPoint.y;
    float f3 = IconDraw.scrSizeX() / 2;
    float f4 = f1; float f5 = f2;
    IconDraw.setColor(-16711681);
    for (int k = 0; k < i; k++) {
      TargetPoint localTargetPoint2 = (TargetPoint)paramArrayList.get(k);
      if (localTargetPoint2.icon != null) {
        float f7 = (float)((localTargetPoint2.x - paramCameraOrtho2D.worldXOffset) * paramCameraOrtho2D.worldScale);
        float f8 = (float)((localTargetPoint2.y - paramCameraOrtho2D.worldYOffset) * paramCameraOrtho2D.worldScale);
        IconDraw.render(localTargetPoint2.icon, f7, f8);
        if ((f7 >= f1 - f3) && (f7 <= f1 + f3) && (f8 >= f2 - f3) && (f8 <= f2 + f3)) {
          j = k;
          f4 = f7; f5 = f8;
        }
      }
    }
    if (j != -1) {
      TargetPoint localTargetPoint1 = (TargetPoint)paramArrayList.get(j);
      for (int m = 0; m < 3; m++) tip[m] = null;
      if (localTargetPoint1.importance == 0) tip[0] = I18N.gui("brief.Primary"); else
        tip[0] = I18N.gui("brief.Secondary");
      switch (localTargetPoint1.type) { case 0:
        tip[1] = I18N.gui("brief.Destroy"); break;
      case 1:
        tip[1] = I18N.gui("brief.DestroyGround"); break;
      case 2:
        tip[1] = I18N.gui("brief.DestroyBridge"); break;
      case 3:
        tip[1] = I18N.gui("brief.Inspect"); break;
      case 4:
        tip[1] = I18N.gui("brief.Escort"); break;
      case 5:
        tip[1] = I18N.gui("brief.Defence"); break;
      case 6:
        tip[1] = I18N.gui("brief.DefenceGround"); break;
      case 7:
        tip[1] = I18N.gui("brief.DefenceBridge");
      }
      if (localTargetPoint1.nameTarget != null) {
        tip[2] = localTargetPoint1.nameTarget;
      }

      float f6 = paramTTFont.width(tip[0]);
      int n = 1;
      for (int i1 = 1; (i1 < 3) && 
        (tip[i1] != null); i1++)
      {
        n = i1;
        f10 = paramTTFont.width(tip[i1]);
        if (f6 >= f10) continue; f6 = f10;
      }
      float f9 = -paramTTFont.descender();
      float f10 = paramTTFont.height() + f9;
      f6 += 2.0F * f9;
      float f11 = f10 * (n + 1) + 2.0F * f9;

      float f12 = f4 - f6 / 2.0F;
      float f13 = f5 + f3;
      if (f12 + f6 > paramGUIRenders.win.dx) f12 = paramGUIRenders.win.dx - f6;
      if (f13 + f11 > paramGUIRenders.win.dy) f13 = paramGUIRenders.win.dy - f11;
      if (f12 < 0.0F) f12 = 0.0F;
      if (f13 < 0.0F) f13 = 0.0F;

      Render.drawTile(f12, f13, f6, f11, 0.0F, paramMat, -813694977, 0.0F, 0.0F, 1.0F, 1.0F);
      Render.drawEnd();
      for (int i2 = 0; i2 <= n; i2++)
        paramTTFont.output(-16777216, f12 + f9, f13 + f9 + (n - i2) * f10 + f9, 0.0F, tip[i2]);
    }
  }

  public void drawBeacons(GUIRenders paramGUIRenders, TTFont paramTTFont, Mat paramMat, CameraOrtho2D paramCameraOrtho2D, ArrayList paramArrayList)
  {
    int i = paramArrayList.size();
    if (i == 0) {
      return;
    }
    for (int j = 0; j < i; j++)
    {
      BeaconPoint localBeaconPoint = (BeaconPoint)paramArrayList.get(j);
      int k = Army.color(localBeaconPoint.army);
      IconDraw.setColor(k);
      if (localBeaconPoint.icon == null)
        continue;
      float f1 = (float)((localBeaconPoint.x - paramCameraOrtho2D.worldXOffset) * paramCameraOrtho2D.worldScale);
      float f2 = (float)((localBeaconPoint.y - paramCameraOrtho2D.worldYOffset) * paramCameraOrtho2D.worldScale);
      IconDraw.render(localBeaconPoint.icon, f1, f2);

      if (paramCameraOrtho2D.worldScale <= 0.01999999955296516D)
        continue;
      float f3 = 20.0F;
      float f4 = 15.0F;
      this.gridFont.output(k, f1 + f3, f2 - f4, 0.0F, localBeaconPoint.id);
    }
  }

  private void drawBeacons()
  {
    if (World.cur().diffCur.RealisticNavigationInstruments)
      drawBeacons(this.renders, this.gridFont, this.emptyMat, this.cameraMap2D, this.beacons);
  }

  private void drawTargets() {
    drawTargets(this.renders, this.gridFont, this.emptyMat, this.cameraMap2D, this.targets);
  }

  private Mat getIconAir(int paramInt) {
    String str = null;
    switch (paramInt) { case 0:
      str = "normfly"; break;
    case 1:
      str = "takeoff"; break;
    case 2:
      str = "landing"; break;
    case 3:
      str = "gattack"; break;
    default:
      return null;
    }
    return IconDraw.get("icons/" + str + ".mat");
  }

  private void drawPlayerPath() {
    checkPlayerPath();
    int i = this.playerPath.size();
    if (i == 0) return;
    if (this.lineNXYZ.length / 3 <= i)
      this.lineNXYZ = new float[(i + 1) * 3];
    this.lineNCounter = 0;
    for (int j = 0; j < i; j++) {
      PathPoint localPathPoint1 = (PathPoint)this.playerPath.get(j);
      this.lineNXYZ[(this.lineNCounter * 3 + 0)] = (float)((localPathPoint1.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
      this.lineNXYZ[(this.lineNCounter * 3 + 1)] = (float)((localPathPoint1.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
      this.lineNXYZ[(this.lineNCounter * 3 + 2)] = 0.0F;
      this.lineNCounter += 1;
    }
    Render.drawBeginLines(-1);
    Render.drawLines(this.lineNXYZ, this.lineNCounter, 2.0F, -16777216, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE | Mat.BLEND, 3);

    Render.drawEnd();
    IconDraw.setColor(-16711681);
    float f1 = 0.0F;
    for (int k = 0; k < i; k++) {
      PathPoint localPathPoint2 = (PathPoint)this.playerPath.get(k);
      float f2 = (float)((localPathPoint2.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
      float f3 = (float)((localPathPoint2.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
      IconDraw.render(getIconAir(localPathPoint2.type), f2, f3);

      if (k == i - 1) {
        this.gridFont.output(-16777216, (int)f2 + IconDraw.scrSizeX() / 2 + 2, (int)f3 - IconDraw.scrSizeY() / 2 - 2, 0.0F, "" + (k + 1));
      }

      float f4 = 1.0F - this.curScale / 7.0F;
      if (k >= i - 1)
        continue;
      PathPoint localPathPoint3 = (PathPoint)this.playerPath.get(k + 1);
      Point3f localPoint3f1 = new Point3f(localPathPoint2.x, localPathPoint2.y, 0.0F);
      Point3f localPoint3f2 = new Point3f(localPathPoint3.x, localPathPoint3.y, 0.0F);
      localPoint3f1.sub(localPoint3f2);
      float f5 = 57.324841F * (float)Math.atan2(localPoint3f1.x, localPoint3f1.y);

      for (f5 = (f5 + 180.0F) % 360.0F; f5 < 0.0F; f5 += 360.0F);
      while (f5 >= 360.0F) f5 -= 360.0F;

      f5 = Math.round(f5);

      float f6 = 0.0F;
      float f7 = 0.0F;

      if ((f5 >= 0.0F) && (f5 < 90.0F))
      {
        f6 = 15.0F;
        f7 = -40.0F;
        if ((f1 >= 270.0F) && (f1 <= 360.0F))
        {
          f6 = -70.0F;
          f7 = 60.0F;
        }
      }
      else if ((f5 >= 90.0F) && (f5 < 180.0F))
      {
        f6 = 15.0F;
        f7 = 60.0F;
        if ((f1 >= 180.0F) && (f1 < 270.0F))
        {
          f6 = -70.0F;
          f7 = -15.0F;
        }
      }
      else if ((f5 >= 180.0F) && (f5 < 270.0F))
      {
        f6 = -70.0F;
        f7 = 60.0F;
        if ((f1 >= 90.0F) && (f1 < 180.0F))
        {
          f6 = 15.0F;
          f7 = 60.0F;
        }
      }
      else if ((f5 >= 270.0F) && (f5 <= 360.0F))
      {
        f6 = -70.0F;
        f7 = -40.0F;
        if ((f1 >= 0.0F) && (f1 < 90.0F))
        {
          f6 = 15.0F;
          f7 = -40.0F;
        }
      }

      f6 *= f4;
      f7 *= f4;

      if (this.curScale >= 3)
      {
        if (f6 < 0.0F)
          f6 /= 2.0F;
        if (f7 > 0.0F) {
          f7 /= 2.0F;
        }
      }
      f1 = f5;
      this.gridFont.output(-16777216, f2 + f6, f3 + f7, 0.0F, "" + (k + 1));
      if (this.curScale >= 2)
        continue;
      double d = Math.sqrt(localPoint3f1.y * localPoint3f1.y + localPoint3f1.x * localPoint3f1.x) / 1000.0D;
      if (d < 0.5D)
        continue;
      String str1 = " km";

      if ((HUD.drawSpeed() == 2) || (HUD.drawSpeed() == 3))
      {
        d *= 0.5399569869041443D;
        str1 = " nm";
      }

      String str2 = "" + d;
      str2 = str2.substring(0, str2.indexOf(".") + 2);

      this.gridFont.output(-16777216, f2 + f6, f3 + f7 - 22.0F, 0.0F, (int)f5 + "°");
      this.gridFont.output(-16777216, f2 + f6, f3 + f7 - 44.0F, 0.0F, str2 + str1);
    }
  }

  private void checkPlayerPath()
  {
    SectFile localSectFile = Main.cur().currentMissionFile;
    String str1 = null;
    if (Mission.isCoop()) {
      str1 = GUINetAircraft.selectedWingName();
      if (str1 == null)
        str1 = localSectFile.get("MAIN", "player", (String)null);
    } else {
      str1 = localSectFile.get("MAIN", "player", (String)null);
    }
    if (str1 == null) {
      if (this.playerName == null) return;
      this.playerPath.clear();
      this.playerName = null;
      return;
    }
    if (str1.equals(this.playerName))
      return;
    this.playerName = str1;
    this.playerPath.clear();
    if (this.playerName != null) {
      int i = localSectFile.sectionIndex(this.playerName + "_WAY");
      if (i >= 0) {
        int j = localSectFile.vars(i);
        for (int k = 0; k < j; k++) {
          PathPoint localPathPoint = new PathPoint(null);
          String str2 = localSectFile.var(i, k);
          if ("NORMFLY".equals(str2)) localPathPoint.type = 0;
          else if ("TAKEOFF".equals(str2)) localPathPoint.type = 1;
          else if ("LANDING".equals(str2)) localPathPoint.type = 2;
          else if ("GATTACK".equals(str2)) localPathPoint.type = 3; else
            localPathPoint.type = 0;
          String str3 = localSectFile.value(i, k);
          if ((str3 == null) || (str3.length() <= 0)) {
            localPathPoint.x = (localPathPoint.y = 0.0F);
          } else {
            NumberTokenizer localNumberTokenizer = new NumberTokenizer(str3);
            localPathPoint.x = localNumberTokenizer.next(-1.0E+030F, -1.0E+030F, 1.0E+030F);
            localPathPoint.y = localNumberTokenizer.next(-1.0E+030F, -1.0E+030F, 1.0E+030F);
            double d1 = localNumberTokenizer.next(0.0D, 0.0D, 10000.0D);
            double d2 = localNumberTokenizer.next(0.0D, 0.0D, 1000.0D);
          }
          this.playerPath.add(localPathPoint);
        }
      }
    }
  }

  protected void doRenderMap2D()
  {
    ZutiRadarRefresh.update(this.lastScale < this.cameraMap2D.worldScale);
    this.lastScale = this.cameraMap2D.worldScale;

    int i = (int)Math.round(Mission.ZUTI_ICON_SIZE * this.client.root.win.dx / 1024.0D);
    IconDraw.setScrSize(i, i);
    try
    {
      Mission localMission = Main.cur().mission;
      if (localMission != null)
      {
        localMission.getClass(); localMission.getClass();
        ZutiSupportMethods.drawTargets(this.renders, this.gridFont, this.emptyMat, this.cameraMap2D);
      }
      else {
        drawTargets();
      }

      drawBornPlaces();

      drawPlayerPath();

      drawBeacons();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected int findBornPlace(float paramFloat1, float paramFloat2)
  {
    if ((id() != 40) && (id() != 39))
      return -1;
    ArrayList localArrayList = World.cur().bornPlaces;
    if ((localArrayList == null) || (localArrayList.size() == 0))
      return -1;
    int i = localArrayList.size();
    double d1 = IconDraw.scrSizeX() / 2 / this.cameraMap2D.worldScale;
    d1 = 2.0D * d1 * d1;
    for (int j = 0; j < i; j++) {
      BornPlace localBornPlace = (BornPlace)localArrayList.get(j);

      if (localBornPlace.zutiDisableSpawning)
      {
        continue;
      }
      double d2 = localBornPlace.place.x;
      double d3 = localBornPlace.place.y;
      if (localBornPlace.zutiStaticPositionOnly)
      {
        d2 = localBornPlace.zutiOriginalX;
        d3 = localBornPlace.zutiOriginalY;
      }

      if (((d2 - paramFloat1) * (d2 - paramFloat1) + (d3 - paramFloat2) * (d3 - paramFloat2) >= d1) || (localBornPlace.army == 0)) {
        continue;
      }
      if (localBornPlace.zutiCanUserJoin())
      {
        return j;
      }
    }
    return -1;
  }

  protected boolean isBornPlace(float paramFloat1, float paramFloat2) {
    return findBornPlace(paramFloat1, paramFloat2) >= 0;
  }
  protected void setBornPlace(float paramFloat1, float paramFloat2) {
    int i = findBornPlace(paramFloat1, paramFloat2);
    if (i < 0) return;
    NetUser localNetUser = (NetUser)NetEnv.host();
    int j = localNetUser.getArmy();
    localNetUser.setBornPlace(i);
    if ((j != localNetUser.getArmy()) && (this.briefSound != null)) {
      localObject = Main.cur().currentMissionFile.get("MAIN", "briefSound" + localNetUser.getArmy());
      if (localObject != null) {
        this.briefSound = ((String)localObject);
        CmdEnv.top().exec("music LIST " + this.briefSound);
      }

    }

    Object localObject = (BornPlace)World.cur().bornPlaces.get(i);
    UserCfg localUserCfg = World.cur().userCfg;

    if ((localUserCfg != null) && (!ZutiSupportMethods.isRegimentValidForSelectedHB(localUserCfg.netRegiment, (BornPlace)localObject)))
    {
      String str = ZutiSupportMethods.getHomeBaseFirstCountry((BornPlace)localObject);

      str = ZutiSupportMethods.getUserCfgRegiment(str);

      localUserCfg.netRegiment = str;
      localUserCfg.netSquadron = 0;
      GUIAirArming.stateId = 2;
      Main.stateStack().push(55);
    }

    if (localUserCfg != null) {
      int k = 0;
      ArrayList localArrayList = ((BornPlace)localObject).zutiGetAcLoadouts(localUserCfg.netAirName);
      for (int m = 0; m < localArrayList.size(); m++) {
        if (((String)localArrayList.get(m)).equals(I18N.weapons(localUserCfg.netAirName, localUserCfg.getWeapon(localUserCfg.netAirName)))) {
          k = 1;
          break;
        }
      }
      if (k == 0) {
        localUserCfg.setWeapon(localUserCfg.netAirName, (String)localArrayList.get(0));
        GUIAirArming.stateId = 2;
        Main.stateStack().push(55);
      }

    }

    ZutiRadarRefresh.findRadars(ZutiSupportMethods.getPlayerArmy());
    fillBeacons();
  }

  protected void doMouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
    if (paramInt == 0) {
      this.bLPressed = paramBoolean;
      if (this.bSelectBorn) {
        if (this.bLPressed) {
          float f1 = (float)(this.cameraMap2D.worldXOffset + paramFloat1 / this.cameraMap2D.worldScale);
          float f2 = (float)(this.cameraMap2D.worldYOffset + (this.renders.win.dy - paramFloat2 - 1.0F) / this.cameraMap2D.worldScale);

          setBornPlace(f1, f2);
        }
        return;
      }
    }
    super.doMouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
  }
  protected void doMouseMove(float paramFloat1, float paramFloat2) {
    if ((this.bLPressed) && (!this.bSelectBorn)) {
      super.doMouseMove(paramFloat1, paramFloat2);
    } else {
      float f1 = (float)(this.cameraMap2D.worldXOffset + paramFloat1 / this.cameraMap2D.worldScale);
      float f2 = (float)(this.cameraMap2D.worldYOffset + (this.renders.win.dy - paramFloat2 - 1.0F) / this.cameraMap2D.worldScale);

      this.bSelectBorn = isBornPlace(f1, f2);
      this.renders.mouseCursor = (this.bSelectBorn ? 2 : 3);
    }
  }

  protected void fillMap() throws Exception
  {
    super.fillMap();
    SectFile localSectFile = Main.cur().currentMissionFile;
    try
    {
      this.iconBornPlace = IconDraw.get("icons/born.mat");
      this.iconPlayer = IconDraw.get("icons/player.mat");

      ZutiSupportMethods.setTargetsLoaded(false);

      if (Mission.cur() != null)
        ZutiSupportMethods.fillTargets(localSectFile);
      else
        fillTargets(localSectFile, this.targets);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected void clientRender()
  {
    GUIBriefingGeneric.DialogClient localDialogClient = this.dialogClient;
    localDialogClient.draw(localDialogClient.x1024(427.0F), localDialogClient.y1024(633.0F), localDialogClient.x1024(170.0F), localDialogClient.y1024(48.0F), 1, i18n("brief.Fly"));
  }
  protected String infoMenuInfo() {
    return i18n("brief.info");
  }

  public GUIBriefing(int paramInt) {
    super(paramInt);
  }

  protected AirportCarrier getCarrier(NetUser paramNetUser)
  {
    BornPlace localBornPlace = (BornPlace)World.cur().bornPlaces.get(paramNetUser.getBornPlace());
    if (!World.land().isWater(localBornPlace.place.x, localBornPlace.place.y)) {
      return null;
    }
    Loc localLoc = new Loc(localBornPlace.place.x, localBornPlace.place.y, 0.0D, 0.0F, 0.0F, 0.0F);
    AirportCarrier localAirportCarrier = (AirportCarrier)Airport.nearest(localLoc.getPoint(), -1, 4);

    return localAirportCarrier;
  }

  protected boolean isCarrierDeckFree(NetUser paramNetUser)
  {
    BornPlace localBornPlace = (BornPlace)World.cur().bornPlaces.get(paramNetUser.getBornPlace());
    if ((localBornPlace.zutiAirspawnIfCarrierFull) || (localBornPlace.zutiAirspawnOnly) || (!World.cur().diffCur.Takeoff_N_Landing) || (!World.land().isWater(localBornPlace.place.x, localBornPlace.place.y)))
    {
      return true;
    }
    Loc localLoc = new Loc(localBornPlace.place.x, localBornPlace.place.y, 0.0D, 0.0F, 0.0F, 0.0F);
    AirportCarrier localAirportCarrier = (AirportCarrier)Airport.nearest(localLoc.getPoint(), -1, 4);
    if ((localAirportCarrier != null) && (localAirportCarrier.ship().isAlive()) && (!localAirportCarrier.ship().zutiIsStatic()))
    {
      try
      {
        UserCfg localUserCfg = World.cur().userCfg;
        Class localClass = (Class)Property.value(localUserCfg.netAirName, "airClass", null);
        NetAircraft localNetAircraft = (NetAircraft)localClass.newInstance();
        CellAirField localCellAirField = localAirportCarrier.ship().getCellTO();
        Aircraft localAircraft = (Aircraft)localNetAircraft;
        if (localAircraft.FM == null);
        localAircraft.setFM(1, false);
        CellAirPlane localCellAirPlane = localAircraft.getCellAirPlane();
        if (localCellAirField.findPlaceForAirPlane(localCellAirPlane))
        {
          localAircraft = null;
          return true;
        }

        localAircraft = null;
        return false;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return false;
      }
    }
    return true;
  }

  protected boolean isValidArming()
  {
    UserCfg localUserCfg = World.cur().userCfg;
    if (localUserCfg.netRegiment == null) return false;
    if ((((NetUser)NetEnv.host()).netUserRegiment.isEmpty()) && (Actor.getByName(localUserCfg.netRegiment) == null))
      return false;
    if (localUserCfg.netAirName == null) return false;
    if (Property.value(localUserCfg.netAirName, "airClass", null) == null) return false;
    if (localUserCfg.getWeapon(localUserCfg.netAirName) == null) return false; try
    {
      Class localClass1 = (Class)Property.value(localUserCfg.netAirName, "airClass", null);

      NetUser localNetUser = (NetUser)NetEnv.host();
      int i = localNetUser.getBornPlace();
      BornPlace localBornPlace = (BornPlace)World.cur().bornPlaces.get(i);
      if (localBornPlace.airNames != null) {
        ArrayList localArrayList = localBornPlace.airNames;
        int j = 0;
        for (int k = 0; k < localArrayList.size(); k++) {
          String str = (String)localArrayList.get(k);
          Class localClass2 = (Class)Property.value(str, "airClass", null);
          if ((localClass2 == null) || 
            (localClass1 != localClass2)) continue;
          j = 1;
          break;
        }

        if (j == 0)
          return false;
      }
      return Aircraft.weaponsExist(localClass1, localUserCfg.getWeapon(localUserCfg.netAirName)); } catch (Exception localException) {
    }
    return false;
  }

  public static class BeaconPoint
  {
    public float x;
    public float y;
    public Mat icon;
    public int army;
    public String id;
  }

  public static class TargetPoint
  {
    public float x;
    public float y;
    public float z = 0.0F;
    public int r;
    public int type;
    public int typeOArmy;
    public int importance;
    public Mat icon;
    public Mat iconOArmy;
    public String nameTarget;
    public String nameTargetOrig;
    public Actor actor;
    public boolean isBaseActorWing = false;
    public Wing wing = null;

    private boolean visibleForPlayerArmy = false;

    private Mission mission = null;

    public TargetPoint()
    {
      this.mission = Main.cur().mission;
    }

    public boolean isGroundUnit()
    {
      return ZutiPadObject.isGroundUnit(this.actor);
    }

    public boolean getIsAlive()
    {
      if (this.actor == null) {
        return false;
      }
      if ((this.actor instanceof RocketryRocket)) {
        return !((RocketryRocket)this.actor).isDamaged();
      }
      return !this.actor.getDiedFlag();
    }

    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof TargetPoint)) {
        return false;
      }
      TargetPoint localTargetPoint = (TargetPoint)paramObject;

      if (this.actor != null)
      {
        if (this.actor.equals(localTargetPoint.actor)) {
          return true;
        }

      }
      else if (this.nameTargetOrig.equals(localTargetPoint.nameTargetOrig)) {
        return true;
      }

      return false;
    }

    public int hashCode()
    {
      if (this.actor != null) {
        return this.actor.hashCode();
      }
      return this.nameTargetOrig.hashCode();
    }

    public void refreshPosition()
    {
      this.mission.getClass(); if (!this.mission.zutiRadar_PlayerSideHasRadars) {
        return;
      }

      if ((this.actor != null) && (this.actor.pos != null))
      {
        Point3d localPoint3d = this.actor.pos.getAbsPoint();

        this.x = (float)localPoint3d.x;
        this.y = (float)localPoint3d.y;
        this.z = (float)localPoint3d.z;
      }
    }

    public boolean isVisibleForPlayerArmy()
    {
      return this.visibleForPlayerArmy;
    }

    public void setVisibleForPlayerArmy(boolean paramBoolean)
    {
      this.visibleForPlayerArmy = paramBoolean;
    }
  }

  private static class PathPoint
  {
    public int type;
    public float x;
    public float y;

    private PathPoint()
    {
    }

    PathPoint(GUIBriefing.1 param1)
    {
      this();
    }
  }
}