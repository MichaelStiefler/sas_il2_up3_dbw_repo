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
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
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
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class AirportCarrier extends Airport
{
  public static final double cellSize = 1.0D;
  private BigshipGeneric ship;
  private Loc[] runway;
  private static float[] x = { -100.0F, -20.0F, -10.0F, 1000.0F, 3000.0F, 4000.0F, 3000.0F, 0.0F, 0.0F };
  private static float[] y = { 0.0F, 0.0F, 0.0F, 0.0F, -500.0F, -1500.0F, -3000.0F, -3000.0F, -3000.0F };
  private static float[] z = { -4.0F, 5.0F, 5.0F, 150.0F, 450.0F, 500.0F, 500.0F, 500.0F, 500.0F };
  private static float[] v = { 0.0F, 80.0F, 100.0F, 180.0F, 250.0F, 270.0F, 280.0F, 300.0F, 300.0F };

  private Loc tmpLoc = new Loc();
  private Point3d tmpP3d = new Point3d();
  private Point3f tmpP3f = new Point3f();
  private Orient tmpOr = new Orient();

  private int curPlaneShift = 0;
  private int oldTickCounter = 0;
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
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this, new Loc());
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(paramBigshipGeneric, null, false);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
    this.runway = paramArrayOfLoc;
    if (Mission.isDogfight()) {
      Point_Stay[][] arrayOfPoint_Stay1 = getStayPlaces();
      Point_Stay[][] arrayOfPoint_Stay2 = World.cur().airdrome.stay;
      Point_Stay[][] arrayOfPoint_Stay; = new Point_Stay[arrayOfPoint_Stay2.length + arrayOfPoint_Stay1.length][];
      int i = 0;
      for (int j = 0; j < arrayOfPoint_Stay2.length; j++)
        arrayOfPoint_Stay;[(i++)] = arrayOfPoint_Stay2[j];
      for (int k = 0; k < arrayOfPoint_Stay1.length; k++)
        arrayOfPoint_Stay;[(i++)] = arrayOfPoint_Stay1[k];
      World.cur().airdrome.stay = arrayOfPoint_Stay;;
    }
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
    float f = paramFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.massEmpty * 0.0003333F;
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
    paramFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way = localWay;
    return true;
  }

  public void rebuildLandWay(FlightModel paramFlightModel)
  {
    if (!this.ship.isAlive()) {
      paramFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setLanding(false);
      return;
    }
    this.tmpLoc.set(this.runway[1]);
    this.tmpLoc.add(this.ship.initLoc);
    float f = paramFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.massEmpty * 0.0003333F;
    if (f > 1.0F) f = 1.0F;
    if (f < 0.4F) f = 0.4F;

    for (int i = 0; i < x.length; i++) {
      WayPoint localWayPoint = paramFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.get(i);
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
    int i = paramFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur();
    paramFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.last();
    if (paramFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 2) {
      this.ship.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.tmpP3d);
      paramFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().set(this.tmpP3d);
    }
    paramFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(i);
  }

  public double ShiftFromLine(FlightModel paramFlightModel) {
    this.tmpLoc.set(paramFlightModel.Loc);
    this.r.set(this.runway[0]);
    this.r.add(this.ship.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
    this.tmpLoc.sub(this.r);
    return this.tmpLoc.getY();
  }

  public boolean nearestRunway(Point3d paramPoint3d, Loc paramLoc)
  {
    paramLoc.add(this.runway[1], this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
    return true;
  }

  public int landingFeedback(Point3d paramPoint3d, Aircraft paramAircraft) {
    this.tmpLoc.set(this.runway[1]);
    this.tmpLoc.add(this.ship.initLoc);
    Aircraft localAircraft = War.getNearestFriendAtPoint(this.tmpLoc.getPoint(), paramAircraft, 50.0F);
    if ((localAircraft != null) && (localAircraft != paramAircraft)) return 1;
    if (paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl > 0.0F) return 0;
    if (this.jdField_landingRequest_of_type_Int > 0) return 1;
    this.jdField_landingRequest_of_type_Int = 3000;
    return 0;
  }

  public void setTakeoffOld(Point3d paramPoint3d, Aircraft paramAircraft) {
    if (!Actor.isValid(paramAircraft)) return;
    this.r.set(this.runway[0]);
    this.r.add(this.ship.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
    if (Time.tickCounter() != this.oldTickCounter) {
      this.oldTickCounter = Time.tickCounter();
      this.curPlaneShift = 0;
    }
    this.curPlaneShift += 1;
    paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setStationedOnGround(false);
    paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setWasAirborne(true);

    this.tmpLoc.set(-(this.curPlaneShift * 200.0F), -(this.curPlaneShift * 100.0F), 300.0D, 0.0F, 0.0F, 0.0F);
    this.tmpLoc.add(this.r);
    paramAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.tmpLoc);
    paramAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.tmpP3d, this.tmpOr);
    startSpeed.set(100.0D, 0.0D, 0.0D);
    this.tmpOr.transform(startSpeed);
    paramAircraft.setSpeed(startSpeed);
    paramAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
    if ((paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver)) ((Maneuver)paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).direction = paramAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient().getAzimut();

    paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport = this;

    if (paramAircraft == World.getPlayerAircraft()) {
      paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setCurControlAll(true);
      paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setEngineRunning();
      paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setPowerControl(0.75F);
    }
  }

  public double speedLen()
  {
    this.ship.getSpeed(shipSpeed);
    return shipSpeed.length();
  }

  public void setTakeoff(Point3d paramPoint3d, Aircraft[] paramArrayOfAircraft) {
    CellAirField localCellAirField = this.ship.getCellTO();
    if (localCellAirField == null) {
      for (i = 0; i < paramArrayOfAircraft.length; i++) setTakeoffOld(paramPoint3d, paramArrayOfAircraft[i]);
      return;
    }
    if (Time.tickCounter() != this.oldTickCounter) {
      this.oldTickCounter = Time.tickCounter();
      this.curPlaneShift = 0;
      localCellAirField.freeCells();
    }
    this.ship.getSpeed(shipSpeed);
    Object localObject;
    for (int i = 0; i < paramArrayOfAircraft.length; i++) {
      if (!Actor.isValid(paramArrayOfAircraft[i]))
        continue;
      localObject = paramArrayOfAircraft[i].getCellAirPlane();
      if (localCellAirField.findPlaceForAirPlane((CellAirPlane)localObject)) {
        localCellAirField.placeAirPlane((CellAirPlane)localObject, localCellAirField.resX(), localCellAirField.resY());
        double d1 = -localCellAirField.leftUpperCorner().jdField_x_of_type_Double - localCellAirField.resX() * localCellAirField.getCellSize() - ((CellAirPlane)localObject).ofsX;
        double d2 = localCellAirField.leftUpperCorner().jdField_y_of_type_Double - localCellAirField.resY() * localCellAirField.getCellSize() - ((CellAirPlane)localObject).ofsY;
        double d3 = this.runway[0].getZ();
        this.tmpLoc.set(d2, d1, d3 + paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.H, this.runway[0].getAzimut(), this.runway[0].getTangage(), this.runway[0].getKren());
        this.tmpLoc.add(this.ship.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
        Point3d localPoint3d = this.tmpLoc.getPoint();
        Orient localOrient = this.tmpLoc.getOrient();
        localOrient.increment(0.0F, paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch, 0.0F);
        paramArrayOfAircraft[i].setOnGround(localPoint3d, localOrient, shipSpeed);
        if ((paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver)) ((Maneuver)paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).direction = paramArrayOfAircraft[i].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient().getAzimut();

        paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport = this;

        paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoe = true;
        paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turnOffCollisions = true;
        paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoeLoc.set(paramArrayOfAircraft[i].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
        paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoeLoc.sub(this.ship.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
        paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoeLastCarrier = this.ship;
        paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.bFlatTopGearCheck = true;
        paramArrayOfAircraft[i].makeMirrorCarrierRelPos();
        if (this.curPlaneShift++ <= 1)
          continue;
        if (paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasWingControl) {
          paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.wingControl = 1.0F;
          paramArrayOfAircraft[i].jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.forceWing(1.0F);
        }
      }
      else {
        setTakeoffOld(paramPoint3d, paramArrayOfAircraft[i]);
      }
    }

    if ((Actor.isValid(paramArrayOfAircraft[0])) && ((paramArrayOfAircraft[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver))) {
      localObject = (Maneuver)paramArrayOfAircraft[0].jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      if ((((Maneuver)localObject).Group != null) && (((Maneuver)localObject).Group.w != null)) ((Maneuver)localObject).Group.w.takeoffAirport = this; 
    }
  }

  public void getTakeoff(Aircraft paramAircraft, Loc paramLoc)
  {
    this.tmpLoc.sub(paramLoc, this.ship.initLoc);
    this.tmpLoc.set(this.tmpLoc.getPoint().jdField_x_of_type_Double, this.tmpLoc.getPoint().jdField_y_of_type_Double, this.runway[0].getZ() + paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.H, this.runway[0].getAzimut(), this.runway[0].getTangage(), this.runway[0].getKren());
    this.tmpLoc.add(this.ship.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
    paramLoc.set(this.tmpLoc);
    Point3d localPoint3d = paramLoc.getPoint();
    Orient localOrient = paramLoc.getOrient();
    localOrient.increment(0.0F, paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch, 0.0F);
    if ((paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver)) ((Maneuver)paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).direction = paramLoc.getAzimut();

    paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport = this;
    paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoe = true;
    paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turnOffCollisions = true;
    paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoeLoc.set(paramLoc);
    paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoeLoc.sub(this.ship.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
    paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.brakeShoeLastCarrier = this.ship;
  }

  public float height() {
    return (float)(this.ship.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().getZ() + this.runway[0].getZ());
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
    Point_Stay[][] arrayOfPoint_Stay = null;
    Class localClass = this.ship.getClass();
    arrayOfPoint_Stay = (Point_Stay[][])Property.value(localClass, "StayPlaces", null);
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

        double d1 = -localCellAirField.leftUpperCorner().jdField_x_of_type_Double - localCellAirField.resX() * localCellAirField.getCellSize() - ((CellAirPlane)localObject1).ofsX;

        double d3 = localCellAirField.leftUpperCorner().jdField_y_of_type_Double - localCellAirField.resY() * localCellAirField.getCellSize() - ((CellAirPlane)localObject1).ofsY;

        ((ArrayList)localObject2).add(new Point2d(d3, d1));
      }

      int j = ((ArrayList)localObject2).size();
      if (j > 0) {
        arrayOfPoint_Stay = new Point_Stay[j][1];
        for (int k = 0; k < j; k++) {
          Point2d localPoint2d = (Point2d)((ArrayList)localObject2).get(k);
          arrayOfPoint_Stay[k][0] = new Point_Stay((float)localPoint2d.jdField_x_of_type_Double, (float)localPoint2d.jdField_y_of_type_Double);
        }
        Property.set(localClass, "StayPlaces", arrayOfPoint_Stay);
      }
    }
    if (arrayOfPoint_Stay == null) {
      return null;
    }
    Object localObject1 = new Point_Stay[arrayOfPoint_Stay.length][1];
    for (int i = 0; i < arrayOfPoint_Stay.length; i++) {
      localObject2 = arrayOfPoint_Stay[i][0];
      double d2 = ((Point_Stay)localObject2).x;
      double d4 = ((Point_Stay)localObject2).y;
      double d5 = this.runway[0].getZ();
      this.tmpLoc.set(d2, d4, d5, this.runway[0].getAzimut(), this.runway[0].getTangage(), this.runway[0].getKren());
      this.tmpLoc.add(this.ship.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
      Point3d localPoint3d = this.tmpLoc.getPoint();
      localObject1[i][0] = new Point_Stay((float)localPoint3d.jdField_x_of_type_Double, (float)localPoint3d.jdField_y_of_type_Double);
    }
    return (Point_Stay)(Point_Stay)localObject1;
  }
}