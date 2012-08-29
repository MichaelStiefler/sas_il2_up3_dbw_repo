package com.maddox.il2.ai;

import java.io.PrintStream;

public final class AnglesRange
{
  private float minA;
  private float maxA;
  private static AnglesFork tmpfork = new AnglesFork();

  public AnglesRange(float paramFloat1, float paramFloat2)
  {
    set(paramFloat1, paramFloat2);
  }

  public boolean fullcircle() {
    return this.maxA - this.minA >= 360.0F;
  }

  public boolean isInside(float paramFloat) {
    if (fullcircle()) {
      return true;
    }
    return (paramFloat >= this.minA) && (paramFloat <= this.maxA);
  }

  public final void set(float paramFloat1, float paramFloat2) {
    if (paramFloat2 - paramFloat1 >= 360.0F)
    {
      this.minA = -181.0F;
      this.maxA = 181.0F;
    }
    else if ((paramFloat1 <= -360.0F) || (paramFloat2 >= 360.0F)) {
      System.out.println("*** err1: AnglesRange(" + paramFloat1 + "," + paramFloat2 + ")");
      this.minA = 0.0F;
      this.maxA = 0.0F;
    }
    else
    {
      this.minA = paramFloat1;
      this.maxA = paramFloat2;
    }
  }

  public float transformIntoRangeSpace(float paramFloat)
  {
    if (fullcircle())
    {
      if ((paramFloat >= -180.0F) && (paramFloat <= 180.0F))
      {
        return paramFloat;
      }

      return AnglesFork.signedAngleDeg(paramFloat);
    }

    if ((paramFloat >= this.minA) && (paramFloat <= this.maxA))
    {
      return paramFloat;
    }

    float f1 = (this.minA + this.maxA) * 0.5F;
    tmpfork.setDeg(f1, paramFloat);

    float f2 = tmpfork.getDiffDeg();

    return f1 + f2;
  }

  public String toString() {
    return "(" + this.minA + "," + this.maxA + ")";
  }
}