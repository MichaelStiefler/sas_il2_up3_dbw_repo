package com.maddox.JGP;

import java.io.Serializable;

public class Vector2f extends Tuple2f
  implements Serializable, Cloneable
{
  public Vector2f(float paramFloat1, float paramFloat2)
  {
    super(paramFloat1, paramFloat2);
  }

  public Vector2f(float[] paramArrayOfFloat)
  {
    super(paramArrayOfFloat);
  }

  public Vector2f(Tuple2f paramTuple2f)
  {
    super(paramTuple2f);
  }

  public Vector2f(Tuple2d paramTuple2d)
  {
    super(paramTuple2d);
  }

  public Vector2f()
  {
  }

  public final float dot(Tuple2f paramTuple2f)
  {
    return this.jdField_x_of_type_Float * paramTuple2f.jdField_x_of_type_Float + this.jdField_y_of_type_Float * paramTuple2f.jdField_y_of_type_Float;
  }

  public final float length()
  {
    return (float)Math.sqrt(this.jdField_x_of_type_Float * this.jdField_x_of_type_Float + this.jdField_y_of_type_Float * this.jdField_y_of_type_Float);
  }

  public final float lengthSquared()
  {
    return this.jdField_x_of_type_Float * this.jdField_x_of_type_Float + this.jdField_y_of_type_Float * this.jdField_y_of_type_Float;
  }

  public final void normalize()
  {
    double d = length();

    this.jdField_x_of_type_Float = (float)(this.jdField_x_of_type_Float / d);
    this.jdField_y_of_type_Float = (float)(this.jdField_y_of_type_Float / d);
  }

  public final void normalize(Tuple2f paramTuple2f)
  {
    set(paramTuple2f);
    normalize();
  }

  public final float angle(Tuple2f paramTuple2f)
  {
    return (float)Math.abs(Math.atan2(this.jdField_x_of_type_Float * paramTuple2f.jdField_y_of_type_Float - this.jdField_y_of_type_Float * paramTuple2f.jdField_x_of_type_Float, dot(paramTuple2f)));
  }

  public final float direction()
  {
    float f = (float)Math.atan2(this.jdField_y_of_type_Float, this.jdField_x_of_type_Float);
    if (f < 0.0F) f += 6.283186F;
    return f;
  }
}