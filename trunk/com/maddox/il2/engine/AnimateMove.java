package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

public class AnimateMove extends Animate
{
  public float len;
  private static Loc locRel = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);

  public double setup(Animator paramAnimator, Loc paramLoc, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    Point3d localPoint3d = paramLoc.getPoint();
    float f = (float)(-Math.atan2(paramDouble2 - localPoint3d.y, paramDouble1 - localPoint3d.x) * 180.0D / 3.141592653589793D);
    paramLoc.getOrient().set(f, paramLoc.getTangage(), paramLoc.getKren());
    return Math.sqrt((paramDouble1 - localPoint3d.x) * (paramDouble1 - localPoint3d.x) + (paramDouble2 - localPoint3d.y) * (paramDouble2 - localPoint3d.y)) * paramDouble3;
  }

  public double fullStepLen(Animator paramAnimator, double paramDouble) {
    return this.len * paramDouble;
  }

  public void fullStep(Animator paramAnimator, Loc paramLoc, double paramDouble) {
    paramLoc.getPoint().z = 0.0D;
    locRel.getPoint().x = fullStepLen(paramAnimator, paramDouble);
    locRes.add(locRel, paramLoc);
    paramLoc.set(locRes);
  }

  public void step(Animator paramAnimator, Loc paramLoc, Hook paramHook, double paramDouble1, double paramDouble2)
  {
    paramLoc.getPoint().z = 0.0D;
    locRel.getPoint().x = (fullStepLen(paramAnimator, paramDouble1) * paramDouble2);
    locRes.add(locRel, paramLoc);
    Actor localActor = paramAnimator.actor;
    step(((AnimatedActor)localActor).getAnimatedMesh(), paramDouble2);
    setPos(localActor, paramHook);
  }

  public AnimateMove(String paramString, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2) {
    super(paramString, paramInt1, paramInt2, paramFloat1, false, false);
    this.len = paramFloat2;
  }

  public AnimateMove(String paramString, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramString, paramInt1, paramInt2, paramFloat1, paramBoolean1, paramBoolean2);
    this.len = paramFloat2;
  }
}