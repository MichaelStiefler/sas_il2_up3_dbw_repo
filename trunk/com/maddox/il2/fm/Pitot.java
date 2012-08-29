package com.maddox.il2.fm;

public class Pitot
{
  private static final float[] pitot = { 0.0F, 0.630378F, 0.00632175F, -3.07351E-005F, 4.47977E-008F };

  private static final float poly(float[] paramArrayOfFloat, float paramFloat)
  {
    return (((paramArrayOfFloat[4] * paramFloat + paramArrayOfFloat[3]) * paramFloat + paramArrayOfFloat[2]) * paramFloat + paramArrayOfFloat[1]) * paramFloat + paramArrayOfFloat[0];
  }

  public static final float Indicator(float paramFloat1, float paramFloat2)
  {
    paramFloat2 *= (float)Math.sqrt(Atmosphere.density(paramFloat1) / 1.225F);

    return paramFloat2;
  }
}