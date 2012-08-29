// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Matrix4d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Quat4f, Quat4d, Tuple3d, Matrix3f, 
//            Matrix3d, Tuple4d, AxisAngle4d, AxisAngle4f, 
//            Tuple3f, Matrix4f, Tuple4f, Point3d, 
//            Point3f

public class Matrix4d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Matrix4d(double d, double d1, double d2, double d3, double d4, double d5, double d6, 
            double d7, double d8, double d9, double d10, double d11, double d12, double d13, 
            double d14, double d15)
    {
        set(d, d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15);
    }

    public Matrix4d(double ad[])
    {
        set(ad);
    }

    public Matrix4d(com.maddox.JGP.Quat4d quat4d, com.maddox.JGP.Tuple3d tuple3d, double d)
    {
        set(quat4d, tuple3d, d);
    }

    public Matrix4d(com.maddox.JGP.Quat4f quat4f, com.maddox.JGP.Tuple3d tuple3d, double d)
    {
        set(quat4f, tuple3d, d);
    }

    public Matrix4d(com.maddox.JGP.Matrix4d matrix4d)
    {
        set(matrix4d);
    }

    public Matrix4d(com.maddox.JGP.Matrix4f matrix4f)
    {
        set(matrix4f);
    }

    public Matrix4d(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Tuple3d tuple3d, double d)
    {
        set(matrix3f);
        mulRotationScale(d);
        setTranslation(tuple3d);
        m33 = 1.0D;
    }

    public Matrix4d(com.maddox.JGP.Matrix3d matrix3d, com.maddox.JGP.Tuple3d tuple3d, double d)
    {
        set(matrix3d, tuple3d, d);
    }

    public Matrix4d()
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
        m00 = 1.0D;
        m01 = 0.0D;
        m02 = 0.0D;
        m03 = 0.0D;
        m10 = 0.0D;
        m11 = 1.0D;
        m12 = 0.0D;
        m13 = 0.0D;
        m20 = 0.0D;
        m21 = 0.0D;
        m22 = 1.0D;
        m23 = 0.0D;
        m30 = 0.0D;
        m31 = 0.0D;
        m32 = 0.0D;
        m33 = 1.0D;
    }

    public final void setElement(int i, int j, double d)
    {
        if(i == 0)
        {
            if(j == 0)
                m00 = d;
            else
            if(j == 1)
                m01 = d;
            else
            if(j == 2)
                m02 = d;
            else
                m03 = d;
        } else
        if(i == 1)
        {
            if(j == 0)
                m10 = d;
            else
            if(j == 1)
                m11 = d;
            else
            if(j == 2)
                m12 = d;
            else
                m13 = d;
        } else
        if(i == 2)
        {
            if(j == 0)
                m20 = d;
            else
            if(j == 1)
                m21 = d;
            else
            if(j == 2)
                m22 = d;
            else
                m23 = d;
        } else
        if(j == 0)
            m30 = d;
        else
        if(j == 1)
            m31 = d;
        else
        if(j == 2)
            m32 = d;
        else
            m33 = d;
    }

    public final double getElement(int i, int j)
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

    public final void get(com.maddox.JGP.Matrix3d matrix3d)
    {
        SVD(matrix3d, null);
    }

    public final void get(com.maddox.JGP.Matrix3f matrix3f)
    {
        SVD(matrix3f);
    }

    public final double get(com.maddox.JGP.Matrix3d matrix3d, com.maddox.JGP.Tuple3d tuple3d)
    {
        get(tuple3d);
        return SVD(matrix3d, null);
    }

    public final double get(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Tuple3d tuple3d)
    {
        get(tuple3d);
        return (double)SVD(matrix3f);
    }

    public final void get(com.maddox.JGP.Quat4f quat4f)
    {
        quat4f.set(this);
        quat4f.normalize();
    }

    public final void get(com.maddox.JGP.Quat4d quat4d)
    {
        quat4d.set(this);
        quat4d.normalize();
    }

    public final void get(com.maddox.JGP.Tuple3d tuple3d)
    {
        tuple3d.x = m03;
        tuple3d.y = m13;
        tuple3d.z = m23;
    }

    public final void getRotationScale(com.maddox.JGP.Matrix3f matrix3f)
    {
        matrix3f.m00 = (float)m00;
        matrix3f.m01 = (float)m01;
        matrix3f.m02 = (float)m02;
        matrix3f.m10 = (float)m10;
        matrix3f.m11 = (float)m11;
        matrix3f.m12 = (float)m12;
        matrix3f.m20 = (float)m20;
        matrix3f.m21 = (float)m21;
        matrix3f.m22 = (float)m22;
    }

    public final void getRotationScale(com.maddox.JGP.Matrix3d matrix3d)
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

    public final double getScale()
    {
        return (double)SVD(null);
    }

    public final void setRotationScale(com.maddox.JGP.Matrix3d matrix3d)
    {
        m00 = matrix3d.m00;
        m01 = matrix3d.m01;
        m02 = matrix3d.m02;
        m10 = matrix3d.m10;
        m11 = matrix3d.m11;
        m12 = matrix3d.m12;
        m20 = matrix3d.m20;
        m21 = matrix3d.m21;
        m22 = matrix3d.m22;
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

    public final void setScale(double d)
    {
        SVD(null, this);
        mulRotationScale(d);
    }

    public final void setRow(int i, double d, double d1, double d2, 
            double d3)
    {
        if(i == 0)
        {
            m00 = d;
            m01 = d1;
            m02 = d2;
            m03 = d3;
        } else
        if(i == 1)
        {
            m10 = d;
            m11 = d1;
            m12 = d2;
            m13 = d3;
        } else
        if(i == 2)
        {
            m20 = d;
            m21 = d1;
            m22 = d2;
            m23 = d3;
        } else
        if(i == 3)
        {
            m30 = d;
            m31 = d1;
            m32 = d2;
            m33 = d3;
        }
    }

    public final void setRow(int i, com.maddox.JGP.Tuple4d tuple4d)
    {
        if(i == 0)
        {
            m00 = tuple4d.x;
            m01 = tuple4d.y;
            m02 = tuple4d.z;
            m03 = tuple4d.w;
        } else
        if(i == 1)
        {
            m10 = tuple4d.x;
            m11 = tuple4d.y;
            m12 = tuple4d.z;
            m13 = tuple4d.w;
        } else
        if(i == 2)
        {
            m20 = tuple4d.x;
            m21 = tuple4d.y;
            m22 = tuple4d.z;
            m23 = tuple4d.w;
        } else
        if(i == 3)
        {
            m30 = tuple4d.x;
            m31 = tuple4d.y;
            m32 = tuple4d.z;
            m33 = tuple4d.w;
        }
    }

    public final void setRow(int i, double ad[])
    {
        if(i == 0)
        {
            m00 = ad[0];
            m01 = ad[1];
            m02 = ad[2];
            m03 = ad[3];
        } else
        if(i == 1)
        {
            m10 = ad[0];
            m11 = ad[1];
            m12 = ad[2];
            m13 = ad[3];
        } else
        if(i == 2)
        {
            m20 = ad[0];
            m21 = ad[1];
            m22 = ad[2];
            m23 = ad[3];
        } else
        if(i == 3)
        {
            m30 = ad[0];
            m31 = ad[1];
            m32 = ad[2];
            m33 = ad[3];
        }
    }

    public final void getRow(int i, com.maddox.JGP.Tuple4d tuple4d)
    {
        if(i == 0)
        {
            tuple4d.x = m00;
            tuple4d.y = m01;
            tuple4d.z = m02;
            tuple4d.w = m03;
        } else
        if(i == 1)
        {
            tuple4d.x = m10;
            tuple4d.y = m11;
            tuple4d.z = m12;
            tuple4d.w = m13;
        } else
        if(i == 2)
        {
            tuple4d.x = m20;
            tuple4d.y = m21;
            tuple4d.z = m22;
            tuple4d.w = m23;
        } else
        if(i == 3)
        {
            tuple4d.x = m30;
            tuple4d.y = m31;
            tuple4d.z = m32;
            tuple4d.w = m33;
        }
    }

    public final void getRow(int i, double ad[])
    {
        if(i == 0)
        {
            ad[0] = m00;
            ad[1] = m01;
            ad[2] = m02;
            ad[3] = m03;
        } else
        if(i == 1)
        {
            ad[0] = m10;
            ad[1] = m11;
            ad[2] = m12;
            ad[3] = m13;
        } else
        if(i == 2)
        {
            ad[0] = m20;
            ad[1] = m21;
            ad[2] = m22;
            ad[3] = m23;
        } else
        if(i == 3)
        {
            ad[0] = m30;
            ad[1] = m31;
            ad[2] = m32;
            ad[3] = m33;
        }
    }

    public final void setColumn(int i, double d, double d1, double d2, 
            double d3)
    {
        if(i == 0)
        {
            m00 = d;
            m10 = d1;
            m20 = d2;
            m30 = d3;
        } else
        if(i == 1)
        {
            m01 = d;
            m11 = d1;
            m21 = d2;
            m31 = d3;
        } else
        if(i == 2)
        {
            m02 = d;
            m12 = d1;
            m22 = d2;
            m32 = d3;
        } else
        if(i == 3)
        {
            m03 = d;
            m13 = d1;
            m23 = d2;
            m33 = d3;
        }
    }

    public final void setColumn(int i, com.maddox.JGP.Tuple4d tuple4d)
    {
        if(i == 0)
        {
            m00 = tuple4d.x;
            m10 = tuple4d.y;
            m20 = tuple4d.z;
            m30 = tuple4d.w;
        } else
        if(i == 1)
        {
            m01 = tuple4d.x;
            m11 = tuple4d.y;
            m21 = tuple4d.z;
            m31 = tuple4d.w;
        } else
        if(i == 2)
        {
            m02 = tuple4d.x;
            m12 = tuple4d.y;
            m22 = tuple4d.z;
            m32 = tuple4d.w;
        } else
        if(i == 3)
        {
            m03 = tuple4d.x;
            m13 = tuple4d.y;
            m23 = tuple4d.z;
            m33 = tuple4d.w;
        }
    }

    public final void setColumn(int i, double ad[])
    {
        if(i == 0)
        {
            m00 = ad[0];
            m10 = ad[1];
            m20 = ad[2];
            m30 = ad[3];
        } else
        if(i == 1)
        {
            m01 = ad[0];
            m11 = ad[1];
            m21 = ad[2];
            m31 = ad[3];
        } else
        if(i == 2)
        {
            m02 = ad[0];
            m12 = ad[1];
            m22 = ad[2];
            m32 = ad[3];
        } else
        if(i == 3)
        {
            m03 = ad[0];
            m13 = ad[1];
            m23 = ad[2];
            m33 = ad[3];
        }
    }

    public final void getColumn(int i, com.maddox.JGP.Tuple4d tuple4d)
    {
        if(i == 0)
        {
            tuple4d.x = m00;
            tuple4d.y = m10;
            tuple4d.z = m20;
            tuple4d.w = m30;
        } else
        if(i == 1)
        {
            tuple4d.x = m01;
            tuple4d.y = m11;
            tuple4d.z = m21;
            tuple4d.w = m31;
        } else
        if(i == 2)
        {
            tuple4d.x = m02;
            tuple4d.y = m12;
            tuple4d.z = m22;
            tuple4d.w = m32;
        } else
        if(i == 3)
        {
            tuple4d.x = m03;
            tuple4d.y = m13;
            tuple4d.z = m23;
            tuple4d.w = m33;
        }
    }

    public final void getColumn(int i, double ad[])
    {
        if(i == 0)
        {
            ad[0] = m00;
            ad[1] = m10;
            ad[2] = m20;
            ad[3] = m30;
        } else
        if(i == 1)
        {
            ad[0] = m01;
            ad[1] = m11;
            ad[2] = m21;
            ad[3] = m31;
        } else
        if(i == 2)
        {
            ad[0] = m02;
            ad[1] = m12;
            ad[2] = m22;
            ad[3] = m32;
        } else
        if(i == 3)
        {
            ad[0] = m03;
            ad[1] = m13;
            ad[2] = m23;
            ad[3] = m33;
        }
    }

    public final void add(double d)
    {
        m00 += d;
        m01 += d;
        m02 += d;
        m03 += d;
        m10 += d;
        m11 += d;
        m12 += d;
        m13 += d;
        m20 += d;
        m21 += d;
        m22 += d;
        m23 += d;
        m30 += d;
        m31 += d;
        m32 += d;
        m33 += d;
    }

    public final void add(double d, com.maddox.JGP.Matrix4d matrix4d)
    {
        set(matrix4d);
        add(d);
    }

    public final void add(com.maddox.JGP.Matrix4d matrix4d, com.maddox.JGP.Matrix4d matrix4d1)
    {
        set(matrix4d.m00 + matrix4d1.m00, matrix4d.m01 + matrix4d1.m01, matrix4d.m02 + matrix4d1.m02, matrix4d.m03 + matrix4d1.m03, matrix4d.m10 + matrix4d1.m10, matrix4d.m11 + matrix4d1.m11, matrix4d.m12 + matrix4d1.m12, matrix4d.m13 + matrix4d1.m13, matrix4d.m20 + matrix4d1.m20, matrix4d.m21 + matrix4d1.m21, matrix4d.m22 + matrix4d1.m22, matrix4d.m23 + matrix4d1.m23, matrix4d.m30 + matrix4d1.m30, matrix4d.m31 + matrix4d1.m31, matrix4d.m32 + matrix4d1.m32, matrix4d.m33 + matrix4d1.m33);
    }

    public final void add(com.maddox.JGP.Matrix4d matrix4d)
    {
        m00 += matrix4d.m00;
        m01 += matrix4d.m01;
        m02 += matrix4d.m02;
        m03 += matrix4d.m03;
        m10 += matrix4d.m10;
        m11 += matrix4d.m11;
        m12 += matrix4d.m12;
        m13 += matrix4d.m13;
        m20 += matrix4d.m20;
        m21 += matrix4d.m21;
        m22 += matrix4d.m22;
        m23 += matrix4d.m23;
        m30 += matrix4d.m30;
        m31 += matrix4d.m31;
        m32 += matrix4d.m32;
        m33 += matrix4d.m33;
    }

    public final void sub(com.maddox.JGP.Matrix4d matrix4d, com.maddox.JGP.Matrix4d matrix4d1)
    {
        set(matrix4d.m00 - matrix4d1.m00, matrix4d.m01 - matrix4d1.m01, matrix4d.m02 - matrix4d1.m02, matrix4d.m03 - matrix4d1.m03, matrix4d.m10 - matrix4d1.m10, matrix4d.m11 - matrix4d1.m11, matrix4d.m12 - matrix4d1.m12, matrix4d.m13 - matrix4d1.m13, matrix4d.m20 - matrix4d1.m20, matrix4d.m21 - matrix4d1.m21, matrix4d.m22 - matrix4d1.m22, matrix4d.m23 - matrix4d1.m23, matrix4d.m30 - matrix4d1.m30, matrix4d.m31 - matrix4d1.m31, matrix4d.m32 - matrix4d1.m32, matrix4d.m33 - matrix4d1.m33);
    }

    public final void sub(com.maddox.JGP.Matrix4d matrix4d)
    {
        m00 -= matrix4d.m00;
        m01 -= matrix4d.m01;
        m02 -= matrix4d.m02;
        m03 -= matrix4d.m03;
        m10 -= matrix4d.m10;
        m11 -= matrix4d.m11;
        m12 -= matrix4d.m12;
        m13 -= matrix4d.m13;
        m20 -= matrix4d.m20;
        m21 -= matrix4d.m21;
        m22 -= matrix4d.m22;
        m23 -= matrix4d.m23;
        m30 -= matrix4d.m30;
        m31 -= matrix4d.m31;
        m32 -= matrix4d.m32;
        m33 -= matrix4d.m33;
    }

    public final void transpose()
    {
        double d = m01;
        m01 = m10;
        m10 = d;
        d = m02;
        m02 = m20;
        m20 = d;
        d = m03;
        m03 = m30;
        m30 = d;
        d = m12;
        m12 = m21;
        m21 = d;
        d = m13;
        m13 = m31;
        m31 = d;
        d = m23;
        m23 = m32;
        m32 = d;
    }

    public final void transpose(com.maddox.JGP.Matrix4d matrix4d)
    {
        set(matrix4d);
        transpose();
    }

    public final void set(double ad[])
    {
        m00 = ad[0];
        m01 = ad[1];
        m02 = ad[2];
        m03 = ad[3];
        m10 = ad[4];
        m11 = ad[5];
        m12 = ad[6];
        m13 = ad[7];
        m20 = ad[8];
        m21 = ad[9];
        m22 = ad[10];
        m23 = ad[11];
        m30 = ad[12];
        m31 = ad[13];
        m32 = ad[14];
        m33 = ad[15];
    }

    public final void set(com.maddox.JGP.Matrix3f matrix3f)
    {
        m00 = matrix3f.m00;
        m01 = matrix3f.m01;
        m02 = matrix3f.m02;
        m03 = 0.0D;
        m10 = matrix3f.m10;
        m11 = matrix3f.m11;
        m12 = matrix3f.m12;
        m13 = 0.0D;
        m20 = matrix3f.m20;
        m21 = matrix3f.m21;
        m22 = matrix3f.m22;
        m23 = 0.0D;
        m30 = 0.0D;
        m31 = 0.0D;
        m32 = 0.0D;
        m33 = 1.0D;
    }

    public final void set(com.maddox.JGP.Matrix3d matrix3d)
    {
        m00 = matrix3d.m00;
        m01 = matrix3d.m01;
        m02 = matrix3d.m02;
        m03 = 0.0D;
        m10 = matrix3d.m10;
        m11 = matrix3d.m11;
        m12 = matrix3d.m12;
        m13 = 0.0D;
        m20 = matrix3d.m20;
        m21 = matrix3d.m21;
        m22 = matrix3d.m22;
        m23 = 0.0D;
        m30 = 0.0D;
        m31 = 0.0D;
        m32 = 0.0D;
        m33 = 1.0D;
    }

    public final void set(com.maddox.JGP.Quat4d quat4d)
    {
        setFromQuat(quat4d.x, quat4d.y, quat4d.z, quat4d.w);
    }

    public final void set(com.maddox.JGP.AxisAngle4d axisangle4d)
    {
        setFromAxisAngle(axisangle4d.x, axisangle4d.y, axisangle4d.z, axisangle4d.angle);
    }

    public final void set(com.maddox.JGP.Quat4f quat4f)
    {
        setFromQuat(quat4f.x, quat4f.y, quat4f.z, quat4f.w);
    }

    public final void set(com.maddox.JGP.AxisAngle4f axisangle4f)
    {
        setFromAxisAngle(axisangle4f.x, axisangle4f.y, axisangle4f.z, axisangle4f.angle);
    }

    public final void set(com.maddox.JGP.Quat4d quat4d, com.maddox.JGP.Tuple3d tuple3d, double d)
    {
        set(quat4d);
        mulRotationScale(d);
        m03 = tuple3d.x;
        m13 = tuple3d.y;
        m23 = tuple3d.z;
    }

    public final void set(com.maddox.JGP.Quat4f quat4f, com.maddox.JGP.Tuple3d tuple3d, double d)
    {
        set(quat4f);
        mulRotationScale(d);
        m03 = tuple3d.x;
        m13 = tuple3d.y;
        m23 = tuple3d.z;
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
        m00 = matrix4d.m00;
        m01 = matrix4d.m01;
        m02 = matrix4d.m02;
        m03 = matrix4d.m03;
        m10 = matrix4d.m10;
        m11 = matrix4d.m11;
        m12 = matrix4d.m12;
        m13 = matrix4d.m13;
        m20 = matrix4d.m20;
        m21 = matrix4d.m21;
        m22 = matrix4d.m22;
        m23 = matrix4d.m23;
        m30 = matrix4d.m30;
        m31 = matrix4d.m31;
        m32 = matrix4d.m32;
        m33 = matrix4d.m33;
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

    public final void invert(com.maddox.JGP.Matrix4d matrix4d)
    {
        set(matrix4d);
        invert();
    }

    public final void invert()
    {
        double d = determinant();
        if(d == 0.0D)
        {
            return;
        } else
        {
            d = 1.0D / d;
            set(m11 * (m22 * m33 - m23 * m32) + m12 * (m23 * m31 - m21 * m33) + m13 * (m21 * m32 - m22 * m31), m21 * (m02 * m33 - m03 * m32) + m22 * (m03 * m31 - m01 * m33) + m23 * (m01 * m32 - m02 * m31), m31 * (m02 * m13 - m03 * m12) + m32 * (m03 * m11 - m01 * m13) + m33 * (m01 * m12 - m02 * m11), m01 * (m13 * m22 - m12 * m23) + m02 * (m11 * m23 - m13 * m21) + m03 * (m12 * m21 - m11 * m22), m12 * (m20 * m33 - m23 * m30) + m13 * (m22 * m30 - m20 * m32) + m10 * (m23 * m32 - m22 * m33), m22 * (m00 * m33 - m03 * m30) + m23 * (m02 * m30 - m00 * m32) + m20 * (m03 * m32 - m02 * m33), m32 * (m00 * m13 - m03 * m10) + m33 * (m02 * m10 - m00 * m12) + m30 * (m03 * m12 - m02 * m13), m02 * (m13 * m20 - m10 * m23) + m03 * (m10 * m22 - m12 * m20) + m00 * (m12 * m23 - m13 * m22), m13 * (m20 * m31 - m21 * m30) + m10 * (m21 * m33 - m23 * m31) + m11 * (m23 * m30 - m20 * m33), m23 * (m00 * m31 - m01 * m30) + m20 * (m01 * m33 - m03 * m31) + m21 * (m03 * m30 - m00 * m33), m33 * (m00 * m11 - m01 * m10) + m30 * (m01 * m13 - m03 * m11) + m31 * (m03 * m10 - m00 * m13), m03 * (m11 * m20 - m10 * m21) + m00 * (m13 * m21 - m11 * m23) + m01 * (m10 * m23 - m13 * m20), m10 * (m22 * m31 - m21 * m32) + m11 * (m20 * m32 - m22 * m30) + m12 * (m21 * m30 - m20 * m31), m20 * (m02 * m31 - m01 * m32) + m21 * (m00 * m32 - m02 * m30) + m22 * (m01 * m30 - m00 * m31), m30 * (m02 * m11 - m01 * m12) + m31 * (m00 * m12 - m02 * m10) + m32 * (m01 * m10 - m00 * m11), m00 * (m11 * m22 - m12 * m21) + m01 * (m12 * m20 - m10 * m22) + m02 * (m10 * m21 - m11 * m20));
            mul(d);
            return;
        }
    }

    public final double determinant()
    {
        return ((((m00 * m11 - m01 * m10) * (m22 * m33 - m23 * m32) - (m00 * m12 - m02 * m10) * (m21 * m33 - m23 * m31)) + (m00 * m13 - m03 * m10) * (m21 * m32 - m22 * m31) + (m01 * m12 - m02 * m11) * (m20 * m33 - m23 * m30)) - (m01 * m13 - m03 * m11) * (m20 * m32 - m22 * m30)) + (m02 * m13 - m03 * m12) * (m20 * m31 - m21 * m30);
    }

    public final void set(double d)
    {
        m00 = d;
        m01 = 0.0D;
        m02 = 0.0D;
        m03 = 0.0D;
        m10 = 0.0D;
        m11 = d;
        m12 = 0.0D;
        m13 = 0.0D;
        m20 = 0.0D;
        m21 = 0.0D;
        m22 = d;
        m23 = 0.0D;
        m30 = 0.0D;
        m31 = 0.0D;
        m32 = 0.0D;
        m33 = 1.0D;
    }

    public final void set(com.maddox.JGP.Tuple3d tuple3d)
    {
        setIdentity();
        setTranslation(tuple3d);
    }

    public final void set(double d, com.maddox.JGP.Tuple3d tuple3d)
    {
        set(d);
        setTranslation(tuple3d);
    }

    public final void set(com.maddox.JGP.Tuple3d tuple3d, double d)
    {
        m00 = d;
        m01 = 0.0D;
        m02 = 0.0D;
        m03 = d * tuple3d.x;
        m10 = 0.0D;
        m11 = d;
        m12 = 0.0D;
        m13 = d * tuple3d.y;
        m20 = 0.0D;
        m21 = 0.0D;
        m22 = d;
        m23 = d * tuple3d.z;
        m30 = 0.0D;
        m31 = 0.0D;
        m32 = 0.0D;
        m33 = 1.0D;
    }

    public final void set(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Tuple3f tuple3f, float f)
    {
        setRotationScale(matrix3f);
        mulRotationScale(f);
        setTranslation(tuple3f);
        m33 = 1.0D;
    }

    public final void set(com.maddox.JGP.Matrix3d matrix3d, com.maddox.JGP.Tuple3d tuple3d, double d)
    {
        setRotationScale(matrix3d);
        mulRotationScale(d);
        setTranslation(tuple3d);
        m33 = 1.0D;
    }

    public final void setTranslation(com.maddox.JGP.Tuple3d tuple3d)
    {
        m03 = tuple3d.x;
        m13 = tuple3d.y;
        m23 = tuple3d.z;
    }

    public final void rotX(double d)
    {
        double d1 = java.lang.Math.cos(d);
        double d2 = java.lang.Math.sin(d);
        m00 = 1.0D;
        m01 = 0.0D;
        m02 = 0.0D;
        m03 = 0.0D;
        m10 = 0.0D;
        m11 = d1;
        m12 = -d2;
        m13 = 0.0D;
        m20 = 0.0D;
        m21 = d2;
        m22 = d1;
        m23 = 0.0D;
        m30 = 0.0D;
        m31 = 0.0D;
        m32 = 0.0D;
        m33 = 1.0D;
    }

    public final void rotY(double d)
    {
        double d1 = java.lang.Math.cos(d);
        double d2 = java.lang.Math.sin(d);
        m00 = d1;
        m01 = 0.0D;
        m02 = d2;
        m03 = 0.0D;
        m10 = 0.0D;
        m11 = 1.0D;
        m12 = 0.0D;
        m13 = 0.0D;
        m20 = -d2;
        m21 = 0.0D;
        m22 = d1;
        m23 = 0.0D;
        m30 = 0.0D;
        m31 = 0.0D;
        m32 = 0.0D;
        m33 = 1.0D;
    }

    public final void rotZ(double d)
    {
        double d1 = java.lang.Math.cos(d);
        double d2 = java.lang.Math.sin(d);
        m00 = d1;
        m01 = -d2;
        m02 = 0.0D;
        m03 = 0.0D;
        m10 = d2;
        m11 = d1;
        m12 = 0.0D;
        m13 = 0.0D;
        m20 = 0.0D;
        m21 = 0.0D;
        m22 = 1.0D;
        m23 = 0.0D;
        m30 = 0.0D;
        m31 = 0.0D;
        m32 = 0.0D;
        m33 = 1.0D;
    }

    public final void mul(double d)
    {
        m00 *= d;
        m01 *= d;
        m02 *= d;
        m03 *= d;
        m10 *= d;
        m11 *= d;
        m12 *= d;
        m13 *= d;
        m20 *= d;
        m21 *= d;
        m22 *= d;
        m23 *= d;
        m30 *= d;
        m31 *= d;
        m32 *= d;
        m33 *= d;
    }

    public final void mul(double d, com.maddox.JGP.Matrix4d matrix4d)
    {
        set(matrix4d);
        mul(d);
    }

    public final void mul(com.maddox.JGP.Matrix4d matrix4d)
    {
        mul(this, matrix4d);
    }

    public final void mul(com.maddox.JGP.Matrix4d matrix4d, com.maddox.JGP.Matrix4d matrix4d1)
    {
        set(matrix4d.m00 * matrix4d1.m00 + matrix4d.m01 * matrix4d1.m10 + matrix4d.m02 * matrix4d1.m20 + matrix4d.m03 * matrix4d1.m30, matrix4d.m00 * matrix4d1.m01 + matrix4d.m01 * matrix4d1.m11 + matrix4d.m02 * matrix4d1.m21 + matrix4d.m03 * matrix4d1.m31, matrix4d.m00 * matrix4d1.m02 + matrix4d.m01 * matrix4d1.m12 + matrix4d.m02 * matrix4d1.m22 + matrix4d.m03 * matrix4d1.m32, matrix4d.m00 * matrix4d1.m03 + matrix4d.m01 * matrix4d1.m13 + matrix4d.m02 * matrix4d1.m23 + matrix4d.m03 * matrix4d1.m33, matrix4d.m10 * matrix4d1.m00 + matrix4d.m11 * matrix4d1.m10 + matrix4d.m12 * matrix4d1.m20 + matrix4d.m13 * matrix4d1.m30, matrix4d.m10 * matrix4d1.m01 + matrix4d.m11 * matrix4d1.m11 + matrix4d.m12 * matrix4d1.m21 + matrix4d.m13 * matrix4d1.m31, matrix4d.m10 * matrix4d1.m02 + matrix4d.m11 * matrix4d1.m12 + matrix4d.m12 * matrix4d1.m22 + matrix4d.m13 * matrix4d1.m32, matrix4d.m10 * matrix4d1.m03 + matrix4d.m11 * matrix4d1.m13 + matrix4d.m12 * matrix4d1.m23 + matrix4d.m13 * matrix4d1.m33, matrix4d.m20 * matrix4d1.m00 + matrix4d.m21 * matrix4d1.m10 + matrix4d.m22 * matrix4d1.m20 + matrix4d.m23 * matrix4d1.m30, matrix4d.m20 * matrix4d1.m01 + matrix4d.m21 * matrix4d1.m11 + matrix4d.m22 * matrix4d1.m21 + matrix4d.m23 * matrix4d1.m31, matrix4d.m20 * matrix4d1.m02 + matrix4d.m21 * matrix4d1.m12 + matrix4d.m22 * matrix4d1.m22 + matrix4d.m23 * matrix4d1.m32, matrix4d.m20 * matrix4d1.m03 + matrix4d.m21 * matrix4d1.m13 + matrix4d.m22 * matrix4d1.m23 + matrix4d.m23 * matrix4d1.m33, matrix4d.m30 * matrix4d1.m00 + matrix4d.m31 * matrix4d1.m10 + matrix4d.m32 * matrix4d1.m20 + matrix4d.m33 * matrix4d1.m30, matrix4d.m30 * matrix4d1.m01 + matrix4d.m31 * matrix4d1.m11 + matrix4d.m32 * matrix4d1.m21 + matrix4d.m33 * matrix4d1.m31, matrix4d.m30 * matrix4d1.m02 + matrix4d.m31 * matrix4d1.m12 + matrix4d.m32 * matrix4d1.m22 + matrix4d.m33 * matrix4d1.m32, matrix4d.m30 * matrix4d1.m03 + matrix4d.m31 * matrix4d1.m13 + matrix4d.m32 * matrix4d1.m23 + matrix4d.m33 * matrix4d1.m33);
    }

    public final void mulTransposeBoth(com.maddox.JGP.Matrix4d matrix4d, com.maddox.JGP.Matrix4d matrix4d1)
    {
        mul(matrix4d1, matrix4d);
        transpose();
    }

    public final void mulTransposeRight(com.maddox.JGP.Matrix4d matrix4d, com.maddox.JGP.Matrix4d matrix4d1)
    {
        set(matrix4d.m00 * matrix4d1.m00 + matrix4d.m01 * matrix4d1.m01 + matrix4d.m02 * matrix4d1.m02 + matrix4d.m03 * matrix4d1.m03, matrix4d.m00 * matrix4d1.m10 + matrix4d.m01 * matrix4d1.m11 + matrix4d.m02 * matrix4d1.m12 + matrix4d.m03 * matrix4d1.m13, matrix4d.m00 * matrix4d1.m20 + matrix4d.m01 * matrix4d1.m21 + matrix4d.m02 * matrix4d1.m22 + matrix4d.m03 * matrix4d1.m23, matrix4d.m00 * matrix4d1.m30 + matrix4d.m01 * matrix4d1.m31 + matrix4d.m02 * matrix4d1.m32 + matrix4d.m03 * matrix4d1.m33, matrix4d.m10 * matrix4d1.m00 + matrix4d.m11 * matrix4d1.m01 + matrix4d.m12 * matrix4d1.m02 + matrix4d.m13 * matrix4d1.m03, matrix4d.m10 * matrix4d1.m10 + matrix4d.m11 * matrix4d1.m11 + matrix4d.m12 * matrix4d1.m12 + matrix4d.m13 * matrix4d1.m13, matrix4d.m10 * matrix4d1.m20 + matrix4d.m11 * matrix4d1.m21 + matrix4d.m12 * matrix4d1.m22 + matrix4d.m13 * matrix4d1.m23, matrix4d.m10 * matrix4d1.m30 + matrix4d.m11 * matrix4d1.m31 + matrix4d.m12 * matrix4d1.m32 + matrix4d.m13 * matrix4d1.m33, matrix4d.m20 * matrix4d1.m00 + matrix4d.m21 * matrix4d1.m01 + matrix4d.m22 * matrix4d1.m02 + matrix4d.m23 * matrix4d1.m03, matrix4d.m20 * matrix4d1.m10 + matrix4d.m21 * matrix4d1.m11 + matrix4d.m22 * matrix4d1.m12 + matrix4d.m23 * matrix4d1.m13, matrix4d.m20 * matrix4d1.m20 + matrix4d.m21 * matrix4d1.m21 + matrix4d.m22 * matrix4d1.m22 + matrix4d.m23 * matrix4d1.m23, matrix4d.m20 * matrix4d1.m30 + matrix4d.m21 * matrix4d1.m31 + matrix4d.m22 * matrix4d1.m32 + matrix4d.m23 * matrix4d1.m33, matrix4d.m30 * matrix4d1.m00 + matrix4d.m31 * matrix4d1.m01 + matrix4d.m32 * matrix4d1.m02 + matrix4d.m33 * matrix4d1.m03, matrix4d.m30 * matrix4d1.m10 + matrix4d.m31 * matrix4d1.m11 + matrix4d.m32 * matrix4d1.m12 + matrix4d.m33 * matrix4d1.m13, matrix4d.m30 * matrix4d1.m20 + matrix4d.m31 * matrix4d1.m21 + matrix4d.m32 * matrix4d1.m22 + matrix4d.m33 * matrix4d1.m23, matrix4d.m30 * matrix4d1.m30 + matrix4d.m31 * matrix4d1.m31 + matrix4d.m32 * matrix4d1.m32 + matrix4d.m33 * matrix4d1.m33);
    }

    public final void mulTransposeLeft(com.maddox.JGP.Matrix4d matrix4d, com.maddox.JGP.Matrix4d matrix4d1)
    {
        set(matrix4d.m00 * matrix4d1.m00 + matrix4d.m10 * matrix4d1.m10 + matrix4d.m20 * matrix4d1.m20 + matrix4d.m30 * matrix4d1.m30, matrix4d.m00 * matrix4d1.m01 + matrix4d.m10 * matrix4d1.m11 + matrix4d.m20 * matrix4d1.m21 + matrix4d.m30 * matrix4d1.m31, matrix4d.m00 * matrix4d1.m02 + matrix4d.m10 * matrix4d1.m12 + matrix4d.m20 * matrix4d1.m22 + matrix4d.m30 * matrix4d1.m32, matrix4d.m00 * matrix4d1.m03 + matrix4d.m10 * matrix4d1.m13 + matrix4d.m20 * matrix4d1.m23 + matrix4d.m30 * matrix4d1.m33, matrix4d.m01 * matrix4d1.m00 + matrix4d.m11 * matrix4d1.m10 + matrix4d.m21 * matrix4d1.m20 + matrix4d.m31 * matrix4d1.m30, matrix4d.m01 * matrix4d1.m01 + matrix4d.m11 * matrix4d1.m11 + matrix4d.m21 * matrix4d1.m21 + matrix4d.m31 * matrix4d1.m31, matrix4d.m01 * matrix4d1.m02 + matrix4d.m11 * matrix4d1.m12 + matrix4d.m21 * matrix4d1.m22 + matrix4d.m31 * matrix4d1.m32, matrix4d.m01 * matrix4d1.m03 + matrix4d.m11 * matrix4d1.m13 + matrix4d.m21 * matrix4d1.m23 + matrix4d.m31 * matrix4d1.m33, matrix4d.m02 * matrix4d1.m00 + matrix4d.m12 * matrix4d1.m10 + matrix4d.m22 * matrix4d1.m20 + matrix4d.m32 * matrix4d1.m30, matrix4d.m02 * matrix4d1.m01 + matrix4d.m12 * matrix4d1.m11 + matrix4d.m22 * matrix4d1.m21 + matrix4d.m32 * matrix4d1.m31, matrix4d.m02 * matrix4d1.m02 + matrix4d.m12 * matrix4d1.m12 + matrix4d.m22 * matrix4d1.m22 + matrix4d.m32 * matrix4d1.m32, matrix4d.m02 * matrix4d1.m03 + matrix4d.m12 * matrix4d1.m13 + matrix4d.m22 * matrix4d1.m23 + matrix4d.m32 * matrix4d1.m33, matrix4d.m03 * matrix4d1.m00 + matrix4d.m13 * matrix4d1.m10 + matrix4d.m23 * matrix4d1.m20 + matrix4d.m33 * matrix4d1.m30, matrix4d.m03 * matrix4d1.m01 + matrix4d.m13 * matrix4d1.m11 + matrix4d.m23 * matrix4d1.m21 + matrix4d.m33 * matrix4d1.m31, matrix4d.m03 * matrix4d1.m02 + matrix4d.m13 * matrix4d1.m12 + matrix4d.m23 * matrix4d1.m22 + matrix4d.m33 * matrix4d1.m32, matrix4d.m03 * matrix4d1.m03 + matrix4d.m13 * matrix4d1.m13 + matrix4d.m23 * matrix4d1.m23 + matrix4d.m33 * matrix4d1.m33);
    }

    public boolean equals(com.maddox.JGP.Matrix4d matrix4d)
    {
        return matrix4d != null && m00 == matrix4d.m00 && m01 == matrix4d.m01 && m02 == matrix4d.m02 && m03 == matrix4d.m03 && m10 == matrix4d.m10 && m11 == matrix4d.m11 && m12 == matrix4d.m12 && m13 == matrix4d.m13 && m20 == matrix4d.m20 && m21 == matrix4d.m21 && m22 == matrix4d.m22 && m23 == matrix4d.m23 && m30 == matrix4d.m30 && m31 == matrix4d.m31 && m32 == matrix4d.m32 && m33 == matrix4d.m33;
    }

    public boolean equals(java.lang.Object obj)
    {
        return obj != null && (obj instanceof com.maddox.JGP.Matrix4d) && equals((com.maddox.JGP.Matrix4d)obj);
    }

    /**
     * @deprecated Method epsilonEquals is deprecated
     */

    public boolean epsilonEquals(com.maddox.JGP.Matrix4d matrix4d, float f)
    {
        return java.lang.Math.abs(m00 - matrix4d.m00) <= (double)f && java.lang.Math.abs(m01 - matrix4d.m01) <= (double)f && java.lang.Math.abs(m02 - matrix4d.m02) <= (double)f && java.lang.Math.abs(m03 - matrix4d.m03) <= (double)f && java.lang.Math.abs(m10 - matrix4d.m10) <= (double)f && java.lang.Math.abs(m11 - matrix4d.m11) <= (double)f && java.lang.Math.abs(m12 - matrix4d.m12) <= (double)f && java.lang.Math.abs(m13 - matrix4d.m13) <= (double)f && java.lang.Math.abs(m20 - matrix4d.m20) <= (double)f && java.lang.Math.abs(m21 - matrix4d.m21) <= (double)f && java.lang.Math.abs(m22 - matrix4d.m22) <= (double)f && java.lang.Math.abs(m23 - matrix4d.m23) <= (double)f && java.lang.Math.abs(m30 - matrix4d.m30) <= (double)f && java.lang.Math.abs(m31 - matrix4d.m31) <= (double)f && java.lang.Math.abs(m32 - matrix4d.m32) <= (double)f && java.lang.Math.abs(m33 - matrix4d.m33) <= (double)f;
    }

    public boolean epsilonEquals(com.maddox.JGP.Matrix4d matrix4d, double d)
    {
        return java.lang.Math.abs(m00 - matrix4d.m00) <= d && java.lang.Math.abs(m01 - matrix4d.m01) <= d && java.lang.Math.abs(m02 - matrix4d.m02) <= d && java.lang.Math.abs(m03 - matrix4d.m03) <= d && java.lang.Math.abs(m10 - matrix4d.m10) <= d && java.lang.Math.abs(m11 - matrix4d.m11) <= d && java.lang.Math.abs(m12 - matrix4d.m12) <= d && java.lang.Math.abs(m13 - matrix4d.m13) <= d && java.lang.Math.abs(m20 - matrix4d.m20) <= d && java.lang.Math.abs(m21 - matrix4d.m21) <= d && java.lang.Math.abs(m22 - matrix4d.m22) <= d && java.lang.Math.abs(m23 - matrix4d.m23) <= d && java.lang.Math.abs(m30 - matrix4d.m30) <= d && java.lang.Math.abs(m31 - matrix4d.m31) <= d && java.lang.Math.abs(m32 - matrix4d.m32) <= d && java.lang.Math.abs(m33 - matrix4d.m33) <= d;
    }

    public int hashCode()
    {
        long l = java.lang.Double.doubleToLongBits(m00);
        int i = (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m01);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m02);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m03);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m10);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m11);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m12);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m13);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m20);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m21);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m22);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m23);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m30);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m31);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m32);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m33);
        i ^= (int)(l ^ l >> 32);
        return i;
    }

    public final void transform(com.maddox.JGP.Tuple4d tuple4d, com.maddox.JGP.Tuple4d tuple4d1)
    {
        tuple4d1.set(m00 * tuple4d.x + m01 * tuple4d.y + m02 * tuple4d.z + m03 * tuple4d.w, m10 * tuple4d.x + m11 * tuple4d.y + m12 * tuple4d.z + m13 * tuple4d.w, m20 * tuple4d.x + m21 * tuple4d.y + m22 * tuple4d.z + m23 * tuple4d.w, m30 * tuple4d.x + m31 * tuple4d.y + m32 * tuple4d.z + m33 * tuple4d.w);
    }

    public final void transform(com.maddox.JGP.Tuple4d tuple4d)
    {
        transform(tuple4d, tuple4d);
    }

    public final void transform(com.maddox.JGP.Tuple4f tuple4f, com.maddox.JGP.Tuple4f tuple4f1)
    {
        tuple4f1.set((float)(m00 * (double)tuple4f.x + m01 * (double)tuple4f.y + m02 * (double)tuple4f.z + m03 * (double)tuple4f.w), (float)(m10 * (double)tuple4f.x + m11 * (double)tuple4f.y + m12 * (double)tuple4f.z + m13 * (double)tuple4f.w), (float)(m20 * (double)tuple4f.x + m21 * (double)tuple4f.y + m22 * (double)tuple4f.z + m23 * (double)tuple4f.w), (float)(m30 * (double)tuple4f.x + m31 * (double)tuple4f.y + m32 * (double)tuple4f.z + m33 * (double)tuple4f.w));
    }

    public final void transform(com.maddox.JGP.Tuple4f tuple4f)
    {
        transform(tuple4f, tuple4f);
    }

    public final void transform(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        point3d1.set(m00 * point3d.x + m01 * point3d.y + m02 * point3d.z + m03, m10 * point3d.x + m11 * point3d.y + m12 * point3d.z + m13, m20 * point3d.x + m21 * point3d.y + m22 * point3d.z + m23);
    }

    public final void transform(com.maddox.JGP.Point3d point3d)
    {
        transform(point3d, point3d);
    }

    public final void transform(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1)
    {
        point3f1.set((float)(m00 * (double)point3f.x + m01 * (double)point3f.y + m02 * (double)point3f.z + m03), (float)(m10 * (double)point3f.x + m11 * (double)point3f.y + m12 * (double)point3f.z + m13), (float)(m20 * (double)point3f.x + m21 * (double)point3f.y + m22 * (double)point3f.z + m23));
    }

    public final void transform(com.maddox.JGP.Point3f point3f)
    {
        transform(point3f, point3f);
    }

    public final void transform(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1)
    {
        tuple3d1.set(m00 * tuple3d.x + m01 * tuple3d.y + m02 * tuple3d.z, m10 * tuple3d.x + m11 * tuple3d.y + m12 * tuple3d.z, m20 * tuple3d.x + m21 * tuple3d.y + m22 * tuple3d.z);
    }

    public final void transform(com.maddox.JGP.Tuple3d tuple3d)
    {
        transform(tuple3d, tuple3d);
    }

    public final void transform(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        tuple3f1.set((float)(m00 * (double)tuple3f.x + m01 * (double)tuple3f.y + m02 * (double)tuple3f.z), (float)(m10 * (double)tuple3f.x + m11 * (double)tuple3f.y + m12 * (double)tuple3f.z), (float)(m20 * (double)tuple3f.x + m21 * (double)tuple3f.y + m22 * (double)tuple3f.z));
    }

    public final void transform(com.maddox.JGP.Tuple3f tuple3f)
    {
        transform(tuple3f, tuple3f);
    }

    public final void setRotation(com.maddox.JGP.Matrix3d matrix3d)
    {
        double d = SVD(null, null);
        setRotationScale(matrix3d);
        mulRotationScale(d);
    }

    public final void setRotation(com.maddox.JGP.Matrix3f matrix3f)
    {
        double d = SVD(null, null);
        setRotationScale(matrix3f);
        mulRotationScale(d);
    }

    public final void setRotation(com.maddox.JGP.Quat4f quat4f)
    {
        double d = SVD(null, null);
        double d1 = m03;
        double d2 = m13;
        double d3 = m23;
        double d4 = m30;
        double d5 = m31;
        double d6 = m32;
        double d7 = m33;
        set(quat4f);
        mulRotationScale(d);
        m03 = d1;
        m13 = d2;
        m23 = d3;
        m30 = d4;
        m31 = d5;
        m32 = d6;
        m33 = d7;
    }

    public final void setRotation(com.maddox.JGP.Quat4d quat4d)
    {
        double d = SVD(null, null);
        double d1 = m03;
        double d2 = m13;
        double d3 = m23;
        double d4 = m30;
        double d5 = m31;
        double d6 = m32;
        double d7 = m33;
        set(quat4d);
        mulRotationScale(d);
        m03 = d1;
        m13 = d2;
        m23 = d3;
        m30 = d4;
        m31 = d5;
        m32 = d6;
        m33 = d7;
    }

    public final void setRotation(com.maddox.JGP.AxisAngle4d axisangle4d)
    {
        double d = SVD(null, null);
        double d1 = m03;
        double d2 = m13;
        double d3 = m23;
        double d4 = m30;
        double d5 = m31;
        double d6 = m32;
        double d7 = m33;
        set(axisangle4d);
        mulRotationScale(d);
        m03 = d1;
        m13 = d2;
        m23 = d3;
        m30 = d4;
        m31 = d5;
        m32 = d6;
        m33 = d7;
    }

    public final void setZero()
    {
        m00 = 0.0D;
        m01 = 0.0D;
        m02 = 0.0D;
        m03 = 0.0D;
        m10 = 0.0D;
        m11 = 0.0D;
        m12 = 0.0D;
        m13 = 0.0D;
        m20 = 0.0D;
        m21 = 0.0D;
        m22 = 0.0D;
        m23 = 0.0D;
        m30 = 0.0D;
        m31 = 0.0D;
        m32 = 0.0D;
        m33 = 0.0D;
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

    public final void negate(com.maddox.JGP.Matrix4d matrix4d)
    {
        set(matrix4d);
        negate();
    }

    private void set(double d, double d1, double d2, double d3, double d4, double d5, double d6, 
            double d7, double d8, double d9, double d10, double d11, double d12, double d13, 
            double d14, double d15)
    {
        m00 = d;
        m01 = d1;
        m02 = d2;
        m03 = d3;
        m10 = d4;
        m11 = d5;
        m12 = d6;
        m13 = d7;
        m20 = d8;
        m21 = d9;
        m22 = d10;
        m23 = d11;
        m30 = d12;
        m31 = d13;
        m32 = d14;
        m33 = d15;
    }

    private double SVD(com.maddox.JGP.Matrix3d matrix3d, com.maddox.JGP.Matrix4d matrix4d)
    {
        double d = java.lang.Math.sqrt((m00 * m00 + m10 * m10 + m20 * m20 + m01 * m01 + m11 * m11 + m21 * m21 + m02 * m02 + m12 * m12 + m22 * m22) / 3D);
        double d1 = d != 0.0D ? 1.0D / d : 0.0D;
        if(matrix3d != null)
        {
            getRotationScale(matrix3d);
            matrix3d.mul(d1);
        }
        if(matrix4d != null)
        {
            if(matrix4d != this)
                matrix4d.setRotationScale(this);
            matrix4d.mulRotationScale(d1);
        }
        return d;
    }

    private float SVD(com.maddox.JGP.Matrix3f matrix3f)
    {
        double d = java.lang.Math.sqrt((m00 * m00 + m10 * m10 + m20 * m20 + m01 * m01 + m11 * m11 + m21 * m21 + m02 * m02 + m12 * m12 + m22 * m22) / 3D);
        double d1 = d != 0.0D ? 1.0D / d : 0.0D;
        if(matrix3f != null)
        {
            getRotationScale(matrix3f);
            matrix3f.mul((float)d1);
        }
        return (float)d;
    }

    private void mulRotationScale(double d)
    {
        m00 *= d;
        m01 *= d;
        m02 *= d;
        m10 *= d;
        m11 *= d;
        m12 *= d;
        m20 *= d;
        m21 *= d;
        m22 *= d;
    }

    private void setRotationScale(com.maddox.JGP.Matrix4d matrix4d)
    {
        m00 = matrix4d.m00;
        m01 = matrix4d.m01;
        m02 = matrix4d.m02;
        m10 = matrix4d.m10;
        m11 = matrix4d.m11;
        m12 = matrix4d.m12;
        m20 = matrix4d.m20;
        m21 = matrix4d.m21;
        m22 = matrix4d.m22;
    }

    private void setTranslation(com.maddox.JGP.Tuple3f tuple3f)
    {
        m03 = tuple3f.x;
        m13 = tuple3f.y;
        m23 = tuple3f.z;
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
        m00 = 1.0D - (d15 + d17);
        m01 = d13 - d11;
        m02 = d14 + d10;
        m10 = d13 + d11;
        m11 = 1.0D - (d12 + d17);
        m12 = d16 - d9;
        m20 = d14 - d10;
        m21 = d16 + d9;
        m22 = 1.0D - (d12 + d15);
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
        m00 = d5 + d * d * d7;
        m11 = d5 + d1 * d1 * d7;
        m22 = d5 + d2 * d2 * d7;
        double d8 = d * d1 * d7;
        double d9 = d2 * d6;
        m01 = d8 - d9;
        m10 = d8 + d9;
        d8 = d * d2 * d7;
        d9 = d1 * d6;
        m02 = d8 + d9;
        m20 = d8 - d9;
        d8 = d1 * d2 * d7;
        d9 = d * d6;
        m12 = d8 - d9;
        m21 = d8 + d9;
    }

    public void setEulers(double ad[])
    {
        setEulers(ad[0], ad[1], ad[2]);
    }

    public void getEulers(double ad[])
    {
        double d3 = -m20;
        double d2 = java.lang.Math.sqrt(1.0D - d3 * d3);
        double d;
        double d1;
        double d4;
        double d5;
        if(d2 > 0.001D)
        {
            d4 = m22;
            d5 = m21;
            d = m00;
            d1 = m10;
        } else
        {
            d2 = 0.0D;
            d = 1.0D;
            d1 = 0.0D;
            d4 = m11;
            d5 = -m12;
        }
        ad[0] = java.lang.Math.atan2(d1, d);
        ad[1] = java.lang.Math.atan2(d3, d2);
        ad[2] = java.lang.Math.atan2(d5, d4);
    }

    public void setEulers(double d, double d1, double d2)
    {
        double d3 = java.lang.Math.cos(d);
        double d4 = java.lang.Math.sin(d);
        double d5 = java.lang.Math.cos(d1);
        double d6 = java.lang.Math.sin(d1);
        double d7 = java.lang.Math.cos(d2);
        double d8 = java.lang.Math.sin(d2);
        m00 = d3 * d5;
        m01 = -d4 * d7 + d3 * d6 * d8;
        m02 = d4 * d8 + d3 * d6 * d7;
        m10 = d4 * d5;
        m11 = d3 * d7 + d4 * d6 * d8;
        m12 = -d3 * d8 + d4 * d6 * d7;
        m20 = -d6;
        m21 = d5 * d8;
        m22 = d5 * d7;
        m03 = 0.0D;
        m13 = 0.0D;
        m23 = 0.0D;
        m30 = 0.0D;
        m31 = 0.0D;
        m32 = 0.0D;
        m33 = 1.0D;
    }

    public void setEulersInv(double d, double d1, double d2)
    {
        double d3 = java.lang.Math.cos(d);
        double d4 = java.lang.Math.sin(d);
        double d5 = java.lang.Math.cos(d1);
        double d6 = java.lang.Math.sin(d1);
        double d7 = java.lang.Math.cos(d2);
        double d8 = java.lang.Math.sin(d2);
        m00 = d5 * d3;
        m01 = d5 * d4;
        m02 = -d6;
        m10 = d8 * d6 * d3 - d7 * d4;
        m11 = d8 * d6 * d4 + d7 * d3;
        m12 = d8 * d5;
        m20 = d7 * d6 * d3 + d8 * d4;
        m21 = d7 * d6 * d4 - d8 * d3;
        m22 = d7 * d5;
        m03 = 0.0D;
        m13 = 0.0D;
        m23 = 0.0D;
        m30 = 0.0D;
        m31 = 0.0D;
        m32 = 0.0D;
        m33 = 1.0D;
    }

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
}
