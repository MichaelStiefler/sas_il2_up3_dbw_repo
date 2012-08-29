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
    double d1 = this.jdField_x_of_type_Float - paramPoint4f.jdField_x_of_type_Float;
    double d2 = this.jdField_y_of_type_Float - paramPoint4f.jdField_y_of_type_Float;
    double d3 = this.jdField_z_of_type_Float - paramPoint4f.jdField_z_of_type_Float;
    double d4 = this.jdField_z_of_type_Float - paramPoint4f.jdField_w_of_type_Float;
    return (float)(d1 * d1 + d2 * d2 + d3 * d3 + d4 * d4);
  }

  public final float distance(Point4f paramPoint4f)
  {
    return (float)Math.sqrt(distanceSquared(paramPoint4f));
  }

  public final float distanceL1(Point4f paramPoint4f)
  {
    return Math.abs(this.jdField_x_of_type_Float - paramPoint4f.jdField_x_of_type_Float) + Math.abs(this.jdField_y_of_type_Float - paramPoint4f.jdField_y_of_type_Float) + Math.abs(this.jdField_z_of_type_Float - paramPoint4f.jdField_z_of_type_Float) + Math.abs(this.jdField_w_of_type_Float - paramPoint4f.jdField_w_of_type_Float);
  }

  public final float distanceLinf(Point4f paramPoint4f)
  {
    return Math.max(Math.max(Math.abs(this.jdField_x_of_type_Float - paramPoint4f.jdField_x_of_type_Float), Math.abs(this.jdField_y_of_type_Float - paramPoint4f.jdField_y_of_type_Float)), Math.max(Math.abs(this.jdField_z_of_type_Float - paramPoint4f.jdField_z_of_type_Float), Math.abs(this.jdField_w_of_type_Float - paramPoint4f.jdField_w_of_type_Float)));
  }

  public final void project(Point4f paramPoint4f)
  {
    this.jdField_x_of_type_Float = (paramPoint4f.jdField_x_of_type_Float / paramPoint4f.jdField_w_of_type_Float);
    this.jdField_y_of_type_Float = (paramPoint4f.jdField_y_of_type_Float / paramPoint4f.jdField_w_of_type_Float);
    this.jdField_z_of_type_Float = (paramPoint4f.jdField_z_of_type_Float / paramPoint4f.jdField_w_of_type_Float);
    this.jdField_w_of_type_Float = 1.0F;
  }
}