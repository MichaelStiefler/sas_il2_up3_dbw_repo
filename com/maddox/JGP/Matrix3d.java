// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Matrix3d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple3d, Quat4d, AxisAngle4d, Quat4f, 
//            AxisAngle4f, Matrix3f, Tuple3f

public class Matrix3d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Matrix3d(double d, double d1, double d2, double d3, double d4, double d5, double d6, 
            double d7, double d8)
    {
        set(d, d1, d2, d3, d4, d5, d6, d7, d8);
    }

    public Matrix3d(double ad[])
    {
        set(ad);
    }

    public Matrix3d(com.maddox.JGP.Matrix3d matrix3d)
    {
        set(matrix3d);
    }

    public Matrix3d(com.maddox.JGP.Matrix3f matrix3f)
    {
        set(matrix3f);
    }

    public Matrix3d()
    {
        setZero();
    }

    public java.lang.String toString()
    {
        java.lang.String s = java.lang.System.getProperty("line.separator");
        return "[" + s + "  [" + m00 + "\t" + m01 + "\t" + m02 + "]" + s + "  [" + m10 + "\t" + m11 + "\t" + m12 + "]" + s + "  [" + m20 + "\t" + m21 + "\t" + m22 + "] ]";
    }

    public final void setIdentity()
    {
        m00 = 1.0D;
        m01 = 0.0D;
        m02 = 0.0D;
        m10 = 0.0D;
        m11 = 1.0D;
        m12 = 0.0D;
        m20 = 0.0D;
        m21 = 0.0D;
        m22 = 1.0D;
    }

    public final void setScale(double d)
    {
        SVD(this);
        m00 *= d;
        m11 *= d;
        m22 *= d;
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
                m02 = d;
        } else
        if(i == 1)
        {
            if(j == 0)
                m10 = d;
            else
            if(j == 1)
                m11 = d;
            else
                m12 = d;
        } else
        if(j == 0)
            m20 = d;
        else
        if(j == 1)
            m21 = d;
        else
            m22 = d;
    }

    public final double getElement(int i, int j)
    {
        if(i == 0)
        {
            if(j == 0)
                return m00;
            if(j == 1)
                return m01;
            else
                return m02;
        }
        if(i == 1)
        {
            if(j == 0)
                return m10;
            if(j == 1)
                return m11;
            else
                return m12;
        }
        if(j == 0)
            return m20;
        if(j == 1)
            return m21;
        else
            return m22;
    }

    public final void setRow(int i, double d, double d1, double d2)
    {
        if(i == 0)
        {
            m00 = d;
            m01 = d1;
            m02 = d2;
        } else
        if(i == 1)
        {
            m10 = d;
            m11 = d1;
            m12 = d2;
        } else
        if(i == 2)
        {
            m20 = d;
            m21 = d1;
            m22 = d2;
        }
    }

    public final void setRow(int i, com.maddox.JGP.Tuple3d tuple3d)
    {
        if(i == 0)
        {
            m00 = tuple3d.x;
            m01 = tuple3d.y;
            m02 = tuple3d.z;
        } else
        if(i == 1)
        {
            m10 = tuple3d.x;
            m11 = tuple3d.y;
            m12 = tuple3d.z;
        } else
        if(i == 2)
        {
            m20 = tuple3d.x;
            m21 = tuple3d.y;
            m22 = tuple3d.z;
        }
    }

    public final void setRow(int i, double ad[])
    {
        if(i == 0)
        {
            m00 = ad[0];
            m01 = ad[1];
            m02 = ad[2];
        } else
        if(i == 1)
        {
            m10 = ad[0];
            m11 = ad[1];
            m12 = ad[2];
        } else
        if(i == 2)
        {
            m20 = ad[0];
            m21 = ad[1];
            m22 = ad[2];
        }
    }

    public final void getRow(int i, double ad[])
    {
        if(i == 0)
        {
            ad[0] = m00;
            ad[1] = m01;
            ad[2] = m02;
        } else
        if(i == 1)
        {
            ad[0] = m10;
            ad[1] = m11;
            ad[2] = m12;
        } else
        if(i == 2)
        {
            ad[0] = m20;
            ad[1] = m21;
            ad[2] = m22;
        }
    }

    public final void getRow(int i, com.maddox.JGP.Tuple3d tuple3d)
    {
        if(i == 0)
        {
            tuple3d.x = m00;
            tuple3d.y = m01;
            tuple3d.z = m02;
        } else
        if(i == 1)
        {
            tuple3d.x = m10;
            tuple3d.y = m11;
            tuple3d.z = m12;
        } else
        if(i == 2)
        {
            tuple3d.x = m20;
            tuple3d.y = m21;
            tuple3d.z = m22;
        }
    }

    public final void setColumn(int i, double d, double d1, double d2)
    {
        if(i == 0)
        {
            m00 = d;
            m10 = d1;
            m20 = d2;
        } else
        if(i == 1)
        {
            m01 = d;
            m11 = d1;
            m21 = d2;
        } else
        if(i == 2)
        {
            m02 = d;
            m12 = d1;
            m22 = d2;
        }
    }

    public final void setColumn(int i, com.maddox.JGP.Tuple3d tuple3d)
    {
        if(i == 0)
        {
            m00 = tuple3d.x;
            m10 = tuple3d.y;
            m20 = tuple3d.z;
        } else
        if(i == 1)
        {
            m01 = tuple3d.x;
            m11 = tuple3d.y;
            m21 = tuple3d.z;
        } else
        if(i == 2)
        {
            m02 = tuple3d.x;
            m12 = tuple3d.y;
            m22 = tuple3d.z;
        }
    }

    public final void setColumn(int i, double ad[])
    {
        if(i == 0)
        {
            m00 = ad[0];
            m10 = ad[1];
            m20 = ad[2];
        } else
        if(i == 1)
        {
            m01 = ad[0];
            m11 = ad[1];
            m21 = ad[2];
        } else
        if(i == 2)
        {
            m02 = ad[0];
            m12 = ad[1];
            m22 = ad[2];
        }
    }

    public final void getColumn(int i, com.maddox.JGP.Tuple3d tuple3d)
    {
        if(i == 0)
        {
            tuple3d.x = m00;
            tuple3d.y = m10;
            tuple3d.z = m20;
        } else
        if(i == 1)
        {
            tuple3d.x = m01;
            tuple3d.y = m11;
            tuple3d.z = m21;
        } else
        if(i == 2)
        {
            tuple3d.x = m02;
            tuple3d.y = m12;
            tuple3d.z = m22;
        }
    }

    public final void getColumn(int i, double ad[])
    {
        if(i == 0)
        {
            ad[0] = m00;
            ad[1] = m10;
            ad[2] = m20;
        } else
        if(i == 1)
        {
            ad[0] = m01;
            ad[1] = m11;
            ad[2] = m21;
        } else
        if(i == 2)
        {
            ad[0] = m02;
            ad[1] = m12;
            ad[2] = m22;
        }
    }

    public final double getScale()
    {
        return SVD(null);
    }

    public final void add(double d)
    {
        m00 += d;
        m01 += d;
        m02 += d;
        m10 += d;
        m11 += d;
        m12 += d;
        m20 += d;
        m21 += d;
        m22 += d;
    }

    public final void add(double d, com.maddox.JGP.Matrix3d matrix3d)
    {
        set(matrix3d);
        add(d);
    }

    public final void add(com.maddox.JGP.Matrix3d matrix3d, com.maddox.JGP.Matrix3d matrix3d1)
    {
        set(matrix3d.m00 + matrix3d1.m00, matrix3d.m01 + matrix3d1.m01, matrix3d.m02 + matrix3d1.m02, matrix3d.m10 + matrix3d1.m10, matrix3d.m11 + matrix3d1.m11, matrix3d.m12 + matrix3d1.m12, matrix3d.m20 + matrix3d1.m20, matrix3d.m21 + matrix3d1.m21, matrix3d.m22 + matrix3d1.m22);
    }

    public final void add(com.maddox.JGP.Matrix3d matrix3d)
    {
        m00 += matrix3d.m00;
        m01 += matrix3d.m01;
        m02 += matrix3d.m02;
        m10 += matrix3d.m10;
        m11 += matrix3d.m11;
        m12 += matrix3d.m12;
        m20 += matrix3d.m20;
        m21 += matrix3d.m21;
        m22 += matrix3d.m22;
    }

    public final void sub(com.maddox.JGP.Matrix3d matrix3d, com.maddox.JGP.Matrix3d matrix3d1)
    {
        set(matrix3d.m00 - matrix3d1.m00, matrix3d.m01 - matrix3d1.m01, matrix3d.m02 - matrix3d1.m02, matrix3d.m10 - matrix3d1.m10, matrix3d.m11 - matrix3d1.m11, matrix3d.m12 - matrix3d1.m12, matrix3d.m20 - matrix3d1.m20, matrix3d.m21 - matrix3d1.m21, matrix3d.m22 - matrix3d1.m22);
    }

    public final void sub(com.maddox.JGP.Matrix3d matrix3d)
    {
        m00 -= matrix3d.m00;
        m01 -= matrix3d.m01;
        m02 -= matrix3d.m02;
        m10 -= matrix3d.m10;
        m11 -= matrix3d.m11;
        m12 -= matrix3d.m12;
        m20 -= matrix3d.m20;
        m21 -= matrix3d.m21;
        m22 -= matrix3d.m22;
    }

    public final void transpose()
    {
        double d = m01;
        m01 = m10;
        m10 = d;
        d = m02;
        m02 = m20;
        m20 = d;
        d = m12;
        m12 = m21;
        m21 = d;
    }

    public final void transpose(com.maddox.JGP.Matrix3d matrix3d)
    {
        set(matrix3d);
        transpose();
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

    public final void set(com.maddox.JGP.Matrix3d matrix3d)
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

    public final void set(com.maddox.JGP.Matrix3f matrix3f)
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

    public final void set(double ad[])
    {
        m00 = ad[0];
        m01 = ad[1];
        m02 = ad[2];
        m10 = ad[3];
        m11 = ad[4];
        m12 = ad[5];
        m20 = ad[6];
        m21 = ad[7];
        m22 = ad[8];
    }

    public final void invert(com.maddox.JGP.Matrix3d matrix3d)
    {
        set(matrix3d);
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
            set(m11 * m22 - m12 * m21, m02 * m21 - m01 * m22, m01 * m12 - m02 * m11, m12 * m20 - m10 * m22, m00 * m22 - m02 * m20, m02 * m10 - m00 * m12, m10 * m21 - m11 * m20, m01 * m20 - m00 * m21, m00 * m11 - m01 * m10);
            mul(d);
            return;
        }
    }

    public final double determinant()
    {
        return (m00 * (m11 * m22 - m21 * m12) - m01 * (m10 * m22 - m20 * m12)) + m02 * (m10 * m21 - m20 * m11);
    }

    public final void set(double d)
    {
        m00 = d;
        m01 = 0.0D;
        m02 = 0.0D;
        m10 = 0.0D;
        m11 = d;
        m12 = 0.0D;
        m20 = 0.0D;
        m21 = 0.0D;
        m22 = d;
    }

    public final void rotX(double d)
    {
        double d1 = java.lang.Math.cos(d);
        double d2 = java.lang.Math.sin(d);
        m00 = 1.0D;
        m01 = 0.0D;
        m02 = 0.0D;
        m10 = 0.0D;
        m11 = d1;
        m12 = -d2;
        m20 = 0.0D;
        m21 = d2;
        m22 = d1;
    }

    public final void rotY(double d)
    {
        double d1 = java.lang.Math.cos(d);
        double d2 = java.lang.Math.sin(d);
        m00 = d1;
        m01 = 0.0D;
        m02 = d2;
        m10 = 0.0D;
        m11 = 1.0D;
        m12 = 0.0D;
        m20 = -d2;
        m21 = 0.0D;
        m22 = d1;
    }

    public final void rotZ(double d)
    {
        double d1 = java.lang.Math.cos(d);
        double d2 = java.lang.Math.sin(d);
        m00 = d1;
        m01 = -d2;
        m02 = 0.0D;
        m10 = d2;
        m11 = d1;
        m12 = 0.0D;
        m20 = 0.0D;
        m21 = 0.0D;
        m22 = 1.0D;
    }

    public final void mul(double d)
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

    public final void mul(double d, com.maddox.JGP.Matrix3d matrix3d)
    {
        set(matrix3d);
        mul(d);
    }

    public final void mul(com.maddox.JGP.Matrix3d matrix3d)
    {
        mul(this, matrix3d);
    }

    public final void mul(com.maddox.JGP.Matrix3d matrix3d, com.maddox.JGP.Matrix3d matrix3d1)
    {
        set(matrix3d.m00 * matrix3d1.m00 + matrix3d.m01 * matrix3d1.m10 + matrix3d.m02 * matrix3d1.m20, matrix3d.m00 * matrix3d1.m01 + matrix3d.m01 * matrix3d1.m11 + matrix3d.m02 * matrix3d1.m21, matrix3d.m00 * matrix3d1.m02 + matrix3d.m01 * matrix3d1.m12 + matrix3d.m02 * matrix3d1.m22, matrix3d.m10 * matrix3d1.m00 + matrix3d.m11 * matrix3d1.m10 + matrix3d.m12 * matrix3d1.m20, matrix3d.m10 * matrix3d1.m01 + matrix3d.m11 * matrix3d1.m11 + matrix3d.m12 * matrix3d1.m21, matrix3d.m10 * matrix3d1.m02 + matrix3d.m11 * matrix3d1.m12 + matrix3d.m12 * matrix3d1.m22, matrix3d.m20 * matrix3d1.m00 + matrix3d.m21 * matrix3d1.m10 + matrix3d.m22 * matrix3d1.m20, matrix3d.m20 * matrix3d1.m01 + matrix3d.m21 * matrix3d1.m11 + matrix3d.m22 * matrix3d1.m21, matrix3d.m20 * matrix3d1.m02 + matrix3d.m21 * matrix3d1.m12 + matrix3d.m22 * matrix3d1.m22);
    }

    public final void mulNormalize(com.maddox.JGP.Matrix3d matrix3d)
    {
        mul(matrix3d);
        SVD(this);
    }

    public final void mulNormalize(com.maddox.JGP.Matrix3d matrix3d, com.maddox.JGP.Matrix3d matrix3d1)
    {
        mul(matrix3d, matrix3d1);
        SVD(this);
    }

    public final void mulTransposeBoth(com.maddox.JGP.Matrix3d matrix3d, com.maddox.JGP.Matrix3d matrix3d1)
    {
        mul(matrix3d1, matrix3d);
        transpose();
    }

    public final void mulTransposeRight(com.maddox.JGP.Matrix3d matrix3d, com.maddox.JGP.Matrix3d matrix3d1)
    {
        set(matrix3d.m00 * matrix3d1.m00 + matrix3d.m01 * matrix3d1.m01 + matrix3d.m02 * matrix3d1.m02, matrix3d.m00 * matrix3d1.m10 + matrix3d.m01 * matrix3d1.m11 + matrix3d.m02 * matrix3d1.m12, matrix3d.m00 * matrix3d1.m20 + matrix3d.m01 * matrix3d1.m21 + matrix3d.m02 * matrix3d1.m22, matrix3d.m10 * matrix3d1.m00 + matrix3d.m11 * matrix3d1.m01 + matrix3d.m12 * matrix3d1.m02, matrix3d.m10 * matrix3d1.m10 + matrix3d.m11 * matrix3d1.m11 + matrix3d.m12 * matrix3d1.m12, matrix3d.m10 * matrix3d1.m20 + matrix3d.m11 * matrix3d1.m21 + matrix3d.m12 * matrix3d1.m22, matrix3d.m20 * matrix3d1.m00 + matrix3d.m21 * matrix3d1.m01 + matrix3d.m22 * matrix3d1.m02, matrix3d.m20 * matrix3d1.m10 + matrix3d.m21 * matrix3d1.m11 + matrix3d.m22 * matrix3d1.m12, matrix3d.m20 * matrix3d1.m20 + matrix3d.m21 * matrix3d1.m21 + matrix3d.m22 * matrix3d1.m22);
    }

    public final void mulTransposeLeft(com.maddox.JGP.Matrix3d matrix3d, com.maddox.JGP.Matrix3d matrix3d1)
    {
        set(matrix3d.m00 * matrix3d1.m00 + matrix3d.m10 * matrix3d1.m10 + matrix3d.m20 * matrix3d1.m20, matrix3d.m00 * matrix3d1.m01 + matrix3d.m10 * matrix3d1.m11 + matrix3d.m20 * matrix3d1.m21, matrix3d.m00 * matrix3d1.m02 + matrix3d.m10 * matrix3d1.m12 + matrix3d.m20 * matrix3d1.m22, matrix3d.m01 * matrix3d1.m00 + matrix3d.m11 * matrix3d1.m10 + matrix3d.m21 * matrix3d1.m20, matrix3d.m01 * matrix3d1.m01 + matrix3d.m11 * matrix3d1.m11 + matrix3d.m21 * matrix3d1.m21, matrix3d.m01 * matrix3d1.m02 + matrix3d.m11 * matrix3d1.m12 + matrix3d.m21 * matrix3d1.m22, matrix3d.m02 * matrix3d1.m00 + matrix3d.m12 * matrix3d1.m10 + matrix3d.m22 * matrix3d1.m20, matrix3d.m02 * matrix3d1.m01 + matrix3d.m12 * matrix3d1.m11 + matrix3d.m22 * matrix3d1.m21, matrix3d.m02 * matrix3d1.m02 + matrix3d.m12 * matrix3d1.m12 + matrix3d.m22 * matrix3d1.m22);
    }

    public final void normalize()
    {
        SVD(this);
    }

    public final void normalize(com.maddox.JGP.Matrix3d matrix3d)
    {
        set(matrix3d);
        SVD(this);
    }

    public final void normalizeCP()
    {
        double d = java.lang.Math.pow(java.lang.Math.abs(determinant()), -0.33333333333333331D);
        mul(d);
    }

    public final void normalizeCP(com.maddox.JGP.Matrix3d matrix3d)
    {
        set(matrix3d);
        normalizeCP();
    }

    public boolean equals(com.maddox.JGP.Matrix3d matrix3d)
    {
        return matrix3d != null && m00 == matrix3d.m00 && m01 == matrix3d.m01 && m02 == matrix3d.m02 && m10 == matrix3d.m10 && m11 == matrix3d.m11 && m12 == matrix3d.m12 && m20 == matrix3d.m20 && m21 == matrix3d.m21 && m22 == matrix3d.m22;
    }

    public boolean equals(java.lang.Object obj)
    {
        return obj != null && (obj instanceof com.maddox.JGP.Matrix3d) && equals((com.maddox.JGP.Matrix3d)obj);
    }

    public boolean epsilonEquals(com.maddox.JGP.Matrix3d matrix3d, double d)
    {
        return java.lang.Math.abs(m00 - matrix3d.m00) <= d && java.lang.Math.abs(m01 - matrix3d.m01) <= d && java.lang.Math.abs(m02 - matrix3d.m02) <= d && java.lang.Math.abs(m10 - matrix3d.m10) <= d && java.lang.Math.abs(m11 - matrix3d.m11) <= d && java.lang.Math.abs(m12 - matrix3d.m12) <= d && java.lang.Math.abs(m20 - matrix3d.m20) <= d && java.lang.Math.abs(m21 - matrix3d.m21) <= d && java.lang.Math.abs(m22 - matrix3d.m22) <= d;
    }

    public int hashCode()
    {
        long l = java.lang.Double.doubleToLongBits(m00);
        int i = (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m01);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m02);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m10);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m11);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m12);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m20);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m21);
        i ^= (int)(l ^ l >> 32);
        l = java.lang.Double.doubleToLongBits(m22);
        i ^= (int)(l ^ l >> 32);
        return i;
    }

    public final void setZero()
    {
        m00 = 0.0D;
        m01 = 0.0D;
        m02 = 0.0D;
        m10 = 0.0D;
        m11 = 0.0D;
        m12 = 0.0D;
        m20 = 0.0D;
        m21 = 0.0D;
        m22 = 0.0D;
    }

    public final void negate()
    {
        m00 = -m00;
        m01 = -m01;
        m02 = -m02;
        m10 = -m10;
        m11 = -m11;
        m12 = -m12;
        m20 = -m20;
        m21 = -m21;
        m22 = -m22;
    }

    public final void negate(com.maddox.JGP.Matrix3d matrix3d)
    {
        set(matrix3d);
        negate();
    }

    public final void transform(com.maddox.JGP.Tuple3d tuple3d)
    {
        transform(tuple3d, tuple3d);
    }

    public final void transform(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1)
    {
        tuple3d1.set(m00 * tuple3d.x + m01 * tuple3d.y + m02 * tuple3d.z, m10 * tuple3d.x + m11 * tuple3d.y + m12 * tuple3d.z, m20 * tuple3d.x + m21 * tuple3d.y + m22 * tuple3d.z);
    }

    public final void transform(com.maddox.JGP.Tuple3f tuple3f)
    {
        transform(tuple3f, tuple3f);
    }

    public final void transform(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        tuple3f1.set((float)(m00 * (double)tuple3f.x + m01 * (double)tuple3f.y + m02 * (double)tuple3f.z), (float)(m10 * (double)tuple3f.x + m11 * (double)tuple3f.y + m12 * (double)tuple3f.z), (float)(m20 * (double)tuple3f.x + m21 * (double)tuple3f.y + m22 * (double)tuple3f.z));
    }

    private void set(double d, double d1, double d2, double d3, double d4, double d5, double d6, 
            double d7, double d8)
    {
        m00 = d;
        m01 = d1;
        m02 = d2;
        m10 = d3;
        m11 = d4;
        m12 = d5;
        m20 = d6;
        m21 = d7;
        m22 = d8;
    }

    private double SVD(com.maddox.JGP.Matrix3d matrix3d)
    {
        double d = java.lang.Math.sqrt((m00 * m00 + m10 * m10 + m20 * m20 + m01 * m01 + m11 * m11 + m21 * m21 + m02 * m02 + m12 * m12 + m22 * m22) / 3D);
        double d1 = d != 0.0D ? 1.0D / d : 0.0D;
        if(matrix3d != null)
        {
            if(matrix3d != this)
                matrix3d.set(this);
            matrix3d.mul(d1);
        }
        return d;
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
    }

    public double m00;
    public double m01;
    public double m02;
    public double m10;
    public double m11;
    public double m12;
    public double m20;
    public double m21;
    public double m22;
}
