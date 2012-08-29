package com.maddox.il2.game;

import com.maddox.JGP.Tuple2f;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.objects.ships.ZutiTypeAircraftCarrier;

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
            com.maddox.il2.objects.ships.ZutiTypeAircraftCarrier zutitypeaircraftcarrier)
    {
        pribitek = 180D - d2;
        if(zutitypeaircraftcarrier.getDeckTypeId() == 1)
        {
            distanceFromShipCenter = deckType1_Distance[i];
            delta_Angle = d2 - deckType1_Angles[i];
        } else
        if(zutitypeaircraftcarrier.getDeckTypeId() == 2)
        {
            distanceFromShipCenter = deckType2_Distance[i];
            delta_Angle = d2 - deckType2_Angles[i];
        } else
        if(zutitypeaircraftcarrier.getDeckTypeId() == 3)
        {
            distanceFromShipCenter = deckType3_Distance[i];
            delta_Angle = d2 - deckType3_Angles[i];
        } else
        if(zutitypeaircraftcarrier.getDeckTypeId() == 4)
        {
            distanceFromShipCenter = deckType4_Distance[i];
            delta_Angle = d2 - deskType4_Angles[i];
        } else
        if(zutitypeaircraftcarrier.getDeckTypeId() == 5)
        {
            distanceFromShipCenter = deckType5_Distance[i];
            delta_Angle = d2 - deckType5_Angles[i];
        } else
        if(zutitypeaircraftcarrier.getDeckTypeId() == 6)
        {
            distanceFromShipCenter = deckType6_Distance[i];
            delta_Angle = d2 - deckType6_Angles[i];
        } else
        if(zutitypeaircraftcarrier.getDeckTypeId() == 7)
        {
            distanceFromShipCenter = deckType7_Distance[i];
            delta_Angle = d2 - deckType7_Angles[i];
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
    private double deckType1_Angles[] = {
        6.1200000000000001D, -5.1900000000000004D, 4.5099999999999998D, -3.9900000000000002D, 3.5800000000000001D, -3.2400000000000002D
    };
    private double deckType1_Distance[] = {
        70.400000000000006D, 82.840000000000003D, 95.299999999999997D, 107.76000000000001D, 120.23D, 132.71000000000001D
    };
    private double deckType2_Angles[] = {
        2.1200000000000001D, -6.5199999999999996D, 2.79D, -4.8600000000000003D, 2.1600000000000001D
    };
    private double deckType2_Distance[] = {
        67.549999999999997D, 88.069999999999993D, 102.62D, 117.92D, 132.59D
    };
    private double deckType3_Angles[] = {
        10.779999999999999D, -9.4600000000000009D, 7.5899999999999999D
    };
    private double deckType3_Distance[] = {
        42.759999999999998D, 48.659999999999997D, 60.530000000000001D
    };
    private double deskType4_Angles[] = {
        33.689999999999998D, -23.960000000000001D, 14.93D, -12.529999999999999D, 9.4600000000000009D, -8.4299999999999997D, 6.9100000000000001D, -6.3399999999999999D
    };
    private double deckType4_Distance[] = {
        14.42D, 19.699999999999999D, 31.050000000000001D, 36.880000000000003D, 48.659999999999997D, 54.590000000000003D, 66.480000000000004D, 72.439999999999998D
    };
    private double deckType5_Angles[] = {
        0.0D, -5.1900000000000004D, 4.0899999999999999D, 0.0D, 0.0D
    };
    private double deckType5_Distance[] = {
        37.5D, 55.229999999999997D, 70.180000000000007D, 87.5D, 107.5D
    };
    private double deckType6_Angles[] = {
        -19.98D, 15.949999999999999D, -11.31D, 9.8699999999999992D, -7.8499999999999996D, 7.1299999999999999D, 0.0D, 0.0D
    };
    private double deckType6_Distance[] = {
        29.260000000000002D, 36.399999999999999D, 50.990000000000002D, 58.359999999999999D, 73.189999999999998D, 80.620000000000005D, 97.5D, 120D
    };
    private double deckType7_Angles[] = {
        10.779999999999999D, -9.4600000000000009D, 7.5899999999999999D, -6.9100000000000001D, 0.0D, 0.0D
    };
    private double deckType7_Distance[] = {
        53.439999999999998D, 60.829999999999998D, 75.659999999999997D, 83.099999999999994D, 100D, 122.5D
    };
}
