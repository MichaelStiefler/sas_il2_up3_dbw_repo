// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Quat4d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple4d, Matrix3d, Matrix4f, Matrix4d, 
//            Matrix3f, AxisAngle4f, AxisAngle4d, Vector3d, 
//            Quat4f, Tuple4f

public class Quat4d extends com.maddox.JGP.Tuple4d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Quat4d(double d, double d1, double d2, double d3)
    {
        super(d, d1, d2, d3);
    }

    public Quat4d(double ad[])
    {
        super(ad);
    }

    public Quat4d(com.maddox.JGP.Quat4d quat4d)
    {
        super(quat4d);
    }

    public Quat4d(com.maddox.JGP.Quat4f quat4f)
    {
        super(quat4f);
    }

    public Quat4d(com.maddox.JGP.Tuple4d tuple4d)
    {
        super(tuple4d);
    }

    public Quat4d(com.maddox.JGP.Tuple4f tuple4f)
    {
        super(tuple4f);
    }

    public Quat4d()
    {
    }

    public final void conjugate(com.maddox.JGP.Quat4d quat4d)
    {
        x = -quat4d.x;
        y = -quat4d.y;
        z = -quat4d.z;
        w = quat4d.w;
    }

    public final void conjugate()
    {
        x = -x;
        y = -y;
        z = -z;
    }

    public final void mul(com.maddox.JGP.Quat4d quat4d, com.maddox.JGP.Quat4d quat4d1)
    {
        set((quat4d.x * quat4d1.w + quat4d.w * quat4d1.x + quat4d.y * quat4d1.z) - quat4d.z * quat4d1.y, (quat4d.y * quat4d1.w + quat4d.w * quat4d1.y + quat4d.z * quat4d1.x) - quat4d.x * quat4d1.z, (quat4d.z * quat4d1.w + quat4d.w * quat4d1.z + quat4d.x * quat4d1.y) - quat4d.y * quat4d1.x, quat4d.w * quat4d1.w - quat4d.x * quat4d1.x - quat4d.y * quat4d1.y - quat4d.z * quat4d1.z);
    }

    public final void mul(com.maddox.JGP.Quat4d quat4d)
    {
        set((x * quat4d.w + w * quat4d.x + y * quat4d.z) - z * quat4d.y, (y * quat4d.w + w * quat4d.y + z * quat4d.x) - x * quat4d.z, (z * quat4d.w + w * quat4d.z + x * quat4d.y) - y * quat4d.x, w * quat4d.w - x * quat4d.x - y * quat4d.y - z * quat4d.z);
    }

    public final void mulInverse(com.maddox.JGP.Quat4d quat4d, com.maddox.JGP.Quat4d quat4d1)
    {
        double d = norm();
        d = d != 0.0D ? 1.0D / d : d;
        set(((quat4d.x * quat4d1.w - quat4d.w * quat4d1.x - quat4d.y * quat4d1.z) + quat4d.z * quat4d1.y) * d, ((quat4d.y * quat4d1.w - quat4d.w * quat4d1.y - quat4d.z * quat4d1.x) + quat4d.x * quat4d1.z) * d, ((quat4d.z * quat4d1.w - quat4d.w * quat4d1.z - quat4d.x * quat4d1.y) + quat4d.y * quat4d1.x) * d, (quat4d.w * quat4d1.w + quat4d.x * quat4d1.x + quat4d.y * quat4d1.y + quat4d.z * quat4d1.z) * d);
    }

    public final void mulInverse(com.maddox.JGP.Quat4d quat4d)
    {
        double d = norm();
        d = d != 0.0D ? 1.0D / d : d;
        set(((x * quat4d.w - w * quat4d.x - y * quat4d.z) + z * quat4d.y) * d, ((y * quat4d.w - w * quat4d.y - z * quat4d.x) + x * quat4d.z) * d, ((z * quat4d.w - w * quat4d.z - x * quat4d.y) + y * quat4d.x) * d, (w * quat4d.w + x * quat4d.x + y * quat4d.y + z * quat4d.z) * d);
    }

    private final double norm()
    {
        return x * x + y * y + z * z + w * w;
    }

    public final void inverse(com.maddox.JGP.Quat4d quat4d)
    {
        double d = quat4d.norm();
        x = -quat4d.x / d;
        y = -quat4d.y / d;
        z = -quat4d.z / d;
        w = quat4d.w / d;
    }

    public final void inverse()
    {
        double d = norm();
        x = -x / d;
        y = -y / d;
        z = -z / d;
        w /= d;
    }

    public final void normalize(com.maddox.JGP.Quat4d quat4d)
    {
        double d = java.lang.Math.sqrt(quat4d.norm());
        x = quat4d.x / d;
        y = quat4d.y / d;
        z = quat4d.z / d;
        w = quat4d.w / d;
    }

    public final void normalize()
    {
        double d = java.lang.Math.sqrt(norm());
        x /= d;
        y /= d;
        z /= d;
        w /= d;
    }

    public final void set(com.maddox.JGP.Matrix4f matrix4f)
    {
        setFromMat(matrix4f.m00, matrix4f.m01, matrix4f.m02, matrix4f.m10, matrix4f.m11, matrix4f.m12, matrix4f.m20, matrix4f.m21, matrix4f.m22);
    }

    public final void set(com.maddox.JGP.Matrix4d matrix4d)
    {
        setFromMat(matrix4d.m00, matrix4d.m01, matrix4d.m02, matrix4d.m10, matrix4d.m11, matrix4d.m12, matrix4d.m20, matrix4d.m21, matrix4d.m22);
    }

    public final void set(com.maddox.JGP.Matrix3f matrix3f)
    {
        setFromMat(matrix3f.m00, matrix3f.m01, matrix3f.m02, matrix3f.m10, matrix3f.m11, matrix3f.m12, matrix3f.m20, matrix3f.m21, matrix3f.m22);
    }

    public final void set(com.maddox.JGP.Matrix3d matrix3d)
    {
        setFromMat(matrix3d.m00, matrix3d.m01, matrix3d.m02, matrix3d.m10, matrix3d.m11, matrix3d.m12, matrix3d.m20, matrix3d.m21, matrix3d.m22);
    }

    public final void set(com.maddox.JGP.AxisAngle4f axisangle4f)
    {
        x = axisangle4f.x;
        y = axisangle4f.y;
        z = axisangle4f.z;
        double d = java.lang.Math.sqrt(x * x + y * y + z * z);
        double d1 = java.lang.Math.sin(0.5D * (double)axisangle4f.angle) / d;
        x *= d1;
        y *= d1;
        z *= d1;
        w = java.lang.Math.cos(0.5D * (double)axisangle4f.angle);
    }

    public final void set(com.maddox.JGP.AxisAngle4d axisangle4d)
    {
        x = axisangle4d.x;
        y = axisangle4d.y;
        z = axisangle4d.z;
        double d = java.lang.Math.sqrt(x * x + y * y + z * z);
        double d1 = java.lang.Math.sin(0.5D * axisangle4d.angle) / d;
        x *= d1;
        y *= d1;
        z *= d1;
        w = java.lang.Math.cos(0.5D * axisangle4d.angle);
    }

    public void set(com.maddox.JGP.Vector3d vector3d)
    {
        double d = vector3d.length() + 9.9999999999999996E-076D;
        double d1 = (float)(java.lang.Math.sin(0.5D * d) / d);
        x = vector3d.x * d1;
        y = vector3d.y * d1;
        z = vector3d.z * d1;
        w = (float)java.lang.Math.cos(0.5D * d);
    }

    public final void interpolate(com.maddox.JGP.Quat4d quat4d, double d)
    {
        normalize();
        double d1 = java.lang.Math.sqrt(quat4d.norm());
        double d2 = quat4d.x / d1;
        double d3 = quat4d.y / d1;
        double d4 = quat4d.z / d1;
        double d5 = quat4d.w / d1;
        double d6 = x * d2 + y * d3 + z * d4 + w * d5;
        if(d6 < 0.0D)
        {
            d6 = -d6;
            d2 = -d2;
            d3 = -d3;
            d4 = -d4;
            d5 = -d5;
        }
        if(d6 > 1.0D)
            return;
        d6 = java.lang.Math.acos(d6);
        double d7 = java.lang.Math.sin(d6);
        if(d7 == 0.0D)
        {
            return;
        } else
        {
            double d8 = java.lang.Math.sin((1.0D - d) * d6) / d7;
            d6 = java.lang.Math.sin(d * d6) / d7;
            x = d8 * x + d6 * d2;
            y = d8 * y + d6 * d3;
            z = d8 * z + d6 * d4;
            w = d8 * w + d6 * d5;
            return;
        }
    }

    public final void interpolate(com.maddox.JGP.Quat4d quat4d, com.maddox.JGP.Quat4d quat4d1, double d)
    {
        if(this != quat4d1)
        {
            set(quat4d);
            interpolate(quat4d1, d);
        } else
        {
            interpolate(quat4d, 1.0D - d);
        }
    }

    private void setFromMat(double d, double d1, double d2, double d3, double d4, double d5, double d6, 
            double d7, double d8)
    {
        double d13 = d + d4 + d8;
        if(d13 >= 0.0D)
        {
            double d9 = java.lang.Math.sqrt(d13 + 1.0D);
            w = d9 * 0.5D;
            d9 = 0.5D / d9;
            x = (d7 - d5) * d9;
            y = (d2 - d6) * d9;
            z = (d3 - d1) * d9;
        } else
        {
            double d14 = java.lang.Math.max(java.lang.Math.max(d, d4), d8);
            if(d14 == d)
            {
                double d10 = java.lang.Math.sqrt((d - (d4 + d8)) + 1.0D);
                x = d10 * 0.5D;
                d10 = 0.5D / d10;
                y = (d1 + d3) * d10;
                z = (d6 + d2) * d10;
                w = (d7 - d5) * d10;
            } else
            if(d14 == d4)
            {
                double d11 = java.lang.Math.sqrt((d4 - (d8 + d)) + 1.0D);
                y = d11 * 0.5D;
                d11 = 0.5D / d11;
                z = (d5 + d7) * d11;
                x = (d1 + d3) * d11;
                w = (d2 - d6) * d11;
            } else
            {
                double d12 = java.lang.Math.sqrt((d8 - (d + d4)) + 1.0D);
                z = d12 * 0.5D;
                d12 = 0.5D / d12;
                x = (d6 + d2) * d12;
                y = (d5 + d7) * d12;
                w = (d3 - d1) * d12;
            }
        }
    }

    public void setEulers(double d, double d1, double d2)
    {
        double d3 = java.lang.Math.sin(d * 0.5D);
        double d4 = java.lang.Math.cos(d * 0.5D);
        double d5 = java.lang.Math.sin(d1 * 0.5D);
        double d6 = java.lang.Math.cos(d1 * 0.5D);
        double d7 = java.lang.Math.sin(d2 * 0.5D);
        double d8 = java.lang.Math.cos(d2 * 0.5D);
        x = d4 * d6 * d7 - d3 * d5 * d8;
        y = d4 * d5 * d8 + d3 * d6 * d7;
        z = d3 * d6 * d8 - d4 * d5 * d7;
        w = d4 * d6 * d8 + d3 * d5 * d7;
    }

    public void getEulers(double ad[])
    {
        MQ.set(this);
        MQ.getEulers(ad);
    }

    private static final com.maddox.JGP.Matrix3d MQ = new Matrix3d();

}
