package com.maddox.il2.ai;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.CellAirField;
import com.maddox.il2.ai.air.CellAirPlane;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.game.Mission;
import com.maddox.il2.gui.GUINetClientDBrief;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.NetAircraft.AircraftNet;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class AirportCarrier extends Airport
{
  public static final double cellSize = 1.0D;
  private BigshipGeneric ship;
  private Loc[] runway;
  public BornPlace bornPlace2Move;
  public int bornPlaceArmyBk;
  private List lastCarrierUsers = new ArrayList();

  private GUINetClientDBrief ui = null;
  private Loc clientLoc = null;

  private static final Loc invalidLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);

  private Aircraft lastAddedAC = null;
  private CellAirPlane lastAddedCells = null;

  private int rnd1 = World.Rnd().nextInt(0, 30);
  private int rnd2 = World.Rnd().nextInt(40, 60);

  private boolean skipCheck = false;

  private static float[] x = { -100.0F, -20.0F, -10.0F, 1000.0F, 3000.0F, 4000.0F, 3000.0F, 0.0F, 0.0F };
  private static float[] y = { 0.0F, 0.0F, 0.0F, 0.0F, -500.0F, -1500.0F, -3000.0F, -3000.0F, -3000.0F };
  private static float[] z = { -4.0F, 5.0F, 5.0F, 150.0F, 450.0F, 500.0F, 500.0F, 500.0F, 500.0F };
  private static float[] v = { 0.0F, 80.0F, 100.0F, 180.0F, 250.0F, 270.0F, 280.0F, 300.0F, 300.0F };

  private Loc tmpLoc = new Loc();
  private Point3d tmpP3d = new Point3d();
  private Point3f tmpP3f = new Point3f();
  private Orient tmpOr = new Orient();

  public int curPlaneShift = 0;
  private int oldTickCounter = 0;
  private int oldIdleTickCounter = 0;
  private Loc r = new Loc();
  private static Vector3d startSpeed = new Vector3d();
  private static Vector3d shipSpeed = new Vector3d();

  private static Class _clsBigArrestorPlane = null;
  private static CellAirPlane _cellBigArrestorPlane = null;
  private static HashMap _clsMapArrestorPlane = new HashMap();

  public BigshipGeneric ship()
  {
    return this.ship;
  }

  public AirportCarrier(BigshipGeneric paramBigshipGeneric, Loc[] paramArrayOfLoc) {
    this.ship = paramBigshipGeneric;
    this.pos = new ActorPosMove(this, new Loc());
    this.pos.setBase(paramBigshipGeneric, null, false);
    this.pos.reset();
    this.runway = paramArrayOfLoc;
    if (Mission.isDogfight()) {
      Point_Stay[][] arrayOfPoint_Stay1 = getStayPlaces();
      Point_Stay[][] arrayOfPoint_Stay2 = World.cur().airdrome.stay;
      Point_Stay[][] arrayOfPoint_Stay; = new Point_Stay[arrayOfPoint_Stay2.length + arrayOfPoint_Stay1.length][];
      int i = 0;
      for (int j = 0; j < arrayOfPoint_Stay2.length; j++)
        arrayOfPoint_Stay;[(i++)] = arrayOfPoint_Stay2[j];
      for (j = 0; j < arrayOfPoint_Stay1.length; j++)
        arrayOfPoint_Stay;[(i++)] = arrayOfPoint_Stay1[j];
      World.cur().airdrome.stay = arrayOfPoint_Stay;;
    }
    startDeckOperations();
  }

  public void disableBornPlace()
  {
    if (this.bornPlace2Move != null)
      this.bornPlace2Move.army = -2;
  }

  public void enableBornPlace() {
    this.bornPlace2Move.army = this.bornPlaceArmyBk;
  }

  public boolean isAlive() {
    return Actor.isAlive(this.ship);
  }

  public int getArmy() {
    if (Actor.isAlive(this.ship)) {
      return this.ship.getArmy();
    }
    return super.getArmy();
  }

  public boolean landWay(FlightModel paramFlightModel)
  {
    Way localWay = new Way();

    this.tmpLoc.set(this.runway[1]);
    this.tmpLoc.add(this.ship.initLoc);
    float f = paramFlightModel.M.massEmpty * 0.0003333F;
    if (f > 1.0F) f = 1.0F;
    if (f < 0.4F) f = 0.4F;

    for (int i = x.length - 1; i >= 0; i--) {
      WayPoint localWayPoint = new WayPoint();
      this.tmpP3d.set(x[i] * f, y[i] * f, z[i] * f);
      localWayPoint.set(Math.min(v[i] * 0.278F, paramFlightModel.Vmax * 0.6F));
      localWayPoint.Action = 2;
      localWayPoint.sTarget = this.ship.name();
      this.tmpLoc.transform(this.tmpP3d);
      this.tmpP3f.set(this.tmpP3d);
      localWayPoint.set(this.tmpP3f);
      localWay.add(localWayPoint);
    }
    localWay.setLanding(true);
    paramFlightModel.AP.way = localWay;
    return true;
  }

  public void rebuildLandWay(FlightModel paramFlightModel)
  {
    if (!this.ship.isAlive()) {
      paramFlightModel.AP.way.setLanding(false);
      return;
    }
    this.tmpLoc.set(this.runway[1]);
    this.tmpLoc.add(this.ship.initLoc);
    float f = paramFlightModel.M.massEmpty * 0.0003333F;
    if (f > 1.0F) f = 1.0F;
    if (f < 0.4F) f = 0.4F;

    for (int i = 0; i < x.length; i++) {
      WayPoint localWayPoint = paramFlightModel.AP.way.get(i);
      this.tmpP3d.set(x[(x.length - 1 - i)] * f, y[(x.length - 1 - i)] * f, z[(x.length - 1 - i)] * f);
      this.tmpLoc.transform(this.tmpP3d);
      this.tmpP3f.set(this.tmpP3d);
      localWayPoint.set(this.tmpP3f);
    }
  }

  public void rebuildLastPoint(FlightModel paramFlightModel) {
    if (!Actor.isAlive(this.ship)) {
      return;
    }
    int i = paramFlightModel.AP.way.Cur();
    paramFlightModel.AP.way.last();
    if (paramFlightModel.AP.way.curr().Action == 2) {
      this.ship.pos.getAbs(this.tmpP3d);
      paramFlightModel.AP.way.curr().set(this.tmpP3d);
    }
    paramFlightModel.AP.way.setCur(i);
  }

  public double ShiftFromLine(FlightModel paramFlightModel) {
    this.tmpLoc.set(paramFlightModel.Loc);
    this.r.set(this.runway[0]);
    this.r.add(this.ship.pos.getAbs());
    this.tmpLoc.sub(this.r);
    return this.tmpLoc.getY();
  }

  public boolean nearestRunway(Point3d paramPoint3d, Loc paramLoc)
  {
    paramLoc.add(this.runway[1], this.pos.getAbs());
    return true;
  }

  public int landingFeedback(Point3d paramPoint3d, Aircraft paramAircraft) {
    this.tmpLoc.set(this.runway[1]);
    this.tmpLoc.add(this.ship.initLoc);
    Aircraft localAircraft = War.getNearestFriendAtPoint(this.tmpLoc.getPoint(), paramAircraft, 50.0F);
    if ((localAircraft != null) && (localAircraft != paramAircraft)) return 1;
    if (paramAircraft.FM.CT.GearControl > 0.0F) return 0;
    if (this.landingRequest > 0) return 1;
    this.landingRequest = 3000;
    return 0;
  }

  public void setTakeoffOld(Point3d paramPoint3d, Aircraft paramAircraft) {
    if (!Actor.isValid(paramAircraft)) return;
    this.r.set(this.runway[0]);
    this.r.add(this.ship.pos.getAbs());

    if (!Mission.isDogfight())
    {
      if (Time.tickCounter() != this.oldTickCounter) {
        this.oldTickCounter = Time.tickCounter();
        this.curPlaneShift = 0;
      }
    }
    this.curPlaneShift += 1;
    paramAircraft.FM.setStationedOnGround(false);
    paramAircraft.FM.setWasAirborne(true);

    this.tmpLoc.set(-(this.curPlaneShift * 200.0F), -(this.curPlaneShift * 100.0F), 300.0D, 0.0F, 0.0F, 0.0F);
    this.tmpLoc.add(this.r);
    paramAircraft.pos.setAbs(this.tmpLoc);
    paramAircraft.pos.getAbs(this.tmpP3d, this.tmpOr);
    startSpeed.set(100.0D, 0.0D, 0.0D);
    this.tmpOr.transform(startSpeed);
    paramAircraft.setSpeed(startSpeed);
    paramAircraft.pos.reset();
    if ((paramAircraft.FM instanceof Maneuver)) ((Maneuver)paramAircraft.FM).direction = paramAircraft.pos.getAbsOrient().getAzimut();

    paramAircraft.FM.AP.way.takeoffAirport = this;

    if (paramAircraft == World.getPlayerAircraft()) {
      paramAircraft.FM.EI.setCurControlAll(true);
      paramAircraft.FM.EI.setEngineRunning();
      paramAircraft.FM.CT.setPowerControl(0.75F);
    }
  }

  public double speedLen()
  {
    this.ship.getSpeed(shipSpeed);
    return shipSpeed.length();
  }

  private void checkIsDeckClear()
  {
    if (this.skipCheck) {
      return;
    }
    if (this.lastAddedAC != null)
    {
      if (this.lastAddedAC.isDestroyed())
      {
        CellAirField localCellAirField = this.ship.getCellTO();
        boolean bool = localCellAirField.removeAirPlane(this.lastAddedCells);
        if (bool)
        {
          this.curPlaneShift -= 1;
        }
        this.lastAddedAC = null;
        this.lastAddedCells = null;
      }
    }

    int i = 1;

    for (int j = this.lastCarrierUsers.size() - 1; j >= 0; j--)
    {
      Aircraft localAircraft = (Aircraft)this.lastCarrierUsers.get(j);
      if ((localAircraft != null) && (!localAircraft.isDestroyed()) && (localAircraft.isAlive()) && ((localAircraft.FM.Gears.isUnderDeck()) || (NetAircraft.isOnCarrierDeck(this, localAircraft.pos.getAbs()))))
      {
        i = 0;
      }
      else
      {
        this.lastCarrierUsers.remove(localAircraft);
      }
    }

    if (i != 0)
    {
      this.lastCarrierUsers.clear();
      deckCleared();
    }
  }

  private void checkPlaneIdle()
  {
    for (int i = this.lastCarrierUsers.size() - 1; i >= 0; i--)
    {
      Aircraft localAircraft1 = (Aircraft)this.lastCarrierUsers.get(i);

      if ((localAircraft1 == null) || (localAircraft1.isDestroyed()) || ((!localAircraft1.FM.Gears.isUnderDeck()) && (!NetAircraft.isOnCarrierDeck(this, localAircraft1.pos.getAbs()))))
        continue;
      localAircraft1.idleTimeOnCarrier += 1;
      if (localAircraft1.idleTimeOnCarrier < 6)
        continue;
      if ((localAircraft1.isNetPlayer()) && (!localAircraft1.isNetMaster()))
      {
        Aircraft localAircraft2 = localAircraft1;
        NetUser localNetUser = ((NetAircraft.AircraftNet)localAircraft2.net).netUser;
        localNetUser.kick(localNetUser);
      }
      else
      {
        if (localAircraft1.FM.AP.way.size() == 1)
          continue;
        localAircraft1.net.destroy();
        localAircraft1.destroy();
        this.lastCarrierUsers.remove(localAircraft1);
      }
    }
  }

  public Loc setClientTakeOff(Point3d paramPoint3d, Aircraft paramAircraft)
  {
    Loc localLoc = null;
    if (this.clientLoc != null)
      localLoc = new Loc(this.clientLoc);
    this.clientLoc = null;

    if ((localLoc == null) || (!isLocValid(localLoc)))
    {
      setTakeoffOld(paramPoint3d, paramAircraft);
      localObject = paramAircraft.pos.getAbs();
      double d1 = World.Rnd().nextDouble(400.0D, 800.0D);
      double d2 = World.Rnd().nextDouble(400.0D, 800.0D);
      if (World.Rnd().nextFloat() < 0.5F)
        d1 *= -1.0D;
      if (World.Rnd().nextFloat() < 0.5F)
        d2 *= -1.0D;
      Point3d localPoint3d = new Point3d(d1, d2, 0.0D);
      ((Loc)localObject).add(localPoint3d);
      paramAircraft.pos.setAbs((Loc)localObject);
      return localObject;
    }

    localLoc.add(this.ship.pos.getAbs());
    Object localObject = localLoc.getPoint();
    Orient localOrient = localLoc.getOrient();
    localOrient.increment(0.0F, paramAircraft.FM.Gears.Pitch, 0.0F);
    this.ship.getSpeed(shipSpeed);
    paramAircraft.setOnGround((Point3d)localObject, localOrient, shipSpeed);
    if ((paramAircraft.FM instanceof Maneuver)) ((Maneuver)paramAircraft.FM).direction = paramAircraft.pos.getAbsOrient().getAzimut();
    paramAircraft.FM.AP.way.takeoffAirport = this;

    paramAircraft.FM.brakeShoe = true;
    paramAircraft.FM.turnOffCollisions = true;
    paramAircraft.FM.brakeShoeLoc.set(paramAircraft.pos.getAbs());
    paramAircraft.FM.brakeShoeLoc.sub(this.ship.pos.getAbs());
    paramAircraft.FM.brakeShoeLastCarrier = this.ship;
    paramAircraft.FM.Gears.bFlatTopGearCheck = true;
    paramAircraft.makeMirrorCarrierRelPos();

    if (paramAircraft.FM.CT.bHasWingControl)
    {
      paramAircraft.FM.CT.wingControl = 1.0F;
      paramAircraft.FM.CT.forceWing(1.0F);
    }

    return (Loc)localLoc;
  }

  private Point3d reservePlaceForPlane(CellAirPlane paramCellAirPlane, Aircraft paramAircraft)
  {
    CellAirField localCellAirField = this.ship.getCellTO();
    if (localCellAirField.findPlaceForAirPlane(paramCellAirPlane)) {
      localCellAirField.placeAirPlane(paramCellAirPlane, localCellAirField.resX(), localCellAirField.resY());
      double d1 = -localCellAirField.leftUpperCorner().x - localCellAirField.resX() * localCellAirField.getCellSize() - paramCellAirPlane.ofsX;
      double d2 = localCellAirField.leftUpperCorner().y - localCellAirField.resY() * localCellAirField.getCellSize() - paramCellAirPlane.ofsY;
      double d3 = this.runway[0].getZ();
      this.curPlaneShift += 1;
      if ((Mission.isDogfight()) && (this.ship.net.isMaster()))
      {
        if (paramAircraft != null)
        {
          this.lastCarrierUsers.add(paramAircraft);
          this.lastAddedAC = paramAircraft;
        }
        this.lastAddedCells = paramCellAirPlane;
      }
      return new Point3d(d2, d1, d3);
    }
    return null;
  }

  public void setTakeoff(Point3d paramPoint3d, Aircraft[] paramArrayOfAircraft) {
    CellAirField localCellAirField = this.ship.getCellTO();
    if (localCellAirField == null) {
      for (i = 0; i < paramArrayOfAircraft.length; i++) setTakeoffOld(paramPoint3d, paramArrayOfAircraft[i]);
      return;
    }

    if (!Mission.isDogfight())
    {
      if (Time.tickCounter() != this.oldTickCounter) {
        this.oldTickCounter = Time.tickCounter();
        this.curPlaneShift = 0;
        localCellAirField.freeCells();
      }
    }
    this.ship.getSpeed(shipSpeed);
    for (int i = 0; i < paramArrayOfAircraft.length; i++) {
      if (!Actor.isValid(paramArrayOfAircraft[i]))
      {
        continue;
      }
      CellAirPlane localCellAirPlane = paramArrayOfAircraft[i].getCellAirPlane();

      Point3d localPoint3d1 = reservePlaceForPlane(localCellAirPlane, paramArrayOfAircraft[i]);

      if (localPoint3d1 != null)
      {
        double d1 = localPoint3d1.x;
        double d2 = localPoint3d1.y;
        double d3 = localPoint3d1.z;

        this.tmpLoc.set(d1, d2, d3 + paramArrayOfAircraft[i].FM.Gears.H, this.runway[0].getAzimut(), this.runway[0].getTangage(), this.runway[0].getKren());
        this.tmpLoc.add(this.ship.pos.getAbs());
        Point3d localPoint3d2 = this.tmpLoc.getPoint();
        Orient localOrient = this.tmpLoc.getOrient();
        localOrient.increment(0.0F, paramArrayOfAircraft[i].FM.Gears.Pitch, 0.0F);
        paramArrayOfAircraft[i].setOnGround(localPoint3d2, localOrient, shipSpeed);
        if ((paramArrayOfAircraft[i].FM instanceof Maneuver)) ((Maneuver)paramArrayOfAircraft[i].FM).direction = paramArrayOfAircraft[i].pos.getAbsOrient().getAzimut();

        paramArrayOfAircraft[i].FM.AP.way.takeoffAirport = this;

        paramArrayOfAircraft[i].FM.brakeShoe = true;
        paramArrayOfAircraft[i].FM.turnOffCollisions = true;
        paramArrayOfAircraft[i].FM.brakeShoeLoc.set(paramArrayOfAircraft[i].pos.getAbs());
        paramArrayOfAircraft[i].FM.brakeShoeLoc.sub(this.ship.pos.getAbs());
        paramArrayOfAircraft[i].FM.brakeShoeLastCarrier = this.ship;
        paramArrayOfAircraft[i].FM.Gears.bFlatTopGearCheck = true;
        paramArrayOfAircraft[i].makeMirrorCarrierRelPos();
        if (this.curPlaneShift > 1)
        {
          if (paramArrayOfAircraft[i].FM.CT.bHasWingControl) {
            paramArrayOfAircraft[i].FM.CT.wingControl = 1.0F;
            paramArrayOfAircraft[i].FM.CT.forceWing(1.0F);
          }
        }
      } else {
        setTakeoffOld(paramPoint3d, paramArrayOfAircraft[i]);
      }
    }

    if ((Actor.isValid(paramArrayOfAircraft[0])) && ((paramArrayOfAircraft[0].FM instanceof Maneuver))) {
      Maneuver localManeuver = (Maneuver)paramArrayOfAircraft[0].FM;
      if ((localManeuver.Group != null) && (localManeuver.Group.w != null)) localManeuver.Group.w.takeoffAirport = this; 
    }
  }

  public void getTakeoff(Aircraft paramAircraft, Loc paramLoc)
  {
    this.tmpLoc.sub(paramLoc, this.ship.initLoc);
    this.tmpLoc.set(this.tmpLoc.getPoint().x, this.tmpLoc.getPoint().y, this.runway[0].getZ() + paramAircraft.FM.Gears.H, this.runway[0].getAzimut(), this.runway[0].getTangage(), this.runway[0].getKren());
    this.tmpLoc.add(this.ship.pos.getAbs());
    paramLoc.set(this.tmpLoc);
    Point3d localPoint3d = paramLoc.getPoint();
    Orient localOrient = paramLoc.getOrient();
    localOrient.increment(0.0F, paramAircraft.FM.Gears.Pitch, 0.0F);
    if ((paramAircraft.FM instanceof Maneuver)) ((Maneuver)paramAircraft.FM).direction = paramLoc.getAzimut();

    paramAircraft.FM.AP.way.takeoffAirport = this;
    paramAircraft.FM.brakeShoe = true;
    paramAircraft.FM.turnOffCollisions = true;
    paramAircraft.FM.brakeShoeLoc.set(paramLoc);
    paramAircraft.FM.brakeShoeLoc.sub(this.ship.pos.getAbs());
    paramAircraft.FM.brakeShoeLastCarrier = this.ship;
  }

  public float height() {
    return (float)(this.ship.pos.getAbs().getZ() + this.runway[0].getZ());
  }

  public static boolean isPlaneContainsArrestor(Class paramClass)
  {
    clsBigArrestorPlane();
    return _clsMapArrestorPlane.containsKey(paramClass);
  }

  private static Class clsBigArrestorPlane() {
    if (_clsBigArrestorPlane != null) {
      return _clsBigArrestorPlane;
    }
    double d1 = 0.0D;
    SectFile localSectFile1 = new SectFile("com/maddox/il2/objects/air.ini", 0);
    int i = localSectFile1.sections();
    for (int j = 0; j < i; j++) {
      int k = localSectFile1.vars(j);
      for (int m = 0; m < k; m++) {
        String str1 = localSectFile1.value(j, m);
        StringTokenizer localStringTokenizer = new StringTokenizer(localSectFile1.value(j, m));
        if (localStringTokenizer.hasMoreTokens()) {
          String str2 = "com.maddox.il2.objects." + localStringTokenizer.nextToken();
          Class localClass = null;
          String str3 = null;
          try {
            localClass = Class.forName(str2);
            str3 = Property.stringValue(localClass, "FlightModel", null);
          } catch (Exception localException1) {
            System.out.println(localException1.getMessage());
            localException1.printStackTrace();
          }
          try {
            if (str3 != null) {
              SectFile localSectFile2 = FlightModelMain.sectFile(str3);
              if (localSectFile2.get("Controls", "CArrestorHook", 0) == 1) {
                _clsMapArrestorPlane.put(localClass, null);
                String str4 = Aircraft.getPropertyMesh(localClass, null);
                SectFile localSectFile3 = new SectFile(str4, 0);
                String str5 = localSectFile3.get("_ROOT_", "CollisionObject", (String)null);
                if (str5 != null) {
                  NumberTokenizer localNumberTokenizer = new NumberTokenizer(str5);
                  if (localNumberTokenizer.hasMoreTokens()) {
                    localNumberTokenizer.next();
                    if (localNumberTokenizer.hasMoreTokens()) {
                      double d2 = localNumberTokenizer.next(-1.0D);
                      if ((d2 > 0.0D) && 
                        (d1 < d2)) {
                        d1 = d2;
                        _clsBigArrestorPlane = localClass;
                      }
                    }
                  }
                }
              }
            }
          }
          catch (Exception localException2)
          {
            System.out.println(localException2.getMessage());
            localException2.printStackTrace();
          }
        }
      }
    }
    return _clsBigArrestorPlane;
  }
  private static CellAirPlane cellBigArrestorPlane() {
    if (_cellBigArrestorPlane != null)
      return _cellBigArrestorPlane;
    _cellBigArrestorPlane = Aircraft.getCellAirPlane(clsBigArrestorPlane());
    return _cellBigArrestorPlane;
  }
  private Point_Stay[][] getStayPlaces() {
    Point_Stay[][] arrayOfPoint_Stay = (Point_Stay[][])null;
    Class localClass = this.ship.getClass();
    arrayOfPoint_Stay = (Point_Stay[][])(Point_Stay[][])Property.value(localClass, "StayPlaces", null);
    Object localObject2;
    if (arrayOfPoint_Stay == null) {
      localObject1 = cellBigArrestorPlane();
      CellAirField localCellAirField = this.ship.getCellTO();
      localCellAirField = (CellAirField)localCellAirField.getClone();
      localObject2 = new ArrayList();
      while (true) {
        localObject1 = (CellAirPlane)((CellAirPlane)localObject1).getClone();
        if (!localCellAirField.findPlaceForAirPlane((CellAirPlane)localObject1)) break;
        localCellAirField.placeAirPlane((CellAirPlane)localObject1, localCellAirField.resX(), localCellAirField.resY());

        double d1 = -localCellAirField.leftUpperCorner().x - localCellAirField.resX() * localCellAirField.getCellSize() - ((CellAirPlane)localObject1).ofsX;

        double d3 = localCellAirField.leftUpperCorner().y - localCellAirField.resY() * localCellAirField.getCellSize() - ((CellAirPlane)localObject1).ofsY;

        ((ArrayList)localObject2).add(new Point2d(d3, d1));
      }

      int j = ((ArrayList)localObject2).size();
      if (j > 0) {
        arrayOfPoint_Stay = new Point_Stay[j][1];
        for (int k = 0; k < j; k++) {
          Point2d localPoint2d = (Point2d)((ArrayList)localObject2).get(k);
          arrayOfPoint_Stay[k][0] = new Point_Stay((float)localPoint2d.x, (float)localPoint2d.y);
        }
        Property.set(localClass, "StayPlaces", arrayOfPoint_Stay);
      }
    }
    if (arrayOfPoint_Stay == null) {
      return (Point_Stay[][])null;
    }
    Object localObject1 = new Point_Stay[arrayOfPoint_Stay.length][1];
    for (int i = 0; i < arrayOfPoint_Stay.length; i++) {
      localObject2 = arrayOfPoint_Stay[i][0];
      double d2 = ((Point_Stay)localObject2).x;
      double d4 = ((Point_Stay)localObject2).y;
      double d5 = this.runway[0].getZ();
      this.tmpLoc.set(d2, d4, d5, this.runway[0].getAzimut(), this.runway[0].getTangage(), this.runway[0].getKren());
      this.tmpLoc.add(this.ship.pos.getAbs());
      Point3d localPoint3d = this.tmpLoc.getPoint();
      localObject1[i][0] = new Point_Stay((float)localPoint3d.x, (float)localPoint3d.y);
    }
    return (Point_Stay)(Point_Stay)localObject1;
  }

  public void setCellUsed(Aircraft paramAircraft)
  {
    this.skipCheck = false;
    if (this.ship.net.isMaster())
    {
      this.lastCarrierUsers.add(paramAircraft);
      this.lastAddedAC = paramAircraft;
    }
    if ((paramAircraft.FM.CT.bHasWingControl) && (!paramAircraft.isNetPlayer()))
    {
      paramAircraft.FM.CT.wingControl = 0.0F;
    }
  }

  public Loc requestCell(Aircraft paramAircraft)
  {
    if (!ship().isAlive()) {
      return invalidLoc;
    }
    CellAirPlane localCellAirPlane = paramAircraft.getCellAirPlane();
    Point3d localPoint3d = reservePlaceForPlane(localCellAirPlane, null);
    if (localPoint3d != null)
    {
      this.skipCheck = true;
      double d1 = localPoint3d.x;
      double d2 = localPoint3d.y;
      double d3 = localPoint3d.z;
      Loc localLoc = new Loc();
      localLoc.set(d1, d2, d3 + paramAircraft.FM.Gears.H, this.runway[0].getAzimut(), this.runway[0].getTangage(), this.runway[0].getKren());
      return localLoc;
    }

    return invalidLoc;
  }

  private void deckCleared()
  {
    this.curPlaneShift = 0;
    CellAirField localCellAirField = this.ship.getCellTO();
    localCellAirField.freeCells();
  }

  public void setGuiCallback(GUINetClientDBrief paramGUINetClientDBrief)
  {
    this.ui = paramGUINetClientDBrief;
  }

  public void setClientLoc(Loc paramLoc)
  {
    this.clientLoc = paramLoc;
    boolean bool = isLocValid(paramLoc);
    if (this.ui != null)
    {
      this.ui.flyFromCarrier(bool);
    }
  }

  private boolean isLocValid(Loc paramLoc)
  {
    if (paramLoc == null) {
      return false;
    }

    return ((int)paramLoc.getX() != 0) || ((int)paramLoc.getY() != 0) || ((int)paramLoc.getZ() != 0) || ((int)paramLoc.getTangage() != 0) || ((int)paramLoc.getKren() != 0);
  }

  public void startDeckOperations()
  {
    if ((Mission.isDogfight()) && (this.ship.net.isMaster()))
      interpPut(new DeckUpdater(), "deck_operations", Time.current(), null);
  }

  class DeckUpdater extends Interpolate {
    DeckUpdater() {
    }

    public boolean tick() {
      if (AirportCarrier.this.ship().isAlive())
      {
        if (Time.tickCounter() > AirportCarrier.this.oldTickCounter + 150 + AirportCarrier.this.rnd1)
        {
          AirportCarrier.access$002(AirportCarrier.this, Time.tickCounter());
          AirportCarrier.this.checkIsDeckClear();
        }
        if (Time.tickCounter() > AirportCarrier.this.oldIdleTickCounter + 2000 + AirportCarrier.this.rnd2)
        {
          AirportCarrier.access$302(AirportCarrier.this, Time.tickCounter());
          AirportCarrier.this.checkPlaneIdle();
          AirportCarrier.access$602(AirportCarrier.this, false);
        }
      }
      return true;
    }
  }
}