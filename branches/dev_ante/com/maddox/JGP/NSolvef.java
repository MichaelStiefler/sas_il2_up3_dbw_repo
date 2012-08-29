package com.maddox.JGP;

import java.io.PrintStream;

public class NSolvef
{
  private static float[] pd = { 6.0F, 1.0F, 6.0F, 6.0F, 50.0F, 1.0F, 6.0F, 6.0F, 0.0F, 31.0F, 0.0F, 3.0F, 2.0F, 1.0F, 16.0F, 8.0F, 6.0F, 1.0F, 9.0F, 59.0F };

  private static final int lu(float[][] paramArrayOfFloat, int[] paramArrayOfInt)
  {
    int m = paramArrayOfFloat[0].length;

    int i2 = 0; for (int i = 0; i < m; i++) paramArrayOfInt[i] = i;

    for (int k = 0; k < m; k++)
    {
      i = k; int n = k; for (float f1 = 0.0F; i < m; i++) {
        float f2 = Math.abs(paramArrayOfFloat[paramArrayOfInt[i]][k]);
        if (f2 <= f1) continue; f1 = f2; n = i;
      }

      if (k != n) {
        i2++;
        int i1 = paramArrayOfInt[k];
        paramArrayOfInt[k] = paramArrayOfInt[n];
        paramArrayOfInt[n] = i1;
      }

      for (i = k + 1; i < m; i++)
      {
        paramArrayOfFloat[paramArrayOfInt[i]][k] /= paramArrayOfFloat[paramArrayOfInt[k]][k];

        for (int j = k + 1; j < m; j++) {
          paramArrayOfFloat[paramArrayOfInt[i]][j] -= paramArrayOfFloat[paramArrayOfInt[i]][k] * paramArrayOfFloat[paramArrayOfInt[k]][j];
        }
      }
    }
    return i2;
  }

  private static final void backsubs1(float[][] paramArrayOfFloat, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int[] paramArrayOfInt)
  {
    int m = paramArrayOfFloat[0].length;

    for (int k = 0; k < m; k++) {
      for (int i = k + 1; i < m; i++)
        paramArrayOfFloat1[paramArrayOfInt[i]] -= paramArrayOfFloat[paramArrayOfInt[i]][k] * paramArrayOfFloat1[paramArrayOfInt[k]];
    }
    paramArrayOfFloat2[(m - 1)] = (paramArrayOfFloat1[paramArrayOfInt[(m - 1)]] / paramArrayOfFloat[paramArrayOfInt[(m - 1)]][(m - 1)]);
    for (k = m - 2; k >= 0; k--) {
      float f = 0.0F;
      for (int j = k + 1; j < m; j++) {
        f += paramArrayOfFloat[paramArrayOfInt[k]][j] * paramArrayOfFloat2[j];
      }
      paramArrayOfFloat2[k] = ((paramArrayOfFloat1[paramArrayOfInt[k]] - f) / paramArrayOfFloat[paramArrayOfInt[k]][k]);
    }
  }

  public static final float[] nsolve(int paramInt, float[] paramArrayOfFloat)
  {
    float[][] arrayOfFloat = new float[paramInt][paramInt]; float[] arrayOfFloat1 = new float[paramInt];
    float[] arrayOfFloat2 = new float[paramInt];

    int[] arrayOfInt = new int[paramInt];
    int k;
    for (int j = k = 0; j < paramInt; j++) {
      for (int i = 0; i < paramInt; k++) { arrayOfFloat[j][i] = paramArrayOfFloat[k]; i++; }
      arrayOfFloat1[j] = paramArrayOfFloat[(k++)];
      arrayOfFloat2[j] = 0.0F;
    }

    lu(arrayOfFloat, arrayOfInt);
    backsubs1(arrayOfFloat, arrayOfFloat1, arrayOfFloat2, arrayOfInt);
    return arrayOfFloat2;
  }

  public static final float[] Solve(float[][] paramArrayOfFloat, float[] paramArrayOfFloat1)
    throws JGPException
  {
    if ((paramArrayOfFloat.length != paramArrayOfFloat[0].length) && (paramArrayOfFloat.length != paramArrayOfFloat1.length)) {
      throw new JGPException("Invalid matrix size");
    }
    int i = paramArrayOfFloat.length;
    float[] arrayOfFloat2 = new float[i];

    int[] arrayOfInt = new int[i];

    float[][] arrayOfFloat = (float[][])paramArrayOfFloat.clone();
    float[] arrayOfFloat1 = (float[])paramArrayOfFloat1.clone();
    int k;
    for (int j = k = 0; j < i; j++) arrayOfFloat2[j] = 0.0F;

    lu(arrayOfFloat, arrayOfInt);
    backsubs1(arrayOfFloat, arrayOfFloat1, arrayOfFloat2, arrayOfInt);
    return arrayOfFloat2;
  }

  public static Point2f Solve2f(float[] paramArrayOfFloat) throws JGPException
  {
    return new Point2f(nsolve(2, paramArrayOfFloat));
  }

  public static Point3f Solve3f(float[] paramArrayOfFloat) throws JGPException
  {
    return new Point3f(nsolve(3, paramArrayOfFloat));
  }

  public static void main(String[] paramArrayOfString)
  {
    float[] arrayOfFloat = nsolve(4, pd);
    for (int i = 0; i < arrayOfFloat.length; i++) {
      System.out.print(" " + arrayOfFloat[i]);
    }
    System.out.println("\nMust be 1 2 3 4");
  }
}