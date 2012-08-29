package com.maddox.JGP;

import java.io.Serializable;

public class Vector2d extends Tuple2d
  implements Serializable, Cloneable
{
  public Vector2d(double paramDouble1, double paramDouble2)
  {
    super(paramDouble1, paramDouble2);
  }

  public Vector2d(double[] paramArrayOfDouble)
  {
    super(paramArrayOfDouble);
  }

  public Vector2d(Tuple2d paramTuple2d)
  {
    super(paramTuple2d);
  }

  public Vector2d(Tuple2f paramTuple2f)
  {
    super(paramTuple2f);
  }

  public Vector2d()
  {
  }

  public final double dot(Tuple2d paramTuple2d)
  {
    return this.jdField_x_of_type_Double * paramTuple2d.jdField_x_of_type_Double + this.jdField_y_of_type_Double * paramTuple2d.jdField_y_of_type_Double;
  }

  public final double length()
  {
    return Math.sqrt(this.jdField_x_of_type_Double * this.jdField_x_of_type_Double + this.jdField_y_of_type_Double * this.jdField_y_of_type_Double);
  }

  public final double lengthSquared()
  {
    return this.jdField_x_of_type_Double * this.jdField_x_of_type_Double + this.jdField_y_of_type_Double * this.jdField_y_of_type_Double;
  }

  public final void normalize()
  {
    double d = length();

    this.jdField_x_of_type_Double /= d;
    this.jdField_y_of_type_Double /= d;
  }

  public final void normalize(Tuple2d paramTuple2d)
  {
    set(paramTuple2d);
    normalize();
  }

  public final double angle(Vector2d paramVector2d)
  {
    return Math.abs(Math.atan2(this.jdField_x_of_type_Double * paramVector2d.jdField_y_of_type_Double - this.jdField_y_of_type_Double * paramVector2d.jdField_x_of_type_Double, dot(paramVector2d)));
  }

  public final double direction()
  {
    double d = Math.atan2(this.jdField_y_of_type_Double, this.jdField_x_of_type_Double);
    if (d < 0.0D) d += 6.283185307179586D;
    return d;
  }
}