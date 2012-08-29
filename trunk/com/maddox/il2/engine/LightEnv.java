package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

public abstract class LightEnv
{
  private Sun sun;

  public Sun sun()
  {
    return this.sun; } 
  public void setSun(Sun paramSun) { this.sun = paramSun; }

  public int prepareForRender(Point3d paramPoint3d, float paramFloat) {
    return 0;
  }
  protected void changedPos(LightPoint paramLightPoint, double paramDouble1, double paramDouble2, double paramDouble3) {
  }
  protected void changedEmit(LightPoint paramLightPoint, float paramFloat1, float paramFloat2) {
  }
  protected void add(LightPoint paramLightPoint) {
  }
  protected void remove(LightPoint paramLightPoint) {
  }
  public void clear() {
  }

  public LightEnv() {
    this.sun = new Sun();
  }
  public LightEnv(Sun paramSun) {
    this.sun = paramSun;
  }

  protected void activate()
  {
    this.sun.activate();
  }
}