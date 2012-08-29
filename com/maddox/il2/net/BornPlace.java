package com.maddox.il2.net;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiAircraft;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.LDRres;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BornPlace
{
  public Point2d place = new Point2d();
  public float r;
  public int army;
  public boolean bParachute = true;
  public ArrayList airNames;
  private static Vector3d v1 = new Vector3d();
  private static Point3d corn = new Point3d();
  private static Point3d corn1 = new Point3d();
  private static Point3d pship = new Point3d();

  static ClipFilter clipFilter = new ClipFilter();
  public int tmpForBrief;
  public double zutiOriginalX = 0.0D;
  public double zutiOriginalY = 0.0D;
  public boolean zutiStaticPositionOnly = false;

  public ArrayList zutiBpStayPoints = null;

  public boolean zutiAlreadyAssigned = false;

  public int zutiSpawnHeight = 1000;
  public int zutiSpawnSpeed = 200;
  public int zutiSpawnOrient = 0;

  public int zutiBpIndex = -1;

  public int zutiRadarHeight_MIN = 0;
  public int zutiRadarHeight_MAX = 5000;
  public int zutiRadarRange = 50;

  public int zutiMaxBasePilots = 0;

  public boolean zutiAirspawnIfCarrierFull = false;
  public boolean zutiAirspawnOnly = false;
  private int zutiNumberOfSpawnPlaces = 0;

  public ArrayList zutiAircrafts = null;

  public ArrayList zutiHomeBaseCountries = null;

  public boolean zutiEnablePlaneLimits = false;
  public boolean zutiDecreasingNumberOfPlanes = false;
  public boolean zutiIncludeStaticPlanes = false;

  public boolean zutiDisableSpawning = false;
  public boolean zutiEnableFriction = false;
  public double zutiFriction = 3.8D;

  public Loc getAircraftPlace(Aircraft paramAircraft, int paramInt)
  {
    Loc localLoc = null;
    if ((paramInt < 0) || (paramInt > World.cur().airdrome.stay.length)) {
      double d1 = World.land().HQ(this.place.x, this.place.y);
      localLoc = new Loc(this.place.x, this.place.y, d1, 0.0F, 0.0F, 0.0F);
    }
    else
    {
      Object localObject1;
      Object localObject2;
      if (paramInt >= World.cur().airdrome.stayHold.length) {
        localObject1 = World.cur().airdrome.stay[paramInt][0];
        localLoc = new Loc(((Point_Stay)localObject1).x, ((Point_Stay)localObject1).y, 0.0D, 0.0F, 0.0F, 0.0F);
        if ((!World.cur().diffCur.Takeoff_N_Landing) || (this.zutiAirspawnOnly))
        {
          localObject2 = new Point3d(200.0D, 200.0D, 0.0D);
          localLoc.add((Tuple3d)localObject2);
          return localLoc;
        }
        localObject2 = (AirportCarrier)Airport.nearest(localLoc.getPoint(), -1, 4);
        if (localObject2 != null)
        {
          corn.set(localLoc.getPoint());
          corn1.set(localLoc.getPoint());
          corn.z = Engine.cur.land.HQ(localLoc.getPoint().x, localLoc.getPoint().y);
          corn.z += 40.0D;
          Actor localActor = Engine.collideEnv().getLine(corn, corn1, false, clipFilter, pship);
          if (((AirportCarrier)localObject2).ship() != localActor) {
            localObject2 = null;
          }
        }
        if (localObject2 != null) {
          if (Mission.isDogfight())
          {
            if ((paramAircraft == World.getPlayerAircraft()) && (!Main.cur().netServerParams.isMaster()))
            {
              return ((AirportCarrier)localObject2).setClientTakeOff(localLoc.getPoint(), paramAircraft);
            }

            ((AirportCarrier)localObject2).setTakeoff(localLoc.getPoint(), new Aircraft[] { paramAircraft });
            return paramAircraft.pos.getAbs();
          }

          ((AirportCarrier)localObject2).getTakeoff(paramAircraft, localLoc);
        } else {
          localLoc = new Loc(((Point_Stay)localObject1).x, ((Point_Stay)localObject1).y, 1000.0D, 0.0F, 0.0F, 0.0F);
        }
      } else {
        localObject1 = World.cur().airdrome.stay;
        localObject2 = localObject1[paramInt][(localObject1[paramInt].length - 1)];
        World.land(); double d2 = Landscape.HQ(((Point_Stay)localObject2).x, ((Point_Stay)localObject2).y);
        localLoc = new Loc(((Point_Stay)localObject2).x, ((Point_Stay)localObject2).y, d2, 0.0F, 0.0F, 0.0F);
        int i = localObject1[paramInt].length - 2;
        if (i >= 0) {
          Point3d localPoint3d1 = new Point3d(((Point_Stay)localObject2).x, ((Point_Stay)localObject2).y, 0.0D);
          Point3d localPoint3d2 = new Point3d(localObject1[paramInt][i].x, localObject1[paramInt][i].y, 0.0D);
          Vector3d localVector3d = new Vector3d();
          localVector3d.sub(localPoint3d2, localPoint3d1);
          localVector3d.normalize();
          Orient localOrient = new Orient();
          localOrient.setAT0(localVector3d);
          if ((!World.cur().diffCur.Takeoff_N_Landing) || (this.zutiAirspawnOnly)) {
            localLoc.set(localOrient);
            return localLoc;
          }
          localLoc.getPoint().z += paramAircraft.FM.Gears.H;
          Engine.land().N(localLoc.getPoint().x, localLoc.getPoint().y, v1);
          localOrient.orient(v1);
          localOrient.increment(0.0F, paramAircraft.FM.Gears.Pitch, 0.0F);
          localLoc.set(localOrient);
        }
      }
    }
    return (Loc)(Loc)localLoc;
  }

  public BornPlace(double paramDouble1, double paramDouble2, int paramInt, float paramFloat)
  {
    this.place.set(paramDouble1, paramDouble2);
    this.army = paramInt;
    this.r = paramFloat;

    this.zutiOriginalX = this.place.x;
    this.zutiOriginalY = this.place.y;
  }

  public boolean zutiCanUserJoin()
  {
    return (this.zutiMaxBasePilots == 0) || ((this.zutiMaxBasePilots > 0) && (this.tmpForBrief < this.zutiMaxBasePilots));
  }

  public void zutiCountBornPlaceStayPoints()
  {
    this.zutiNumberOfSpawnPlaces = 0;

    if (World.cur().airdrome.stay == null) {
      return;
    }
    Point_Stay[][] arrayOfPoint_Stay = World.cur().airdrome.stay;

    double d1 = this.place.x;
    double d2 = this.place.y;

    double d3 = this.r * this.r;
    int i = arrayOfPoint_Stay.length;
    for (int j = 0; j < i; j++)
    {
      try
      {
        if (arrayOfPoint_Stay[j] != null)
        {
          Point_Stay localPoint_Stay = arrayOfPoint_Stay[j][(arrayOfPoint_Stay[j].length - 1)];
          double d4 = (localPoint_Stay.x - d1) * (localPoint_Stay.x - d1) + (localPoint_Stay.y - d2) * (localPoint_Stay.y - d2);
          if (d4 <= d3)
          {
            this.zutiNumberOfSpawnPlaces += 1;
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        System.out.println("Error when reading HomeBase born place nr. " + j + " (" + localException.toString() + ")");
      }
    }
  }

  public void zutiSetBornPlaceStayPointsNumber(int paramInt)
  {
    this.zutiNumberOfSpawnPlaces = paramInt;
  }

  public void zutiFillAirNames()
  {
    if ((this.zutiAircrafts == null) || (this.zutiAircrafts.size() < 1))
    {
      zutiCreateCompleteACList();
      return;
    }

    if (this.airNames == null)
      this.airNames = new ArrayList();
    this.airNames.clear();

    int i = this.zutiAircrafts.size();
    for (int j = 0; j < i; j++)
    {
      ZutiAircraft localZutiAircraft = (ZutiAircraft)this.zutiAircrafts.get(j);
      this.airNames.add(localZutiAircraft.getAcName());
    }
  }

  public ArrayList zutiGetAcLoadouts(String paramString)
  {
    if ((this.zutiAircrafts == null) || (this.zutiAircrafts.size() < 1)) {
      return new ArrayList();
    }
    int i = this.zutiAircrafts.size();
    for (int j = 0; j < i; j++)
    {
      ZutiAircraft localZutiAircraft = (ZutiAircraft)this.zutiAircrafts.get(j);
      if (localZutiAircraft.getAcName().equals(paramString)) {
        return localZutiAircraft.getSelectedWeaponI18NNames();
      }
    }
    return new ArrayList();
  }

  public void zutiReleaseAircraft(FlightModel paramFlightModel, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (this.zutiAircrafts == null) {
      return;
    }

    boolean bool = false;

    if (paramFlightModel != null) {
      bool = isLandedOnHomeBase(paramFlightModel, this);
    }

    int i = this.zutiAircrafts.size();
    for (int j = 0; j < i; j++)
    {
      ZutiAircraft localZutiAircraft1 = (ZutiAircraft)this.zutiAircrafts.get(j);
      if ((!localZutiAircraft1.getAcName().equals(paramString)) && (!localZutiAircraft1.getClassShortAcName().equals(paramString))) {
        continue;
      }
      if (this.zutiDecreasingNumberOfPlanes)
      {
        if ((!paramBoolean1) || (!paramBoolean3) || (!bool)) {
          localZutiAircraft1.decreasePlaneCount();
        }
      }
      if (paramBoolean2) break;
      localZutiAircraft1.decreaseInUse(); break;
    }

    if ((!bool) && (paramBoolean1) && (paramBoolean3) && (!paramBoolean2) && (this.zutiDecreasingNumberOfPlanes))
    {
      if (isLandedOnHomeBase(paramFlightModel, null))
      {
        BornPlace localBornPlace = getCurrentBornPlace(paramFlightModel.Loc.x, paramFlightModel.Loc.y);
        if (localBornPlace != null)
        {
          for (int k = 0; k < localBornPlace.zutiAircrafts.size(); k++)
          {
            ZutiAircraft localZutiAircraft2 = (ZutiAircraft)localBornPlace.zutiAircrafts.get(k);
            if ((!localZutiAircraft2.getAcName().equals(paramString)) && (!localZutiAircraft2.getClassShortAcName().equals(paramString)))
              continue;
            localZutiAircraft2.increasePlaneCount();
            break;
          }
        }
      }
    }
  }

  public boolean zutiIsAcAvailable(String paramString)
  {
    try
    {
      if (this.zutiAircrafts == null) {
        return false;
      }

      int i = this.zutiAircrafts.size();
      for (int j = 0; j < i; j++)
      {
        ZutiAircraft localZutiAircraft = (ZutiAircraft)this.zutiAircrafts.get(j);
        if (!localZutiAircraft.getAcName().equals(paramString)) {
          continue;
        }
        boolean bool = localZutiAircraft.isAvailable(this.zutiDecreasingNumberOfPlanes);
        if (bool) {
          localZutiAircraft.increaseInUse();
        }
        return bool;
      }
    }
    catch (Exception localException) {
      System.out.println("BornPlace error, ID_03: " + localException.toString());
    }return false;
  }

  public ArrayList zutiGetNotAvailablePlanesList()
  {
    ArrayList localArrayList = new ArrayList();

    if (this.zutiAircrafts == null) {
      return localArrayList;
    }
    for (int i = 0; i < this.zutiAircrafts.size(); i++)
    {
      ZutiAircraft localZutiAircraft = (ZutiAircraft)this.zutiAircrafts.get(i);
      if (!localZutiAircraft.isAvailable(this.zutiDecreasingNumberOfPlanes)) {
        localArrayList.add(localZutiAircraft.getAcName());
      }
    }
    return localArrayList;
  }

  public ArrayList zutiGetAvailablePlanesList()
  {
    ArrayList localArrayList = new ArrayList();

    if (this.zutiAircrafts == null) {
      return localArrayList;
    }
    for (int i = 0; i < this.zutiAircrafts.size(); i++)
    {
      ZutiAircraft localZutiAircraft = (ZutiAircraft)this.zutiAircrafts.get(i);
      if (localZutiAircraft.isAvailable(this.zutiDecreasingNumberOfPlanes)) {
        localArrayList.add(localZutiAircraft.getAcName());
      }
    }
    return localArrayList;
  }

  public void zutiActivatePlanes(ArrayList paramArrayList)
  {
    if (this.zutiAircrafts == null) {
      return;
    }

    this.airNames.clear();
    for (int i = 0; i < this.zutiAircrafts.size(); i++)
    {
      ZutiAircraft localZutiAircraft = (ZutiAircraft)this.zutiAircrafts.get(i);
      String str1 = localZutiAircraft.getAcName();
      int j = 1;

      for (int k = 0; k < paramArrayList.size(); k++)
      {
        String str2 = (String)paramArrayList.get(k);
        if (str1.equals(str2)) {
          j = 0;
        }
      }
      if (j != 0)
      {
        this.airNames.add(str1);
        localZutiAircraft.setMaxAllowed(1);
      }
      else {
        localZutiAircraft.setMaxAllowed(0);
      }
    }
  }

  private void zutiAddAllAircraft() {
    if (this.airNames == null) {
      this.airNames = new ArrayList();
    }
    this.airNames.clear();

    ArrayList localArrayList = Main.cur().airClasses;
    for (int i = 0; i < localArrayList.size(); i++)
    {
      Class localClass = (Class)localArrayList.get(i);
      if (!Property.containsValue(localClass, "cockpitClass"))
        continue;
      String str = Property.stringValue(localClass, "keyName");
      if (!this.airNames.contains(str))
        this.airNames.add(str);
    }
  }

  private void zutiCreateCompleteACList()
  {
    this.zutiAircrafts = new ArrayList();

    zutiAddAllAircraft();

    for (int i = 0; i < this.airNames.size(); i++)
    {
      String str = (String)this.airNames.get(i);
      ZutiAircraft localZutiAircraft = new ZutiAircraft();
      localZutiAircraft.setAcName(str);
      if (this.zutiDecreasingNumberOfPlanes)
        localZutiAircraft.setMaxAllowed(-1);
      else {
        localZutiAircraft.setMaxAllowed(0);
      }
      if (this.zutiEnablePlaneLimits)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add("Default");
        localZutiAircraft.setSelectedWeapons(localArrayList);
      }
      this.zutiAircrafts.add(localZutiAircraft);
    }
  }

  public void zutiLoadDefaultCountries()
  {
    if (this.zutiHomeBaseCountries == null) {
      this.zutiHomeBaseCountries = new ArrayList();
    }
    this.zutiHomeBaseCountries.clear();

    if (this.army == 1)
    {
      this.zutiHomeBaseCountries.clear();
      this.zutiHomeBaseCountries.add("None");
      this.zutiHomeBaseCountries.add("USSR");
      this.zutiHomeBaseCountries.add("RAF");
      this.zutiHomeBaseCountries.add("USAAF");
    }
    else if (this.army == 2)
    {
      this.zutiHomeBaseCountries.clear();
      this.zutiHomeBaseCountries.add("None");
      this.zutiHomeBaseCountries.add("Germany");
      this.zutiHomeBaseCountries.add("Italy");
      this.zutiHomeBaseCountries.add("IJA");
    }
    else
    {
      this.zutiHomeBaseCountries.clear();
      this.zutiHomeBaseCountries.add("None");
    }
  }

  public void zutiLoadAllCountries()
  {
    if (this.zutiHomeBaseCountries == null) {
      this.zutiHomeBaseCountries = new ArrayList();
    }
    this.zutiHomeBaseCountries.clear();

    ResourceBundle localResourceBundle = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());

    List localList = Regiment.getAll();
    int i = localList.size();
    for (int j = 0; j < i; j++)
    {
      Regiment localRegiment = (Regiment)localList.get(j);
      String str = localResourceBundle.getString(localRegiment.branch());
      if (!this.zutiHomeBaseCountries.contains(str))
        this.zutiHomeBaseCountries.add(str);
    }
  }

  public static BornPlace getCurrentBornPlace(double paramDouble1, double paramDouble2)
  {
    ArrayList localArrayList = World.cur().bornPlaces;
    if ((localArrayList == null) || (localArrayList.size() == 0)) {
      return null;
    }
    for (int i = 0; i < localArrayList.size(); i++)
    {
      BornPlace localBornPlace = (BornPlace)localArrayList.get(i);
      double d1 = paramDouble1 - localBornPlace.place.x;
      double d2 = paramDouble2 - localBornPlace.place.y;
      double d3 = Math.sqrt(d1 * d1 + d2 * d2);
      if (localBornPlace.r > d3)
        return localBornPlace;
    }
    return null;
  }

  public double getBornPlaceFriction(double paramDouble1, double paramDouble2)
  {
    double d1 = paramDouble1 - this.place.x;
    double d2 = paramDouble2 - this.place.y;
    double d3 = Math.sqrt(d1 * d1 + d2 * d2);
    if (this.r > d3)
    {
      if (this.zutiEnableFriction) {
        return this.zutiFriction;
      }
      return 3.799999952316284D;
    }

    return -1.0D;
  }

  public static boolean isLandedOnHomeBase(FlightModel paramFlightModel, BornPlace paramBornPlace)
  {
    if (paramBornPlace == null)
    {
      ArrayList localArrayList = World.cur().bornPlaces;
      if ((localArrayList == null) || (localArrayList.size() == 0)) {
        return false;
      }
      for (int i = 0; i < localArrayList.size(); i++)
      {
        BornPlace localBornPlace = (BornPlace)localArrayList.get(i);
        if (paramFlightModel.actor.getArmy() != localBornPlace.army)
          continue;
        double d3 = paramFlightModel.Loc.x - localBornPlace.place.x;
        double d5 = paramFlightModel.Loc.y - localBornPlace.place.y;
        double d6 = Math.sqrt(d3 * d3 + d5 * d5);
        if (localBornPlace.r > d6)
          return true;
      }
    }
    else
    {
      double d1 = paramFlightModel.Loc.x - paramBornPlace.place.x;
      double d2 = paramFlightModel.Loc.y - paramBornPlace.place.y;
      double d4 = Math.sqrt(d1 * d1 + d2 * d2);
      if (paramBornPlace.r > d4)
        return true;
    }
    return false;
  }

  static class ClipFilter
    implements ActorFilter
  {
    public boolean isUse(Actor paramActor, double paramDouble)
    {
      return paramActor instanceof BigshipGeneric;
    }
  }
}