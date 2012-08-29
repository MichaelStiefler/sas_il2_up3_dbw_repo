package com.maddox.il2.objects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.NetObj;
import com.maddox.rts.Time;

public class Wreckage extends ActorMesh
{
  public static String SMOKE = "EFFECTS/Smokes/SmokeBlack_Wreckage.eff";
  public static String SMOKE_EXPLODE = "EFFECTS/Smokes/SmokeBlack_Wreckage_Explode.eff";
  public static String FIRE = "EFFECTS/Explodes/Wreckage_Burn.eff";
  private float M;
  private float A;
  private int lr;
  private Vector3d v = new Vector3d();
  private Vector3f W = new Vector3f();
  private static Vector3d N = new Vector3d();
  private static Vector3f Nf = new Vector3f();
  private static Vector3f sz = new Vector3f();
  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Orient oh = new Orient();
  private static float wn;
  private static float[] dv = { -80.0F, -60.0F, -40.0F, -20.0F, 20.0F, 40.0F, 60.0F, 80.0F, 0.0F };
  private boolean bBoundToBeDestroyed = false;
  public NetObj netOwner;
  private static long timeLastWreckageDrop_Water = 0L;
  private static Point3d posLastWreckageDrop_Water = new Point3d();
  private static Point3d pClipZ1 = new Point3d();
  private static Point3d pClipZ2 = new Point3d();
  private static Point3d pClipRes = new Point3d();
  static ClipFilter clipFilter = new ClipFilter();

  private static Loc LO = new Loc();

  public void setSpeed(Vector3d paramVector3d)
  {
    this.v.set(paramVector3d); this.v.jdField_z_of_type_Double += 5.0D;
    if (this.v.jdField_z_of_type_Double > 50.0D)
      this.v.jdField_z_of_type_Double = 50.0D;
  }

  private void construct(float paramFloat)
  {
    collide(true);
    drawing(true);
    getDimensions(sz);
    this.M = paramFloat;
    float f1 = sz.jdField_x_of_type_Float * sz.jdField_y_of_type_Float + sz.jdField_x_of_type_Float * sz.jdField_z_of_type_Float + sz.jdField_z_of_type_Float * sz.jdField_y_of_type_Float;
    if (f1 < 0.01F) {
      f1 = 0.01F;
    }
    if (this.M < 0.001F)
    {
      f2 = sz.jdField_x_of_type_Float * sz.jdField_y_of_type_Float * sz.jdField_z_of_type_Float;
      if (f2 < 0.01F) {
        f2 = 0.01F;
      }
      f3 = f1 * 2.0F * 0.02F;
      this.M = (500.0F * Math.min(f2, f3));
    }
    float f2 = sz.jdField_x_of_type_Float; float f3 = sz.jdField_y_of_type_Float; float f4 = sz.jdField_z_of_type_Float;
    int i = 0; int j = 1; int k = 2;
    float f5;
    int m;
    if (f2 > f3) {
      f5 = f2; f2 = f3; f3 = f5;
      m = i; i = j; j = m;
    }
    if (f3 > f4) {
      f5 = f3; f3 = f4; f4 = f5;
      m = j; j = k; k = m;
      if (f2 > f3) {
        f5 = f2; f2 = f3; f3 = f5;
        m = i; i = j; j = m;
      }
    }
    if (f3 * 8.0F < f4) {
      switch (i) { case 0:
        this.W.set(1.0F, 0.0F, 0.0F); break;
      case 1:
        this.W.set(0.0F, 1.0F, 0.0F); break;
      case 2:
        this.W.set(0.0F, 0.0F, 1.0F);
      }
      wn = k;
    } else if (f2 * 3.0F < f3) {
      switch (k) { case 0:
        this.W.set(1.0F, 0.0F, 0.0F); break;
      case 1:
        this.W.set(0.0F, 1.0F, 0.0F); break;
      case 2:
        this.W.set(0.0F, 0.0F, 1.0F);
      }
      wn = j;
    } else {
      this.W.set(sz);
      if (this.W.jdField_x_of_type_Float < 1.0E-010F) this.W.jdField_x_of_type_Float += 1.0E-010F;
      this.W.normalize();
      wn = k;
    }
    wn = 60.0F + 80.0F / (wn + 0.2F);
    this.W.scale(wn);
    this.lr = World.Rnd().nextInt(0, 8);
    this.A = (0.02F / this.M);
    interpPut(new Interpolater(), null, Time.current(), null);
    MsgDestroy.Post(Time.current() + 120000L, this);
  }

  float DEG2RAD(float paramFloat)
  {
    return paramFloat * 0.01745329F; } 
  float RAD2DEG(float paramFloat) { return paramFloat * 57.295784F;
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }
  public Wreckage(ActorHMesh paramActorHMesh, int paramInt) {
    super(paramActorHMesh, paramInt);
    construct(paramActorHMesh.getChunkMass());
    paramActorHMesh.hierMesh().setCurChunk(paramInt);
    if ((paramActorHMesh instanceof Aircraft)) {
      setOwner(paramActorHMesh, false, false, false);
      this.netOwner = ((Aircraft)paramActorHMesh).netUser();
    }
  }

  private static void WreckageONLYDrop_Water(Point3d paramPoint3d)
  {
    if (!Config.isUSE_RENDER()) return;
    long l = Time.current();
    if (l == timeLastWreckageDrop_Water) {
      double d1 = posLastWreckageDrop_Water.jdField_x_of_type_Double - paramPoint3d.jdField_x_of_type_Double;
      double d2 = posLastWreckageDrop_Water.jdField_y_of_type_Double - paramPoint3d.jdField_y_of_type_Double;
      if (d1 * d1 + d2 * d2 < 100.0D) {
        return;
      }
    }
    pClipZ1.set(p);
    pClipZ2.set(p);
    pClipZ1.jdField_z_of_type_Double -= 2.0D;
    pClipZ2.jdField_z_of_type_Double += 42.0D;
    Actor localActor = Engine.collideEnv().getLine(pClipZ2, pClipZ1, false, clipFilter, pClipRes);
    if (Actor.isValid(localActor)) {
      return;
    }
    timeLastWreckageDrop_Water = l;
    posLastWreckageDrop_Water.set(paramPoint3d);

    Explosions.WreckageDrop_Water(paramPoint3d);
  }

  public static Wreck makeWreck(ActorHMesh paramActorHMesh, int paramInt)
  {
    HierMesh localHierMesh1 = paramActorHMesh.hierMesh();

    localHierMesh1.setCurChunk(paramInt);
    int[] arrayOfInt = localHierMesh1.getSubTrees(localHierMesh1.chunkName());
    if ((arrayOfInt == null) || (arrayOfInt.length < 1)) {
      return null;
    }

    double d1 = 0.0D;
    Point3d localPoint3d1 = new Point3d(0.0D, 0.0D, 0.0D);

    Point3f localPoint3f1 = new Point3f();
    Point3f localPoint3f2 = new Point3f();
    Point3d localPoint3d2 = new Point3d();

    for (int i = 0; i < arrayOfInt.length; i++) {
      localHierMesh1.setCurChunk(arrayOfInt[i]);
      if (!localHierMesh1.isChunkVisible())
      {
        continue;
      }

      localHierMesh1.getChunkCurVisBoundBox(localPoint3f1, localPoint3f2);
      localPoint3d2.set(localPoint3f1);
      localPoint3d2.add(localPoint3f2.jdField_x_of_type_Float, localPoint3f2.jdField_y_of_type_Float, localPoint3f2.jdField_z_of_type_Float);
      localPoint3d2.scale(0.5D);

      localHierMesh1.getChunkLocObj(LO);
      LO.transform(localPoint3d2);

      localPoint3f2.sub(localPoint3f1);
      double d2 = localPoint3f2.jdField_x_of_type_Float * localPoint3f2.jdField_y_of_type_Float * localPoint3f2.jdField_z_of_type_Float;
      double d3 = 500.0D;
      double d4 = d2 * d3;

      d1 += d4;
      localPoint3d2.scale(d4);
      localPoint3d1.add(localPoint3d2);
    }

    if (d1 > 0.0001D) {
      localPoint3d1.scale(1.0D / d1);
    } else {
      localHierMesh1.setCurChunk(paramInt);
      localHierMesh1.getChunkLocObj(LO);
      localPoint3d1.set(LO.getPoint());
    }

    localHierMesh1.setCurChunk(paramInt);
    localHierMesh1.getChunkLocObj(LO);
    LO.getPoint().set(localPoint3d1);

    HierMesh localHierMesh2 = new HierMesh(localHierMesh1, paramInt, LO);

    Loc localLoc = new Loc();
    paramActorHMesh.pos.getAbs(localLoc);
    LO.add(localLoc);

    return new Wreck(localHierMesh2, LO);
  }

  static class ClipFilter
    implements ActorFilter
  {
    public boolean isUse(Actor paramActor, double paramDouble)
    {
      return ((paramActor instanceof BigshipGeneric)) || ((paramActor instanceof ShipGeneric));
    }
  }

  class Interpolater extends Interpolate
  {
    Interpolater()
    {
    }

    private double deceleron(double paramDouble, float paramFloat)
    {
      if (paramDouble > 0.0D) paramDouble -= paramFloat * paramDouble * paramDouble; else paramDouble += paramFloat * paramDouble * paramDouble;
      return paramDouble;
    }
    public boolean tick() {
      float f1 = Time.tickLenFs();
      float f3 = (float)Wreckage.this.v.length();

      float f2 = 10.0F / (f3 + 0.1F); if (f2 > 1.0F) f2 = 1.0F;
      f2 *= Wreckage.dv[Wreckage.this.lr] * f1;

      Wreckage.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(Wreckage.p, Wreckage.o);
      Wreckage.o.increment(Wreckage.this.W.z * f1, Wreckage.this.W.y * f1, -Wreckage.this.W.x * f1);
      Wreckage.oh.set(f2, 0.0F, 0.0F);
      Wreckage.oh.transform(Wreckage.this.v);
      Wreckage.o.setYaw(Wreckage.o.getYaw() - f2);
      float f5 = Wreckage.this.A * f1;
      Wreckage.this.v.jdField_x_of_type_Double = deceleron(Wreckage.this.v.jdField_x_of_type_Double, f5);
      Wreckage.this.v.jdField_y_of_type_Double = deceleron(Wreckage.this.v.jdField_y_of_type_Double, f5);
      Wreckage.this.v.jdField_z_of_type_Double = deceleron(Wreckage.this.v.jdField_z_of_type_Double, f5);
      Wreckage.this.v.jdField_z_of_type_Double -= World.g() * f1;
      Wreckage.p.scaleAdd(f1, Wreckage.this.v, Wreckage.p);
      double d = World.land().HQ(Wreckage.p.jdField_x_of_type_Double, Wreckage.p.jdField_y_of_type_Double);
      if (Wreckage.p.jdField_z_of_type_Double <= d)
      {
        World.land().N(Wreckage.p.jdField_x_of_type_Double, Wreckage.p.jdField_y_of_type_Double, Wreckage.Nf); Wreckage.N.set(Wreckage.Nf);
        float f4;
        if ((f4 = (float)Wreckage.this.v.dot(Wreckage.N)) < 0.0F) {
          if (f4 < -40.0F) f4 = -40.0F;
          Wreckage.N.scale(2.0F * f4);
          Wreckage.this.v.sub(Wreckage.N);
          Wreckage.this.v.scale(0.5D);
          if (World.land().isWater(Wreckage.p.jdField_x_of_type_Double, Wreckage.p.jdField_y_of_type_Double)) {
            MsgDestroy.Post(Time.current(), this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
            Wreckage.access$1000(Wreckage.p);
            return false;
          }
          if ((!Wreckage.this.bBoundToBeDestroyed) && (Math.abs(Wreckage.this.v.jdField_z_of_type_Double) < 1.0D)) {
            MsgDestroy.Post(Time.current() + 80000L, this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
            Wreckage.access$1102(Wreckage.this, true);
            return false;
          }
        }
        Wreckage.p.jdField_z_of_type_Double = d;
      }
      Wreckage.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(Wreckage.p, Wreckage.o);
      return true;
    }
  }
}