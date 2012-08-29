// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NSolvef.java

package com.maddox.JGP;

import java.io.PrintStream;

// Referenced classes of package com.maddox.JGP:
//            JGPException, Point2f, Point3f

public class NSolvef
{

    public NSolvef()
    {
    }

    private static final int lu(float af[][], int ai[])
    {
        int j1 = af[0].length;
        int i2 = 0;
        for(int i = 0; i < j1; i++)
            ai[i] = i;

        for(int i1 = 0; i1 < j1; i1++)
        {
            int j = i1;
            int k1 = i1;
            float f = 0.0F;
            for(; j < j1; j++)
            {
                float f1 = java.lang.Math.abs(af[ai[j]][i1]);
                if(f1 > f)
                {
                    f = f1;
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
                af[ai[k]][i1] /= af[ai[i1]][i1];
                for(int l = i1 + 1; l < j1; l++)
                    af[ai[k]][l] -= af[ai[k]][i1] * af[ai[i1]][l];

            }

        }

        return i2;
    }

    private static final void backsubs1(float af[][], float af1[], float af2[], int ai[])
    {
        int i1 = af[0].length;
        for(int k = 0; k < i1; k++)
        {
            for(int i = k + 1; i < i1; i++)
                af1[ai[i]] -= af[ai[i]][k] * af1[ai[k]];

        }

        af2[i1 - 1] = af1[ai[i1 - 1]] / af[ai[i1 - 1]][i1 - 1];
        for(int l = i1 - 2; l >= 0; l--)
        {
            float f = 0.0F;
            for(int j = l + 1; j < i1; j++)
                f += af[ai[l]][j] * af2[j];

            af2[l] = (af1[ai[l]] - f) / af[ai[l]][l];
        }

    }

    public static final float[] nsolve(int i, float af[])
    {
        float af1[][] = new float[i][i];
        float af2[] = new float[i];
        float af3[] = new float[i];
        int ai[] = new int[i];
        int l;
        for(int k = l = 0; k < i; k++)
        {
            for(int j = 0; j < i;)
            {
                af1[k][j] = af[l];
                j++;
                l++;
            }

            af2[k] = af[l++];
            af3[k] = 0.0F;
        }

        com.maddox.JGP.NSolvef.lu(af1, ai);
        com.maddox.JGP.NSolvef.backsubs1(af1, af2, af3, ai);
        return af3;
    }

    public static final float[] Solve(float af[][], float af1[])
        throws com.maddox.JGP.JGPException
    {
        if(af.length != af[0].length && af.length != af1.length)
            throw new JGPException("Invalid matrix size");
        int i = af.length;
        float af4[] = new float[i];
        int ai[] = new int[i];
        float af2[][] = (float[][])af.clone();
        float af3[] = (float[])af1.clone();
        boolean flag;
        for(int j = 0; flag = false; j < i; j++)
            af4[j] = 0.0F;

        com.maddox.JGP.NSolvef.lu(af2, ai);
        com.maddox.JGP.NSolvef.backsubs1(af2, af3, af4, ai);
        return af4;
    }

    public static com.maddox.JGP.Point2f Solve2f(float af[])
        throws com.maddox.JGP.JGPException
    {
        return new Point2f(com.maddox.JGP.NSolvef.nsolve(2, af));
    }

    public static com.maddox.JGP.Point3f Solve3f(float af[])
        throws com.maddox.JGP.JGPException
    {
        return new Point3f(com.maddox.JGP.NSolvef.nsolve(3, af));
    }

    public static void main(java.lang.String args[])
    {
        float af[] = com.maddox.JGP.NSolvef.nsolve(4, pd);
        for(int i = 0; i < af.length; i++)
            java.lang.System.out.print(" " + af[i]);

        java.lang.System.out.println("\nMust be 1 2 3 4");
    }

    private static float pd[] = {
        6F, 1.0F, 6F, 6F, 50F, 1.0F, 6F, 6F, 0.0F, 31F, 
        0.0F, 3F, 2.0F, 1.0F, 16F, 8F, 6F, 1.0F, 9F, 59F
    };

}
