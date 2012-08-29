package com.maddox.JGP;

import java.io.Serializable;

public class Vector3f extends Tuple3f
  implements Serializable, Cloneable
{
  public Vector3f(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super(paramFloat1, paramFloat2, paramFloat3);
  }

  public Vector3f(float[] paramArrayOfFloat)
  {
    super(paramArrayOfFloat);
  }

  public Vector3f(Tuple3d paramTuple3d)
  {
    super(paramTuple3d);
  }

  public Vector3f(Tuple3f paramTuple3f)
  {
    super(paramTuple3f);
  }

  public Vector3f()
  {
  }

  public final float lengthSquared()
  {
    return this.jdField_x_of_type_Float * this.jdField_x_of_type_Float + this.jdField_y_of_type_Float * this.jdField_y_of_type_Float + this.jdField_z_of_type_Float * this.jdField_z_of_type_Float;
  }

  public final float length()
  {
    return (float)Math.sqrt(lengthSquared());
  }

  public final void cross(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    set(paramTuple3f1.jdField_y_of_type_Float * paramTuple3f2.jdField_z_of_type_Float - paramTuple3f1.jdField_z_of_type_Float * paramTuple3f2.jdField_y_of_type_Float, paramTuple3f1.jdField_z_of_type_Float * paramTuple3f2.jdField_x_of_type_Float - paramTuple3f1.jdField_x_of_type_Float * paramTuple3f2.jdField_z_of_type_Float, paramTuple3f1.jdField_x_of_type_Float * paramTuple3f2.jdField_y_of_type_Float - paramTuple3f1.jdField_y_of_type_Float * paramTuple3f2.jdField_x_of_type_Float);
  }

  public final float dot(Tuple3f paramTuple3f)
  {
    return this.jdField_x_of_type_Float * paramTuple3f.jdField_x_of_type_Float + this.jdField_y_of_type_Float * paramTuple3f.jdField_y_of_type_Float + this.jdField_z_of_type_Float * paramTuple3f.jdField_z_of_type_Float;
  }

  public final float normalize(Tuple3f paramTuple3f)
  {
    set(paramTuple3f);
    return normalize();
  }

  public final float normalize()
  {
    float f = Math.max(length(), 1.0E-035F);
    this.jdField_x_of_type_Float /= f;
    this.jdField_y_of_type_Float /= f;
    this.jdField_z_of_type_Float /= f;
    return f;
  }

  public final float angle(Vector3f paramVector3f)
  {
    double d1 = this.jdField_y_of_type_Float * paramVector3f.jdField_z_of_type_Float - this.jdField_z_of_type_Float * paramVector3f.jdField_y_of_type_Float;
    double d2 = this.jdField_z_of_type_Float * paramVector3f.jdField_x_of_type_Float - this.jdField_x_of_type_Float * paramVector3f.jdField_z_of_type_Float;
    double d3 = this.jdField_x_of_type_Float * paramVector3f.jdField_y_of_type_Float - this.jdField_y_of_type_Float * paramVector3f.jdField_x_of_type_Float;
    double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);

    return (float)Math.abs(Math.atan2(d4, dot(paramVector3f)));
  }
}