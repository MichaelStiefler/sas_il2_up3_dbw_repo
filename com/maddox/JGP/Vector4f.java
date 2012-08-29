package com.maddox.JGP;

import java.io.Serializable;

public class Vector4f extends Tuple4f
  implements Serializable, Cloneable
{
  public Vector4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    super(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }

  public Vector4f(float[] paramArrayOfFloat)
  {
    super(paramArrayOfFloat);
  }

  public Vector4f(Tuple4d paramTuple4d)
  {
    super(paramTuple4d);
  }

  public Vector4f(Tuple4f paramTuple4f)
  {
    super(paramTuple4f);
  }

  public Vector4f()
  {
  }

  public final float lengthSquared()
  {
    return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
  }

  public final float length()
  {
    return (float)Math.sqrt(lengthSquared());
  }

  public final float dot(Tuple4f paramTuple4f)
  {
    return this.x * paramTuple4f.x + this.y * paramTuple4f.y + this.z * paramTuple4f.z + this.w * paramTuple4f.w;
  }

  public final void normalize(Tuple4d paramTuple4d)
  {
    set(paramTuple4d);
    normalize();
  }

  public final void normalize()
  {
    double d = length();

    this.x = (float)(this.x / d);
    this.y = (float)(this.y / d);
    this.z = (float)(this.z / d);
    this.w = (float)(this.w / d);
  }

  public final float angle(Vector4f paramVector4f)
  {
    double d1 = dot(paramVector4f);
    double d2 = paramVector4f.length();
    double d3 = length();

    return (float)Math.acos(d1 / d2 / d3);
  }
}