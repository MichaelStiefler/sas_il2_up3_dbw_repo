package com.maddox.il2.ai;

import java.util.Random;

public class RangeRandom extends Random
{
  private int countAccess = 0;

  public RangeRandom(long paramLong)
  {
    super(paramLong);
  }

  public RangeRandom()
  {
    super(1L);
  }

  public int nextInt(int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1;
    if (i < 0) i = -i;
    return paramInt1 + nextInt(i + 1);
  }

  public long nextLong(long paramLong1, long paramLong2)
  {
    long l = paramLong2 - paramLong1;
    if (l < 0L) l = -l;
    return paramLong1 + nextLong() % (l + 1L);
  }

  public double nextDouble(double paramDouble1, double paramDouble2) {
    return paramDouble1 + (paramDouble2 - paramDouble1) * nextDouble();
  }

  public float nextFloat(float paramFloat1, float paramFloat2)
  {
    return paramFloat1 + (paramFloat2 - paramFloat1) * nextFloat();
  }

  public float nextFloat_Dome(float paramFloat1, float paramFloat2)
  {
    float f1 = nextFloat();
    float f2 = nextFloat();
    float f3 = nextFloat();
    float f4 = (f1 + f2 + f3) * 0.3333333F;
    return paramFloat1 + (paramFloat2 - paramFloat1) * f4;
  }

  public float nextFloat_DomeInv(float paramFloat1, float paramFloat2)
  {
    float f1 = nextFloat();
    float f2 = nextFloat();
    float f3 = nextFloat();
    float f4 = (f1 + f2 + f3) * 0.3333333F;
    if (f4 >= 0.5F)
      f4 -= 0.5F;
    else {
      f4 += 0.5F;
    }
    return paramFloat1 + (paramFloat2 - paramFloat1) * f4;
  }

  public float nextFloat_Fade(float paramFloat1, float paramFloat2)
  {
    float f1 = nextFloat();
    float f2 = nextFloat();
    float f3 = f1 * f2;
    return paramFloat1 + (paramFloat2 - paramFloat1) * f3;
  }

  public int countAccess()
  {
    return this.countAccess;
  }

  protected synchronized int next(int paramInt)
  {
    this.countAccess += 1;
    return super.next(paramInt);
  }
}