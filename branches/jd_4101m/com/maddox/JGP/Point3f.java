package com.maddox.JGP;

import java.io.Serializable;

public class Point3f extends Tuple3f
  implements Serializable, Cloneable
{
  public Point3f(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super(paramFloat1, paramFloat2, paramFloat3);
  }

  public Point3f(float[] paramArrayOfFloat)
  {
    super(paramArrayOfFloat);
  }

  public Point3f(Point3f paramPoint3f)
  {
    super(paramPoint3f);
  }

  public Point3f(Point3d paramPoint3d)
  {
    super(paramPoint3d);
  }

  public Point3f(Tuple3f paramTuple3f)
  {
    super(paramTuple3f);
  }

  public Point3f(Tuple3d paramTuple3d)
  {
    super(paramTuple3d);
  }

  public Point3f()
  {
  }

  public final float distanceSquared(Point3f paramPoint3f)
  {
    double d1 = this.x - paramPoint3f.x;
    double d2 = this.y - paramPoint3f.y;
    double d3 = this.z - paramPoint3f.z;
    return (float)(d1 * d1 + d2 * d2 + d3 * d3);
  }

  public final float distance(Point3f paramPoint3f)
  {
    return (float)Math.sqrt(distanceSquared(paramPoint3f));
  }

  public final float distanceL1(Point3f paramPoint3f)
  {
    return Math.abs(this.x - paramPoint3f.x) + Math.abs(this.y - paramPoint3f.y) + Math.abs(this.z - paramPoint3f.z);
  }

  public final float distanceLinf(Point3f paramPoint3f)
  {
    return Math.max(Math.max(Math.abs(this.x - paramPoint3f.x), Math.abs(this.y - paramPoint3f.y)), Math.abs(this.z - paramPoint3f.z));
  }

  public final void project(Point4f paramPoint4f)
  {
    this.x = (paramPoint4f.x / paramPoint4f.w);
    this.y = (paramPoint4f.y / paramPoint4f.w);
    this.z = (paramPoint4f.z / paramPoint4f.w);
  }
}