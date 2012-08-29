package com.maddox.JGP;

import java.io.PrintStream;

public class NSolved
{
  private static double[] pd = { 6.0D, 1.0D, 6.0D, 6.0D, 50.0D, 1.0D, 6.0D, 6.0D, 0.0D, 31.0D, 0.0D, 3.0D, 2.0D, 1.0D, 16.0D, 8.0D, 6.0D, 1.0D, 9.0D, 59.0D };

  private static final int lu(double[][] paramArrayOfDouble, int[] paramArrayOfInt)
  {
    int m = paramArrayOfDouble[0].length;

    int i2 = 0; for (int i = 0; i < m; i++) paramArrayOfInt[i] = i;

    for (int k = 0; k < m; k++)
    {
      i = k; int n = k; for (double d1 = 0.0D; i < m; i++) {
        double d2 = Math.abs(paramArrayOfDouble[paramArrayOfInt[i]][k]);
        if (d2 <= d1) continue; d1 = d2; n = i;
      }

      if (k != n) {
        i2++;
        int i1 = paramArrayOfInt[k];
        paramArrayOfInt[k] = paramArrayOfInt[n];
        paramArrayOfInt[n] = i1;
      }

      for (i = k + 1; i < m; i++)
      {
        paramArrayOfDouble[paramArrayOfInt[i]][k] /= paramArrayOfDouble[paramArrayOfInt[k]][k];

        for (int j = k + 1; j < m; j++) {
          paramArrayOfDouble[paramArrayOfInt[i]][j] -= paramArrayOfDouble[paramArrayOfInt[i]][k] * paramArrayOfDouble[paramArrayOfInt[k]][j];
        }
      }
    }
    return i2;
  }

  private static final void backsubs1(double[][] paramArrayOfDouble, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int[] paramArrayOfInt)
  {
    int m = paramArrayOfDouble[0].length;

    for (int k = 0; k < m; k++) {
      for (int i = k + 1; i < m; i++)
        paramArrayOfDouble1[paramArrayOfInt[i]] -= paramArrayOfDouble[paramArrayOfInt[i]][k] * paramArrayOfDouble1[paramArrayOfInt[k]];
    }
    paramArrayOfDouble2[(m - 1)] = (paramArrayOfDouble1[paramArrayOfInt[(m - 1)]] / paramArrayOfDouble[paramArrayOfInt[(m - 1)]][(m - 1)]);
    for (k = m - 2; k >= 0; k--) {
      double d = 0.0D;
      for (int j = k + 1; j < m; j++) {
        d += paramArrayOfDouble[paramArrayOfInt[k]][j] * paramArrayOfDouble2[j];
      }
      paramArrayOfDouble2[k] = ((paramArrayOfDouble1[paramArrayOfInt[k]] - d) / paramArrayOfDouble[paramArrayOfInt[k]][k]);
    }
  }

  public static final double[] nsolve(int paramInt, double[] paramArrayOfDouble)
  {
    double[][] arrayOfDouble = new double[paramInt][paramInt]; double[] arrayOfDouble1 = new double[paramInt];
    double[] arrayOfDouble2 = new double[paramInt];

    int[] arrayOfInt = new int[paramInt];
    int k;
    for (int j = k = 0; j < paramInt; j++) {
      for (int i = 0; i < paramInt; k++) { arrayOfDouble[j][i] = paramArrayOfDouble[k]; i++; }
      arrayOfDouble1[j] = paramArrayOfDouble[(k++)];
      arrayOfDouble2[j] = 0.0D;
    }

    lu(arrayOfDouble, arrayOfInt);
    backsubs1(arrayOfDouble, arrayOfDouble1, arrayOfDouble2, arrayOfInt);
    return arrayOfDouble2;
  }

  public static final double[] Solve(double[][] paramArrayOfDouble, double[] paramArrayOfDouble1)
    throws JGPException
  {
    if ((paramArrayOfDouble.length != paramArrayOfDouble[0].length) && (paramArrayOfDouble.length != paramArrayOfDouble1.length)) {
      throw new JGPException("Invalid matrix size");
    }
    int i = paramArrayOfDouble.length;
    double[] arrayOfDouble2 = new double[i];

    int[] arrayOfInt = new int[i];

    double[][] arrayOfDouble = (double[][])paramArrayOfDouble.clone();
    double[] arrayOfDouble1 = (double[])paramArrayOfDouble1.clone();
    int k;
    for (int j = k = 0; j < i; j++) arrayOfDouble2[j] = 0.0D;

    lu(arrayOfDouble, arrayOfInt);
    backsubs1(arrayOfDouble, arrayOfDouble1, arrayOfDouble2, arrayOfInt);
    return arrayOfDouble2;
  }

  public static Point2d Solve2d(double[] paramArrayOfDouble) throws JGPException
  {
    return new Point2d(nsolve(2, paramArrayOfDouble));
  }

  public static Point3d Solve3d(double[] paramArrayOfDouble) throws JGPException
  {
    return new Point3d(nsolve(3, paramArrayOfDouble));
  }

  public static void main(String[] paramArrayOfString)
  {
    double[] arrayOfDouble = nsolve(4, pd);
    for (int i = 0; i < arrayOfDouble.length; i++) {
      System.out.print(" " + arrayOfDouble[i]);
    }
    System.out.println("\nMust be 1 2 3 4");
  }
}