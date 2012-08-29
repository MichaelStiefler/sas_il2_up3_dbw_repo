// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Quat4f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple4f, Matrix3f, Vector3f, Matrix4f, 
//            Matrix4d, Matrix3d, AxisAngle4f, AxisAngle4d, 
//            Quat4d, Tuple4d, Tuple3f

public class Quat4f extends com.maddox.JGP.Tuple4f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Quat4f(float f, float f1, float f2, float f3)
    {
        super(f, f1, f2, f3);
    }

    public Quat4f(float af[])
    {
        super(af);
    }

    public Quat4f(com.maddox.JGP.Quat4f quat4f)
    {
        super(quat4f);
    }

    public Quat4f(com.maddox.JGP.Quat4d quat4d)
    {
        super(quat4d);
    }

    public Quat4f(com.maddox.JGP.Tuple4d tuple4d)
    {
        super(tuple4d);
    }

    public Quat4f(com.maddox.JGP.Tuple4f tuple4f)
    {
        super(tuple4f);
    }

    public Quat4f()
    {
    }

    public Quat4f(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        set(tuple3f, tuple3f1);
    }

    public final void setIdent()
    {
        x = 0.0F;
        y = 0.0F;
        z = 0.0F;
        w = 1.0F;
    }

    public final void conjugate(com.maddox.JGP.Quat4f quat4f)
    {
        x = -quat4f.x;
        y = -quat4f.y;
        z = -quat4f.z;
        w = quat4f.w;
    }

    public final void conjugate()
    {
        x = -x;
        y = -y;
        z = -z;
    }

    public final void mul(com.maddox.JGP.Quat4f quat4f, com.maddox.JGP.Quat4f quat4f1)
    {
        set((quat4f.x * quat4f1.w + quat4f.w * quat4f1.x + quat4f.y * quat4f1.z) - quat4f.z * quat4f1.y, (quat4f.y * quat4f1.w + quat4f.w * quat4f1.y + quat4f.z * quat4f1.x) - quat4f.x * quat4f1.z, (quat4f.z * quat4f1.w + quat4f.w * quat4f1.z + quat4f.x * quat4f1.y) - quat4f.y * quat4f1.x, quat4f.w * quat4f1.w - quat4f.x * quat4f1.x - quat4f.y * quat4f1.y - quat4f.z * quat4f1.z);
    }

    public final void mul(com.maddox.JGP.Quat4f quat4f)
    {
        set((x * quat4f.w + w * quat4f.x + y * quat4f.z) - z * quat4f.y, (y * quat4f.w + w * quat4f.y + z * quat4f.x) - x * quat4f.z, (z * quat4f.w + w * quat4f.z + x * quat4f.y) - y * quat4f.x, w * quat4f.w - x * quat4f.x - y * quat4f.y - z * quat4f.z);
    }

    public final void mulInverse(com.maddox.JGP.Quat4f quat4f, com.maddox.JGP.Quat4f quat4f1)
    {
        float f = norm();
        f = (double)f != 0.0D ? 1.0F / f : f;
        set(((quat4f.x * quat4f1.w - quat4f.w * quat4f1.x - quat4f.y * quat4f1.z) + quat4f.z * quat4f1.y) * f, ((quat4f.y * quat4f1.w - quat4f.w * quat4f1.y - quat4f.z * quat4f1.x) + quat4f.x * quat4f1.z) * f, ((quat4f.z * quat4f1.w - quat4f.w * quat4f1.z - quat4f.x * quat4f1.y) + quat4f.y * quat4f1.x) * f, (quat4f.w * quat4f1.w + quat4f.x * quat4f1.x + quat4f.y * quat4f1.y + quat4f.z * quat4f1.z) * f);
    }

    public final void mulInverse(com.maddox.JGP.Quat4f quat4f)
    {
        float f = norm();
        f = (double)f != 0.0D ? 1.0F / f : f;
        set(((x * quat4f.w - w * quat4f.x - y * quat4f.z) + z * quat4f.y) * f, ((y * quat4f.w - w * quat4f.y - z * quat4f.x) + x * quat4f.z) * f, ((z * quat4f.w - w * quat4f.z - x * quat4f.y) + y * quat4f.x) * f, (w * quat4f.w + x * quat4f.x + y * quat4f.y + z * quat4f.z) * f);
    }

    private final float norm()
    {
        return x * x + y * y + z * z + w * w;
    }

    public final void inverse(com.maddox.JGP.Quat4f quat4f)
    {
        float f = quat4f.norm();
        x = -quat4f.x / f;
        y = -quat4f.y / f;
        z = -quat4f.z / f;
        w = quat4f.w / f;
    }

    public final void inverse()
    {
        float f = norm();
        x = -x / f;
        y = -y / f;
        z = -z / f;
        w = w / f;
    }

    public final void normalize(com.maddox.JGP.Quat4f quat4f)
    {
        float f = (float)java.lang.Math.sqrt(quat4f.norm());
        x = quat4f.x / f;
        y = quat4f.y / f;
        z = quat4f.z / f;
        w = quat4f.w / f;
    }

    public final void normalize()
    {
        float f = (float)java.lang.Math.sqrt(norm());
        x /= f;
        y /= f;
        z /= f;
        w /= f;
    }

    public final void set(com.maddox.JGP.Matrix4f matrix4f)
    {
        setFromMat(matrix4f.m00, matrix4f.m01, matrix4f.m02, matrix4f.m10, matrix4f.m11, matrix4f.m12, matrix4f.m20, matrix4f.m21, matrix4f.m22);
    }

    public final void set(com.maddox.JGP.Matrix4d matrix4d)
    {
        setFromMat((float)matrix4d.m00, (float)matrix4d.m01, (float)matrix4d.m02, (float)matrix4d.m10, (float)matrix4d.m11, (float)matrix4d.m12, (float)matrix4d.m20, (float)matrix4d.m21, (float)matrix4d.m22);
    }

    public final void set(com.maddox.JGP.Matrix3f matrix3f)
    {
        setFromMat(matrix3f.m00, matrix3f.m01, matrix3f.m02, matrix3f.m10, matrix3f.m11, matrix3f.m12, matrix3f.m20, matrix3f.m21, matrix3f.m22);
    }

    public final void set(com.maddox.JGP.Matrix3d matrix3d)
    {
        setFromMat((float)matrix3d.m00, (float)matrix3d.m01, (float)matrix3d.m02, (float)matrix3d.m10, (float)matrix3d.m11, (float)matrix3d.m12, (float)matrix3d.m20, (float)matrix3d.m21, (float)matrix3d.m22);
    }

    public final void set(com.maddox.JGP.AxisAngle4f axisangle4f)
    {
        x = axisangle4f.x;
        y = axisangle4f.y;
        z = axisangle4f.z;
        float f = (float)java.lang.Math.sqrt(x * x + y * y + z * z) + 1E-035F;
        float f1 = (float)(java.lang.Math.sin(0.5D * (double)axisangle4f.angle) / (double)f);
        x *= f1;
        y *= f1;
        z *= f1;
        w = (float)java.lang.Math.cos(0.5D * (double)axisangle4f.angle);
    }

    public void set(com.maddox.JGP.Vector3f vector3f)
    {
        float f = vector3f.length() + 1E-035F;
        float f1 = (float)(java.lang.Math.sin(0.5D * (double)f) / (double)f);
        x = vector3f.x * f1;
        y = vector3f.y * f1;
        z = vector3f.z * f1;
        w = (float)java.lang.Math.cos(0.5D * (double)f);
    }

    public final void set(com.maddox.JGP.AxisAngle4d axisangle4d)
    {
        x = (float)axisangle4d.x;
        y = (float)axisangle4d.y;
        z = (float)axisangle4d.z;
        float f = (float)java.lang.Math.sqrt(x * x + y * y + z * z);
        float f1 = (float)(java.lang.Math.sin(0.5D * axisangle4d.angle) / (double)f);
        x *= f1;
        y *= f1;
        z *= f1;
        w = (float)java.lang.Math.cos(0.5D * axisangle4d.angle);
    }

    public final void interpolate(com.maddox.JGP.Quat4f quat4f, float f)
    {
        normalize();
        float f1 = (float)java.lang.Math.sqrt(quat4f.norm());
        float f2 = quat4f.x / f1;
        float f3 = quat4f.y / f1;
        float f4 = quat4f.z / f1;
        float f5 = quat4f.w / f1;
        float f6 = x * f2 + y * f3 + z * f4 + w * f5;
        if(f6 < 0.0F)
        {
            f6 = -f6;
            f2 = -f2;
            f3 = -f3;
            f4 = -f4;
            f5 = -f5;
        }
        if(f6 >= 1.0F)
            return;
        f6 = (float)java.lang.Math.acos(f6);
        float f7 = (float)java.lang.Math.sin(f6);
        if(f7 == 0.0F)
        {
            return;
        } else
        {
            float f8 = (float)java.lang.Math.sin((1.0D - (double)f) * (double)f6) / f7;
            f6 = (float)java.lang.Math.sin(f * f6) / f7;
            x = f8 * x + f6 * f2;
            y = f8 * y + f6 * f3;
            z = f8 * z + f6 * f4;
            w = f8 * w + f6 * f5;
            return;
        }
    }

    public final void interpolate(com.maddox.JGP.Quat4f quat4f, com.maddox.JGP.Quat4f quat4f1, float f)
    {
        if(this != quat4f1)
        {
            set(quat4f);
            interpolate(quat4f1, f);
        } else
        {
            interpolate(quat4f, 1.0F - f);
        }
    }

    private void setFromMat(float f, float f1, float f2, float f3, float f4, float f5, float f6, 
            float f7, float f8)
    {
        float f13 = f + f4 + f8;
        if((double)f13 >= 0.0D)
        {
            float f9 = (float)java.lang.Math.sqrt((double)f13 + 1.0D);
            w = (float)((double)f9 * 0.5D);
            f9 = 0.5F / f9;
            x = (f7 - f5) * f9;
            y = (f2 - f6) * f9;
            z = (f3 - f1) * f9;
        } else
        {
            float f14 = java.lang.Math.max(java.lang.Math.max(f, f4), f8);
            if(f14 == f)
            {
                float f10 = (float)java.lang.Math.sqrt((double)(f - (f4 + f8)) + 1.0D);
                x = (float)((double)f10 * 0.5D);
                f10 = 0.5F / f10;
                y = (f1 + f3) * f10;
                z = (f6 + f2) * f10;
                w = (f7 - f5) * f10;
            } else
            if(f14 == f4)
            {
                float f11 = (float)java.lang.Math.sqrt((double)(f4 - (f8 + f)) + 1.0D);
                y = (float)((double)f11 * 0.5D);
                f11 = 0.5F / f11;
                z = (f5 + f7) * f11;
                x = (f1 + f3) * f11;
                w = (f2 - f6) * f11;
            } else
            {
                float f12 = (float)java.lang.Math.sqrt((double)(f8 - (f + f4)) + 1.0D);
                z = (float)((double)f12 * 0.5D);
                f12 = 0.5F / f12;
                x = (f6 + f2) * f12;
                y = (f5 + f7) * f12;
                w = (f3 - f1) * f12;
            }
        }
    }

    public final void set(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        tmp1.normalize(tuple3f);
        tmp2.normalize(tuple3f1);
        float f = tmp1.dot(tmp2);
        float f1 = (float)java.lang.Math.acos(f);
        float f2 = java.lang.Math.abs(f1);
        if(f2 < 0.0001F)
        {
            setIdent();
            return;
        }
        if(f2 > 3.141493F)
        {
            axis.set(1.0F, 0.0F, 0.0F);
            if(axis.dot(tmp1) < 0.1F)
            {
                axis.cross(axis, tmp1);
                axis.normalize();
            } else
            {
                axis.set(0.0F, 0.0F, 1.0F);
            }
        } else
        {
            axis.cross(tmp1, tmp2);
            axis.normalize();
        }
        axis.scale(f1);
        set(axis);
    }

    public void setEulers(float f, float f1, float f2)
    {
        float f3 = (float)java.lang.Math.sin(f * 0.5F);
        float f4 = (float)java.lang.Math.cos(f * 0.5F);
        float f5 = (float)java.lang.Math.sin(f1 * 0.5F);
        float f6 = (float)java.lang.Math.cos(f1 * 0.5F);
        float f7 = (float)java.lang.Math.sin(f2 * 0.5F);
        float f8 = (float)java.lang.Math.cos(f2 * 0.5F);
        x = f4 * f6 * f7 - f3 * f5 * f8;
        y = f4 * f5 * f8 + f3 * f6 * f7;
        z = f3 * f6 * f8 - f4 * f5 * f7;
        w = f4 * f6 * f8 + f3 * f5 * f7;
    }

    public void getEulers(float af[])
    {
        MQ.set(this);
        MQ.getEulers(af);
    }

    private static final com.maddox.JGP.Matrix3f MQ = new Matrix3f();
    private static com.maddox.JGP.Vector3f tmp1 = new Vector3f();
    private static com.maddox.JGP.Vector3f tmp2 = new Vector3f();
    private static com.maddox.JGP.Vector3f axis = new Vector3f();

}
