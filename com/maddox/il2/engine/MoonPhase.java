// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MoonPhase.java

package com.maddox.il2.engine;

import java.io.PrintStream;
import java.util.Date;
import java.util.GregorianCalendar;

public class MoonPhase
{

    public MoonPhase()
    {
    }

    private static double fixangle(double d)
    {
        return d - 360D * java.lang.Math.floor(d / 360D);
    }

    private static double dsin(double d)
    {
        return java.lang.Math.sin(java.lang.Math.toRadians(d));
    }

    private static double dcos(double d)
    {
        return java.lang.Math.cos(java.lang.Math.toRadians(d));
    }

    private static double jtime(double d)
    {
        d /= 1000D;
        double d1 = d / 86400D + 2440587.5D;
        return d1;
    }

    private static double jdaytosecs(double d)
    {
        return (d - 2440587.5D) * 86400D;
    }

    private static int[] jyear(double d)
    {
        d += 0.5D;
        double d1 = java.lang.Math.floor(d);
        double d2 = d - d1;
        int ai[] = new int[3];
        double d3;
        if(d1 < 2299161D)
        {
            d3 = d1;
        } else
        {
            double d4 = java.lang.Math.floor((d1 - 1867216.25D) / 36524.25D);
            d3 = (d1 + 1.0D + d4) - java.lang.Math.floor(d4 / 4D);
        }
        double d5 = d3 + 1524D;
        double d6 = java.lang.Math.floor((d5 - 122.09999999999999D) / 365.25D);
        double d7 = java.lang.Math.floor(365.25D * d6);
        double d8 = java.lang.Math.floor((d5 - d7) / 30.600100000000001D);
        ai[0] = (int)((d5 - d7 - java.lang.Math.floor(30.600100000000001D * d8)) + d2);
        ai[1] = (int)(d8 >= 14D ? d8 - 13D : d8 - 1.0D);
        ai[2] = (int)(ai[1] <= 2 ? d6 - 4715D : d6 - 4716D);
        return ai;
    }

    private static double meanphase(double d, double d1)
    {
        double d2 = (d - 2415020D) / 36525D;
        double d3 = d2 * d2;
        double d4 = d3 * d2;
        return ((2415020.7593299998D + 29.530588680000001D * d1 + 0.0001178D * d3) - 1.55E-007D * d4) + 0.00033D * com.maddox.il2.engine.MoonPhase.dsin((166.56D + 132.87D * d2) - 0.0091730000000000006D * d3);
    }

    private static double truephase(double d, double d1)
    {
        boolean flag = false;
        d += d1;
        double d2 = d / 1236.8499999999999D;
        double d3 = d2 * d2;
        double d4 = d3 * d2;
        double d5 = ((2415020.7593299998D + 29.530588680000001D * d + 0.0001178D * d3) - 1.55E-007D * d4) + 0.00033D * com.maddox.il2.engine.MoonPhase.dsin((166.56D + 132.87D * d2) - 0.0091730000000000006D * d3);
        double d6 = (359.2242D + 29.10535608D * d) - 3.3300000000000003E-005D * d3 - 3.4699999999999998E-006D * d4;
        double d7 = 306.02530000000002D + 385.81691805999998D * d + 0.0107306D * d3 + 1.236E-005D * d4;
        double d8 = (21.296399999999998D + 390.67050646000001D * d) - 0.0016528000000000001D * d3 - 2.39E-006D * d4;
        if(d1 < 0.01D || java.lang.Math.abs(d1 - 0.5D) < 0.01D)
        {
            d5 += (((((((((0.1734D - 0.00039300000000000001D * d2) * com.maddox.il2.engine.MoonPhase.dsin(d6) + 0.0020999999999999999D * com.maddox.il2.engine.MoonPhase.dsin(2D * d6)) - 0.40679999999999999D * com.maddox.il2.engine.MoonPhase.dsin(d7)) + 0.0161D * com.maddox.il2.engine.MoonPhase.dsin(2D * d7)) - 0.00040000000000000002D * com.maddox.il2.engine.MoonPhase.dsin(3D * d7)) + 0.0104D * com.maddox.il2.engine.MoonPhase.dsin(2D * d8)) - 0.0051000000000000004D * com.maddox.il2.engine.MoonPhase.dsin(d6 + d7) - 0.0074000000000000003D * com.maddox.il2.engine.MoonPhase.dsin(d6 - d7)) + 0.00040000000000000002D * com.maddox.il2.engine.MoonPhase.dsin(2D * d8 + d6)) - 0.00040000000000000002D * com.maddox.il2.engine.MoonPhase.dsin(2D * d8 - d6) - 0.00059999999999999995D * com.maddox.il2.engine.MoonPhase.dsin(2D * d8 + d7)) + 0.001D * com.maddox.il2.engine.MoonPhase.dsin(2D * d8 - d7) + 0.00050000000000000001D * com.maddox.il2.engine.MoonPhase.dsin(d6 + 2D * d7);
            flag = true;
        } else
        if(java.lang.Math.abs(d1 - 0.25D) < 0.01D || java.lang.Math.abs(d1 - 0.75D) < 0.01D)
        {
            d5 += ((((((((((0.1721D - 0.00040000000000000002D * d2) * com.maddox.il2.engine.MoonPhase.dsin(d6) + 0.0020999999999999999D * com.maddox.il2.engine.MoonPhase.dsin(2D * d6)) - 0.628D * com.maddox.il2.engine.MoonPhase.dsin(d7)) + 0.0088999999999999999D * com.maddox.il2.engine.MoonPhase.dsin(2D * d7)) - 0.00040000000000000002D * com.maddox.il2.engine.MoonPhase.dsin(3D * d7)) + 0.0079000000000000008D * com.maddox.il2.engine.MoonPhase.dsin(2D * d8)) - 0.011900000000000001D * com.maddox.il2.engine.MoonPhase.dsin(d6 + d7) - 0.0047000000000000002D * com.maddox.il2.engine.MoonPhase.dsin(d6 - d7)) + 0.00029999999999999997D * com.maddox.il2.engine.MoonPhase.dsin(2D * d8 + d6)) - 0.00040000000000000002D * com.maddox.il2.engine.MoonPhase.dsin(2D * d8 - d6) - 0.00059999999999999995D * com.maddox.il2.engine.MoonPhase.dsin(2D * d8 + d7)) + 0.0020999999999999999D * com.maddox.il2.engine.MoonPhase.dsin(2D * d8 - d7) + 0.00029999999999999997D * com.maddox.il2.engine.MoonPhase.dsin(d6 + 2D * d7) + 0.00040000000000000002D * com.maddox.il2.engine.MoonPhase.dsin(d6 - 2D * d7)) - 0.00029999999999999997D * com.maddox.il2.engine.MoonPhase.dsin(2D * d6 + d7);
            if(d1 < 0.5D)
                d5 += (0.0028D - 0.00040000000000000002D * com.maddox.il2.engine.MoonPhase.dcos(d6)) + 0.00029999999999999997D * com.maddox.il2.engine.MoonPhase.dcos(d7);
            else
                d5 += (-0.0028D + 0.00040000000000000002D * com.maddox.il2.engine.MoonPhase.dcos(d6)) - 0.00029999999999999997D * com.maddox.il2.engine.MoonPhase.dcos(d7);
            flag = true;
        }
        if(!flag)
        {
            java.lang.System.out.println("truephase() called with invalid phase selector (phase).");
            java.lang.System.exit(1);
        }
        return d5;
    }

    public static double[] phasehunt(java.util.GregorianCalendar gregoriancalendar)
    {
        double d = com.maddox.il2.engine.MoonPhase.jtime(gregoriancalendar.getTimeInMillis());
        double d1 = d - 45D;
        int ai[] = com.maddox.il2.engine.MoonPhase.jyear(d1);
        int i = ai[0];
        int j = ai[1];
        int k = ai[2];
        double d2 = java.lang.Math.floor((((double)i + (double)(j - 1) * 0.083333333333333329D) - 1900D) * 12.368499999999999D);
        double d4;
        d1 = d4 = com.maddox.il2.engine.MoonPhase.meanphase(d1, d2);
        do
        {
            d1 += 29.530588680000001D;
            double d3 = d2 + 1.0D;
            double d5 = com.maddox.il2.engine.MoonPhase.meanphase(d1, d3);
            if(d4 > d || d5 <= d)
            {
                d4 = d5;
                d2 = d3;
            } else
            {
                double ad[] = {
                    com.maddox.il2.engine.MoonPhase.jdaytosecs(com.maddox.il2.engine.MoonPhase.truephase(d2, 0.0D)), com.maddox.il2.engine.MoonPhase.jdaytosecs(com.maddox.il2.engine.MoonPhase.truephase(d2, 0.25D)), com.maddox.il2.engine.MoonPhase.jdaytosecs(com.maddox.il2.engine.MoonPhase.truephase(d2, 0.5D)), com.maddox.il2.engine.MoonPhase.jdaytosecs(com.maddox.il2.engine.MoonPhase.truephase(d2, 0.75D)), com.maddox.il2.engine.MoonPhase.jdaytosecs(com.maddox.il2.engine.MoonPhase.truephase(d3, 0.0D))
                };
                return ad;
            }
        } while(true);
    }

    private static double kepler(double d, double d1)
    {
        double d4 = 9.9999999999999995E-007D;
        d = java.lang.Math.toRadians(d);
        double d2 = d;
        double d3;
        do
        {
            d3 = d2 - d1 * java.lang.Math.sin(d2) - d;
            d2 -= d3 / (1.0D - d1 * java.lang.Math.cos(d2));
        } while(java.lang.Math.abs(d3) > d4);
        return d2;
    }

    public static double[] phase(java.util.GregorianCalendar gregoriancalendar)
    {
        java.util.Date date = gregoriancalendar.getTime();
        long l = date.getTime();
        double d = com.maddox.il2.engine.MoonPhase.jtime(l);
        double d7 = d - 2444238.5D;
        double d8 = com.maddox.il2.engine.MoonPhase.fixangle(0.98564733209908373D * d7);
        double d9 = com.maddox.il2.engine.MoonPhase.fixangle((d8 + 278.83354000000003D) - 282.59640300000001D);
        double d10 = com.maddox.il2.engine.MoonPhase.kepler(d9, 0.016718D);
        d10 = java.lang.Math.sqrt(1.0340044870138985D) * java.lang.Math.tan(d10 / 2D);
        d10 = 2D * java.lang.Math.toDegrees(java.lang.Math.atan(d10));
        double d11 = com.maddox.il2.engine.MoonPhase.fixangle(d10 + 282.59640300000001D);
        double d35 = (1.0D + 0.016718D * java.lang.Math.cos(java.lang.Math.toRadians(d10))) / 0.99972050847600002D;
        double d36 = 149598500D / d35;
        double d37 = d35 * 0.53312800000000005D;
        double d12 = com.maddox.il2.engine.MoonPhase.fixangle(13.1763966D * d7 + 64.975464000000002D);
        double d13 = com.maddox.il2.engine.MoonPhase.fixangle(d12 - 0.11140410000000001D * d7 - 349.38306299999999D);
        double d14 = com.maddox.il2.engine.MoonPhase.fixangle(151.95042900000001D - 0.052953899999999998D * d7);
        double d15 = 1.2739D * java.lang.Math.sin(java.lang.Math.toRadians(2D * (d12 - d11) - d13));
        double d16 = 0.18579999999999999D * java.lang.Math.sin(java.lang.Math.toRadians(d9));
        double d17 = 0.37D * java.lang.Math.sin(java.lang.Math.toRadians(d9));
        double d18 = (d13 + d15) - d16 - d17;
        double d19 = 6.2885999999999997D * java.lang.Math.sin(java.lang.Math.toRadians(d18));
        double d20 = 0.214D * java.lang.Math.sin(java.lang.Math.toRadians(2D * d18));
        double d21 = ((d12 + d15 + d19) - d16) + d20;
        double d22 = 0.6583D * java.lang.Math.sin(java.lang.Math.toRadians(2D * (d21 - d11)));
        double d23 = d21 + d22;
        double d24 = d14 - 0.16D * java.lang.Math.sin(java.lang.Math.toRadians(d9));
        double d25 = java.lang.Math.sin(java.lang.Math.toRadians(d23 - d24)) * java.lang.Math.cos(java.lang.Math.toRadians(5.1453959999999999D));
        double d26 = java.lang.Math.cos(java.lang.Math.toRadians(d23 - d24));
        double d27 = java.lang.Math.toDegrees(java.lang.Math.atan2(d25, d26));
        d27 += d24;
        double d28 = java.lang.Math.toDegrees(java.lang.Math.asin(java.lang.Math.sin(java.lang.Math.toRadians(d23 - d24)) * java.lang.Math.sin(java.lang.Math.toRadians(5.1453959999999999D))));
        double d29 = d23 - d11;
        double d30 = (1.0D - java.lang.Math.cos(java.lang.Math.toRadians(d29))) / 2D;
        double d31 = 383242.41154199D / (1.0D + 0.054899999999999997D * java.lang.Math.cos(java.lang.Math.toRadians(d18 + d19)));
        double d32 = d31 / 384401D;
        double d33 = 0.5181D / d32;
        double d34 = 0.95069999999999999D / d32;
        double d1 = d30;
        double d2 = 29.530588680000001D * (com.maddox.il2.engine.MoonPhase.fixangle(d29) / 360D);
        double d3 = d31;
        double d4 = d33;
        double d5 = d36;
        double d6 = d37;
        double d38 = com.maddox.il2.engine.MoonPhase.fixangle(d29) / 360D;
        double ad[] = new double[7];
        ad[0] = d38;
        ad[1] = d1;
        ad[2] = d2;
        ad[3] = d3;
        ad[4] = d4;
        ad[5] = d5;
        ad[6] = d6;
        return ad;
    }

    private static final double EPOCH = 2444238.5D;
    private static final double ELONGE = 278.83354000000003D;
    private static final double ELONGP = 282.59640300000001D;
    private static final double ECCENT = 0.016718D;
    private static final double SUN_MAX = 149598500D;
    private static final double SUN_ANG_SIZE = 0.53312800000000005D;
    private static final double MMLONG = 64.975464000000002D;
    private static final double MMLONGP = 349.38306299999999D;
    private static final double MMNODE = 151.95042900000001D;
    private static final double MINC = 5.1453959999999999D;
    private static final double MECC = 0.054899999999999997D;
    private static final double M_ANG_SIZE = 0.5181D;
    private static final double MS_MAX = 384401D;
    private static final double MPARALLAX = 0.95069999999999999D;
    private static final double SYN_MONTH = 29.530588680000001D;
}
