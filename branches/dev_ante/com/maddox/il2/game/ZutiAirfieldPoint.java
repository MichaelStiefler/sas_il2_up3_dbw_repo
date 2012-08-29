package com.maddox.il2.game;

public class ZutiAirfieldPoint
{
  private double x1 = 0.0D;
  private double y1 = 0.0D;
  private double x2 = 0.0D;
  private double y2 = 0.0D;
  private double friction = 3.8D;

  public ZutiAirfieldPoint(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5)
  {
    this.x1 = paramDouble1;
    this.y1 = paramDouble2;
    this.x2 = paramDouble3;
    this.y2 = paramDouble4;

    this.friction = paramDouble5;
  }

  public double isInZAPArea(double paramDouble1, double paramDouble2)
  {
    if ((paramDouble1 >= this.x1) && (paramDouble1 <= this.x2) && (paramDouble2 <= this.y1) && (paramDouble2 >= this.y2))
    {
      return this.friction;
    }

    return -1.0D;
  }
}