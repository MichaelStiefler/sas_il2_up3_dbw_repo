package com.maddox.il2.ai;

import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosStatic;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Time;
import java.util.ArrayList;

public abstract class AirportStatic extends Airport
{
  private ArrayList runway;
  public static final int PT_RUNWAY = 1;
  public static final int PT_TAXI = 2;
  public static final int PT_STAY = 4;
  private static Point3d p3d = new Point3d();

  private static float[] x = { -500.0F, 0.0F, 220.0F, 2000.0F, 4000.0F, 5000.0F, 4000.0F, 0.0F, 0.0F };
  private static float[] y = { 0.0F, 0.0F, 0.0F, 0.0F, -500.0F, -2000.0F, -4000.0F, -4000.0F, -4000.0F };
  private static float[] z = { 0.0F, 6.0F, 20.0F, 160.0F, 500.0F, 600.0F, 700.0F, 700.0F, 700.0F };
  private static float[] v = { 0.0F, 180.0F, 220.0F, 240.0F, 270.0F, 280.0F, 300.0F, 300.0F, 300.0F };

  private static Point3d pWay = new Point3d();
  private static Point3d pd = new Point3d();
  private static Point3f pf = new Point3f();

  private static Vector3d v1 = new Vector3d();
  private static Vector3d zeroSpeed = new Vector3d();
  private static Loc tmpLoc = new Loc();

  private static Point3d pcur = new Point3d();
  private static Point3d np = new Point3d();
  private static Vector3d p1 = new Vector3d();
  private static Vector3d p2 = new Vector3d();

  public AirportStatic()
  {
    this.runway = new ArrayList();
  }

  public static void make(ArrayList paramArrayList, Point2f[][] paramArrayOfPoint2f1, Point2f[][] paramArrayOfPoint2f2, Point2f[][] paramArrayOfPoint2f3)
  {
    if (paramArrayList == null) return;

    ArrayList localArrayList = new ArrayList();
    double d = 4000000.0D;
    if (paramArrayList.size() == 4) d = 2890000.0D;

    while (paramArrayList.size() > 0) {
      Loc localLoc = (Loc)paramArrayList.remove(0);

      int i = 0;
      Object localObject = null;
      for (int j = 0; j < localArrayList.size(); j++) {
        localObject = (AirportStatic)localArrayList.get(j);
        if (((AirportStatic)localObject).oppositeRunway(localLoc) != null) {
          i = 1;
          break;
        }
      }
      if (i != 0)
      {
        ((AirportStatic)localObject).runway.add(new Runway(localLoc));
        j = ((AirportStatic)localObject).runway.size();

        p3d.set(0.0D, 0.0D, 0.0D);
        for (int k = 0; k < j; k++) {
          localLoc = ((Runway)((AirportStatic)localObject).runway.get(k)).loc;
          p3d.x += localLoc.getPoint().x;
          p3d.y += localLoc.getPoint().y;
          p3d.z += localLoc.getPoint().z;
        }
        p3d.x /= j;
        p3d.y /= j;
        p3d.z /= j;
        ((AirportStatic)localObject).pos.setAbs(p3d);
      }
      else {
        if (Engine.cur.land.isWater(localLoc.getPoint().x, localLoc.getPoint().y))
          localObject = new AirportMaritime();
        else
          localObject = new AirportGround();
        ((AirportStatic)localObject).pos = new ActorPosStatic((Actor)localObject, localLoc);
        ((AirportStatic)localObject).runway.add(new Runway(localLoc));
        localArrayList.add(localObject);
      }
    }
  }

  public boolean landWay(FlightModel paramFlightModel)
  {
    paramFlightModel.AP.way.curr().getP(pWay);
    Runway localRunway = nearestRunway(pWay);
    if (localRunway == null) return false;

    Way localWay = new Way();

    float f1 = (float)Engine.land().HQ_Air(localRunway.loc.getX(), localRunway.loc.getY());
    float f2 = paramFlightModel.M.massEmpty / 3000.0F;
    if (f2 > 1.0F) f2 = 1.0F;
    if (paramFlightModel.EI.engines[0].getType() > 1) f2 = 1.0F;
    if (paramFlightModel.EI.engines[0].getType() == 3) f2 = 1.5F;
    float f3 = f2;
    if (f3 > 1.0F) f3 = 1.0F;

    for (int i = x.length - 1; i >= 0; i--) {
      WayPoint localWayPoint = new WayPoint();
      pd.set(x[i] * f2, y[i] * f2, z[i] * f3);
      localWayPoint.set(Math.min(v[i] * 0.278F, paramFlightModel.Vmax * 0.7F));
      localWayPoint.Action = 2;
      localRunway.loc.transform(pd);
      float f4 = (float)Engine.land().HQ_Air(pd.x, pd.y);
      pd.z -= f4 - f1;
      pf.set(pd);
      localWayPoint.set(pf);
      localWay.add(localWayPoint);
    }
    localWay.setLanding(true);
    paramFlightModel.AP.way = localWay;

    return true;
  }

  public void setTakeoff(Point3d paramPoint3d, Aircraft[] paramArrayOfAircraft)
  {
    Runway localRunway1 = nearestRunway(paramPoint3d);
    if (localRunway1 == null) return;
    Runway localRunway2 = oppositeRunway(localRunway1.loc);
    double d = 1000.0D;
    if (localRunway2 != null) {
      d = localRunway1.loc.getPoint().distance(localRunway2.loc.getPoint());
    }
    if (Time.tickCounter() != localRunway1.oldTickCounter) {
      localRunway1.oldTickCounter = Time.tickCounter();
      localRunway1.curPlaneShift = 0;
    }
    for (int i = 0; i < paramArrayOfAircraft.length; i++)
      if (Actor.isValid(paramArrayOfAircraft[i])) {
        float f = paramArrayOfAircraft[i].collisionR() * 2.0F + 20.0F;
        for (int j = localRunway1.curPlaneShift; j > 0; j--) {
          localRunway1.planeShift[j] = (localRunway1.planeShift[(j - 1)] + f);
          localRunway1.planes[j] = localRunway1.planes[(j - 1)];
        }
        localRunway1.planeShift[0] = 0.0F;
        localRunway1.planes[0] = paramArrayOfAircraft[i];
        localRunway1.curPlaneShift += 1;
        if (localRunway1.curPlaneShift > 31) throw new RuntimeException("Too many planes on airdrome");
        for (j = 0; j < localRunway1.curPlaneShift; j++) {
          if (!Actor.isValid(localRunway1.planes[j]))
            continue;
          tmpLoc.set(localRunway1.planeShift[j] - d, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
          tmpLoc.add(localRunway1.loc);
          Point3d localPoint3d = tmpLoc.getPoint();
          Orient localOrient = tmpLoc.getOrient();

          localPoint3d.z = (World.land().HQ(localPoint3d.x, localPoint3d.y) + localRunway1.planes[j].FM.Gears.H);
          Engine.land().N(localPoint3d.x, localPoint3d.y, v1);
          localOrient.orient(v1);
          localOrient.increment(0.0F, localRunway1.planes[j].FM.Gears.Pitch, 0.0F);

          localRunway1.planes[j].setOnGround(localPoint3d, localOrient, zeroSpeed);
          if ((localRunway1.planes[j].FM instanceof Maneuver)) {
            ((Maneuver)localRunway1.planes[j].FM).direction = localRunway1.planes[j].pos.getAbsOrient().getAzimut();
            ((Maneuver)localRunway1.planes[j].FM).rwLoc = localRunway1.loc;
          }
          localRunway1.planes[j].FM.AP.way.takeoffAirport = this;
        }
      }
    if ((Actor.isValid(paramArrayOfAircraft[0])) && ((paramArrayOfAircraft[0].FM instanceof Maneuver))) {
      Maneuver localManeuver = (Maneuver)paramArrayOfAircraft[0].FM;
      if ((localManeuver.Group != null) && (localManeuver.Group.w != null)) localManeuver.Group.w.takeoffAirport = this;
    }
  }

  public double ShiftFromLine(FlightModel paramFlightModel)
  {
    tmpLoc.set(paramFlightModel.Loc);
    if ((paramFlightModel instanceof Maneuver)) {
      Maneuver localManeuver = (Maneuver)paramFlightModel;
      if (localManeuver.rwLoc != null) {
        tmpLoc.sub(localManeuver.rwLoc);
        return tmpLoc.getY();
      }
    }
    return 0.0D;
  }

  public boolean nearestRunway(Point3d paramPoint3d, Loc paramLoc)
  {
    Runway localRunway = nearestRunway(paramPoint3d);
    if (localRunway != null) {
      paramLoc.set(localRunway.loc);
      return true;
    }
    return false;
  }

  private Runway nearestRunway(Point3d paramPoint3d)
  {
    Object localObject = null;
    double d1 = 0.0D;
    np.set(paramPoint3d);
    int i = this.runway.size();
    for (int j = 0; j < i; j++) {
      Runway localRunway = (Runway)this.runway.get(j);
      np.z = localRunway.loc.getPoint().z;
      double d2 = localRunway.loc.getPoint().distanceSquared(np);
      if ((localObject == null) || (d2 < d1)) {
        localObject = localRunway;
        d1 = d2;
      }
    }
    if (d1 > 225000000.0D)
      localObject = null;
    return localObject;
  }

  private Runway oppositeRunway(Loc paramLoc) {
    int i = this.runway.size();
    for (int j = 0; j < i; j++) {
      Runway localRunway = (Runway)this.runway.get(j);
      pcur.set(localRunway.loc.getPoint());
      paramLoc.transformInv(pcur);
      if ((Math.abs(pcur.y) < 15.0D) && (pcur.x < -800.0D) && (pcur.x > -2500.0D)) {
        p1.set(1.0D, 0.0D, 0.0D);
        p2.set(1.0D, 0.0D, 0.0D);
        localRunway.loc.getOrient().transform(p1);
        paramLoc.getOrient().transform(p2);
        if (p1.dot(p2) < -0.9D) return localRunway;
      }
    }
    return null;
  }

  private static class Runway
  {
    public Loc loc = new Loc();
    public float[] planeShift = new float[32];
    public Aircraft[] planes = new Aircraft[32];
    public int curPlaneShift = 0;
    public int oldTickCounter = 0;

    public Runway(Loc paramLoc) {
      this.loc.set(paramLoc.getX(), paramLoc.getY(), World.land().HQ(paramLoc.getX(), paramLoc.getY()), paramLoc.getAzimut(), 0.0F, 0.0F);
    }
  }
}