package com.maddox.gwindow;

public class GSize
{
  public float dx = 0.0F;
  public float dy = 0.0F;

  public void add(float paramFloat1, float paramFloat2) {
    this.dx += paramFloat1;
    this.dy += paramFloat2;
  }
  public void add(GSize paramGSize) {
    this.dx += paramGSize.dx;
    this.dy += paramGSize.dy;
  }

  public void sub(float paramFloat1, float paramFloat2) {
    this.dx -= paramFloat1;
    this.dy -= paramFloat2;
  }
  public void sub(GSize paramGSize) {
    this.dx -= paramGSize.dx;
    this.dy -= paramGSize.dy;
  }

  public void set(float paramFloat1, float paramFloat2) {
    this.dx = paramFloat1;
    this.dy = paramFloat2;
  }

  public void set(GSize paramGSize) {
    this.dx = paramGSize.dx;
    this.dy = paramGSize.dy;
  }
  public GSize() {
  }
  public GSize(float paramFloat1, float paramFloat2) {
    set(paramFloat1, paramFloat2);
  }
  public GSize(GSize paramGSize) {
    set(paramGSize);
  }
}