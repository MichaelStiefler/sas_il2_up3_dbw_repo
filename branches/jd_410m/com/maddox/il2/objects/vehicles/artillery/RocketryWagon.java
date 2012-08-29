package com.maddox.il2.objects.vehicles.artillery;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Message;
import com.maddox.rts.Time;

public class RocketryWagon extends ActorMesh
{
  private static final int STA_HELL = -2;
  private static final int STA_WAIT = -1;
  private static final int STA_RAMP = 0;
  private static final int STA_FALL = 1;
  private int sta = -1;

  private RocketryGeneric.TrajSeg[] traj = null;

  private RocketryRocket rocket = null;
  long timeOfStartMS;
  private long countdownTicks = 0L;

  private static Point3d tmpP = new Point3d();
  private static Vector3d tmpV = new Vector3d();
  private static Vector3d tmpV3d0 = new Vector3d();
  private static Vector3d tmpV3d1 = new Vector3d();
  private static Loc tmpL = new Loc();

  void silentDeath()
  {
    this.rocket = null;
    destroy();
  }

  void forgetRocket() {
    this.rocket = null;
  }

  private static final long SecsToTicks(float paramFloat) {
    long l = ()(0.5D + paramFloat / Time.tickLenFs());
    return l < 1L ? 1L : l;
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  private int chooseTrajectorySegment(float paramFloat)
  {
    for (int i = 0; i < this.traj.length; i++) {
      if (paramFloat < this.traj[i].t0) {
        return i - 1;
      }
    }
    if (paramFloat < this.traj[(i - 1)].t0 + this.traj[(i - 1)].t) {
      return i - 1;
    }
    return -2;
  }

  private void computeCurLoc(int paramInt, float paramFloat, Loc paramLoc)
  {
    if (paramInt < 0) {
      paramLoc.getPoint().set(this.traj[0].pos0);
      if (this.traj[0].v0.lengthSquared() > 0.0D)
        paramLoc.getOrient().setAT0(this.traj[0].v0);
      else {
        paramLoc.getOrient().setAT0(this.traj[0].a);
      }
      return;
    }

    paramFloat = (float)(paramFloat - this.traj[paramInt].t0);

    tmpV3d0.scale(this.traj[paramInt].v0, paramFloat);
    tmpV3d1.scale(this.traj[paramInt].a, paramFloat * paramFloat * 0.5D);
    tmpV3d0.add(tmpV3d1);
    tmpV3d0.add(this.traj[paramInt].pos0);
    paramLoc.getPoint().set(tmpV3d0);

    tmpV3d0.scale(this.traj[paramInt].a, paramFloat);
    tmpV3d0.add(this.traj[paramInt].v0);
    if (tmpV3d0.lengthSquared() <= 0.0D) {
      tmpV3d0.set(this.traj[paramInt].a);
    }
    paramLoc.getOrient().setAT0(tmpV3d0);
  }

  private void advanceState(int paramInt1, int paramInt2)
  {
    this.sta = paramInt1;

    while (this.sta < paramInt2) {
      this.sta += 1;

      switch (this.sta) {
      case 0:
        if (this.rocket != null) {
          this.rocket.forgetWagon();
          this.rocket = null;
        }
        Eff3DActor.New(this, null, null, 1.0F, "3DO/Effects/Tracers/GunpowderRocket/rocket.eff", -1.0F);
      case 1:
      }

    }

    this.sta = paramInt2;
  }

  public RocketryWagon(RocketryRocket paramRocketryRocket, String paramString, long paramLong1, long paramLong2, RocketryGeneric.TrajSeg[] paramArrayOfTrajSeg)
  {
    super(paramString);
    this.rocket = paramRocketryRocket;

    this.traj = paramArrayOfTrajSeg;
    this.timeOfStartMS = paramLong1;

    setArmy(0);
    this.sta = -1;

    float f = (float)(paramLong2 - this.timeOfStartMS) * 0.001F;
    int i = chooseTrajectorySegment(f);

    if (i == -2)
    {
      this.rocket = null;
      collide(false);
      drawing(false);
      return;
    }

    collide(false);
    drawing(true);

    if (this.sta != i) {
      advanceState(this.sta, i);
    }

    computeCurLoc(this.sta, f, tmpL);
    this.pos.setAbs(tmpL);
    this.pos.reset();

    if (!interpEnd("move"))
      interpPut(new Move(), "move", paramLong2, null); 
  }

  class Move extends Interpolate {
    Move() {
    }

    private void disappear() {
      if (RocketryWagon.this.rocket != null) {
        RocketryWagon.this.rocket.forgetWagon();
        RocketryWagon.access$002(RocketryWagon.this, null);
      }
      RocketryWagon.this.collide(false);
      RocketryWagon.this.drawing(false);
      RocketryWagon.this.postDestroy();
    }

    public boolean tick()
    {
      if (RocketryWagon.this.sta == -2) {
        if (RocketryWagon.access$206(RocketryWagon.this) > 0L) {
          return true;
        }
        disappear();
        return false;
      }

      long l = Time.current();
      float f = (float)(l - RocketryWagon.this.timeOfStartMS) * 0.001F;

      int i = RocketryWagon.this.chooseTrajectorySegment(f);
      if (RocketryWagon.this.sta != i) {
        if (i == -2)
        {
          disappear();
          return false;
        }

        RocketryWagon.this.advanceState(RocketryWagon.this.sta, i);
      }

      RocketryWagon.this.computeCurLoc(RocketryWagon.this.sta, f, RocketryWagon.tmpL);

      if ((RocketryWagon.this.sta != 1) || 
        (RocketryWagon.this.sta == 1))
      {
        double d = Engine.land().HQ_Air(RocketryWagon.tmpL.getPoint().x, RocketryWagon.tmpL.getPoint().y);
        if (RocketryWagon.tmpL.getPoint().z <= d) {
          if (Engine.land().isWater(RocketryWagon.tmpL.getPoint().x, RocketryWagon.tmpL.getPoint().y)) {
            Explosions.WreckageDrop_Water(RocketryWagon.tmpL.getPoint());
            RocketryWagon.access$202(RocketryWagon.this, 0L);
          } else {
            RocketryWagon.tmpL.getPoint().z = d;
            RocketryWagon.access$202(RocketryWagon.this, RocketryWagon.access$700(World.Rnd().nextFloat(15.0F, 25.0F)));
          }
          RocketryWagon.access$102(RocketryWagon.this, -2);
        }
      }

      RocketryWagon.this.pos.setAbs(RocketryWagon.tmpL);

      return true;
    }
  }
}