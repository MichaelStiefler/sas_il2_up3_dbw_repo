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
    return this.x * paramTuple2f.x + this.y * paramTuple2f.y;
  }

  public final float length()
  {
    return (float)Math.sqrt(this.x * this.x + this.y * this.y);
  }

  public final float lengthSquared()
  {
    return this.x * this.x + this.y * this.y;
  }

  public final void normalize()
  {
    double d = length();

    this.x = (float)(this.x / d);
    this.y = (float)(this.y / d);
  }

  public final void normalize(Tuple2f paramTuple2f)
  {
    set(paramTuple2f);
    normalize();
  }

  public final float angle(Tuple2f paramTuple2f)
  {
    return (float)Math.abs(Math.atan2(this.x * paramTuple2f.y - this.y * paramTuple2f.x, dot(paramTuple2f)));
  }

  public final float direction()
  {
    float f = (float)Math.atan2(this.y, this.x);
    if (f < 0.0F) f += 6.283186F;
    return f;
  }
}