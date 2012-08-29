package com.maddox.il2.engine;

import java.io.PrintStream;
import java.util.Date;
import java.util.GregorianCalendar;

public class MoonPhase
{
  private static final double EPOCH = 2444238.5D;
  private static final double ELONGE = 278.83354000000003D;
  private static final double ELONGP = 282.59640300000001D;
  private static final double ECCENT = 0.016718D;
  private static final double SUN_MAX = 149598500.0D;
  private static final double SUN_ANG_SIZE = 0.5331280000000001D;
  private static final double MMLONG = 64.975464000000002D;
  private static final double MMLONGP = 349.38306299999999D;
  private static final double MMNODE = 151.95042900000001D;
  private static final double MINC = 5.145396D;
  private static final double MECC = 0.0549D;
  private static final double M_ANG_SIZE = 0.5181D;
  private static final double MS_MAX = 384401.0D;
  private static final double MPARALLAX = 0.9507D;
  private static final double SYN_MONTH = 29.530588680000001D;

  private static double fixangle(double paramDouble)
  {
    return paramDouble - 360.0D * Math.floor(paramDouble / 360.0D);
  }

  private static double dsin(double paramDouble)
  {
    return Math.sin(Math.toRadians(paramDouble));
  }

  private static double dcos(double paramDouble)
  {
    return Math.cos(Math.toRadians(paramDouble));
  }

  private static double jtime(double paramDouble)
  {
    paramDouble /= 1000.0D;
    double d = paramDouble / 86400.0D + 2440587.5D;
    return d;
  }

  private static double jdaytosecs(double paramDouble)
  {
    return (paramDouble - 2440587.5D) * 86400.0D;
  }

  private static int[] jyear(double paramDouble)
  {
    paramDouble += 0.5D;
    double d1 = Math.floor(paramDouble);
    double d2 = paramDouble - d1;

    int[] arrayOfInt = new int[3];
    double d3;
    if (d1 < 2299161.0D)
    {
      d3 = d1;
    }
    else {
      double d4 = Math.floor((d1 - 1867216.25D) / 36524.25D);
      d3 = d1 + 1.0D + d4 - Math.floor(d4 / 4.0D);
    }

    double d5 = d3 + 1524.0D;
    double d6 = Math.floor((d5 - 122.09999999999999D) / 365.25D);
    double d7 = Math.floor(365.25D * d6);
    double d8 = Math.floor((d5 - d7) / 30.600100000000001D);

    arrayOfInt[0] = (int)(d5 - d7 - Math.floor(30.600100000000001D * d8) + d2);
    arrayOfInt[1] = (int)(d8 < 14.0D ? d8 - 1.0D : d8 - 13.0D);
    arrayOfInt[2] = (int)(arrayOfInt[1] > 2 ? d6 - 4716.0D : d6 - 4715.0D);

    return arrayOfInt;
  }

  private static double meanphase(double paramDouble1, double paramDouble2)
  {
    double d1 = (paramDouble1 - 2415020.0D) / 36525.0D;
    double d2 = d1 * d1;
    double d3 = d2 * d1;

    return 2415020.7593299998D + 29.530588680000001D * paramDouble2 + 0.0001178D * d2 - 1.55E-007D * d3 + 0.00033D * dsin(166.56D + 132.87D * d1 - 0.009173000000000001D * d2);
  }

  private static double truephase(double paramDouble1, double paramDouble2)
  {
    int i = 0;

    paramDouble1 += paramDouble2;
    double d1 = paramDouble1 / 1236.8499999999999D;
    double d2 = d1 * d1;
    double d3 = d2 * d1;

    double d4 = 2415020.7593299998D + 29.530588680000001D * paramDouble1 + 0.0001178D * d2 - 1.55E-007D * d3 + 0.00033D * dsin(166.56D + 132.87D * d1 - 0.009173000000000001D * d2);

    double d5 = 359.2242D + 29.10535608D * paramDouble1 - 3.33E-005D * d2 - 3.47E-006D * d3;

    double d6 = 306.02530000000002D + 385.81691805999998D * paramDouble1 + 0.0107306D * d2 + 1.236E-005D * d3;

    double d7 = 21.296399999999998D + 390.67050646000001D * paramDouble1 - 0.0016528D * d2 - 2.39E-006D * d3;

    if ((paramDouble2 < 0.01D) || (Math.abs(paramDouble2 - 0.5D) < 0.01D))
    {
      d4 += (0.1734D - 0.000393D * d1) * dsin(d5) + 0.0021D * dsin(2.0D * d5) - 0.4068D * dsin(d6) + 0.0161D * dsin(2.0D * d6) - 0.0004D * dsin(3.0D * d6) + 0.0104D * dsin(2.0D * d7) - 0.0051D * dsin(d5 + d6) - 0.0074D * dsin(d5 - d6) + 0.0004D * dsin(2.0D * d7 + d5) - 0.0004D * dsin(2.0D * d7 - d5) - 0.0006D * dsin(2.0D * d7 + d6) + 0.001D * dsin(2.0D * d7 - d6) + 0.0005D * dsin(d5 + 2.0D * d6);
      i = 1;
    } else if ((Math.abs(paramDouble2 - 0.25D) < 0.01D) || (Math.abs(paramDouble2 - 0.75D) < 0.01D))
    {
      d4 += (0.1721D - 0.0004D * d1) * dsin(d5) + 0.0021D * dsin(2.0D * d5) - 0.628D * dsin(d6) + 0.0089D * dsin(2.0D * d6) - 0.0004D * dsin(3.0D * d6) + 0.007900000000000001D * dsin(2.0D * d7) - 0.0119D * dsin(d5 + d6) - 0.0047D * dsin(d5 - d6) + 0.0003D * dsin(2.0D * d7 + d5) - 0.0004D * dsin(2.0D * d7 - d5) - 0.0006D * dsin(2.0D * d7 + d6) + 0.0021D * dsin(2.0D * d7 - d6) + 0.0003D * dsin(d5 + 2.0D * d6) + 0.0004D * dsin(d5 - 2.0D * d6) - 0.0003D * dsin(2.0D * d5 + d6);
      if (paramDouble2 < 0.5D)
      {
        d4 += 0.0028D - 0.0004D * dcos(d5) + 0.0003D * dcos(d6);
      }
      else
      {
        d4 += -0.0028D + 0.0004D * dcos(d5) - 0.0003D * dcos(d6);
      }
      i = 1;
    }

    if (i == 0)
    {
      System.out.println("truephase() called with invalid phase selector (phase).");
      System.exit(1);
    }

    return d4; } 
  public static double[] phasehunt(GregorianCalendar paramGregorianCalendar) { double d1 = jtime(paramGregorianCalendar.getTimeInMillis());

    double d2 = d1 - 45.0D;

    int[] arrayOfInt = jyear(d2);
    int i = arrayOfInt[0];
    int j = arrayOfInt[1];
    int k = arrayOfInt[2];

    double d3 = Math.floor((i + (j - 1) * 0.08333333333333333D - 1900.0D) * 12.368499999999999D);
    double d5;
    d2 = d5 = meanphase(d2, d3);
    double d4;
    while (true) { d2 += 29.530588680000001D;
      d4 = d3 + 1.0D;
      double d6 = meanphase(d2, d4);
      if ((d5 <= d1) && (d6 > d1))
        break;
      d5 = d6;
      d3 = d4;
    }

    double[] arrayOfDouble = { jdaytosecs(truephase(d3, 0.0D)), jdaytosecs(truephase(d3, 0.25D)), jdaytosecs(truephase(d3, 0.5D)), jdaytosecs(truephase(d3, 0.75D)), jdaytosecs(truephase(d4, 0.0D)) };

    return arrayOfDouble; } 
  private static double kepler(double paramDouble1, double paramDouble2) {
    double d3 = 1.0E-006D;

    paramDouble1 = Math.toRadians(paramDouble1);
    double d1 = paramDouble1;
    double d2;
    do {
      d2 = d1 - paramDouble2 * Math.sin(d1) - paramDouble1;
      d1 -= d2 / (1.0D - paramDouble2 * Math.cos(d1));
    }while (Math.abs(d2) > d3);

    return d1;
  }

  public static double[] phase(GregorianCalendar paramGregorianCalendar)
  {
    Date localDate = paramGregorianCalendar.getTime();
    long l = localDate.getTime();
    double d1 = jtime(l);

    double d8 = d1 - 2444238.5D;
    double d9 = fixangle(0.9856473320990837D * d8);
    double d10 = fixangle(d9 + 278.83354000000003D - 282.59640300000001D);
    double d11 = kepler(d10, 0.016718D);
    d11 = Math.sqrt(1.034004487013899D) * Math.tan(d11 / 2.0D);
    d11 = 2.0D * Math.toDegrees(Math.atan(d11));
    double d12 = fixangle(d11 + 282.59640300000001D);
    double d36 = (1.0D + 0.016718D * Math.cos(Math.toRadians(d11))) / 0.999720508476D;
    double d37 = 149598500.0D / d36;
    double d38 = d36 * 0.5331280000000001D;

    double d13 = fixangle(13.1763966D * d8 + 64.975464000000002D);

    double d14 = fixangle(d13 - 0.1114041D * d8 - 349.38306299999999D);

    double d15 = fixangle(151.95042900000001D - 0.0529539D * d8);

    double d16 = 1.2739D * Math.sin(Math.toRadians(2.0D * (d13 - d12) - d14));

    double d17 = 0.1858D * Math.sin(Math.toRadians(d10));

    double d18 = 0.37D * Math.sin(Math.toRadians(d10));

    double d19 = d14 + d16 - d17 - d18;

    double d20 = 6.2886D * Math.sin(Math.toRadians(d19));

    double d21 = 0.214D * Math.sin(Math.toRadians(2.0D * d19));

    double d22 = d13 + d16 + d20 - d17 + d21;

    double d23 = 0.6583D * Math.sin(Math.toRadians(2.0D * (d22 - d12)));

    double d24 = d22 + d23;

    double d25 = d15 - 0.16D * Math.sin(Math.toRadians(d10));

    double d26 = Math.sin(Math.toRadians(d24 - d25)) * Math.cos(Math.toRadians(5.145396D));

    double d27 = Math.cos(Math.toRadians(d24 - d25));

    double d28 = Math.toDegrees(Math.atan2(d26, d27));
    d28 += d25;

    double d29 = Math.toDegrees(Math.asin(Math.sin(Math.toRadians(d24 - d25)) * Math.sin(Math.toRadians(5.145396D))));

    double d30 = d24 - d12;

    double d31 = (1.0D - Math.cos(Math.toRadians(d30))) / 2.0D;

    double d32 = 383242.41154199D / (1.0D + 0.0549D * Math.cos(Math.toRadians(d19 + d20)));

    double d33 = d32 / 384401.0D;
    double d34 = 0.5181D / d33;

    double d35 = 0.9507D / d33;

    double d2 = d31;
    double d3 = 29.530588680000001D * (fixangle(d30) / 360.0D);
    double d4 = d32;
    double d5 = d34;
    double d6 = d37;
    double d7 = d38;
    double d39 = fixangle(d30) / 360.0D;

    double[] arrayOfDouble = new double[7];
    arrayOfDouble[0] = d39;
    arrayOfDouble[1] = d2;
    arrayOfDouble[2] = d3;
    arrayOfDouble[3] = d4;
    arrayOfDouble[4] = d5;
    arrayOfDouble[5] = d6;
    arrayOfDouble[6] = d7;

    return arrayOfDouble;
  }
}