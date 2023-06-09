package com.maddox.JGP;

import java.io.Serializable;

public class AxisAngle4d
  implements Serializable, Cloneable
{
  public double x;
  public double y;
  public double z;
  public double angle;
  private static Matrix3d Mr = new Matrix3d();

  public AxisAngle4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    set(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
  }

  public AxisAngle4d(double[] paramArrayOfDouble)
  {
    set(paramArrayOfDouble);
  }

  public AxisAngle4d(AxisAngle4d paramAxisAngle4d)
  {
    set(paramAxisAngle4d);
  }

  public AxisAngle4d(AxisAngle4f paramAxisAngle4f)
  {
    set(paramAxisAngle4f);
  }

  public AxisAngle4d()
  {
    this.x = 0.0D;
    this.y = 0.0D;
    this.z = 1.0D;
    this.angle = 0.0D;
  }

  public final void set(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    this.x = paramDouble1;
    this.y = paramDouble2;
    this.z = paramDouble3;
    this.angle = paramDouble4;
  }

  public final void set(double[] paramArrayOfDouble)
  {
    this.x = paramArrayOfDouble[0];
    this.y = paramArrayOfDouble[1];
    this.z = paramArrayOfDouble[2];
    this.angle = paramArrayOfDouble[3];
  }

  public final void set(AxisAngle4d paramAxisAngle4d)
  {
    this.x = paramAxisAngle4d.x;
    this.y = paramAxisAngle4d.y;
    this.z = paramAxisAngle4d.z;
    this.angle = paramAxisAngle4d.angle;
  }

  public final void set(AxisAngle4f paramAxisAngle4f)
  {
    this.x = paramAxisAngle4f.x;
    this.y = paramAxisAngle4f.y;
    this.z = paramAxisAngle4f.z;
    this.angle = paramAxisAngle4f.angle;
  }

  public final void get(double[] paramArrayOfDouble)
  {
    paramArrayOfDouble[0] = this.x;
    paramArrayOfDouble[1] = this.y;
    paramArrayOfDouble[2] = this.z;
    paramArrayOfDouble[3] = this.angle;
  }

  public final void set(Matrix4f paramMatrix4f)
  {
    setFromMat(paramMatrix4f.m00, paramMatrix4f.m01, paramMatrix4f.m02, paramMatrix4f.m10, paramMatrix4f.m11, paramMatrix4f.m12, paramMatrix4f.m20, paramMatrix4f.m21, paramMatrix4f.m22);
  }

  public final void set(Matrix4d paramMatrix4d)
  {
    setFromMat(paramMatrix4d.m00, paramMatrix4d.m01, paramMatrix4d.m02, paramMatrix4d.m10, paramMatrix4d.m11, paramMatrix4d.m12, paramMatrix4d.m20, paramMatrix4d.m21, paramMatrix4d.m22);
  }

  public final void set(Matrix3f paramMatrix3f)
  {
    setFromMat(paramMatrix3f.m00, paramMatrix3f.m01, paramMatrix3f.m02, paramMatrix3f.m10, paramMatrix3f.m11, paramMatrix3f.m12, paramMatrix3f.m20, paramMatrix3f.m21, paramMatrix3f.m22);
  }

  public final void set(Matrix3d paramMatrix3d)
  {
    setFromMat(paramMatrix3d.m00, paramMatrix3d.m01, paramMatrix3d.m02, paramMatrix3d.m10, paramMatrix3d.m11, paramMatrix3d.m12, paramMatrix3d.m20, paramMatrix3d.m21, paramMatrix3d.m22);
  }

  public final void set(Quat4f paramQuat4f)
  {
    setFromQuat(paramQuat4f.x, paramQuat4f.y, paramQuat4f.z, paramQuat4f.w);
  }

  public final void set(Quat4d paramQuat4d)
  {
    setFromQuat(paramQuat4d.x, paramQuat4d.y, paramQuat4d.z, paramQuat4d.w);
  }

  private void setFromMat(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9)
  {
    double d1 = (paramDouble1 + paramDouble5 + paramDouble9 - 1.0D) * 0.5D;
    this.x = (paramDouble8 - paramDouble6);
    this.y = (paramDouble3 - paramDouble7);
    this.z = (paramDouble4 - paramDouble2);
    double d2 = 0.5D * Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    this.angle = Math.atan2(d2, d1);
  }

  private void setFromQuat(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    double d = Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2 + paramDouble3 * paramDouble3);
    this.angle = (2.0D * Math.atan2(d, paramDouble4));
    this.x = paramDouble1;
    this.y = paramDouble2;
    this.z = paramDouble3;
  }

  public String toString()
  {
    return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.angle + ")";
  }

  public boolean equals(AxisAngle4d paramAxisAngle4d)
  {
    return (paramAxisAngle4d != null) && (this.x == paramAxisAngle4d.x) && (this.y == paramAxisAngle4d.y) && (this.z == paramAxisAngle4d.z) && (this.angle == paramAxisAngle4d.angle);
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof AxisAngle4d)) && (equals((AxisAngle4d)paramObject));
  }

  public boolean epsilonEquals(AxisAngle4d paramAxisAngle4d, double paramDouble)
  {
    return (Math.abs(paramAxisAngle4d.x - this.x) <= paramDouble) && (Math.abs(paramAxisAngle4d.y - this.y) <= paramDouble) && (Math.abs(paramAxisAngle4d.z - this.z) <= paramDouble) && (Math.abs(paramAxisAngle4d.angle - this.angle) <= paramDouble);
  }

  public int hashCode()
  {
    long l1 = Double.doubleToLongBits(this.x);
    long l2 = Double.doubleToLongBits(this.y);
    long l3 = Double.doubleToLongBits(this.z);
    long l4 = Double.doubleToLongBits(this.angle);
    return (int)(l1 ^ l1 >> 32 ^ l2 ^ l2 >> 32 ^ l3 ^ l3 >> 32 ^ l4 ^ l4 >> 32);
  }

  public void set(Vector3d paramVector3d)
  {
    double d1 = paramVector3d.length();
    double d2;
    if (d1 != 0.0D) d2 = 1.0D / d1; else d2 = 0.0D;
    this.x = (paramVector3d.x * d2);
    this.y = (paramVector3d.y * d2);
    this.z = (paramVector3d.z * d2);
    this.angle = d1;
  }

  public void get(Vector3d paramVector3d)
  {
    if (this.angle >= 0.0D) {
      paramVector3d.x = (this.x * this.angle);
      paramVector3d.y = (this.y * this.angle);
      paramVector3d.z = (this.z * this.angle);
    } else {
      paramVector3d.x = (-this.x * this.angle);
      paramVector3d.y = (-this.y * this.angle);
      paramVector3d.z = (-this.z * this.angle);
    }
  }

  private void makeMatrix()
  {
    double d1 = Math.sin(this.angle);
    double d2 = Math.cos(this.angle);
    double d3 = 1.0D - d2;
    double d4 = this.x; double d5 = this.y; double d6 = this.z;
    double d8 = d4 * d3;
    double d7 = d4 * d4;
    Mr.m00 = (d7 + d2 * (1.0D - d7));
    Mr.m01 = (d8 * d5 + d6 * d1);
    Mr.m02 = (d8 * d6 - d5 * d1);
    d7 = d5 * d5;
    Mr.m10 = (d8 * d5 - d6 * d1);
    Mr.m11 = (d7 + d2 * (1.0D - d7));
    Mr.m12 = (d5 * d3 * d6 + d4 * d1);
    d7 = d6 * d6;
    Mr.m20 = (d8 * d6 + d5 * d1);
    Mr.m21 = (d5 * d3 * d6 - d4 * d1);
    Mr.m22 = (d7 + d2 * (1.0D - d7));
  }

  public void rotate(Vector3d paramVector3d)
  {
    makeMatrix();
    Mr.transform(paramVector3d);
  }

  private void makeMatrixRightHanded()
  {
    double d1 = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);

    d1 = 1.0D / d1;
    this.x *= d1;
    this.y *= d1;
    this.z *= d1;
    double d2 = Math.cos(this.angle);
    double d3 = Math.sin(this.angle);
    double d4 = 1.0D - d2;
    Mr.m00 = (d2 + this.x * this.x * d4);
    Mr.m11 = (d2 + this.y * this.y * d4);
    Mr.m22 = (d2 + this.z * this.z * d4);

    double d5 = this.x * this.y * d4;
    double d6 = this.z * d3;
    Mr.m01 = (d5 - d6);
    Mr.m10 = (d5 + d6);

    d5 = this.x * this.z * d4;
    d6 = this.y * d3;
    Mr.m02 = (d5 + d6);
    Mr.m20 = (d5 - d6);

    d5 = this.y * this.z * d4;
    d6 = this.x * d3;
    Mr.m12 = (d5 - d6);
    Mr.m21 = (d5 + d6);
  }

  public void rotateRightHanded(Vector3d paramVector3d)
  {
    makeMatrixRightHanded();
    Mr.transform(paramVector3d);
  }
}