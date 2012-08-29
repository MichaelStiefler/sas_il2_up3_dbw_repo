package com.maddox.JGP;

import java.io.Serializable;

public class Matrix3f
  implements Serializable, Cloneable
{
  public float m00;
  public float m01;
  public float m02;
  public float m10;
  public float m11;
  public float m12;
  public float m20;
  public float m21;
  public float m22;

  public Matrix3f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    set(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9);
  }

  public Matrix3f(float[] paramArrayOfFloat)
  {
    set(paramArrayOfFloat);
  }

  public Matrix3f(Matrix3d paramMatrix3d)
  {
    this.m00 = (float)paramMatrix3d.m00; this.m01 = (float)paramMatrix3d.m01; this.m02 = (float)paramMatrix3d.m02;
    this.m10 = (float)paramMatrix3d.m10; this.m11 = (float)paramMatrix3d.m11; this.m12 = (float)paramMatrix3d.m12;
    this.m20 = (float)paramMatrix3d.m20; this.m21 = (float)paramMatrix3d.m21; this.m22 = (float)paramMatrix3d.m22;
  }

  public Matrix3f(Matrix3f paramMatrix3f)
  {
    this.m00 = paramMatrix3f.m00; this.m01 = paramMatrix3f.m01; this.m02 = paramMatrix3f.m02;
    this.m10 = paramMatrix3f.m10; this.m11 = paramMatrix3f.m11; this.m12 = paramMatrix3f.m12;
    this.m20 = paramMatrix3f.m20; this.m21 = paramMatrix3f.m21; this.m22 = paramMatrix3f.m22;
  }

  public Matrix3f()
  {
    setZero();
  }

  public Matrix3f(Matrix4f paramMatrix4f) {
    this.m00 = paramMatrix4f.m00;
    this.m01 = paramMatrix4f.m01;
    this.m02 = paramMatrix4f.m02;
    this.m10 = paramMatrix4f.m10;
    this.m11 = paramMatrix4f.m11;
    this.m12 = paramMatrix4f.m12;
    this.m20 = paramMatrix4f.m20;
    this.m21 = paramMatrix4f.m21;
    this.m22 = paramMatrix4f.m22;
  }

  public Matrix3f(float[][] paramArrayOfFloat) {
    this.m00 = paramArrayOfFloat[0][0];
    this.m01 = paramArrayOfFloat[0][1];
    this.m02 = paramArrayOfFloat[0][2];
    this.m10 = paramArrayOfFloat[1][0];
    this.m11 = paramArrayOfFloat[1][1];
    this.m12 = paramArrayOfFloat[1][2];
    this.m20 = paramArrayOfFloat[2][0];
    this.m21 = paramArrayOfFloat[2][1];
    this.m22 = paramArrayOfFloat[2][2];
  }

  public Matrix3f(float paramFloat)
  {
    set(paramFloat);
  }
  public Matrix3f(AxisAngle4f paramAxisAngle4f) {
    set(paramAxisAngle4f);
  }
  public Matrix3f(Quat4f paramQuat4f) {
    set(paramQuat4f);
  }
  public final void set(Matrix4f paramMatrix4f) {
    this.m00 = paramMatrix4f.m00;
    this.m01 = paramMatrix4f.m01;
    this.m02 = paramMatrix4f.m02;
    this.m10 = paramMatrix4f.m10;
    this.m11 = paramMatrix4f.m11;
    this.m12 = paramMatrix4f.m12;
    this.m20 = paramMatrix4f.m20;
    this.m21 = paramMatrix4f.m21;
    this.m22 = paramMatrix4f.m22;
  }

  public final void set(float[][] paramArrayOfFloat) {
    this.m00 = paramArrayOfFloat[0][0];
    this.m01 = paramArrayOfFloat[0][1];
    this.m02 = paramArrayOfFloat[0][2];
    this.m10 = paramArrayOfFloat[1][0];
    this.m11 = paramArrayOfFloat[1][1];
    this.m12 = paramArrayOfFloat[1][2];
    this.m20 = paramArrayOfFloat[2][0];
    this.m21 = paramArrayOfFloat[2][1];
    this.m22 = paramArrayOfFloat[2][2];
  }

  public String toString()
  {
    String str = System.getProperty("line.separator");
    return "[" + str + "  [" + this.m00 + "\t" + this.m01 + "\t" + this.m02 + "]" + str + "  [" + this.m10 + "\t" + this.m11 + "\t" + this.m12 + "]" + str + "  [" + this.m20 + "\t" + this.m21 + "\t" + this.m22 + "] ]";
  }

  public final void setIdentity()
  {
    this.m00 = 1.0F; this.m01 = 0.0F; this.m02 = 0.0F;
    this.m10 = 0.0F; this.m11 = 1.0F; this.m12 = 0.0F;
    this.m20 = 0.0F; this.m21 = 0.0F; this.m22 = 1.0F;
  }

  public final void setScale(float paramFloat)
  {
    SVD(this);
    mul(paramFloat);
  }

  public final void setElement(int paramInt1, int paramInt2, float paramFloat)
  {
    if (paramInt1 == 0) {
      if (paramInt2 == 0)
        this.m00 = paramFloat;
      else if (paramInt2 == 1)
        this.m01 = paramFloat;
      else
        this.m02 = paramFloat;
    }
    else if (paramInt1 == 1) {
      if (paramInt2 == 0)
        this.m10 = paramFloat;
      else if (paramInt2 == 1)
        this.m11 = paramFloat;
      else {
        this.m12 = paramFloat;
      }
    }
    else if (paramInt2 == 0)
      this.m20 = paramFloat;
    else if (paramInt2 == 1)
      this.m21 = paramFloat;
    else
      this.m22 = paramFloat;
  }

  public final float getElement(int paramInt1, int paramInt2)
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

  public final void setRow(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramInt == 0) {
      this.m00 = paramFloat1;
      this.m01 = paramFloat2;
      this.m02 = paramFloat3;
    } else if (paramInt == 1) {
      this.m10 = paramFloat1;
      this.m11 = paramFloat2;
      this.m12 = paramFloat3;
    } else if (paramInt == 2) {
      this.m20 = paramFloat1;
      this.m21 = paramFloat2;
      this.m22 = paramFloat3;
    }
  }

  public final void setRow(int paramInt, Tuple3f paramTuple3f)
  {
    if (paramInt == 0) {
      this.m00 = paramTuple3f.x;
      this.m01 = paramTuple3f.y;
      this.m02 = paramTuple3f.z;
    } else if (paramInt == 1) {
      this.m10 = paramTuple3f.x;
      this.m11 = paramTuple3f.y;
      this.m12 = paramTuple3f.z;
    } else if (paramInt == 2) {
      this.m20 = paramTuple3f.x;
      this.m21 = paramTuple3f.y;
      this.m22 = paramTuple3f.z;
    }
  }

  public final void getRow(int paramInt, float[] paramArrayOfFloat)
  {
    if (paramInt == 0) {
      paramArrayOfFloat[0] = this.m00;
      paramArrayOfFloat[1] = this.m01;
      paramArrayOfFloat[2] = this.m02;
    } else if (paramInt == 1) {
      paramArrayOfFloat[0] = this.m10;
      paramArrayOfFloat[1] = this.m11;
      paramArrayOfFloat[2] = this.m12;
    } else if (paramInt == 2) {
      paramArrayOfFloat[0] = this.m20;
      paramArrayOfFloat[1] = this.m21;
      paramArrayOfFloat[2] = this.m22;
    }
  }

  public final void getRow(int paramInt, Tuple3f paramTuple3f)
  {
    if (paramInt == 0) {
      paramTuple3f.x = this.m00;
      paramTuple3f.y = this.m01;
      paramTuple3f.z = this.m02;
    } else if (paramInt == 1) {
      paramTuple3f.x = this.m10;
      paramTuple3f.y = this.m11;
      paramTuple3f.z = this.m12;
    } else if (paramInt == 2) {
      paramTuple3f.x = this.m20;
      paramTuple3f.y = this.m21;
      paramTuple3f.z = this.m22;
    }
  }

  public final void setRow(int paramInt, float[] paramArrayOfFloat)
  {
    if (paramInt == 0) {
      this.m00 = paramArrayOfFloat[0];
      this.m01 = paramArrayOfFloat[1];
      this.m02 = paramArrayOfFloat[2];
    } else if (paramInt == 1) {
      this.m10 = paramArrayOfFloat[0];
      this.m11 = paramArrayOfFloat[1];
      this.m12 = paramArrayOfFloat[2];
    } else if (paramInt == 2) {
      this.m20 = paramArrayOfFloat[0];
      this.m21 = paramArrayOfFloat[1];
      this.m22 = paramArrayOfFloat[2];
    }
  }

  public final void setColumn(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramInt == 0) {
      this.m00 = paramFloat1;
      this.m10 = paramFloat2;
      this.m20 = paramFloat3;
    } else if (paramInt == 1) {
      this.m01 = paramFloat1;
      this.m11 = paramFloat2;
      this.m21 = paramFloat3;
    } else if (paramInt == 2) {
      this.m02 = paramFloat1;
      this.m12 = paramFloat2;
      this.m22 = paramFloat3;
    }
  }

  public final void setColumn(int paramInt, Tuple3f paramTuple3f)
  {
    if (paramInt == 0) {
      this.m00 = paramTuple3f.x;
      this.m10 = paramTuple3f.y;
      this.m20 = paramTuple3f.z;
    } else if (paramInt == 1) {
      this.m01 = paramTuple3f.x;
      this.m11 = paramTuple3f.y;
      this.m21 = paramTuple3f.z;
    } else if (paramInt == 2) {
      this.m02 = paramTuple3f.x;
      this.m12 = paramTuple3f.y;
      this.m22 = paramTuple3f.z;
    }
  }

  public final void setColumn(int paramInt, float[] paramArrayOfFloat)
  {
    if (paramInt == 0) {
      this.m00 = paramArrayOfFloat[0];
      this.m10 = paramArrayOfFloat[1];
      this.m20 = paramArrayOfFloat[2];
    } else if (paramInt == 1) {
      this.m01 = paramArrayOfFloat[0];
      this.m11 = paramArrayOfFloat[1];
      this.m21 = paramArrayOfFloat[2];
    } else if (paramInt == 2) {
      this.m02 = paramArrayOfFloat[0];
      this.m12 = paramArrayOfFloat[1];
      this.m22 = paramArrayOfFloat[2];
    }
  }

  public final void getColumn(int paramInt, Tuple3f paramTuple3f)
  {
    if (paramInt == 0) {
      paramTuple3f.x = this.m00;
      paramTuple3f.y = this.m10;
      paramTuple3f.z = this.m20;
    } else if (paramInt == 1) {
      paramTuple3f.x = this.m01;
      paramTuple3f.y = this.m11;
      paramTuple3f.z = this.m21;
    } else if (paramInt == 2) {
      paramTuple3f.x = this.m02;
      paramTuple3f.y = this.m12;
      paramTuple3f.z = this.m22;
    }
  }

  public final void getColumn(int paramInt, float[] paramArrayOfFloat)
  {
    if (paramInt == 0) {
      paramArrayOfFloat[0] = this.m00;
      paramArrayOfFloat[1] = this.m10;
      paramArrayOfFloat[2] = this.m20;
    } else if (paramInt == 1) {
      paramArrayOfFloat[0] = this.m01;
      paramArrayOfFloat[1] = this.m11;
      paramArrayOfFloat[2] = this.m21;
    } else if (paramInt == 2) {
      paramArrayOfFloat[0] = this.m02;
      paramArrayOfFloat[1] = this.m12;
      paramArrayOfFloat[2] = this.m22;
    }
  }

  public final void get(float[][] paramArrayOfFloat) {
    paramArrayOfFloat[0][0] = this.m00;
    paramArrayOfFloat[0][1] = this.m01;
    paramArrayOfFloat[0][2] = this.m02;
    paramArrayOfFloat[1][0] = this.m10;
    paramArrayOfFloat[1][1] = this.m11;
    paramArrayOfFloat[1][2] = this.m12;
    paramArrayOfFloat[2][0] = this.m20;
    paramArrayOfFloat[2][1] = this.m21;
    paramArrayOfFloat[2][2] = this.m22;
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
  }

  public final float getScale()
  {
    return SVD(null);
  }

  public final void add(float paramFloat)
  {
    this.m00 += paramFloat; this.m01 += paramFloat; this.m02 += paramFloat;
    this.m10 += paramFloat; this.m11 += paramFloat; this.m12 += paramFloat;
    this.m20 += paramFloat; this.m21 += paramFloat; this.m22 += paramFloat;
  }

  public final void add(float paramFloat, Matrix3f paramMatrix3f)
  {
    set(paramMatrix3f);
    add(paramFloat);
  }

  public final void add(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2)
  {
    set(paramMatrix3f1.m00 + paramMatrix3f2.m00, paramMatrix3f1.m01 + paramMatrix3f2.m01, paramMatrix3f1.m02 + paramMatrix3f2.m02, paramMatrix3f1.m10 + paramMatrix3f2.m10, paramMatrix3f1.m11 + paramMatrix3f2.m11, paramMatrix3f1.m12 + paramMatrix3f2.m12, paramMatrix3f1.m20 + paramMatrix3f2.m20, paramMatrix3f1.m21 + paramMatrix3f2.m21, paramMatrix3f1.m22 + paramMatrix3f2.m22);
  }

  public final void add(Matrix3f paramMatrix3f)
  {
    this.m00 += paramMatrix3f.m00; this.m01 += paramMatrix3f.m01; this.m02 += paramMatrix3f.m02;
    this.m10 += paramMatrix3f.m10; this.m11 += paramMatrix3f.m11; this.m12 += paramMatrix3f.m12;
    this.m20 += paramMatrix3f.m20; this.m21 += paramMatrix3f.m21; this.m22 += paramMatrix3f.m22;
  }

  public final void sub(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2)
  {
    set(paramMatrix3f1.m00 - paramMatrix3f2.m00, paramMatrix3f1.m01 - paramMatrix3f2.m01, paramMatrix3f1.m02 - paramMatrix3f2.m02, paramMatrix3f1.m10 - paramMatrix3f2.m10, paramMatrix3f1.m11 - paramMatrix3f2.m11, paramMatrix3f1.m12 - paramMatrix3f2.m12, paramMatrix3f1.m20 - paramMatrix3f2.m20, paramMatrix3f1.m21 - paramMatrix3f2.m21, paramMatrix3f1.m22 - paramMatrix3f2.m22);
  }

  public final void sub(Matrix3f paramMatrix3f)
  {
    this.m00 -= paramMatrix3f.m00; this.m01 -= paramMatrix3f.m01; this.m02 -= paramMatrix3f.m02;
    this.m10 -= paramMatrix3f.m10; this.m11 -= paramMatrix3f.m11; this.m12 -= paramMatrix3f.m12;
    this.m20 -= paramMatrix3f.m20; this.m21 -= paramMatrix3f.m21; this.m22 -= paramMatrix3f.m22;
  }

  public final void transpose()
  {
    float f = this.m01;
    this.m01 = this.m10;
    this.m10 = f;

    f = this.m02;
    this.m02 = this.m20;
    this.m20 = f;

    f = this.m12;
    this.m12 = this.m21;
    this.m21 = f;
  }

  public final void transpose(Matrix3f paramMatrix3f)
  {
    set(paramMatrix3f);
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

  public final void set(AxisAngle4d paramAxisAngle4d)
  {
    setFromAxisAngle(paramAxisAngle4d.x, paramAxisAngle4d.y, paramAxisAngle4d.z, paramAxisAngle4d.angle);
  }

  public final void set(Quat4d paramQuat4d)
  {
    setFromQuat(paramQuat4d.x, paramQuat4d.y, paramQuat4d.z, paramQuat4d.w);
  }

  public final void set(Matrix3f paramMatrix3f)
  {
    this.m00 = paramMatrix3f.m00; this.m01 = paramMatrix3f.m01; this.m02 = paramMatrix3f.m02;
    this.m10 = paramMatrix3f.m10; this.m11 = paramMatrix3f.m11; this.m12 = paramMatrix3f.m12;
    this.m20 = paramMatrix3f.m20; this.m21 = paramMatrix3f.m21; this.m22 = paramMatrix3f.m22;
  }

  public final void set(Matrix3d paramMatrix3d)
  {
    this.m00 = (float)paramMatrix3d.m00; this.m01 = (float)paramMatrix3d.m01; this.m02 = (float)paramMatrix3d.m02;
    this.m10 = (float)paramMatrix3d.m10; this.m11 = (float)paramMatrix3d.m11; this.m12 = (float)paramMatrix3d.m12;
    this.m20 = (float)paramMatrix3d.m20; this.m21 = (float)paramMatrix3d.m21; this.m22 = (float)paramMatrix3d.m22;
  }

  public final void set(float[] paramArrayOfFloat)
  {
    this.m00 = paramArrayOfFloat[0]; this.m01 = paramArrayOfFloat[1]; this.m02 = paramArrayOfFloat[2];
    this.m10 = paramArrayOfFloat[3]; this.m11 = paramArrayOfFloat[4]; this.m12 = paramArrayOfFloat[5];
    this.m20 = paramArrayOfFloat[6]; this.m21 = paramArrayOfFloat[7]; this.m22 = paramArrayOfFloat[8];
  }

  public final void invert(Matrix3f paramMatrix3f)
  {
    set(paramMatrix3f);
    invert();
  }

  public final void invert()
  {
    double d = determinant();
    if (d == 0.0D)
      return;
    d = 1.0D / d;

    set(this.m11 * this.m22 - this.m12 * this.m21, this.m02 * this.m21 - this.m01 * this.m22, this.m01 * this.m12 - this.m02 * this.m11, this.m12 * this.m20 - this.m10 * this.m22, this.m00 * this.m22 - this.m02 * this.m20, this.m02 * this.m10 - this.m00 * this.m12, this.m10 * this.m21 - this.m11 * this.m20, this.m01 * this.m20 - this.m00 * this.m21, this.m00 * this.m11 - this.m01 * this.m10);

    mul((float)d);
  }

  public final float determinant()
  {
    return this.m00 * (this.m11 * this.m22 - this.m21 * this.m12) - this.m01 * (this.m10 * this.m22 - this.m20 * this.m12) + this.m02 * (this.m10 * this.m21 - this.m20 * this.m11);
  }

  public final void set(float paramFloat)
  {
    this.m00 = paramFloat; this.m01 = 0.0F; this.m02 = 0.0F;
    this.m10 = 0.0F; this.m11 = paramFloat; this.m12 = 0.0F;
    this.m20 = 0.0F; this.m21 = 0.0F; this.m22 = paramFloat;
  }

  public final void rotX(float paramFloat)
  {
    double d1 = Math.cos(paramFloat);
    double d2 = Math.sin(paramFloat);
    this.m00 = 1.0F; this.m01 = 0.0F; this.m02 = 0.0F;
    this.m10 = 0.0F; this.m11 = (float)d1; this.m12 = (float)(-d2);
    this.m20 = 0.0F; this.m21 = (float)d2; this.m22 = (float)d1;
  }

  public final void rotY(float paramFloat)
  {
    double d1 = Math.cos(paramFloat);
    double d2 = Math.sin(paramFloat);
    this.m00 = (float)d1; this.m01 = 0.0F; this.m02 = (float)d2;
    this.m10 = 0.0F; this.m11 = 1.0F; this.m12 = 0.0F;
    this.m20 = (float)(-d2); this.m21 = 0.0F; this.m22 = (float)d1;
  }

  public final void rotZ(float paramFloat)
  {
    double d1 = Math.cos(paramFloat);
    double d2 = Math.sin(paramFloat);
    this.m00 = (float)d1; this.m01 = (float)(-d2); this.m02 = 0.0F;
    this.m10 = (float)d2; this.m11 = (float)d1; this.m12 = 0.0F;
    this.m20 = 0.0F; this.m21 = 0.0F; this.m22 = 1.0F;
  }

  public final void mul(float paramFloat)
  {
    this.m00 *= paramFloat; this.m01 *= paramFloat; this.m02 *= paramFloat;
    this.m10 *= paramFloat; this.m11 *= paramFloat; this.m12 *= paramFloat;
    this.m20 *= paramFloat; this.m21 *= paramFloat; this.m22 *= paramFloat;
  }

  public final void mul(float paramFloat, Matrix3f paramMatrix3f)
  {
    set(paramMatrix3f);
    mul(paramFloat);
  }

  public final void mul(Matrix3f paramMatrix3f)
  {
    mul(this, paramMatrix3f);
  }

  public final void mul(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2)
  {
    set(paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m01 * paramMatrix3f2.m10 + paramMatrix3f1.m02 * paramMatrix3f2.m20, paramMatrix3f1.m00 * paramMatrix3f2.m01 + paramMatrix3f1.m01 * paramMatrix3f2.m11 + paramMatrix3f1.m02 * paramMatrix3f2.m21, paramMatrix3f1.m00 * paramMatrix3f2.m02 + paramMatrix3f1.m01 * paramMatrix3f2.m12 + paramMatrix3f1.m02 * paramMatrix3f2.m22, paramMatrix3f1.m10 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m10 + paramMatrix3f1.m12 * paramMatrix3f2.m20, paramMatrix3f1.m10 * paramMatrix3f2.m01 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m12 * paramMatrix3f2.m21, paramMatrix3f1.m10 * paramMatrix3f2.m02 + paramMatrix3f1.m11 * paramMatrix3f2.m12 + paramMatrix3f1.m12 * paramMatrix3f2.m22, paramMatrix3f1.m20 * paramMatrix3f2.m00 + paramMatrix3f1.m21 * paramMatrix3f2.m10 + paramMatrix3f1.m22 * paramMatrix3f2.m20, paramMatrix3f1.m20 * paramMatrix3f2.m01 + paramMatrix3f1.m21 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m21, paramMatrix3f1.m20 * paramMatrix3f2.m02 + paramMatrix3f1.m21 * paramMatrix3f2.m12 + paramMatrix3f1.m22 * paramMatrix3f2.m22);
  }

  public final void mulNormalize(Matrix3f paramMatrix3f)
  {
    mul(paramMatrix3f);
    SVD(this);
  }

  public final void mulNormalize(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2)
  {
    mul(paramMatrix3f1, paramMatrix3f2);
    SVD(this);
  }

  public final void mulTransposeBoth(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2)
  {
    mul(paramMatrix3f2, paramMatrix3f1);
    transpose();
  }

  public final void mulTransposeRight(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2)
  {
    set(paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m01 * paramMatrix3f2.m01 + paramMatrix3f1.m02 * paramMatrix3f2.m02, paramMatrix3f1.m00 * paramMatrix3f2.m10 + paramMatrix3f1.m01 * paramMatrix3f2.m11 + paramMatrix3f1.m02 * paramMatrix3f2.m12, paramMatrix3f1.m00 * paramMatrix3f2.m20 + paramMatrix3f1.m01 * paramMatrix3f2.m21 + paramMatrix3f1.m02 * paramMatrix3f2.m22, paramMatrix3f1.m10 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m01 + paramMatrix3f1.m12 * paramMatrix3f2.m02, paramMatrix3f1.m10 * paramMatrix3f2.m10 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m12 * paramMatrix3f2.m12, paramMatrix3f1.m10 * paramMatrix3f2.m20 + paramMatrix3f1.m11 * paramMatrix3f2.m21 + paramMatrix3f1.m12 * paramMatrix3f2.m22, paramMatrix3f1.m20 * paramMatrix3f2.m00 + paramMatrix3f1.m21 * paramMatrix3f2.m01 + paramMatrix3f1.m22 * paramMatrix3f2.m02, paramMatrix3f1.m20 * paramMatrix3f2.m10 + paramMatrix3f1.m21 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m12, paramMatrix3f1.m20 * paramMatrix3f2.m20 + paramMatrix3f1.m21 * paramMatrix3f2.m21 + paramMatrix3f1.m22 * paramMatrix3f2.m22);
  }

  public final void mulTransposeLeft(Matrix3f paramMatrix3f1, Matrix3f paramMatrix3f2)
  {
    set(paramMatrix3f1.m00 * paramMatrix3f2.m00 + paramMatrix3f1.m10 * paramMatrix3f2.m10 + paramMatrix3f1.m20 * paramMatrix3f2.m20, paramMatrix3f1.m00 * paramMatrix3f2.m01 + paramMatrix3f1.m10 * paramMatrix3f2.m11 + paramMatrix3f1.m20 * paramMatrix3f2.m21, paramMatrix3f1.m00 * paramMatrix3f2.m02 + paramMatrix3f1.m10 * paramMatrix3f2.m12 + paramMatrix3f1.m20 * paramMatrix3f2.m22, paramMatrix3f1.m01 * paramMatrix3f2.m00 + paramMatrix3f1.m11 * paramMatrix3f2.m10 + paramMatrix3f1.m21 * paramMatrix3f2.m20, paramMatrix3f1.m01 * paramMatrix3f2.m01 + paramMatrix3f1.m11 * paramMatrix3f2.m11 + paramMatrix3f1.m21 * paramMatrix3f2.m21, paramMatrix3f1.m01 * paramMatrix3f2.m02 + paramMatrix3f1.m11 * paramMatrix3f2.m12 + paramMatrix3f1.m21 * paramMatrix3f2.m22, paramMatrix3f1.m02 * paramMatrix3f2.m00 + paramMatrix3f1.m12 * paramMatrix3f2.m10 + paramMatrix3f1.m22 * paramMatrix3f2.m20, paramMatrix3f1.m02 * paramMatrix3f2.m01 + paramMatrix3f1.m12 * paramMatrix3f2.m11 + paramMatrix3f1.m22 * paramMatrix3f2.m21, paramMatrix3f1.m02 * paramMatrix3f2.m02 + paramMatrix3f1.m12 * paramMatrix3f2.m12 + paramMatrix3f1.m22 * paramMatrix3f2.m22);
  }

  public final void normalize()
  {
    SVD(this);
  }

  public final void normalize(Matrix3f paramMatrix3f)
  {
    set(paramMatrix3f);
    SVD(this);
  }

  public final void normalizeCP()
  {
    double d = Math.pow(determinant(), -0.3333333333333333D);
    mul((float)d);
  }

  public final void normalizeCP(Matrix3f paramMatrix3f)
  {
    set(paramMatrix3f);
    normalizeCP();
  }

  public boolean equals(Matrix3f paramMatrix3f)
  {
    return (paramMatrix3f != null) && (this.m00 == paramMatrix3f.m00) && (this.m01 == paramMatrix3f.m01) && (this.m02 == paramMatrix3f.m02) && (this.m10 == paramMatrix3f.m10) && (this.m11 == paramMatrix3f.m11) && (this.m12 == paramMatrix3f.m12) && (this.m20 == paramMatrix3f.m20) && (this.m21 == paramMatrix3f.m21) && (this.m22 == paramMatrix3f.m22);
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof Matrix3f)) && (equals((Matrix3f)paramObject));
  }

  public boolean epsilonEquals(Matrix3f paramMatrix3f, double paramDouble)
  {
    return (Math.abs(this.m00 - paramMatrix3f.m00) <= paramDouble) && (Math.abs(this.m01 - paramMatrix3f.m01) <= paramDouble) && (Math.abs(this.m02 - paramMatrix3f.m02) <= paramDouble) && (Math.abs(this.m10 - paramMatrix3f.m10) <= paramDouble) && (Math.abs(this.m11 - paramMatrix3f.m11) <= paramDouble) && (Math.abs(this.m12 - paramMatrix3f.m12) <= paramDouble) && (Math.abs(this.m20 - paramMatrix3f.m20) <= paramDouble) && (Math.abs(this.m21 - paramMatrix3f.m21) <= paramDouble) && (Math.abs(this.m22 - paramMatrix3f.m22) <= paramDouble);
  }

  public int hashCode()
  {
    return Float.floatToIntBits(this.m00) ^ Float.floatToIntBits(this.m01) ^ Float.floatToIntBits(this.m02) ^ Float.floatToIntBits(this.m10) ^ Float.floatToIntBits(this.m11) ^ Float.floatToIntBits(this.m12) ^ Float.floatToIntBits(this.m20) ^ Float.floatToIntBits(this.m21) ^ Float.floatToIntBits(this.m22);
  }

  public final void setZero()
  {
    this.m00 = 0.0F; this.m01 = 0.0F; this.m02 = 0.0F;
    this.m10 = 0.0F; this.m11 = 0.0F; this.m12 = 0.0F;
    this.m20 = 0.0F; this.m21 = 0.0F; this.m22 = 0.0F;
  }

  public final void negate()
  {
    this.m00 = (-this.m00); this.m01 = (-this.m01); this.m02 = (-this.m02);
    this.m10 = (-this.m10); this.m11 = (-this.m11); this.m12 = (-this.m12);
    this.m20 = (-this.m20); this.m21 = (-this.m21); this.m22 = (-this.m22);
  }

  public final void negate(Matrix3f paramMatrix3f)
  {
    set(paramMatrix3f);
    negate();
  }

  public final void transform(Tuple3f paramTuple3f)
  {
    transform(paramTuple3f, paramTuple3f);
  }

  public final void transform(Tuple3f paramTuple3f1, Tuple3f paramTuple3f2)
  {
    paramTuple3f2.set(this.m00 * paramTuple3f1.x + this.m01 * paramTuple3f1.y + this.m02 * paramTuple3f1.z, this.m10 * paramTuple3f1.x + this.m11 * paramTuple3f1.y + this.m12 * paramTuple3f1.z, this.m20 * paramTuple3f1.x + this.m21 * paramTuple3f1.y + this.m22 * paramTuple3f1.z);
  }

  private void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    this.m00 = paramFloat1; this.m01 = paramFloat2; this.m02 = paramFloat3;
    this.m10 = paramFloat4; this.m11 = paramFloat5; this.m12 = paramFloat6;
    this.m20 = paramFloat7; this.m21 = paramFloat8; this.m22 = paramFloat9;
  }

  private float SVD(Matrix3f paramMatrix3f)
  {
    float f1 = (float)Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20 + this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21 + this.m02 * this.m02 + this.m12 * this.m12 + this.m22 * this.m22) / 3.0D);

    float f2 = f1 == 0.0F ? 0.0F : 1.0F / f1;

    if (paramMatrix3f != null) {
      if (paramMatrix3f != this)
        paramMatrix3f.set(this);
      paramMatrix3f.mul(f2);
    }

    return f1;
  }

  private void setFromQuat(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    double d1 = paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2 + paramDouble3 * paramDouble3 + paramDouble4 * paramDouble4;
    double d2 = d1 > 0.0D ? 2.0D / d1 : 0.0D;

    double d3 = paramDouble1 * d2; double d4 = paramDouble2 * d2; double d5 = paramDouble3 * d2;
    double d6 = paramDouble4 * d3; double d7 = paramDouble4 * d4; double d8 = paramDouble4 * d5;
    double d9 = paramDouble1 * d3; double d10 = paramDouble1 * d4; double d11 = paramDouble1 * d5;
    double d12 = paramDouble2 * d4; double d13 = paramDouble2 * d5; double d14 = paramDouble3 * d5;

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
}