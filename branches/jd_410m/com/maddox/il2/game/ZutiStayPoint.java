package com.maddox.il2.game;

import com.maddox.il2.ai.air.Point_Stay;

public class ZutiStayPoint
{
  public Point_Stay pointStay = null;
  public double delta_Angle = 0.0D;
  public double distanceFromShipCenter = 0.0D;
  public double pribitek = 0.0D;

  private double[] CarrierGenericUSA_Angles = { 6.12D, -5.19D, 4.51D, -3.99D, 3.58D, -3.24D };
  private double[] CarrierGenericUSA_Distance = { 70.400000000000006D, 82.840000000000003D, 95.299999999999997D, 107.76000000000001D, 120.23D, 132.71000000000001D };

  private double[] CarrierCV9_Angles = { 2.12D, -6.52D, 2.79D, -4.86D, 2.16D };
  private double[] CarrierCV9_Distance = { 67.549999999999997D, 88.069999999999993D, 102.62D, 117.92D, 132.59D };

  private double[] CarrierCVE_Angles = { 9.460000000000001D, -7.13D };
  private double[] CarrierCVE_Distance = { 45.619999999999997D, 60.469999999999999D };

  private double[] CarrierCVL_Angles = { 21.800000000000001D, -17.100000000000001D, 12.529999999999999D, -10.779999999999999D, 8.75D, -7.85D, 0.0D };
  private double[] CarrierCVL_Distance = { 26.93D, 34.0D, 46.100000000000001D, 53.439999999999998D, 65.760000000000005D, 73.189999999999998D, 10.0D };

  private double[] CarrierHMS_Angles = { 0.0D, -5.19D, 4.09D, 0.0D, 0.0D };
  private double[] CarrierHMS_Distance = { 37.5D, 55.229999999999997D, 70.180000000000007D, 87.5D, 107.5D };

  private double[] CarrierIJN_Akagi_Angles = { -19.98D, 15.949999999999999D, -11.31D, 9.869999999999999D, -7.85D, 7.13D, 0.0D, 0.0D };
  private double[] CarrierIJN_Akagi_Distance = { 29.260000000000002D, 36.399999999999999D, 50.990000000000002D, 58.359999999999999D, 73.189999999999998D, 80.620000000000005D, 97.5D, 120.0D };

  private double[] CarrierGenericIJN_Angles = { 10.779999999999999D, -9.460000000000001D, 7.59D, -6.91D, 0.0D, 0.0D };
  private double[] CarrierGenericIJN_Distance = { 53.439999999999998D, 60.829999999999998D, 75.659999999999997D, 83.099999999999994D, 100.0D, 122.5D };

  public void PsVsShip(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, String paramString)
  {
    this.pribitek = (180.0D - paramDouble3);

    if ((paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[0]) > 0) || (paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[1]) > 0) || (paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[2]) > 0))
    {
      this.distanceFromShipCenter = this.CarrierGenericUSA_Distance[paramInt];
      this.delta_Angle = (paramDouble3 - this.CarrierGenericUSA_Angles[paramInt]);
    }
    else if ((paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[3]) > 0) || (paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[4]) > 0))
    {
      this.distanceFromShipCenter = this.CarrierCV9_Distance[paramInt];
      this.delta_Angle = (paramDouble3 - this.CarrierCV9_Angles[paramInt]);
    }
    else if ((paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[5]) > 0) || (paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[6]) > 0) || (paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[18]) > 0))
    {
      this.distanceFromShipCenter = this.CarrierCVE_Distance[paramInt];
      this.delta_Angle = (paramDouble3 - this.CarrierCVE_Angles[paramInt]);
    }
    else if (paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[7]) > 0)
    {
      this.distanceFromShipCenter = this.CarrierCVL_Distance[paramInt];
      this.delta_Angle = (paramDouble3 - this.CarrierCVL_Angles[paramInt]);
    }
    else if ((paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[8]) > 0) || (paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[9]) > 0) || (paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[13]) > 0) || (paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[14]) > 0))
    {
      this.distanceFromShipCenter = this.CarrierHMS_Distance[paramInt];
      this.delta_Angle = (paramDouble3 - this.CarrierHMS_Angles[paramInt]);
    }
    else if ((paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[10]) > 0) || (paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[15]) > 0))
    {
      this.distanceFromShipCenter = this.CarrierIJN_Akagi_Distance[paramInt];
      this.delta_Angle = (paramDouble3 - this.CarrierIJN_Akagi_Angles[paramInt]);
    }
    else if (paramString.indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_SUBCLASS_STRING[11]) > 0)
    {
      this.distanceFromShipCenter = this.CarrierGenericIJN_Distance[paramInt];
      this.delta_Angle = (paramDouble3 - this.CarrierGenericIJN_Angles[paramInt]);
    }
  }

  public void PsVsShipRefresh(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    paramDouble3 += this.pribitek;

    double d1 = this.distanceFromShipCenter * Math.cos(Math.toRadians(paramDouble3 + this.delta_Angle));
    double d2 = this.distanceFromShipCenter * Math.sin(Math.toRadians(paramDouble3 + this.delta_Angle));

    this.pointStay.set((float)(paramDouble1 + d1), (float)(paramDouble2 + d2));
  }
}