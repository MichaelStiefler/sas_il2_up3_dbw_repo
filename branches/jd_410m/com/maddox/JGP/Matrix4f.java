package com.maddox.JGP;

import java.io.Serializable;

public class Matrix4f
  implements Serializable, Cloneable
{
  public float m00;
  public float m01;
  public float m02;
  public float m03;
  public float m10;
  public float m11;
  public float m12;
  public float m13;
  public float m20;
  public float m21;
  public float m22;
  public float m23;
  public float m30;
  public float m31;
  public float m32;
  public float m33;

  public Matrix4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    set(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12, paramFloat13, paramFloat14, paramFloat15, paramFloat16);
  }

  public Matrix4f(float[] paramArrayOfFloat)
  {
    set(paramArrayOfFloat);
  }

  public Matrix4f(Quat4f paramQuat4f, Tuple3f paramTuple3f, float paramFloat)
  {
    set(paramQuat4f, paramTuple3f, paramFloat);
  }

  public Matrix4f(Matrix4d paramMatrix4d)
  {
    set(paramMatrix4d);
  }

  public Matrix4f(Matrix4f paramMatrix4f)
  {
    set(paramMatrix4f);
  }

  public Matrix4f(Matrix3f paramMatrix3f, Tuple3f paramTuple3f, float paramFloat)
  {
    set(paramMatrix3f);
    mulRotationScale(paramFloat);
    setTranslation(paramTuple3f);
    this.m33 = 1.0F;
  }

  public Matrix4f()
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
    this.m00 = 1.0F; this.m01 = 0.0F; this.m02 = 0.0F; this.m03 = 0.0F;
    this.m10 = 0.0F; this.m11 = 1.0F; this.m12 = 0.0F; this.m13 = 0.0F;
    this.m20 = 0.0F; this.m21 = 0.0F; this.m22 = 1.0F; this.m23 = 0.0F;
    this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
  }

  public final void setElement(int paramInt1, int paramInt2, float paramFloat)
  {
    if (paramInt1 == 0) {
      if (paramInt2 == 0)
        this.m00 = paramFloat;
      else if (paramInt2 == 1)
        this.m01 = paramFloat;
      else if (paramInt2 == 2)
        this.m02 = paramFloat;
      else
        this.m03 = paramFloat;
    } else if (paramInt1 == 1) {
      if (paramInt2 == 0)
        this.m10 = paramFloat;
      else if (paramInt2 == 1)
        this.m11 = paramFloat;
      else if (paramInt2 == 2)
        this.m12 = paramFloat;
      else
        this.m13 = paramFloat;
    } else if (paramInt1 == 2) {
      if (paramInt2 == 0)
        this.m20 = paramFloat;
      else if (paramInt2 == 1)
        this.m21 = paramFloat;
      else if (paramInt2 == 2)
        this.m22 = paramFloat;
      else
        this.m23 = paramFloat;
    }
    else if (paramInt2 == 0)
      this.m30 = paramFloat;
    else if (paramInt2 == 1)
      this.m31 = paramFloat;
    else if (paramInt2 == 2)
      this.m32 = paramFloat;
    else
      this.m33 = paramFloat;
  }

  public final float getElement(int paramInt1, int paramInt2)
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

  public final void setScale(float paramFloat)
  {
    SVD(null, this);
    mulRotationScale(paramFloat);
  }

  public final void get(Matrix3d paramMatrix3d)
  {
    SVD(paramMatrix3d);
  }

  public final void get(Matrix3f paramMatrix3f)
  {
    SVD(paramMatrix3f, null);
  }

  public final float get(Matrix3f paramMatrix3f, Tuple3f paramTuple3f)
  {
    get(paramTuple3f);
    return SVD(paramMatrix3f, null);
  }

  public final void get(Quat4f paramQuat4f)
  {
    paramQuat4f.set(this);
    paramQuat4f.normalize();
  }

  public final void get(Tuple3f paramTuple3f)
  {
    paramTuple3f.x = this.m03;
    paramTuple3f.y = this.m13;
    paramTuple3f.z = this.m23;
  }

  public final void getRotationScale(Matrix3f paramMatrix3f)
  {
    paramMatrix3f.m00 = this.m00; paramMatrix3f.m01 = this.m01; paramMatrix3f.m02 = this.m02;
    paramMatrix3f.m10 = this.m10; paramMatrix3f.m11 = this.m11; paramMatrix3f.m12 = this.m12;
    paramMatrix3f.m20 = this.m20; paramMatrix3f.m21 = this.m21; paramMatrix3f.m22 = this.m22;
  }

  public final float getScale()
  {
    return SVD(null);
  }

  public final void setRotationScale(Matrix3f paramMatrix3f)
  {
    this.m00 = paramMatrix3f.m00; this.m01 = paramMatrix3f.m01; this.m02 = paramMatrix3f.m02;
    this.m10 = paramMatrix3f.m10; this.m11 = paramMatrix3f.m11; this.m12 = paramMatrix3f.m12;
    this.m20 = paramMatrix3f.m20; this.m21 = paramMatrix3f.m21; this.m22 = paramMatrix3f.m22;
  }

  public final void setRow(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (paramInt == 0) {
      this.m00 = paramFloat1;
      this.m01 = paramFloat2;
      this.m02 = paramFloat3;
      this.m03 = paramFloat4;
    } else if (paramInt == 1) {
      this.m10 = paramFloat1;
      this.m11 = paramFloat2;
      this.m12 = paramFloat3;
      this.m13 = paramFloat4;
    } else if (paramInt == 2) {
      this.m20 = paramFloat1;
      this.m21 = paramFloat2;
      this.m22 = paramFloat3;
      this.m23 = paramFloat4;
    } else if (paramInt == 3) {
      this.m30 = paramFloat1;
      this.m31 = paramFloat2;
      this.m32 = paramFloat3;
      this.m33 = paramFloat4;
    }
  }

  public final void setRow(int paramInt, Tuple4f paramTuple4f)
  {
    if (paramInt == 0) {
      this.m00 = paramTuple4f.x;
      this.m01 = paramTuple4f.y;
      this.m02 = paramTuple4f.z;
      this.m03 = paramTuple4f.w;
    } else if (paramInt == 1) {
      this.m10 = paramTuple4f.x;
      this.m11 = paramTuple4f.y;
      this.m12 = paramTuple4f.z;
      this.m13 = paramTuple4f.w;
    } else if (paramInt == 2) {
      this.m20 = paramTuple4f.x;
      this.m21 = paramTuple4f.y;
      this.m22 = paramTuple4f.z;
      this.m23 = paramTuple4f.w;
    } else if (paramInt == 3) {
      this.m30 = paramTuple4f.x;
      this.m31 = paramTuple4f.y;
      this.m32 = paramTuple4f.z;
      this.m33 = paramTuple4f.w;
    }
  }

  public final void setRow(int paramInt, float[] paramArrayOfFloat)
  {
    if (paramInt == 0) {
      this.m00 = paramArrayOfFloat[0];
      this.m01 = paramArrayOfFloat[1];
      this.m02 = paramArrayOfFloat[2];
      this.m03 = paramArrayOfFloat[3];
    } else if (paramInt == 1) {
      this.m10 = paramArrayOfFloat[0];
      this.m11 = paramArrayOfFloat[1];
      this.m12 = paramArrayOfFloat[2];
      this.m13 = paramArrayOfFloat[3];
    } else if (paramInt == 2) {
      this.m20 = paramArrayOfFloat[0];
      this.m21 = paramArrayOfFloat[1];
      this.m22 = paramArrayOfFloat[2];
      this.m23 = paramArrayOfFloat[3];
    } else if (paramInt == 3) {
      this.m30 = paramArrayOfFloat[0];
      this.m31 = paramArrayOfFloat[1];
      this.m32 = paramArrayOfFloat[2];
      this.m33 = paramArrayOfFloat[3];
    }
  }

  public final void getRow(int paramInt, Vector4f paramVector4f)
  {
    if (paramInt == 0) {
      paramVector4f.x = this.m00;
      paramVector4f.y = this.m01;
      paramVector4f.z = this.m02;
      paramVector4f.w = this.m03;
    } else if (paramInt == 1) {
      paramVector4f.x = this.m10;
      paramVector4f.y = this.m11;
      paramVector4f.z = this.m12;
      paramVector4f.w = this.m13;
    } else if (paramInt == 2) {
      paramVector4f.x = this.m20;
      paramVector4f.y = this.m21;
      paramVector4f.z = this.m22;
      paramVector4f.w = this.m23;
    } else if (paramInt == 3) {
      paramVector4f.x = this.m30;
      paramVector4f.y = this.m31;
      paramVector4f.z = this.m32;
      paramVector4f.w = this.m33;
    }
  }

  public final void getRow(int paramInt, float[] paramArrayOfFloat)
  {
    if (paramInt == 0) {
      paramArrayOfFloat[0] = this.m00;
      paramArrayOfFloat[1] = this.m01;
      paramArrayOfFloat[2] = this.m02;
      paramArrayOfFloat[3] = this.m03;
    } else if (paramInt == 1) {
      paramArrayOfFloat[0] = this.m10;
      paramArrayOfFloat[1] = this.m11;
      paramArrayOfFloat[2] = this.m12;
      paramArrayOfFloat[3] = this.m13;
    } else if (paramInt == 2) {
      paramArrayOfFloat[0] = this.m20;
      paramArrayOfFloat[1] = this.m21;
      paramArrayOfFloat[2] = this.m22;
      paramArrayOfFloat[3] = this.m23;
    } else if (paramInt == 3) {
      paramArrayOfFloat[0] = this.m30;
      paramArrayOfFloat[1] = this.m31;
      paramArrayOfFloat[2] = this.m32;
      paramArrayOfFloat[3] = this.m33;
    }
  }

  public final void setColumn(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (paramInt == 0) {
      this.m00 = paramFloat1;
      this.m10 = paramFloat2;
      this.m20 = paramFloat3;
      this.m30 = paramFloat4;
    } else if (paramInt == 1) {
      this.m01 = paramFloat1;
      this.m11 = paramFloat2;
      this.m21 = paramFloat3;
      this.m31 = paramFloat4;
    } else if (paramInt == 2) {
      this.m02 = paramFloat1;
      this.m12 = paramFloat2;
      this.m22 = paramFloat3;
      this.m32 = paramFloat4;
    } else if (paramInt == 3) {
      this.m03 = paramFloat1;
      this.m13 = paramFloat2;
      this.m23 = paramFloat3;
      this.m33 = paramFloat4;
    }
  }

  public final void setColumn(int paramInt, Vector4f paramVector4f)
  {
    if (paramInt == 0) {
      this.m00 = paramVector4f.x;
      this.m10 = paramVector4f.y;
      this.m20 = paramVector4f.z;
      this.m30 = paramVector4f.w;
    } else if (paramInt == 1) {
      this.m01 = paramVector4f.x;
      this.m11 = paramVector4f.y;
      this.m21 = paramVector4f.z;
      this.m31 = paramVector4f.w;
    } else if (paramInt == 2) {
      this.m02 = paramVector4f.x;
      this.m12 = paramVector4f.y;
      this.m22 = paramVector4f.z;
      this.m32 = paramVector4f.w;
    } else if (paramInt == 3) {
      this.m03 = paramVector4f.x;
      this.m13 = paramVector4f.y;
      this.m23 = paramVector4f.z;
      this.m33 = paramVector4f.w;
    }
  }

  public final void setColumn(int paramInt, float[] paramArrayOfFloat)
  {
    if (paramInt == 0) {
      this.m00 = paramArrayOfFloat[0];
      this.m10 = paramArrayOfFloat[1];
      this.m20 = paramArrayOfFloat[2];
      this.m30 = paramArrayOfFloat[3];
    } else if (paramInt == 1) {
      this.m01 = paramArrayOfFloat[0];
      this.m11 = paramArrayOfFloat[1];
      this.m21 = paramArrayOfFloat[2];
      this.m31 = paramArrayOfFloat[3];
    } else if (paramInt == 2) {
      this.m02 = paramArrayOfFloat[0];
      this.m12 = paramArrayOfFloat[1];
      this.m22 = paramArrayOfFloat[2];
      this.m32 = paramArrayOfFloat[3];
    } else if (paramInt == 3) {
      this.m03 = paramArrayOfFloat[0];
      this.m13 = paramArrayOfFloat[1];
      this.m23 = paramArrayOfFloat[2];
      this.m33 = paramArrayOfFloat[3];
    }
  }

  public final void getColumn(int paramInt, Vector4f paramVector4f)
  {
    if (paramInt == 0) {
      paramVector4f.x = this.m00;
      paramVector4f.y = this.m10;
      paramVector4f.z = this.m20;
      paramVector4f.w = this.m30;
    } else if (paramInt == 1) {
      paramVector4f.x = this.m01;
      paramVector4f.y = this.m11;
      paramVector4f.z = this.m21;
      paramVector4f.w = this.m31;
    } else if (paramInt == 2) {
      paramVector4f.x = this.m02;
      paramVector4f.y = this.m12;
      paramVector4f.z = this.m22;
      paramVector4f.w = this.m32;
    } else if (paramInt == 3) {
      paramVector4f.x = this.m03;
      paramVector4f.y = this.m13;
      paramVector4f.z = this.m23;
      paramVector4f.w = this.m33;
    }
  }

  public final void getColumn(int paramInt, float[] paramArrayOfFloat)
  {
    if (paramInt == 0) {
      paramArrayOfFloat[0] = this.m00;
      paramArrayOfFloat[1] = this.m10;
      paramArrayOfFloat[2] = this.m20;
      paramArrayOfFloat[3] = this.m30;
    } else if (paramInt == 1) {
      paramArrayOfFloat[0] = this.m01;
      paramArrayOfFloat[1] = this.m11;
      paramArrayOfFloat[2] = this.m21;
      paramArrayOfFloat[3] = this.m31;
    } else if (paramInt == 2) {
      paramArrayOfFloat[0] = this.m02;
      paramArrayOfFloat[1] = this.m12;
      paramArrayOfFloat[2] = this.m22;
      paramArrayOfFloat[3] = this.m32;
    } else if (paramInt == 3) {
      paramArrayOfFloat[0] = this.m03;
      paramArrayOfFloat[1] = this.m13;
      paramArrayOfFloat[2] = this.m23;
      paramArrayOfFloat[3] = this.m33;
    }
  }

  public final void add(float paramFloat)
  {
    this.m00 += paramFloat; this.m01 += paramFloat; this.m02 += paramFloat; this.m03 += paramFloat;
    this.m10 += paramFloat; this.m11 += paramFloat; this.m12 += paramFloat; this.m13 += paramFloat;
    this.m20 += paramFloat; this.m21 += paramFloat; this.m22 += paramFloat; this.m23 += paramFloat;
    this.m30 += paramFloat; this.m31 += paramFloat; this.m32 += paramFloat; this.m33 += paramFloat;
  }

  public final void add(float paramFloat, Matrix4f paramMatrix4f)
  {
    set(paramMatrix4f);
    add(paramFloat);
  }

  public final void add(Matrix4f paramMatrix4f1, Matrix4f paramMatrix4f2)
  {
    set(paramMatrix4f1);
    add(paramMatrix4f2);
  }

  public final void add(Matrix4f paramMatrix4f)
  {
    this.m00 += paramMatrix4f.m00; this.m01 += paramMatrix4f.m01; this.m02 += paramMatrix4f.m02; this.m03 += paramMatrix4f.m03;
    this.m10 += paramMatrix4f.m10; this.m11 += paramMatrix4f.m11; this.m12 += paramMatrix4f.m12; this.m13 += paramMatrix4f.m13;
    this.m20 += paramMatrix4f.m20; this.m21 += paramMatrix4f.m21; this.m22 += paramMatrix4f.m22; this.m23 += paramMatrix4f.m23;
    this.m30 += paramMatrix4f.m30; this.m31 += paramMatrix4f.m31; this.m32 += paramMatrix4f.m32; this.m33 += paramMatrix4f.m33;
  }

  public final void sub(Matrix4f paramMatrix4f1, Matrix4f paramMatrix4f2)
  {
    set(paramMatrix4f1.m00 - paramMatrix4f2.m00, paramMatrix4f1.m01 - paramMatrix4f2.m01, paramMatrix4f1.m02 - paramMatrix4f2.m02, paramMatrix4f1.m03 - paramMatrix4f2.m03, paramMatrix4f1.m10 - paramMatrix4f2.m10, paramMatrix4f1.m11 - paramMatrix4f2.m11, paramMatrix4f1.m12 - paramMatrix4f2.m12, paramMatrix4f1.m13 - paramMatrix4f2.m13, paramMatrix4f1.m20 - paramMatrix4f2.m20, paramMatrix4f1.m21 - paramMatrix4f2.m21, paramMatrix4f1.m22 - paramMatrix4f2.m22, paramMatrix4f1.m23 - paramMatrix4f2.m23, paramMatrix4f1.m30 - paramMatrix4f2.m30, paramMatrix4f1.m31 - paramMatrix4f2.m31, paramMatrix4f1.m32 - paramMatrix4f2.m32, paramMatrix4f1.m33 - paramMatrix4f2.m33);
  }

  public final void sub(Matrix4f paramMatrix4f)
  {
    this.m00 -= paramMatrix4f.m00; this.m01 -= paramMatrix4f.m01; this.m02 -= paramMatrix4f.m02; this.m03 -= paramMatrix4f.m03;
    this.m10 -= paramMatrix4f.m10; this.m11 -= paramMatrix4f.m11; this.m12 -= paramMatrix4f.m12; this.m13 -= paramMatrix4f.m13;
    this.m20 -= paramMatrix4f.m20; this.m21 -= paramMatrix4f.m21; this.m22 -= paramMatrix4f.m22; this.m23 -= paramMatrix4f.m23;
    this.m30 -= paramMatrix4f.m30; this.m31 -= paramMatrix4f.m31; this.m32 -= paramMatrix4f.m32; this.m33 -= paramMatrix4f.m33;
  }

  public final void transpose()
  {
    float f = this.m01;
    this.m01 = this.m10;
    this.m10 = f;

    f = this.m02;
    this.m02 = this.m20;
    this.m20 = f;

    f = this.m03;
    this.m03 = this.m30;
    this.m30 = f;

    f = this.m12;
    this.m12 = this.m21;
    this.m21 = f;

    f = this.m13;
    this.m13 = this.m31;
    this.m31 = f;

    f = this.m23;
    this.m23 = this.m32;
    this.m32 = f;
  }

  public final void transpose(Matrix4f paramMatrix4f)
  {
    set(paramMatrix4f);
    transpose();
  }

  public final void set(Quat4f paramQuat4f)
  {
    setFromQuat(paramQuat4f.x, paramQuat4f.y, paramQuat4f.z, paramQuat4f.w);
  }

  public final void set(AxisAngle4f paramAxisAngle4f)
  {
    setFromAxisAngle(paramAxisAngle4f.x, paramAxisAngle4f.y, paramAxisAngle4f.z, paramAxisAngle4f.angle);
  }

  public final void set(Quat4d paramQuat4d)
  {
    setFromQuat(paramQuat4d.x, paramQuat4d.y, paramQuat4d.z, paramQuat4d.w);
  }

  public final void set(AxisAngle4d paramAxisAngle4d)
  {
    setFromAxisAngle(paramAxisAngle4d.x, paramAxisAngle4d.y, paramAxisAngle4d.z, paramAxisAngle4d.angle);
  }

  public final void set(Quat4d paramQuat4d, Tuple3d paramTuple3d, double paramDouble)
  {
    set(paramQuat4d);
    mulRotationScale((float)paramDouble);
    this.m03 = (float)paramTuple3d.x;
    this.m13 = (float)paramTuple3d.y;
    this.m23 = (float)paramTuple3d.z;
  }

  public final void set(Quat4f paramQuat4f, Tuple3f paramTuple3f, float paramFloat)
  {
    set(paramQuat4f);
    mulRotationScale(paramFloat);
    this.m03 = paramTuple3f.x;
    this.m13 = paramTuple3f.y;
    this.m23 = paramTuple3f.z;
  }

  public final void set(Matrix4d paramMatrix4d)
  {
    this.m00 = (float)paramMatrix4d.m00; this.m01 = (float)paramMatrix4d.m01; this.m02 = (float)paramMatrix4d.m02; this.m03 = (float)paramMatrix4d.m03;
    this.m10 = (float)paramMatrix4d.m10; this.m11 = (float)paramMatrix4d.m11; this.m12 = (float)paramMatrix4d.m12; this.m13 = (float)paramMatrix4d.m13;
    this.m20 = (float)paramMatrix4d.m20; this.m21 = (float)paramMatrix4d.m21; this.m22 = (float)paramMatrix4d.m22; this.m23 = (float)paramMatrix4d.m23;
    this.m30 = (float)paramMatrix4d.m30; this.m31 = (float)paramMatrix4d.m31; this.m32 = (float)paramMatrix4d.m32; this.m33 = (float)paramMatrix4d.m33;
  }

  public final void set(Matrix4f paramMatrix4f)
  {
    this.m00 = paramMatrix4f.m00; this.m01 = paramMatrix4f.m01; this.m02 = paramMatrix4f.m02; this.m03 = paramMatrix4f.m03;
    this.m10 = paramMatrix4f.m10; this.m11 = paramMatrix4f.m11; this.m12 = paramMatrix4f.m12; this.m13 = paramMatrix4f.m13;
    this.m20 = paramMatrix4f.m20; this.m21 = paramMatrix4f.m21; this.m22 = paramMatrix4f.m22; this.m23 = paramMatrix4f.m23;
    this.m30 = paramMatrix4f.m30; this.m31 = paramMatrix4f.m31; this.m32 = paramMatrix4f.m32; this.m33 = paramMatrix4f.m33;
  }

  public final void invert(Matrix4f paramMatrix4f)
  {
    set(paramMatrix4f);
    invert();
  }

  public final void invert()
  {
    float f = determinant();
    if (f == 0.0D)
      return;
    f = 1.0F / f;

    set(this.m11 * (this.m22 * this.m33 - this.m23 * this.m32) + this.m12 * (this.m23 * this.m31 - this.m21 * this.m33) + this.m13 * (this.m21 * this.m32 - this.m22 * this.m31), this.m21 * (this.m02 * this.m33 - this.m03 * this.m32) + this.m22 * (this.m03 * this.m31 - this.m01 * this.m33) + this.m23 * (this.m01 * this.m32 - this.m02 * this.m31), this.m31 * (this.m02 * this.m13 - this.m03 * this.m12) + this.m32 * (this.m03 * this.m11 - this.m01 * this.m13) + this.m33 * (this.m01 * this.m12 - this.m02 * this.m11), this.m01 * (this.m13 * this.m22 - this.m12 * this.m23) + this.m02 * (this.m11 * this.m23 - this.m13 * this.m21) + this.m03 * (this.m12 * this.m21 - this.m11 * this.m22), this.m12 * (this.m20 * this.m33 - this.m23 * this.m30) + this.m13 * (this.m22 * this.m30 - this.m20 * this.m32) + this.m10 * (this.m23 * this.m32 - this.m22 * this.m33), this.m22 * (this.m00 * this.m33 - this.m03 * this.m30) + this.m23 * (this.m02 * this.m30 - this.m00 * this.m32) + this.m20 * (this.m03 * this.m32 - this.m02 * this.m33), this.m32 * (this.m00 * this.m13 - this.m03 * this.m10) + this.m33 * (this.m02 * this.m10 - this.m00 * this.m12) + this.m30 * (this.m03 * this.m12 - this.m02 * this.m13), this.m02 * (this.m13 * this.m20 - this.m10 * this.m23) + this.m03 * (this.m10 * this.m22 - this.m12 * this.m20) + this.m00 * (this.m12 * this.m23 - this.m13 * this.m22), this.m13 * (this.m20 * this.m31 - this.m21 * this.m30) + this.m10 * (this.m21 * this.m33 - this.m23 * this.m31) + this.m11 * (this.m23 * this.m30 - this.m20 * this.m33), this.m23 * (this.m00 * this.m31 - this.m01 * this.m30) + this.m20 * (this.m01 * this.m33 - this.m03 * this.m31) + this.m21 * (this.m03 * this.m30 - this.m00 * this.m33), this.m33 * (this.m00 * this.m11 - this.m01 * this.m10) + this.m30 * (this.m01 * this.m13 - this.m03 * this.m11) + this.m31 * (this.m03 * this.m10 - this.m00 * this.m13), this.m03 * (this.m11 * this.m20 - this.m10 * this.m21) + this.m00 * (this.m13 * this.m21 - this.m11 * this.m23) + this.m01 * (this.m10 * this.m23 - this.m13 * this.m20), this.m10 * (this.m22 * this.m31 - this.m21 * this.m32) + this.m11 * (this.m20 * this.m32 - this.m22 * this.m30) + this.m12 * (this.m21 * this.m30 - this.m20 * this.m31), this.m20 * (this.m02 * this.m31 - this.m01 * this.m32) + this.m21 * (this.m00 * this.m32 - this.m02 * this.m30) + this.m22 * (this.m01 * this.m30 - this.m00 * this.m31), this.m30 * (this.m02 * this.m11 - this.m01 * this.m12) + this.m31 * (this.m00 * this.m12 - this.m02 * this.m10) + this.m32 * (this.m01 * this.m10 - this.m00 * this.m11), this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20));

    mul(f);
  }

  public final float determinant()
  {
    return (this.m00 * this.m11 - this.m01 * this.m10) * (this.m22 * this.m33 - this.m23 * this.m32) - (this.m00 * this.m12 - this.m02 * this.m10) * (this.m21 * this.m33 - this.m23 * this.m31) + (this.m00 * this.m13 - this.m03 * this.m10) * (this.m21 * this.m32 - this.m22 * this.m31) + (this.m01 * this.m12 - this.m02 * this.m11) * (this.m20 * this.m33 - this.m23 * this.m30) - (this.m01 * this.m13 - this.m03 * this.m11) * (this.m20 * this.m32 - this.m22 * this.m30) + (this.m02 * this.m13 - this.m03 * this.m12) * (this.m20 * this.m31 - this.m21 * this.m30);
  }

  public final void set(Matrix3f paramMatrix3f)
  {
    this.m00 = paramMatrix3f.m00; this.m01 = paramMatrix3f.m01; this.m02 = paramMatrix3f.m02; this.m03 = 0.0F;
    this.m10 = paramMatrix3f.m10; this.m11 = paramMatrix3f.m11; this.m12 = paramMatrix3f.m12; this.m13 = 0.0F;
    this.m20 = paramMatrix3f.m20; this.m21 = paramMatrix3f.m21; this.m22 = paramMatrix3f.m22; this.m23 = 0.0F;
    this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
  }

  public final void set(Matrix3d paramMatrix3d)
  {
    this.m00 = (float)paramMatrix3d.m00; this.m01 = (float)paramMatrix3d.m01; this.m02 = (float)paramMatrix3d.m02; this.m03 = 0.0F;
    this.m10 = (float)paramMatrix3d.m10; this.m11 = (float)paramMatrix3d.m11; this.m12 = (float)paramMatrix3d.m12; this.m13 = 0.0F;
    this.m20 = (float)paramMatrix3d.m20; this.m21 = (float)paramMatrix3d.m21; this.m22 = (float)paramMatrix3d.m22; this.m23 = 0.0F;
    this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
  }

  public final void set(float paramFloat)
  {
    this.m00 = paramFloat; this.m01 = 0.0F; this.m02 = 0.0F; this.m03 = 0.0F;
    this.m10 = 0.0F; this.m11 = paramFloat; this.m12 = 0.0F; this.m13 = 0.0F;
    this.m20 = 0.0F; this.m21 = 0.0F; this.m22 = paramFloat; this.m23 = 0.0F;
    this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
  }

  public final void set(float[] paramArrayOfFloat)
  {
    this.m00 = paramArrayOfFloat[0]; this.m01 = paramArrayOfFloat[1]; this.m02 = paramArrayOfFloat[2]; this.m03 = paramArrayOfFloat[3];
    this.m10 = paramArrayOfFloat[4]; this.m11 = paramArrayOfFloat[5]; this.m12 = paramArrayOfFloat[6]; this.m13 = paramArrayOfFloat[7];
    this.m20 = paramArrayOfFloat[8]; this.m21 = paramArrayOfFloat[9]; this.m22 = paramArrayOfFloat[10]; this.m23 = paramArrayOfFloat[11];
    this.m30 = paramArrayOfFloat[12]; this.m31 = paramArrayOfFloat[13]; this.m32 = paramArrayOfFloat[14]; this.m33 = paramArrayOfFloat[15];
  }

  public final void set(Tuple3f paramTuple3f)
  {
    setIdentity();
    setTranslation(paramTuple3f);
  }

  public final void set(float paramFloat, Tuple3f paramTuple3f)
  {
    set(paramFloat);
    setTranslation(paramTuple3f);
  }

  public final void set(Tuple3f paramTuple3f, float paramFloat)
  {
    this.m00 = paramFloat; this.m01 = 0.0F; this.m02 = 0.0F; this.m03 = (paramFloat * paramTuple3f.x);
    this.m10 = 0.0F; this.m11 = paramFloat; this.m12 = 0.0F; this.m13 = (paramFloat * paramTuple3f.y);
    this.m20 = 0.0F; this.m21 = 0.0F; this.m22 = paramFloat; this.m23 = (paramFloat * paramTuple3f.z);
    this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
  }

  public final void set(Matrix3f paramMatrix3f, Tuple3f paramTuple3f, float paramFloat)
  {
    setRotationScale(paramMatrix3f);
    mulRotationScale(paramFloat);
    setTranslation(paramTuple3f);
    this.m33 = 1.0F;
  }

  public final void set(Matrix3d paramMatrix3d, Tuple3d paramTuple3d, double paramDouble)
  {
    setRotationScale(paramMatrix3d);
    mulRotationScale((float)paramDouble);
    setTranslation(paramTuple3d);
    this.m33 = 1.0F;
  }

  public void setTranslation(Tuple3f paramTuple3f)
  {
    this.m03 = paramTuple3f.x;
    this.m13 = paramTuple3f.y;
    this.m23 = paramTuple3f.z;
  }

  public final void rotX(float paramFloat)
  {
    float f1 = (float)Math.cos(paramFloat);
    float f2 = (float)Math.sin(paramFloat);
    this.m00 = 1.0F; this.m01 = 0.0F; this.m02 = 0.0F; this.m03 = 0.0F;
    this.m10 = 0.0F; this.m11 = f1; this.m12 = (-f2); this.m13 = 0.0F;
    this.m20 = 0.0F; this.m21 = f2; this.m22 = f1; this.m23 = 0.0F;
    this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
  }

  public final void rotY(float paramFloat)
  {
    float f1 = (float)Math.cos(paramFloat);
    float f2 = (float)Math.sin(paramFloat);
    this.m00 = f1; this.m01 = 0.0F; this.m02 = f2; this.m03 = 0.0F;
    this.m10 = 0.0F; this.m11 = 1.0F; this.m12 = 0.0F; this.m13 = 0.0F;
    this.m20 = (-f2); this.m21 = 0.0F; this.m22 = f1; this.m23 = 0.0F;
    this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
  }

  public final void rotZ(float paramFloat)
  {
    float f1 = (float)Math.cos(paramFloat);
    float f2 = (float)Math.sin(paramFloat);
    this.m00 = f1; this.m01 = (-f2); this.m02 = 0.0F; this.m03 = 0.0F;
    this.m10 = f2; this.m11 = f1; this.m12 = 0.0F; this.m13 = 0.0F;
    this.m20 = 0.0F; this.m21 = 0.0F; this.m22 = 1.0F; this.m23 = 0.0F;
    this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
  }

  public final void mul(float paramFloat)
  {
    this.m00 *= paramFloat; this.m01 *= paramFloat; this.m02 *= paramFloat; this.m03 *= paramFloat;
    this.m10 *= paramFloat; this.m11 *= paramFloat; this.m12 *= paramFloat; this.m13 *= paramFloat;
    this.m20 *= paramFloat; this.m21 *= paramFloat; this.m22 *= paramFloat; this.m23 *= paramFloat;
    this.m30 *= paramFloat; this.m31 *= paramFloat; this.m32 *= paramFloat; this.m33 *= paramFloat;
  }

  public final void mul(float paramFloat, Matrix4f paramMatrix4f)
  {
    set(paramMatrix4f);
    mul(paramFloat);
  }

  public final void mul(Matrix4f paramMatrix4f)
  {
    mul(this, paramMatrix4f);
  }

  public final void mul(Matrix4f paramMatrix4f1, Matrix4f paramMatrix4f2)
  {
    set(paramMatrix4f1.m00 * paramMatrix4f2.m00 + paramMatrix4f1.m01 * paramMatrix4f2.m10 + paramMatrix4f1.m02 * paramMatrix4f2.m20 + paramMatrix4f1.m03 * paramMatrix4f2.m30, paramMatrix4f1.m00 * paramMatrix4f2.m01 + paramMatrix4f1.m01 * paramMatrix4f2.m11 + paramMatrix4f1.m02 * paramMatrix4f2.m21 + paramMatrix4f1.m03 * paramMatrix4f2.m31, paramMatrix4f1.m00 * paramMatrix4f2.m02 + paramMatrix4f1.m01 * paramMatrix4f2.m12 + paramMatrix4f1.m02 * paramMatrix4f2.m22 + paramMatrix4f1.m03 * paramMatrix4f2.m32, paramMatrix4f1.m00 * paramMatrix4f2.m03 + paramMatrix4f1.m01 * paramMatrix4f2.m13 + paramMatrix4f1.m02 * paramMatrix4f2.m23 + paramMatrix4f1.m03 * paramMatrix4f2.m33, paramMatrix4f1.m10 * paramMatrix4f2.m00 + paramMatrix4f1.m11 * paramMatrix4f2.m10 + paramMatrix4f1.m12 * paramMatrix4f2.m20 + paramMatrix4f1.m13 * paramMatrix4f2.m30, paramMatrix4f1.m10 * paramMatrix4f2.m01 + paramMatrix4f1.m11 * paramMatrix4f2.m11 + paramMatrix4f1.m12 * paramMatrix4f2.m21 + paramMatrix4f1.m13 * paramMatrix4f2.m31, paramMatrix4f1.m10 * paramMatrix4f2.m02 + paramMatrix4f1.m11 * paramMatrix4f2.m12 + paramMatrix4f1.m12 * paramMatrix4f2.m22 + paramMatrix4f1.m13 * paramMatrix4f2.m32, paramMatrix4f1.m10 * paramMatrix4f2.m03 + paramMatrix4f1.m11 * paramMatrix4f2.m13 + paramMatrix4f1.m12 * paramMatrix4f2.m23 + paramMatrix4f1.m13 * paramMatrix4f2.m33, paramMatrix4f1.m20 * paramMatrix4f2.m00 + paramMatrix4f1.m21 * paramMatrix4f2.m10 + paramMatrix4f1.m22 * paramMatrix4f2.m20 + paramMatrix4f1.m23 * paramMatrix4f2.m30, paramMatrix4f1.m20 * paramMatrix4f2.m01 + paramMatrix4f1.m21 * paramMatrix4f2.m11 + paramMatrix4f1.m22 * paramMatrix4f2.m21 + paramMatrix4f1.m23 * paramMatrix4f2.m31, paramMatrix4f1.m20 * paramMatrix4f2.m02 + paramMatrix4f1.m21 * paramMatrix4f2.m12 + paramMatrix4f1.m22 * paramMatrix4f2.m22 + paramMatrix4f1.m23 * paramMatrix4f2.m32, paramMatrix4f1.m20 * paramMatrix4f2.m03 + paramMatrix4f1.m21 * paramMatrix4f2.m13 + paramMatrix4f1.m22 * paramMatrix4f2.m23 + paramMatrix4f1.m23 * paramMatrix4f2.m33, paramMatrix4f1.m30 * paramMatrix4f2.m00 + paramMatrix4f1.m31 * paramMatrix4f2.m10 + paramMatrix4f1.m32 * paramMatrix4f2.m20 + paramMatrix4f1.m33 * paramMatrix4f2.m30, paramMatrix4f1.m30 * paramMatrix4f2.m01 + paramMatrix4f1.m31 * paramMatrix4f2.m11 + paramMatrix4f1.m32 * paramMatrix4f2.m21 + paramMatrix4f1.m33 * paramMatrix4f2.m31, paramMatrix4f1.m30 * paramMatrix4f2.m02 + paramMatrix4f1.m31 * paramMatrix4f2.m12 + paramMatrix4f1.m32 * paramMatrix4f2.m22 + paramMatrix4f1.m33 * paramMatrix4f2.m32, paramMatrix4f1.m30 * paramMatrix4f2.m03 + paramMatrix4f1.m31 * paramMatrix4f2.m13 + paramMatrix4f1.m32 * paramMatrix4f2.m23 + paramMatrix4f1.m33 * paramMatrix4f2.m33);
  }

  public final void mulTransposeBoth(Matrix4f paramMatrix4f1, Matrix4f paramMatrix4f2)
  {
    mul(paramMatrix4f2, paramMatrix4f1);
    transpose();
  }

  public final void mulTransposeRight(Matrix4f paramMatrix4f1, Matrix4f paramMatrix4f2)
  {
    set(paramMatrix4f1.m00 * paramMatrix4f2.m00 + paramMatrix4f1.m01 * paramMatrix4f2.m01 + paramMatrix4f1.m02 * paramMatrix4f2.m02 + paramMatrix4f1.m03 * paramMatrix4f2.m03, paramMatrix4f1.m00 * paramMatrix4f2.m10 + paramMatrix4f1.m01 * paramMatrix4f2.m11 + paramMatrix4f1.m02 * paramMatrix4f2.m12 + paramMatrix4f1.m03 * paramMatrix4f2.m13, paramMatrix4f1.m00 * paramMatrix4f2.m20 + paramMatrix4f1.m01 * paramMatrix4f2.m21 + paramMatrix4f1.m02 * paramMatrix4f2.m22 + paramMatrix4f1.m03 * paramMatrix4f2.m23, paramMatrix4f1.m00 * paramMatrix4f2.m30 + paramMatrix4f1.m01 * paramMatrix4f2.m31 + paramMatrix4f1.m02 * paramMatrix4f2.m32 + paramMatrix4f1.m03 * paramMatrix4f2.m33, paramMatrix4f1.m10 * paramMatrix4f2.m00 + paramMatrix4f1.m11 * paramMatrix4f2.m01 + paramMatrix4f1.m12 * paramMatrix4f2.m02 + paramMatrix4f1.m13 * paramMatrix4f2.m03, paramMatrix4f1.m10 * paramMatrix4f2.m10 + paramMatrix4f1.m11 * paramMatrix4f2.m11 + paramMatrix4f1.m12 * paramMatrix4f2.m12 + paramMatrix4f1.m13 * paramMatrix4f2.m13, paramMatrix4f1.m10 * paramMatrix4f2.m20 + paramMatrix4f1.m11 * paramMatrix4f2.m21 + paramMatrix4f1.m12 * paramMatrix4f2.m22 + paramMatrix4f1.m13 * paramMatrix4f2.m23, paramMatrix4f1.m10 * paramMatrix4f2.m30 + paramMatrix4f1.m11 * paramMatrix4f2.m31 + paramMatrix4f1.m12 * paramMatrix4f2.m32 + paramMatrix4f1.m13 * paramMatrix4f2.m33, paramMatrix4f1.m20 * paramMatrix4f2.m00 + paramMatrix4f1.m21 * paramMatrix4f2.m01 + paramMatrix4f1.m22 * paramMatrix4f2.m02 + paramMatrix4f1.m23 * paramMatrix4f2.m03, paramMatrix4f1.m20 * paramMatrix4f2.m10 + paramMatrix4f1.m21 * paramMatrix4f2.m11 + paramMatrix4f1.m22 * paramMatrix4f2.m12 + paramMatrix4f1.m23 * paramMatrix4f2.m13, paramMatrix4f1.m20 * paramMatrix4f2.m20 + paramMatrix4f1.m21 * paramMatrix4f2.m21 + paramMatrix4f1.m22 * paramMatrix4f2.m22 + paramMatrix4f1.m23 * paramMatrix4f2.m23, paramMatrix4f1.m20 * paramMatrix4f2.m30 + paramMatrix4f1.m21 * paramMatrix4f2.m31 + paramMatrix4f1.m22 * paramMatrix4f2.m32 + paramMatrix4f1.m23 * paramMatrix4f2.m33, paramMatrix4f1.m30 * paramMatrix4f2.m00 + paramMatrix4f1.m31 * paramMatrix4f2.m01 + paramMatrix4f1.m32 * paramMatrix4f2.m02 + paramMatrix4f1.m33 * paramMatrix4f2.m03, paramMatrix4f1.m30 * paramMatrix4f2.m10 + paramMatrix4f1.m31 * paramMatrix4f2.m11 + paramMatrix4f1.m32 * paramMatrix4f2.m12 + paramMatrix4f1.m33 * paramMatrix4f2.m13, paramMatrix4f1.m30 * paramMatrix4f2.m20 + paramMatrix4f1.m31 * paramMatrix4f2.m21 + paramMatrix4f1.m32 * paramMatrix4f2.m22 + paramMatrix4f1.m33 * paramMatrix4f2.m23, paramMatrix4f1.m30 * paramMatrix4f2.m30 + paramMatrix4f1.m31 * paramMatrix4f2.m31 + paramMatrix4f1.m32 * paramMatrix4f2.m32 + paramMatrix4f1.m33 * paramMatrix4f2.m33);
  }

  public final void mulTransposeLeft(Matrix4f paramMatrix4f1, Matrix4f paramMatrix4f2)
  {
    set(paramMatrix4f1.m00 * paramMatrix4f2.m00 + paramMatrix4f1.m10 * paramMatrix4f2.m10 + paramMatrix4f1.m20 * paramMatrix4f2.m20 + paramMatrix4f1.m30 * paramMatrix4f2.m30, paramMatrix4f1.m00 * paramMatrix4f2.m01 + paramMatrix4f1.m10 * paramMatrix4f2.m11 + paramMatrix4f1.m20 * paramMatrix4f2.m21 + paramMatrix4f1.m30 * paramMatrix4f2.m31, paramMatrix4f1.m00 * paramMatrix4f2.m02 + paramMatrix4f1.m10 * paramMatrix4f2.m12 + paramMatrix4f1.m20 * paramMatrix4f2.m22 + paramMatrix4f1.m30 * paramMatrix4f2.m32, paramMatrix4f1.m00 * paramMatrix4f2.m03 + paramMatrix4f1.m10 * paramMatrix4f2.m13 + paramMatrix4f1.m20 * paramMatrix4f2.m23 + paramMatrix4f1.m30 * paramMatrix4f2.m33, paramMatrix4f1.m01 * paramMatrix4f2.m00 + paramMatrix4f1.m11 * paramMatrix4f2.m10 + paramMatrix4f1.m21 * paramMatrix4f2.m20 + paramMatrix4f1.m31 * paramMatrix4f2.m30, paramMatrix4f1.m01 * paramMatrix4f2.m01 + paramMatrix4f1.m11 * paramMatrix4f2.m11 + paramMatrix4f1.m21 * paramMatrix4f2.m21 + paramMatrix4f1.m31 * paramMatrix4f2.m31, paramMatrix4f1.m01 * paramMatrix4f2.m02 + paramMatrix4f1.m11 * paramMatrix4f2.m12 + paramMatrix4f1.m21 * paramMatrix4f2.m22 + paramMatrix4f1.m31 * paramMatrix4f2.m32, paramMatrix4f1.m01 * paramMatrix4f2.m03 + paramMatrix4f1.m11 * paramMatrix4f2.m13 + paramMatrix4f1.m21 * paramMatrix4f2.m23 + paramMatrix4f1.m31 * paramMatrix4f2.m33, paramMatrix4f1.m02 * paramMatrix4f2.m00 + paramMatrix4f1.m12 * paramMatrix4f2.m10 + paramMatrix4f1.m22 * paramMatrix4f2.m20 + paramMatrix4f1.m32 * paramMatrix4f2.m30, paramMatrix4f1.m02 * paramMatrix4f2.m01 + paramMatrix4f1.m12 * paramMatrix4f2.m11 + paramMatrix4f1.m22 * paramMatrix4f2.m21 + paramMatrix4f1.m32 * paramMatrix4f2.m31, paramMatrix4f1.m02 * paramMatrix4f2.m02 + paramMatrix4f1.m12 * paramMatrix4f2.m12 + paramMatrix4f1.m22 * paramMatrix4f2.m22 + paramMatrix4f1.m32 * paramMatrix4f2.m32, paramMatrix4f1.m02 * paramMatrix4f2.m03 + paramMatrix4f1.m12 * paramMatrix4f2.m13 + paramMatrix4f1.m22 * paramMatrix4f2.m23 + paramMatrix4f1.m32 * paramMatrix4f2.m33, paramMatrix4f1.m03 * paramMatrix4f2.m00 + paramMatrix4f1.m13 * paramMatrix4f2.m10 + paramMatrix4f1.m23 * paramMatrix4f2.m20 + paramMatrix4f1.m33 * paramMatrix4f2.m30, paramMatrix4f1.m03 * paramMatrix4f2.m01 + paramMatrix4f1.m13 * paramMatrix4f2.m11 + paramMatrix4f1.m23 * paramMatrix4f2.m21 + paramMatrix4f1.m33 * paramMatrix4f2.m31, paramMatrix4f1.m03 * paramMatrix4f2.m02 + paramMatrix4f1.m13 * paramMatrix4f2.m12 + paramMatrix4f1.m23 * paramMatrix4f2.m22 + paramMatrix4f1.m33 * paramMatrix4f2.m32, paramMatrix4f1.m03 * paramMatrix4f2.m03 + paramMatrix4f1.m13 * paramMatrix4f2.m13 + paramMatrix4f1.m23 * paramMatrix4f2.m23 + paramMatrix4f1.m33 * paramMatrix4f2.m33);
  }

  public boolean equals(Matrix4f paramMatrix4f)
  {
    return (paramMatrix4f != null) && (this.m00 == paramMatrix4f.m00) && (this.m01 == paramMatrix4f.m01) && (this.m02 == paramMatrix4f.m02) && (this.m03 == paramMatrix4f.m03) && (this.m10 == paramMatrix4f.m10) && (this.m11 == paramMatrix4f.m11) && (this.m12 == paramMatrix4f.m12) && (this.m13 == paramMatrix4f.m13) && (this.m20 == paramMatrix4f.m20) && (this.m21 == paramMatrix4f.m21) && (this.m22 == paramMatrix4f.m22) && (this.m23 == paramMatrix4f.m23) && (this.m30 == paramMatrix4f.m30) && (this.m31 == paramMatrix4f.m31) && (this.m32 == paramMatrix4f.m32) && (this.m33 == paramMatrix4f.m33);
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof Matrix4f)) && (equals((Matrix4f)paramObject));
  }

  public boolean epsilonEquals(Matrix4f paramMatrix4f, float paramFloat)
  {
    return (Math.abs(this.m00 - paramMatrix4f.m00) <= paramFloat) && (Math.abs(this.m01 - paramMatrix4f.m01) <= paramFloat) && (Math.abs(this.m02 - paramMatrix4f.m02) <= paramFloat) && (Math.abs(this.m03 - paramMatrix4f.m03) <= paramFloat) && (Math.abs(this.m10 - paramMatrix4f.m10) <= paramFloat) && (Math.abs(this.m11 - paramMatrix4f.m11) <= paramFloat) && (Math.abs(this.m12 - paramMatrix4f.m12) <= paramFloat) && (Math.abs(this.m13 - paramMatrix4f.m13) <= paramFloat) && (Math.abs(this.m20 - paramMatrix4f.m20) <= paramFloat) && (Math.abs(this.m21 - paramMatrix4f.m21) <= paramFloat) && (Math.abs(this.m22 - paramMatrix4f.m22) <= paramFloat) && (Math.abs(this.m23 - paramMatrix4f.m23) <= paramFloat) && (Math.abs(this.m30 - paramMatrix4f.m30) <= paramFloat) && (Math.abs(this.m31 - paramMatrix4f.m31) <= paramFloat) && (Math.abs(this.m32 - paramMatrix4f.m32) <= paramFloat) && (Math.abs(this.m33 - paramMatrix4f.m33) <= paramFloat);
  }

  public int hashCode()
  {
    return Float.floatToIntBits(this.m00) ^ Float.floatToIntBits(this.m01) ^ Float.floatToIntBits(this.m02) ^ Float.floatToIntBits(this.m03) ^ Float.floatToIntBits(this.m10) ^ Float.floatToIntBits(this.m11) ^ Float.floatToIntBits(this.m12) ^ Float.floatToIntBits(this.m13) ^ Float.floatToIntBits(this.m20) ^ Float.floatToIntBits(this.m21) ^ Float.floatToIntBits(this.m22) ^ Float.floatToIntBits(this.m23) ^ Float.floatToIntBits(this.m30) ^ Float.floatToIntBits(this.m31) ^ Float.floatToIntBits(this.m32) ^ Float.floatToIntBits(this.m33);
  }

  public final void transform(Tuple4f paramTuple4f1, Tuple4f paramTuple4f2)
  {
    paramTuple4f2.set(this.m00 * paramTuple4f1.x + this.m01 * paramTuple4f1.y + this.m02 * paramTuple4f1.z + this.m03 * paramTuple4f1.w, this.m10 * paramTuple4f1.x + this.m11 * paramTuple4f1.y + this.m12 * paramTuple4f1.z + this.m13 * paramTuple4f1.w, this.m20 * paramTuple4f1.x + this.m21 * paramTuple4f1.y + this.m22 * paramTuple4f1.z + this.m23 * paramTuple4f1.w, this.m30 * paramTuple4f1.x + this.m31 * paramTuple4f1.y + this.m32 * paramTuple4f1.z + this.m33 * paramTuple4f1.w);
  }

  public final void transform(Tuple4f paramTuple4f)
  {
    transform(paramTuple4f, paramTuple4f);
  }

  public final void transform(Point3f paramPoint3f1, Point3f paramPoint3f2)
  {
    paramPoint3f2.set(this.m00 * paramPoint3f1.x + this.m01 * paramPoint3f1.y + this.m02 * paramPoint3f1.z + this.m03, this.m10 * paramPoint3f1.x + this.m11 * paramPoint3f1.y + this.m12 * paramPoint3f1.z + this.m13, this.m20 * paramPoint3f1.x + this.m21 * paramPoint3f1.y + this.m22 * paramPoint3f1.z + this.m23);
  }

  public final void transform(Point3f paramPoint3f)
  {
    transform(paramPoint3f, paramPoint3f);
  }

  public final void transform(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    paramTuple3f2.set(this.m00 * paramTuple3f1.x + this.m01 * paramTuple3f1.y + this.m02 * paramTuple3f1.z, this.m10 * paramTuple3f1.x + this.m11 * paramTuple3f1.y + this.m12 * paramTuple3f1.z, this.m20 * paramTuple3f1.x + this.m21 * paramTuple3f1.y + this.m22 * paramTuple3f1.z);
  }

  public final void transform(Tuple3f paramTuple3f)
  {
    transform(paramTuple3f, paramTuple3f);
  }

  public final void setRotation(Matrix3d paramMatrix3d)
  {
    float f = SVD(null);
    setRotationScale(paramMatrix3d);
    mulRotationScale(f);
  }

  public final void setRotation(Matrix3f paramMatrix3f)
  {
    float f = SVD(null);
    setRotationScale(paramMatrix3f);
    mulRotationScale(f);
  }

  public final void setRotation(Quat4f paramQuat4f)
  {
    float f1 = SVD(null, null);

    float f2 = this.m03;
    float f3 = this.m13;
    float f4 = this.m23;
    float f5 = this.m30;
    float f6 = this.m31;
    float f7 = this.m32;
    float f8 = this.m33;

    set(paramQuat4f);
    mulRotationScale(f1);

    this.m03 = f2;
    this.m13 = f3;
    this.m23 = f4;
    this.m30 = f5;
    this.m31 = f6;
    this.m32 = f7;
    this.m33 = f8;
  }

  public final void setRotation(Quat4d paramQuat4d)
  {
    float f1 = SVD(null, null);

    float f2 = this.m03;
    float f3 = this.m13;
    float f4 = this.m23;
    float f5 = this.m30;
    float f6 = this.m31;
    float f7 = this.m32;
    float f8 = this.m33;

    set(paramQuat4d);
    mulRotationScale(f1);

    this.m03 = f2;
    this.m13 = f3;
    this.m23 = f4;
    this.m30 = f5;
    this.m31 = f6;
    this.m32 = f7;
    this.m33 = f8;
  }

  public final void setRotation(AxisAngle4f paramAxisAngle4f)
  {
    float f1 = SVD(null, null);

    float f2 = this.m03;
    float f3 = this.m13;
    float f4 = this.m23;
    float f5 = this.m30;
    float f6 = this.m31;
    float f7 = this.m32;
    float f8 = this.m33;

    set(paramAxisAngle4f);
    mulRotationScale(f1);

    this.m03 = f2;
    this.m13 = f3;
    this.m23 = f4;
    this.m30 = f5;
    this.m31 = f6;
    this.m32 = f7;
    this.m33 = f8;
  }

  public final void setZero()
  {
    this.m00 = 0.0F; this.m01 = 0.0F; this.m02 = 0.0F; this.m03 = 0.0F;
    this.m10 = 0.0F; this.m11 = 0.0F; this.m12 = 0.0F; this.m13 = 0.0F;
    this.m20 = 0.0F; this.m21 = 0.0F; this.m22 = 0.0F; this.m23 = 0.0F;
    this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 0.0F;
  }

  public final void negate()
  {
    this.m00 = (-this.m00); this.m01 = (-this.m01); this.m02 = (-this.m02); this.m03 = (-this.m03);
    this.m10 = (-this.m10); this.m11 = (-this.m11); this.m12 = (-this.m12); this.m13 = (-this.m13);
    this.m20 = (-this.m20); this.m21 = (-this.m21); this.m22 = (-this.m22); this.m23 = (-this.m23);
    this.m30 = (-this.m30); this.m31 = (-this.m31); this.m32 = (-this.m32); this.m33 = (-this.m33);
  }

  public final void negate(Matrix4f paramMatrix4f)
  {
    set(paramMatrix4f);
    negate();
  }

  private void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    this.m00 = paramFloat1; this.m01 = paramFloat2; this.m02 = paramFloat3; this.m03 = paramFloat4;
    this.m10 = paramFloat5; this.m11 = paramFloat6; this.m12 = paramFloat7; this.m13 = paramFloat8;
    this.m20 = paramFloat9; this.m21 = paramFloat10; this.m22 = paramFloat11; this.m23 = paramFloat12;
    this.m30 = paramFloat13; this.m31 = paramFloat14; this.m32 = paramFloat15; this.m33 = paramFloat16;
  }

  private float SVD(Matrix3f paramMatrix3f, Matrix4f paramMatrix4f)
  {
    float f1 = (float)Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20 + this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21 + this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22) / 3.0D);

    float f2 = f1 == 0.0F ? 0.0F : 1.0F / f1;

    if (paramMatrix3f != null) {
      getRotationScale(paramMatrix3f);
      paramMatrix3f.mul(f2);
    }

    if (paramMatrix4f != null) {
      if (paramMatrix4f != this)
        paramMatrix4f.setRotationScale(this);
      paramMatrix4f.mulRotationScale(f2);
    }

    return f1;
  }

  private float SVD(Matrix3d paramMatrix3d)
  {
    float f1 = (float)Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20 + this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21 + this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22) / 3.0D);

    float f2 = f1 == 0.0F ? 0.0F : 1.0F / f1;

    if (paramMatrix3d != null) {
      getRotationScale(paramMatrix3d);
      paramMatrix3d.mul(f2);
    }

    return f1;
  }

  private void mulRotationScale(float paramFloat)
  {
    this.m00 *= paramFloat; this.m01 *= paramFloat; this.m02 *= paramFloat;
    this.m10 *= paramFloat; this.m11 *= paramFloat; this.m12 *= paramFloat;
    this.m20 *= paramFloat; this.m21 *= paramFloat; this.m22 *= paramFloat;
  }

  private void setRotationScale(Matrix4f paramMatrix4f)
  {
    this.m00 = paramMatrix4f.m00; this.m01 = paramMatrix4f.m01; this.m02 = paramMatrix4f.m02;
    this.m10 = paramMatrix4f.m10; this.m11 = paramMatrix4f.m11; this.m12 = paramMatrix4f.m12;
    this.m20 = paramMatrix4f.m20; this.m21 = paramMatrix4f.m21; this.m22 = paramMatrix4f.m22;
  }

  private void setRotationScale(Matrix3d paramMatrix3d)
  {
    this.m00 = (float)paramMatrix3d.m00; this.m01 = (float)paramMatrix3d.m01; this.m02 = (float)paramMatrix3d.m02;
    this.m10 = (float)paramMatrix3d.m10; this.m11 = (float)paramMatrix3d.m11; this.m12 = (float)paramMatrix3d.m12;
    this.m20 = (float)paramMatrix3d.m20; this.m21 = (float)paramMatrix3d.m21; this.m22 = (float)paramMatrix3d.m22;
  }

  private void setTranslation(Tuple3d paramTuple3d)
  {
    this.m03 = (float)paramTuple3d.x;
    this.m13 = (float)paramTuple3d.y;
    this.m23 = (float)paramTuple3d.z;
  }

  private final void getRotationScale(Matrix3d paramMatrix3d)
  {
    paramMatrix3d.m00 = this.m00; paramMatrix3d.m01 = this.m01; paramMatrix3d.m02 = this.m02;
    paramMatrix3d.m10 = this.m10; paramMatrix3d.m11 = this.m11; paramMatrix3d.m12 = this.m12;
    paramMatrix3d.m20 = this.m20; paramMatrix3d.m21 = this.m21; paramMatrix3d.m22 = this.m22;
  }

  private void setFromQuat(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    double d1 = paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2 + paramDouble3 * paramDouble3 + paramDouble4 * paramDouble4;
    double d2 = d1 > 0.0D ? 2.0D / d1 : 0.0D;

    double d3 = paramDouble1 * d2; double d4 = paramDouble2 * d2; double d5 = paramDouble3 * d2;
    double d6 = paramDouble4 * d3; double d7 = paramDouble4 * d4; double d8 = paramDouble4 * d5;
    double d9 = paramDouble1 * d3; double d10 = paramDouble1 * d4; double d11 = paramDouble1 * d5;
    double d12 = paramDouble2 * d4; double d13 = paramDouble2 * d5; double d14 = paramDouble3 * d5;

    setIdentity();
    this.m00 = (float)(1.0D - (d12 + d14)); this.m01 = (float)(d10 - d8); this.m02 = (float)(d11 + d7);
    this.m10 = (float)(d10 + d8); this.m11 = (float)(1.0D - (d9 + d14)); this.m12 = (float)(d13 - d6);
    this.m20 = (float)(d11 - d7); this.m21 = (float)(d13 + d6); this.m22 = (float)(1.0D - (d9 + d12));
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

    this.m00 = (float)(d2 + paramDouble1 * paramDouble1 * d4);
    this.m11 = (float)(d2 + paramDouble2 * paramDouble2 * d4);
    this.m22 = (float)(d2 + paramDouble3 * paramDouble3 * d4);

    double d5 = paramDouble1 * paramDouble2 * d4;
    double d6 = paramDouble3 * d3;
    this.m01 = (float)(d5 - d6);
    this.m10 = (float)(d5 + d6);

    d5 = paramDouble1 * paramDouble3 * d4;
    d6 = paramDouble2 * d3;
    this.m02 = (float)(d5 + d6);
    this.m20 = (float)(d5 - d6);

    d5 = paramDouble2 * paramDouble3 * d4;
    d6 = paramDouble1 * d3;
    this.m12 = (float)(d5 - d6);
    this.m21 = (float)(d5 + d6);
  }

  public void setEulers(float[] paramArrayOfFloat) {
    setEulers(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
  }

  public void getEulers(float[] paramArrayOfFloat)
  {
    float f4 = -this.m20;
    float f3 = (float)Math.sqrt(1.0F - f4 * f4);
    float f5;
    float f6;
    float f1;
    float f2;
    if (f3 > 0.001F) {
      f5 = this.m22;
      f6 = this.m21;
      f1 = this.m00;
      f2 = this.m10;
    } else {
      f3 = 0.0F;

      f1 = 1.0F;
      f2 = 0.0F;
      f5 = this.m11;
      f6 = -this.m12;
    }
    paramArrayOfFloat[0] = (float)Math.atan2(f2, f1);
    paramArrayOfFloat[1] = (float)Math.atan2(f4, f3);
    paramArrayOfFloat[2] = (float)Math.atan2(f6, f5);
  }

  public void setEulers(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = (float)Math.cos(paramFloat1);
    float f2 = (float)Math.sin(paramFloat1);
    float f3 = (float)Math.cos(paramFloat2);
    float f4 = (float)Math.sin(paramFloat2);
    float f5 = (float)Math.cos(paramFloat3);
    float f6 = (float)Math.sin(paramFloat3);

    this.m00 = (f1 * f3);
    this.m01 = (-f2 * f5 + f1 * f4 * f6);
    this.m02 = (f2 * f6 + f1 * f4 * f5);
    this.m10 = (f2 * f3);
    this.m11 = (f1 * f5 + f2 * f4 * f6);
    this.m12 = (-f1 * f6 + f2 * f4 * f5);
    this.m20 = (-f4);
    this.m21 = (f3 * f6);
    this.m22 = (f3 * f5);

    this.m03 = 0.0F;
    this.m13 = 0.0F;
    this.m23 = 0.0F;

    this.m30 = 0.0F;
    this.m31 = 0.0F;
    this.m32 = 0.0F;
    this.m33 = 1.0F;
  }

  public void setEulersInv(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = (float)Math.cos(paramFloat1);
    float f2 = (float)Math.sin(paramFloat1);
    float f3 = (float)Math.cos(paramFloat2);
    float f4 = (float)Math.sin(paramFloat2);
    float f5 = (float)Math.cos(paramFloat3);
    float f6 = (float)Math.sin(paramFloat3);

    this.m00 = (f3 * f1);
    this.m01 = (f3 * f2);
    this.m02 = (-f4);
    this.m10 = (f6 * f4 * f1 - f5 * f2);
    this.m11 = (f6 * f4 * f2 + f5 * f1);
    this.m12 = (f6 * f3);
    this.m20 = (f5 * f4 * f1 + f6 * f2);
    this.m21 = (f5 * f4 * f2 - f6 * f1);
    this.m22 = (f5 * f3);

    this.m03 = 0.0F;
    this.m13 = 0.0F;
    this.m23 = 0.0F;

    this.m30 = 0.0F;
    this.m31 = 0.0F;
    this.m32 = 0.0F;
    this.m33 = 1.0F;
  }
}