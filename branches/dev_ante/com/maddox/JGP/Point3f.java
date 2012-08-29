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
    double d1 = this.jdField_x_of_type_Float - paramPoint3f.jdField_x_of_type_Float;
    double d2 = this.jdField_y_of_type_Float - paramPoint3f.jdField_y_of_type_Float;
    double d3 = this.jdField_z_of_type_Float - paramPoint3f.jdField_z_of_type_Float;
    return (float)(d1 * d1 + d2 * d2 + d3 * d3);
  }

  public final float distance(Point3f paramPoint3f)
  {
    return (float)Math.sqrt(distanceSquared(paramPoint3f));
  }

  public final float distanceL1(Point3f paramPoint3f)
  {
    return Math.abs(this.jdField_x_of_type_Float - paramPoint3f.jdField_x_of_type_Float) + Math.abs(this.jdField_y_of_type_Float - paramPoint3f.jdField_y_of_type_Float) + Math.abs(this.jdField_z_of_type_Float - paramPoint3f.jdField_z_of_type_Float);
  }

  public final float distanceLinf(Point3f paramPoint3f)
  {
    return Math.max(Math.max(Math.abs(this.jdField_x_of_type_Float - paramPoint3f.jdField_x_of_type_Float), Math.abs(this.jdField_y_of_type_Float - paramPoint3f.jdField_y_of_type_Float)), Math.abs(this.jdField_z_of_type_Float - paramPoint3f.jdField_z_of_type_Float));
  }

  public final void project(Point4f paramPoint4f)
  {
    this.jdField_x_of_type_Float = (paramPoint4f.jdField_x_of_type_Float / paramPoint4f.jdField_w_of_type_Float);
    this.jdField_y_of_type_Float = (paramPoint4f.jdField_y_of_type_Float / paramPoint4f.jdField_w_of_type_Float);
    this.jdField_z_of_type_Float = (paramPoint4f.jdField_z_of_type_Float / paramPoint4f.jdField_w_of_type_Float);
  }
}