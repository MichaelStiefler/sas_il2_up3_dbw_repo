package com.maddox.JGP;

import java.io.Serializable;

public class Matrix4d
  implements Serializable, Cloneable
{
  public double m00;
  public double m01;
  public double m02;
  public double m03;
  public double m10;
  public double m11;
  public double m12;
  public double m13;
  public double m20;
  public double m21;
  public double m22;
  public double m23;
  public double m30;
  public double m31;
  public double m32;
  public double m33;

  public Matrix4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12, double paramDouble13, double paramDouble14, double paramDouble15, double paramDouble16)
  {
    set(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramDouble11, paramDouble12, paramDouble13, paramDouble14, paramDouble15, paramDouble16);
  }

  public Matrix4d(double[] paramArrayOfDouble)
  {
    set(paramArrayOfDouble);
  }

  public Matrix4d(Quat4d paramQuat4d, Tuple3d paramTuple3d, double paramDouble)
  {
    set(paramQuat4d, paramTuple3d, paramDouble);
  }

  public Matrix4d(Quat4f paramQuat4f, Tuple3d paramTuple3d, double paramDouble)
  {
    set(paramQuat4f, paramTuple3d, paramDouble);
  }

  public Matrix4d(Matrix4d paramMatrix4d)
  {
    set(paramMatrix4d);
  }

  public Matrix4d(Matrix4f paramMatrix4f)
  {
    set(paramMatrix4f);
  }

  public Matrix4d(Matrix3f paramMatrix3f, Tuple3d paramTuple3d, double paramDouble)
  {
    set(paramMatrix3f);
    mulRotationScale(paramDouble);
    setTranslation(paramTuple3d);
    this.m33 = 1.0D;
  }

  public Matrix4d(Matrix3d paramMatrix3d, Tuple3d paramTuple3d, double paramDouble)
  {
    set(paramMatrix3d, paramTuple3d, paramDouble);
  }

  public Matrix4d()
  {
    setZero();
  }

  public String toString()
  {
    String str = System.getProperty("line.separator");
    return "[" + str + "  [" + this.m00 + "\t" + this.m01 + "\t" + this.m02 + "\t" + this.m03 + "]" + str + "  [" + this.m10 + "\t" + this.m11 + "\t" + this.m12 + "\t" + this.m13 + "]" + str + "  [" + this.m20 + "\t" + this.m21 + "\t" + this.m22 + "\t" + this.m23 + "]" + str + "  [" + this.m30 + "\t" + this.m31 + "\t" + this.m32 + "\t" + this.m33 + "] ]";
  }

  public final void setIdentity()
  {
    this.m00 = 1.0D; this.m01 = 0.0D; this.m02 = 0.0D; this.m03 = 0.0D;
    this.m10 = 0.0D; this.m11 = 1.0D; this.m12 = 0.0D; this.m13 = 0.0D;
    this.m20 = 0.0D; this.m21 = 0.0D; this.m22 = 1.0D; this.m23 = 0.0D;
    this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
  }

  public final void setElement(int paramInt1, int paramInt2, double paramDouble)
  {
    if (paramInt1 == 0) {
      if (paramInt2 == 0)
        this.m00 = paramDouble;
      else if (paramInt2 == 1)
        this.m01 = paramDouble;
      else if (paramInt2 == 2)
        this.m02 = paramDouble;
      else
        this.m03 = paramDouble;
    } else if (paramInt1 == 1) {
      if (paramInt2 == 0)
        this.m10 = paramDouble;
      else if (paramInt2 == 1)
        this.m11 = paramDouble;
      else if (paramInt2 == 2)
        this.m12 = paramDouble;
      else
        this.m13 = paramDouble;
    } else if (paramInt1 == 2) {
      if (paramInt2 == 0)
        this.m20 = paramDouble;
      else if (paramInt2 == 1)
        this.m21 = paramDouble;
      else if (paramInt2 == 2)
        this.m22 = paramDouble;
      else
        this.m23 = paramDouble;
    }
    else if (paramInt2 == 0)
      this.m30 = paramDouble;
    else if (paramInt2 == 1)
      this.m31 = paramDouble;
    else if (paramInt2 == 2)
      this.m32 = paramDouble;
    else
      this.m33 = paramDouble;
  }

  public final double getElement(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0) {
      if (paramInt2 == 0)
        return this.m00;
      if (paramInt2 == 1)
        return this.m01;
      if (paramInt2 == 2) {
        return this.m02;
      }
      return this.m03;
    }if (paramInt1 == 1) {
      if (paramInt2 == 0)
        return this.m10;
      if (paramInt2 == 1)
        return this.m11;
      if (paramInt2 == 2) {
        return this.m12;
      }
      return this.m13;
    }if (paramInt1 == 2) {
      if (paramInt2 == 0)
        return this.m20;
      if (paramInt2 == 1)
        return this.m21;
      if (paramInt2 == 2) {
        return this.m22;
      }
      return this.m23;
    }
    if (paramInt2 == 0)
      return this.m30;
    if (paramInt2 == 1)
      return this.m31;
    if (paramInt2 == 2) {
      return this.m32;
    }
    return this.m33;
  }

  public final void get(Matrix3d paramMatrix3d)
  {
    SVD(paramMatrix3d, null);
  }

  public final void get(Matrix3f paramMatrix3f)
  {
    SVD(paramMatrix3f);
  }

  public final double get(Matrix3d paramMatrix3d, Tuple3d paramTuple3d)
  {
    get(paramTuple3d);
    return SVD(paramMatrix3d, null);
  }

  public final double get(Matrix3f paramMatrix3f, Tuple3d paramTuple3d)
  {
    get(paramTuple3d);
    return SVD(paramMatrix3f);
  }

  public final void get(Quat4f paramQuat4f)
  {
    paramQuat4f.set(this);
    paramQuat4f.normalize();
  }

  public final void get(Quat4d paramQuat4d)
  {
    paramQuat4d.set(this);
    paramQuat4d.normalize();
  }

  public final void get(Tuple3d paramTuple3d)
  {
    paramTuple3d.jdField_x_of_type_Double = this.m03;
    paramTuple3d.jdField_y_of_type_Double = this.m13;
    paramTuple3d.jdField_z_of_type_Double = this.m23;
  }

  public final void getRotationScale(Matrix3f paramMatrix3f)
  {
    paramMatrix3f.m00 = (float)this.m00; paramMatrix3f.m01 = (float)this.m01; paramMatrix3f.m02 = (float)this.m02;
    paramMatrix3f.m10 = (float)this.m10; paramMatrix3f.m11 = (float)this.m11; paramMatrix3f.m12 = (float)this.m12;
    paramMatrix3f.m20 = (float)this.m20; paramMatrix3f.m21 = (float)this.m21; paramMatrix3f.m22 = (float)this.m22;
  }

  public final void getRotationScale(Matrix3d paramMatrix3d)
  {
    paramMatrix3d.m00 = this.m00; paramMatrix3d.m01 = this.m01; paramMatrix3d.m02 = this.m02;
    paramMatrix3d.m10 = this.m10; paramMatrix3d.m11 = this.m11; paramMatrix3d.m12 = this.m12;
    paramMatrix3d.m20 = this.m20; paramMatrix3d.m21 = this.m21; paramMatrix3d.m22 = this.m22;
  }

  public final double getScale()
  {
    return SVD(null);
  }

  public final void setRotationScale(Matrix3d paramMatrix3d)
  {
    this.m00 = paramMatrix3d.m00; this.m01 = paramMatrix3d.m01; this.m02 = paramMatrix3d.m02;
    this.m10 = paramMatrix3d.m10; this.m11 = paramMatrix3d.m11; this.m12 = paramMatrix3d.m12;
    this.m20 = paramMatrix3d.m20; this.m21 = paramMatrix3d.m21; this.m22 = paramMatrix3d.m22;
  }

  public final void setRotationScale(Matrix3f paramMatrix3f)
  {
    this.m00 = paramMatrix3f.m00; this.m01 = paramMatrix3f.m01; this.m02 = paramMatrix3f.m02;
    this.m10 = paramMatrix3f.m10; this.m11 = paramMatrix3f.m11; this.m12 = paramMatrix3f.m12;
    this.m20 = paramMatrix3f.m20; this.m21 = paramMatrix3f.m21; this.m22 = paramMatrix3f.m22;
  }

  public final void setScale(double paramDouble)
  {
    SVD(null, this);
    mulRotationScale(paramDouble);
  }

  public final void setRow(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    if (paramInt == 0) {
      this.m00 = paramDouble1;
      this.m01 = paramDouble2;
      this.m02 = paramDouble3;
      this.m03 = paramDouble4;
    } else if (paramInt == 1) {
      this.m10 = paramDouble1;
      this.m11 = paramDouble2;
      this.m12 = paramDouble3;
      this.m13 = paramDouble4;
    } else if (paramInt == 2) {
      this.m20 = paramDouble1;
      this.m21 = paramDouble2;
      this.m22 = paramDouble3;
      this.m23 = paramDouble4;
    } else if (paramInt == 3) {
      this.m30 = paramDouble1;
      this.m31 = paramDouble2;
      this.m32 = paramDouble3;
      this.m33 = paramDouble4;
    }
  }

  public final void setRow(int paramInt, Tuple4d paramTuple4d)
  {
    if (paramInt == 0) {
      this.m00 = paramTuple4d.jdField_x_of_type_Double;
      this.m01 = paramTuple4d.jdField_y_of_type_Double;
      this.m02 = paramTuple4d.jdField_z_of_type_Double;
      this.m03 = paramTuple4d.w;
    } else if (paramInt == 1) {
      this.m10 = paramTuple4d.jdField_x_of_type_Double;
      this.m11 = paramTuple4d.jdField_y_of_type_Double;
      this.m12 = paramTuple4d.jdField_z_of_type_Double;
      this.m13 = paramTuple4d.w;
    } else if (paramInt == 2) {
      this.m20 = paramTuple4d.jdField_x_of_type_Double;
      this.m21 = paramTuple4d.jdField_y_of_type_Double;
      this.m22 = paramTuple4d.jdField_z_of_type_Double;
      this.m23 = paramTuple4d.w;
    } else if (paramInt == 3) {
      this.m30 = paramTuple4d.jdField_x_of_type_Double;
      this.m31 = paramTuple4d.jdField_y_of_type_Double;
      this.m32 = paramTuple4d.jdField_z_of_type_Double;
      this.m33 = paramTuple4d.w;
    }
  }

  public final void setRow(int paramInt, double[] paramArrayOfDouble)
  {
    if (paramInt == 0) {
      this.m00 = paramArrayOfDouble[0];
      this.m01 = paramArrayOfDouble[1];
      this.m02 = paramArrayOfDouble[2];
      this.m03 = paramArrayOfDouble[3];
    } else if (paramInt == 1) {
      this.m10 = paramArrayOfDouble[0];
      this.m11 = paramArrayOfDouble[1];
      this.m12 = paramArrayOfDouble[2];
      this.m13 = paramArrayOfDouble[3];
    } else if (paramInt == 2) {
      this.m20 = paramArrayOfDouble[0];
      this.m21 = paramArrayOfDouble[1];
      this.m22 = paramArrayOfDouble[2];
      this.m23 = paramArrayOfDouble[3];
    } else if (paramInt == 3) {
      this.m30 = paramArrayOfDouble[0];
      this.m31 = paramArrayOfDouble[1];
      this.m32 = paramArrayOfDouble[2];
      this.m33 = paramArrayOfDouble[3];
    }
  }

  public final void getRow(int paramInt, Tuple4d paramTuple4d)
  {
    if (paramInt == 0) {
      paramTuple4d.jdField_x_of_type_Double = this.m00;
      paramTuple4d.jdField_y_of_type_Double = this.m01;
      paramTuple4d.jdField_z_of_type_Double = this.m02;
      paramTuple4d.w = this.m03;
    } else if (paramInt == 1) {
      paramTuple4d.jdField_x_of_type_Double = this.m10;
      paramTuple4d.jdField_y_of_type_Double = this.m11;
      paramTuple4d.jdField_z_of_type_Double = this.m12;
      paramTuple4d.w = this.m13;
    } else if (paramInt == 2) {
      paramTuple4d.jdField_x_of_type_Double = this.m20;
      paramTuple4d.jdField_y_of_type_Double = this.m21;
      paramTuple4d.jdField_z_of_type_Double = this.m22;
      paramTuple4d.w = this.m23;
    } else if (paramInt == 3) {
      paramTuple4d.jdField_x_of_type_Double = this.m30;
      paramTuple4d.jdField_y_of_type_Double = this.m31;
      paramTuple4d.jdField_z_of_type_Double = this.m32;
      paramTuple4d.w = this.m33;
    }
  }

  public final void getRow(int paramInt, double[] paramArrayOfDouble)
  {
    if (paramInt == 0) {
      paramArrayOfDouble[0] = this.m00;
      paramArrayOfDouble[1] = this.m01;
      paramArrayOfDouble[2] = this.m02;
      paramArrayOfDouble[3] = this.m03;
    } else if (paramInt == 1) {
      paramArrayOfDouble[0] = this.m10;
      paramArrayOfDouble[1] = this.m11;
      paramArrayOfDouble[2] = this.m12;
      paramArrayOfDouble[3] = this.m13;
    } else if (paramInt == 2) {
      paramArrayOfDouble[0] = this.m20;
      paramArrayOfDouble[1] = this.m21;
      paramArrayOfDouble[2] = this.m22;
      paramArrayOfDouble[3] = this.m23;
    } else if (paramInt == 3) {
      paramArrayOfDouble[0] = this.m30;
      paramArrayOfDouble[1] = this.m31;
      paramArrayOfDouble[2] = this.m32;
      paramArrayOfDouble[3] = this.m33;
    }
  }

  public final void setColumn(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    if (paramInt == 0) {
      this.m00 = paramDouble1;
      this.m10 = paramDouble2;
      this.m20 = paramDouble3;
      this.m30 = paramDouble4;
    } else if (paramInt == 1) {
      this.m01 = paramDouble1;
      this.m11 = paramDouble2;
      this.m21 = paramDouble3;
      this.m31 = paramDouble4;
    } else if (paramInt == 2) {
      this.m02 = paramDouble1;
      this.m12 = paramDouble2;
      this.m22 = paramDouble3;
      this.m32 = paramDouble4;
    } else if (paramInt == 3) {
      this.m03 = paramDouble1;
      this.m13 = paramDouble2;
      this.m23 = paramDouble3;
      this.m33 = paramDouble4;
    }
  }

  public final void setColumn(int paramInt, Tuple4d paramTuple4d)
  {
    if (paramInt == 0) {
      this.m00 = paramTuple4d.jdField_x_of_type_Double;
      this.m10 = paramTuple4d.jdField_y_of_type_Double;
      this.m20 = paramTuple4d.jdField_z_of_type_Double;
      this.m30 = paramTuple4d.w;
    } else if (paramInt == 1) {
      this.m01 = paramTuple4d.jdField_x_of_type_Double;
      this.m11 = paramTuple4d.jdField_y_of_type_Double;
      this.m21 = paramTuple4d.jdField_z_of_type_Double;
      this.m31 = paramTuple4d.w;
    } else if (paramInt == 2) {
      this.m02 = paramTuple4d.jdField_x_of_type_Double;
      this.m12 = paramTuple4d.jdField_y_of_type_Double;
      this.m22 = paramTuple4d.jdField_z_of_type_Double;
      this.m32 = paramTuple4d.w;
    } else if (paramInt == 3) {
      this.m03 = paramTuple4d.jdField_x_of_type_Double;
      this.m13 = paramTuple4d.jdField_y_of_type_Double;
      this.m23 = paramTuple4d.jdField_z_of_type_Double;
      this.m33 = paramTuple4d.w;
    }
  }

  public final void setColumn(int paramInt, double[] paramArrayOfDouble)
  {
    if (paramInt == 0) {
      this.m00 = paramArrayOfDouble[0];
      this.m10 = paramArrayOfDouble[1];
      this.m20 = paramArrayOfDouble[2];
      this.m30 = paramArrayOfDouble[3];
    } else if (paramInt == 1) {
      this.m01 = paramArrayOfDouble[0];
      this.m11 = paramArrayOfDouble[1];
      this.m21 = paramArrayOfDouble[2];
      this.m31 = paramArrayOfDouble[3];
    } else if (paramInt == 2) {
      this.m02 = paramArrayOfDouble[0];
      this.m12 = paramArrayOfDouble[1];
      this.m22 = paramArrayOfDouble[2];
      this.m32 = paramArrayOfDouble[3];
    } else if (paramInt == 3) {
      this.m03 = paramArrayOfDouble[0];
      this.m13 = paramArrayOfDouble[1];
      this.m23 = paramArrayOfDouble[2];
      this.m33 = paramArrayOfDouble[3];
    }
  }

  public final void getColumn(int paramInt, Tuple4d paramTuple4d)
  {
    if (paramInt == 0) {
      paramTuple4d.jdField_x_of_type_Double = this.m00;
      paramTuple4d.jdField_y_of_type_Double = this.m10;
      paramTuple4d.jdField_z_of_type_Double = this.m20;
      paramTuple4d.w = this.m30;
    } else if (paramInt == 1) {
      paramTuple4d.jdField_x_of_type_Double = this.m01;
      paramTuple4d.jdField_y_of_type_Double = this.m11;
      paramTuple4d.jdField_z_of_type_Double = this.m21;
      paramTuple4d.w = this.m31;
    } else if (paramInt == 2) {
      paramTuple4d.jdField_x_of_type_Double = this.m02;
      paramTuple4d.jdField_y_of_type_Double = this.m12;
      paramTuple4d.jdField_z_of_type_Double = this.m22;
      paramTuple4d.w = this.m32;
    } else if (paramInt == 3) {
      paramTuple4d.jdField_x_of_type_Double = this.m03;
      paramTuple4d.jdField_y_of_type_Double = this.m13;
      paramTuple4d.jdField_z_of_type_Double = this.m23;
      paramTuple4d.w = this.m33;
    }
  }

  public final void getColumn(int paramInt, double[] paramArrayOfDouble)
  {
    if (paramInt == 0) {
      paramArrayOfDouble[0] = this.m00;
      paramArrayOfDouble[1] = this.m10;
      paramArrayOfDouble[2] = this.m20;
      paramArrayOfDouble[3] = this.m30;
    } else if (paramInt == 1) {
      paramArrayOfDouble[0] = this.m01;
      paramArrayOfDouble[1] = this.m11;
      paramArrayOfDouble[2] = this.m21;
      paramArrayOfDouble[3] = this.m31;
    } else if (paramInt == 2) {
      paramArrayOfDouble[0] = this.m02;
      paramArrayOfDouble[1] = this.m12;
      paramArrayOfDouble[2] = this.m22;
      paramArrayOfDouble[3] = this.m32;
    } else if (paramInt == 3) {
      paramArrayOfDouble[0] = this.m03;
      paramArrayOfDouble[1] = this.m13;
      paramArrayOfDouble[2] = this.m23;
      paramArrayOfDouble[3] = this.m33;
    }
  }

  public final void add(double paramDouble)
  {
    this.m00 += paramDouble; this.m01 += paramDouble; this.m02 += paramDouble; this.m03 += paramDouble;
    this.m10 += paramDouble; this.m11 += paramDouble; this.m12 += paramDouble; this.m13 += paramDouble;
    this.m20 += paramDouble; this.m21 += paramDouble; this.m22 += paramDouble; this.m23 += paramDouble;
    this.m30 += paramDouble; this.m31 += paramDouble; this.m32 += paramDouble; this.m33 += paramDouble;
  }

  public final void add(double paramDouble, Matrix4d paramMatrix4d)
  {
    set(paramMatrix4d);
    add(paramDouble);
  }

  public final void add(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2)
  {
    set(paramMatrix4d1.m00 + paramMatrix4d2.m00, paramMatrix4d1.m01 + paramMatrix4d2.m01, paramMatrix4d1.m02 + paramMatrix4d2.m02, paramMatrix4d1.m03 + paramMatrix4d2.m03, paramMatrix4d1.m10 + paramMatrix4d2.m10, paramMatrix4d1.m11 + paramMatrix4d2.m11, paramMatrix4d1.m12 + paramMatrix4d2.m12, paramMatrix4d1.m13 + paramMatrix4d2.m13, paramMatrix4d1.m20 + paramMatrix4d2.m20, paramMatrix4d1.m21 + paramMatrix4d2.m21, paramMatrix4d1.m22 + paramMatrix4d2.m22, paramMatrix4d1.m23 + paramMatrix4d2.m23, paramMatrix4d1.m30 + paramMatrix4d2.m30, paramMatrix4d1.m31 + paramMatrix4d2.m31, paramMatrix4d1.m32 + paramMatrix4d2.m32, paramMatrix4d1.m33 + paramMatrix4d2.m33);
  }

  public final void add(Matrix4d paramMatrix4d)
  {
    this.m00 += paramMatrix4d.m00; this.m01 += paramMatrix4d.m01; this.m02 += paramMatrix4d.m02; this.m03 += paramMatrix4d.m03;
    this.m10 += paramMatrix4d.m10; this.m11 += paramMatrix4d.m11; this.m12 += paramMatrix4d.m12; this.m13 += paramMatrix4d.m13;
    this.m20 += paramMatrix4d.m20; this.m21 += paramMatrix4d.m21; this.m22 += paramMatrix4d.m22; this.m23 += paramMatrix4d.m23;
    this.m30 += paramMatrix4d.m30; this.m31 += paramMatrix4d.m31; this.m32 += paramMatrix4d.m32; this.m33 += paramMatrix4d.m33;
  }

  public final void sub(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2)
  {
    set(paramMatrix4d1.m00 - paramMatrix4d2.m00, paramMatrix4d1.m01 - paramMatrix4d2.m01, paramMatrix4d1.m02 - paramMatrix4d2.m02, paramMatrix4d1.m03 - paramMatrix4d2.m03, paramMatrix4d1.m10 - paramMatrix4d2.m10, paramMatrix4d1.m11 - paramMatrix4d2.m11, paramMatrix4d1.m12 - paramMatrix4d2.m12, paramMatrix4d1.m13 - paramMatrix4d2.m13, paramMatrix4d1.m20 - paramMatrix4d2.m20, paramMatrix4d1.m21 - paramMatrix4d2.m21, paramMatrix4d1.m22 - paramMatrix4d2.m22, paramMatrix4d1.m23 - paramMatrix4d2.m23, paramMatrix4d1.m30 - paramMatrix4d2.m30, paramMatrix4d1.m31 - paramMatrix4d2.m31, paramMatrix4d1.m32 - paramMatrix4d2.m32, paramMatrix4d1.m33 - paramMatrix4d2.m33);
  }

  public final void sub(Matrix4d paramMatrix4d)
  {
    this.m00 -= paramMatrix4d.m00; this.m01 -= paramMatrix4d.m01; this.m02 -= paramMatrix4d.m02; this.m03 -= paramMatrix4d.m03;
    this.m10 -= paramMatrix4d.m10; this.m11 -= paramMatrix4d.m11; this.m12 -= paramMatrix4d.m12; this.m13 -= paramMatrix4d.m13;
    this.m20 -= paramMatrix4d.m20; this.m21 -= paramMatrix4d.m21; this.m22 -= paramMatrix4d.m22; this.m23 -= paramMatrix4d.m23;
    this.m30 -= paramMatrix4d.m30; this.m31 -= paramMatrix4d.m31; this.m32 -= paramMatrix4d.m32; this.m33 -= paramMatrix4d.m33;
  }

  public final void transpose()
  {
    double d = this.m01;
    this.m01 = this.m10;
    this.m10 = d;

    d = this.m02;
    this.m02 = this.m20;
    this.m20 = d;

    d = this.m03;
    this.m03 = this.m30;
    this.m30 = d;

    d = this.m12;
    this.m12 = this.m21;
    this.m21 = d;

    d = this.m13;
    this.m13 = this.m31;
    this.m31 = d;

    d = this.m23;
    this.m23 = this.m32;
    this.m32 = d;
  }

  public final void transpose(Matrix4d paramMatrix4d)
  {
    set(paramMatrix4d);
    transpose();
  }

  public final void set(double[] paramArrayOfDouble)
  {
    this.m00 = paramArrayOfDouble[0]; this.m01 = paramArrayOfDouble[1]; this.m02 = paramArrayOfDouble[2]; this.m03 = paramArrayOfDouble[3];
    this.m10 = paramArrayOfDouble[4]; this.m11 = paramArrayOfDouble[5]; this.m12 = paramArrayOfDouble[6]; this.m13 = paramArrayOfDouble[7];
    this.m20 = paramArrayOfDouble[8]; this.m21 = paramArrayOfDouble[9]; this.m22 = paramArrayOfDouble[10]; this.m23 = paramArrayOfDouble[11];
    this.m30 = paramArrayOfDouble[12]; this.m31 = paramArrayOfDouble[13]; this.m32 = paramArrayOfDouble[14]; this.m33 = paramArrayOfDouble[15];
  }

  public final void set(Matrix3f paramMatrix3f)
  {
    this.m00 = paramMatrix3f.m00; this.m01 = paramMatrix3f.m01; this.m02 = paramMatrix3f.m02; this.m03 = 0.0D;
    this.m10 = paramMatrix3f.m10; this.m11 = paramMatrix3f.m11; this.m12 = paramMatrix3f.m12; this.m13 = 0.0D;
    this.m20 = paramMatrix3f.m20; this.m21 = paramMatrix3f.m21; this.m22 = paramMatrix3f.m22; this.m23 = 0.0D;
    this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
  }

  public final void set(Matrix3d paramMatrix3d)
  {
    this.m00 = paramMatrix3d.m00; this.m01 = paramMatrix3d.m01; this.m02 = paramMatrix3d.m02; this.m03 = 0.0D;
    this.m10 = paramMatrix3d.m10; this.m11 = paramMatrix3d.m11; this.m12 = paramMatrix3d.m12; this.m13 = 0.0D;
    this.m20 = paramMatrix3d.m20; this.m21 = paramMatrix3d.m21; this.m22 = paramMatrix3d.m22; this.m23 = 0.0D;
    this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
  }

  public final void set(Quat4d paramQuat4d)
  {
    setFromQuat(paramQuat4d.jdField_x_of_type_Double, paramQuat4d.jdField_y_of_type_Double, paramQuat4d.jdField_z_of_type_Double, paramQuat4d.w);
  }

  public final void set(AxisAngle4d paramAxisAngle4d)
  {
    setFromAxisAngle(paramAxisAngle4d.jdField_x_of_type_Double, paramAxisAngle4d.jdField_y_of_type_Double, paramAxisAngle4d.jdField_z_of_type_Double, paramAxisAngle4d.angle);
  }

  public final void set(Quat4f paramQuat4f)
  {
    setFromQuat(paramQuat4f.jdField_x_of_type_Float, paramQuat4f.jdField_y_of_type_Float, paramQuat4f.jdField_z_of_type_Float, paramQuat4f.w);
  }

  public final void set(AxisAngle4f paramAxisAngle4f)
  {
    setFromAxisAngle(paramAxisAngle4f.jdField_x_of_type_Float, paramAxisAngle4f.jdField_y_of_type_Float, paramAxisAngle4f.jdField_z_of_type_Float, paramAxisAngle4f.angle);
  }

  public final void set(Quat4d paramQuat4d, Tuple3d paramTuple3d, double paramDouble)
  {
    set(paramQuat4d);
    mulRotationScale(paramDouble);
    this.m03 = paramTuple3d.jdField_x_of_type_Double;
    this.m13 = paramTuple3d.jdField_y_of_type_Double;
    this.m23 = paramTuple3d.jdField_z_of_type_Double;
  }

  public final void set(Quat4f paramQuat4f, Tuple3d paramTuple3d, double paramDouble)
  {
    set(paramQuat4f);
    mulRotationScale(paramDouble);
    this.m03 = paramTuple3d.jdField_x_of_type_Double;
    this.m13 = paramTuple3d.jdField_y_of_type_Double;
    this.m23 = paramTuple3d.jdField_z_of_type_Double;
  }

  public final void set(Quat4f paramQuat4f, Tuple3f paramTuple3f, float paramFloat)
  {
    set(paramQuat4f);
    mulRotationScale(paramFloat);
    this.m03 = paramTuple3f.jdField_x_of_type_Float;
    this.m13 = paramTuple3f.jdField_y_of_type_Float;
    this.m23 = paramTuple3f.jdField_z_of_type_Float;
  }

  public final void set(Matrix4d paramMatrix4d)
  {
    this.m00 = paramMatrix4d.m00; this.m01 = paramMatrix4d.m01; this.m02 = paramMatrix4d.m02; this.m03 = paramMatrix4d.m03;
    this.m10 = paramMatrix4d.m10; this.m11 = paramMatrix4d.m11; this.m12 = paramMatrix4d.m12; this.m13 = paramMatrix4d.m13;
    this.m20 = paramMatrix4d.m20; this.m21 = paramMatrix4d.m21; this.m22 = paramMatrix4d.m22; this.m23 = paramMatrix4d.m23;
    this.m30 = paramMatrix4d.m30; this.m31 = paramMatrix4d.m31; this.m32 = paramMatrix4d.m32; this.m33 = paramMatrix4d.m33;
  }

  public final void set(Matrix4f paramMatrix4f)
  {
    this.m00 = paramMatrix4f.m00; this.m01 = paramMatrix4f.m01; this.m02 = paramMatrix4f.m02; this.m03 = paramMatrix4f.m03;
    this.m10 = paramMatrix4f.m10; this.m11 = paramMatrix4f.m11; this.m12 = paramMatrix4f.m12; this.m13 = paramMatrix4f.m13;
    this.m20 = paramMatrix4f.m20; this.m21 = paramMatrix4f.m21; this.m22 = paramMatrix4f.m22; this.m23 = paramMatrix4f.m23;
    this.m30 = paramMatrix4f.m30; this.m31 = paramMatrix4f.m31; this.m32 = paramMatrix4f.m32; this.m33 = paramMatrix4f.m33;
  }

  public final void invert(Matrix4d paramMatrix4d)
  {
    set(paramMatrix4d);
    invert();
  }

  public final void invert()
  {
    double d = determinant();
    if (d == 0.0D)
      return;
    d = 1.0D / d;

    set(this.m11 * (this.m22 * this.m33 - this.m23 * this.m32) + this.m12 * (this.m23 * this.m31 - this.m21 * this.m33) + this.m13 * (this.m21 * this.m32 - this.m22 * this.m31), this.m21 * (this.m02 * this.m33 - this.m03 * this.m32) + this.m22 * (this.m03 * this.m31 - this.m01 * this.m33) + this.m23 * (this.m01 * this.m32 - this.m02 * this.m31), this.m31 * (this.m02 * this.m13 - this.m03 * this.m12) + this.m32 * (this.m03 * this.m11 - this.m01 * this.m13) + this.m33 * (this.m01 * this.m12 - this.m02 * this.m11), this.m01 * (this.m13 * this.m22 - this.m12 * this.m23) + this.m02 * (this.m11 * this.m23 - this.m13 * this.m21) + this.m03 * (this.m12 * this.m21 - this.m11 * this.m22), this.m12 * (this.m20 * this.m33 - this.m23 * this.m30) + this.m13 * (this.m22 * this.m30 - this.m20 * this.m32) + this.m10 * (this.m23 * this.m32 - this.m22 * this.m33), this.m22 * (this.m00 * this.m33 - this.m03 * this.m30) + this.m23 * (this.m02 * this.m30 - this.m00 * this.m32) + this.m20 * (this.m03 * this.m32 - this.m02 * this.m33), this.m32 * (this.m00 * this.m13 - this.m03 * this.m10) + this.m33 * (this.m02 * this.m10 - this.m00 * this.m12) + this.m30 * (this.m03 * this.m12 - this.m02 * this.m13), this.m02 * (this.m13 * this.m20 - this.m10 * this.m23) + this.m03 * (this.m10 * this.m22 - this.m12 * this.m20) + this.m00 * (this.m12 * this.m23 - this.m13 * this.m22), this.m13 * (this.m20 * this.m31 - this.m21 * this.m30) + this.m10 * (this.m21 * this.m33 - this.m23 * this.m31) + this.m11 * (this.m23 * this.m30 - this.m20 * this.m33), this.m23 * (this.m00 * this.m31 - this.m01 * this.m30) + this.m20 * (this.m01 * this.m33 - this.m03 * this.m31) + this.m21 * (this.m03 * this.m30 - this.m00 * this.m33), this.m33 * (this.m00 * this.m11 - this.m01 * this.m10) + this.m30 * (this.m01 * this.m13 - this.m03 * this.m11) + this.m31 * (this.m03 * this.m10 - this.m00 * this.m13), this.m03 * (this.m11 * this.m20 - this.m10 * this.m21) + this.m00 * (this.m13 * this.m21 - this.m11 * this.m23) + this.m01 * (this.m10 * this.m23 - this.m13 * this.m20), this.m10 * (this.m22 * this.m31 - this.m21 * this.m32) + this.m11 * (this.m20 * this.m32 - this.m22 * this.m30) + this.m12 * (this.m21 * this.m30 - this.m20 * this.m31), this.m20 * (this.m02 * this.m31 - this.m01 * this.m32) + this.m21 * (this.m00 * this.m32 - this.m02 * this.m30) + this.m22 * (this.m01 * this.m30 - this.m00 * this.m31), this.m30 * (this.m02 * this.m11 - this.m01 * this.m12) + this.m31 * (this.m00 * this.m12 - this.m02 * this.m10) + this.m32 * (this.m01 * this.m10 - this.m00 * this.m11), this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20));

    mul(d);
  }

  public final double determinant()
  {
    return (this.m00 * this.m11 - this.m01 * this.m10) * (this.m22 * this.m33 - this.m23 * this.m32) - (this.m00 * this.m12 - this.m02 * this.m10) * (this.m21 * this.m33 - this.m23 * this.m31) + (this.m00 * this.m13 - this.m03 * this.m10) * (this.m21 * this.m32 - this.m22 * this.m31) + (this.m01 * this.m12 - this.m02 * this.m11) * (this.m20 * this.m33 - this.m23 * this.m30) - (this.m01 * this.m13 - this.m03 * this.m11) * (this.m20 * this.m32 - this.m22 * this.m30) + (this.m02 * this.m13 - this.m03 * this.m12) * (this.m20 * this.m31 - this.m21 * this.m30);
  }

  public final void set(double paramDouble)
  {
    this.m00 = paramDouble; this.m01 = 0.0D; this.m02 = 0.0D; this.m03 = 0.0D;
    this.m10 = 0.0D; this.m11 = paramDouble; this.m12 = 0.0D; this.m13 = 0.0D;
    this.m20 = 0.0D; this.m21 = 0.0D; this.m22 = paramDouble; this.m23 = 0.0D;
    this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
  }

  public final void set(Tuple3d paramTuple3d)
  {
    setIdentity();
    setTranslation(paramTuple3d);
  }

  public final void set(double paramDouble, Tuple3d paramTuple3d)
  {
    set(paramDouble);
    setTranslation(paramTuple3d);
  }

  public final void set(Tuple3d paramTuple3d, double paramDouble)
  {
    this.m00 = paramDouble; this.m01 = 0.0D; this.m02 = 0.0D; this.m03 = (paramDouble * paramTuple3d.jdField_x_of_type_Double);
    this.m10 = 0.0D; this.m11 = paramDouble; this.m12 = 0.0D; this.m13 = (paramDouble * paramTuple3d.jdField_y_of_type_Double);
    this.m20 = 0.0D; this.m21 = 0.0D; this.m22 = paramDouble; this.m23 = (paramDouble * paramTuple3d.jdField_z_of_type_Double);
    this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
  }

  public final void set(Matrix3f paramMatrix3f, Tuple3f paramTuple3f, float paramFloat)
  {
    setRotationScale(paramMatrix3f);
    mulRotationScale(paramFloat);
    setTranslation(paramTuple3f);
    this.m33 = 1.0D;
  }

  public final void set(Matrix3d paramMatrix3d, Tuple3d paramTuple3d, double paramDouble)
  {
    setRotationScale(paramMatrix3d);
    mulRotationScale(paramDouble);
    setTranslation(paramTuple3d);
    this.m33 = 1.0D;
  }

  public final void setTranslation(Tuple3d paramTuple3d)
  {
    this.m03 = paramTuple3d.jdField_x_of_type_Double;
    this.m13 = paramTuple3d.jdField_y_of_type_Double;
    this.m23 = paramTuple3d.jdField_z_of_type_Double;
  }

  public final void rotX(double paramDouble)
  {
    double d1 = Math.cos(paramDouble);
    double d2 = Math.sin(paramDouble);
    this.m00 = 1.0D; this.m01 = 0.0D; this.m02 = 0.0D; this.m03 = 0.0D;
    this.m10 = 0.0D; this.m11 = d1; this.m12 = (-d2); this.m13 = 0.0D;
    this.m20 = 0.0D; this.m21 = d2; this.m22 = d1; this.m23 = 0.0D;
    this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
  }

  public final void rotY(double paramDouble)
  {
    double d1 = Math.cos(paramDouble);
    double d2 = Math.sin(paramDouble);
    this.m00 = d1; this.m01 = 0.0D; this.m02 = d2; this.m03 = 0.0D;
    this.m10 = 0.0D; this.m11 = 1.0D; this.m12 = 0.0D; this.m13 = 0.0D;
    this.m20 = (-d2); this.m21 = 0.0D; this.m22 = d1; this.m23 = 0.0D;
    this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
  }

  public final void rotZ(double paramDouble)
  {
    double d1 = Math.cos(paramDouble);
    double d2 = Math.sin(paramDouble);
    this.m00 = d1; this.m01 = (-d2); this.m02 = 0.0D; this.m03 = 0.0D;
    this.m10 = d2; this.m11 = d1; this.m12 = 0.0D; this.m13 = 0.0D;
    this.m20 = 0.0D; this.m21 = 0.0D; this.m22 = 1.0D; this.m23 = 0.0D;
    this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
  }

  public final void mul(double paramDouble)
  {
    this.m00 *= paramDouble; this.m01 *= paramDouble; this.m02 *= paramDouble; this.m03 *= paramDouble;
    this.m10 *= paramDouble; this.m11 *= paramDouble; this.m12 *= paramDouble; this.m13 *= paramDouble;
    this.m20 *= paramDouble; this.m21 *= paramDouble; this.m22 *= paramDouble; this.m23 *= paramDouble;
    this.m30 *= paramDouble; this.m31 *= paramDouble; this.m32 *= paramDouble; this.m33 *= paramDouble;
  }

  public final void mul(double paramDouble, Matrix4d paramMatrix4d)
  {
    set(paramMatrix4d);
    mul(paramDouble);
  }

  public final void mul(Matrix4d paramMatrix4d)
  {
    mul(this, paramMatrix4d);
  }

  public final void mul(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2)
  {
    set(paramMatrix4d1.m00 * paramMatrix4d2.m00 + paramMatrix4d1.m01 * paramMatrix4d2.m10 + paramMatrix4d1.m02 * paramMatrix4d2.m20 + paramMatrix4d1.m03 * paramMatrix4d2.m30, paramMatrix4d1.m00 * paramMatrix4d2.m01 + paramMatrix4d1.m01 * paramMatrix4d2.m11 + paramMatrix4d1.m02 * paramMatrix4d2.m21 + paramMatrix4d1.m03 * paramMatrix4d2.m31, paramMatrix4d1.m00 * paramMatrix4d2.m02 + paramMatrix4d1.m01 * paramMatrix4d2.m12 + paramMatrix4d1.m02 * paramMatrix4d2.m22 + paramMatrix4d1.m03 * paramMatrix4d2.m32, paramMatrix4d1.m00 * paramMatrix4d2.m03 + paramMatrix4d1.m01 * paramMatrix4d2.m13 + paramMatrix4d1.m02 * paramMatrix4d2.m23 + paramMatrix4d1.m03 * paramMatrix4d2.m33, paramMatrix4d1.m10 * paramMatrix4d2.m00 + paramMatrix4d1.m11 * paramMatrix4d2.m10 + paramMatrix4d1.m12 * paramMatrix4d2.m20 + paramMatrix4d1.m13 * paramMatrix4d2.m30, paramMatrix4d1.m10 * paramMatrix4d2.m01 + paramMatrix4d1.m11 * paramMatrix4d2.m11 + paramMatrix4d1.m12 * paramMatrix4d2.m21 + paramMatrix4d1.m13 * paramMatrix4d2.m31, paramMatrix4d1.m10 * paramMatrix4d2.m02 + paramMatrix4d1.m11 * paramMatrix4d2.m12 + paramMatrix4d1.m12 * paramMatrix4d2.m22 + paramMatrix4d1.m13 * paramMatrix4d2.m32, paramMatrix4d1.m10 * paramMatrix4d2.m03 + paramMatrix4d1.m11 * paramMatrix4d2.m13 + paramMatrix4d1.m12 * paramMatrix4d2.m23 + paramMatrix4d1.m13 * paramMatrix4d2.m33, paramMatrix4d1.m20 * paramMatrix4d2.m00 + paramMatrix4d1.m21 * paramMatrix4d2.m10 + paramMatrix4d1.m22 * paramMatrix4d2.m20 + paramMatrix4d1.m23 * paramMatrix4d2.m30, paramMatrix4d1.m20 * paramMatrix4d2.m01 + paramMatrix4d1.m21 * paramMatrix4d2.m11 + paramMatrix4d1.m22 * paramMatrix4d2.m21 + paramMatrix4d1.m23 * paramMatrix4d2.m31, paramMatrix4d1.m20 * paramMatrix4d2.m02 + paramMatrix4d1.m21 * paramMatrix4d2.m12 + paramMatrix4d1.m22 * paramMatrix4d2.m22 + paramMatrix4d1.m23 * paramMatrix4d2.m32, paramMatrix4d1.m20 * paramMatrix4d2.m03 + paramMatrix4d1.m21 * paramMatrix4d2.m13 + paramMatrix4d1.m22 * paramMatrix4d2.m23 + paramMatrix4d1.m23 * paramMatrix4d2.m33, paramMatrix4d1.m30 * paramMatrix4d2.m00 + paramMatrix4d1.m31 * paramMatrix4d2.m10 + paramMatrix4d1.m32 * paramMatrix4d2.m20 + paramMatrix4d1.m33 * paramMatrix4d2.m30, paramMatrix4d1.m30 * paramMatrix4d2.m01 + paramMatrix4d1.m31 * paramMatrix4d2.m11 + paramMatrix4d1.m32 * paramMatrix4d2.m21 + paramMatrix4d1.m33 * paramMatrix4d2.m31, paramMatrix4d1.m30 * paramMatrix4d2.m02 + paramMatrix4d1.m31 * paramMatrix4d2.m12 + paramMatrix4d1.m32 * paramMatrix4d2.m22 + paramMatrix4d1.m33 * paramMatrix4d2.m32, paramMatrix4d1.m30 * paramMatrix4d2.m03 + paramMatrix4d1.m31 * paramMatrix4d2.m13 + paramMatrix4d1.m32 * paramMatrix4d2.m23 + paramMatrix4d1.m33 * paramMatrix4d2.m33);
  }

  public final void mulTransposeBoth(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2)
  {
    mul(paramMatrix4d2, paramMatrix4d1);
    transpose();
  }

  public final void mulTransposeRight(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2)
  {
    set(paramMatrix4d1.m00 * paramMatrix4d2.m00 + paramMatrix4d1.m01 * paramMatrix4d2.m01 + paramMatrix4d1.m02 * paramMatrix4d2.m02 + paramMatrix4d1.m03 * paramMatrix4d2.m03, paramMatrix4d1.m00 * paramMatrix4d2.m10 + paramMatrix4d1.m01 * paramMatrix4d2.m11 + paramMatrix4d1.m02 * paramMatrix4d2.m12 + paramMatrix4d1.m03 * paramMatrix4d2.m13, paramMatrix4d1.m00 * paramMatrix4d2.m20 + paramMatrix4d1.m01 * paramMatrix4d2.m21 + paramMatrix4d1.m02 * paramMatrix4d2.m22 + paramMatrix4d1.m03 * paramMatrix4d2.m23, paramMatrix4d1.m00 * paramMatrix4d2.m30 + paramMatrix4d1.m01 * paramMatrix4d2.m31 + paramMatrix4d1.m02 * paramMatrix4d2.m32 + paramMatrix4d1.m03 * paramMatrix4d2.m33, paramMatrix4d1.m10 * paramMatrix4d2.m00 + paramMatrix4d1.m11 * paramMatrix4d2.m01 + paramMatrix4d1.m12 * paramMatrix4d2.m02 + paramMatrix4d1.m13 * paramMatrix4d2.m03, paramMatrix4d1.m10 * paramMatrix4d2.m10 + paramMatrix4d1.m11 * paramMatrix4d2.m11 + paramMatrix4d1.m12 * paramMatrix4d2.m12 + paramMatrix4d1.m13 * paramMatrix4d2.m13, paramMatrix4d1.m10 * paramMatrix4d2.m20 + paramMatrix4d1.m11 * paramMatrix4d2.m21 + paramMatrix4d1.m12 * paramMatrix4d2.m22 + paramMatrix4d1.m13 * paramMatrix4d2.m23, paramMatrix4d1.m10 * paramMatrix4d2.m30 + paramMatrix4d1.m11 * paramMatrix4d2.m31 + paramMatrix4d1.m12 * paramMatrix4d2.m32 + paramMatrix4d1.m13 * paramMatrix4d2.m33, paramMatrix4d1.m20 * paramMatrix4d2.m00 + paramMatrix4d1.m21 * paramMatrix4d2.m01 + paramMatrix4d1.m22 * paramMatrix4d2.m02 + paramMatrix4d1.m23 * paramMatrix4d2.m03, paramMatrix4d1.m20 * paramMatrix4d2.m10 + paramMatrix4d1.m21 * paramMatrix4d2.m11 + paramMatrix4d1.m22 * paramMatrix4d2.m12 + paramMatrix4d1.m23 * paramMatrix4d2.m13, paramMatrix4d1.m20 * paramMatrix4d2.m20 + paramMatrix4d1.m21 * paramMatrix4d2.m21 + paramMatrix4d1.m22 * paramMatrix4d2.m22 + paramMatrix4d1.m23 * paramMatrix4d2.m23, paramMatrix4d1.m20 * paramMatrix4d2.m30 + paramMatrix4d1.m21 * paramMatrix4d2.m31 + paramMatrix4d1.m22 * paramMatrix4d2.m32 + paramMatrix4d1.m23 * paramMatrix4d2.m33, paramMatrix4d1.m30 * paramMatrix4d2.m00 + paramMatrix4d1.m31 * paramMatrix4d2.m01 + paramMatrix4d1.m32 * paramMatrix4d2.m02 + paramMatrix4d1.m33 * paramMatrix4d2.m03, paramMatrix4d1.m30 * paramMatrix4d2.m10 + paramMatrix4d1.m31 * paramMatrix4d2.m11 + paramMatrix4d1.m32 * paramMatrix4d2.m12 + paramMatrix4d1.m33 * paramMatrix4d2.m13, paramMatrix4d1.m30 * paramMatrix4d2.m20 + paramMatrix4d1.m31 * paramMatrix4d2.m21 + paramMatrix4d1.m32 * paramMatrix4d2.m22 + paramMatrix4d1.m33 * paramMatrix4d2.m23, paramMatrix4d1.m30 * paramMatrix4d2.m30 + paramMatrix4d1.m31 * paramMatrix4d2.m31 + paramMatrix4d1.m32 * paramMatrix4d2.m32 + paramMatrix4d1.m33 * paramMatrix4d2.m33);
  }

  public final void mulTransposeLeft(Matrix4d paramMatrix4d1, Matrix4d paramMatrix4d2)
  {
    set(paramMatrix4d1.m00 * paramMatrix4d2.m00 + paramMatrix4d1.m10 * paramMatrix4d2.m10 + paramMatrix4d1.m20 * paramMatrix4d2.m20 + paramMatrix4d1.m30 * paramMatrix4d2.m30, paramMatrix4d1.m00 * paramMatrix4d2.m01 + paramMatrix4d1.m10 * paramMatrix4d2.m11 + paramMatrix4d1.m20 * paramMatrix4d2.m21 + paramMatrix4d1.m30 * paramMatrix4d2.m31, paramMatrix4d1.m00 * paramMatrix4d2.m02 + paramMatrix4d1.m10 * paramMatrix4d2.m12 + paramMatrix4d1.m20 * paramMatrix4d2.m22 + paramMatrix4d1.m30 * paramMatrix4d2.m32, paramMatrix4d1.m00 * paramMatrix4d2.m03 + paramMatrix4d1.m10 * paramMatrix4d2.m13 + paramMatrix4d1.m20 * paramMatrix4d2.m23 + paramMatrix4d1.m30 * paramMatrix4d2.m33, paramMatrix4d1.m01 * paramMatrix4d2.m00 + paramMatrix4d1.m11 * paramMatrix4d2.m10 + paramMatrix4d1.m21 * paramMatrix4d2.m20 + paramMatrix4d1.m31 * paramMatrix4d2.m30, paramMatrix4d1.m01 * paramMatrix4d2.m01 + paramMatrix4d1.m11 * paramMatrix4d2.m11 + paramMatrix4d1.m21 * paramMatrix4d2.m21 + paramMatrix4d1.m31 * paramMatrix4d2.m31, paramMatrix4d1.m01 * paramMatrix4d2.m02 + paramMatrix4d1.m11 * paramMatrix4d2.m12 + paramMatrix4d1.m21 * paramMatrix4d2.m22 + paramMatrix4d1.m31 * paramMatrix4d2.m32, paramMatrix4d1.m01 * paramMatrix4d2.m03 + paramMatrix4d1.m11 * paramMatrix4d2.m13 + paramMatrix4d1.m21 * paramMatrix4d2.m23 + paramMatrix4d1.m31 * paramMatrix4d2.m33, paramMatrix4d1.m02 * paramMatrix4d2.m00 + paramMatrix4d1.m12 * paramMatrix4d2.m10 + paramMatrix4d1.m22 * paramMatrix4d2.m20 + paramMatrix4d1.m32 * paramMatrix4d2.m30, paramMatrix4d1.m02 * paramMatrix4d2.m01 + paramMatrix4d1.m12 * paramMatrix4d2.m11 + paramMatrix4d1.m22 * paramMatrix4d2.m21 + paramMatrix4d1.m32 * paramMatrix4d2.m31, paramMatrix4d1.m02 * paramMatrix4d2.m02 + paramMatrix4d1.m12 * paramMatrix4d2.m12 + paramMatrix4d1.m22 * paramMatrix4d2.m22 + paramMatrix4d1.m32 * paramMatrix4d2.m32, paramMatrix4d1.m02 * paramMatrix4d2.m03 + paramMatrix4d1.m12 * paramMatrix4d2.m13 + paramMatrix4d1.m22 * paramMatrix4d2.m23 + paramMatrix4d1.m32 * paramMatrix4d2.m33, paramMatrix4d1.m03 * paramMatrix4d2.m00 + paramMatrix4d1.m13 * paramMatrix4d2.m10 + paramMatrix4d1.m23 * paramMatrix4d2.m20 + paramMatrix4d1.m33 * paramMatrix4d2.m30, paramMatrix4d1.m03 * paramMatrix4d2.m01 + paramMatrix4d1.m13 * paramMatrix4d2.m11 + paramMatrix4d1.m23 * paramMatrix4d2.m21 + paramMatrix4d1.m33 * paramMatrix4d2.m31, paramMatrix4d1.m03 * paramMatrix4d2.m02 + paramMatrix4d1.m13 * paramMatrix4d2.m12 + paramMatrix4d1.m23 * paramMatrix4d2.m22 + paramMatrix4d1.m33 * paramMatrix4d2.m32, paramMatrix4d1.m03 * paramMatrix4d2.m03 + paramMatrix4d1.m13 * paramMatrix4d2.m13 + paramMatrix4d1.m23 * paramMatrix4d2.m23 + paramMatrix4d1.m33 * paramMatrix4d2.m33);
  }

  public boolean equals(Matrix4d paramMatrix4d)
  {
    return (paramMatrix4d != null) && (this.m00 == paramMatrix4d.m00) && (this.m01 == paramMatrix4d.m01) && (this.m02 == paramMatrix4d.m02) && (this.m03 == paramMatrix4d.m03) && (this.m10 == paramMatrix4d.m10) && (this.m11 == paramMatrix4d.m11) && (this.m12 == paramMatrix4d.m12) && (this.m13 == paramMatrix4d.m13) && (this.m20 == paramMatrix4d.m20) && (this.m21 == paramMatrix4d.m21) && (this.m22 == paramMatrix4d.m22) && (this.m23 == paramMatrix4d.m23) && (this.m30 == paramMatrix4d.m30) && (this.m31 == paramMatrix4d.m31) && (this.m32 == paramMatrix4d.m32) && (this.m33 == paramMatrix4d.m33);
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof Matrix4d)) && (equals((Matrix4d)paramObject));
  }

  /** @deprecated */
  public boolean epsilonEquals(Matrix4d paramMatrix4d, float paramFloat)
  {
    return (Math.abs(this.m00 - paramMatrix4d.m00) <= paramFloat) && (Math.abs(this.m01 - paramMatrix4d.m01) <= paramFloat) && (Math.abs(this.m02 - paramMatrix4d.m02) <= paramFloat) && (Math.abs(this.m03 - paramMatrix4d.m03) <= paramFloat) && (Math.abs(this.m10 - paramMatrix4d.m10) <= paramFloat) && (Math.abs(this.m11 - paramMatrix4d.m11) <= paramFloat) && (Math.abs(this.m12 - paramMatrix4d.m12) <= paramFloat) && (Math.abs(this.m13 - paramMatrix4d.m13) <= paramFloat) && (Math.abs(this.m20 - paramMatrix4d.m20) <= paramFloat) && (Math.abs(this.m21 - paramMatrix4d.m21) <= paramFloat) && (Math.abs(this.m22 - paramMatrix4d.m22) <= paramFloat) && (Math.abs(this.m23 - paramMatrix4d.m23) <= paramFloat) && (Math.abs(this.m30 - paramMatrix4d.m30) <= paramFloat) && (Math.abs(this.m31 - paramMatrix4d.m31) <= paramFloat) && (Math.abs(this.m32 - paramMatrix4d.m32) <= paramFloat) && (Math.abs(this.m33 - paramMatrix4d.m33) <= paramFloat);
  }

  public boolean epsilonEquals(Matrix4d paramMatrix4d, double paramDouble)
  {
    return (Math.abs(this.m00 - paramMatrix4d.m00) <= paramDouble) && (Math.abs(this.m01 - paramMatrix4d.m01) <= paramDouble) && (Math.abs(this.m02 - paramMatrix4d.m02) <= paramDouble) && (Math.abs(this.m03 - paramMatrix4d.m03) <= paramDouble) && (Math.abs(this.m10 - paramMatrix4d.m10) <= paramDouble) && (Math.abs(this.m11 - paramMatrix4d.m11) <= paramDouble) && (Math.abs(this.m12 - paramMatrix4d.m12) <= paramDouble) && (Math.abs(this.m13 - paramMatrix4d.m13) <= paramDouble) && (Math.abs(this.m20 - paramMatrix4d.m20) <= paramDouble) && (Math.abs(this.m21 - paramMatrix4d.m21) <= paramDouble) && (Math.abs(this.m22 - paramMatrix4d.m22) <= paramDouble) && (Math.abs(this.m23 - paramMatrix4d.m23) <= paramDouble) && (Math.abs(this.m30 - paramMatrix4d.m30) <= paramDouble) && (Math.abs(this.m31 - paramMatrix4d.m31) <= paramDouble) && (Math.abs(this.m32 - paramMatrix4d.m32) <= paramDouble) && (Math.abs(this.m33 - paramMatrix4d.m33) <= paramDouble);
  }

  public int hashCode()
  {
    long l = Double.doubleToLongBits(this.m00);
    int i = (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m01);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m02);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m03);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m10);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m11);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m12);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m13);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m20);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m21);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m22);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m23);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m30);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m31);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m32);
    i ^= (int)(l ^ l >> 32);
    l = Double.doubleToLongBits(this.m33);
    i ^= (int)(l ^ l >> 32);

    return i;
  }

  public final void transform(Tuple4d paramTuple4d1, Tuple4d paramTuple4d2)
  {
    paramTuple4d2.set(this.m00 * paramTuple4d1.jdField_x_of_type_Double + this.m01 * paramTuple4d1.jdField_y_of_type_Double + this.m02 * paramTuple4d1.jdField_z_of_type_Double + this.m03 * paramTuple4d1.w, this.m10 * paramTuple4d1.jdField_x_of_type_Double + this.m11 * paramTuple4d1.jdField_y_of_type_Double + this.m12 * paramTuple4d1.jdField_z_of_type_Double + this.m13 * paramTuple4d1.w, this.m20 * paramTuple4d1.jdField_x_of_type_Double + this.m21 * paramTuple4d1.jdField_y_of_type_Double + this.m22 * paramTuple4d1.jdField_z_of_type_Double + this.m23 * paramTuple4d1.w, this.m30 * paramTuple4d1.jdField_x_of_type_Double + this.m31 * paramTuple4d1.jdField_y_of_type_Double + this.m32 * paramTuple4d1.jdField_z_of_type_Double + this.m33 * paramTuple4d1.w);
  }

  public final void transform(Tuple4d paramTuple4d)
  {
    transform(paramTuple4d, paramTuple4d);
  }

  public final void transform(Tuple4f paramTuple4f1, Tuple4f paramTuple4f2)
  {
    paramTuple4f2.set((float)(this.m00 * paramTuple4f1.jdField_x_of_type_Float + this.m01 * paramTuple4f1.jdField_y_of_type_Float + this.m02 * paramTuple4f1.jdField_z_of_type_Float + this.m03 * paramTuple4f1.w), (float)(this.m10 * paramTuple4f1.jdField_x_of_type_Float + this.m11 * paramTuple4f1.jdField_y_of_type_Float + this.m12 * paramTuple4f1.jdField_z_of_type_Float + this.m13 * paramTuple4f1.w), (float)(this.m20 * paramTuple4f1.jdField_x_of_type_Float + this.m21 * paramTuple4f1.jdField_y_of_type_Float + this.m22 * paramTuple4f1.jdField_z_of_type_Float + this.m23 * paramTuple4f1.w), (float)(this.m30 * paramTuple4f1.jdField_x_of_type_Float + this.m31 * paramTuple4f1.jdField_y_of_type_Float + this.m32 * paramTuple4f1.jdField_z_of_type_Float + this.m33 * paramTuple4f1.w));
  }

  public final void transform(Tuple4f paramTuple4f)
  {
    transform(paramTuple4f, paramTuple4f);
  }

  public final void transform(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    paramPoint3d2.set(this.m00 * paramPoint3d1.jdField_x_of_type_Double + this.m01 * paramPoint3d1.jdField_y_of_type_Double + this.m02 * paramPoint3d1.jdField_z_of_type_Double + this.m03, this.m10 * paramPoint3d1.jdField_x_of_type_Double + this.m11 * paramPoint3d1.jdField_y_of_type_Double + this.m12 * paramPoint3d1.jdField_z_of_type_Double + this.m13, this.m20 * paramPoint3d1.jdField_x_of_type_Double + this.m21 * paramPoint3d1.jdField_y_of_type_Double + this.m22 * paramPoint3d1.jdField_z_of_type_Double + this.m23);
  }

  public final void transform(Point3d paramPoint3d)
  {
    transform(paramPoint3d, paramPoint3d);
  }

  public final void transform(Point3f paramPoint3f1, Point3f paramPoint3f2)
  {
    paramPoint3f2.set((float)(this.m00 * paramPoint3f1.jdField_x_of_type_Float + this.m01 * paramPoint3f1.jdField_y_of_type_Float + this.m02 * paramPoint3f1.jdField_z_of_type_Float + this.m03), (float)(this.m10 * paramPoint3f1.jdField_x_of_type_Float + this.m11 * paramPoint3f1.jdField_y_of_type_Float + this.m12 * paramPoint3f1.jdField_z_of_type_Float + this.m13), (float)(this.m20 * paramPoint3f1.jdField_x_of_type_Float + this.m21 * paramPoint3f1.jdField_y_of_type_Float + this.m22 * paramPoint3f1.jdField_z_of_type_Float + this.m23));
  }

  public final void transform(Point3f paramPoint3f)
  {
    transform(paramPoint3f, paramPoint3f);
  }

  public final void transform(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2)
  {
    paramTuple3d2.set(this.m00 * paramTuple3d1.jdField_x_of_type_Double + this.m01 * paramTuple3d1.jdField_y_of_type_Double + this.m02 * paramTuple3d1.jdField_z_of_type_Double, this.m10 * paramTuple3d1.jdField_x_of_type_Double + this.m11 * paramTuple3d1.jdField_y_of_type_Double + this.m12 * paramTuple3d1.jdField_z_of_type_Double, this.m20 * paramTuple3d1.jdField_x_of_type_Double + this.m21 * paramTuple3d1.jdField_y_of_type_Double + this.m22 * paramTuple3d1.jdField_z_of_type_Double);
  }

  public final void transform(Tuple3d paramTuple3d)
  {
    transform(paramTuple3d, paramTuple3d);
  }

  public final void transform(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    paramTuple3f2.set((float)(this.m00 * paramTuple3f1.jdField_x_of_type_Float + this.m01 * paramTuple3f1.jdField_y_of_type_Float + this.m02 * paramTuple3f1.jdField_z_of_type_Float), (float)(this.m10 * paramTuple3f1.jdField_x_of_type_Float + this.m11 * paramTuple3f1.jdField_y_of_type_Float + this.m12 * paramTuple3f1.jdField_z_of_type_Float), (float)(this.m20 * paramTuple3f1.jdField_x_of_type_Float + this.m21 * paramTuple3f1.jdField_y_of_type_Float + this.m22 * paramTuple3f1.jdField_z_of_type_Float));
  }

  public final void transform(Tuple3f paramTuple3f)
  {
    transform(paramTuple3f, paramTuple3f);
  }

  public final void setRotation(Matrix3d paramMatrix3d)
  {
    double d = SVD(null, null);
    setRotationScale(paramMatrix3d);
    mulRotationScale(d);
  }

  public final void setRotation(Matrix3f paramMatrix3f)
  {
    double d = SVD(null, null);
    setRotationScale(paramMatrix3f);
    mulRotationScale(d);
  }

  public final void setRotation(Quat4f paramQuat4f)
  {
    double d1 = SVD(null, null);

    double d2 = this.m03;
    double d3 = this.m13;
    double d4 = this.m23;
    double d5 = this.m30;
    double d6 = this.m31;
    double d7 = this.m32;
    double d8 = this.m33;

    set(paramQuat4f);
    mulRotationScale(d1);

    this.m03 = d2;
    this.m13 = d3;
    this.m23 = d4;
    this.m30 = d5;
    this.m31 = d6;
    this.m32 = d7;
    this.m33 = d8;
  }

  public final void setRotation(Quat4d paramQuat4d)
  {
    double d1 = SVD(null, null);

    double d2 = this.m03;
    double d3 = this.m13;
    double d4 = this.m23;
    double d5 = this.m30;
    double d6 = this.m31;
    double d7 = this.m32;
    double d8 = this.m33;

    set(paramQuat4d);
    mulRotationScale(d1);

    this.m03 = d2;
    this.m13 = d3;
    this.m23 = d4;
    this.m30 = d5;
    this.m31 = d6;
    this.m32 = d7;
    this.m33 = d8;
  }

  public final void setRotation(AxisAngle4d paramAxisAngle4d)
  {
    double d1 = SVD(null, null);

    double d2 = this.m03;
    double d3 = this.m13;
    double d4 = this.m23;
    double d5 = this.m30;
    double d6 = this.m31;
    double d7 = this.m32;
    double d8 = this.m33;

    set(paramAxisAngle4d);
    mulRotationScale(d1);

    this.m03 = d2;
    this.m13 = d3;
    this.m23 = d4;
    this.m30 = d5;
    this.m31 = d6;
    this.m32 = d7;
    this.m33 = d8;
  }

  public final void setZero()
  {
    this.m00 = 0.0D; this.m01 = 0.0D; this.m02 = 0.0D; this.m03 = 0.0D;
    this.m10 = 0.0D; this.m11 = 0.0D; this.m12 = 0.0D; this.m13 = 0.0D;
    this.m20 = 0.0D; this.m21 = 0.0D; this.m22 = 0.0D; this.m23 = 0.0D;
    this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 0.0D;
  }

  public final void negate()
  {
    this.m00 = (-this.m00); this.m01 = (-this.m01); this.m02 = (-this.m02); this.m03 = (-this.m03);
    this.m10 = (-this.m10); this.m11 = (-this.m11); this.m12 = (-this.m12); this.m13 = (-this.m13);
    this.m20 = (-this.m20); this.m21 = (-this.m21); this.m22 = (-this.m22); this.m23 = (-this.m23);
    this.m30 = (-this.m30); this.m31 = (-this.m31); this.m32 = (-this.m32); this.m33 = (-this.m33);
  }

  public final void negate(Matrix4d paramMatrix4d)
  {
    set(paramMatrix4d);
    negate();
  }

  private void set(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12, double paramDouble13, double paramDouble14, double paramDouble15, double paramDouble16)
  {
    this.m00 = paramDouble1; this.m01 = paramDouble2; this.m02 = paramDouble3; this.m03 = paramDouble4;
    this.m10 = paramDouble5; this.m11 = paramDouble6; this.m12 = paramDouble7; this.m13 = paramDouble8;
    this.m20 = paramDouble9; this.m21 = paramDouble10; this.m22 = paramDouble11; this.m23 = paramDouble12;
    this.m30 = paramDouble13; this.m31 = paramDouble14; this.m32 = paramDouble15; this.m33 = paramDouble16;
  }

  private double SVD(Matrix3d paramMatrix3d, Matrix4d paramMatrix4d)
  {
    double d1 = Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20 + this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21 + this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22) / 3.0D);

    double d2 = d1 == 0.0D ? 0.0D : 1.0D / d1;

    if (paramMatrix3d != null) {
      getRotationScale(paramMatrix3d);
      paramMatrix3d.mul(d2);
    }

    if (paramMatrix4d != null) {
      if (paramMatrix4d != this)
        paramMatrix4d.setRotationScale(this);
      paramMatrix4d.mulRotationScale(d2);
    }

    return d1;
  }

  private float SVD(Matrix3f paramMatrix3f)
  {
    double d1 = Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20 + this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21 + this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22) / 3.0D);

    double d2 = d1 == 0.0D ? 0.0D : 1.0D / d1;

    if (paramMatrix3f != null) {
      getRotationScale(paramMatrix3f);
      paramMatrix3f.mul((float)d2);
    }

    return (float)d1;
  }

  private void mulRotationScale(double paramDouble)
  {
    this.m00 *= paramDouble; this.m01 *= paramDouble; this.m02 *= paramDouble;
    this.m10 *= paramDouble; this.m11 *= paramDouble; this.m12 *= paramDouble;
    this.m20 *= paramDouble; this.m21 *= paramDouble; this.m22 *= paramDouble;
  }

  private void setRotationScale(Matrix4d paramMatrix4d)
  {
    this.m00 = paramMatrix4d.m00; this.m01 = paramMatrix4d.m01; this.m02 = paramMatrix4d.m02;
    this.m10 = paramMatrix4d.m10; this.m11 = paramMatrix4d.m11; this.m12 = paramMatrix4d.m12;
    this.m20 = paramMatrix4d.m20; this.m21 = paramMatrix4d.m21; this.m22 = paramMatrix4d.m22;
  }

  private void setTranslation(Tuple3f paramTuple3f)
  {
    this.m03 = paramTuple3f.jdField_x_of_type_Float;
    this.m13 = paramTuple3f.jdField_y_of_type_Float;
    this.m23 = paramTuple3f.jdField_z_of_type_Float;
  }
  private void setFromQuat(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    double d1 = paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2 + paramDouble3 * paramDouble3 + paramDouble4 * paramDouble4;
    double d2 = d1 > 0.0D ? 2.0D / d1 : 0.0D;

    double d3 = paramDouble1 * d2; double d4 = paramDouble2 * d2; double d5 = paramDouble3 * d2;
    double d6 = paramDouble4 * d3; double d7 = paramDouble4 * d4; double d8 = paramDouble4 * d5;
    double d9 = paramDouble1 * d3; double d10 = paramDouble1 * d4; double d11 = paramDouble1 * d5;
    double d12 = paramDouble2 * d4; double d13 = paramDouble2 * d5; double d14 = paramDouble3 * d5;

    setIdentity();
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

    this.m03 = 0.0D;
    this.m13 = 0.0D;
    this.m23 = 0.0D;

    this.m30 = 0.0D;
    this.m31 = 0.0D;
    this.m32 = 0.0D;
    this.m33 = 1.0D;
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

    this.m03 = 0.0D;
    this.m13 = 0.0D;
    this.m23 = 0.0D;

    this.m30 = 0.0D;
    this.m31 = 0.0D;
    this.m32 = 0.0D;
    this.m33 = 1.0D;
  }
}