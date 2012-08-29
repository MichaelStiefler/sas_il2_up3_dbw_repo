// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Matrix4f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Quat4f, Tuple3f, Matrix3f, Tuple4f, 
//            Vector4f, AxisAngle4f, Quat4d, AxisAngle4d, 
//            Tuple3d, Matrix4d, Matrix3d, Point3f

public class Matrix4f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Matrix4f(float f, float f1, float f2, float f3, float f4, float f5, float f6, 
            float f7, float f8, float f9, float f10, float f11, float f12, float f13, 
            float f14, float f15)
    {
        set(f, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15);
    }

    public Matrix4f(float af[])
    {
        set(af);
    }

    public Matrix4f(com.maddox.JGP.Quat4f quat4f, com.maddox.JGP.Tuple3f tuple3f, float f)
    {
        set(quat4f, tuple3f, f);
    }

    public Matrix4f(com.maddox.JGP.Matrix4d matrix4d)
    {
        set(matrix4d);
    }

    public Matrix4f(com.maddox.JGP.Matrix4f matrix4f)
    {
        set(matrix4f);
    }

    public Matrix4f(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Tuple3f tuple3f, float f)
    {
        set(matrix3f);
        mulRotationScale(f);
        setTranslation(tuple3f);
        m33 = 1.0F;
    }

    public Matrix4f()
    {
        setZero();
    }

    public java.lang.String toString()
    {
        java.lang.String s = java.lang.System.getProperty("line.separator");
        return "[" + s + "  [" + m00 + "\t" + m01 + "\t" + m02 + "\t" + m03 + "]" + s + "  [" + m10 + "\t" + m11 + "\t" + m12 + "\t" + m13 + "]" + s + "  [" + m20 + "\t" + m21 + "\t" + m22 + "\t" + m23 + "]" + s + "  [" + m30 + "\t" + m31 + "\t" + m32 + "\t" + m33 + "] ]";
    }

    public final void setIdentity()
    {
        m00 = 1.0F;
        m01 = 0.0F;
        m02 = 0.0F;
        m03 = 0.0F;
        m10 = 0.0F;
        m11 = 1.0F;
        m12 = 0.0F;
        m13 = 0.0F;
        m20 = 0.0F;
        m21 = 0.0F;
        m22 = 1.0F;
        m23 = 0.0F;
        m30 = 0.0F;
        m31 = 0.0F;
        m32 = 0.0F;
        m33 = 1.0F;
    }

    public final void setElement(int i, int j, float f)
    {
        if(i == 0)
        {
            if(j == 0)
                m00 = f;
            else
            if(j == 1)
                m01 = f;
            else
            if(j == 2)
                m02 = f;
            else
                m03 = f;
        } else
        if(i == 1)
        {
            if(j == 0)
                m10 = f;
            else
            if(j == 1)
                m11 = f;
            else
            if(j == 2)
                m12 = f;
            else
                m13 = f;
        } else
        if(i == 2)
        {
            if(j == 0)
                m20 = f;
            else
            if(j == 1)
                m21 = f;
            else
            if(j == 2)
                m22 = f;
            else
                m23 = f;
        } else
        if(j == 0)
            m30 = f;
        else
        if(j == 1)
            m31 = f;
        else
        if(j == 2)
            m32 = f;
        else
            m33 = f;
    }

    public final float getElement(int i, int j)
    {
        if(i == 0)
        {
            if(j == 0)
                return m00;
            if(j == 1)
                return m01;
            if(j == 2)
                return m02;
            else
                return m03;
        }
        if(i == 1)
        {
            if(j == 0)
                return m10;
            if(j == 1)
                return m11;
            if(j == 2)
                return m12;
            else
                return m13;
        }
        if(i == 2)
        {
            if(j == 0)
                return m20;
            if(j == 1)
                return m21;
            if(j == 2)
                return m22;
            else
                return m23;
        }
        if(j == 0)
            return m30;
        if(j == 1)
            return m31;
        if(j == 2)
            return m32;
        else
            return m33;
    }

    public final void setScale(float f)
    {
        SVD(null, this);
        mulRotationScale(f);
    }

    public final void get(com.maddox.JGP.Matrix3d matrix3d)
    {
        SVD(matrix3d);
    }

    public final void get(com.maddox.JGP.Matrix3f matrix3f)
    {
        SVD(matrix3f, null);
    }

    public final float get(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Tuple3f tuple3f)
    {
        get(tuple3f);
        return SVD(matrix3f, null);
    }

    public final void get(com.maddox.JGP.Quat4f quat4f)
    {
        quat4f.set(this);
        quat4f.normalize();
    }

    public final void get(com.maddox.JGP.Tuple3f tuple3f)
    {
        tuple3f.x = m03;
        tuple3f.y = m13;
        tuple3f.z = m23;
    }

    public final void getRotationScale(com.maddox.JGP.Matrix3f matrix3f)
    {
        matrix3f.m00 = m00;
        matrix3f.m01 = m01;
        matrix3f.m02 = m02;
        matrix3f.m10 = m10;
        matrix3f.m11 = m11;
        matrix3f.m12 = m12;
        matrix3f.m20 = m20;
        matrix3f.m21 = m21;
        matrix3f.m22 = m22;
    }

    public final float getScale()
    {
        return SVD(null);
    }

    public final void setRotationScale(com.maddox.JGP.Matrix3f matrix3f)
    {
        m00 = matrix3f.m00;
        m01 = matrix3f.m01;
        m02 = matrix3f.m02;
        m10 = matrix3f.m10;
        m11 = matrix3f.m11;
        m12 = matrix3f.m12;
        m20 = matrix3f.m20;
        m21 = matrix3f.m21;
        m22 = matrix3f.m22;
    }

    public final void setRow(int i, float f, float f1, float f2, float f3)
    {
        if(i == 0)
        {
            m00 = f;
            m01 = f1;
            m02 = f2;
            m03 = f3;
        } else
        if(i == 1)
        {
            m10 = f;
            m11 = f1;
            m12 = f2;
            m13 = f3;
        } else
        if(i == 2)
        {
            m20 = f;
            m21 = f1;
            m22 = f2;
            m23 = f3;
        } else
        if(i == 3)
        {
            m30 = f;
            m31 = f1;
            m32 = f2;
            m33 = f3;
        }
    }

    public final void setRow(int i, com.maddox.JGP.Tuple4f tuple4f)
    {
        if(i == 0)
        {
            m00 = tuple4f.x;
            m01 = tuple4f.y;
            m02 = tuple4f.z;
            m03 = tuple4f.w;
        } else
        if(i == 1)
        {
            m10 = tuple4f.x;
            m11 = tuple4f.y;
            m12 = tuple4f.z;
            m13 = tuple4f.w;
        } else
        if(i == 2)
        {
            m20 = tuple4f.x;
            m21 = tuple4f.y;
            m22 = tuple4f.z;
            m23 = tuple4f.w;
        } else
        if(i == 3)
        {
            m30 = tuple4f.x;
            m31 = tuple4f.y;
            m32 = tuple4f.z;
            m33 = tuple4f.w;
        }
    }

    public final void setRow(int i, float af[])
    {
        if(i == 0)
        {
            m00 = af[0];
            m01 = af[1];
            m02 = af[2];
            m03 = af[3];
        } else
        if(i == 1)
        {
            m10 = af[0];
            m11 = af[1];
            m12 = af[2];
            m13 = af[3];
        } else
        if(i == 2)
        {
            m20 = af[0];
            m21 = af[1];
            m22 = af[2];
            m23 = af[3];
        } else
        if(i == 3)
        {
            m30 = af[0];
            m31 = af[1];
            m32 = af[2];
            m33 = af[3];
        }
    }

    public final void getRow(int i, com.maddox.JGP.Vector4f vector4f)
    {
        if(i == 0)
        {
            vector4f.x = m00;
            vector4f.y = m01;
            vector4f.z = m02;
            vector4f.w = m03;
        } else
        if(i == 1)
        {
            vector4f.x = m10;
            vector4f.y = m11;
            vector4f.z = m12;
            vector4f.w = m13;
        } else
        if(i == 2)
        {
            vector4f.x = m20;
            vector4f.y = m21;
            vector4f.z = m22;
            vector4f.w = m23;
        } else
        if(i == 3)
        {
            vector4f.x = m30;
            vector4f.y = m31;
            vector4f.z = m32;
            vector4f.w = m33;
        }
    }

    public final void getRow(int i, float af[])
    {
        if(i == 0)
        {
            af[0] = m00;
            af[1] = m01;
            af[2] = m02;
            af[3] = m03;
        } else
        if(i == 1)
        {
            af[0] = m10;
            af[1] = m11;
            af[2] = m12;
            af[3] = m13;
        } else
        if(i == 2)
        {
            af[0] = m20;
            af[1] = m21;
            af[2] = m22;
            af[3] = m23;
        } else
        if(i == 3)
        {
            af[0] = m30;
            af[1] = m31;
            af[2] = m32;
            af[3] = m33;
        }
    }

    public final void setColumn(int i, float f, float f1, float f2, float f3)
    {
        if(i == 0)
        {
            m00 = f;
            m10 = f1;
            m20 = f2;
            m30 = f3;
        } else
        if(i == 1)
        {
            m01 = f;
            m11 = f1;
            m21 = f2;
            m31 = f3;
        } else
        if(i == 2)
        {
            m02 = f;
            m12 = f1;
            m22 = f2;
            m32 = f3;
        } else
        if(i == 3)
        {
            m03 = f;
            m13 = f1;
            m23 = f2;
            m33 = f3;
        }
    }

    public final void setColumn(int i, com.maddox.JGP.Vector4f vector4f)
    {
        if(i == 0)
        {
            m00 = vector4f.x;
            m10 = vector4f.y;
            m20 = vector4f.z;
            m30 = vector4f.w;
        } else
        if(i == 1)
        {
            m01 = vector4f.x;
            m11 = vector4f.y;
            m21 = vector4f.z;
            m31 = vector4f.w;
        } else
        if(i == 2)
        {
            m02 = vector4f.x;
            m12 = vector4f.y;
            m22 = vector4f.z;
            m32 = vector4f.w;
        } else
        if(i == 3)
        {
            m03 = vector4f.x;
            m13 = vector4f.y;
            m23 = vector4f.z;
            m33 = vector4f.w;
        }
    }

    public final void setColumn(int i, float af[])
    {
        if(i == 0)
        {
            m00 = af[0];
            m10 = af[1];
            m20 = af[2];
            m30 = af[3];
        } else
        if(i == 1)
        {
            m01 = af[0];
            m11 = af[1];
            m21 = af[2];
            m31 = af[3];
        } else
        if(i == 2)
        {
            m02 = af[0];
            m12 = af[1];
            m22 = af[2];
            m32 = af[3];
        } else
        if(i == 3)
        {
            m03 = af[0];
            m13 = af[1];
            m23 = af[2];
            m33 = af[3];
        }
    }

    public final void getColumn(int i, com.maddox.JGP.Vector4f vector4f)
    {
        if(i == 0)
        {
            vector4f.x = m00;
            vector4f.y = m10;
            vector4f.z = m20;
            vector4f.w = m30;
        } else
        if(i == 1)
        {
            vector4f.x = m01;
            vector4f.y = m11;
            vector4f.z = m21;
            vector4f.w = m31;
        } else
        if(i == 2)
        {
            vector4f.x = m02;
            vector4f.y = m12;
            vector4f.z = m22;
            vector4f.w = m32;
        } else
        if(i == 3)
        {
            vector4f.x = m03;
            vector4f.y = m13;
            vector4f.z = m23;
            vector4f.w = m33;
        }
    }

    public final void getColumn(int i, float af[])
    {
        if(i == 0)
        {
            af[0] = m00;
            af[1] = m10;
            af[2] = m20;
            af[3] = m30;
        } else
        if(i == 1)
        {
            af[0] = m01;
            af[1] = m11;
            af[2] = m21;
            af[3] = m31;
        } else
        if(i == 2)
        {
            af[0] = m02;
            af[1] = m12;
            af[2] = m22;
            af[3] = m32;
        } else
        if(i == 3)
        {
            af[0] = m03;
            af[1] = m13;
            af[2] = m23;
            af[3] = m33;
        }
    }

    public final void add(float f)
    {
        m00 += f;
        m01 += f;
        m02 += f;
        m03 += f;
        m10 += f;
        m11 += f;
        m12 += f;
        m13 += f;
        m20 += f;
        m21 += f;
        m22 += f;
        m23 += f;
        m30 += f;
        m31 += f;
        m32 += f;
        m33 += f;
    }

    public final void add(float f, com.maddox.JGP.Matrix4f matrix4f)
    {
        set(matrix4f);
        add(f);
    }

    public final void add(com.maddox.JGP.Matrix4f matrix4f, com.maddox.JGP.Matrix4f matrix4f1)
    {
        set(matrix4f);
        add(matrix4f1);
    }

    public final void add(com.maddox.JGP.Matrix4f matrix4f)
    {
        m00 += matrix4f.m00;
        m01 += matrix4f.m01;
        m02 += matrix4f.m02;
        m03 += matrix4f.m03;
        m10 += matrix4f.m10;
        m11 += matrix4f.m11;
        m12 += matrix4f.m12;
        m13 += matrix4f.m13;
        m20 += matrix4f.m20;
        m21 += matrix4f.m21;
        m22 += matrix4f.m22;
        m23 += matrix4f.m23;
        m30 += matrix4f.m30;
        m31 += matrix4f.m31;
        m32 += matrix4f.m32;
        m33 += matrix4f.m33;
    }

    public final void sub(com.maddox.JGP.Matrix4f matrix4f, com.maddox.JGP.Matrix4f matrix4f1)
    {
        set(matrix4f.m00 - matrix4f1.m00, matrix4f.m01 - matrix4f1.m01, matrix4f.m02 - matrix4f1.m02, matrix4f.m03 - matrix4f1.m03, matrix4f.m10 - matrix4f1.m10, matrix4f.m11 - matrix4f1.m11, matrix4f.m12 - matrix4f1.m12, matrix4f.m13 - matrix4f1.m13, matrix4f.m20 - matrix4f1.m20, matrix4f.m21 - matrix4f1.m21, matrix4f.m22 - matrix4f1.m22, matrix4f.m23 - matrix4f1.m23, matrix4f.m30 - matrix4f1.m30, matrix4f.m31 - matrix4f1.m31, matrix4f.m32 - matrix4f1.m32, matrix4f.m33 - matrix4f1.m33);
    }

    public final void sub(com.maddox.JGP.Matrix4f matrix4f)
    {
        m00 -= matrix4f.m00;
        m01 -= matrix4f.m01;
        m02 -= matrix4f.m02;
        m03 -= matrix4f.m03;
        m10 -= matrix4f.m10;
        m11 -= matrix4f.m11;
        m12 -= matrix4f.m12;
        m13 -= matrix4f.m13;
        m20 -= matrix4f.m20;
        m21 -= matrix4f.m21;
        m22 -= matrix4f.m22;
        m23 -= matrix4f.m23;
        m30 -= matrix4f.m30;
        m31 -= matrix4f.m31;
        m32 -= matrix4f.m32;
        m33 -= matrix4f.m33;
    }

    public final void transpose()
    {
        float f = m01;
        m01 = m10;
        m10 = f;
        f = m02;
        m02 = m20;
        m20 = f;
        f = m03;
        m03 = m30;
        m30 = f;
        f = m12;
        m12 = m21;
        m21 = f;
        f = m13;
        m13 = m31;
        m31 = f;
        f = m23;
        m23 = m32;
        m32 = f;
    }

    public final void transpose(com.maddox.JGP.Matrix4f matrix4f)
    {
        set(matrix4f);
        transpose();
    }

    public final void set(com.maddox.JGP.Quat4f quat4f)
    {
        setFromQuat(quat4f.x, quat4f.y, quat4f.z, quat4f.w);
    }

    public final void set(com.maddox.JGP.AxisAngle4f axisangle4f)
    {
        setFromAxisAngle(axisangle4f.x, axisangle4f.y, axisangle4f.z, axisangle4f.angle);
    }

    public final void set(com.maddox.JGP.Quat4d quat4d)
    {
        setFromQuat(quat4d.x, quat4d.y, quat4d.z, quat4d.w);
    }

    public final void set(com.maddox.JGP.AxisAngle4d axisangle4d)
    {
        setFromAxisAngle(axisangle4d.x, axisangle4d.y, axisangle4d.z, axisangle4d.angle);
    }

    public final void set(com.maddox.JGP.Quat4d quat4d, com.maddox.JGP.Tuple3d tuple3d, double d)
    {
        set(quat4d);
        mulRotationScale((float)d);
        m03 = (float)tuple3d.x;
        m13 = (float)tuple3d.y;
        m23 = (float)tuple3d.z;
    }

    public final void set(com.maddox.JGP.Quat4f quat4f, com.maddox.JGP.Tuple3f tuple3f, float f)
    {
        set(quat4f);
        mulRotationScale(f);
        m03 = tuple3f.x;
        m13 = tuple3f.y;
        m23 = tuple3f.z;
    }

    public final void set(com.maddox.JGP.Matrix4d matrix4d)
    {
        m00 = (float)matrix4d.m00;
        m01 = (float)matrix4d.m01;
        m02 = (float)matrix4d.m02;
        m03 = (float)matrix4d.m03;
        m10 = (float)matrix4d.m10;
        m11 = (float)matrix4d.m11;
        m12 = (float)matrix4d.m12;
        m13 = (float)matrix4d.m13;
        m20 = (float)matrix4d.m20;
        m21 = (float)matrix4d.m21;
        m22 = (float)matrix4d.m22;
        m23 = (float)matrix4d.m23;
        m30 = (float)matrix4d.m30;
        m31 = (float)matrix4d.m31;
        m32 = (float)matrix4d.m32;
        m33 = (float)matrix4d.m33;
    }

    public final void set(com.maddox.JGP.Matrix4f matrix4f)
    {
        m00 = matrix4f.m00;
        m01 = matrix4f.m01;
        m02 = matrix4f.m02;
        m03 = matrix4f.m03;
        m10 = matrix4f.m10;
        m11 = matrix4f.m11;
        m12 = matrix4f.m12;
        m13 = matrix4f.m13;
        m20 = matrix4f.m20;
        m21 = matrix4f.m21;
        m22 = matrix4f.m22;
        m23 = matrix4f.m23;
        m30 = matrix4f.m30;
        m31 = matrix4f.m31;
        m32 = matrix4f.m32;
        m33 = matrix4f.m33;
    }

    public final void invert(com.maddox.JGP.Matrix4f matrix4f)
    {
        set(matrix4f);
        invert();
    }

    public final void invert()
    {
        float f = determinant();
        if((double)f == 0.0D)
        {
            return;
        } else
        {
            f = 1.0F / f;
            set(m11 * (m22 * m33 - m23 * m32) + m12 * (m23 * m31 - m21 * m33) + m13 * (m21 * m32 - m22 * m31), m21 * (m02 * m33 - m03 * m32) + m22 * (m03 * m31 - m01 * m33) + m23 * (m01 * m32 - m02 * m31), m31 * (m02 * m13 - m03 * m12) + m32 * (m03 * m11 - m01 * m13) + m33 * (m01 * m12 - m02 * m11), m01 * (m13 * m22 - m12 * m23) + m02 * (m11 * m23 - m13 * m21) + m03 * (m12 * m21 - m11 * m22), m12 * (m20 * m33 - m23 * m30) + m13 * (m22 * m30 - m20 * m32) + m10 * (m23 * m32 - m22 * m33), m22 * (m00 * m33 - m03 * m30) + m23 * (m02 * m30 - m00 * m32) + m20 * (m03 * m32 - m02 * m33), m32 * (m00 * m13 - m03 * m10) + m33 * (m02 * m10 - m00 * m12) + m30 * (m03 * m12 - m02 * m13), m02 * (m13 * m20 - m10 * m23) + m03 * (m10 * m22 - m12 * m20) + m00 * (m12 * m23 - m13 * m22), m13 * (m20 * m31 - m21 * m30) + m10 * (m21 * m33 - m23 * m31) + m11 * (m23 * m30 - m20 * m33), m23 * (m00 * m31 - m01 * m30) + m20 * (m01 * m33 - m03 * m31) + m21 * (m03 * m30 - m00 * m33), m33 * (m00 * m11 - m01 * m10) + m30 * (m01 * m13 - m03 * m11) + m31 * (m03 * m10 - m00 * m13), m03 * (m11 * m20 - m10 * m21) + m00 * (m13 * m21 - m11 * m23) + m01 * (m10 * m23 - m13 * m20), m10 * (m22 * m31 - m21 * m32) + m11 * (m20 * m32 - m22 * m30) + m12 * (m21 * m30 - m20 * m31), m20 * (m02 * m31 - m01 * m32) + m21 * (m00 * m32 - m02 * m30) + m22 * (m01 * m30 - m00 * m31), m30 * (m02 * m11 - m01 * m12) + m31 * (m00 * m12 - m02 * m10) + m32 * (m01 * m10 - m00 * m11), m00 * (m11 * m22 - m12 * m21) + m01 * (m12 * m20 - m10 * m22) + m02 * (m10 * m21 - m11 * m20));
            mul(f);
            return;
        }
    }

    public final float determinant()
    {
        return ((((m00 * m11 - m01 * m10) * (m22 * m33 - m23 * m32) - (m00 * m12 - m02 * m10) * (m21 * m33 - m23 * m31)) + (m00 * m13 - m03 * m10) * (m21 * m32 - m22 * m31) + (m01 * m12 - m02 * m11) * (m20 * m33 - m23 * m30)) - (m01 * m13 - m03 * m11) * (m20 * m32 - m22 * m30)) + (m02 * m13 - m03 * m12) * (m20 * m31 - m21 * m30);
    }

    public final void set(com.maddox.JGP.Matrix3f matrix3f)
    {
        m00 = matrix3f.m00;
        m01 = matrix3f.m01;
        m02 = matrix3f.m02;
        m03 = 0.0F;
        m10 = matrix3f.m10;
        m11 = matrix3f.m11;
        m12 = matrix3f.m12;
        m13 = 0.0F;
        m20 = matrix3f.m20;
        m21 = matrix3f.m21;
        m22 = matrix3f.m22;
        m23 = 0.0F;
        m30 = 0.0F;
        m31 = 0.0F;
        m32 = 0.0F;
        m33 = 1.0F;
    }

    public final void set(com.maddox.JGP.Matrix3d matrix3d)
    {
        m00 = (float)matrix3d.m00;
        m01 = (float)matrix3d.m01;
        m02 = (float)matrix3d.m02;
        m03 = 0.0F;
        m10 = (float)matrix3d.m10;
        m11 = (float)matrix3d.m11;
        m12 = (float)matrix3d.m12;
        m13 = 0.0F;
        m20 = (float)matrix3d.m20;
        m21 = (float)matrix3d.m21;
        m22 = (float)matrix3d.m22;
        m23 = 0.0F;
        m30 = 0.0F;
        m31 = 0.0F;
        m32 = 0.0F;
        m33 = 1.0F;
    }

    public final void set(float f)
    {
        m00 = f;
        m01 = 0.0F;
        m02 = 0.0F;
        m03 = 0.0F;
        m10 = 0.0F;
        m11 = f;
        m12 = 0.0F;
        m13 = 0.0F;
        m20 = 0.0F;
        m21 = 0.0F;
        m22 = f;
        m23 = 0.0F;
        m30 = 0.0F;
        m31 = 0.0F;
        m32 = 0.0F;
        m33 = 1.0F;
    }

    public final void set(float af[])
    {
        m00 = af[0];
        m01 = af[1];
        m02 = af[2];
        m03 = af[3];
        m10 = af[4];
        m11 = af[5];
        m12 = af[6];
        m13 = af[7];
        m20 = af[8];
        m21 = af[9];
        m22 = af[10];
        m23 = af[11];
        m30 = af[12];
        m31 = af[13];
        m32 = af[14];
        m33 = af[15];
    }

    public final void set(com.maddox.JGP.Tuple3f tuple3f)
    {
        setIdentity();
        setTranslation(tuple3f);
    }

    public final void set(float f, com.maddox.JGP.Tuple3f tuple3f)
    {
        set(f);
        setTranslation(tuple3f);
    }

    public final void set(com.maddox.JGP.Tuple3f tuple3f, float f)
    {
        m00 = f;
        m01 = 0.0F;
        m02 = 0.0F;
        m03 = f * tuple3f.x;
        m10 = 0.0F;
        m11 = f;
        m12 = 0.0F;
        m13 = f * tuple3f.y;
        m20 = 0.0F;
        m21 = 0.0F;
        m22 = f;
        m23 = f * tuple3f.z;
        m30 = 0.0F;
        m31 = 0.0F;
        m32 = 0.0F;
        m33 = 1.0F;
    }

    public final void set(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Tuple3f tuple3f, float f)
    {
        setRotationScale(matrix3f);
        mulRotationScale(f);
        setTranslation(tuple3f);
        m33 = 1.0F;
    }

    public final void set(com.maddox.JGP.Matrix3d matrix3d, com.maddox.JGP.Tuple3d tuple3d, double d)
    {
        setRotationScale(matrix3d);
        mulRotationScale((float)d);
        setTranslation(tuple3d);
        m33 = 1.0F;
    }

    public void setTranslation(com.maddox.JGP.Tuple3f tuple3f)
    {
        m03 = tuple3f.x;
        m13 = tuple3f.y;
        m23 = tuple3f.z;
    }

    public final void rotX(float f)
    {
        float f1 = (float)java.lang.Math.cos(f);
        float f2 = (float)java.lang.Math.sin(f);
        m00 = 1.0F;
        m01 = 0.0F;
        m02 = 0.0F;
        m03 = 0.0F;
        m10 = 0.0F;
        m11 = f1;
        m12 = -f2;
        m13 = 0.0F;
        m20 = 0.0F;
        m21 = f2;
        m22 = f1;
        m23 = 0.0F;
        m30 = 0.0F;
        m31 = 0.0F;
        m32 = 0.0F;
        m33 = 1.0F;
    }

    public final void rotY(float f)
    {
        float f1 = (float)java.lang.Math.cos(f);
        float f2 = (float)java.lang.Math.sin(f);
        m00 = f1;
        m01 = 0.0F;
        m02 = f2;
        m03 = 0.0F;
        m10 = 0.0F;
        m11 = 1.0F;
        m12 = 0.0F;
        m13 = 0.0F;
        m20 = -f2;
        m21 = 0.0F;
        m22 = f1;
        m23 = 0.0F;
        m30 = 0.0F;
        m31 = 0.0F;
        m32 = 0.0F;
        m33 = 1.0F;
    }

    public final void rotZ(float f)
    {
        float f1 = (float)java.lang.Math.cos(f);
        float f2 = (float)java.lang.Math.sin(f);
        m00 = f1;
        m01 = -f2;
        m02 = 0.0F;
        m03 = 0.0F;
        m10 = f2;
        m11 = f1;
        m12 = 0.0F;
        m13 = 0.0F;
        m20 = 0.0F;
        m21 = 0.0F;
        m22 = 1.0F;
        m23 = 0.0F;
        m30 = 0.0F;
        m31 = 0.0F;
        m32 = 0.0F;
        m33 = 1.0F;
    }

    public final void mul(float f)
    {
        m00 *= f;
        m01 *= f;
        m02 *= f;
        m03 *= f;
        m10 *= f;
        m11 *= f;
        m12 *= f;
        m13 *= f;
        m20 *= f;
        m21 *= f;
        m22 *= f;
        m23 *= f;
        m30 *= f;
        m31 *= f;
        m32 *= f;
        m33 *= f;
    }

    public final void mul(float f, com.maddox.JGP.Matrix4f matrix4f)
    {
        set(matrix4f);
        mul(f);
    }

    public final void mul(com.maddox.JGP.Matrix4f matrix4f)
    {
        mul(this, matrix4f);
    }

    public final void mul(com.maddox.JGP.Matrix4f matrix4f, com.maddox.JGP.Matrix4f matrix4f1)
    {
        set(matrix4f.m00 * matrix4f1.m00 + matrix4f.m01 * matrix4f1.m10 + matrix4f.m02 * matrix4f1.m20 + matrix4f.m03 * matrix4f1.m30, matrix4f.m00 * matrix4f1.m01 + matrix4f.m01 * matrix4f1.m11 + matrix4f.m02 * matrix4f1.m21 + matrix4f.m03 * matrix4f1.m31, matrix4f.m00 * matrix4f1.m02 + matrix4f.m01 * matrix4f1.m12 + matrix4f.m02 * matrix4f1.m22 + matrix4f.m03 * matrix4f1.m32, matrix4f.m00 * matrix4f1.m03 + matrix4f.m01 * matrix4f1.m13 + matrix4f.m02 * matrix4f1.m23 + matrix4f.m03 * matrix4f1.m33, matrix4f.m10 * matrix4f1.m00 + matrix4f.m11 * matrix4f1.m10 + matrix4f.m12 * matrix4f1.m20 + matrix4f.m13 * matrix4f1.m30, matrix4f.m10 * matrix4f1.m01 + matrix4f.m11 * matrix4f1.m11 + matrix4f.m12 * matrix4f1.m21 + matrix4f.m13 * matrix4f1.m31, matrix4f.m10 * matrix4f1.m02 + matrix4f.m11 * matrix4f1.m12 + matrix4f.m12 * matrix4f1.m22 + matrix4f.m13 * matrix4f1.m32, matrix4f.m10 * matrix4f1.m03 + matrix4f.m11 * matrix4f1.m13 + matrix4f.m12 * matrix4f1.m23 + matrix4f.m13 * matrix4f1.m33, matrix4f.m20 * matrix4f1.m00 + matrix4f.m21 * matrix4f1.m10 + matrix4f.m22 * matrix4f1.m20 + matrix4f.m23 * matrix4f1.m30, matrix4f.m20 * matrix4f1.m01 + matrix4f.m21 * matrix4f1.m11 + matrix4f.m22 * matrix4f1.m21 + matrix4f.m23 * matrix4f1.m31, matrix4f.m20 * matrix4f1.m02 + matrix4f.m21 * matrix4f1.m12 + matrix4f.m22 * matrix4f1.m22 + matrix4f.m23 * matrix4f1.m32, matrix4f.m20 * matrix4f1.m03 + matrix4f.m21 * matrix4f1.m13 + matrix4f.m22 * matrix4f1.m23 + matrix4f.m23 * matrix4f1.m33, matrix4f.m30 * matrix4f1.m00 + matrix4f.m31 * matrix4f1.m10 + matrix4f.m32 * matrix4f1.m20 + matrix4f.m33 * matrix4f1.m30, matrix4f.m30 * matrix4f1.m01 + matrix4f.m31 * matrix4f1.m11 + matrix4f.m32 * matrix4f1.m21 + matrix4f.m33 * matrix4f1.m31, matrix4f.m30 * matrix4f1.m02 + matrix4f.m31 * matrix4f1.m12 + matrix4f.m32 * matrix4f1.m22 + matrix4f.m33 * matrix4f1.m32, matrix4f.m30 * matrix4f1.m03 + matrix4f.m31 * matrix4f1.m13 + matrix4f.m32 * matrix4f1.m23 + matrix4f.m33 * matrix4f1.m33);
    }

    public final void mulTransposeBoth(com.maddox.JGP.Matrix4f matrix4f, com.maddox.JGP.Matrix4f matrix4f1)
    {
        mul(matrix4f1, matrix4f);
        transpose();
    }

    public final void mulTransposeRight(com.maddox.JGP.Matrix4f matrix4f, com.maddox.JGP.Matrix4f matrix4f1)
    {
        set(matrix4f.m00 * matrix4f1.m00 + matrix4f.m01 * matrix4f1.m01 + matrix4f.m02 * matrix4f1.m02 + matrix4f.m03 * matrix4f1.m03, matrix4f.m00 * matrix4f1.m10 + matrix4f.m01 * matrix4f1.m11 + matrix4f.m02 * matrix4f1.m12 + matrix4f.m03 * matrix4f1.m13, matrix4f.m00 * matrix4f1.m20 + matrix4f.m01 * matrix4f1.m21 + matrix4f.m02 * matrix4f1.m22 + matrix4f.m03 * matrix4f1.m23, matrix4f.m00 * matrix4f1.m30 + matrix4f.m01 * matrix4f1.m31 + matrix4f.m02 * matrix4f1.m32 + matrix4f.m03 * matrix4f1.m33, matrix4f.m10 * matrix4f1.m00 + matrix4f.m11 * matrix4f1.m01 + matrix4f.m12 * matrix4f1.m02 + matrix4f.m13 * matrix4f1.m03, matrix4f.m10 * matrix4f1.m10 + matrix4f.m11 * matrix4f1.m11 + matrix4f.m12 * matrix4f1.m12 + matrix4f.m13 * matrix4f1.m13, matrix4f.m10 * matrix4f1.m20 + matrix4f.m11 * matrix4f1.m21 + matrix4f.m12 * matrix4f1.m22 + matrix4f.m13 * matrix4f1.m23, matrix4f.m10 * matrix4f1.m30 + matrix4f.m11 * matrix4f1.m31 + matrix4f.m12 * matrix4f1.m32 + matrix4f.m13 * matrix4f1.m33, matrix4f.m20 * matrix4f1.m00 + matrix4f.m21 * matrix4f1.m01 + matrix4f.m22 * matrix4f1.m02 + matrix4f.m23 * matrix4f1.m03, matrix4f.m20 * matrix4f1.m10 + matrix4f.m21 * matrix4f1.m11 + matrix4f.m22 * matrix4f1.m12 + matrix4f.m23 * matrix4f1.m13, matrix4f.m20 * matrix4f1.m20 + matrix4f.m21 * matrix4f1.m21 + matrix4f.m22 * matrix4f1.m22 + matrix4f.m23 * matrix4f1.m23, matrix4f.m20 * matrix4f1.m30 + matrix4f.m21 * matrix4f1.m31 + matrix4f.m22 * matrix4f1.m32 + matrix4f.m23 * matrix4f1.m33, matrix4f.m30 * matrix4f1.m00 + matrix4f.m31 * matrix4f1.m01 + matrix4f.m32 * matrix4f1.m02 + matrix4f.m33 * matrix4f1.m03, matrix4f.m30 * matrix4f1.m10 + matrix4f.m31 * matrix4f1.m11 + matrix4f.m32 * matrix4f1.m12 + matrix4f.m33 * matrix4f1.m13, matrix4f.m30 * matrix4f1.m20 + matrix4f.m31 * matrix4f1.m21 + matrix4f.m32 * matrix4f1.m22 + matrix4f.m33 * matrix4f1.m23, matrix4f.m30 * matrix4f1.m30 + matrix4f.m31 * matrix4f1.m31 + matrix4f.m32 * matrix4f1.m32 + matrix4f.m33 * matrix4f1.m33);
    }

    public final void mulTransposeLeft(com.maddox.JGP.Matrix4f matrix4f, com.maddox.JGP.Matrix4f matrix4f1)
    {
        set(matrix4f.m00 * matrix4f1.m00 + matrix4f.m10 * matrix4f1.m10 + matrix4f.m20 * matrix4f1.m20 + matrix4f.m30 * matrix4f1.m30, matrix4f.m00 * matrix4f1.m01 + matrix4f.m10 * matrix4f1.m11 + matrix4f.m20 * matrix4f1.m21 + matrix4f.m30 * matrix4f1.m31, matrix4f.m00 * matrix4f1.m02 + matrix4f.m10 * matrix4f1.m12 + matrix4f.m20 * matrix4f1.m22 + matrix4f.m30 * matrix4f1.m32, matrix4f.m00 * matrix4f1.m03 + matrix4f.m10 * matrix4f1.m13 + matrix4f.m20 * matrix4f1.m23 + matrix4f.m30 * matrix4f1.m33, matrix4f.m01 * matrix4f1.m00 + matrix4f.m11 * matrix4f1.m10 + matrix4f.m21 * matrix4f1.m20 + matrix4f.m31 * matrix4f1.m30, matrix4f.m01 * matrix4f1.m01 + matrix4f.m11 * matrix4f1.m11 + matrix4f.m21 * matrix4f1.m21 + matrix4f.m31 * matrix4f1.m31, matrix4f.m01 * matrix4f1.m02 + matrix4f.m11 * matrix4f1.m12 + matrix4f.m21 * matrix4f1.m22 + matrix4f.m31 * matrix4f1.m32, matrix4f.m01 * matrix4f1.m03 + matrix4f.m11 * matrix4f1.m13 + matrix4f.m21 * matrix4f1.m23 + matrix4f.m31 * matrix4f1.m33, matrix4f.m02 * matrix4f1.m00 + matrix4f.m12 * matrix4f1.m10 + matrix4f.m22 * matrix4f1.m20 + matrix4f.m32 * matrix4f1.m30, matrix4f.m02 * matrix4f1.m01 + matrix4f.m12 * matrix4f1.m11 + matrix4f.m22 * matrix4f1.m21 + matrix4f.m32 * matrix4f1.m31, matrix4f.m02 * matrix4f1.m02 + matrix4f.m12 * matrix4f1.m12 + matrix4f.m22 * matrix4f1.m22 + matrix4f.m32 * matrix4f1.m32, matrix4f.m02 * matrix4f1.m03 + matrix4f.m12 * matrix4f1.m13 + matrix4f.m22 * matrix4f1.m23 + matrix4f.m32 * matrix4f1.m33, matrix4f.m03 * matrix4f1.m00 + matrix4f.m13 * matrix4f1.m10 + matrix4f.m23 * matrix4f1.m20 + matrix4f.m33 * matrix4f1.m30, matrix4f.m03 * matrix4f1.m01 + matrix4f.m13 * matrix4f1.m11 + matrix4f.m23 * matrix4f1.m21 + matrix4f.m33 * matrix4f1.m31, matrix4f.m03 * matrix4f1.m02 + matrix4f.m13 * matrix4f1.m12 + matrix4f.m23 * matrix4f1.m22 + matrix4f.m33 * matrix4f1.m32, matrix4f.m03 * matrix4f1.m03 + matrix4f.m13 * matrix4f1.m13 + matrix4f.m23 * matrix4f1.m23 + matrix4f.m33 * matrix4f1.m33);
    }

    public boolean equals(com.maddox.JGP.Matrix4f matrix4f)
    {
        return matrix4f != null && m00 == matrix4f.m00 && m01 == matrix4f.m01 && m02 == matrix4f.m02 && m03 == matrix4f.m03 && m10 == matrix4f.m10 && m11 == matrix4f.m11 && m12 == matrix4f.m12 && m13 == matrix4f.m13 && m20 == matrix4f.m20 && m21 == matrix4f.m21 && m22 == matrix4f.m22 && m23 == matrix4f.m23 && m30 == matrix4f.m30 && m31 == matrix4f.m31 && m32 == matrix4f.m32 && m33 == matrix4f.m33;
    }

    public boolean equals(java.lang.Object obj)
    {
        return obj != null && (obj instanceof com.maddox.JGP.Matrix4f) && equals((com.maddox.JGP.Matrix4f)obj);
    }

    public boolean epsilonEquals(com.maddox.JGP.Matrix4f matrix4f, float f)
    {
        return java.lang.Math.abs(m00 - matrix4f.m00) <= f && java.lang.Math.abs(m01 - matrix4f.m01) <= f && java.lang.Math.abs(m02 - matrix4f.m02) <= f && java.lang.Math.abs(m03 - matrix4f.m03) <= f && java.lang.Math.abs(m10 - matrix4f.m10) <= f && java.lang.Math.abs(m11 - matrix4f.m11) <= f && java.lang.Math.abs(m12 - matrix4f.m12) <= f && java.lang.Math.abs(m13 - matrix4f.m13) <= f && java.lang.Math.abs(m20 - matrix4f.m20) <= f && java.lang.Math.abs(m21 - matrix4f.m21) <= f && java.lang.Math.abs(m22 - matrix4f.m22) <= f && java.lang.Math.abs(m23 - matrix4f.m23) <= f && java.lang.Math.abs(m30 - matrix4f.m30) <= f && java.lang.Math.abs(m31 - matrix4f.m31) <= f && java.lang.Math.abs(m32 - matrix4f.m32) <= f && java.lang.Math.abs(m33 - matrix4f.m33) <= f;
    }

    public int hashCode()
    {
        return java.lang.Float.floatToIntBits(m00) ^ java.lang.Float.floatToIntBits(m01) ^ java.lang.Float.floatToIntBits(m02) ^ java.lang.Float.floatToIntBits(m03) ^ java.lang.Float.floatToIntBits(m10) ^ java.lang.Float.floatToIntBits(m11) ^ java.lang.Float.floatToIntBits(m12) ^ java.lang.Float.floatToIntBits(m13) ^ java.lang.Float.floatToIntBits(m20) ^ java.lang.Float.floatToIntBits(m21) ^ java.lang.Float.floatToIntBits(m22) ^ java.lang.Float.floatToIntBits(m23) ^ java.lang.Float.floatToIntBits(m30) ^ java.lang.Float.floatToIntBits(m31) ^ java.lang.Float.floatToIntBits(m32) ^ java.lang.Float.floatToIntBits(m33);
    }

    public final void transform(com.maddox.JGP.Tuple4f tuple4f, com.maddox.JGP.Tuple4f tuple4f1)
    {
        tuple4f1.set(m00 * tuple4f.x + m01 * tuple4f.y + m02 * tuple4f.z + m03 * tuple4f.w, m10 * tuple4f.x + m11 * tuple4f.y + m12 * tuple4f.z + m13 * tuple4f.w, m20 * tuple4f.x + m21 * tuple4f.y + m22 * tuple4f.z + m23 * tuple4f.w, m30 * tuple4f.x + m31 * tuple4f.y + m32 * tuple4f.z + m33 * tuple4f.w);
    }

    public final void transform(com.maddox.JGP.Tuple4f tuple4f)
    {
        transform(tuple4f, tuple4f);
    }

    public final void transform(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1)
    {
        point3f1.set(m00 * point3f.x + m01 * point3f.y + m02 * point3f.z + m03, m10 * point3f.x + m11 * point3f.y + m12 * point3f.z + m13, m20 * point3f.x + m21 * point3f.y + m22 * point3f.z + m23);
    }

    public final void transform(com.maddox.JGP.Point3f point3f)
    {
        transform(point3f, point3f);
    }

    public final void transform(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        tuple3f1.set(m00 * tuple3f.x + m01 * tuple3f.y + m02 * tuple3f.z, m10 * tuple3f.x + m11 * tuple3f.y + m12 * tuple3f.z, m20 * tuple3f.x + m21 * tuple3f.y + m22 * tuple3f.z);
    }

    public final void transform(com.maddox.JGP.Tuple3f tuple3f)
    {
        transform(tuple3f, tuple3f);
    }

    public final void setRotation(com.maddox.JGP.Matrix3d matrix3d)
    {
        float f = SVD(null);
        setRotationScale(matrix3d);
        mulRotationScale(f);
    }

    public final void setRotation(com.maddox.JGP.Matrix3f matrix3f)
    {
        float f = SVD(null);
        setRotationScale(matrix3f);
        mulRotationScale(f);
    }

    public final void setRotation(com.maddox.JGP.Quat4f quat4f)
    {
        float f = SVD(null, null);
        float f1 = m03;
        float f2 = m13;
        float f3 = m23;
        float f4 = m30;
        float f5 = m31;
        float f6 = m32;
        float f7 = m33;
        set(quat4f);
        mulRotationScale(f);
        m03 = f1;
        m13 = f2;
        m23 = f3;
        m30 = f4;
        m31 = f5;
        m32 = f6;
        m33 = f7;
    }

    public final void setRotation(com.maddox.JGP.Quat4d quat4d)
    {
        float f = SVD(null, null);
        float f1 = m03;
        float f2 = m13;
        float f3 = m23;
        float f4 = m30;
        float f5 = m31;
        float f6 = m32;
        float f7 = m33;
        set(quat4d);
        mulRotationScale(f);
        m03 = f1;
        m13 = f2;
        m23 = f3;
        m30 = f4;
        m31 = f5;
        m32 = f6;
        m33 = f7;
    }

    public final void setRotation(com.maddox.JGP.AxisAngle4f axisangle4f)
    {
        float f = SVD(null, null);
        float f1 = m03;
        float f2 = m13;
        float f3 = m23;
        float f4 = m30;
        float f5 = m31;
        float f6 = m32;
        float f7 = m33;
        set(axisangle4f);
        mulRotationScale(f);
        m03 = f1;
        m13 = f2;
        m23 = f3;
        m30 = f4;
        m31 = f5;
        m32 = f6;
        m33 = f7;
    }

    public final void setZero()
    {
        m00 = 0.0F;
        m01 = 0.0F;
        m02 = 0.0F;
        m03 = 0.0F;
        m10 = 0.0F;
        m11 = 0.0F;
        m12 = 0.0F;
        m13 = 0.0F;
        m20 = 0.0F;
        m21 = 0.0F;
        m22 = 0.0F;
        m23 = 0.0F;
        m30 = 0.0F;
        m31 = 0.0F;
        m32 = 0.0F;
        m33 = 0.0F;
    }

    public final void negate()
    {
        m00 = -m00;
        m01 = -m01;
        m02 = -m02;
        m03 = -m03;
        m10 = -m10;
        m11 = -m11;
        m12 = -m12;
        m13 = -m13;
        m20 = -m20;
        m21 = -m21;
        m22 = -m22;
        m23 = -m23;
        m30 = -m30;
        m31 = -m31;
        m32 = -m32;
        m33 = -m33;
    }

    public final void negate(com.maddox.JGP.Matrix4f matrix4f)
    {
        set(matrix4f);
        negate();
    }

    private void set(float f, float f1, float f2, float f3, float f4, float f5, float f6, 
            float f7, float f8, float f9, float f10, float f11, float f12, float f13, 
            float f14, float f15)
    {
        m00 = f;
        m01 = f1;
        m02 = f2;
        m03 = f3;
        m10 = f4;
        m11 = f5;
        m12 = f6;
        m13 = f7;
        m20 = f8;
        m21 = f9;
        m22 = f10;
        m23 = f11;
        m30 = f12;
        m31 = f13;
        m32 = f14;
        m33 = f15;
    }

    private float SVD(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Matrix4f matrix4f)
    {
        float f = (float)java.lang.Math.sqrt((double)(m00 * m00 + m10 * m10 + m20 * m20 + m01 * m01 + m11 * m11 + m21 * m21 + m02 * m02 + m12 * m12 + m22 * m22) / 3D);
        float f1 = f != 0.0F ? 1.0F / f : 0.0F;
        if(matrix3f != null)
        {
            getRotationScale(matrix3f);
            matrix3f.mul(f1);
        }
        if(matrix4f != null)
        {
            if(matrix4f != this)
                matrix4f.setRotationScale(this);
            matrix4f.mulRotationScale(f1);
        }
        return f;
    }

    private float SVD(com.maddox.JGP.Matrix3d matrix3d)
    {
        float f = (float)java.lang.Math.sqrt((double)(m00 * m00 + m10 * m10 + m20 * m20 + m01 * m01 + m11 * m11 + m21 * m21 + m02 * m02 + m12 * m12 + m22 * m22) / 3D);
        float f1 = f != 0.0F ? 1.0F / f : 0.0F;
        if(matrix3d != null)
        {
            getRotationScale(matrix3d);
            matrix3d.mul(f1);
        }
        return f;
    }

    private void mulRotationScale(float f)
    {
        m00 *= f;
        m01 *= f;
        m02 *= f;
        m10 *= f;
        m11 *= f;
        m12 *= f;
        m20 *= f;
        m21 *= f;
        m22 *= f;
    }

    private void setRotationScale(com.maddox.JGP.Matrix4f matrix4f)
    {
        m00 = matrix4f.m00;
        m01 = matrix4f.m01;
        m02 = matrix4f.m02;
        m10 = matrix4f.m10;
        m11 = matrix4f.m11;
        m12 = matrix4f.m12;
        m20 = matrix4f.m20;
        m21 = matrix4f.m21;
        m22 = matrix4f.m22;
    }

    private void setRotationScale(com.maddox.JGP.Matrix3d matrix3d)
    {
        m00 = (float)matrix3d.m00;
        m01 = (float)matrix3d.m01;
        m02 = (float)matrix3d.m02;
        m10 = (float)matrix3d.m10;
        m11 = (float)matrix3d.m11;
        m12 = (float)matrix3d.m12;
        m20 = (float)matrix3d.m20;
        m21 = (float)matrix3d.m21;
        m22 = (float)matrix3d.m22;
    }

    private void setTranslation(com.maddox.JGP.Tuple3d tuple3d)
    {
        m03 = (float)tuple3d.x;
        m13 = (float)tuple3d.y;
        m23 = (float)tuple3d.z;
    }

    private final void getRotationScale(com.maddox.JGP.Matrix3d matrix3d)
    {
        matrix3d.m00 = m00;
        matrix3d.m01 = m01;
        matrix3d.m02 = m02;
        matrix3d.m10 = m10;
        matrix3d.m11 = m11;
        matrix3d.m12 = m12;
        matrix3d.m20 = m20;
        matrix3d.m21 = m21;
        matrix3d.m22 = m22;
    }

    private void setFromQuat(double d, double d1, double d2, double d3)
    {
        double d4 = d * d + d1 * d1 + d2 * d2 + d3 * d3;
        double d5 = d4 <= 0.0D ? 0.0D : 2D / d4;
        double d6 = d * d5;
        double d7 = d1 * d5;
        double d8 = d2 * d5;
        double d9 = d3 * d6;
        double d10 = d3 * d7;
        double d11 = d3 * d8;
        double d12 = d * d6;
        double d13 = d * d7;
        double d14 = d * d8;
        double d15 = d1 * d7;
        double d16 = d1 * d8;
        double d17 = d2 * d8;
        setIdentity();
        m00 = (float)(1.0D - (d15 + d17));
        m01 = (float)(d13 - d11);
        m02 = (float)(d14 + d10);
        m10 = (float)(d13 + d11);
        m11 = (float)(1.0D - (d12 + d17));
        m12 = (float)(d16 - d9);
        m20 = (float)(d14 - d10);
        m21 = (float)(d16 + d9);
        m22 = (float)(1.0D - (d12 + d15));
    }

    private void setFromAxisAngle(double d, double d1, double d2, double d3)
    {
        double d4 = java.lang.Math.sqrt(d * d + d1 * d1 + d2 * d2);
        d4 = 1.0D / d4;
        d *= d4;
        d1 *= d4;
        d2 *= d4;
        double d5 = java.lang.Math.cos(d3);
        double d6 = java.lang.Math.sin(d3);
        double d7 = 1.0D - d5;
        m00 = (float)(d5 + d * d * d7);
        m11 = (float)(d5 + d1 * d1 * d7);
        m22 = (float)(d5 + d2 * d2 * d7);
        double d8 = d * d1 * d7;
        double d9 = d2 * d6;
        m01 = (float)(d8 - d9);
        m10 = (float)(d8 + d9);
        d8 = d * d2 * d7;
        d9 = d1 * d6;
        m02 = (float)(d8 + d9);
        m20 = (float)(d8 - d9);
        d8 = d1 * d2 * d7;
        d9 = d * d6;
        m12 = (float)(d8 - d9);
        m21 = (float)(d8 + d9);
    }

    public void setEulers(float af[])
    {
        setEulers(af[0], af[1], af[2]);
    }

    public void getEulers(float af[])
    {
        float f3 = -m20;
        float f2 = (float)java.lang.Math.sqrt(1.0F - f3 * f3);
        float f;
        float f1;
        float f4;
        float f5;
        if(f2 > 0.001F)
        {
            f4 = m22;
            f5 = m21;
            f = m00;
            f1 = m10;
        } else
        {
            f2 = 0.0F;
            f = 1.0F;
            f1 = 0.0F;
            f4 = m11;
            f5 = -m12;
        }
        af[0] = (float)java.lang.Math.atan2(f1, f);
        af[1] = (float)java.lang.Math.atan2(f3, f2);
        af[2] = (float)java.lang.Math.atan2(f5, f4);
    }

    public void setEulers(float f, float f1, float f2)
    {
        float f3 = (float)java.lang.Math.cos(f);
        float f4 = (float)java.lang.Math.sin(f);
        float f5 = (float)java.lang.Math.cos(f1);
        float f6 = (float)java.lang.Math.sin(f1);
        float f7 = (float)java.lang.Math.cos(f2);
        float f8 = (float)java.lang.Math.sin(f2);
        m00 = f3 * f5;
        m01 = -f4 * f7 + f3 * f6 * f8;
        m02 = f4 * f8 + f3 * f6 * f7;
        m10 = f4 * f5;
        m11 = f3 * f7 + f4 * f6 * f8;
        m12 = -f3 * f8 + f4 * f6 * f7;
        m20 = -f6;
        m21 = f5 * f8;
        m22 = f5 * f7;
        m03 = 0.0F;
        m13 = 0.0F;
        m23 = 0.0F;
        m30 = 0.0F;
        m31 = 0.0F;
        m32 = 0.0F;
        m33 = 1.0F;
    }

    public void setEulersInv(float f, float f1, float f2)
    {
        float f3 = (float)java.lang.Math.cos(f);
        float f4 = (float)java.lang.Math.sin(f);
        float f5 = (float)java.lang.Math.cos(f1);
        float f6 = (float)java.lang.Math.sin(f1);
        float f7 = (float)java.lang.Math.cos(f2);
        float f8 = (float)java.lang.Math.sin(f2);
        m00 = f5 * f3;
        m01 = f5 * f4;
        m02 = -f6;
        m10 = f8 * f6 * f3 - f7 * f4;
        m11 = f8 * f6 * f4 + f7 * f3;
        m12 = f8 * f5;
        m20 = f7 * f6 * f3 + f8 * f4;
        m21 = f7 * f6 * f4 - f8 * f3;
        m22 = f7 * f5;
        m03 = 0.0F;
        m13 = 0.0F;
        m23 = 0.0F;
        m30 = 0.0F;
        m31 = 0.0F;
        m32 = 0.0F;
        m33 = 1.0F;
    }

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
}
