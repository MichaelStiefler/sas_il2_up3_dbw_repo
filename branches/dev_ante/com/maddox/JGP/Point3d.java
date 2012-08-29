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
    double d1 = this.jdField_x_of_type_Double - paramPoint3d.jdField_x_of_type_Double;
    double d2 = this.jdField_y_of_type_Double - paramPoint3d.jdField_y_of_type_Double;
    double d3 = this.jdField_z_of_type_Double - paramPoint3d.jdField_z_of_type_Double;
    return d1 * d1 + d2 * d2 + d3 * d3;
  }

  public final double distance(Point3d paramPoint3d)
  {
    return Math.sqrt(distanceSquared(paramPoint3d));
  }

  public final double distanceL1(Point3d paramPoint3d)
  {
    return Math.abs(this.jdField_x_of_type_Double - paramPoint3d.jdField_x_of_type_Double) + Math.abs(this.jdField_y_of_type_Double - paramPoint3d.jdField_y_of_type_Double) + Math.abs(this.jdField_z_of_type_Double - paramPoint3d.jdField_z_of_type_Double);
  }

  public final double distanceLinf(Point3d paramPoint3d)
  {
    return Math.max(Math.max(Math.abs(this.jdField_x_of_type_Double - paramPoint3d.jdField_x_of_type_Double), Math.abs(this.jdField_y_of_type_Double - paramPoint3d.jdField_y_of_type_Double)), Math.abs(this.jdField_z_of_type_Double - paramPoint3d.jdField_z_of_type_Double));
  }

  public final void project(Point4d paramPoint4d)
  {
    this.jdField_x_of_type_Double = (paramPoint4d.jdField_x_of_type_Double / paramPoint4d.jdField_w_of_type_Double);
    this.jdField_y_of_type_Double = (paramPoint4d.jdField_y_of_type_Double / paramPoint4d.jdField_w_of_type_Double);
    this.jdField_z_of_type_Double = (paramPoint4d.jdField_z_of_type_Double / paramPoint4d.jdField_w_of_type_Double);
  }
}