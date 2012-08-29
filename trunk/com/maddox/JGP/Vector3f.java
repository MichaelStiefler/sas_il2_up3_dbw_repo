package com.maddox.JGP;

import java.io.Serializable;

public class Vector3f extends Tuple3f
  implements Serializable, Cloneable
{
  public Vector3f(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super(paramFloat1, paramFloat2, paramFloat3);
  }

  public Vector3f(float[] paramArrayOfFloat)
  {
    super(paramArrayOfFloat);
  }

  public Vector3f(Tuple3d paramTuple3d)
  {
    super(paramTuple3d);
  }

  public Vector3f(Tuple3f paramTuple3f)
  {
    super(paramTuple3f);
  }

  public Vector3f()
  {
  }

  public final float lengthSquared()
  {
    return this.x * this.x + this.y * this.y + this.z * this.z;
  }

  public final float length()
  {
    return (float)Math.sqrt(lengthSquared());
  }

  public final void cross(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    set(paramTuple3f1.y * paramTuple3f2.z - paramTuple3f1.z * paramTuple3f2.y, paramTuple3f1.z * paramTuple3f2.x - paramTuple3f1.x * paramTuple3f2.z, paramTuple3f1.x * paramTuple3f2.y - paramTuple3f1.y * paramTuple3f2.x);
  }

  public final float dot(Tuple3f paramTuple3f)
  {
    return this.x * paramTuple3f.x + this.y * paramTuple3f.y + this.z * paramTuple3f.z;
  }

  public final float normalize(Tuple3f paramTuple3f)
  {
    set(paramTuple3f);
    return normalize();
  }

  public final float normalize()
  {
    float f = Math.max(length(), 1.0E-035F);
    this.x /= f;
    this.y /= f;
    this.z /= f;
    return f;
  }

  public final float angle(Vector3f paramVector3f)
  {
    double d1 = this.y * paramVector3f.z - this.z * paramVector3f.y;
    double d2 = this.z * paramVector3f.x - this.x * paramVector3f.z;
    double d3 = this.x * paramVector3f.y - this.y * paramVector3f.x;
    double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);

    return (float)Math.abs(Math.atan2(d4, dot(paramVector3f)));
  }
}