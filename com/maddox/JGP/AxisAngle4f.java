package com.maddox.JGP;

import java.io.Serializable;

public class AxisAngle4f
  implements Serializable, Cloneable
{
  public float x;
  public float y;
  public float z;
  public float angle;
  private static Matrix3f Mr = new Matrix3f();

  public AxisAngle4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    set(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }

  public AxisAngle4f(float[] paramArrayOfFloat)
  {
    set(paramArrayOfFloat);
  }

  public AxisAngle4f(AxisAngle4f paramAxisAngle4f)
  {
    set(paramAxisAngle4f);
  }

  public AxisAngle4f(AxisAngle4d paramAxisAngle4d)
  {
    set(paramAxisAngle4d);
  }

  public AxisAngle4f(Tuple3f paramTuple3f, float paramFloat) {
    this.jdField_x_of_type_Float = paramTuple3f.jdField_x_of_type_Float;
    this.jdField_y_of_type_Float = paramTuple3f.jdField_y_of_type_Float;
    this.jdField_z_of_type_Float = paramTuple3f.jdField_z_of_type_Float;
    this.angle = paramFloat;
  }

  public AxisAngle4f()
  {
    this.jdField_x_of_type_Float = 0.0F;
    this.jdField_y_of_type_Float = 0.0F;
    this.jdField_z_of_type_Float = 1.0F;
    this.angle = 0.0F;
  }

  public final void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.jdField_x_of_type_Float = paramFloat1;
    this.jdField_y_of_type_Float = paramFloat2;
    this.jdField_z_of_type_Float = paramFloat3;
    this.angle = paramFloat4;
  }

  public final void set(float[] paramArrayOfFloat)
  {
    this.jdField_x_of_type_Float = paramArrayOfFloat[0];
    this.jdField_y_of_type_Float = paramArrayOfFloat[1];
    this.jdField_z_of_type_Float = paramArrayOfFloat[2];
    this.angle = paramArrayOfFloat[3];
  }

  public final void set(AxisAngle4f paramAxisAngle4f)
  {
    this.jdField_x_of_type_Float = paramAxisAngle4f.jdField_x_of_type_Float;
    this.jdField_y_of_type_Float = paramAxisAngle4f.jdField_y_of_type_Float;
    this.jdField_z_of_type_Float = paramAxisAngle4f.jdField_z_of_type_Float;
    this.angle = paramAxisAngle4f.angle;
  }

  public final void set(AxisAngle4d paramAxisAngle4d)
  {
    this.jdField_x_of_type_Float = (float)paramAxisAngle4d.x;
    this.jdField_y_of_type_Float = (float)paramAxisAngle4d.y;
    this.jdField_z_of_type_Float = (float)paramAxisAngle4d.z;
    this.angle = (float)paramAxisAngle4d.angle;
  }

  public final void get(float[] paramArrayOfFloat)
  {
    paramArrayOfFloat[0] = this.jdField_x_of_type_Float;
    paramArrayOfFloat[1] = this.jdField_y_of_type_Float;
    paramArrayOfFloat[2] = this.jdField_z_of_type_Float;
    paramArrayOfFloat[3] = this.angle;
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
    setFromQuat(paramQuat4f.jdField_x_of_type_Float, paramQuat4f.jdField_y_of_type_Float, paramQuat4f.jdField_z_of_type_Float, paramQuat4f.w);
  }

  public final void set(Quat4d paramQuat4d)
  {
    setFromQuat(paramQuat4d.x, paramQuat4d.y, paramQuat4d.z, paramQuat4d.w);
  }

  private void setFromMat(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9)
  {
    double d1 = (paramDouble1 + paramDouble5 + paramDouble9 - 1.0D) * 0.5D;
    this.jdField_x_of_type_Float = (float)(paramDouble8 - paramDouble6);
    this.jdField_y_of_type_Float = (float)(paramDouble3 - paramDouble7);
    this.jdField_z_of_type_Float = (float)(paramDouble4 - paramDouble2);
    double d2 = 0.5D * Math.sqrt(this.jdField_x_of_type_Float * this.jdField_x_of_type_Float + this.jdField_y_of_type_Float * this.jdField_y_of_type_Float + this.jdField_z_of_type_Float * this.jdField_z_of_type_Float);
    this.angle = (float)Math.atan2(d2, d1);
  }

  private void setFromQuat(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    double d = Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2 + paramDouble3 * paramDouble3);
    this.angle = (float)(2.0D * Math.atan2(d, paramDouble4));
    this.jdField_x_of_type_Float = (float)paramDouble1;
    this.jdField_y_of_type_Float = (float)paramDouble2;
    this.jdField_z_of_type_Float = (float)paramDouble3;
  }

  public String toString()
  {
    return "(" + this.jdField_x_of_type_Float + ", " + this.jdField_y_of_type_Float + ", " + this.jdField_z_of_type_Float + ", " + this.angle + ")";
  }

  public boolean equals(AxisAngle4f paramAxisAngle4f)
  {
    return (paramAxisAngle4f != null) && (this.jdField_x_of_type_Float == paramAxisAngle4f.jdField_x_of_type_Float) && (this.jdField_y_of_type_Float == paramAxisAngle4f.jdField_y_of_type_Float) && (this.jdField_z_of_type_Float == paramAxisAngle4f.jdField_z_of_type_Float) && (this.angle == paramAxisAngle4f.angle);
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof AxisAngle4f)) && (equals((AxisAngle4f)paramObject));
  }

  public boolean epsilonEquals(AxisAngle4f paramAxisAngle4f, float paramFloat)
  {
    return (Math.abs(paramAxisAngle4f.jdField_x_of_type_Float - this.jdField_x_of_type_Float) <= paramFloat) && (Math.abs(paramAxisAngle4f.jdField_y_of_type_Float - this.jdField_y_of_type_Float) <= paramFloat) && (Math.abs(paramAxisAngle4f.jdField_z_of_type_Float - this.jdField_z_of_type_Float) <= paramFloat) && (Math.abs(paramAxisAngle4f.angle - this.angle) <= paramFloat);
  }

  public int hashCode()
  {
    return Float.floatToIntBits(this.jdField_x_of_type_Float) ^ Float.floatToIntBits(this.jdField_y_of_type_Float) ^ Float.floatToIntBits(this.jdField_z_of_type_Float) ^ Float.floatToIntBits(this.angle);
  }

  public void set(Vector3f paramVector3f)
  {
    float f1 = paramVector3f.length();
    float f2;
    if (f1 != 0.0F) f2 = 1.0F / f1; else f2 = 0.0F;
    this.jdField_x_of_type_Float = (paramVector3f.jdField_x_of_type_Float * f2);
    this.jdField_y_of_type_Float = (paramVector3f.jdField_y_of_type_Float * f2);
    this.jdField_z_of_type_Float = (paramVector3f.jdField_z_of_type_Float * f2);
    this.angle = f1;
  }

  public void get(Vector3f paramVector3f)
  {
    if (this.angle >= 0.0F) {
      paramVector3f.jdField_x_of_type_Float = (this.jdField_x_of_type_Float * this.angle);
      paramVector3f.jdField_y_of_type_Float = (this.jdField_y_of_type_Float * this.angle);
      paramVector3f.jdField_z_of_type_Float = (this.jdField_z_of_type_Float * this.angle);
    } else {
      paramVector3f.jdField_x_of_type_Float = (-this.jdField_x_of_type_Float * this.angle);
      paramVector3f.jdField_y_of_type_Float = (-this.jdField_y_of_type_Float * this.angle);
      paramVector3f.jdField_z_of_type_Float = (-this.jdField_z_of_type_Float * this.angle);
    }
  }

  private void makeMatrix()
  {
    float f1 = (float)Math.sin(this.angle);
    float f2 = (float)Math.cos(this.angle);
    float f3 = 1.0F - f2;
    float f4 = this.jdField_x_of_type_Float; float f5 = this.jdField_y_of_type_Float; float f6 = this.jdField_z_of_type_Float;
    float f8 = f4 * f3;
    float f7 = f4 * f4;
    Mr.m00 = (f7 + f2 * (1.0F - f7));
    Mr.m01 = (f8 * f5 + f6 * f1);
    Mr.m02 = (f8 * f6 - f5 * f1);
    f7 = f5 * f5;
    Mr.m10 = (f8 * f5 - f6 * f1);
    Mr.m11 = (f7 + f2 * (1.0F - f7));
    Mr.m12 = (f5 * f3 * f6 + f4 * f1);
    f7 = f6 * f6;
    Mr.m20 = (f8 * f6 + f5 * f1);
    Mr.m21 = (f5 * f3 * f6 - f4 * f1);
    Mr.m22 = (f7 + f2 * (1.0F - f7));
  }

  public void rotate(Vector3f paramVector3f)
  {
    makeMatrix();
    Mr.transform(paramVector3f);
  }

  private void makeMatrixRightHanded()
  {
    double d1 = Math.sqrt(this.jdField_x_of_type_Float * this.jdField_x_of_type_Float + this.jdField_y_of_type_Float * this.jdField_y_of_type_Float + this.jdField_z_of_type_Float * this.jdField_z_of_type_Float);

    d1 = 1.0D / d1;
    this.jdField_x_of_type_Float = (float)(this.jdField_x_of_type_Float * d1);
    this.jdField_y_of_type_Float = (float)(this.jdField_y_of_type_Float * d1);
    this.jdField_z_of_type_Float = (float)(this.jdField_z_of_type_Float * d1);
    double d2 = Math.cos(this.angle);
    double d3 = Math.sin(this.angle);
    double d4 = 1.0D - d2;
    Mr.m00 = (float)(d2 + this.jdField_x_of_type_Float * this.jdField_x_of_type_Float * d4);
    Mr.m11 = (float)(d2 + this.jdField_y_of_type_Float * this.jdField_y_of_type_Float * d4);
    Mr.m22 = (float)(d2 + this.jdField_z_of_type_Float * this.jdField_z_of_type_Float * d4);

    double d5 = this.jdField_x_of_type_Float * this.jdField_y_of_type_Float * d4;
    double d6 = this.jdField_z_of_type_Float * d3;
    Mr.m01 = (float)(d5 - d6);
    Mr.m10 = (float)(d5 + d6);

    d5 = this.jdField_x_of_type_Float * this.jdField_z_of_type_Float * d4;
    d6 = this.jdField_y_of_type_Float * d3;
    Mr.m02 = (float)(d5 + d6);
    Mr.m20 = (float)(d5 - d6);

    d5 = this.jdField_y_of_type_Float * this.jdField_z_of_type_Float * d4;
    d6 = this.jdField_x_of_type_Float * d3;
    Mr.m12 = (float)(d5 - d6);
    Mr.m21 = (float)(d5 + d6);
  }

  public void rotateRightHanded(Vector3f paramVector3f)
  {
    makeMatrixRightHanded();
    Mr.transform(paramVector3f);
  }
}