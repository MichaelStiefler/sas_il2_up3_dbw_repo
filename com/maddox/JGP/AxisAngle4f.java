// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AxisAngle4f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Matrix3f, Tuple3f, AxisAngle4d, Matrix4f, 
//            Matrix4d, Matrix3d, Quat4f, Quat4d, 
//            Vector3f

public class AxisAngle4f
    implements java.io.Serializable, java.lang.Cloneable
{

    public AxisAngle4f(float f, float f1, float f2, float f3)
    {
        set(f, f1, f2, f3);
    }

    public AxisAngle4f(float af[])
    {
        set(af);
    }

    public AxisAngle4f(com.maddox.JGP.AxisAngle4f axisangle4f)
    {
        set(axisangle4f);
    }

    public AxisAngle4f(com.maddox.JGP.AxisAngle4d axisangle4d)
    {
        set(axisangle4d);
    }

    public AxisAngle4f(com.maddox.JGP.Tuple3f tuple3f, float f)
    {
        x = tuple3f.x;
        y = tuple3f.y;
        z = tuple3f.z;
        angle = f;
    }

    public AxisAngle4f()
    {
        x = 0.0F;
        y = 0.0F;
        z = 1.0F;
        angle = 0.0F;
    }

    public final void set(float f, float f1, float f2, float f3)
    {
        x = f;
        y = f1;
        z = f2;
        angle = f3;
    }

    public final void set(float af[])
    {
        x = af[0];
        y = af[1];
        z = af[2];
        angle = af[3];
    }

    public final void set(com.maddox.JGP.AxisAngle4f axisangle4f)
    {
        x = axisangle4f.x;
        y = axisangle4f.y;
        z = axisangle4f.z;
        angle = axisangle4f.angle;
    }

    public final void set(com.maddox.JGP.AxisAngle4d axisangle4d)
    {
        x = (float)axisangle4d.x;
        y = (float)axisangle4d.y;
        z = (float)axisangle4d.z;
        angle = (float)axisangle4d.angle;
    }

    public final void get(float af[])
    {
        af[0] = x;
        af[1] = y;
        af[2] = z;
        af[3] = angle;
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

    public final void set(com.maddox.JGP.Quat4f quat4f)
    {
        setFromQuat(quat4f.x, quat4f.y, quat4f.z, quat4f.w);
    }

    public final void set(com.maddox.JGP.Quat4d quat4d)
    {
        setFromQuat(quat4d.x, quat4d.y, quat4d.z, quat4d.w);
    }

    private void setFromMat(double d, double d1, double d2, double d3, double d4, double d5, double d6, 
            double d7, double d8)
    {
        double d9 = ((d + d4 + d8) - 1.0D) * 0.5D;
        x = (float)(d7 - d5);
        y = (float)(d2 - d6);
        z = (float)(d3 - d1);
        double d10 = 0.5D * java.lang.Math.sqrt(x * x + y * y + z * z);
        angle = (float)java.lang.Math.atan2(d10, d9);
    }

    private void setFromQuat(double d, double d1, double d2, double d3)
    {
        double d4 = java.lang.Math.sqrt(d * d + d1 * d1 + d2 * d2);
        angle = (float)(2D * java.lang.Math.atan2(d4, d3));
        x = (float)d;
        y = (float)d1;
        z = (float)d2;
    }

    public java.lang.String toString()
    {
        return "(" + x + ", " + y + ", " + z + ", " + angle + ")";
    }

    public boolean equals(com.maddox.JGP.AxisAngle4f axisangle4f)
    {
        return axisangle4f != null && x == axisangle4f.x && y == axisangle4f.y && z == axisangle4f.z && angle == axisangle4f.angle;
    }

    public boolean equals(java.lang.Object obj)
    {
        return obj != null && (obj instanceof com.maddox.JGP.AxisAngle4f) && equals((com.maddox.JGP.AxisAngle4f)obj);
    }

    public boolean epsilonEquals(com.maddox.JGP.AxisAngle4f axisangle4f, float f)
    {
        return java.lang.Math.abs(axisangle4f.x - x) <= f && java.lang.Math.abs(axisangle4f.y - y) <= f && java.lang.Math.abs(axisangle4f.z - z) <= f && java.lang.Math.abs(axisangle4f.angle - angle) <= f;
    }

    public int hashCode()
    {
        return java.lang.Float.floatToIntBits(x) ^ java.lang.Float.floatToIntBits(y) ^ java.lang.Float.floatToIntBits(z) ^ java.lang.Float.floatToIntBits(angle);
    }

    public void set(com.maddox.JGP.Vector3f vector3f)
    {
        float f = vector3f.length();
        float f1;
        if(f != 0.0F)
            f1 = 1.0F / f;
        else
            f1 = 0.0F;
        x = vector3f.x * f1;
        y = vector3f.y * f1;
        z = vector3f.z * f1;
        angle = f;
    }

    public void get(com.maddox.JGP.Vector3f vector3f)
    {
        if(angle >= 0.0F)
        {
            vector3f.x = x * angle;
            vector3f.y = y * angle;
            vector3f.z = z * angle;
        } else
        {
            vector3f.x = -x * angle;
            vector3f.y = -y * angle;
            vector3f.z = -z * angle;
        }
    }

    private void makeMatrix()
    {
        float f = (float)java.lang.Math.sin(angle);
        float f1 = (float)java.lang.Math.cos(angle);
        float f2 = 1.0F - f1;
        float f3 = x;
        float f4 = y;
        float f5 = z;
        float f7 = f3 * f2;
        float f6 = f3 * f3;
        Mr.m00 = f6 + f1 * (1.0F - f6);
        Mr.m01 = f7 * f4 + f5 * f;
        Mr.m02 = f7 * f5 - f4 * f;
        f6 = f4 * f4;
        Mr.m10 = f7 * f4 - f5 * f;
        Mr.m11 = f6 + f1 * (1.0F - f6);
        Mr.m12 = f4 * f2 * f5 + f3 * f;
        f6 = f5 * f5;
        Mr.m20 = f7 * f5 + f4 * f;
        Mr.m21 = f4 * f2 * f5 - f3 * f;
        Mr.m22 = f6 + f1 * (1.0F - f6);
    }

    public void rotate(com.maddox.JGP.Vector3f vector3f)
    {
        makeMatrix();
        Mr.transform(vector3f);
    }

    private void makeMatrixRightHanded()
    {
        double d = java.lang.Math.sqrt(x * x + y * y + z * z);
        d = 1.0D / d;
        x *= d;
        y *= d;
        z *= d;
        double d1 = java.lang.Math.cos(angle);
        double d2 = java.lang.Math.sin(angle);
        double d3 = 1.0D - d1;
        Mr.m00 = (float)(d1 + (double)(x * x) * d3);
        Mr.m11 = (float)(d1 + (double)(y * y) * d3);
        Mr.m22 = (float)(d1 + (double)(z * z) * d3);
        double d4 = (double)(x * y) * d3;
        double d5 = (double)z * d2;
        Mr.m01 = (float)(d4 - d5);
        Mr.m10 = (float)(d4 + d5);
        d4 = (double)(x * z) * d3;
        d5 = (double)y * d2;
        Mr.m02 = (float)(d4 + d5);
        Mr.m20 = (float)(d4 - d5);
        d4 = (double)(y * z) * d3;
        d5 = (double)x * d2;
        Mr.m12 = (float)(d4 - d5);
        Mr.m21 = (float)(d4 + d5);
    }

    public void rotateRightHanded(com.maddox.JGP.Vector3f vector3f)
    {
        makeMatrixRightHanded();
        Mr.transform(vector3f);
    }

    public float x;
    public float y;
    public float z;
    public float angle;
    private static com.maddox.JGP.Matrix3f Mr = new Matrix3f();

}
