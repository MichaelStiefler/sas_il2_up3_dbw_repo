package com.maddox.il2.engine;

public abstract class Animate
{
  public String name;
  public int frame0;
  public int frame1;
  public long time;
  public boolean bLandNormal = false;

  public boolean b2Frames = false;

  protected static Loc locRes = new Loc();

  private static Loc locNull = new Loc();
  private static Loc locHook = new Loc();
  private static HookOnLandNormal hookOnLandNormal = new HookOnLandNormal(0.0D);

  public abstract double setup(Animator paramAnimator, Loc paramLoc, double paramDouble1, double paramDouble2, double paramDouble3);

  public abstract double fullStepLen(Animator paramAnimator, double paramDouble);

  public abstract void fullStep(Animator paramAnimator, Loc paramLoc, double paramDouble);

  public abstract void step(Animator paramAnimator, Loc paramLoc, Hook paramHook, double paramDouble1, double paramDouble2);

  protected void step(Mesh paramMesh, double paramDouble)
  {
    if (this.b2Frames) {
      paramMesh.setFrame(this.frame0, this.frame1, (float)paramDouble);
    } else {
      int i = this.frame0;
      int j = this.frame1;
      if (i == j) {
        paramMesh.setFrame(i);
      } else if (i > j) {
        k = i; i = j; j = k;
      }
      int k = j - i + 1;
      double d = 1.0D / k;
      if (paramDouble > 1.0D - d)
        paramMesh.setFrame(j, i, (float)((paramDouble - (1.0D - d)) / d));
      else
        paramMesh.setFrameFromRange(i, j, (float)(paramDouble / (1.0D - d)));
    }
  }

  protected void setPos(Actor paramActor, Hook paramHook)
  {
    double d1 = 0.0D;
    if (paramHook == null) {
      d1 = paramActor.collisionR();
    } else {
      locHook.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
      paramHook.computePos(paramActor, locNull, locHook);
      d1 = -locHook.getZ();
    }
    if (this.bLandNormal) {
      hookOnLandNormal.dz = d1;
      hookOnLandNormal.computePos(paramActor, locNull, locRes);
    } else {
      double d2 = Engine.land().HQ(locRes.getX(), locRes.getY());
      locRes.getPoint().z = (d2 + d1);
      locRes.getOrient().set(locRes.getOrient().getAzimut(), 0.0F, 0.0F);
    }
    paramActor.pos.setRel(locRes);
  }

  public void set(String paramString, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.name = paramString;
    this.frame0 = paramInt1;
    this.frame1 = paramInt2;
    this.time = Math.round(paramFloat * 1000.0F);
    this.bLandNormal = paramBoolean1;
    this.b2Frames = paramBoolean2;
  }

  public Animate(String paramString, int paramInt1, int paramInt2, float paramFloat) {
    set(paramString, paramInt1, paramInt2, paramFloat, false, false);
  }

  public Animate(String paramString, int paramInt1, int paramInt2, float paramFloat, boolean paramBoolean1, boolean paramBoolean2) {
    set(paramString, paramInt1, paramInt2, paramFloat, paramBoolean1, paramBoolean2);
  }
}