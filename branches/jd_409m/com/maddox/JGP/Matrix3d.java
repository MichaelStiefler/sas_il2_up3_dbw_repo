package com.maddox.JGP;

import java.io.Serializable;

public class Matrix3d
  implements Serializable, Cloneable
{
  public double m00;
  public double m01;
  public double m02;
  public double m10;
  public double m11;
  public double m12;
  public double m20;
  public double m21;
  public double m22;

  public Matrix3d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9)
  {
    set(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9);
  }

  public Matrix3d(double[] paramArrayOfDouble)
  {
    set(paramArrayOfDouble);
  }

  public Matrix3d(Matrix3d paramMatrix3d)
  {
    set(paramMatrix3d);
  }

  public Matrix3d(Matrix3f paramMatrix3f)
  {
    set(paramMatrix3f);
  }

  public Matrix3d()
  {
    setZero();
  }

  public String toString()
  {
    String str = System.getProperty("line.separator");

    return "[" + str + "  [" + this.m00 + "\t" + this.m01 + "\t" + this.m02 + "]" + str + "  [" + this.m10 + "\t" + this.m11 + "\t" + this.m12 + "]" + str + "  [" + this.m20 + "\t" + this.m21 + "\t" + this.m22 + "] ]";
  }

  public final void setIdentity()
  {
    this.m00 = 1.0D; this.m01 = 0.0D; this.m02 = 0.0D;
    this.m10 = 0.0D; this.m11 = 1.0D; this.m12 = 0.0D;
    this.m20 = 0.0D; this.m21 = 0.0D; this.m22 = 1.0D;
  }

  public final void setScale(double paramDouble)
  {
    SVD(this);
    this.m00 *= paramDouble;
    this.m11 *= paramDouble;
    this.m22 *= paramDouble;
  }

  public final void setElement(int paramInt1, int paramInt2, double paramDouble)
  {
    if (paramInt1 == 0) {
      if (paramInt2 == 0)
        this.m00 = paramDouble;
      else if (paramInt2 == 1)
        this.m01 = paramDouble;
      else
        this.m02 = paramDouble;
    } else if (paramInt1 == 1) {
      if (paramInt2 == 0)
        this.m10 = paramDouble;
      else if (paramInt2 == 1)
        this.m11 = paramDouble;
      else
        this.m12 = paramDouble;
    }
    else if (paramInt2 == 0)
      this.m20 = paramDouble;
    else if (paramInt2 == 1)
      this.m21 = paramDouble;
    else
      this.m22 = paramDouble;
  }

  public final double getElement(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0) {
      if (paramInt2 == 0)
        return this.m00;
      if (paramInt2 == 1) {
        return this.m01;
      }
      return this.m02;
    }
    if (paramInt1 == 1) {
      if (paramInt2 == 0)
        return this.m10;
      if (paramInt2 == 1) {
        return this.m11;
      }
      return this.m12;
    }

    if (paramInt2 == 0)
      return this.m20;
    if (paramInt2 == 1) {
      return this.m21;
    }
    return this.m22;
  }

  public final void setRow(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (paramInt == 0) {
      this.m00 = paramDouble1;
      this.m01 = paramDouble2;
      this.m02 = paramDouble3;
    } else if (paramInt == 1) {
      this.m10 = paramDouble1;
      this.m11 = paramDouble2;
      this.m12 = paramDouble3;
    } else if (paramInt == 2) {
      this.m20 = paramDouble1;
      this.m21 = paramDouble2;
      this.m22 = paramDouble3;
    }
  }

  public final void setRow(int paramInt, Tuple3d paramTuple3d)
  {
    if (paramInt == 0) {
      this.m00 = paramTuple3d.x;
      this.m01 = paramTuple3d.y;
      this.m02 = paramTuple3d.z;
    } else if (paramInt == 1) {
      this.m10 = paramTuple3d.x;
      this.m11 = paramTuple3d.y;
      this.m12 = paramTuple3d.z;
    } else if (paramInt == 2) {
      this.m20 = paramTuple3d.x;
      this.m21 = paramTuple3d.y;
      this.m22 = paramTuple3d.z;
    }
  }

  public final void setRow(int paramInt, double[] paramArrayOfDouble)
  {
    if (paramInt == 0) {
      this.m00 = paramArrayOfDouble[0];
      this.m01 = paramArrayOfDouble[1];
      this.m02 = paramArrayOfDouble[2];
    } else if (paramInt == 1) {
      this.m10 = paramArrayOfDouble[0];
      this.m11 = paramArrayOfDouble[1];
      this.m12 = paramArrayOfDouble[2];
    } else if (paramInt == 2) {
      this.m20 = paramArrayOfDouble[0];
      this.m21 = paramArrayOfDouble[1];
      this.m22 = paramArrayOfDouble[2];
    }
  }

  public final void getRow(int paramInt, double[] paramArrayOfDouble)
  {
    if (paramInt == 0) {
      paramArrayOfDouble[0] = this.m00;
      paramArrayOfDouble[1] = this.m01;
      paramArrayOfDouble[2] = this.m02;
    } else if (paramInt == 1) {
      paramArrayOfDouble[0] = this.m10;
      paramArrayOfDouble[1] = this.m11;
      paramArrayOfDouble[2] = this.m12;
    } else if (paramInt == 2) {
      paramArrayOfDouble[0] = this.m20;
      paramArrayOfDouble[1] = this.m21;
      paramArrayOfDouble[2] = this.m22;
    }
  }

  public final void getRow(int paramInt, Tuple3d paramTuple3d)
  {
    if (paramInt == 0) {
      paramTuple3d.x = this.m00;
      paramTuple3d.y = this.m01;
      paramTuple3d.z = this.m02;
    } else if (paramInt == 1) {
      paramTuple3d.x = this.m10;
      paramTuple3d.y = this.m11;
      paramTuple3d.z = this.m12;
    } else if (paramInt == 2) {
      paramTuple3d.x = this.m20;
      paramTuple3d.y = this.m21;
      paramTuple3d.z = this.m22;
    }
  }

  public final void setColumn(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (paramInt == 0) {
      this.m00 = paramDouble1;
      this.m10 = paramDouble2;
      this.m20 = paramDouble3;
    } else if (paramInt == 1) {
      this.m01 = paramDouble1;
      this.m11 = paramDouble2;
      this.m21 = paramDouble3;
    } else if (paramInt == 2) {
      this.m02 = paramDouble1;
      this.m12 = paramDouble2;
      this.m22 = paramDouble3;
    }
  }

  public final void setColumn(int paramInt, Tuple3d paramTuple3d)
  {
    if (paramInt == 0) {
      this.m00 = paramTuple3d.x;
      this.m10 = paramTuple3d.y;
      this.m20 = paramTuple3d.z;
    } else if (paramInt == 1) {
      this.m01 = paramTuple3d.x;
      this.m11 = paramTuple3d.y;
      this.m21 = paramTuple3d.z;
    } else if (paramInt == 2) {
      this.m02 = paramTuple3d.x;
      this.m12 = paramTuple3d.y;
      this.m22 = paramTuple3d.z;
    }
  }

  public final void setColumn(int paramInt, double[] paramArrayOfDouble)
  {
    if (paramInt == 0) {
      this.m00 = paramArrayOfDouble[0];
      this.m10 = paramArrayOfDouble[1];
      this.m20 = paramArrayOfDouble[2];
    } else if (paramInt == 1) {
      this.m01 = paramArrayOfDouble[0];
      this.m11 = paramArrayOfDouble[1];
      this.m21 = paramArrayOfDouble[2];
    } else if (paramInt == 2) {
      this.m02 = paramArrayOfDouble[0];
      this.m12 = paramArrayOfDouble[1];
      this.m22 = paramArrayOfDouble[2];
    }
  }

  public final void getColumn(int paramInt, Tuple3d paramTuple3d)
  {
    if (paramInt == 0) {
      paramTuple3d.x = this.m00;
      paramTuple3d.y = this.m10;
      paramTuple3d.z = this.m20;
    } else if (paramInt == 1) {
      paramTuple3d.x = this.m01;
      paramTuple3d.y = this.m11;
      paramTuple3d.z = this.m21;
    } else if (paramInt == 2) {
      paramTuple3d.x = this.m02;
      paramTuple3d.y = this.m12;
      paramTuple3d.z = this.m22;
    }
  }

  public final void getColumn(int paramInt, double[] paramArrayOfDouble)
  {
    if (paramInt == 0) {
      paramArrayOfDouble[0] = this.m00;
      paramArrayOfDouble[1] = this.m10;
      paramArrayOfDouble[2] = this.m20;
    } else if (paramInt == 1) {
      paramArrayOfDouble[0] = this.m01;
      paramArrayOfDouble[1] = this.m11;
      paramArrayOfDouble[2] = this.m21;
    } else if (paramInt == 2) {
      paramArrayOfDouble[0] = this.m02;
      paramArrayOfDouble[1] = this.m12;
      paramArrayOfDouble[2] = this.m22;
    }
  }

  public final double getScale()
  {
    return SVD(null);
  }

  public final void add(double paramDouble)
  {
    this.m00 += paramDouble; this.m01 += paramDouble; this.m02 += paramDouble;
    this.m10 += paramDouble; this.m11 += paramDouble; this.m12 += paramDouble;
    this.m20 += paramDouble; this.m21 += paramDouble; this.m22 += paramDouble;
  }

  public final void add(double paramDouble, Matrix3d paramMatrix3d)
  {
    set(paramMatrix3d);
    add(paramDouble);
  }

  public final void add(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2)
  {
    set(paramMatrix3d1.m00 + paramMatrix3d2.m00, paramMatrix3d1.m01 + paramMatrix3d2.m01, paramMatrix3d1.m02 + paramMatrix3d2.m02, paramMatrix3d1.m10 + paramMatrix3d2.m10, paramMatrix3d1.m11 + paramMatrix3d2.m11, paramMatrix3d1.m12 + paramMatrix3d2.m12, paramMatrix3d1.m20 + paramMatrix3d2.m20, paramMatrix3d1.m21 + paramMatrix3d2.m21, paramMatrix3d1.m22 + paramMatrix3d2.m22);
  }

  public final void add(Matrix3d paramMatrix3d)
  {
    this.m00 += paramMatrix3d.m00; this.m01 += paramMatrix3d.m01; this.m02 += paramMatrix3d.m02;
    this.m10 += paramMatrix3d.m10; this.m11 += paramMatrix3d.m11; this.m12 += paramMatrix3d.m12;
    this.m20 += paramMatrix3d.m20; this.m21 += paramMatrix3d.m21; this.m22 += paramMatrix3d.m22;
  }

  public final void sub(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2)
  {
    set(paramMatrix3d1.m00 - paramMatrix3d2.m00, paramMatrix3d1.m01 - paramMatrix3d2.m01, paramMatrix3d1.m02 - paramMatrix3d2.m02, paramMatrix3d1.m10 - paramMatrix3d2.m10, paramMatrix3d1.m11 - paramMatrix3d2.m11, paramMatrix3d1.m12 - paramMatrix3d2.m12, paramMatrix3d1.m20 - paramMatrix3d2.m20, paramMatrix3d1.m21 - paramMatrix3d2.m21, paramMatrix3d1.m22 - paramMatrix3d2.m22);
  }

  public final void sub(Matrix3d paramMatrix3d)
  {
    this.m00 -= paramMatrix3d.m00; this.m01 -= paramMatrix3d.m01; this.m02 -= paramMatrix3d.m02;
    this.m10 -= paramMatrix3d.m10; this.m11 -= paramMatrix3d.m11; this.m12 -= paramMatrix3d.m12;
    this.m20 -= paramMatrix3d.m20; this.m21 -= paramMatrix3d.m21; this.m22 -= paramMatrix3d.m22;
  }

  public final void transpose()
  {
    double d = this.m01;
    this.m01 = this.m10;
    this.m10 = d;

    d = this.m02;
    this.m02 = this.m20;
    this.m20 = d;

    d = this.m12;
    this.m12 = this.m21;
    this.m21 = d;
  }

  public final void transpose(Matrix3d paramMatrix3d)
  {
    set(paramMatrix3d);
    transpose();
  }

  public final void set(Quat4d paramQuat4d)
  {
    setFromQuat(paramQuat4d.x, paramQuat4d.y, paramQuat4d.z, paramQuat4d.w);
  }

  public final void set(AxisAngle4d paramAxisAngle4d)
  {
    setFromAxisAngle(paramAxisAngle4d.x, paramAxisAngle4d.y, paramAxisAngle4d.z, paramAxisAngle4d.angle);
  }

  public final void set(Quat4f paramQuat4f)
  {
    setFromQuat(paramQuat4f.x, paramQuat4f.y, paramQuat4f.z, paramQuat4f.w);
  }

  public final void set(AxisAngle4f paramAxisAngle4f)
  {
    setFromAxisAngle(paramAxisAngle4f.x, paramAxisAngle4f.y, paramAxisAngle4f.z, paramAxisAngle4f.angle);
  }

  public final void set(Matrix3d paramMatrix3d)
  {
    this.m00 = paramMatrix3d.m00; this.m01 = paramMatrix3d.m01; this.m02 = paramMatrix3d.m02;
    this.m10 = paramMatrix3d.m10; this.m11 = paramMatrix3d.m11; this.m12 = paramMatrix3d.m12;
    this.m20 = paramMatrix3d.m20; this.m21 = paramMatrix3d.m21; this.m22 = paramMatrix3d.m22;
  }

  public final void set(Matrix3f paramMatrix3f)
  {
    this.m00 = paramMatrix3f.m00; this.m01 = paramMatrix3f.m01; this.m02 = paramMatrix3f.m02;
    this.m10 = paramMatrix3f.m10; this.m11 = paramMatrix3f.m11; this.m12 = paramMatrix3f.m12;
    this.m20 = paramMatrix3f.m20; this.m21 = paramMatrix3f.m21; this.m22 = paramMatrix3f.m22;
  }

  public final void set(double[] paramArrayOfDouble)
  {
    this.m00 = paramArrayOfDouble[0]; this.m01 = paramArrayOfDouble[1]; this.m02 = paramArrayOfDouble[2];
    this.m10 = paramArrayOfDouble[3]; this.m11 = paramArrayOfDouble[4]; this.m12 = paramArrayOfDouble[5];
    this.m20 = paramArrayOfDouble[6]; this.m21 = paramArrayOfDouble[7]; this.m22 = paramArrayOfDouble[8];
  }

  public final void invert(Matrix3d paramMatrix3d)
  {
    set(paramMatrix3d);
    invert();
  }

  public final void invert()
  {
    double d = determinant();
    if (d == 0.0D)
      return;
    d = 1.0D / d;

    set(this.m11 * this.m22 - this.m12 * this.m21, this.m02 * this.m21 - this.m01 * this.m22, this.m01 * this.m12 - this.m02 * this.m11, this.m12 * this.m20 - this.m10 * this.m22, this.m00 * this.m22 - this.m02 * this.m20, this.m02 * this.m10 - this.m00 * this.m12, this.m10 * this.m21 - this.m11 * this.m20, this.m01 * this.m20 - this.m00 * this.m21, this.m00 * this.m11 - this.m01 * this.m10);

    mul(d);
  }

  public final double determinant()
  {
    return this.m00 * (this.m11 * this.m22 - this.m21 * this.m12) - this.m01 * (this.m10 * this.m22 - this.m20 * this.m12) + this.m02 * (this.m10 * this.m21 - this.m20 * this.m11);
  }

  public final void set(double paramDouble)
  {
    this.m00 = paramDouble; this.m01 = 0.0D; this.m02 = 0.0D;
    this.m10 = 0.0D; this.m11 = paramDouble; this.m12 = 0.0D;
    this.m20 = 0.0D; this.m21 = 0.0D; this.m22 = paramDouble;
  }

  public final void rotX(double paramDouble)
  {
    double d1 = Math.cos(paramDouble);
    double d2 = Math.sin(paramDouble);
    this.m00 = 1.0D; this.m01 = 0.0D; this.m02 = 0.0D;
    this.m10 = 0.0D; this.m11 = d1; this.m12 = (-d2);
    this.m20 = 0.0D; this.m21 = d2; this.m22 = d1;
  }

  public final void rotY(double paramDouble)
  {
    double d1 = Math.cos(paramDouble);
    double d2 = Math.sin(paramDouble);
    this.m00 = d1; this.m01 = 0.0D; this.m02 = d2;
    this.m10 = 0.0D; this.m11 = 1.0D; this.m12 = 0.0D;
    this.m20 = (-d2); this.m21 = 0.0D; this.m22 = d1;
  }

  public final void rotZ(double paramDouble)
  {
    double d1 = Math.cos(paramDouble);
    double d2 = Math.sin(paramDouble);
    this.m00 = d1; this.m01 = (-d2); this.m02 = 0.0D;
    this.m10 = d2; this.m11 = d1; this.m12 = 0.0D;
    this.m20 = 0.0D; this.m21 = 0.0D; this.m22 = 1.0D;
  }

  public final void mul(double paramDouble)
  {
    this.m00 *= paramDouble; this.m01 *= paramDouble; this.m02 *= paramDouble;
    this.m10 *= paramDouble; this.m11 *= paramDouble; this.m12 *= paramDouble;
    this.m20 *= paramDouble; this.m21 *= paramDouble; this.m22 *= paramDouble;
  }

  public final void mul(double paramDouble, Matrix3d paramMatrix3d)
  {
    set(paramMatrix3d);
    mul(paramDouble);
  }

  public final void mul(Matrix3d paramMatrix3d)
  {
    mul(this, paramMatrix3d);
  }

  public final void mul(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2)
  {
    set(paramMatrix3d1.m00 * paramMatrix3d2.m00 + paramMatrix3d1.m01 * paramMatrix3d2.m10 + paramMatrix3d1.m02 * paramMatrix3d2.m20, paramMatrix3d1.m00 * paramMatrix3d2.m01 + paramMatrix3d1.m01 * paramMatrix3d2.m11 + paramMatrix3d1.m02 * paramMatrix3d2.m21, paramMatrix3d1.m00 * paramMatrix3d2.m02 + paramMatrix3d1.m01 * paramMatrix3d2.m12 + paramMatrix3d1.m02 * paramMatrix3d2.m22, paramMatrix3d1.m10 * paramMatrix3d2.m00 + paramMatrix3d1.m11 * paramMatrix3d2.m10 + paramMatrix3d1.m12 * paramMatrix3d2.m20, paramMatrix3d1.m10 * paramMatrix3d2.m01 + paramMatrix3d1.m11 * paramMatrix3d2.m11 + paramMatrix3d1.m12 * paramMatrix3d2.m21, paramMatrix3d1.m10 * paramMatrix3d2.m02 + paramMatrix3d1.m11 * paramMatrix3d2.m12 + paramMatrix3d1.m12 * paramMatrix3d2.m22, paramMatrix3d1.m20 * paramMatrix3d2.m00 + paramMatrix3d1.m21 * paramMatrix3d2.m10 + paramMatrix3d1.m22 * paramMatrix3d2.m20, paramMatrix3d1.m20 * paramMatrix3d2.m01 + paramMatrix3d1.m21 * paramMatrix3d2.m11 + paramMatrix3d1.m22 * paramMatrix3d2.m21, paramMatrix3d1.m20 * paramMatrix3d2.m02 + paramMatrix3d1.m21 * paramMatrix3d2.m12 + paramMatrix3d1.m22 * paramMatrix3d2.m22);
  }

  public final void mulNormalize(Matrix3d paramMatrix3d)
  {
    mul(paramMatrix3d);
    SVD(this);
  }

  public final void mulNormalize(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2)
  {
    mul(paramMatrix3d1, paramMatrix3d2);
    SVD(this);
  }

  public final void mulTransposeBoth(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2)
  {
    mul(paramMatrix3d2, paramMatrix3d1);
    transpose();
  }

  public final void mulTransposeRight(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2)
  {
    set(paramMatrix3d1.m00 * paramMatrix3d2.m00 + paramMatrix3d1.m01 * paramMatrix3d2.m01 + paramMatrix3d1.m02 * paramMatrix3d2.m02, paramMatrix3d1.m00 * paramMatrix3d2.m10 + paramMatrix3d1.m01 * paramMatrix3d2.m11 + paramMatrix3d1.m02 * paramMatrix3d2.m12, paramMatrix3d1.m00 * paramMatrix3d2.m20 + paramMatrix3d1.m01 * paramMatrix3d2.m21 + paramMatrix3d1.m02 * paramMatrix3d2.m22, paramMatrix3d1.m10 * paramMatrix3d2.m00 + paramMatrix3d1.m11 * paramMatrix3d2.m01 + paramMatrix3d1.m12 * paramMatrix3d2.m02, paramMatrix3d1.m10 * paramMatrix3d2.m10 + paramMatrix3d1.m11 * paramMatrix3d2.m11 + paramMatrix3d1.m12 * paramMatrix3d2.m12, paramMatrix3d1.m10 * paramMatrix3d2.m20 + paramMatrix3d1.m11 * paramMatrix3d2.m21 + paramMatrix3d1.m12 * paramMatrix3d2.m22, paramMatrix3d1.m20 * paramMatrix3d2.m00 + paramMatrix3d1.m21 * paramMatrix3d2.m01 + paramMatrix3d1.m22 * paramMatrix3d2.m02, paramMatrix3d1.m20 * paramMatrix3d2.m10 + paramMatrix3d1.m21 * paramMatrix3d2.m11 + paramMatrix3d1.m22 * paramMatrix3d2.m12, paramMatrix3d1.m20 * paramMatrix3d2.m20 + paramMatrix3d1.m21 * paramMatrix3d2.m21 + paramMatrix3d1.m22 * paramMatrix3d2.m22);
  }

  public final void mulTransposeLeft(Matrix3d paramMatrix3d1, Matrix3d paramMatrix3d2)
  {
    set(paramMatrix3d1.m00 * paramMatrix3d2.m00 + paramMatrix3d1.m10 * paramMatrix3d2.m10 + paramMatrix3d1.m20 * paramMatrix3d2.m20, paramMatrix3d1.m00 * paramMatrix3d2.m01 + paramMatrix3d1.m10 * paramMatrix3d2.m11 + paramMatrix3d1.m20 * paramMatrix3d2.m21, paramMatrix3d1.m00 * paramMatrix3d2.m02 + paramMatrix3d1.m10 * paramMatrix3d2.m12 + paramMatrix3d1.m20 * paramMatrix3d2.m22, paramMatrix3d1.m01 * paramMatrix3d2.m00 + paramMatrix3d1.m11 * paramMatrix3d2.m10 + paramMatrix3d1.m21 * paramMatrix3d2.m20, paramMatrix3d1.m01 * paramMatrix3d2.m01 + paramMatrix3d1.m11 * paramMatrix3d2.m11 + paramMatrix3d1.m21 * paramMatrix3d2.m21, paramMatrix3d1.m01 * paramMatrix3d2.m02 + paramMatrix3d1.m11 * paramMatrix3d2.m12 + paramMatrix3d1.m21 * paramMatrix3d2.m22, paramMatrix3d1.m02 * paramMatrix3d2.m00 + paramMatrix3d1.m12 * paramMatrix3d2.m10 + paramMatrix3d1.m22 * paramMatrix3d2.m20, paramMatrix3d1.m02 * paramMatrix3d2.m01 + paramMatrix3d1.m12 * paramMatrix3d2.m11 + paramMatrix3d1.m22 * paramMatrix3d2.m21, paramMatrix3d1.m02 * paramMatrix3d2.m02 + paramMatrix3d1.m12 * paramMatrix3d2.m12 + paramMatrix3d1.m22 * paramMatrix3d2.m22);
  }

  public final void normalize()
  {
    SVD(this);
  }

  public final void normalize(Matrix3d paramMatrix3d)
  {
    set(paramMatrix3d);
    SVD(this);
  }

  public final void normalizeCP()
  {
    double d = Math.pow(Math.abs(determinant()), -0.3333333333333333D);
    mul(d);
  }

  public final void normalizeCP(Matrix3d paramMatrix3d)
  {
    set(paramMatrix3d);
    normalizeCP();
  }

  public boolean equals(Matrix3d paramMatrix3d)
  {
    return (paramMatrix3d != null) && (this.m00 == paramMatrix3d.m00) && (this.m01 == paramMatrix3d.m01) && (this.m02 == paramMatrix3d.m02) && (this.m10 == paramMatrix3d.m10) && (this.m11 == paramMatrix3d.m11) && (this.m12 == paramMatrix3d.m12) && (this.m20 == paramMatrix3d.m20) && (this.m21 == paramMatrix3d.m21) && (this.m22 == paramMatrix3d.m22);
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof Matrix3d)) && (equals((Matrix3d)paramObject));
  }

  public boolean epsilonEquals(Matrix3d paramMatrix3d, double paramDouble)
  {
    return (Math.abs(this.m00 - paramMatrix3d.m00) <= paramDouble) && (Math.abs(this.m01 - paramMatrix3d.m01) <= paramDouble) && (Math.abs(this.m02 - paramMatrix3d.m02) <= paramDouble) && (Math.abs(this.m10 - paramMatrix3d.m10) <= paramDouble) && (Math.abs(this.m11 - paramMatrix3d.m11) <= paramDouble) && (Math.abs(this.m12 - paramMatrix3d.m12) <= paramDouble) && (Math.abs(this.m20 - paramMatrix3d.m20) <= paramDouble) && (Math.abs(this.m21 - paramMatrix3d.m21) <= paramDouble) && (Math.abs(this.m22 - paramMatrix3d.m22) <= paramDouble);
  }

  public int hashCode()
  {
    long l = Double.doubleToLongBits(this.m00);
    int i = (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m01);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m02);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m10);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m11);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m12);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m20);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m21);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m22);
    i ^= (int)(l ^ l >> 32);

    return i;
  }

  public final void setZero()
  {
    this.m00 = 0.0D; this.m01 = 0.0D; this.m02 = 0.0D;
    this.m10 = 0.0D; this.m11 = 0.0D; this.m12 = 0.0D;
    this.m20 = 0.0D; this.m21 = 0.0D; this.m22 = 0.0D;
  }

  public final void negate()
  {
    this.m00 = (-this.m00); this.m01 = (-this.m01); this.m02 = (-this.m02);
    this.m10 = (-this.m10); this.m11 = (-this.m11); this.m12 = (-this.m12);
    this.m20 = (-this.m20); this.m21 = (-this.m21); this.m22 = (-this.m22);
  }

  public final void negate(Matrix3d paramMatrix3d)
  {
    set(paramMatrix3d);
    negate();
  }

  public final void transform(Tuple3d paramTuple3d)
  {
    transform(paramTuple3d, paramTuple3d);
  }

  public final void transform(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2)
  {
    paramTuple3d2.set(this.m00 * paramTuple3d1.x + this.m01 * paramTuple3d1.y + this.m02 * paramTuple3d1.z, this.m10 * paramTuple3d1.x + this.m11 * paramTuple3d1.y + this.m12 * paramTuple3d1.z, this.m20 * paramTuple3d1.x + this.m21 * paramTuple3d1.y + this.m22 * paramTuple3d1.z);
  }

  public final void transform(Tuple3f paramTuple3f)
  {
    transform(paramTuple3f, paramTuple3f);
  }

  public final void transform(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    paramTuple3f2.set((float)(this.m00 * paramTuple3f1.x + this.m01 * paramTuple3f1.y + this.m02 * paramTuple3f1.z), (float)(this.m10 * paramTuple3f1.x + this.m11 * paramTuple3f1.y + this.m12 * paramTuple3f1.z), (float)(this.m20 * paramTuple3f1.x + this.m21 * paramTuple3f1.y + this.m22 * paramTuple3f1.z));
  }

  private void set(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9)
  {
    this.m00 = paramDouble1; this.m01 = paramDouble2; this.m02 = paramDouble3;
    this.m10 = paramDouble4; this.m11 = paramDouble5; this.m12 = paramDouble6;
    this.m20 = paramDouble7; this.m21 = paramDouble8; this.m22 = paramDouble9;
  }

  private double SVD(Matrix3d paramMatrix3d)
  {
    double d1 = Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20 + this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21 + this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22) / 3.0D);

    double d2 = d1 == 0.0D ? 0.0D : 1.0D / d1;

    if (paramMatrix3d != null) {
      if (paramMatrix3d != this)
        paramMatrix3d.set(this);
      paramMatrix3d.mul(d2);
    }

    return d1;
  }

  private void setFromQuat(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    double d1 = paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2 + paramDouble3 * paramDouble3 + paramDouble4 * paramDouble4;
    double d2 = d1 > 0.0D ? 2.0D / d1 : 0.0D;

    double d3 = paramDouble1 * d2; double d4 = paramDouble2 * d2; double d5 = paramDouble3 * d2;
    double d6 = paramDouble4 * d3; double d7 = paramDouble4 * d4; double d8 = paramDouble4 * d5;
    double d9 = paramDouble1 * d3; double d10 = paramDouble1 * d4; double d11 = paramDouble1 * d5;
    double d12 = paramDouble2 * d4; double d13 = paramDouble2 * d5; double d14 = paramDouble3 * d5;

    this.m00 = (1.0D - (d12 + d14)); this.m01 = (d10 - d8); this.m02 = (d11 + d7);
    this.m10 = (d10 + d8); this.m11 = (1.0D - (d9 + d14)); this.m12 = (d13 - d6);
    this.m20 = (d11 - d7); this.m21 = (d13 + d6); this.m22 = (1.0D - (d9 + d12));
  }

  private void setFromAxisAngle(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    double d1 = Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2 + paramDouble3 * paramDouble3);

    d1 = 1.0D / d1;
    paramDouble1 *= d1;
    paramDouble2 *= d1;
    paramDouble3 *= d1;
    double d2 = Math.cos(paramDouble4);
    double d3 = Math.sin(paramDouble4);
    double d4 = 1.0D - d2;
    this.m00 = (d2 + paramDouble1 * paramDouble1 * d4);
    this.m11 = (d2 + paramDouble2 * paramDouble2 * d4);
    this.m22 = (d2 + paramDouble3 * paramDouble3 * d4);

    double d5 = paramDouble1 * paramDouble2 * d4;
    double d6 = paramDouble3 * d3;
    this.m01 = (d5 - d6);
    this.m10 = (d5 + d6);

    d5 = paramDouble1 * paramDouble3 * d4;
    d6 = paramDouble2 * d3;
    this.m02 = (d5 + d6);
    this.m20 = (d5 - d6);

    d5 = paramDouble2 * paramDouble3 * d4;
    d6 = paramDouble1 * d3;
    this.m12 = (d5 - d6);
    this.m21 = (d5 + d6);
  }

  public void setEulers(double[] paramArrayOfDouble) {
    setEulers(paramArrayOfDouble[0], paramArrayOfDouble[1], paramArrayOfDouble[2]);
  }

  public void getEulers(double[] paramArrayOfDouble)
  {
    double d4 = -this.m20;
    double d3 = Math.sqrt(1.0D - d4 * d4);
    double d5;
    double d6;
    double d1;
    double d2;
    if (d3 > 0.001D) {
      d5 = this.m22;
      d6 = this.m21;
      d1 = this.m00;
      d2 = this.m10;
    } else {
      d3 = 0.0D;

      d1 = 1.0D;
      d2 = 0.0D;
      d5 = this.m11;
      d6 = -this.m12;
    }
    paramArrayOfDouble[0] = Math.atan2(d2, d1);
    paramArrayOfDouble[1] = Math.atan2(d4, d3);
    paramArrayOfDouble[2] = Math.atan2(d6, d5);
  }

  public void setEulers(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d1 = Math.cos(paramDouble1);
    double d2 = Math.sin(paramDouble1);
    double d3 = Math.cos(paramDouble2);
    double d4 = Math.sin(paramDouble2);
    double d5 = Math.cos(paramDouble3);
    double d6 = Math.sin(paramDouble3);

    this.m00 = (d1 * d3);
    this.m01 = (-d2 * d5 + d1 * d4 * d6);
    this.m02 = (d2 * d6 + d1 * d4 * d5);
    this.m10 = (d2 * d3);
    this.m11 = (d1 * d5 + d2 * d4 * d6);
    this.m12 = (-d1 * d6 + d2 * d4 * d5);
    this.m20 = (-d4);
    this.m21 = (d3 * d6);
    this.m22 = (d3 * d5);
  }

  public void setEulersInv(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d1 = Math.cos(paramDouble1);
    double d2 = Math.sin(paramDouble1);
    double d3 = Math.cos(paramDouble2);
    double d4 = Math.sin(paramDouble2);
    double d5 = Math.cos(paramDouble3);
    double d6 = Math.sin(paramDouble3);

    this.m00 = (d3 * d1);
    this.m01 = (d3 * d2);
    this.m02 = (-d4);
    this.m10 = (d6 * d4 * d1 - d5 * d2);
    this.m11 = (d6 * d4 * d2 + d5 * d1);
    this.m12 = (d6 * d3);
    this.m20 = (d5 * d4 * d1 + d6 * d2);
    this.m21 = (d5 * d4 * d2 - d6 * d1);
    this.m22 = (d5 * d3);
  }
}