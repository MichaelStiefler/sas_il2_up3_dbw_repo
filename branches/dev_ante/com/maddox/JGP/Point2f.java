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
    double d1 = this.jdField_x_of_type_Float - paramPoint2f.jdField_x_of_type_Float;
    double d2 = this.jdField_y_of_type_Float - paramPoint2f.jdField_y_of_type_Float;
    return (float)(d1 * d1 + d2 * d2);
  }

  public final float distance(Point2f paramPoint2f)
  {
    return (float)Math.sqrt(distanceSquared(paramPoint2f));
  }

  public final float distanceL1(Point2f paramPoint2f)
  {
    return Math.abs(this.jdField_x_of_type_Float - paramPoint2f.jdField_x_of_type_Float) + Math.abs(this.jdField_y_of_type_Float - paramPoint2f.jdField_y_of_type_Float);
  }

  public final float distanceLinf(Point2f paramPoint2f)
  {
    return Math.max(Math.abs(this.jdField_x_of_type_Float - paramPoint2f.jdField_x_of_type_Float), Math.abs(this.jdField_y_of_type_Float - paramPoint2f.jdField_y_of_type_Float));
  }
}