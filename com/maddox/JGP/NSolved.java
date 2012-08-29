// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NSolved.java

package com.maddox.JGP;

import java.io.PrintStream;

// Referenced classes of package com.maddox.JGP:
//            JGPException, Point2d, Point3d

public class NSolved
{

    public NSolved()
    {
    }

    private static final int lu(double ad[][], int ai[])
    {
        int j1 = ad[0].length;
        int i2 = 0;
        for(int i = 0; i < j1; i++)
            ai[i] = i;

        for(int i1 = 0; i1 < j1; i1++)
        {
            int j = i1;
            int k1 = i1;
            double d = 0.0D;
            for(; j < j1; j++)
            {
                double d1 = java.lang.Math.abs(ad[ai[j]][i1]);
                if(d1 > d)
                {
                    d = d1;
                    k1 = j;
                }
            }

            if(i1 != k1)
            {
                i2++;
                int l1 = ai[i1];
                ai[i1] = ai[k1];
                ai[k1] = l1;
            }
            for(int k = i1 + 1; k < j1; k++)
            {
                ad[ai[k]][i1] /= ad[ai[i1]][i1];
                for(int l = i1 + 1; l < j1; l++)
                    ad[ai[k]][l] -= ad[ai[k]][i1] * ad[ai[i1]][l];

            }

        }

        return i2;
    }

    private static final void backsubs1(double ad[][], double ad1[], double ad2[], int ai[])
    {
        int i1 = ad[0].length;
        for(int k = 0; k < i1; k++)
        {
            for(int i = k + 1; i < i1; i++)
                ad1[ai[i]] -= ad[ai[i]][k] * ad1[ai[k]];

        }

        ad2[i1 - 1] = ad1[ai[i1 - 1]] / ad[ai[i1 - 1]][i1 - 1];
        for(int l = i1 - 2; l >= 0; l--)
        {
            double d = 0.0D;
            for(int j = l + 1; j < i1; j++)
                d += ad[ai[l]][j] * ad2[j];

            ad2[l] = (ad1[ai[l]] - d) / ad[ai[l]][l];
        }

    }

    public static final double[] nsolve(int i, double ad[])
    {
        double ad1[][] = new double[i][i];
        double ad2[] = new double[i];
        double ad3[] = new double[i];
        int ai[] = new int[i];
        int l;
        for(int k = l = 0; k < i; k++)
        {
            for(int j = 0; j < i;)
            {
                ad1[k][j] = ad[l];
                j++;
                l++;
            }

            ad2[k] = ad[l++];
            ad3[k] = 0.0D;
        }

        com.maddox.JGP.NSolved.lu(ad1, ai);
        com.maddox.JGP.NSolved.backsubs1(ad1, ad2, ad3, ai);
        return ad3;
    }

    public static final double[] Solve(double ad[][], double ad1[])
        throws com.maddox.JGP.JGPException
    {
        if(ad.length != ad[0].length && ad.length != ad1.length)
            throw new JGPException("Invalid matrix size");
        int i = ad.length;
        double ad4[] = new double[i];
        int ai[] = new int[i];
        double ad2[][] = (double[][])ad.clone();
        double ad3[] = (double[])ad1.clone();
        boolean flag;
        for(int j = 0; flag = false; j < i; j++)
            ad4[j] = 0.0D;

        com.maddox.JGP.NSolved.lu(ad2, ai);
        com.maddox.JGP.NSolved.backsubs1(ad2, ad3, ad4, ai);
        return ad4;
    }

    public static com.maddox.JGP.Point2d Solve2d(double ad[])
        throws com.maddox.JGP.JGPException
    {
        return new Point2d(com.maddox.JGP.NSolved.nsolve(2, ad));
    }

    public static com.maddox.JGP.Point3d Solve3d(double ad[])
        throws com.maddox.JGP.JGPException
    {
        return new Point3d(com.maddox.JGP.NSolved.nsolve(3, ad));
    }

    public static void main(java.lang.String args[])
    {
        double ad[] = com.maddox.JGP.NSolved.nsolve(4, pd);
        for(int i = 0; i < ad.length; i++)
            java.lang.System.out.print(" " + ad[i]);

        java.lang.System.out.println("\nMust be 1 2 3 4");
    }

    private static double pd[] = {
        6D, 1.0D, 6D, 6D, 50D, 1.0D, 6D, 6D, 0.0D, 31D, 
        0.0D, 3D, 2D, 1.0D, 16D, 8D, 6D, 1.0D, 9D, 59D
    };

}
