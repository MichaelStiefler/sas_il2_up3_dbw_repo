package com.maddox.JGP;

import java.io.Serializable;

public abstract class Tuple4f
  implements Serializable, Cloneable
{
  public float x;
  public float y;
  public float z;
  public float w;

  public Tuple4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    set(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }

  public Tuple4f(float[] paramArrayOfFloat)
  {
    set(paramArrayOfFloat);
  }

  public Tuple4f(Tuple4f paramTuple4f)
  {
    set(paramTuple4f);
  }

  public Tuple4f(Tuple4d paramTuple4d)
  {
    set(paramTuple4d);
  }

  public Tuple4f()
  {
    this.x = 0.0F;
    this.y = 0.0F;
    this.z = 0.0F;
    this.w = 0.0F;
  }

  public final void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.x = paramFloat1;
    this.y = paramFloat2;
    this.z = paramFloat3;
    this.w = paramFloat4;
  }

  public final void set(float[] paramArrayOfFloat)
  {
    this.x = paramArrayOfFloat[0];
    this.y = paramArrayOfFloat[1];
    this.z = paramArrayOfFloat[2];
    this.w = paramArrayOfFloat[3];
  }

  public final void set(Tuple4f paramTuple4f)
  {
    this.x = paramTuple4f.x;
    this.y = paramTuple4f.y;
    this.z = paramTuple4f.z;
    this.w = paramTuple4f.w;
  }

  public final void set(Tuple4d paramTuple4d)
  {
    this.x = (float)paramTuple4d.x;
    this.y = (float)paramTuple4d.y;
    this.z = (float)paramTuple4d.z;
    this.w = (float)paramTuple4d.w;
  }

  public final void get(float[] paramArrayOfFloat)
  {
    paramArrayOfFloat[0] = this.x;
    paramArrayOfFloat[1] = this.y;
    paramArrayOfFloat[2] = this.z;
    paramArrayOfFloat[3] = this.w;
  }

  public final void get(Tuple4f paramTuple4f)
  {
    paramTuple4f.x = this.x;
    paramTuple4f.y = this.y;
    paramTuple4f.z = this.z;
    paramTuple4f.w = this.w;
  }

  public final void add(Tuple4f paramTuple4f1, Tuple4f paramTuple4f2)
  {
    paramTuple4f1.x += paramTuple4f2.x;
    paramTuple4f1.y += paramTuple4f2.y;
    paramTuple4f1.z += paramTuple4f2.z;
    paramTuple4f1.w += paramTuple4f2.w;
  }

  public final void add(Tuple4f paramTuple4f)
  {
    this.x += paramTuple4f.x;
    this.y += paramTuple4f.y;
    this.z += paramTuple4f.z;
    this.w += paramTuple4f.w;
  }

  public final void sub(Tuple4f paramTuple4f1, Tuple4f paramTuple4f2)
  {
    paramTuple4f1.x -= paramTuple4f2.x;
    paramTuple4f1.y -= paramTuple4f2.y;
    paramTuple4f1.z -= paramTuple4f2.z;
    this.w = (paramTuple4f1.z - paramTuple4f2.w);
  }

  public final void sub(Tuple4f paramTuple4f)
  {
    this.x -= paramTuple4f.x;
    this.y -= paramTuple4f.y;
    this.z -= paramTuple4f.z;
    this.w -= paramTuple4f.w;
  }

  public final void negate(Tuple4f paramTuple4f)
  {
    this.x = (-paramTuple4f.x);
    this.y = (-paramTuple4f.y);
    this.z = (-paramTuple4f.z);
    this.w = (-paramTuple4f.w);
  }

  public final void negate()
  {
    this.x = (-this.x);
    this.y = (-this.y);
    this.z = (-this.z);
    this.w = (-this.w);
  }

  public final void scale(float paramFloat, Tuple4f paramTuple4f)
  {
    this.x = (paramFloat * paramTuple4f.x);
    this.y = (paramFloat * paramTuple4f.y);
    this.z = (paramFloat * paramTuple4f.z);
    this.w = (paramFloat * paramTuple4f.w);
  }

  public final void scale(float paramFloat)
  {
    this.x *= paramFloat;
    this.y *= paramFloat;
    this.z *= paramFloat;
    this.w *= paramFloat;
  }

  public final void scaleAdd(float paramFloat, Tuple4f paramTuple4f1, Tuple4f paramTuple4f2)
  {
    this.x = (paramFloat * paramTuple4f1.x + paramTuple4f2.x);
    this.y = (paramFloat * paramTuple4f1.y + paramTuple4f2.y);
    this.z = (paramFloat * paramTuple4f1.z + paramTuple4f2.z);
    this.w = (paramFloat * paramTuple4f1.w + paramTuple4f2.w);
  }

  public final void scaleAdd(float paramFloat, Tuple4f paramTuple4f)
  {
    this.x = (paramFloat * this.x + paramTuple4f.x);
    this.y = (paramFloat * this.y + paramTuple4f.y);
    this.z = (paramFloat * this.z + paramTuple4f.z);
    this.w = (paramFloat * this.z + paramTuple4f.w);
  }

  public int hashCode()
  {
    return Float.floatToIntBits(this.x) ^ Float.floatToIntBits(this.y) ^ Float.floatToIntBits(this.z) ^ Float.floatToIntBits(this.w);
  }

  public boolean equals(Tuple4f paramTuple4f)
  {
    return (paramTuple4f != null) && (this.x == paramTuple4f.x) && (this.y == paramTuple4f.y) && (this.z == paramTuple4f.z) && (this.w == paramTuple4f.w);
  }

  public boolean epsilonEquals(Tuple4f paramTuple4f, float paramFloat)
  {
    return (Math.abs(paramTuple4f.x - this.x) <= paramFloat) && (Math.abs(paramTuple4f.y - this.y) <= paramFloat) && (Math.abs(paramTuple4f.z - this.z) <= paramFloat) && (Math.abs(paramTuple4f.w - this.w) <= paramFloat);
  }

  public String toString()
  {
    return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
  }

  public final void clamp(float paramFloat1, float paramFloat2, Tuple4f paramTuple4f)
  {
    set(paramTuple4f);
    clamp(paramFloat1, paramFloat2);
  }

  public final void clampMin(float paramFloat, Tuple4f paramTuple4f)
  {
    set(paramTuple4f);
    clampMin(paramFloat);
  }

  public final void clampMax(float paramFloat, Tuple4f paramTuple4f)
  {
    set(paramTuple4f);
    clampMax(paramFloat);
  }

  public final void absolute(Tuple4f paramTuple4f)
  {
    set(paramTuple4f);
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
    if (this.z < paramFloat)
      this.z = paramFloat;
    if (this.w < paramFloat)
      this.w = paramFloat;
  }

  public final void clampMax(float paramFloat)
  {
    if (this.x > paramFloat)
      this.x = paramFloat;
    if (this.y > paramFloat)
      this.y = paramFloat;
    if (this.z > paramFloat)
      this.z = paramFloat;
    if (this.w > paramFloat)
      this.w = paramFloat;
  }

  public final void absolute()
  {
    if (this.x < 0.0D)
      this.x = (-this.x);
    if (this.y < 0.0D)
      this.y = (-this.y);
    if (this.z < 0.0D)
      this.z = (-this.z);
    if (this.w < 0.0D)
      this.w = (-this.w);
  }

  public final void interpolate(Tuple4f paramTuple4f1, Tuple4f paramTuple4f2, float paramFloat)
  {
    paramTuple4f1.x += (paramTuple4f2.x - paramTuple4f1.x) * paramFloat;
    paramTuple4f1.y += (paramTuple4f2.y - paramTuple4f1.y) * paramFloat;
    paramTuple4f1.z += (paramTuple4f2.z - paramTuple4f1.z) * paramFloat;
    paramTuple4f1.w += (paramTuple4f2.w - paramTuple4f1.w) * paramFloat;
  }

  public final void interpolate(Tuple4f paramTuple4f, float paramFloat)
  {
    this.x += paramFloat * (paramTuple4f.x - this.x);
    this.y += paramFloat * (paramTuple4f.y - this.y);
    this.z += paramFloat * (paramTuple4f.z - this.z);
    this.w += paramFloat * (paramTuple4f.w - this.w);
  }
}