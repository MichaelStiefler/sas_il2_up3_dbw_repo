package com.maddox.JGP;

import java.io.Serializable;

public abstract class Tuple2f
  implements Serializable, Cloneable
{
  public float x;
  public float y;

  public Tuple2f(float paramFloat1, float paramFloat2)
  {
    this.x = paramFloat1;
    this.y = paramFloat2;
  }

  public Tuple2f(float[] paramArrayOfFloat)
  {
    this.x = paramArrayOfFloat[0];
    this.y = paramArrayOfFloat[1];
  }

  public Tuple2f(Tuple2f paramTuple2f)
  {
    this.x = paramTuple2f.x;
    this.y = paramTuple2f.y;
  }

  public Tuple2f(Tuple2d paramTuple2d)
  {
    this.x = (float)paramTuple2d.x;
    this.y = (float)paramTuple2d.y;
  }

  public Tuple2f()
  {
    this.x = 0.0F;
    this.y = 0.0F;
  }

  public final void set(float paramFloat1, float paramFloat2)
  {
    this.x = paramFloat1;
    this.y = paramFloat2;
  }

  public final void set(float[] paramArrayOfFloat)
  {
    this.x = paramArrayOfFloat[0];
    this.y = paramArrayOfFloat[1];
  }

  public final void set(Tuple2f paramTuple2f)
  {
    this.x = paramTuple2f.x;
    this.y = paramTuple2f.y;
  }

  public final void set(Tuple3f paramTuple3f)
  {
    this.x = paramTuple3f.x;
    this.y = paramTuple3f.y;
  }

  public final void set(Tuple2d paramTuple2d)
  {
    this.x = (float)paramTuple2d.x;
    this.y = (float)paramTuple2d.y;
  }

  public final void get(float[] paramArrayOfFloat)
  {
    paramArrayOfFloat[0] = this.x;
    paramArrayOfFloat[1] = this.y;
  }

  public final void add(Tuple2f paramTuple2f1, Tuple2f paramTuple2f2)
  {
    paramTuple2f1.x += paramTuple2f2.x;
    paramTuple2f1.y += paramTuple2f2.y;
  }

  public final void add(float paramFloat1, float paramFloat2)
  {
    this.x += paramFloat1;
    this.y += paramFloat2;
  }

  public final void add(Tuple2f paramTuple2f)
  {
    this.x += paramTuple2f.x;
    this.y += paramTuple2f.y;
  }

  public final void sub(Tuple2f paramTuple2f1, Tuple2f paramTuple2f2)
  {
    paramTuple2f1.x -= paramTuple2f2.x;
    paramTuple2f1.y -= paramTuple2f2.y;
  }

  public final void sub(float paramFloat1, float paramFloat2)
  {
    this.x -= paramFloat1;
    this.y -= paramFloat2;
  }

  public final void sub(Tuple2f paramTuple2f)
  {
    this.x -= paramTuple2f.x;
    this.y -= paramTuple2f.y;
  }

  public final void negate(Tuple2f paramTuple2f)
  {
    this.x = (-paramTuple2f.x);
    this.y = (-paramTuple2f.y);
  }

  public final void negate()
  {
    this.x = (-this.x);
    this.y = (-this.y);
  }

  public final void scale(float paramFloat, Tuple2f paramTuple2f)
  {
    this.x = (paramFloat * paramTuple2f.x);
    this.y = (paramFloat * paramTuple2f.y);
  }

  public final void scale(float paramFloat)
  {
    this.x *= paramFloat;
    this.y *= paramFloat;
  }

  public final void scaleAdd(float paramFloat, Tuple2f paramTuple2f1, Tuple2f paramTuple2f2)
  {
    this.x = (paramFloat * paramTuple2f1.x + paramTuple2f2.x);
    this.y = (paramFloat * paramTuple2f1.y + paramTuple2f2.y);
  }

  public final void scaleAdd(float paramFloat, Tuple2f paramTuple2f)
  {
    this.x = (paramFloat * this.x + paramTuple2f.x);
    this.y = (paramFloat * this.y + paramTuple2f.y);
  }

  public int hashCode()
  {
    int i = Float.floatToIntBits(this.x);
    int j = Float.floatToIntBits(this.y);
    return i ^ j;
  }

  public boolean equals(Tuple2f paramTuple2f)
  {
    return (paramTuple2f != null) && (this.x == paramTuple2f.x) && (this.y == paramTuple2f.y);
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof Tuple2f)) && (equals((Tuple2f)paramObject));
  }

  public boolean epsilonEquals(Tuple2f paramTuple2f, float paramFloat)
  {
    return (Math.abs(paramTuple2f.x - this.x) <= paramFloat) && (Math.abs(paramTuple2f.y - this.y) <= paramFloat);
  }

  public String toString()
  {
    return "(" + this.x + ", " + this.y + ")";
  }

  public final void clamp(float paramFloat1, float paramFloat2, Tuple2f paramTuple2f)
  {
    set(paramTuple2f);
    clamp(paramFloat1, paramFloat2);
  }

  public final void clampMin(float paramFloat, Tuple2f paramTuple2f)
  {
    set(paramTuple2f);
    clampMin(paramFloat);
  }

  public final void clampMax(float paramFloat, Tuple2f paramTuple2f)
  {
    set(paramTuple2f);
    clampMax(paramFloat);
  }

  public final void absolute(Tuple2f paramTuple2f)
  {
    set(paramTuple2f);
    absolute();
  }

  public final void clamp(float paramFloat1, float paramFloat2)
  {
    clampMin(paramFloat1);
    clampMax(paramFloat2);
  }

  public final void clampMin(float paramFloat)
  {
    if (this.x < paramFloat)
      this.x = paramFloat;
    if (this.y < paramFloat)
      this.y = paramFloat;
  }

  public final void clampMax(float paramFloat)
  {
    if (this.x > paramFloat)
      this.x = paramFloat;
    if (this.y > paramFloat)
      this.y = paramFloat;
  }

  public final void absolute()
  {
    if (this.x < 0.0D)
      this.x = (-this.x);
    if (this.y < 0.0D)
      this.y = (-this.y);
  }

  public final void interpolate(Tuple2f paramTuple2f1, Tuple2f paramTuple2f2, float paramFloat)
  {
    paramTuple2f1.x += (paramTuple2f2.x - paramTuple2f1.x) * paramFloat;
    paramTuple2f1.y += (paramTuple2f2.y - paramTuple2f1.y) * paramFloat;
  }

  public final void interpolate(Tuple2f paramTuple2f, float paramFloat)
  {
    this.x += paramFloat * (paramTuple2f.x - this.x);
    this.y += paramFloat * (paramTuple2f.y - this.y);
  }
}