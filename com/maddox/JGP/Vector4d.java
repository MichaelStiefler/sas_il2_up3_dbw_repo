package com.maddox.JGP;

import java.io.Serializable;

public class Vector4d extends Tuple4d
  implements Serializable, Cloneable
{
  public Vector4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    super(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
  }

  public Vector4d(double[] paramArrayOfDouble)
  {
    super(paramArrayOfDouble);
  }

  public Vector4d(Tuple4d paramTuple4d)
  {
    super(paramTuple4d);
  }

  public Vector4d(Tuple4f paramTuple4f)
  {
    super(paramTuple4f);
  }

  public Vector4d()
  {
  }

  public final double lengthSquared()
  {
    return this.jdField_x_of_type_Double * this.jdField_x_of_type_Double + this.jdField_y_of_type_Double * this.jdField_y_of_type_Double + this.jdField_z_of_type_Double * this.jdField_z_of_type_Double + this.jdField_w_of_type_Double * this.jdField_w_of_type_Double;
  }

  public final double length()
  {
    return Math.sqrt(lengthSquared());
  }

  public final double dot(Tuple4d paramTuple4d)
  {
    return this.jdField_x_of_type_Double * paramTuple4d.jdField_x_of_type_Double + this.jdField_y_of_type_Double * paramTuple4d.jdField_y_of_type_Double + this.jdField_z_of_type_Double * paramTuple4d.jdField_z_of_type_Double + this.jdField_w_of_type_Double * paramTuple4d.jdField_w_of_type_Double;
  }

  public final void normalize(Tuple4d paramTuple4d)
  {
    set(paramTuple4d);
    normalize();
  }

  public final void normalize()
  {
    double d = length();

    this.jdField_x_of_type_Double /= d;
    this.jdField_y_of_type_Double /= d;
    this.jdField_z_of_type_Double /= d;
    this.jdField_w_of_type_Double /= d;
  }

  public final double angle(Vector4d paramVector4d)
  {
    double d1 = dot(paramVector4d);
    double d2 = paramVector4d.length();
    double d3 = length();

    return Math.acos(d1 / d2 / d3);
  }
}