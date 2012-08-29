package com.maddox.JGP;

import java.io.Serializable;

public class Point2d extends Tuple2d
  implements Serializable, Cloneable
{
  public Point2d(double paramDouble1, double paramDouble2)
  {
    super(paramDouble1, paramDouble2);
  }

  public Point2d(double[] paramArrayOfDouble)
  {
    super(paramArrayOfDouble);
  }

  public Point2d(Point2d paramPoint2d)
  {
    super(paramPoint2d);
  }

  public Point2d(Point2f paramPoint2f)
  {
    super(paramPoint2f);
  }

  public Point2d(Tuple2d paramTuple2d)
  {
    super(paramTuple2d);
  }

  public Point2d(Tuple2f paramTuple2f)
  {
    super(paramTuple2f);
  }

  public Point2d()
  {
  }

  public final double distanceSquared(Point2d paramPoint2d)
  {
    double d1 = this.jdField_x_of_type_Double - paramPoint2d.jdField_x_of_type_Double;
    double d2 = this.jdField_y_of_type_Double - paramPoint2d.jdField_y_of_type_Double;
    return d1 * d1 + d2 * d2;
  }

  public final double distance(Point2d paramPoint2d)
  {
    return Math.sqrt(distanceSquared(paramPoint2d));
  }

  public final double distanceL1(Point2d paramPoint2d)
  {
    return Math.abs(this.jdField_x_of_type_Double - paramPoint2d.jdField_x_of_type_Double) + Math.abs(this.jdField_y_of_type_Double - paramPoint2d.jdField_y_of_type_Double);
  }

  public final double distanceLinf(Point2d paramPoint2d)
  {
    return Math.max(Math.abs(this.jdField_x_of_type_Double - paramPoint2d.jdField_x_of_type_Double), Math.abs(this.jdField_y_of_type_Double - paramPoint2d.jdField_y_of_type_Double));
  }
}