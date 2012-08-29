package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Locator;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.rts.Message;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;

public class Runaway extends ActorMesh
{
  public static boolean bDrawing = false;
  public static final int RW_SELECTION_ANY = 0;
  public static final int RW_SELECTION_GROUND = 1;
  public static final int RW_SELECTION_MARITIME = 2;
  private Runaway next;
  private float[] PlaneShift = new float[32];
  private Aircraft[] Planes = new Aircraft[32];
  private int curPlaneShift = 0;
  private int oldTickCounter = 0;

  private static float[] x = { -500.0F, 0.0F, 210.0F, 2000.0F, 4000.0F, 5000.0F, 4000.0F, 0.0F, 0.0F };
  private static float[] y = { 0.0F, 0.0F, 0.0F, 0.0F, -500.0F, -2000.0F, -4000.0F, -4000.0F, -4000.0F };
  private static float[] z = { 0.0F, 0.0F, 10.0F, 130.0F, 300.0F, 500.0F, 500.0F, 500.0F, 500.0F };
  private static float[] v = { 0.0F, 170.0F, 180.0F, 225.0F, 250.0F, 250.0F, 250.0F, 250.0F, 250.0F };
  private static Locator RW;
  private static Point3d pcur;
  private static Point3d pd;
  private static Point3f pf;
  private Vector3d zeroSpeed = new Vector3d();
  private Loc tmpLoc = new Loc();
  private int craftNo;

  public Runaway next()
  {
    return this.next;
  }
  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public Runaway() {
    super("3do/Airfield/runaway/mono.sim");

    collide(false);
    drawing(bDrawing);

    Runaway localRunaway = World.cur().runawayList;
    if (localRunaway == null) { World.cur().runawayList = this; return; }
    do localRunaway = localRunaway.next; while (localRunaway.next != null);
    localRunaway.next = this;
  }

  public static Runaway nearest(Point3d paramPoint3d, int paramInt)
  {
    double d1 = 0.0D; double d2 = 1.7976931348623157E+308D;
    Runaway localRunaway2 = World.cur().runawayList;
    if (localRunaway2 == null) return null;
    localRunaway2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(pcur);
    if ((paramInt == 0) || ((!isMaritime(localRunaway2)) && (paramInt == 1)) || ((isMaritime(localRunaway2)) && (paramInt == 2)))
    {
      d2 = paramPoint3d.distanceSquared(pcur);
    }
    Runaway localRunaway1 = localRunaway2;

    for (localRunaway2 = localRunaway2.next; localRunaway2 != null; localRunaway2 = localRunaway2.next) {
      localRunaway2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(pcur);
      d1 = paramPoint3d.distanceSquared(pcur);
      if ((d1 >= d2) || (
        (paramInt != 0) && ((isMaritime(localRunaway2)) || (paramInt != 1)) && ((!isMaritime(localRunaway2)) || (paramInt != 2))))
      {
        continue;
      }
      d2 = d1;
      localRunaway1 = localRunaway2;
    }

    if (d2 > 225000000.0D)
    {
      return null;
    }
    if (((isMaritime(localRunaway1)) && (paramInt == 1)) || ((!isMaritime(localRunaway1)) && (paramInt == 2)))
    {
      return null;
    }
    return localRunaway1;
  }

  public static Runaway opposite(Loc paramLoc)
  {
    Runaway localRunaway = World.cur().runawayList;
    if (localRunaway == null) return null;
    localRunaway.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(pcur);
    paramLoc.transformInv(pcur);
    if ((Math.abs(pcur.jdField_y_of_type_Double) < 15.0D) && (pcur.jdField_x_of_type_Double < -800.0D) && (pcur.jdField_x_of_type_Double > -2500.0D)) return localRunaway;
    for (localRunaway = localRunaway.next; localRunaway != null; localRunaway = localRunaway.next) {
      localRunaway.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(pcur);
      paramLoc.transformInv(pcur);
      if ((Math.abs(pcur.jdField_y_of_type_Double) < 15.0D) && (pcur.jdField_x_of_type_Double < -800.0D) && (pcur.jdField_x_of_type_Double > -2500.0D)) return localRunaway;
    }
    return null;
  }

  public static Runaway nearest(Point3f paramPoint3f, int paramInt)
  {
    pd.set(paramPoint3f);
    return nearest(pd, paramInt);
  }

  public static boolean isMaritime(Runaway paramRunaway)
  {
    return Engine.cur.land.isWater(paramRunaway.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_x_of_type_Double, paramRunaway.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_y_of_type_Double);
  }

  public void setAircrafts(Aircraft[] paramArrayOfAircraft) {
    Runaway localRunaway = opposite(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
    float f1 = 1000.0F;
    if (localRunaway != null) {
      f1 = (float)this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().getPoint().distance(localRunaway.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().getPoint());
    }
    if (Time.tickCounter() != this.oldTickCounter) {
      this.oldTickCounter = Time.tickCounter();
      this.curPlaneShift = 0;
    }
    for (int i = 0; i < paramArrayOfAircraft.length; i++)
      if (Actor.isValid(paramArrayOfAircraft[i])) {
        float f2 = paramArrayOfAircraft[i].collisionR() * 2.0F + 20.0F;
        for (int j = this.curPlaneShift; j > 0; j--) {
          this.PlaneShift[j] = (this.PlaneShift[(j - 1)] + f2);
          this.Planes[j] = this.Planes[(j - 1)];
        }
        this.PlaneShift[0] = 0.0F;
        this.Planes[0] = paramArrayOfAircraft[i];
        this.curPlaneShift += 1;
        if (this.curPlaneShift > 31) throw new RuntimeException("Too many planes on airdrome");
        for (int k = 0; k < this.curPlaneShift; k++) {
          if (!Actor.isValid(this.Planes[k]))
            continue;
          this.tmpLoc.set(this.PlaneShift[k] - f1, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
          this.tmpLoc.add(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
          this.Planes[k].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.tmpLoc);
          this.Planes[k].setSpeed(this.zeroSpeed);
          this.Planes[k].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
          if (!(this.Planes[k].jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver)) continue; ((Maneuver)this.Planes[k].jdField_FM_of_type_ComMaddoxIl2FmFlightModel).direction = this.Planes[k].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient().getAzimut();
        }
      }
  }

  public Way landWay(FlightModel paramFlightModel)
  {
    Way localWay = new Way();

    RW.set(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
    float f1 = (float)Engine.land().HQ_Air(RW.getX(), RW.getY());
    float f2 = paramFlightModel.M.massEmpty / 3000.0F;
    if (f2 > 1.0F) f2 = 1.0F;

    for (int i = x.length - 1; i >= 0; i--) {
      WayPoint localWayPoint = new WayPoint();
      pd.set(x[i] * f2, y[i] * f2, z[i] * f2);
      localWayPoint.set(Math.min(v[i] * 0.278F, paramFlightModel.Vmax * 0.6F));
      localWayPoint.Action = 2;
      RW.transform(pd);
      float f3 = (float)Engine.land().HQ_Air(pd.jdField_x_of_type_Double, pd.jdField_y_of_type_Double);
      pd.z -= f3 - f1;
      pf.set(pd);
      localWayPoint.set(pf);
      localWay.add(localWayPoint);
    }
    localWay.setLanding(true);
    return localWay;
  }

  static
  {
    Class localClass = Runaway.class;
    Spawn.add(localClass, new SPAWN());
    Property.set(localClass, "iconName", "icons/runaway.mat");

    RW = new Locator();
    pcur = new Point3d();
    pd = new Point3d();
    pf = new Point3f();
  }

  public static class SPAWN
    implements ActorSpawn
  {
    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg)
    {
      Actor localActor = paramActorSpawnArg.set(new Runaway());
      if (localActor != null) {
        Point3d localPoint3d = localActor.pos.getAbsPoint();
        localActor.pos.setAbs(new Point3d(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double, World.land().HQ(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double)));
      }
      return localActor;
    }
  }
}