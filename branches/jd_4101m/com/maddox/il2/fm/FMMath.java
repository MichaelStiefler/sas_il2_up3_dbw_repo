package com.maddox.il2.fm;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Interpolate;
import com.maddox.rts.Time;

public class FMMath extends Interpolate
{
  public static final float PI = 3.141593F;

  public boolean isTick(int paramInt1, int paramInt2)
  {
    if (this.actor == null) return false;
    return (Time.tickCounter() + this.actor.hashCode()) % paramInt1 == paramInt2;
  }

  public static final float DEG2RAD(float paramFloat) {
    return paramFloat * 0.01745329F; } 
  public static final float RAD2DEG(float paramFloat) { return paramFloat * 57.295776F; } 
  public static final double DEG2RAD(double paramDouble) { return paramDouble * 0.0174532925199433D; } 
  public static final double RAD2DEG(double paramDouble) { return paramDouble * 57.295779513082323D; }

  public static final float hypot(float paramFloat1, float paramFloat2) {
    return (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
  }
  public static final double hypot(double paramDouble1, double paramDouble2) {
    return Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2);
  }
  public static final float hypot(float paramFloat1, float paramFloat2, float paramFloat3) {
    return (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2 + paramFloat3 * paramFloat3);
  }
  public static final double hypot(double paramDouble1, double paramDouble2, double paramDouble3) {
    return Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2 + paramDouble3 * paramDouble3);
  }

  public static final float interpolate(float paramFloat1, float paramFloat2, float paramFloat3) {
    return paramFloat1 + (paramFloat2 - paramFloat1) * paramFloat3;
  }

  public static final float sqrta(float paramFloat) {
    if (paramFloat >= 0.0F) return (float)Math.sqrt(paramFloat);
    return -(float)Math.sqrt(-paramFloat);
  }

  public static final boolean isNAN(float paramFloat) {
    return (paramFloat >= 1.0F) && (paramFloat <= 0.0F);
  }

  public static final boolean isNAN(double paramDouble) {
    return (paramDouble >= 1.0D) && (paramDouble <= 0.0D);
  }

  public static final boolean isNAN(Tuple3f paramTuple3f) {
    return (isNAN(paramTuple3f.x)) || (isNAN(paramTuple3f.y)) || (isNAN(paramTuple3f.z));
  }
  public static final boolean isNAN(Tuple3d paramTuple3d) {
    return (isNAN(paramTuple3d.x)) || (isNAN(paramTuple3d.y)) || (isNAN(paramTuple3d.z));
  }

  public static final float clamp(float paramFloat1, float paramFloat2, float paramFloat3) {
    return Math.min(paramFloat3, Math.max(paramFloat2, paramFloat1));
  }

  public static final float positiveSquareEquation(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = paramFloat2 * paramFloat2 - 4.0F * paramFloat1 * paramFloat3;
    if (f1 < 0.0F) return -1.0F;
    f1 = (float)Math.sqrt(f1);
    float f2 = (-paramFloat2 + f1) / (2.0F * paramFloat1);
    if (f2 > 0.0F) return f2;
    f2 = (-paramFloat2 - f1) / (2.0F * paramFloat1);
    if (f2 > 0.0F) return f2;
    return -1.0F;
  }

  public static final double positiveSquareEquation(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d1 = paramDouble2 * paramDouble2 - 4.0D * paramDouble1 * paramDouble3;
    if (d1 < 0.0D) return -1.0D;
    d1 = Math.sqrt(d1);
    double d2 = (-paramDouble2 + d1) / (2.0D * paramDouble1);
    if (d2 > 0.0D) return d2;
    d2 = (-paramDouble2 - d1) / (2.0D * paramDouble1);
    if (d2 > 0.0D) return d2;
    return -1.0D;
  }
}