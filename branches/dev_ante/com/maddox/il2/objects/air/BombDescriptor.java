package com.maddox.il2.objects.air;

class BombDescriptor
{
  public String sBombName;
  private double[] dHeightCorrectionCoeffs;
  private static double[] dHeights = { 500.0D, 1000.0D, 1500.0D, 2000.0D, 2500.0D, 3000.0D, 3500.0D, 4000.0D, 4500.0D, 5000.0D, 5500.0D, 6000.0D, 6500.0D, 7000.0D, 7500.0D, 8000.0D, 8500.0D };

  private static int nLastIndex = dHeights.length - 1;

  public BombDescriptor(String paramString, double[] paramArrayOfDouble)
  {
    this.sBombName = paramString;
    this.dHeightCorrectionCoeffs = paramArrayOfDouble;
  }

  public double GetCorrectionCoeff(double paramDouble)
  {
    if (paramDouble <= dHeights[0])
      return this.dHeightCorrectionCoeffs[0];
    if (paramDouble >= dHeights[nLastIndex]) {
      return this.dHeightCorrectionCoeffs[nLastIndex];
    }

    for (int i = 0; i <= nLastIndex; i++)
    {
      if (dHeights[i] > paramDouble)
      {
        return this.dHeightCorrectionCoeffs[(i - 1)] + (this.dHeightCorrectionCoeffs[i] - this.dHeightCorrectionCoeffs[(i - 1)]) * ((paramDouble - dHeights[(i - 1)]) / (dHeights[i] - dHeights[(i - 1)]));
      }

    }

    return 0.0D;
  }
}