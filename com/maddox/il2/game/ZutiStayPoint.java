// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ZutiStayPoint.java

package com.maddox.il2.game;

import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.objects.ships.BigshipGeneric;

public class ZutiStayPoint
{

    public ZutiStayPoint()
    {
        pointStay = null;
        delta_Angle = 0.0D;
        distanceFromShipCenter = 0.0D;
        pribitek = 0.0D;
    }

    public void PsVsShip(double d, double d1, double d2, int i, 
            java.lang.String s)
    {
        pribitek = 180D - d2;
        if(s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[0]) > 0 || s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[1]) > 0 || s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[2]) > 0)
        {
            distanceFromShipCenter = CarrierGenericUSA_Distance[i];
            delta_Angle = d2 - CarrierGenericUSA_Angles[i];
        } else
        if(s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[3]) > 0 || s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[4]) > 0)
        {
            distanceFromShipCenter = CarrierCV9_Distance[i];
            delta_Angle = d2 - CarrierCV9_Angles[i];
        } else
        if(s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[5]) > 0 || s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[6]) > 0 || s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[18]) > 0)
        {
            distanceFromShipCenter = CarrierCVE_Distance[i];
            delta_Angle = d2 - CarrierCVE_Angles[i];
        } else
        if(s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[7]) > 0)
        {
            distanceFromShipCenter = CarrierCVL_Distance[i];
            delta_Angle = d2 - CarrierCVL_Angles[i];
        } else
        if(s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[8]) > 0 || s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[9]) > 0 || s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[13]) > 0 || s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[14]) > 0)
        {
            distanceFromShipCenter = CarrierHMS_Distance[i];
            delta_Angle = d2 - CarrierHMS_Angles[i];
        } else
        if(s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[10]) > 0 || s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[15]) > 0)
        {
            distanceFromShipCenter = CarrierIJN_Akagi_Distance[i];
            delta_Angle = d2 - CarrierIJN_Akagi_Angles[i];
        } else
        if(s.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[11]) > 0)
        {
            distanceFromShipCenter = CarrierGenericIJN_Distance[i];
            delta_Angle = d2 - CarrierGenericIJN_Angles[i];
        }
    }

    public void PsVsShipRefresh(double d, double d1, double d2)
    {
        d2 += pribitek;
        double d3 = distanceFromShipCenter * java.lang.Math.cos(java.lang.Math.toRadians(d2 + delta_Angle));
        double d4 = distanceFromShipCenter * java.lang.Math.sin(java.lang.Math.toRadians(d2 + delta_Angle));
        pointStay.set((float)(d + d3), (float)(d1 + d4));
    }

    public com.maddox.il2.ai.air.Point_Stay pointStay;
    public double delta_Angle;
    public double distanceFromShipCenter;
    public double pribitek;
    private double CarrierGenericUSA_Angles[] = {
        6.1200000000000001D, -5.1900000000000004D, 4.5099999999999998D, -3.9900000000000002D, 3.5800000000000001D, -3.2400000000000002D
    };
    private double CarrierGenericUSA_Distance[] = {
        70.400000000000006D, 82.840000000000003D, 95.299999999999997D, 107.76000000000001D, 120.23D, 132.71000000000001D
    };
    private double CarrierCV9_Angles[] = {
        2.1200000000000001D, -6.5199999999999996D, 2.79D, -4.8600000000000003D, 2.1600000000000001D
    };
    private double CarrierCV9_Distance[] = {
        67.549999999999997D, 88.069999999999993D, 102.62D, 117.92D, 132.59D
    };
    private double CarrierCVE_Angles[] = {
        9.4600000000000009D, -7.1299999999999999D
    };
    private double CarrierCVE_Distance[] = {
        45.619999999999997D, 60.469999999999999D
    };
    private double CarrierCVL_Angles[] = {
        21.800000000000001D, -17.100000000000001D, 12.529999999999999D, -10.779999999999999D, 8.75D, -7.8499999999999996D, 0.0D
    };
    private double CarrierCVL_Distance[] = {
        26.93D, 34D, 46.100000000000001D, 53.439999999999998D, 65.760000000000005D, 73.189999999999998D, 10D
    };
    private double CarrierHMS_Angles[] = {
        0.0D, -5.1900000000000004D, 4.0899999999999999D, 0.0D, 0.0D
    };
    private double CarrierHMS_Distance[] = {
        37.5D, 55.229999999999997D, 70.180000000000007D, 87.5D, 107.5D
    };
    private double CarrierIJN_Akagi_Angles[] = {
        -19.98D, 15.949999999999999D, -11.31D, 9.8699999999999992D, -7.8499999999999996D, 7.1299999999999999D, 0.0D, 0.0D
    };
    private double CarrierIJN_Akagi_Distance[] = {
        29.260000000000002D, 36.399999999999999D, 50.990000000000002D, 58.359999999999999D, 73.189999999999998D, 80.620000000000005D, 97.5D, 120D
    };
    private double CarrierGenericIJN_Angles[] = {
        10.779999999999999D, -9.4600000000000009D, 7.5899999999999999D, -6.9100000000000001D, 0.0D, 0.0D
    };
    private double CarrierGenericIJN_Distance[] = {
        53.439999999999998D, 60.829999999999998D, 75.659999999999997D, 83.099999999999994D, 100D, 122.5D
    };
}
