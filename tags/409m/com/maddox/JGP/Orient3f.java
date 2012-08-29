// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orient3f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Quat4f, Matrix3f, Tuple3f

public class Orient3f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Orient3f()
    {
        Yaw = 0.0F;
        Pitch = 0.0F;
        Roll = 0.0F;
        Q = new Quat4f();
        M = new Matrix3f();
        Mi = new Matrix3f();
        QuatOK = false;
        MatrixOK = false;
        MatrInvOK = false;
    }

    public Orient3f(float f, float f1, float f2)
    {
        Yaw = 0.0F;
        Pitch = 0.0F;
        Roll = 0.0F;
        Q = new Quat4f();
        M = new Matrix3f();
        Mi = new Matrix3f();
        QuatOK = false;
        MatrixOK = false;
        MatrInvOK = false;
        set(f, f1, f2);
    }

    public void set(float f, float f1, float f2)
    {
        Yaw = f;
        Pitch = f1;
        Roll = f2;
        QuatOK = MatrixOK = MatrInvOK = false;
    }

    public void set(com.maddox.JGP.Orient3f orient3f)
    {
        Yaw = orient3f.Yaw;
        Pitch = orient3f.Pitch;
        Roll = orient3f.Roll;
        QuatOK = orient3f.QuatOK;
        MatrixOK = orient3f.MatrixOK;
        MatrInvOK = orient3f.MatrInvOK;
        if(QuatOK)
            Q.set(orient3f.Q);
        if(MatrixOK)
            M.set(orient3f.M);
        if(MatrInvOK)
            Mi.set(orient3f.Mi);
    }

    public void set(float af[])
    {
        Yaw = af[0];
        Pitch = af[1];
        Roll = af[2];
        QuatOK = MatrixOK = MatrInvOK = false;
    }

    public float getYaw()
    {
        return Yaw;
    }

    public float getPitch()
    {
        return Pitch;
    }

    public float getRoll()
    {
        return Roll;
    }

    public void get(float af[])
    {
        af[0] = Yaw;
        af[1] = Pitch;
        af[2] = Roll;
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

    public void add(com.maddox.JGP.Orient3f orient3f, com.maddox.JGP.Orient3f orient3f1)
    {
        orient3f1.makeMatrix();
        orient3f.makeMatrix();
        M.mul(orient3f1.M, orient3f.M);
        M.getEulers(tmp);
        Yaw = tmp[0];
        Pitch = tmp[1];
        Roll = tmp[2];
        MatrixOK = QuatOK = MatrInvOK = false;
    }

    public void add(com.maddox.JGP.Orient3f orient3f)
    {
        add(this, orient3f);
    }

    public void sub(com.maddox.JGP.Orient3f orient3f, com.maddox.JGP.Orient3f orient3f1)
    {
        orient3f1.makeMatrix();
        orient3f.makeMatrix();
        M.mulTransposeLeft(orient3f1.M, orient3f.M);
        M.getEulers(tmp);
        Yaw = tmp[0];
        Pitch = tmp[1];
        Roll = tmp[2];
        MatrixOK = QuatOK = MatrInvOK = false;
    }

    public void sub(com.maddox.JGP.Orient3f orient3f)
    {
        sub(this, orient3f);
    }

    public void increment(com.maddox.JGP.Orient3f orient3f)
    {
        add(orient3f, this);
    }

    public void increment(float f, float f1, float f2)
    {
        Tmp.set(f, f1, f2);
        add(Tmp, this);
    }

    public void wrap()
    {
        Yaw %= 6.283185F;
        Pitch %= 6.283185F;
        Roll %= 6.283185F;
        if(Pitch > 3.141593F)
            Pitch -= 6.283185F;
        if(Pitch > 1.570796F)
        {
            Pitch = 3.141593F - Pitch;
            Roll += 3.141593F;
            Yaw += 3.141593F;
            if(Roll > 3.141593F)
                Roll -= 6.283185F;
            if(Yaw > 3.141593F)
                Yaw -= 6.283185F;
        } else
        if(Pitch < -1.570796F)
        {
            Pitch = -3.141593F - Pitch;
            Roll += 3.141593F;
            Yaw += 3.141593F;
            if(Roll > 3.141593F)
                Roll -= 6.283185F;
            if(Yaw > 3.141593F)
                Yaw -= 6.283185F;
        }
        if(Roll > 3.141593F)
            Roll -= 6.283185F;
        else
        if(Roll < -3.141593F)
            Roll += 6.283185F;
        if(Yaw < 0.0F)
            Yaw += 6.283185F;
    }

    public void getMatrix(com.maddox.JGP.Matrix3f matrix3f)
    {
        makeMatrix();
        matrix3f.set(M);
    }

    public void getMatrixInv(com.maddox.JGP.Matrix3f matrix3f)
    {
        makeMatrixInv();
        matrix3f.set(M);
    }

    public void transform(com.maddox.JGP.Tuple3f tuple3f)
    {
        makeMatrix();
        M.transform(tuple3f);
    }

    public void transform(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        makeMatrix();
        M.transform(tuple3f, tuple3f1);
    }

    public void transformInv(com.maddox.JGP.Tuple3f tuple3f)
    {
        makeMatrixInv();
        Mi.transform(tuple3f);
    }

    public void transformInv(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        makeMatrixInv();
        Mi.transform(tuple3f, tuple3f1);
    }

    public final void interpolate(com.maddox.JGP.Orient3f orient3f, com.maddox.JGP.Orient3f orient3f1, float f)
    {
        orient3f.makeQuat();
        orient3f1.makeQuat();
        Q1.interpolate(orient3f.Q, orient3f1.Q, f);
        Q1.getEulers(tmp);
        Yaw = tmp[0];
        Pitch = tmp[1];
        Roll = tmp[2];
        MatrixOK = QuatOK = MatrInvOK = false;
    }

    public final void interpolate(com.maddox.JGP.Orient3f orient3f, float f)
    {
        makeQuat();
        orient3f.makeQuat();
        Q.interpolate(orient3f.Q, f);
        Q.getEulers(tmp);
        Yaw = tmp[0];
        Pitch = tmp[1];
        Roll = tmp[2];
        MatrixOK = QuatOK = MatrInvOK = false;
    }

    public int hashCode()
    {
        int i = java.lang.Float.floatToIntBits(Yaw);
        int j = java.lang.Float.floatToIntBits(Pitch);
        int k = java.lang.Float.floatToIntBits(Roll);
        return i ^ j ^ k;
    }

    public boolean equals(com.maddox.JGP.Orient3f orient3f)
    {
        return orient3f != null && Yaw == orient3f.Yaw && Pitch == orient3f.Pitch && Roll == orient3f.Roll;
    }

    public boolean epsilonEquals(com.maddox.JGP.Orient3f orient3f, float f)
    {
        return java.lang.Math.abs(orient3f.Yaw - Yaw) <= f && java.lang.Math.abs(orient3f.Pitch - Pitch) <= f && java.lang.Math.abs(orient3f.Roll - Roll) <= f;
    }

    public java.lang.String toString()
    {
        return "(" + Yaw + ", " + Pitch + ", " + Roll + ")";
    }

    private float Yaw;
    private float Pitch;
    private float Roll;
    private com.maddox.JGP.Quat4f Q;
    private com.maddox.JGP.Matrix3f M;
    private com.maddox.JGP.Matrix3f Mi;
    private boolean QuatOK;
    private boolean MatrixOK;
    private boolean MatrInvOK;
    private static float tmp[] = new float[3];
    private static final float PI = 3.141593F;
    private static final float PI2 = 6.283185F;
    private static final float PI_2 = 1.570796F;
    private static final com.maddox.JGP.Quat4f Q1 = new Quat4f();
    private static final com.maddox.JGP.Orient3f Tmp = new Orient3f();

}
