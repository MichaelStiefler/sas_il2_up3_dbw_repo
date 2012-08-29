package com.maddox.JGP;

import java.io.Serializable;

public class Point3d extends Tuple3d
  implements Serializable, Cloneable
{
  public Point3d(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    super(paramDouble1, paramDouble2, paramDouble3);
  }

  public Point3d(double[] paramArrayOfDouble)
  {
    super(paramArrayOfDouble);
  }

  public Point3d(Point3d paramPoint3d)
  {
    super(paramPoint3d);
  }

  public Point3d(Point3f paramPoint3f)
  {
    super(paramPoint3f);
  }

  public Point3d(Tuple3d paramTuple3d)
  {
    super(paramTuple3d);
  }

  public Point3d(Tuple3f paramTuple3f)
  {
    super(paramTuple3f);
  }

  public Point3d()
  {
  }

  public final double distanceSquared(Point3d paramPoint3d)
  {
    double d1 = this.x - paramPoint3d.x;
    double d2 = this.y - paramPoint3d.y;
    double d3 = this.z - paramPoint3d.z;
    return d1 * d1 + d2 * d2 + d3 * d3;
  }

  public final double distance(Point3d paramPoint3d)
  {
    return Math.sqrt(distanceSquared(paramPoint3d));
  }

  public final double distanceL1(Point3d paramPoint3d)
  {
    return Math.abs(this.x - paramPoint3d.x) + Math.abs(this.y - paramPoint3d.y) + Math.abs(this.z - paramPoint3d.z);
  }

  public final double distanceLinf(Point3d paramPoint3d)
  {
    return Math.max(Math.max(Math.abs(this.x - paramPoint3d.x), Math.abs(this.y - paramPoint3d.y)), Math.abs(this.z - paramPoint3d.z));
  }

  public final void project(Point4d paramPoint4d)
  {
    this.x = (paramPoint4d.x / paramPoint4d.w);
    this.y = (paramPoint4d.y / paramPoint4d.w);
    this.z = (paramPoint4d.z / paramPoint4d.w);
  }
}