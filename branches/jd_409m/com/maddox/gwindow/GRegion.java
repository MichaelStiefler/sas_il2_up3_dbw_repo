package com.maddox.gwindow;

import com.maddox.rts.ObjIO;

public class GRegion
{
  public float x = 0.0F;
  public float y = 0.0F;
  public float dx = 0.0F;
  public float dy = 0.0F;

  public void get(GPoint paramGPoint)
  {
    paramGPoint.x = this.x;
    paramGPoint.y = this.y;
  }
  public void get(GSize paramGSize) {
    paramGSize.dx = this.dx;
    paramGSize.dy = this.dy;
  }
  public void get(GPoint paramGPoint, GSize paramGSize) {
    paramGPoint.x = this.x;
    paramGPoint.y = this.y;
    paramGSize.dx = this.dx;
    paramGSize.dy = this.dy;
  }

  public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this.x = paramFloat1;
    this.y = paramFloat2;
    this.dx = paramFloat3;
    this.dy = paramFloat4;
  }

  public void set(GPoint paramGPoint, GSize paramGSize) {
    this.x = paramGPoint.x;
    this.y = paramGPoint.y;
    this.dx = paramGSize.dx;
    this.dy = paramGSize.dy;
  }

  public void set(GRegion paramGRegion) {
    this.x = paramGRegion.x;
    this.y = paramGRegion.y;
    this.dx = paramGRegion.dx;
    this.dy = paramGRegion.dy;
  }
  public GRegion() {
  }
  public GRegion(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    set(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  public GRegion(GPoint paramGPoint, GSize paramGSize) {
    set(paramGPoint, paramGSize);
  }
  public GRegion(GRegion paramGRegion) {
    set(paramGRegion);
  }

  static
  {
    ObjIO.fields(class$com$maddox$gwindow$GRegion, new String[] { "x", "y", "dx", "dy" });
  }
}