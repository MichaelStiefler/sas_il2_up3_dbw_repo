package com.maddox.JGP;

import java.io.Serializable;

public class Quat4f extends Tuple4f
  implements Serializable, Cloneable
{
  private static final Matrix3f MQ = new Matrix3f();

  private static Vector3f tmp1 = new Vector3f();
  private static Vector3f tmp2 = new Vector3f();
  private static Vector3f axis = new Vector3f();

  public Quat4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    super(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }

  public Quat4f(float[] paramArrayOfFloat)
  {
    super(paramArrayOfFloat);
  }

  public Quat4f(Quat4f paramQuat4f)
  {
    super(paramQuat4f);
  }

  public Quat4f(Quat4d paramQuat4d)
  {
    super(paramQuat4d);
  }

  public Quat4f(Tuple4d paramTuple4d)
  {
    super(paramTuple4d);
  }

  public Quat4f(Tuple4f paramTuple4f)
  {
    super(paramTuple4f);
  }

  public Quat4f()
  {
  }

  public Quat4f(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    set(paramTuple3f1, paramTuple3f2);
  }

  public final void setIdent()
  {
    this.x = 0.0F;
    this.y = 0.0F;
    this.z = 0.0F;
    this.w = 1.0F;
  }

  public final void conjugate(Quat4f paramQuat4f)
  {
    this.x = (-paramQuat4f.x);
    this.y = (-paramQuat4f.y);
    this.z = (-paramQuat4f.z);
    this.w = paramQuat4f.w;
  }

  public final void conjugate()
  {
    this.x = (-this.x);
    this.y = (-this.y);
    this.z = (-this.z);
  }

  public final void mul(Quat4f paramQuat4f1, Quat4f paramQuat4f2)
  {
    set(paramQuat4f1.x * paramQuat4f2.w + paramQuat4f1.w * paramQuat4f2.x + paramQuat4f1.y * paramQuat4f2.z - paramQuat4f1.z * paramQuat4f2.y, paramQuat4f1.y * paramQuat4f2.w + paramQuat4f1.w * paramQuat4f2.y + paramQuat4f1.z * paramQuat4f2.x - paramQuat4f1.x * paramQuat4f2.z, paramQuat4f1.z * paramQuat4f2.w + paramQuat4f1.w * paramQuat4f2.z + paramQuat4f1.x * paramQuat4f2.y - paramQuat4f1.y * paramQuat4f2.x, paramQuat4f1.w * paramQuat4f2.w - paramQuat4f1.x * paramQuat4f2.x - paramQuat4f1.y * paramQuat4f2.y - paramQuat4f1.z * paramQuat4f2.z);
  }

  public final void mul(Quat4f paramQuat4f)
  {
    set(this.x * paramQuat4f.w + this.w * paramQuat4f.x + this.y * paramQuat4f.z - this.z * paramQuat4f.y, this.y * paramQuat4f.w + this.w * paramQuat4f.y + this.z * paramQuat4f.x - this.x * paramQuat4f.z, this.z * paramQuat4f.w + this.w * paramQuat4f.z + this.x * paramQuat4f.y - this.y * paramQuat4f.x, this.w * paramQuat4f.w - this.x * paramQuat4f.x - this.y * paramQuat4f.y - this.z * paramQuat4f.z);
  }

  public final void mulInverse(Quat4f paramQuat4f1, Quat4f paramQuat4f2)
  {
    float f = norm();

    f = f == 0.0D ? f : 1.0F / f;

    set((paramQuat4f1.x * paramQuat4f2.w - paramQuat4f1.w * paramQuat4f2.x - paramQuat4f1.y * paramQuat4f2.z + paramQuat4f1.z * paramQuat4f2.y) * f, (paramQuat4f1.y * paramQuat4f2.w - paramQuat4f1.w * paramQuat4f2.y - paramQuat4f1.z * paramQuat4f2.x + paramQuat4f1.x * paramQuat4f2.z) * f, (paramQuat4f1.z * paramQuat4f2.w - paramQuat4f1.w * paramQuat4f2.z - paramQuat4f1.x * paramQuat4f2.y + paramQuat4f1.y * paramQuat4f2.x) * f, (paramQuat4f1.w * paramQuat4f2.w + paramQuat4f1.x * paramQuat4f2.x + paramQuat4f1.y * paramQuat4f2.y + paramQuat4f1.z * paramQuat4f2.z) * f);
  }

  public final void mulInverse(Quat4f paramQuat4f)
  {
    float f = norm();

    f = f == 0.0D ? f : 1.0F / f;

    set((this.x * paramQuat4f.w - this.w * paramQuat4f.x - this.y * paramQuat4f.z + this.z * paramQuat4f.y) * f, (this.y * paramQuat4f.w - this.w * paramQuat4f.y - this.z * paramQuat4f.x + this.x * paramQuat4f.z) * f, (this.z * paramQuat4f.w - this.w * paramQuat4f.z - this.x * paramQuat4f.y + this.y * paramQuat4f.x) * f, (this.w * paramQuat4f.w + this.x * paramQuat4f.x + this.y * paramQuat4f.y + this.z * paramQuat4f.z) * f);
  }

  private final float norm()
  {
    return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
  }

  public final void inverse(Quat4f paramQuat4f)
  {
    float f = paramQuat4f.norm();

    this.x = (-paramQuat4f.x / f);
    this.y = (-paramQuat4f.y / f);
    this.z = (-paramQuat4f.z / f);
    paramQuat4f.w /= f;
  }

  public final void inverse()
  {
    float f = norm();

    this.x = (-this.x / f);
    this.y = (-this.y / f);
    this.z = (-this.z / f);
    this.w /= f;
  }

  public final void normalize(Quat4f paramQuat4f)
  {
    float f = (float)Math.sqrt(paramQuat4f.norm());

    paramQuat4f.x /= f;
    paramQuat4f.y /= f;
    paramQuat4f.z /= f;
    paramQuat4f.w /= f;
  }

  public final void normalize()
  {
    float f = (float)Math.sqrt(norm());

    this.x /= f;
    this.y /= f;
    this.z /= f;
    this.w /= f;
  }

  public final void set(Matrix4f paramMatrix4f)
  {
    setFromMat(paramMatrix4f.m00, paramMatrix4f.m01, paramMatrix4f.m02, paramMatrix4f.m10, paramMatrix4f.m11, paramMatrix4f.m12, paramMatrix4f.m20, paramMatrix4f.m21, paramMatrix4f.m22);
  }

  public final void set(Matrix4d paramMatrix4d)
  {
    setFromMat((float)paramMatrix4d.m00, (float)paramMatrix4d.m01, (float)paramMatrix4d.m02, (float)paramMatrix4d.m10, (float)paramMatrix4d.m11, (float)paramMatrix4d.m12, (float)paramMatrix4d.m20, (float)paramMatrix4d.m21, (float)paramMatrix4d.m22);
  }

  public final void set(Matrix3f paramMatrix3f)
  {
    setFromMat(paramMatrix3f.m00, paramMatrix3f.m01, paramMatrix3f.m02, paramMatrix3f.m10, paramMatrix3f.m11, paramMatrix3f.m12, paramMatrix3f.m20, paramMatrix3f.m21, paramMatrix3f.m22);
  }

  public final void set(Matrix3d paramMatrix3d)
  {
    setFromMat((float)paramMatrix3d.m00, (float)paramMatrix3d.m01, (float)paramMatrix3d.m02, (float)paramMatrix3d.m10, (float)paramMatrix3d.m11, (float)paramMatrix3d.m12, (float)paramMatrix3d.m20, (float)paramMatrix3d.m21, (float)paramMatrix3d.m22);
  }

  public final void set(AxisAngle4f paramAxisAngle4f)
  {
    this.x = paramAxisAngle4f.x;
    this.y = paramAxisAngle4f.y;
    this.z = paramAxisAngle4f.z;
    float f1 = (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z) + 1.0E-035F;

    float f2 = (float)(Math.sin(0.5D * paramAxisAngle4f.angle) / f1);
    this.x *= f2;
    this.y *= f2;
    this.z *= f2;
    this.w = (float)Math.cos(0.5D * paramAxisAngle4f.angle);
  }

  public void set(Vector3f paramVector3f)
  {
    float f1 = paramVector3f.length() + 1.0E-035F;
    float f2 = (float)(Math.sin(0.5D * f1) / f1);
    this.x = (paramVector3f.x * f2);
    this.y = (paramVector3f.y * f2);
    this.z = (paramVector3f.z * f2);
    this.w = (float)Math.cos(0.5D * f1);
  }

  public final void set(AxisAngle4d paramAxisAngle4d)
  {
    this.x = (float)paramAxisAngle4d.x;
    this.y = (float)paramAxisAngle4d.y;
    this.z = (float)paramAxisAngle4d.z;
    float f1 = (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);

    float f2 = (float)(Math.sin(0.5D * paramAxisAngle4d.angle) / f1);
    this.x *= f2;
    this.y *= f2;
    this.z *= f2;
    this.w = (float)Math.cos(0.5D * paramAxisAngle4d.angle);
  }

  public final void interpolate(Quat4f paramQuat4f, float paramFloat)
  {
    normalize();
    float f1 = (float)Math.sqrt(paramQuat4f.norm());

    float f2 = paramQuat4f.x / f1;
    float f3 = paramQuat4f.y / f1;
    float f4 = paramQuat4f.z / f1;
    float f5 = paramQuat4f.w / f1;

    float f6 = this.x * f2 + this.y * f3 + this.z * f4 + this.w * f5;

    if (f6 < 0.0F) { f6 = -f6;
      f2 = -f2; f3 = -f3; f4 = -f4; f5 = -f5;
    }

    if (f6 >= 1.0F) return;

    f6 = (float)Math.acos(f6);

    float f7 = (float)Math.sin(f6);

    if (f7 == 0.0F) return;

    float f8 = (float)Math.sin((1.0D - paramFloat) * f6) / f7;
    f6 = (float)Math.sin(paramFloat * f6) / f7;

    this.x = (f8 * this.x + f6 * f2);
    this.y = (f8 * this.y + f6 * f3);
    this.z = (f8 * this.z + f6 * f4);
    this.w = (f8 * this.w + f6 * f5);
  }

  public final void interpolate(Quat4f paramQuat4f1, Quat4f paramQuat4f2, float paramFloat)
  {
    if (this != paramQuat4f2) {
      set(paramQuat4f1);
      interpolate(paramQuat4f2, paramFloat);
    } else {
      interpolate(paramQuat4f1, 1.0F - paramFloat);
    }
  }

  private void setFromMat(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    float f2 = paramFloat1 + paramFloat5 + paramFloat9;
    float f1;
    if (f2 >= 0.0D) {
      f1 = (float)Math.sqrt(f2 + 1.0D);
      this.w = (float)(f1 * 0.5D);
      f1 = 0.5F / f1;
      this.x = ((paramFloat8 - paramFloat6) * f1);
      this.y = ((paramFloat3 - paramFloat7) * f1);
      this.z = ((paramFloat4 - paramFloat2) * f1);
    } else {
      float f3 = Math.max(Math.max(paramFloat1, paramFloat5), paramFloat9);
      if (f3 == paramFloat1) {
        f1 = (float)Math.sqrt(paramFloat1 - (paramFloat5 + paramFloat9) + 1.0D);
        this.x = (float)(f1 * 0.5D);
        f1 = 0.5F / f1;
        this.y = ((paramFloat2 + paramFloat4) * f1);
        this.z = ((paramFloat7 + paramFloat3) * f1);
        this.w = ((paramFloat8 - paramFloat6) * f1);
      } else if (f3 == paramFloat5) {
        f1 = (float)Math.sqrt(paramFloat5 - (paramFloat9 + paramFloat1) + 1.0D);
        this.y = (float)(f1 * 0.5D);
        f1 = 0.5F / f1;
        this.z = ((paramFloat6 + paramFloat8) * f1);
        this.x = ((paramFloat2 + paramFloat4) * f1);
        this.w = ((paramFloat3 - paramFloat7) * f1);
      } else {
        f1 = (float)Math.sqrt(paramFloat9 - (paramFloat1 + paramFloat5) + 1.0D);
        this.z = (float)(f1 * 0.5D);
        f1 = 0.5F / f1;
        this.x = ((paramFloat7 + paramFloat3) * f1);
        this.y = ((paramFloat6 + paramFloat8) * f1);
        this.w = ((paramFloat4 - paramFloat2) * f1);
      }
    }
  }

  public final void set(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    tmp1.normalize(paramTuple3f1);
    tmp2.normalize(paramTuple3f2);

    float f1 = tmp1.dot(tmp2);
    float f2 = (float)Math.acos(f1);
    float f3 = Math.abs(f2);

    if (f3 < 1.0E-004F) {
      setIdent(); return;
    }
    if (f3 > 3.141493F)
    {
      axis.set(1.0F, 0.0F, 0.0F);
      if (axis.dot(tmp1) < 0.1F) {
        axis.cross(axis, tmp1);
        axis.normalize();
      } else {
        axis.set(0.0F, 0.0F, 1.0F);
      }
    }
    else {
      axis.cross(tmp1, tmp2);
      axis.normalize();
    }
    axis.scale(f2);
    set(axis);
  }

  public void setEulers(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = (float)Math.sin(paramFloat1 * 0.5F);
    float f2 = (float)Math.cos(paramFloat1 * 0.5F);
    float f3 = (float)Math.sin(paramFloat2 * 0.5F);
    float f4 = (float)Math.cos(paramFloat2 * 0.5F);
    float f5 = (float)Math.sin(paramFloat3 * 0.5F);
    float f6 = (float)Math.cos(paramFloat3 * 0.5F);

    this.x = (f2 * f4 * f5 - f1 * f3 * f6);
    this.y = (f2 * f3 * f6 + f1 * f4 * f5);
    this.z = (f1 * f4 * f6 - f2 * f3 * f5);
    this.w = (f2 * f4 * f6 + f1 * f3 * f5);
  }

  public void getEulers(float[] paramArrayOfFloat)
  {
    MQ.set(this);
    MQ.getEulers(paramArrayOfFloat);
  }
}