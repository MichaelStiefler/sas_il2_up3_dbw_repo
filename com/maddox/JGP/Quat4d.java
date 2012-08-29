package com.maddox.JGP;

import java.io.Serializable;

public class Quat4d extends Tuple4d
  implements Serializable, Cloneable
{
  private static final Matrix3d MQ = new Matrix3d();

  public Quat4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    super(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
  }

  public Quat4d(double[] paramArrayOfDouble)
  {
    super(paramArrayOfDouble);
  }

  public Quat4d(Quat4d paramQuat4d)
  {
    super(paramQuat4d);
  }

  public Quat4d(Quat4f paramQuat4f)
  {
    super(paramQuat4f);
  }

  public Quat4d(Tuple4d paramTuple4d)
  {
    super(paramTuple4d);
  }

  public Quat4d(Tuple4f paramTuple4f)
  {
    super(paramTuple4f);
  }

  public Quat4d()
  {
  }

  public final void conjugate(Quat4d paramQuat4d)
  {
    this.jdField_x_of_type_Double = (-paramQuat4d.jdField_x_of_type_Double);
    this.jdField_y_of_type_Double = (-paramQuat4d.jdField_y_of_type_Double);
    this.jdField_z_of_type_Double = (-paramQuat4d.jdField_z_of_type_Double);
    this.jdField_w_of_type_Double = paramQuat4d.jdField_w_of_type_Double;
  }

  public final void conjugate()
  {
    this.jdField_x_of_type_Double = (-this.jdField_x_of_type_Double);
    this.jdField_y_of_type_Double = (-this.jdField_y_of_type_Double);
    this.jdField_z_of_type_Double = (-this.jdField_z_of_type_Double);
  }

  public final void mul(Quat4d paramQuat4d1, Quat4d paramQuat4d2)
  {
    set(paramQuat4d1.jdField_x_of_type_Double * paramQuat4d2.jdField_w_of_type_Double + paramQuat4d1.jdField_w_of_type_Double * paramQuat4d2.jdField_x_of_type_Double + paramQuat4d1.jdField_y_of_type_Double * paramQuat4d2.jdField_z_of_type_Double - paramQuat4d1.jdField_z_of_type_Double * paramQuat4d2.jdField_y_of_type_Double, paramQuat4d1.jdField_y_of_type_Double * paramQuat4d2.jdField_w_of_type_Double + paramQuat4d1.jdField_w_of_type_Double * paramQuat4d2.jdField_y_of_type_Double + paramQuat4d1.jdField_z_of_type_Double * paramQuat4d2.jdField_x_of_type_Double - paramQuat4d1.jdField_x_of_type_Double * paramQuat4d2.jdField_z_of_type_Double, paramQuat4d1.jdField_z_of_type_Double * paramQuat4d2.jdField_w_of_type_Double + paramQuat4d1.jdField_w_of_type_Double * paramQuat4d2.jdField_z_of_type_Double + paramQuat4d1.jdField_x_of_type_Double * paramQuat4d2.jdField_y_of_type_Double - paramQuat4d1.jdField_y_of_type_Double * paramQuat4d2.jdField_x_of_type_Double, paramQuat4d1.jdField_w_of_type_Double * paramQuat4d2.jdField_w_of_type_Double - paramQuat4d1.jdField_x_of_type_Double * paramQuat4d2.jdField_x_of_type_Double - paramQuat4d1.jdField_y_of_type_Double * paramQuat4d2.jdField_y_of_type_Double - paramQuat4d1.jdField_z_of_type_Double * paramQuat4d2.jdField_z_of_type_Double);
  }

  public final void mul(Quat4d paramQuat4d)
  {
    set(this.jdField_x_of_type_Double * paramQuat4d.jdField_w_of_type_Double + this.jdField_w_of_type_Double * paramQuat4d.jdField_x_of_type_Double + this.jdField_y_of_type_Double * paramQuat4d.jdField_z_of_type_Double - this.jdField_z_of_type_Double * paramQuat4d.jdField_y_of_type_Double, this.jdField_y_of_type_Double * paramQuat4d.jdField_w_of_type_Double + this.jdField_w_of_type_Double * paramQuat4d.jdField_y_of_type_Double + this.jdField_z_of_type_Double * paramQuat4d.jdField_x_of_type_Double - this.jdField_x_of_type_Double * paramQuat4d.jdField_z_of_type_Double, this.jdField_z_of_type_Double * paramQuat4d.jdField_w_of_type_Double + this.jdField_w_of_type_Double * paramQuat4d.jdField_z_of_type_Double + this.jdField_x_of_type_Double * paramQuat4d.jdField_y_of_type_Double - this.jdField_y_of_type_Double * paramQuat4d.jdField_x_of_type_Double, this.jdField_w_of_type_Double * paramQuat4d.jdField_w_of_type_Double - this.jdField_x_of_type_Double * paramQuat4d.jdField_x_of_type_Double - this.jdField_y_of_type_Double * paramQuat4d.jdField_y_of_type_Double - this.jdField_z_of_type_Double * paramQuat4d.jdField_z_of_type_Double);
  }

  public final void mulInverse(Quat4d paramQuat4d1, Quat4d paramQuat4d2)
  {
    double d = norm();

    d = d == 0.0D ? d : 1.0D / d;

    set((paramQuat4d1.jdField_x_of_type_Double * paramQuat4d2.jdField_w_of_type_Double - paramQuat4d1.jdField_w_of_type_Double * paramQuat4d2.jdField_x_of_type_Double - paramQuat4d1.jdField_y_of_type_Double * paramQuat4d2.jdField_z_of_type_Double + paramQuat4d1.jdField_z_of_type_Double * paramQuat4d2.jdField_y_of_type_Double) * d, (paramQuat4d1.jdField_y_of_type_Double * paramQuat4d2.jdField_w_of_type_Double - paramQuat4d1.jdField_w_of_type_Double * paramQuat4d2.jdField_y_of_type_Double - paramQuat4d1.jdField_z_of_type_Double * paramQuat4d2.jdField_x_of_type_Double + paramQuat4d1.jdField_x_of_type_Double * paramQuat4d2.jdField_z_of_type_Double) * d, (paramQuat4d1.jdField_z_of_type_Double * paramQuat4d2.jdField_w_of_type_Double - paramQuat4d1.jdField_w_of_type_Double * paramQuat4d2.jdField_z_of_type_Double - paramQuat4d1.jdField_x_of_type_Double * paramQuat4d2.jdField_y_of_type_Double + paramQuat4d1.jdField_y_of_type_Double * paramQuat4d2.jdField_x_of_type_Double) * d, (paramQuat4d1.jdField_w_of_type_Double * paramQuat4d2.jdField_w_of_type_Double + paramQuat4d1.jdField_x_of_type_Double * paramQuat4d2.jdField_x_of_type_Double + paramQuat4d1.jdField_y_of_type_Double * paramQuat4d2.jdField_y_of_type_Double + paramQuat4d1.jdField_z_of_type_Double * paramQuat4d2.jdField_z_of_type_Double) * d);
  }

  public final void mulInverse(Quat4d paramQuat4d)
  {
    double d = norm();

    d = d == 0.0D ? d : 1.0D / d;

    set((this.jdField_x_of_type_Double * paramQuat4d.jdField_w_of_type_Double - this.jdField_w_of_type_Double * paramQuat4d.jdField_x_of_type_Double - this.jdField_y_of_type_Double * paramQuat4d.jdField_z_of_type_Double + this.jdField_z_of_type_Double * paramQuat4d.jdField_y_of_type_Double) * d, (this.jdField_y_of_type_Double * paramQuat4d.jdField_w_of_type_Double - this.jdField_w_of_type_Double * paramQuat4d.jdField_y_of_type_Double - this.jdField_z_of_type_Double * paramQuat4d.jdField_x_of_type_Double + this.jdField_x_of_type_Double * paramQuat4d.jdField_z_of_type_Double) * d, (this.jdField_z_of_type_Double * paramQuat4d.jdField_w_of_type_Double - this.jdField_w_of_type_Double * paramQuat4d.jdField_z_of_type_Double - this.jdField_x_of_type_Double * paramQuat4d.jdField_y_of_type_Double + this.jdField_y_of_type_Double * paramQuat4d.jdField_x_of_type_Double) * d, (this.jdField_w_of_type_Double * paramQuat4d.jdField_w_of_type_Double + this.jdField_x_of_type_Double * paramQuat4d.jdField_x_of_type_Double + this.jdField_y_of_type_Double * paramQuat4d.jdField_y_of_type_Double + this.jdField_z_of_type_Double * paramQuat4d.jdField_z_of_type_Double) * d);
  }

  private final double norm()
  {
    return this.jdField_x_of_type_Double * this.jdField_x_of_type_Double + this.jdField_y_of_type_Double * this.jdField_y_of_type_Double + this.jdField_z_of_type_Double * this.jdField_z_of_type_Double + this.jdField_w_of_type_Double * this.jdField_w_of_type_Double;
  }

  public final void inverse(Quat4d paramQuat4d)
  {
    double d = paramQuat4d.norm();

    this.jdField_x_of_type_Double = (-paramQuat4d.jdField_x_of_type_Double / d);
    this.jdField_y_of_type_Double = (-paramQuat4d.jdField_y_of_type_Double / d);
    this.jdField_z_of_type_Double = (-paramQuat4d.jdField_z_of_type_Double / d);
    this.jdField_w_of_type_Double = (paramQuat4d.jdField_w_of_type_Double / d);
  }

  public final void inverse()
  {
    double d = norm();

    this.jdField_x_of_type_Double = (-this.jdField_x_of_type_Double / d);
    this.jdField_y_of_type_Double = (-this.jdField_y_of_type_Double / d);
    this.jdField_z_of_type_Double = (-this.jdField_z_of_type_Double / d);
    this.jdField_w_of_type_Double /= d;
  }

  public final void normalize(Quat4d paramQuat4d)
  {
    double d = Math.sqrt(paramQuat4d.norm());

    this.jdField_x_of_type_Double = (paramQuat4d.jdField_x_of_type_Double / d);
    this.jdField_y_of_type_Double = (paramQuat4d.jdField_y_of_type_Double / d);
    this.jdField_z_of_type_Double = (paramQuat4d.jdField_z_of_type_Double / d);
    this.jdField_w_of_type_Double = (paramQuat4d.jdField_w_of_type_Double / d);
  }

  public final void normalize()
  {
    double d = Math.sqrt(norm());

    this.jdField_x_of_type_Double /= d;
    this.jdField_y_of_type_Double /= d;
    this.jdField_z_of_type_Double /= d;
    this.jdField_w_of_type_Double /= d;
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

  public final void set(AxisAngle4f paramAxisAngle4f)
  {
    this.jdField_x_of_type_Double = paramAxisAngle4f.x;
    this.jdField_y_of_type_Double = paramAxisAngle4f.y;
    this.jdField_z_of_type_Double = paramAxisAngle4f.z;
    double d1 = Math.sqrt(this.jdField_x_of_type_Double * this.jdField_x_of_type_Double + this.jdField_y_of_type_Double * this.jdField_y_of_type_Double + this.jdField_z_of_type_Double * this.jdField_z_of_type_Double);

    double d2 = Math.sin(0.5D * paramAxisAngle4f.angle) / d1;
    this.jdField_x_of_type_Double *= d2;
    this.jdField_y_of_type_Double *= d2;
    this.jdField_z_of_type_Double *= d2;
    this.jdField_w_of_type_Double = Math.cos(0.5D * paramAxisAngle4f.angle);
  }

  public final void set(AxisAngle4d paramAxisAngle4d)
  {
    this.jdField_x_of_type_Double = paramAxisAngle4d.jdField_x_of_type_Double;
    this.jdField_y_of_type_Double = paramAxisAngle4d.jdField_y_of_type_Double;
    this.jdField_z_of_type_Double = paramAxisAngle4d.jdField_z_of_type_Double;
    double d1 = Math.sqrt(this.jdField_x_of_type_Double * this.jdField_x_of_type_Double + this.jdField_y_of_type_Double * this.jdField_y_of_type_Double + this.jdField_z_of_type_Double * this.jdField_z_of_type_Double);

    double d2 = Math.sin(0.5D * paramAxisAngle4d.angle) / d1;
    this.jdField_x_of_type_Double *= d2;
    this.jdField_y_of_type_Double *= d2;
    this.jdField_z_of_type_Double *= d2;
    this.jdField_w_of_type_Double = Math.cos(0.5D * paramAxisAngle4d.angle);
  }

  public void set(Vector3d paramVector3d)
  {
    double d1 = paramVector3d.length() + 1.0E-075D;
    double d2 = (float)(Math.sin(0.5D * d1) / d1);
    this.jdField_x_of_type_Double = (paramVector3d.jdField_x_of_type_Double * d2);
    this.jdField_y_of_type_Double = (paramVector3d.jdField_y_of_type_Double * d2);
    this.jdField_z_of_type_Double = (paramVector3d.jdField_z_of_type_Double * d2);
    this.jdField_w_of_type_Double = (float)Math.cos(0.5D * d1);
  }

  public final void interpolate(Quat4d paramQuat4d, double paramDouble)
  {
    normalize();
    double d1 = Math.sqrt(paramQuat4d.norm());

    double d2 = paramQuat4d.jdField_x_of_type_Double / d1;
    double d3 = paramQuat4d.jdField_y_of_type_Double / d1;
    double d4 = paramQuat4d.jdField_z_of_type_Double / d1;
    double d5 = paramQuat4d.jdField_w_of_type_Double / d1;

    double d6 = this.jdField_x_of_type_Double * d2 + this.jdField_y_of_type_Double * d3 + this.jdField_z_of_type_Double * d4 + this.jdField_w_of_type_Double * d5;

    if (d6 < 0.0D) { d6 = -d6;
      d2 = -d2; d3 = -d3; d4 = -d4; d5 = -d5;
    }

    if (d6 > 1.0D) return;

    d6 = Math.acos(d6);

    double d7 = Math.sin(d6);

    if (d7 == 0.0D) return;

    double d8 = Math.sin((1.0D - paramDouble) * d6) / d7;
    d6 = Math.sin(paramDouble * d6) / d7;

    this.jdField_x_of_type_Double = (d8 * this.jdField_x_of_type_Double + d6 * d2);
    this.jdField_y_of_type_Double = (d8 * this.jdField_y_of_type_Double + d6 * d3);
    this.jdField_z_of_type_Double = (d8 * this.jdField_z_of_type_Double + d6 * d4);
    this.jdField_w_of_type_Double = (d8 * this.jdField_w_of_type_Double + d6 * d5);
  }

  public final void interpolate(Quat4d paramQuat4d1, Quat4d paramQuat4d2, double paramDouble)
  {
    if (this != paramQuat4d2) {
      set(paramQuat4d1);
      interpolate(paramQuat4d2, paramDouble);
    } else {
      interpolate(paramQuat4d1, 1.0D - paramDouble);
    }
  }

  private void setFromMat(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9)
  {
    double d2 = paramDouble1 + paramDouble5 + paramDouble9;
    double d1;
    if (d2 >= 0.0D) {
      d1 = Math.sqrt(d2 + 1.0D);
      this.jdField_w_of_type_Double = (d1 * 0.5D);
      d1 = 0.5D / d1;
      this.jdField_x_of_type_Double = ((paramDouble8 - paramDouble6) * d1);
      this.jdField_y_of_type_Double = ((paramDouble3 - paramDouble7) * d1);
      this.jdField_z_of_type_Double = ((paramDouble4 - paramDouble2) * d1);
    } else {
      double d3 = Math.max(Math.max(paramDouble1, paramDouble5), paramDouble9);
      if (d3 == paramDouble1) {
        d1 = Math.sqrt(paramDouble1 - (paramDouble5 + paramDouble9) + 1.0D);
        this.jdField_x_of_type_Double = (d1 * 0.5D);
        d1 = 0.5D / d1;
        this.jdField_y_of_type_Double = ((paramDouble2 + paramDouble4) * d1);
        this.jdField_z_of_type_Double = ((paramDouble7 + paramDouble3) * d1);
        this.jdField_w_of_type_Double = ((paramDouble8 - paramDouble6) * d1);
      } else if (d3 == paramDouble5) {
        d1 = Math.sqrt(paramDouble5 - (paramDouble9 + paramDouble1) + 1.0D);
        this.jdField_y_of_type_Double = (d1 * 0.5D);
        d1 = 0.5D / d1;
        this.jdField_z_of_type_Double = ((paramDouble6 + paramDouble8) * d1);
        this.jdField_x_of_type_Double = ((paramDouble2 + paramDouble4) * d1);
        this.jdField_w_of_type_Double = ((paramDouble3 - paramDouble7) * d1);
      } else {
        d1 = Math.sqrt(paramDouble9 - (paramDouble1 + paramDouble5) + 1.0D);
        this.jdField_z_of_type_Double = (d1 * 0.5D);
        d1 = 0.5D / d1;
        this.jdField_x_of_type_Double = ((paramDouble7 + paramDouble3) * d1);
        this.jdField_y_of_type_Double = ((paramDouble6 + paramDouble8) * d1);
        this.jdField_w_of_type_Double = ((paramDouble4 - paramDouble2) * d1);
      }
    }
  }

  public void setEulers(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d1 = Math.sin(paramDouble1 * 0.5D);
    double d2 = Math.cos(paramDouble1 * 0.5D);
    double d3 = Math.sin(paramDouble2 * 0.5D);
    double d4 = Math.cos(paramDouble2 * 0.5D);
    double d5 = Math.sin(paramDouble3 * 0.5D);
    double d6 = Math.cos(paramDouble3 * 0.5D);

    this.jdField_x_of_type_Double = (d2 * d4 * d5 - d1 * d3 * d6);
    this.jdField_y_of_type_Double = (d2 * d3 * d6 + d1 * d4 * d5);
    this.jdField_z_of_type_Double = (d1 * d4 * d6 - d2 * d3 * d5);
    this.jdField_w_of_type_Double = (d2 * d4 * d6 + d1 * d3 * d5);
  }

  public void getEulers(double[] paramArrayOfDouble)
  {
    MQ.set(this);
    MQ.getEulers(paramArrayOfDouble);
  }
}