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
    return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
  }

  public final double length()
  {
    return Math.sqrt(lengthSquared());
  }

  public final double dot(Tuple4d paramTuple4d)
  {
    return this.x * paramTuple4d.x + this.y * paramTuple4d.y + this.z * paramTuple4d.z + this.w * paramTuple4d.w;
  }

  public final void normalize(Tuple4d paramTuple4d)
  {
    set(paramTuple4d);
    normalize();
  }

  public final void normalize()
  {
    double d = length();

    this.x /= d;
    this.y /= d;
    this.z /= d;
    this.w /= d;
  }

  public final double angle(Vector4d paramVector4d)
  {
    double d1 = dot(paramVector4d);
    double d2 = paramVector4d.length();
    double d3 = length();

    return Math.acos(d1 / d2 / d3);
  }
}