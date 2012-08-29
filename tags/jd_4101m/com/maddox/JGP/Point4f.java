package com.maddox.JGP;

import java.io.Serializable;

public class Point4f extends Tuple4f
  implements Serializable, Cloneable
{
  public Point4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    super(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }

  public Point4f(float[] paramArrayOfFloat)
  {
    super(paramArrayOfFloat);
  }

  public Point4f(Point4f paramPoint4f)
  {
    super(paramPoint4f);
  }

  public Point4f(Point4d paramPoint4d)
  {
    super(paramPoint4d);
  }

  public Point4f(Tuple4d paramTuple4d)
  {
    super(paramTuple4d);
  }

  public Point4f(Tuple4f paramTuple4f)
  {
    super(paramTuple4f);
  }

  public Point4f()
  {
  }

  public final float distanceSquared(Point4f paramPoint4f)
  {
    double d1 = this.x - paramPoint4f.x;
    double d2 = this.y - paramPoint4f.y;
    double d3 = this.z - paramPoint4f.z;
    double d4 = this.z - paramPoint4f.w;
    return (float)(d1 * d1 + d2 * d2 + d3 * d3 + d4 * d4);
  }

  public final float distance(Point4f paramPoint4f)
  {
    return (float)Math.sqrt(distanceSquared(paramPoint4f));
  }

  public final float distanceL1(Point4f paramPoint4f)
  {
    return Math.abs(this.x - paramPoint4f.x) + Math.abs(this.y - paramPoint4f.y) + Math.abs(this.z - paramPoint4f.z) + Math.abs(this.w - paramPoint4f.w);
  }

  public final float distanceLinf(Point4f paramPoint4f)
  {
    return Math.max(Math.max(Math.abs(this.x - paramPoint4f.x), Math.abs(this.y - paramPoint4f.y)), Math.max(Math.abs(this.z - paramPoint4f.z), Math.abs(this.w - paramPoint4f.w)));
  }

  public final void project(Point4f paramPoint4f)
  {
    paramPoint4f.x /= paramPoint4f.w;
    paramPoint4f.y /= paramPoint4f.w;
    paramPoint4f.z /= paramPoint4f.w;
    this.w = 1.0F;
  }
}