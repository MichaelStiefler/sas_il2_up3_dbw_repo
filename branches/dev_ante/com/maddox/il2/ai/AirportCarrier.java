package com.maddox.il2.ai;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Tuple2d;
import com.maddox.JGP.Tuple2f;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.CellAirField;
import com.maddox.il2.ai.air.CellAirPlane;
import com.maddox.il2.ai.air.CellObject;
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
import com.maddox.il2.objects.air.A6M2_21;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.B5N;
import com.maddox.il2.objects.air.B6N;
import com.maddox.il2.objects.air.D3A;
import com.maddox.il2.objects.air.F4F;
import com.maddox.il2.objects.air.F4F3;
import com.maddox.il2.objects.air.F4U;
import com.maddox.il2.objects.air.F6F;
import com.maddox.il2.objects.air.F9F;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.SEAFIRE3;
import com.maddox.il2.objects.air.SEAFIRE3F;
import com.maddox.il2.objects.air.SeaFury;
import com.maddox.il2.objects.air.Swordfish;
import com.maddox.il2.objects.air.TBF;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.sounds.SndAircraft;
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
  private Loc tmpLoc;
  private Point3d tmpP3d;
  private Point3f tmpP3f;
  private Orient tmpOr;
  private int curPlaneShift;
  private int oldTickCounter;
  private Loc r;
  private static Vector3d startSpeed = new Vector3d();
  private static Vector3d shipSpeed = new Vector3d();
  private static Class _clsBigArrestorPlane = null;
  private static CellAirPlane _cellBigArrestorPlane = null;
  private static HashMap _clsMapArrestorPlane = new HashMap();

  public BigshipGeneric ship()
  {
    return this.ship;
  }

  public AirportCarrier(BigshipGeneric paramBigshipGeneric, Loc[] paramArrayOfLoc)
  {
    this.tmpLoc = new Loc();
    this.tmpP3d = new Point3d();
    this.tmpP3f = new Point3f();
    this.tmpOr = new Orient();
    this.curPlaneShift = 0;
    this.oldTickCounter = 0;
    this.r = new Loc();
    this.ship = paramBigshipGeneric;
    this.pos = new ActorPosMove(this, new Loc());
    this.pos.setBase(paramBigshipGeneric, null, false);
    this.pos.reset();
    this.runway = paramArrayOfLoc;
    if (Mission.isDogfight())
    {
      Point_Stay[][] arrayOfPoint_Stay1 = getStayPlaces();
      Point_Stay[][] arrayOfPoint_Stay2 = World.cur().airdrome.stay;
      Point_Stay[][] arrayOfPoint_Stay; = new Point_Stay[arrayOfPoint_Stay2.length + arrayOfPoint_Stay1.length][];
      int i = 0;
      for (int j = 0; j < arrayOfPoint_Stay2.length; j++) {
        arrayOfPoint_Stay;[(i++)] = arrayOfPoint_Stay2[j];
      }
      for (int k = 0; k < arrayOfPoint_Stay1.length; k++) {
        arrayOfPoint_Stay;[(i++)] = arrayOfPoint_Stay1[k];
      }
      World.cur().airdrome.stay = arrayOfPoint_Stay;;
    }
  }

  public boolean isAlive()
  {
    return Actor.isAlive(this.ship);
  }

  public int getArmy()
  {
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
    if (f > 1.0F)
      f = 1.0F;
    if (f < 0.4F)
      f = 0.4F;
    for (int i = x.length - 1; i >= 0; i--)
    {
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
    if (!this.ship.isAlive())
    {
      paramFlightModel.AP.way.setLanding(false);
      return;
    }
    this.tmpLoc.set(this.runway[1]);
    this.tmpLoc.add(this.ship.initLoc);
    float f = paramFlightModel.M.massEmpty * 0.0003333F;
    if (f > 1.0F)
      f = 1.0F;
    if (f < 0.4F)
      f = 0.4F;
    for (int i = 0; i < x.length; i++)
    {
      WayPoint localWayPoint = paramFlightModel.AP.way.get(i);
      this.tmpP3d.set(x[(x.length - 1 - i)] * f, y[(x.length - 1 - i)] * f, z[(x.length - 1 - i)] * f);
      this.tmpLoc.transform(this.tmpP3d);
      this.tmpP3f.set(this.tmpP3d);
      localWayPoint.set(this.tmpP3f);
    }
  }

  public void rebuildLastPoint(FlightModel paramFlightModel)
  {
    if (!Actor.isAlive(this.ship))
      return;
    int i = paramFlightModel.AP.way.Cur();
    paramFlightModel.AP.way.last();
    if (paramFlightModel.AP.way.curr().Action == 2)
    {
      this.ship.pos.getAbs(this.tmpP3d);
      paramFlightModel.AP.way.curr().set(this.tmpP3d);
    }
    paramFlightModel.AP.way.setCur(i);
  }

  public double ShiftFromLine(FlightModel paramFlightModel)
  {
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

  public int landingFeedback(Point3d paramPoint3d, Aircraft paramAircraft)
  {
    this.tmpLoc.set(this.runway[1]);
    this.tmpLoc.add(this.ship.initLoc);
    Aircraft localAircraft = War.getNearestFriendAtPoint(this.tmpLoc.getPoint(), paramAircraft, 50.0F);
    if ((localAircraft != null) && (localAircraft != paramAircraft))
      return 1;
    if (paramAircraft.FM.CT.GearControl > 0.0F)
      return 0;
    if (this.landingRequest > 0)
    {
      return 1;
    }

    this.landingRequest = 3000;
    return 0;
  }

  public void setTakeoffOld(Point3d paramPoint3d, Aircraft paramAircraft)
  {
    if (!Actor.isValid(paramAircraft))
      return;
    this.r.set(this.runway[0]);
    this.r.add(this.ship.pos.getAbs());
    if (Time.tickCounter() != this.oldTickCounter)
    {
      this.oldTickCounter = Time.tickCounter();
      this.curPlaneShift = 0;
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
    if ((paramAircraft.FM instanceof Maneuver))
      ((Maneuver)paramAircraft.FM).direction = paramAircraft.pos.getAbsOrient().getAzimut();
    paramAircraft.FM.AP.way.takeoffAirport = this;
    if (paramAircraft == World.getPlayerAircraft())
    {
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

  private int setAircraftDimensions(Aircraft paramAircraft)
  {
    int i = -1;

    if (((paramAircraft instanceof F4F)) && (!(paramAircraft instanceof F4F3)))
      i = 5;
    else if (((paramAircraft instanceof F6F)) || ((paramAircraft instanceof F4U)))
      i = 6;
    else if ((paramAircraft instanceof TBF))
    {
      i = 7;
    } else if (((paramAircraft instanceof B5N)) || ((paramAircraft instanceof B6N)))
      i = 8;
    else if ((paramAircraft instanceof D3A))
    {
      i = 12;
    } else if ((paramAircraft instanceof A6M2_21))
    {
      i = 11;
    } else if (((paramAircraft instanceof SEAFIRE3)) || ((paramAircraft instanceof SEAFIRE3F))) {
      i = 5;
    }

    if (i == -1)
      try {
        if ((paramAircraft instanceof F9F))
          i = 6;
      }
      catch (Throwable localThrowable1)
      {
      }
    if (i == -1)
      try {
        if ((paramAircraft instanceof SeaFury))
          i = 6;
      }
      catch (Throwable localThrowable2)
      {
      }
    if (i == -1)
      try {
        if ((paramAircraft instanceof Swordfish))
          i = 6;
      }
      catch (Throwable localThrowable3)
      {
      }
    return i;
  }

  public void setTakeoff(Point3d paramPoint3d, Aircraft[] paramArrayOfAircraft)
  {
    CellAirField localCellAirField = this.ship.getCellTO();
    if (localCellAirField == null)
    {
      for (i = 0; i < paramArrayOfAircraft.length; i++) {
        setTakeoffOld(paramPoint3d, paramArrayOfAircraft[i]);
      }
      return;
    }
    if (Time.tickCounter() != this.oldTickCounter)
    {
      this.oldTickCounter = Time.tickCounter();
      this.curPlaneShift = 0;
      localCellAirField.freeCells();
    }
    this.ship.getSpeed(shipSpeed);

    int i = setAircraftDimensions(paramArrayOfAircraft[0]);
    Object localObject;
    for (int j = 0; j < paramArrayOfAircraft.length; j++) {
      if (!Actor.isValid(paramArrayOfAircraft[j]))
        continue;
      localObject = paramArrayOfAircraft[j].getCellAirPlane();
      ((CellAirPlane)localObject).setFoldedWidth(i);

      if (localCellAirField.findPlaceForAirPlaneCarrier((CellAirPlane)localObject))
      {
        localCellAirField.placeAirPlaneCarrier((CellAirPlane)localObject, localCellAirField.resX(), localCellAirField.resY());

        double d1 = -localCellAirField.leftUpperCorner().x - localCellAirField.resX() * localCellAirField.getCellSize() - ((CellAirPlane)localObject).getWidth() / 2;
        double d2 = localCellAirField.leftUpperCorner().y - localCellAirField.resY() * localCellAirField.getCellSize() - ((CellAirPlane)localObject).ofsY;

        double d3 = this.runway[0].getZ();
        this.tmpLoc.set(d2, d1, d3 + paramArrayOfAircraft[j].FM.Gears.H, this.runway[0].getAzimut(), this.runway[0].getTangage(), this.runway[0].getKren());
        this.tmpLoc.add(this.ship.pos.getAbs());
        Point3d localPoint3d = this.tmpLoc.getPoint();
        Orient localOrient = this.tmpLoc.getOrient();
        localOrient.increment(0.0F, paramArrayOfAircraft[j].FM.Gears.Pitch, 0.0F);
        paramArrayOfAircraft[j].setOnGround(localPoint3d, localOrient, shipSpeed);
        if ((paramArrayOfAircraft[j].FM instanceof Maneuver))
          ((Maneuver)paramArrayOfAircraft[j].FM).direction = paramArrayOfAircraft[j].pos.getAbsOrient().getAzimut();
        paramArrayOfAircraft[j].FM.AP.way.takeoffAirport = this;
        paramArrayOfAircraft[j].FM.brakeShoe = true;
        paramArrayOfAircraft[j].FM.turnOffCollisions = true;
        paramArrayOfAircraft[j].FM.brakeShoeLoc.set(paramArrayOfAircraft[j].pos.getAbs());
        paramArrayOfAircraft[j].FM.brakeShoeLoc.sub(this.ship.pos.getAbs());
        paramArrayOfAircraft[j].FM.brakeShoeLastCarrier = this.ship;
        paramArrayOfAircraft[j].FM.Gears.bFlatTopGearCheck = true;
        paramArrayOfAircraft[j].makeMirrorCarrierRelPos();
        if (i > 0) {
          paramArrayOfAircraft[j].FM.CT.wingControl = 1.0F;
          paramArrayOfAircraft[j].FM.CT.forceWing(1.0F);
        }
      }
      else {
        setTakeoffOld(paramPoint3d, paramArrayOfAircraft[j]);
      }
    }

    if ((Actor.isValid(paramArrayOfAircraft[0])) && ((paramArrayOfAircraft[0].FM instanceof Maneuver)))
    {
      localObject = (Maneuver)paramArrayOfAircraft[0].FM;
      if ((((Maneuver)localObject).Group != null) && (((Maneuver)localObject).Group.w != null))
        ((Maneuver)localObject).Group.w.takeoffAirport = this;
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
    if ((paramAircraft.FM instanceof Maneuver))
      ((Maneuver)paramAircraft.FM).direction = paramLoc.getAzimut();
    paramAircraft.FM.AP.way.takeoffAirport = this;
    paramAircraft.FM.brakeShoe = true;
    paramAircraft.FM.turnOffCollisions = true;
    paramAircraft.FM.brakeShoeLoc.set(paramLoc);
    paramAircraft.FM.brakeShoeLoc.sub(this.ship.pos.getAbs());
    paramAircraft.FM.brakeShoeLastCarrier = this.ship;
  }

  public float height()
  {
    return (float)(this.ship.pos.getAbs().getZ() + this.runway[0].getZ());
  }

  public static boolean isPlaneContainsArrestor(Class paramClass)
  {
    clsBigArrestorPlane();
    return _clsMapArrestorPlane.containsKey(paramClass);
  }

  private static Class clsBigArrestorPlane()
  {
    if (_clsBigArrestorPlane != null)
      return _clsBigArrestorPlane;
    double d1 = 0.0D;
    SectFile localSectFile1 = new SectFile("com/maddox/il2/objects/air.ini", 0);
    int i = localSectFile1.sections();
    for (int j = 0; j < i; j++)
    {
      int k = localSectFile1.vars(j);
      for (int m = 0; m < k; m++)
      {
        String str1 = localSectFile1.value(j, m);
        StringTokenizer localStringTokenizer = new StringTokenizer(localSectFile1.value(j, m));
        if (!localStringTokenizer.hasMoreTokens())
          continue;
        String str2 = "com.maddox.il2.objects." + localStringTokenizer.nextToken();
        Class localClass = null;
        String str3 = null;
        try
        {
          localClass = Class.forName(str2);
          str3 = Property.stringValue(localClass, "FlightModel", null);
        }
        catch (Exception localException1)
        {
          System.out.println(localException1.getMessage());
          localException1.printStackTrace();
        }
        try
        {
          if (str3 != null)
          {
            SectFile localSectFile2 = FlightModelMain.sectFile(str3);
            if (localSectFile2.get("Controls", "CArrestorHook", 0) == 1)
            {
              _clsMapArrestorPlane.put(localClass, null);
              String str4 = Aircraft.getPropertyMesh(localClass, null);
              SectFile localSectFile3 = new SectFile(str4, 0);
              String str5 = localSectFile3.get("_ROOT_", "CollisionObject", (String)null);
              if (str5 != null)
              {
                NumberTokenizer localNumberTokenizer = new NumberTokenizer(str5);
                if (localNumberTokenizer.hasMoreTokens())
                {
                  localNumberTokenizer.next();
                  if (localNumberTokenizer.hasMoreTokens())
                  {
                    double d2 = localNumberTokenizer.next(-1.0D);
                    if ((d2 > 0.0D) && (d1 < d2))
                    {
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

    return _clsBigArrestorPlane;
  }

  private static CellAirPlane cellBigArrestorPlane()
  {
    if (_cellBigArrestorPlane != null)
    {
      return _cellBigArrestorPlane;
    }

    _cellBigArrestorPlane = Aircraft.getCellAirPlane(clsBigArrestorPlane());
    return _cellBigArrestorPlane;
  }

  private Point_Stay[][] getStayPlaces()
  {
    Point_Stay[][] arrayOfPoint_Stay = null;
    Class localClass = this.ship.getClass();
    arrayOfPoint_Stay = (Point_Stay[][])Property.value(localClass, "StayPlaces", null);
    Object localObject2;
    if (arrayOfPoint_Stay == null)
    {
      localObject1 = cellBigArrestorPlane();
      CellAirField localCellAirField = this.ship.getCellTO();
      localCellAirField = (CellAirField)localCellAirField.getClone();
      localObject2 = new ArrayList();
      while (true)
      {
        localObject1 = (CellAirPlane)((CellAirPlane)localObject1).getClone();

        if (!localCellAirField.findPlaceForAirPlane((CellAirPlane)localObject1))
          break;
        localCellAirField.placeAirPlane((CellAirPlane)localObject1, localCellAirField.resX(), localCellAirField.resY());
        double d1 = -localCellAirField.leftUpperCorner().x - localCellAirField.resX() * localCellAirField.getCellSize() - ((CellAirPlane)localObject1).ofsX;
        double d3 = localCellAirField.leftUpperCorner().y - localCellAirField.resY() * localCellAirField.getCellSize() - ((CellAirPlane)localObject1).ofsY;
        ((ArrayList)localObject2).add(new Point2d(d3, d1));
      }
      int j = ((ArrayList)localObject2).size();
      if (j > 0)
      {
        arrayOfPoint_Stay = new Point_Stay[j][1];
        for (int k = 0; k < j; k++)
        {
          Point2d localPoint2d = (Point2d)((ArrayList)localObject2).get(k);
          arrayOfPoint_Stay[k][0] = new Point_Stay((float)localPoint2d.x, (float)localPoint2d.y);
        }

        Property.set(localClass, "StayPlaces", arrayOfPoint_Stay);
      }
    }
    if (arrayOfPoint_Stay == null)
      return null;
    Object localObject1 = new Point_Stay[arrayOfPoint_Stay.length][1];
    for (int i = 0; i < arrayOfPoint_Stay.length; i++)
    {
      localObject2 = arrayOfPoint_Stay[i][0];
      double d2 = ((Tuple2f)localObject2).x;
      double d4 = ((Tuple2f)localObject2).y;
      double d5 = this.runway[0].getZ();
      this.tmpLoc.set(d2, d4, d5, this.runway[0].getAzimut(), this.runway[0].getTangage(), this.runway[0].getKren());
      this.tmpLoc.add(this.ship.pos.getAbs());
      Point3d localPoint3d = this.tmpLoc.getPoint();
      localObject1[i][0] = new Point_Stay((float)localPoint3d.x, (float)localPoint3d.y);
    }

    return (Point_Stay)(Point_Stay)localObject1;
  }
}