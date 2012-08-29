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
    double d1 = this.jdField_x_of_type_Double - paramPoint4d.jdField_x_of_type_Double;
    double d2 = this.jdField_y_of_type_Double - paramPoint4d.jdField_y_of_type_Double;
    double d3 = this.jdField_z_of_type_Double - paramPoint4d.jdField_z_of_type_Double;
    double d4 = this.jdField_z_of_type_Double - paramPoint4d.jdField_w_of_type_Double;
    return (float)(d1 * d1 + d2 * d2 + d3 * d3 + d4 * d4);
  }

  public final double distance(Point4d paramPoint4d)
  {
    return Math.sqrt(distanceSquared(paramPoint4d));
  }

  public final double distanceL1(Point4d paramPoint4d)
  {
    return Math.abs(this.jdField_x_of_type_Double - paramPoint4d.jdField_x_of_type_Double) + Math.abs(this.jdField_y_of_type_Double - paramPoint4d.jdField_y_of_type_Double) + Math.abs(this.jdField_z_of_type_Double - paramPoint4d.jdField_z_of_type_Double) + Math.abs(this.jdField_w_of_type_Double - paramPoint4d.jdField_w_of_type_Double);
  }

  public final double distanceLinf(Point4d paramPoint4d)
  {
    return Math.max(Math.max(Math.abs(this.jdField_x_of_type_Double - paramPoint4d.jdField_x_of_type_Double), Math.abs(this.jdField_y_of_type_Double - paramPoint4d.jdField_y_of_type_Double)), Math.max(Math.abs(this.jdField_z_of_type_Double - paramPoint4d.jdField_z_of_type_Double), Math.abs(this.jdField_w_of_type_Double - paramPoint4d.jdField_w_of_type_Double)));
  }

  public final void project(Point4d paramPoint4d)
  {
    this.jdField_x_of_type_Double = (paramPoint4d.jdField_x_of_type_Double / paramPoint4d.jdField_w_of_type_Double);
    this.jdField_y_of_type_Double = (paramPoint4d.jdField_y_of_type_Double / paramPoint4d.jdField_w_of_type_Double);
    this.jdField_z_of_type_Double = (paramPoint4d.jdField_z_of_type_Double / paramPoint4d.jdField_w_of_type_Double);
    this.jdField_w_of_type_Double = 1.0D;
  }
}