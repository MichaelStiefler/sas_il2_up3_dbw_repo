package com.maddox.gwindow;

public class GPoint
{
  public float x = 0.0F;
  public float y = 0.0F;

  public void add(float paramFloat1, float paramFloat2) {
    this.x += paramFloat1;
    this.y += paramFloat2;
  }
  public void add(GSize paramGSize) {
    this.x += paramGSize.dx;
    this.y += paramGSize.dy;
  }

  public void sub(float paramFloat1, float paramFloat2) {
    this.x -= paramFloat1;
    this.y -= paramFloat2;
  }

  public void sub(GSize paramGSize) {
    this.x -= paramGSize.dx;
    this.y -= paramGSize.dy;
  }

  public void size(GPoint paramGPoint, GSize paramGSize)
  {
    paramGSize.dx = (this.x - paramGPoint.x);
    paramGSize.dy = (this.y - paramGPoint.y);
  }

  public void set(float paramFloat1, float paramFloat2) {
    this.x = paramFloat1;
    this.y = paramFloat2;
  }

  public void set(GPoint paramGPoint) {
    this.x = paramGPoint.x;
    this.y = paramGPoint.y;
  }
  public GPoint() {
  }
  public GPoint(float paramFloat1, float paramFloat2) {
    set(paramFloat1, paramFloat2);
  }
  public GPoint(GPoint paramGPoint) {
    set(paramGPoint);
  }
}