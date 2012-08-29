package com.maddox.JGP;

import java.io.Serializable;

public class Point2f extends Tuple2f
  implements Serializable, Cloneable
{
  public Point2f(float paramFloat1, float paramFloat2)
  {
    super(paramFloat1, paramFloat2);
  }

  public Point2f(float[] paramArrayOfFloat)
  {
    super(paramArrayOfFloat);
  }

  public Point2f(Point2f paramPoint2f)
  {
    super(paramPoint2f);
  }

  public Point2f(Point2d paramPoint2d)
  {
    super(paramPoint2d);
  }

  public Point2f(Tuple2f paramTuple2f)
  {
    super(paramTuple2f);
  }

  public Point2f(Tuple2d paramTuple2d)
  {
    super(paramTuple2d);
  }

  public Point2f()
  {
  }

  public final float distanceSquared(Point2f paramPoint2f)
  {
    double d1 = this.x - paramPoint2f.x;
    double d2 = this.y - paramPoint2f.y;
    return (float)(d1 * d1 + d2 * d2);
  }

  public final float distance(Point2f paramPoint2f)
  {
    return (float)Math.sqrt(distanceSquared(paramPoint2f));
  }

  public final float distanceL1(Point2f paramPoint2f)
  {
    return Math.abs(this.x - paramPoint2f.x) + Math.abs(this.y - paramPoint2f.y);
  }

  public final float distanceLinf(Point2f paramPoint2f)
  {
    return Math.max(Math.abs(this.x - paramPoint2f.x), Math.abs(this.y - paramPoint2f.y));
  }
}