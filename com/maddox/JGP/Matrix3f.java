// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Matrix3f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Matrix3d, Matrix4f, Tuple3f, Quat4f, 
//            AxisAngle4f, AxisAngle4d, Quat4d

public class Matrix3f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Matrix3f(float f, float f1, float f2, float f3, float f4, float f5, float f6, 
            float f7, float f8)
    {
        set(f, f1, f2, f3, f4, f5, f6, f7, f8);
    }

    public Matrix3f(float af[])
    {
        set(af);
    }

    public Matrix3f(com.maddox.JGP.Matrix3d matrix3d)
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

    public Matrix3f(com.maddox.JGP.Matrix3f matrix3f)
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

    public Matrix3f()
    {
        setZero();
    }

    public Matrix3f(com.maddox.JGP.Matrix4f matrix4f)
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

    public Matrix3f(float af[][])
    {
        m00 = af[0][0];
        m01 = af[0][1];
        m02 = af[0][2];
        m10 = af[1][0];
        m11 = af[1][1];
        m12 = af[1][2];
        m20 = af[2][0];
        m21 = af[2][1];
        m22 = af[2][2];
    }

    public Matrix3f(float f)
    {
        set(f);
    }

    public Matrix3f(com.maddox.JGP.AxisAngle4f axisangle4f)
    {
        set(axisangle4f);
    }

    public Matrix3f(com.maddox.JGP.Quat4f quat4f)
    {
        set(quat4f);
    }

    public final void set(com.maddox.JGP.Matrix4f matrix4f)
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

    public final void set(float af[][])
    {
        m00 = af[0][0];
        m01 = af[0][1];
        m02 = af[0][2];
        m10 = af[1][0];
        m11 = af[1][1];
        m12 = af[1][2];
        m20 = af[2][0];
        m21 = af[2][1];
        m22 = af[2][2];
    }

    public java.lang.String toString()
    {
        java.lang.String s = java.lang.System.getProperty("line.separator");
        return "[" + s + "  [" + m00 + "\t" + m01 + "\t" + m02 + "]" + s + "  [" + m10 + "\t" + m11 + "\t" + m12 + "]" + s + "  [" + m20 + "\t" + m21 + "\t" + m22 + "] ]";
    }

    public final void setIdentity()
    {
        m00 = 1.0F;
        m01 = 0.0F;
        m02 = 0.0F;
        m10 = 0.0F;
        m11 = 1.0F;
        m12 = 0.0F;
        m20 = 0.0F;
        m21 = 0.0F;
        m22 = 1.0F;
    }

    public final void setScale(float f)
    {
        SVD(this);
        mul(f);
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
                m02 = f;
        } else
        if(i == 1)
        {
            if(j == 0)
                m10 = f;
            else
            if(j == 1)
                m11 = f;
            else
                m12 = f;
        } else
        if(j == 0)
            m20 = f;
        else
        if(j == 1)
            m21 = f;
        else
            m22 = f;
    }

    public final float getElement(int i, int j)
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

    public final void setRow(int i, float f, float f1, float f2)
    {
        if(i == 0)
        {
            m00 = f;
            m01 = f1;
            m02 = f2;
        } else
        if(i == 1)
        {
            m10 = f;
            m11 = f1;
            m12 = f2;
        } else
        if(i == 2)
        {
            m20 = f;
            m21 = f1;
            m22 = f2;
        }
    }

    public final void setRow(int i, com.maddox.JGP.Tuple3f tuple3f)
    {
        if(i == 0)
        {
            m00 = tuple3f.x;
            m01 = tuple3f.y;
            m02 = tuple3f.z;
        } else
        if(i == 1)
        {
            m10 = tuple3f.x;
            m11 = tuple3f.y;
            m12 = tuple3f.z;
        } else
        if(i == 2)
        {
            m20 = tuple3f.x;
            m21 = tuple3f.y;
            m22 = tuple3f.z;
        }
    }

    public final void getRow(int i, float af[])
    {
        if(i == 0)
        {
            af[0] = m00;
            af[1] = m01;
            af[2] = m02;
        } else
        if(i == 1)
        {
            af[0] = m10;
            af[1] = m11;
            af[2] = m12;
        } else
        if(i == 2)
        {
            af[0] = m20;
            af[1] = m21;
            af[2] = m22;
        }
    }

    public final void getRow(int i, com.maddox.JGP.Tuple3f tuple3f)
    {
        if(i == 0)
        {
            tuple3f.x = m00;
            tuple3f.y = m01;
            tuple3f.z = m02;
        } else
        if(i == 1)
        {
            tuple3f.x = m10;
            tuple3f.y = m11;
            tuple3f.z = m12;
        } else
        if(i == 2)
        {
            tuple3f.x = m20;
            tuple3f.y = m21;
            tuple3f.z = m22;
        }
    }

    public final void setRow(int i, float af[])
    {
        if(i == 0)
        {
            m00 = af[0];
            m01 = af[1];
            m02 = af[2];
        } else
        if(i == 1)
        {
            m10 = af[0];
            m11 = af[1];
            m12 = af[2];
        } else
        if(i == 2)
        {
            m20 = af[0];
            m21 = af[1];
            m22 = af[2];
        }
    }

    public final void setColumn(int i, float f, float f1, float f2)
    {
        if(i == 0)
        {
            m00 = f;
            m10 = f1;
            m20 = f2;
        } else
        if(i == 1)
        {
            m01 = f;
            m11 = f1;
            m21 = f2;
        } else
        if(i == 2)
        {
            m02 = f;
            m12 = f1;
            m22 = f2;
        }
    }

    public final void setColumn(int i, com.maddox.JGP.Tuple3f tuple3f)
    {
        if(i == 0)
        {
            m00 = tuple3f.x;
            m10 = tuple3f.y;
            m20 = tuple3f.z;
        } else
        if(i == 1)
        {
            m01 = tuple3f.x;
            m11 = tuple3f.y;
            m21 = tuple3f.z;
        } else
        if(i == 2)
        {
            m02 = tuple3f.x;
            m12 = tuple3f.y;
            m22 = tuple3f.z;
        }
    }

    public final void setColumn(int i, float af[])
    {
        if(i == 0)
        {
            m00 = af[0];
            m10 = af[1];
            m20 = af[2];
        } else
        if(i == 1)
        {
            m01 = af[0];
            m11 = af[1];
            m21 = af[2];
        } else
        if(i == 2)
        {
            m02 = af[0];
            m12 = af[1];
            m22 = af[2];
        }
    }

    public final void getColumn(int i, com.maddox.JGP.Tuple3f tuple3f)
    {
        if(i == 0)
        {
            tuple3f.x = m00;
            tuple3f.y = m10;
            tuple3f.z = m20;
        } else
        if(i == 1)
        {
            tuple3f.x = m01;
            tuple3f.y = m11;
            tuple3f.z = m21;
        } else
        if(i == 2)
        {
            tuple3f.x = m02;
            tuple3f.y = m12;
            tuple3f.z = m22;
        }
    }

    public final void getColumn(int i, float af[])
    {
        if(i == 0)
        {
            af[0] = m00;
            af[1] = m10;
            af[2] = m20;
        } else
        if(i == 1)
        {
            af[0] = m01;
            af[1] = m11;
            af[2] = m21;
        } else
        if(i == 2)
        {
            af[0] = m02;
            af[1] = m12;
            af[2] = m22;
        }
    }

    public final void get(float af[][])
    {
        af[0][0] = m00;
        af[0][1] = m01;
        af[0][2] = m02;
        af[1][0] = m10;
        af[1][1] = m11;
        af[1][2] = m12;
        af[2][0] = m20;
        af[2][1] = m21;
        af[2][2] = m22;
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
    }

    public final float getScale()
    {
        return SVD(null);
    }

    public final void add(float f)
    {
        m00 += f;
        m01 += f;
        m02 += f;
        m10 += f;
        m11 += f;
        m12 += f;
        m20 += f;
        m21 += f;
        m22 += f;
    }

    public final void add(float f, com.maddox.JGP.Matrix3f matrix3f)
    {
        set(matrix3f);
        add(f);
    }

    public final void add(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Matrix3f matrix3f1)
    {
        set(matrix3f.m00 + matrix3f1.m00, matrix3f.m01 + matrix3f1.m01, matrix3f.m02 + matrix3f1.m02, matrix3f.m10 + matrix3f1.m10, matrix3f.m11 + matrix3f1.m11, matrix3f.m12 + matrix3f1.m12, matrix3f.m20 + matrix3f1.m20, matrix3f.m21 + matrix3f1.m21, matrix3f.m22 + matrix3f1.m22);
    }

    public final void add(com.maddox.JGP.Matrix3f matrix3f)
    {
        m00 += matrix3f.m00;
        m01 += matrix3f.m01;
        m02 += matrix3f.m02;
        m10 += matrix3f.m10;
        m11 += matrix3f.m11;
        m12 += matrix3f.m12;
        m20 += matrix3f.m20;
        m21 += matrix3f.m21;
        m22 += matrix3f.m22;
    }

    public final void sub(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Matrix3f matrix3f1)
    {
        set(matrix3f.m00 - matrix3f1.m00, matrix3f.m01 - matrix3f1.m01, matrix3f.m02 - matrix3f1.m02, matrix3f.m10 - matrix3f1.m10, matrix3f.m11 - matrix3f1.m11, matrix3f.m12 - matrix3f1.m12, matrix3f.m20 - matrix3f1.m20, matrix3f.m21 - matrix3f1.m21, matrix3f.m22 - matrix3f1.m22);
    }

    public final void sub(com.maddox.JGP.Matrix3f matrix3f)
    {
        m00 -= matrix3f.m00;
        m01 -= matrix3f.m01;
        m02 -= matrix3f.m02;
        m10 -= matrix3f.m10;
        m11 -= matrix3f.m11;
        m12 -= matrix3f.m12;
        m20 -= matrix3f.m20;
        m21 -= matrix3f.m21;
        m22 -= matrix3f.m22;
    }

    public final void transpose()
    {
        float f = m01;
        m01 = m10;
        m10 = f;
        f = m02;
        m02 = m20;
        m20 = f;
        f = m12;
        m12 = m21;
        m21 = f;
    }

    public final void transpose(com.maddox.JGP.Matrix3f matrix3f)
    {
        set(matrix3f);
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

    public final void set(com.maddox.JGP.AxisAngle4d axisangle4d)
    {
        setFromAxisAngle(axisangle4d.x, axisangle4d.y, axisangle4d.z, axisangle4d.angle);
    }

    public final void set(com.maddox.JGP.Quat4d quat4d)
    {
        setFromQuat(quat4d.x, quat4d.y, quat4d.z, quat4d.w);
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

    public final void set(com.maddox.JGP.Matrix3d matrix3d)
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

    public final void set(float af[])
    {
        m00 = af[0];
        m01 = af[1];
        m02 = af[2];
        m10 = af[3];
        m11 = af[4];
        m12 = af[5];
        m20 = af[6];
        m21 = af[7];
        m22 = af[8];
    }

    public final void invert(com.maddox.JGP.Matrix3f matrix3f)
    {
        set(matrix3f);
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
            mul((float)d);
            return;
        }
    }

    public final float determinant()
    {
        return (m00 * (m11 * m22 - m21 * m12) - m01 * (m10 * m22 - m20 * m12)) + m02 * (m10 * m21 - m20 * m11);
    }

    public final void set(float f)
    {
        m00 = f;
        m01 = 0.0F;
        m02 = 0.0F;
        m10 = 0.0F;
        m11 = f;
        m12 = 0.0F;
        m20 = 0.0F;
        m21 = 0.0F;
        m22 = f;
    }

    public final void rotX(float f)
    {
        double d = java.lang.Math.cos(f);
        double d1 = java.lang.Math.sin(f);
        m00 = 1.0F;
        m01 = 0.0F;
        m02 = 0.0F;
        m10 = 0.0F;
        m11 = (float)d;
        m12 = (float)(-d1);
        m20 = 0.0F;
        m21 = (float)d1;
        m22 = (float)d;
    }

    public final void rotY(float f)
    {
        double d = java.lang.Math.cos(f);
        double d1 = java.lang.Math.sin(f);
        m00 = (float)d;
        m01 = 0.0F;
        m02 = (float)d1;
        m10 = 0.0F;
        m11 = 1.0F;
        m12 = 0.0F;
        m20 = (float)(-d1);
        m21 = 0.0F;
        m22 = (float)d;
    }

    public final void rotZ(float f)
    {
        double d = java.lang.Math.cos(f);
        double d1 = java.lang.Math.sin(f);
        m00 = (float)d;
        m01 = (float)(-d1);
        m02 = 0.0F;
        m10 = (float)d1;
        m11 = (float)d;
        m12 = 0.0F;
        m20 = 0.0F;
        m21 = 0.0F;
        m22 = 1.0F;
    }

    public final void mul(float f)
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

    public final void mul(float f, com.maddox.JGP.Matrix3f matrix3f)
    {
        set(matrix3f);
        mul(f);
    }

    public final void mul(com.maddox.JGP.Matrix3f matrix3f)
    {
        mul(this, matrix3f);
    }

    public final void mul(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Matrix3f matrix3f1)
    {
        set(matrix3f.m00 * matrix3f1.m00 + matrix3f.m01 * matrix3f1.m10 + matrix3f.m02 * matrix3f1.m20, matrix3f.m00 * matrix3f1.m01 + matrix3f.m01 * matrix3f1.m11 + matrix3f.m02 * matrix3f1.m21, matrix3f.m00 * matrix3f1.m02 + matrix3f.m01 * matrix3f1.m12 + matrix3f.m02 * matrix3f1.m22, matrix3f.m10 * matrix3f1.m00 + matrix3f.m11 * matrix3f1.m10 + matrix3f.m12 * matrix3f1.m20, matrix3f.m10 * matrix3f1.m01 + matrix3f.m11 * matrix3f1.m11 + matrix3f.m12 * matrix3f1.m21, matrix3f.m10 * matrix3f1.m02 + matrix3f.m11 * matrix3f1.m12 + matrix3f.m12 * matrix3f1.m22, matrix3f.m20 * matrix3f1.m00 + matrix3f.m21 * matrix3f1.m10 + matrix3f.m22 * matrix3f1.m20, matrix3f.m20 * matrix3f1.m01 + matrix3f.m21 * matrix3f1.m11 + matrix3f.m22 * matrix3f1.m21, matrix3f.m20 * matrix3f1.m02 + matrix3f.m21 * matrix3f1.m12 + matrix3f.m22 * matrix3f1.m22);
    }

    public final void mulNormalize(com.maddox.JGP.Matrix3f matrix3f)
    {
        mul(matrix3f);
        SVD(this);
    }

    public final void mulNormalize(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Matrix3f matrix3f1)
    {
        mul(matrix3f, matrix3f1);
        SVD(this);
    }

    public final void mulTransposeBoth(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Matrix3f matrix3f1)
    {
        mul(matrix3f1, matrix3f);
        transpose();
    }

    public final void mulTransposeRight(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Matrix3f matrix3f1)
    {
        set(matrix3f.m00 * matrix3f1.m00 + matrix3f.m01 * matrix3f1.m01 + matrix3f.m02 * matrix3f1.m02, matrix3f.m00 * matrix3f1.m10 + matrix3f.m01 * matrix3f1.m11 + matrix3f.m02 * matrix3f1.m12, matrix3f.m00 * matrix3f1.m20 + matrix3f.m01 * matrix3f1.m21 + matrix3f.m02 * matrix3f1.m22, matrix3f.m10 * matrix3f1.m00 + matrix3f.m11 * matrix3f1.m01 + matrix3f.m12 * matrix3f1.m02, matrix3f.m10 * matrix3f1.m10 + matrix3f.m11 * matrix3f1.m11 + matrix3f.m12 * matrix3f1.m12, matrix3f.m10 * matrix3f1.m20 + matrix3f.m11 * matrix3f1.m21 + matrix3f.m12 * matrix3f1.m22, matrix3f.m20 * matrix3f1.m00 + matrix3f.m21 * matrix3f1.m01 + matrix3f.m22 * matrix3f1.m02, matrix3f.m20 * matrix3f1.m10 + matrix3f.m21 * matrix3f1.m11 + matrix3f.m22 * matrix3f1.m12, matrix3f.m20 * matrix3f1.m20 + matrix3f.m21 * matrix3f1.m21 + matrix3f.m22 * matrix3f1.m22);
    }

    public final void mulTransposeLeft(com.maddox.JGP.Matrix3f matrix3f, com.maddox.JGP.Matrix3f matrix3f1)
    {
        set(matrix3f.m00 * matrix3f1.m00 + matrix3f.m10 * matrix3f1.m10 + matrix3f.m20 * matrix3f1.m20, matrix3f.m00 * matrix3f1.m01 + matrix3f.m10 * matrix3f1.m11 + matrix3f.m20 * matrix3f1.m21, matrix3f.m00 * matrix3f1.m02 + matrix3f.m10 * matrix3f1.m12 + matrix3f.m20 * matrix3f1.m22, matrix3f.m01 * matrix3f1.m00 + matrix3f.m11 * matrix3f1.m10 + matrix3f.m21 * matrix3f1.m20, matrix3f.m01 * matrix3f1.m01 + matrix3f.m11 * matrix3f1.m11 + matrix3f.m21 * matrix3f1.m21, matrix3f.m01 * matrix3f1.m02 + matrix3f.m11 * matrix3f1.m12 + matrix3f.m21 * matrix3f1.m22, matrix3f.m02 * matrix3f1.m00 + matrix3f.m12 * matrix3f1.m10 + matrix3f.m22 * matrix3f1.m20, matrix3f.m02 * matrix3f1.m01 + matrix3f.m12 * matrix3f1.m11 + matrix3f.m22 * matrix3f1.m21, matrix3f.m02 * matrix3f1.m02 + matrix3f.m12 * matrix3f1.m12 + matrix3f.m22 * matrix3f1.m22);
    }

    public final void normalize()
    {
        SVD(this);
    }

    public final void normalize(com.maddox.JGP.Matrix3f matrix3f)
    {
        set(matrix3f);
        SVD(this);
    }

    public final void normalizeCP()
    {
        double d = java.lang.Math.pow(determinant(), -0.33333333333333331D);
        mul((float)d);
    }

    public final void normalizeCP(com.maddox.JGP.Matrix3f matrix3f)
    {
        set(matrix3f);
        normalizeCP();
    }

    public boolean equals(com.maddox.JGP.Matrix3f matrix3f)
    {
        return matrix3f != null && m00 == matrix3f.m00 && m01 == matrix3f.m01 && m02 == matrix3f.m02 && m10 == matrix3f.m10 && m11 == matrix3f.m11 && m12 == matrix3f.m12 && m20 == matrix3f.m20 && m21 == matrix3f.m21 && m22 == matrix3f.m22;
    }

    public boolean equals(java.lang.Object obj)
    {
        return obj != null && (obj instanceof com.maddox.JGP.Matrix3f) && equals((com.maddox.JGP.Matrix3f)obj);
    }

    public boolean epsilonEquals(com.maddox.JGP.Matrix3f matrix3f, double d)
    {
        return (double)java.lang.Math.abs(m00 - matrix3f.m00) <= d && (double)java.lang.Math.abs(m01 - matrix3f.m01) <= d && (double)java.lang.Math.abs(m02 - matrix3f.m02) <= d && (double)java.lang.Math.abs(m10 - matrix3f.m10) <= d && (double)java.lang.Math.abs(m11 - matrix3f.m11) <= d && (double)java.lang.Math.abs(m12 - matrix3f.m12) <= d && (double)java.lang.Math.abs(m20 - matrix3f.m20) <= d && (double)java.lang.Math.abs(m21 - matrix3f.m21) <= d && (double)java.lang.Math.abs(m22 - matrix3f.m22) <= d;
    }

    public int hashCode()
    {
        return java.lang.Float.floatToIntBits(m00) ^ java.lang.Float.floatToIntBits(m01) ^ java.lang.Float.floatToIntBits(m02) ^ java.lang.Float.floatToIntBits(m10) ^ java.lang.Float.floatToIntBits(m11) ^ java.lang.Float.floatToIntBits(m12) ^ java.lang.Float.floatToIntBits(m20) ^ java.lang.Float.floatToIntBits(m21) ^ java.lang.Float.floatToIntBits(m22);
    }

    public final void setZero()
    {
        m00 = 0.0F;
        m01 = 0.0F;
        m02 = 0.0F;
        m10 = 0.0F;
        m11 = 0.0F;
        m12 = 0.0F;
        m20 = 0.0F;
        m21 = 0.0F;
        m22 = 0.0F;
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

    public final void negate(com.maddox.JGP.Matrix3f matrix3f)
    {
        set(matrix3f);
        negate();
    }

    public final void transform(com.maddox.JGP.Tuple3f tuple3f)
    {
        transform(tuple3f, tuple3f);
    }

    public final void transform(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        tuple3f1.set(m00 * tuple3f.x + m01 * tuple3f.y + m02 * tuple3f.z, m10 * tuple3f.x + m11 * tuple3f.y + m12 * tuple3f.z, m20 * tuple3f.x + m21 * tuple3f.y + m22 * tuple3f.z);
    }

    private void set(float f, float f1, float f2, float f3, float f4, float f5, float f6, 
            float f7, float f8)
    {
        m00 = f;
        m01 = f1;
        m02 = f2;
        m10 = f3;
        m11 = f4;
        m12 = f5;
        m20 = f6;
        m21 = f7;
        m22 = f8;
    }

    private float SVD(com.maddox.JGP.Matrix3f matrix3f)
    {
        float f = (float)java.lang.Math.sqrt((double)(m00 * m00 + m10 * m10 + m20 * m20 + m01 * m01 + m11 * m11 + m21 * m21 + m02 * m02 + m12 * m12 + m22 * m22) / 3D);
        float f1 = f != 0.0F ? 1.0F / f : 0.0F;
        if(matrix3f != null)
        {
            if(matrix3f != this)
                matrix3f.set(this);
            matrix3f.mul(f1);
        }
        return f;
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

    public float m00;
    public float m01;
    public float m02;
    public float m10;
    public float m11;
    public float m12;
    public float m20;
    public float m21;
    public float m22;
}
