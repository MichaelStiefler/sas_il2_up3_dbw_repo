package com.maddox.il2.ai.ground;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Time;

public class Aiming
{
  public static final int RES_OK = 1;
  public static final int RES_UNREACHABLE_ANGLE = 0;
  public static final int RES_NOT_ENOUGH_TIME = -1;
  public AnglesFork anglesYaw;
  public AnglesFork anglesPitch;
  public long timeTot;
  public long timeCur;
  private static Point3d checkBegPoint = new Point3d();
  private static Point3d checkEndPoint = new Point3d();

  private static final long SecsToTicks(float paramFloat)
  {
    long l = ()(0.5D + paramFloat / Time.tickLenFs());
    return l < 1L ? 1L : l;
  }

  public boolean isInDelayMode() {
    return this.timeTot < 0L;
  }
  public boolean isInAimingMode() {
    return this.timeTot >= 0L;
  }

  public boolean tick()
  {
    return (--this.timeCur <= 0L) || (this.timeTot >= 0L);
  }

  public float t()
  {
    return 1.0F - (float)this.timeCur / (float)this.timeTot;
  }

  public boolean aimedNow()
  {
    return this.timeCur <= 0L;
  }

  public Aiming() {
    this.anglesYaw = new AnglesFork();
    this.anglesPitch = new AnglesFork();
    clear();
  }

  private void clear() {
    setDelayMode(10.0F);
  }

  public void setDelayMode(float paramFloat) {
    this.timeTot = -1L;
    this.timeCur = SecsToTicks(paramFloat);
  }
  public void setDelayMode(double paramDouble) {
    this.timeTot = -1L;
    this.timeCur = SecsToTicks((float)paramDouble);
  }

  public int set(Actor paramActor, Loc paramLoc, float paramFloat1, float paramFloat2, Vector3d paramVector3d, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    clear();
    if (paramFloat4 <= 0.0F) paramFloat4 = 0.0F;

    if (paramFloat3 > 0.0F) {
      Vector3d localVector3d1 = new Vector3d();
      localVector3d1.x = World.Rnd().nextDouble(-1.0D, 1.0D);
      localVector3d1.y = World.Rnd().nextDouble(-1.0D, 1.0D);
      localVector3d1.z = World.Rnd().nextDouble(-1.0D, 1.0D);
      if (localVector3d1.length() > 0.0001D) {
        f2 = Geom.tanDeg(paramFloat3);
        localVector3d1.scale(f2 / localVector3d1.length());
        localVector3d2 = new Vector3d();
        localVector3d2.set(paramVector3d);
        localVector3d2.add(localVector3d1);
        if (localVector3d2.length() > 0.01D) {
          localVector3d2.normalize();
          paramVector3d.set(localVector3d2);
        }

      }

    }

    Vector3d localVector3d2 = new Vector3d();
    paramLoc.transformInv(paramVector3d, localVector3d2);
    Orient localOrient = new Orient();
    localOrient.setAT0(localVector3d2);
    float f1 = localOrient.getYaw();
    AnglesFork localAnglesFork = new AnglesFork(localOrient.getPitch());
    float f2 = localAnglesFork.getSrcDeg();

    if ((f2 < paramFloat5) || (f2 > paramFloat6)) {
      return 0;
    }

    this.anglesYaw.setDeg(paramFloat1, f1);
    this.anglesPitch.setDeg(paramFloat2, f2);

    float f3 = this.anglesYaw.getAbsDiffDeg();
    float f4 = this.anglesPitch.getAbsDiffDeg();
    if ((f3 > 3.0F) && (
      (paramFloat4 < 1.0E-004F) || (f3 / paramFloat4 > paramFloat7)))
    {
      clear();
      return -1;
    }

    if ((f4 > 3.0F) && (
      (paramFloat4 < 1.0E-004F) || (f4 / paramFloat4 > paramFloat8)))
    {
      clear();
      return -1;
    }

    paramLoc.get(checkBegPoint);
    checkEndPoint.set(paramVector3d);
    checkEndPoint.scale(100.0D);
    checkEndPoint.add(checkBegPoint);

    Actor localActor = Engine.collideEnv().getLine(checkBegPoint, checkEndPoint, false, paramActor, null);

    if ((localActor != null) && ((localActor.getArmy() == 0) || (localActor.getArmy() == paramActor.getArmy())))
    {
      clear();
      return 0;
    }

    this.timeTot = (this.timeCur = SecsToTicks(paramFloat4));
    return 1;
  }

  public boolean setAutoTime(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    clear();

    this.anglesYaw.setDeg(paramFloat1, paramFloat3);
    this.anglesPitch.setDeg(paramFloat2, paramFloat4);

    float f1 = this.anglesYaw.getAbsDiffDeg() / paramFloat5;
    float f2 = this.anglesPitch.getAbsDiffDeg() / paramFloat6;

    this.timeTot = (this.timeCur = SecsToTicks(Math.max(f1, f2)));
    return this.timeTot > 1L;
  }
}