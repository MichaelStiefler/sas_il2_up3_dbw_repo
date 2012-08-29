// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AxisAngle4d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Matrix3d, AxisAngle4f, Matrix4f, Matrix4d, 
//            Matrix3f, Quat4f, Quat4d, Vector3d

public class AxisAngle4d
    implements java.io.Serializable, java.lang.Cloneable
{

    public AxisAngle4d(double d, double d1, double d2, double d3)
    {
        set(d, d1, d2, d3);
    }

    public AxisAngle4d(double ad[])
    {
        set(ad);
    }

    public AxisAngle4d(com.maddox.JGP.AxisAngle4d axisangle4d)
    {
        set(axisangle4d);
    }

    public AxisAngle4d(com.maddox.JGP.AxisAngle4f axisangle4f)
    {
        set(axisangle4f);
    }

    public AxisAngle4d()
    {
        x = 0.0D;
        y = 0.0D;
        z = 1.0D;
        angle = 0.0D;
    }

    public final void set(double d, double d1, double d2, double d3)
    {
        x = d;
        y = d1;
        z = d2;
        angle = d3;
    }

    public final void set(double ad[])
    {
        x = ad[0];
        y = ad[1];
        z = ad[2];
        angle = ad[3];
    }

    public final void set(com.maddox.JGP.AxisAngle4d axisangle4d)
    {
        x = axisangle4d.x;
        y = axisangle4d.y;
        z = axisangle4d.z;
        angle = axisangle4d.angle;
    }

    public final void set(com.maddox.JGP.AxisAngle4f axisangle4f)
    {
        x = axisangle4f.x;
        y = axisangle4f.y;
        z = axisangle4f.z;
        angle = axisangle4f.angle;
    }

    public final void get(double ad[])
    {
        ad[0] = x;
        ad[1] = y;
        ad[2] = z;
        ad[3] = angle;
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
        x = d7 - d5;
        y = d2 - d6;
        z = d3 - d1;
        double d10 = 0.5D * java.lang.Math.sqrt(x * x + y * y + z * z);
        angle = java.lang.Math.atan2(d10, d9);
    }

    private void setFromQuat(double d, double d1, double d2, double d3)
    {
        double d4 = java.lang.Math.sqrt(d * d + d1 * d1 + d2 * d2);
        angle = 2D * java.lang.Math.atan2(d4, d3);
        x = d;
        y = d1;
        z = d2;
    }

    public java.lang.String toString()
    {
        return "(" + x + ", " + y + ", " + z + ", " + angle + ")";
    }

    public boolean equals(com.maddox.JGP.AxisAngle4d axisangle4d)
    {
        return axisangle4d != null && x == axisangle4d.x && y == axisangle4d.y && z == axisangle4d.z && angle == axisangle4d.angle;
    }

    public boolean equals(java.lang.Object obj)
    {
        return obj != null && (obj instanceof com.maddox.JGP.AxisAngle4d) && equals((com.maddox.JGP.AxisAngle4d)obj);
    }

    public boolean epsilonEquals(com.maddox.JGP.AxisAngle4d axisangle4d, double d)
    {
        return java.lang.Math.abs(axisangle4d.x - x) <= d && java.lang.Math.abs(axisangle4d.y - y) <= d && java.lang.Math.abs(axisangle4d.z - z) <= d && java.lang.Math.abs(axisangle4d.angle - angle) <= d;
    }

    public int hashCode()
    {
        long l = java.lang.Double.doubleToLongBits(x);
        long l1 = java.lang.Double.doubleToLongBits(y);
        long l2 = java.lang.Double.doubleToLongBits(z);
        long l3 = java.lang.Double.doubleToLongBits(angle);
        return (int)(l ^ l >> 32 ^ l1 ^ l1 >> 32 ^ l2 ^ l2 >> 32 ^ l3 ^ l3 >> 32);
    }

    public void set(com.maddox.JGP.Vector3d vector3d)
    {
        double d = vector3d.length();
        double d1;
        if(d != 0.0D)
            d1 = 1.0D / d;
        else
            d1 = 0.0D;
        x = vector3d.x * d1;
        y = vector3d.y * d1;
        z = vector3d.z * d1;
        angle = d;
    }

    public void get(com.maddox.JGP.Vector3d vector3d)
    {
        if(angle >= 0.0D)
        {
            vector3d.x = x * angle;
            vector3d.y = y * angle;
            vector3d.z = z * angle;
        } else
        {
            vector3d.x = -x * angle;
            vector3d.y = -y * angle;
            vector3d.z = -z * angle;
        }
    }

    private void makeMatrix()
    {
        double d = java.lang.Math.sin(angle);
        double d1 = java.lang.Math.cos(angle);
        double d2 = 1.0D - d1;
        double d3 = x;
        double d4 = y;
        double d5 = z;
        double d7 = d3 * d2;
        double d6 = d3 * d3;
        Mr.m00 = d6 + d1 * (1.0D - d6);
        Mr.m01 = d7 * d4 + d5 * d;
        Mr.m02 = d7 * d5 - d4 * d;
        d6 = d4 * d4;
        Mr.m10 = d7 * d4 - d5 * d;
        Mr.m11 = d6 + d1 * (1.0D - d6);
        Mr.m12 = d4 * d2 * d5 + d3 * d;
        d6 = d5 * d5;
        Mr.m20 = d7 * d5 + d4 * d;
        Mr.m21 = d4 * d2 * d5 - d3 * d;
        Mr.m22 = d6 + d1 * (1.0D - d6);
    }

    public void rotate(com.maddox.JGP.Vector3d vector3d)
    {
        makeMatrix();
        Mr.transform(vector3d);
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
        Mr.m00 = d1 + x * x * d3;
        Mr.m11 = d1 + y * y * d3;
        Mr.m22 = d1 + z * z * d3;
        double d4 = x * y * d3;
        double d5 = z * d2;
        Mr.m01 = d4 - d5;
        Mr.m10 = d4 + d5;
        d4 = x * z * d3;
        d5 = y * d2;
        Mr.m02 = d4 + d5;
        Mr.m20 = d4 - d5;
        d4 = y * z * d3;
        d5 = x * d2;
        Mr.m12 = d4 - d5;
        Mr.m21 = d4 + d5;
    }

    public void rotateRightHanded(com.maddox.JGP.Vector3d vector3d)
    {
        makeMatrixRightHanded();
        Mr.transform(vector3d);
    }

    public double x;
    public double y;
    public double z;
    public double angle;
    private static com.maddox.JGP.Matrix3d Mr = new Matrix3d();

}
