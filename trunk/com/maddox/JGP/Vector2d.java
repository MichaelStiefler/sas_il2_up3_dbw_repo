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
    return this.x * paramTuple2d.x + this.y * paramTuple2d.y;
  }

  public final double length()
  {
    return Math.sqrt(this.x * this.x + this.y * this.y);
  }

  public final double lengthSquared()
  {
    return this.x * this.x + this.y * this.y;
  }

  public final void normalize()
  {
    double d = length();

    this.x /= d;
    this.y /= d;
  }

  public final void normalize(Tuple2d paramTuple2d)
  {
    set(paramTuple2d);
    normalize();
  }

  public final double angle(Vector2d paramVector2d)
  {
    return Math.abs(Math.atan2(this.x * paramVector2d.y - this.y * paramVector2d.x, dot(paramVector2d)));
  }

  public final double direction()
  {
    double d = Math.atan2(this.y, this.x);
    if (d < 0.0D) d += 6.283185307179586D;
    return d;
  }
}