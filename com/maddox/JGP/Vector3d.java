package com.maddox.JGP;

import java.io.Serializable;

public class Vector3d extends Tuple3d
  implements Serializable, Cloneable
{
  public Vector3d(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    super(paramDouble1, paramDouble2, paramDouble3);
  }

  public Vector3d(double[] paramArrayOfDouble)
  {
    super(paramArrayOfDouble);
  }

  public Vector3d(Tuple3d paramTuple3d)
  {
    super(paramTuple3d);
  }

  public Vector3d(Tuple3f paramTuple3f)
  {
    super(paramTuple3f);
  }

  public Vector3d()
  {
  }

  public final void cross(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2)
  {
    set(paramTuple3d1.y * paramTuple3d2.z - paramTuple3d1.z * paramTuple3d2.y, paramTuple3d1.z * paramTuple3d2.x - paramTuple3d1.x * paramTuple3d2.z, paramTuple3d1.x * paramTuple3d2.y - paramTuple3d1.y * paramTuple3d2.x);
  }

  public final double normalize(Tuple3d paramTuple3d)
  {
    set(paramTuple3d);
    return normalize();
  }

  public final double normalize()
  {
    double d = Math.max(length(), 1.0E-075D);
    this.x /= d;
    this.y /= d;
    this.z /= d;
    return d;
  }

  public final double dot(Tuple3d paramTuple3d)
  {
    return this.x * paramTuple3d.x + this.y * paramTuple3d.y + this.z * paramTuple3d.z;
  }

  public final double lengthSquared()
  {
    return this.x * this.x + this.y * this.y + this.z * this.z;
  }

  public final double length()
  {
    return Math.sqrt(lengthSquared());
  }

  public final double angle(Vector3d paramVector3d)
  {
    double d1 = this.y * paramVector3d.z - this.z * paramVector3d.y;
    double d2 = this.z * paramVector3d.x - this.x * paramVector3d.z;
    double d3 = this.x * paramVector3d.y - this.y * paramVector3d.x;
    double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);

    return Math.abs(Math.atan2(d4, dot(paramVector3d)));
  }
}