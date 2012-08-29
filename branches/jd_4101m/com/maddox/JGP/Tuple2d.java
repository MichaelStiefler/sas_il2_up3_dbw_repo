package com.maddox.JGP;

import java.io.Serializable;

public abstract class Tuple2d
  implements Serializable, Cloneable
{
  public double x;
  public double y;

  public Tuple2d(double paramDouble1, double paramDouble2)
  {
    this.x = paramDouble1;
    this.y = paramDouble2;
  }

  public Tuple2d(double[] paramArrayOfDouble)
  {
    this.x = paramArrayOfDouble[0];
    this.y = paramArrayOfDouble[1];
  }

  public Tuple2d(Tuple2d paramTuple2d)
  {
    this.x = paramTuple2d.x;
    this.y = paramTuple2d.y;
  }

  public Tuple2d(Tuple2f paramTuple2f)
  {
    this.x = paramTuple2f.x;
    this.y = paramTuple2f.y;
  }

  public Tuple2d()
  {
    this.x = 0.0D;
    this.y = 0.0D;
  }

  public final void set(double paramDouble1, double paramDouble2)
  {
    this.x = paramDouble1;
    this.y = paramDouble2;
  }

  public final void set(double[] paramArrayOfDouble)
  {
    this.x = paramArrayOfDouble[0];
    this.y = paramArrayOfDouble[1];
  }

  public final void set(Tuple2d paramTuple2d)
  {
    this.x = paramTuple2d.x;
    this.y = paramTuple2d.y;
  }

  public final void set(Tuple2f paramTuple2f)
  {
    this.x = paramTuple2f.x;
    this.y = paramTuple2f.y;
  }

  public final void set(Tuple3d paramTuple3d)
  {
    this.x = paramTuple3d.x;
    this.y = paramTuple3d.y;
  }

  public final void get(double[] paramArrayOfDouble)
  {
    paramArrayOfDouble[0] = this.x;
    paramArrayOfDouble[1] = this.y;
  }

  public final void add(Tuple2d paramTuple2d1, Tuple2d paramTuple2d2)
  {
    paramTuple2d1.x += paramTuple2d2.x;
    paramTuple2d1.y += paramTuple2d2.y;
  }

  public final void add(double paramDouble1, double paramDouble2)
  {
    this.x += paramDouble1;
    this.y += paramDouble2;
  }

  public final void add(Tuple2d paramTuple2d)
  {
    this.x += paramTuple2d.x;
    this.y += paramTuple2d.y;
  }

  public final void sub(Tuple2d paramTuple2d1, Tuple2d paramTuple2d2)
  {
    paramTuple2d1.x -= paramTuple2d2.x;
    paramTuple2d1.y -= paramTuple2d2.y;
  }

  public final void sub(double paramDouble1, double paramDouble2)
  {
    this.x -= paramDouble1;
    this.y -= paramDouble2;
  }

  public final void sub(Tuple2d paramTuple2d)
  {
    this.x -= paramTuple2d.x;
    this.y -= paramTuple2d.y;
  }

  public final void negate(Tuple2d paramTuple2d)
  {
    this.x = (-paramTuple2d.x);
    this.y = (-paramTuple2d.y);
  }

  public final void negate()
  {
    this.x = (-this.x);
    this.y = (-this.y);
  }

  public final void scale(double paramDouble, Tuple2d paramTuple2d)
  {
    this.x = (paramDouble * paramTuple2d.x);
    this.y = (paramDouble * paramTuple2d.y);
  }

  public final void scale(double paramDouble)
  {
    this.x *= paramDouble;
    this.y *= paramDouble;
  }

  public final void scaleAdd(double paramDouble, Tuple2d paramTuple2d1, Tuple2d paramTuple2d2)
  {
    this.x = (paramDouble * paramTuple2d1.x + paramTuple2d2.x);
    this.y = (paramDouble * paramTuple2d1.y + paramTuple2d2.y);
  }

  public final void scaleAdd(double paramDouble, Tuple2d paramTuple2d)
  {
    this.x = (paramDouble * this.x + paramTuple2d.x);
    this.y = (paramDouble * this.y + paramTuple2d.y);
  }

  public int hashCode()
  {
    long l1 = Double.doubleToLongBits(this.x);
    long l2 = Double.doubleToLongBits(this.y);
    return (int)(l1 ^ l1 >> 32 ^ l2 ^ l2 >> 32);
  }

  public boolean equals(Tuple2d paramTuple2d)
  {
    return (paramTuple2d != null) && (this.x == paramTuple2d.x) && (this.y == paramTuple2d.y);
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof Tuple2d)) && (equals((Tuple2d)paramObject));
  }

  public boolean epsilonEquals(Tuple2d paramTuple2d, double paramDouble)
  {
    return (Math.abs(paramTuple2d.x - this.x) <= paramDouble) && (Math.abs(paramTuple2d.y - this.y) <= paramDouble);
  }

  public String toString()
  {
    return "(" + this.x + ", " + this.y + ")";
  }

  public final void clamp(double paramDouble1, double paramDouble2, Tuple2d paramTuple2d)
  {
    set(paramTuple2d);
    clamp(paramDouble1, paramDouble2);
  }

  public final void clampMin(double paramDouble, Tuple2d paramTuple2d)
  {
    set(paramTuple2d);
    clampMin(paramDouble);
  }

  public final void clampMax(double paramDouble, Tuple2d paramTuple2d)
  {
    set(paramTuple2d);
    clampMax(paramDouble);
  }

  public final void absolute(Tuple2d paramTuple2d)
  {
    set(paramTuple2d);
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
  }

  public final void clampMax(double paramDouble)
  {
    if (this.x > paramDouble)
      this.x = paramDouble;
    if (this.y > paramDouble)
      this.y = paramDouble;
  }

  public final void absolute()
  {
    if (this.x < 0.0D)
      this.x = (-this.x);
    if (this.y < 0.0D)
      this.y = (-this.y);
  }

  public final void interpolate(Tuple2d paramTuple2d1, Tuple2d paramTuple2d2, double paramDouble)
  {
    paramTuple2d1.x += (paramTuple2d2.x - paramTuple2d1.x) * paramDouble;
    paramTuple2d1.y += (paramTuple2d2.y - paramTuple2d1.y) * paramDouble;
  }

  public final void interpolate(Tuple2d paramTuple2d, double paramDouble)
  {
    this.x += paramDouble * (paramTuple2d.x - this.x);
    this.y += paramDouble * (paramTuple2d.y - this.y);
  }
}