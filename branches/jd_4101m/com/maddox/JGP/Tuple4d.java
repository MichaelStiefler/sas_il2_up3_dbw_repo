package com.maddox.JGP;

import java.io.Serializable;

public abstract class Tuple4d
  implements Serializable, Cloneable
{
  public double x;
  public double y;
  public double z;
  public double w;

  public Tuple4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    set(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
  }

  public Tuple4d(double[] paramArrayOfDouble)
  {
    set(paramArrayOfDouble);
  }

  public Tuple4d(Tuple4d paramTuple4d)
  {
    set(paramTuple4d);
  }

  public Tuple4d(Tuple4f paramTuple4f)
  {
    set(paramTuple4f);
  }

  public Tuple4d()
  {
    this.x = 0.0D;
    this.y = 0.0D;
    this.z = 0.0D;
    this.w = 0.0D;
  }

  public final void set(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    this.x = paramDouble1;
    this.y = paramDouble2;
    this.z = paramDouble3;
    this.w = paramDouble4;
  }

  public final void set(double[] paramArrayOfDouble)
  {
    this.x = paramArrayOfDouble[0];
    this.y = paramArrayOfDouble[1];
    this.z = paramArrayOfDouble[2];
    this.w = paramArrayOfDouble[3];
  }

  public final void set(Tuple4d paramTuple4d)
  {
    this.x = paramTuple4d.x;
    this.y = paramTuple4d.y;
    this.z = paramTuple4d.z;
    this.w = paramTuple4d.w;
  }

  public final void set(Tuple4f paramTuple4f)
  {
    this.x = paramTuple4f.x;
    this.y = paramTuple4f.y;
    this.z = paramTuple4f.z;
    this.w = paramTuple4f.w;
  }

  public final void get(double[] paramArrayOfDouble)
  {
    paramArrayOfDouble[0] = this.x;
    paramArrayOfDouble[1] = this.y;
    paramArrayOfDouble[2] = this.z;
    paramArrayOfDouble[3] = this.w;
  }

  public final void get(Tuple4d paramTuple4d)
  {
    paramTuple4d.x = this.x;
    paramTuple4d.y = this.y;
    paramTuple4d.z = this.z;
    paramTuple4d.w = this.w;
  }

  public final void add(Tuple4d paramTuple4d1, Tuple4d paramTuple4d2)
  {
    paramTuple4d1.x += paramTuple4d2.x;
    paramTuple4d1.y += paramTuple4d2.y;
    paramTuple4d1.z += paramTuple4d2.z;
    paramTuple4d1.w += paramTuple4d2.w;
  }

  public final void add(Tuple4d paramTuple4d)
  {
    this.x += paramTuple4d.x;
    this.y += paramTuple4d.y;
    this.z += paramTuple4d.z;
    this.w += paramTuple4d.w;
  }

  public final void sub(Tuple4d paramTuple4d1, Tuple4d paramTuple4d2)
  {
    paramTuple4d1.x -= paramTuple4d2.x;
    paramTuple4d1.y -= paramTuple4d2.y;
    paramTuple4d1.z -= paramTuple4d2.z;
    this.w = (paramTuple4d1.z - paramTuple4d2.w);
  }

  public final void sub(Tuple4d paramTuple4d)
  {
    this.x -= paramTuple4d.x;
    this.y -= paramTuple4d.y;
    this.z -= paramTuple4d.z;
    this.w -= paramTuple4d.w;
  }

  public final void negate(Tuple4d paramTuple4d)
  {
    this.x = (-paramTuple4d.x);
    this.y = (-paramTuple4d.y);
    this.z = (-paramTuple4d.z);
    this.w = (-paramTuple4d.w);
  }

  public final void negate()
  {
    this.x = (-this.x);
    this.y = (-this.y);
    this.z = (-this.z);
    this.w = (-this.w);
  }

  public final void scale(double paramDouble, Tuple4d paramTuple4d)
  {
    this.x = (paramDouble * paramTuple4d.x);
    this.y = (paramDouble * paramTuple4d.y);
    this.z = (paramDouble * paramTuple4d.z);
    this.w = (paramDouble * paramTuple4d.w);
  }

  public final void scale(double paramDouble)
  {
    this.x *= paramDouble;
    this.y *= paramDouble;
    this.z *= paramDouble;
    this.w *= paramDouble;
  }

  public final void scaleAdd(double paramDouble, Tuple4d paramTuple4d1, Tuple4d paramTuple4d2)
  {
    this.x = (paramDouble * paramTuple4d1.x + paramTuple4d2.x);
    this.y = (paramDouble * paramTuple4d1.y + paramTuple4d2.y);
    this.z = (paramDouble * paramTuple4d1.z + paramTuple4d2.z);
    this.w = (paramDouble * paramTuple4d1.w + paramTuple4d2.w);
  }

  public final void scaleAdd(double paramDouble, Tuple4d paramTuple4d)
  {
    this.x = (paramDouble * this.x + paramTuple4d.x);
    this.y = (paramDouble * this.y + paramTuple4d.y);
    this.z = (paramDouble * this.z + paramTuple4d.z);
    this.w = (paramDouble * this.z + paramTuple4d.w);
  }

  public int hashCode()
  {
    long l1 = Double.doubleToLongBits(this.x);
    long l2 = Double.doubleToLongBits(this.y);
    long l3 = Double.doubleToLongBits(this.z);
    long l4 = Double.doubleToLongBits(this.w);
    return (int)(l1 ^ l1 >> 32 ^ l2 ^ l2 >> 32 ^ l3 ^ l3 >> 32 ^ l4 ^ l4 >> 32);
  }

  public boolean equals(Tuple4d paramTuple4d)
  {
    return (paramTuple4d != null) && (this.x == paramTuple4d.x) && (this.y == paramTuple4d.y) && (this.z == paramTuple4d.z) && (this.w == paramTuple4d.w);
  }

  public boolean epsilonEquals(Tuple4d paramTuple4d, double paramDouble)
  {
    return (Math.abs(paramTuple4d.x - this.x) <= paramDouble) && (Math.abs(paramTuple4d.y - this.y) <= paramDouble) && (Math.abs(paramTuple4d.z - this.z) <= paramDouble) && (Math.abs(paramTuple4d.w - this.w) <= paramDouble);
  }

  public String toString()
  {
    return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
  }

  public final void clamp(double paramDouble1, double paramDouble2, Tuple4d paramTuple4d)
  {
    set(paramTuple4d);
    clamp(paramDouble1, paramDouble2);
  }

  public final void clampMin(double paramDouble, Tuple4d paramTuple4d)
  {
    set(paramTuple4d);
    clampMin(paramDouble);
  }

  public final void clampMax(double paramDouble, Tuple4d paramTuple4d)
  {
    set(paramTuple4d);
    clampMax(paramDouble);
  }

  public final void absolute(Tuple4d paramTuple4d)
  {
    set(paramTuple4d);
    absolute();
  }

  public final void clamp(double paramDouble1, double paramDouble2)
  {
    clampMin(paramDouble1);
    clampMax(paramDouble2);
  }

  public final void clampMin(double paramDouble)
  {
    if (this.x < paramDouble)
      this.x = paramDouble;
    if (this.y < paramDouble)
      this.y = paramDouble;
    if (this.z < paramDouble)
      this.z = paramDouble;
    if (this.w < paramDouble)
      this.w = paramDouble;
  }

  public final void clampMax(double paramDouble)
  {
    if (this.x > paramDouble)
      this.x = paramDouble;
    if (this.y > paramDouble)
      this.y = paramDouble;
    if (this.z > paramDouble)
      this.z = paramDouble;
    if (this.w > paramDouble)
      this.w = paramDouble;
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

  public final void interpolate(Tuple4d paramTuple4d1, Tuple4d paramTuple4d2, double paramDouble)
  {
    paramTuple4d1.x += (paramTuple4d2.x - paramTuple4d1.x) * paramDouble;
    paramTuple4d1.y += (paramTuple4d2.y - paramTuple4d1.y) * paramDouble;
    paramTuple4d1.z += (paramTuple4d2.z - paramTuple4d1.z) * paramDouble;
    paramTuple4d1.w += (paramTuple4d2.w - paramTuple4d1.w) * paramDouble;
  }

  public final void interpolate(Tuple4d paramTuple4d, double paramDouble)
  {
    this.x += paramDouble * (paramTuple4d.x - this.x);
    this.y += paramDouble * (paramTuple4d.y - this.y);
    this.z += paramDouble * (paramTuple4d.z - this.z);
    this.w += paramDouble * (paramTuple4d.w - this.w);
  }
}