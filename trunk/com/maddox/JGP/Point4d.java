package com.maddox.JGP;

import java.io.Serializable;

public class Point4d extends Tuple4d
  implements Serializable, Cloneable
{
  public Point4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    super(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
  }

  public Point4d(double[] paramArrayOfDouble)
  {
    super(paramArrayOfDouble);
  }

  public Point4d(Point4f paramPoint4f)
  {
    super(paramPoint4f);
  }

  public Point4d(Point4d paramPoint4d)
  {
    super(paramPoint4d);
  }

  public Point4d(Tuple4d paramTuple4d)
  {
    super(paramTuple4d);
  }

  public Point4d(Tuple4f paramTuple4f)
  {
    super(paramTuple4f);
  }

  public Point4d()
  {
  }

  public final double distanceSquared(Point4d paramPoint4d)
  {
    double d1 = this.x - paramPoint4d.x;
    double d2 = this.y - paramPoint4d.y;
    double d3 = this.z - paramPoint4d.z;
    double d4 = this.z - paramPoint4d.w;
    return (float)(d1 * d1 + d2 * d2 + d3 * d3 + d4 * d4);
  }

  public final double distance(Point4d paramPoint4d)
  {
    return Math.sqrt(distanceSquared(paramPoint4d));
  }

  public final double distanceL1(Point4d paramPoint4d)
  {
    return Math.abs(this.x - paramPoint4d.x) + Math.abs(this.y - paramPoint4d.y) + Math.abs(this.z - paramPoint4d.z) + Math.abs(this.w - paramPoint4d.w);
  }

  public final double distanceLinf(Point4d paramPoint4d)
  {
    return Math.max(Math.max(Math.abs(this.x - paramPoint4d.x), Math.abs(this.y - paramPoint4d.y)), Math.max(Math.abs(this.z - paramPoint4d.z), Math.abs(this.w - paramPoint4d.w)));
  }

  public final void project(Point4d paramPoint4d)
  {
    paramPoint4d.x /= paramPoint4d.w;
    paramPoint4d.y /= paramPoint4d.w;
    paramPoint4d.z /= paramPoint4d.w;
    this.w = 1.0D;
  }
}