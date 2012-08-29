package com.maddox.JGP;

public class Geom
{
  public static final float PI = 3.141593F;

  public static final float DEG2RAD(float paramFloat)
  {
    return paramFloat * 0.01745329F; } 
  public static final float RAD2DEG(float paramFloat) { return paramFloat * 57.295776F; }

  public static final float sin(float paramFloat) {
    return (float)Math.sin(paramFloat);
  }
  public static final float sinDeg(float paramFloat) {
    return (float)Math.sin(DEG2RAD(paramFloat));
  }

  public static final float cos(float paramFloat) {
    return (float)Math.cos(paramFloat);
  }
  public static final float cosDeg(float paramFloat) {
    return (float)Math.cos(DEG2RAD(paramFloat));
  }

  public static final float tan(float paramFloat) {
    return (float)Math.tan(paramFloat);
  }
  public static final float tanDeg(float paramFloat) {
    return (float)Math.tan(DEG2RAD(paramFloat));
  }

  public static final float interpolate(float paramFloat1, float paramFloat2, float paramFloat3) {
    return paramFloat1 + (paramFloat2 - paramFloat1) * paramFloat3;
  }
}