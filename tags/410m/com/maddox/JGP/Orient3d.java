// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orient3d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Quat4d, Matrix3d, Tuple3d

public class Orient3d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Orient3d()
    {
        Yaw = 0.0D;
        Pitch = 0.0D;
        Roll = 0.0D;
        Q = new Quat4d();
        M = new Matrix3d();
        Mi = new Matrix3d();
        QuatOK = false;
        MatrixOK = false;
        MatrInvOK = false;
    }

    public Orient3d(double d, double d1, double d2)
    {
        Yaw = 0.0D;
        Pitch = 0.0D;
        Roll = 0.0D;
        Q = new Quat4d();
        M = new Matrix3d();
        Mi = new Matrix3d();
        QuatOK = false;
        MatrixOK = false;
        MatrInvOK = false;
        set(d, d1, d2);
    }

    public void set(double d, double d1, double d2)
    {
        Yaw = d;
        Pitch = d1;
        Roll = d2;
        QuatOK = MatrixOK = MatrInvOK = false;
    }

    public void set(com.maddox.JGP.Orient3d orient3d)
    {
        Yaw = orient3d.Yaw;
        Pitch = orient3d.Pitch;
        Roll = orient3d.Roll;
        QuatOK = orient3d.QuatOK;
        MatrixOK = orient3d.MatrixOK;
        MatrInvOK = orient3d.MatrInvOK;
        if(QuatOK)
            Q.set(orient3d.Q);
        if(MatrixOK)
            M.set(orient3d.M);
        if(MatrInvOK)
            Mi.set(orient3d.Mi);
    }

    public void set(double ad[])
    {
        Yaw = ad[0];
        Pitch = ad[1];
        Roll = ad[2];
        QuatOK = MatrixOK = MatrInvOK = false;
    }

    public double getYaw()
    {
        return Yaw;
    }

    public double getPitch()
    {
        return Pitch;
    }

    public double getRoll()
    {
        return Roll;
    }

    public void get(double ad[])
    {
        ad[0] = Yaw;
        ad[1] = Pitch;
        ad[2] = Roll;
    }

    private void makeMatrix()
    {
        if(MatrixOK)
        {
            return;
        } else
        {
            M.setEulers(Yaw, Pitch, Roll);
            MatrixOK = true;
            return;
        }
    }

    private void makeMatrixInv()
    {
        if(MatrInvOK)
        {
            return;
        } else
        {
            Mi.setEulersInv(Yaw, Pitch, Roll);
            MatrInvOK = true;
            return;
        }
    }

    private void makeQuat()
    {
        if(QuatOK)
        {
            return;
        } else
        {
            Q.setEulers(Yaw, Pitch, Roll);
            QuatOK = true;
            return;
        }
    }

    public void add(com.maddox.JGP.Orient3d orient3d, com.maddox.JGP.Orient3d orient3d1)
    {
        orient3d1.makeMatrix();
        orient3d.makeMatrix();
        M.mul(orient3d1.M, orient3d.M);
        M.getEulers(tmp);
        Yaw = tmp[0];
        Pitch = tmp[1];
        Roll = tmp[2];
        MatrixOK = QuatOK = MatrInvOK = false;
    }

    public void add(com.maddox.JGP.Orient3d orient3d)
    {
        add(this, orient3d);
    }

    public void sub(com.maddox.JGP.Orient3d orient3d, com.maddox.JGP.Orient3d orient3d1)
    {
        orient3d1.makeMatrix();
        orient3d.makeMatrix();
        M.mulTransposeLeft(orient3d1.M, orient3d.M);
        M.getEulers(tmp);
        Yaw = tmp[0];
        Pitch = tmp[1];
        Roll = tmp[2];
        MatrixOK = QuatOK = MatrInvOK = false;
    }

    public void sub(com.maddox.JGP.Orient3d orient3d)
    {
        sub(this, orient3d);
    }

    public void increment(com.maddox.JGP.Orient3d orient3d)
    {
        add(orient3d, this);
    }

    public void increment(double d, double d1, double d2)
    {
        Tmp.set(d, d1, d2);
        add(Tmp, this);
    }

    public void wrap()
    {
        Yaw %= 6.2831853071795862D;
        Pitch %= 6.2831853071795862D;
        Roll %= 6.2831853071795862D;
        if(Pitch > 3.1415926535897931D)
            Pitch -= 6.2831853071795862D;
        if(Pitch > 1.5707963267948966D)
        {
            Pitch = 3.1415926535897931D - Pitch;
            Roll += 3.1415926535897931D;
            Yaw += 3.1415926535897931D;
            if(Roll > 3.1415926535897931D)
                Roll -= 6.2831853071795862D;
            if(Yaw > 3.1415926535897931D)
                Yaw -= 6.2831853071795862D;
        } else
        if(Pitch < -1.5707963267948966D)
        {
            Pitch = -3.1415926535897931D - Pitch;
            Roll += 3.1415926535897931D;
            Yaw += 3.1415926535897931D;
            if(Roll > 3.1415926535897931D)
                Roll -= 6.2831853071795862D;
            if(Yaw > 3.1415926535897931D)
                Yaw -= 6.2831853071795862D;
        }
        if(Roll > 3.1415926535897931D)
            Roll -= 6.2831853071795862D;
        else
        if(Roll < -3.1415926535897931D)
            Roll += 6.2831853071795862D;
        if(Yaw < 0.0D)
            Yaw += 6.2831853071795862D;
    }

    public void getMatrix(com.maddox.JGP.Matrix3d matrix3d)
    {
        makeMatrix();
        matrix3d.set(M);
    }

    public void getMatrixInv(com.maddox.JGP.Matrix3d matrix3d)
    {
        makeMatrixInv();
        matrix3d.set(M);
    }

    public void transform(com.maddox.JGP.Tuple3d tuple3d)
    {
        makeMatrix();
        M.transform(tuple3d);
    }

    public void transform(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1)
    {
        makeMatrix();
        M.transform(tuple3d, tuple3d1);
    }

    public void transformInv(com.maddox.JGP.Tuple3d tuple3d)
    {
        makeMatrixInv();
        Mi.transform(tuple3d);
    }

    public void transformInv(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1)
    {
        makeMatrixInv();
        Mi.transform(tuple3d, tuple3d1);
    }

    public final void interpolate(com.maddox.JGP.Orient3d orient3d, com.maddox.JGP.Orient3d orient3d1, double d)
    {
        orient3d.makeQuat();
        orient3d1.makeQuat();
        Q1.interpolate(orient3d.Q, orient3d1.Q, d);
        Q1.getEulers(tmp);
        Yaw = tmp[0];
        Pitch = tmp[1];
        Roll = tmp[2];
        MatrixOK = QuatOK = MatrInvOK = false;
    }

    public final void interpolate(com.maddox.JGP.Orient3d orient3d, double d)
    {
        makeQuat();
        orient3d.makeQuat();
        Q.interpolate(orient3d.Q, d);
        Q.getEulers(tmp);
        Yaw = tmp[0];
        Pitch = tmp[1];
        Roll = tmp[2];
        MatrixOK = QuatOK = MatrInvOK = false;
    }

    public int hashCode()
    {
        long l = java.lang.Double.doubleToLongBits(Yaw);
        long l1 = java.lang.Double.doubleToLongBits(Pitch);
        long l2 = java.lang.Double.doubleToLongBits(Roll);
        l ^= l1 ^ l2;
        return (int)(l ^ l >> 32);
    }

    public boolean equals(com.maddox.JGP.Orient3d orient3d)
    {
        return orient3d != null && Yaw == orient3d.Yaw && Pitch == orient3d.Pitch && Roll == orient3d.Roll;
    }

    public boolean epsilonEquals(com.maddox.JGP.Orient3d orient3d, double d)
    {
        return java.lang.Math.abs(orient3d.Yaw - Yaw) <= d && java.lang.Math.abs(orient3d.Pitch - Pitch) <= d && java.lang.Math.abs(orient3d.Roll - Roll) <= d;
    }

    public java.lang.String toString()
    {
        return "(" + Yaw + ", " + Pitch + ", " + Roll + ")";
    }

    private double Yaw;
    private double Pitch;
    private double Roll;
    private com.maddox.JGP.Quat4d Q;
    private com.maddox.JGP.Matrix3d M;
    private com.maddox.JGP.Matrix3d Mi;
    private boolean QuatOK;
    private boolean MatrixOK;
    private boolean MatrInvOK;
    private static double tmp[] = new double[3];
    private static final double PI = 3.1415926535897931D;
    private static final double PI2 = 6.2831853071795862D;
    private static final double PI_2 = 1.5707963267948966D;
    private static final com.maddox.JGP.Quat4d Q1 = new Quat4d();
    private static final com.maddox.JGP.Orient3d Tmp = new Orient3d();

}
