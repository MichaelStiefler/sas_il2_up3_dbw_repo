package com.maddox.JGP;

import java.io.Serializable;

public class Vector4f extends Tuple4f
  implements Serializable, Cloneable
{
  public Vector4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    super(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }

  public Vector4f(float[] paramArrayOfFloat)
  {
    super(paramArrayOfFloat);
  }

  public Vector4f(Tuple4d paramTuple4d)
  {
    super(paramTuple4d);
  }

  public Vector4f(Tuple4f paramTuple4f)
  {
    super(paramTuple4f);
  }

  public Vector4f()
  {
  }

  public final float lengthSquared()
  {
    return this.jdField_x_of_type_Float * this.jdField_x_of_type_Float + this.jdField_y_of_type_Float * this.jdField_y_of_type_Float + this.jdField_z_of_type_Float * this.jdField_z_of_type_Float + this.jdField_w_of_type_Float * this.jdField_w_of_type_Float;
  }

  public final float length()
  {
    return (float)Math.sqrt(lengthSquared());
  }

  public final float dot(Tuple4f paramTuple4f)
  {
    return this.jdField_x_of_type_Float * paramTuple4f.jdField_x_of_type_Float + this.jdField_y_of_type_Float * paramTuple4f.jdField_y_of_type_Float + this.jdField_z_of_type_Float * paramTuple4f.jdField_z_of_type_Float + this.jdField_w_of_type_Float * paramTuple4f.jdField_w_of_type_Float;
  }

  public final void normalize(Tuple4d paramTuple4d)
  {
    set(paramTuple4d);
    normalize();
  }

  public final void normalize()
  {
    double d = length();

    this.jdField_x_of_type_Float = (float)(this.jdField_x_of_type_Float / d);
    this.jdField_y_of_type_Float = (float)(this.jdField_y_of_type_Float / d);
    this.jdField_z_of_type_Float = (float)(this.jdField_z_of_type_Float / d);
    this.jdField_w_of_type_Float = (float)(this.jdField_w_of_type_Float / d);
  }

  public final float angle(Vector4f paramVector4f)
  {
    double d1 = dot(paramVector4f);
    double d2 = paramVector4f.length();
    double d3 = length();

    return (float)Math.acos(d1 / d2 / d3);
  }
}