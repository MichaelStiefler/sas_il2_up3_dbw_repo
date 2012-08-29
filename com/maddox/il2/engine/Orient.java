// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Orient.java

package com.maddox.il2.engine;

import com.maddox.JGP.Matrix3d;
import com.maddox.JGP.Quat4f;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;

public class Orient
{

    protected static float DEG2RAD(float f)
    {
        return f * 0.01745329F;
    }

    protected static float RAD2DEG(float f)
    {
        return f * 57.29578F;
    }

    public Orient()
    {
        Yaw = 0.0F;
        Pitch = 0.0F;
        Roll = 0.0F;
    }

    public Orient(float f, float f1, float f2)
    {
        Yaw = 0.0F;
        Pitch = 0.0F;
        Roll = 0.0F;
        set(f, f1, f2);
    }

    public void set(float f, float f1, float f2)
    {
        Yaw = -f;
        Pitch = -f1;
        Roll = f2;
    }

    public void setYPR(float f, float f1, float f2)
    {
        Yaw = f;
        Pitch = -f1;
        Roll = -f2;
    }

    public void set(com.maddox.il2.engine.Orient orient1)
    {
        Yaw = orient1.Yaw;
        Pitch = orient1.Pitch;
        Roll = orient1.Roll;
    }

    public final float azimut()
    {
        return getAzimut();
    }

    public final float kren()
    {
        return getKren();
    }

    public final float tangage()
    {
        return getTangage();
    }

    public float getAzimut()
    {
        return 360F - Yaw;
    }

    public float getTangage()
    {
        return -Pitch;
    }

    public float getKren()
    {
        return Roll;
    }

    public float getYaw()
    {
        return Yaw;
    }

    public void setYaw(float f)
    {
        Yaw = f;
    }

    public float getPitch()
    {
        return 360F - Pitch;
    }

    public float getRoll()
    {
        return 360F - Roll;
    }

    public void get(float af[])
    {
        af[0] = 360F - Yaw;
        af[1] = -Pitch;
        af[2] = Roll;
    }

    public void getYPR(float af[])
    {
        wrap();
        af[0] = Yaw;
        af[1] = 360F - Pitch;
        af[2] = 360F - Roll;
    }

    protected void makeMatrix(com.maddox.JGP.Matrix3d matrix3d)
    {
        matrix3d.setEulers(com.maddox.il2.engine.Orient.DEG2RAD(Yaw), com.maddox.il2.engine.Orient.DEG2RAD(Pitch), com.maddox.il2.engine.Orient.DEG2RAD(Roll));
    }

    protected void makeMatrixInv(com.maddox.JGP.Matrix3d matrix3d)
    {
        matrix3d.setEulersInv(com.maddox.il2.engine.Orient.DEG2RAD(Yaw), com.maddox.il2.engine.Orient.DEG2RAD(Pitch), com.maddox.il2.engine.Orient.DEG2RAD(Roll));
    }

    protected void makeQuat(com.maddox.JGP.Quat4f quat4f)
    {
        quat4f.setEulers(com.maddox.il2.engine.Orient.DEG2RAD(Yaw), com.maddox.il2.engine.Orient.DEG2RAD(Pitch), com.maddox.il2.engine.Orient.DEG2RAD(Roll));
    }

    public void add(com.maddox.il2.engine.Orient orient1, com.maddox.il2.engine.Orient orient2)
    {
        orient2.getMatrix(M_0);
        orient1.getMatrix(M_1);
        M_.mul(M_0, M_1);
        M_.getEulers(tmpd);
        Yaw = com.maddox.il2.engine.Orient.RAD2DEG((float)tmpd[0]);
        Pitch = com.maddox.il2.engine.Orient.RAD2DEG((float)tmpd[1]);
        Roll = com.maddox.il2.engine.Orient.RAD2DEG((float)tmpd[2]);
    }

    public void increment(com.maddox.il2.engine.Orient orient1)
    {
        add(orient1, this);
    }

    public void add(com.maddox.il2.engine.Orient orient1)
    {
        add(this, orient1);
    }

    public void sub(com.maddox.il2.engine.Orient orient1, com.maddox.il2.engine.Orient orient2)
    {
        orient2.getMatrix(M_0);
        orient1.getMatrix(M_1);
        M_.mulTransposeLeft(M_0, M_1);
        M_.getEulers(tmpd);
        Yaw = com.maddox.il2.engine.Orient.RAD2DEG((float)tmpd[0]);
        Pitch = com.maddox.il2.engine.Orient.RAD2DEG((float)tmpd[1]);
        Roll = com.maddox.il2.engine.Orient.RAD2DEG((float)tmpd[2]);
    }

    public void increment(float f, float f1, float f2)
    {
        Tmp.set(f, f1, f2);
        add(Tmp, this);
    }

    public void sub(com.maddox.il2.engine.Orient orient1)
    {
        sub(this, orient1);
    }

    public void wrap()
    {
        Yaw = (Yaw + 2880F) % 360F;
        Pitch = (Pitch + 2880F) % 360F;
        Roll = (Roll + 2880F) % 360F;
        if(Pitch > 180F)
            Pitch -= 360F;
        if(Pitch > 90F)
        {
            Pitch = 180F - Pitch;
            Roll += 180F;
            Yaw += 180F;
        } else
        if(Pitch < -90F)
        {
            Pitch = -180F - Pitch;
            Roll += 180F;
            Yaw += 180F;
        }
        for(; Roll > 180F; Roll -= 360F);
        for(; Yaw > 180F; Yaw -= 360F);
    }

    public void wrap360()
    {
        Yaw %= 360F;
        Pitch %= 360F;
        Roll %= 360F;
    }

    public void getMatrix(com.maddox.JGP.Matrix3d matrix3d)
    {
        makeMatrix(matrix3d);
    }

    public void getMatrixInv(com.maddox.JGP.Matrix3d matrix3d)
    {
        makeMatrixInv(matrix3d);
    }

    public void getQuat(com.maddox.JGP.Quat4f quat4f)
    {
        makeQuat(quat4f);
    }

    public void transform(com.maddox.JGP.Tuple3d tuple3d)
    {
        getMatrix(M_);
        M_.transform(tuple3d);
    }

    public void transform(com.maddox.JGP.Tuple3f tuple3f)
    {
        getMatrix(M_);
        M_.transform(tuple3f);
    }

    public void transform(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1)
    {
        getMatrix(M_);
        M_.transform(tuple3d, tuple3d1);
    }

    public void transform(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        getMatrix(M_);
        M_.transform(tuple3f, tuple3f1);
    }

    public void transformInv(com.maddox.JGP.Tuple3d tuple3d)
    {
        getMatrixInv(Mi_);
        Mi_.transform(tuple3d);
    }

    public void transformInv(com.maddox.JGP.Tuple3f tuple3f)
    {
        getMatrixInv(Mi_);
        Mi_.transform(tuple3f);
    }

    public void transformInv(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1)
    {
        getMatrixInv(Mi_);
        Mi_.transform(tuple3d, tuple3d1);
    }

    public void transformInv(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        getMatrixInv(Mi_);
        Mi_.transform(tuple3f, tuple3f1);
    }

    public void interpolate(com.maddox.il2.engine.Orient orient1, com.maddox.il2.engine.Orient orient2, float f)
    {
        orient1.getQuat(Q_0);
        orient2.getQuat(Q_1);
        Q1.interpolate(Q_0, Q_1, f);
        Q1.getEulers(tmpf);
        Yaw = com.maddox.il2.engine.Orient.RAD2DEG(tmpf[0]);
        Pitch = com.maddox.il2.engine.Orient.RAD2DEG(tmpf[1]);
        Roll = com.maddox.il2.engine.Orient.RAD2DEG(tmpf[2]);
    }

    public void interpolate(com.maddox.il2.engine.Orient orient1, float f)
    {
        getQuat(Q_0);
        orient1.getQuat(Q_1);
        Q_0.interpolate(Q_1, f);
        Q_0.getEulers(tmpf);
        Yaw = com.maddox.il2.engine.Orient.RAD2DEG(tmpf[0]);
        Pitch = com.maddox.il2.engine.Orient.RAD2DEG(tmpf[1]);
        Roll = com.maddox.il2.engine.Orient.RAD2DEG(tmpf[2]);
    }

    public int hashCode()
    {
        int i = java.lang.Float.floatToIntBits(Yaw);
        int j = java.lang.Float.floatToIntBits(Pitch);
        int k = java.lang.Float.floatToIntBits(Roll);
        return i ^ j ^ k;
    }

    public boolean equals(com.maddox.il2.engine.Orient orient1)
    {
        return orient1 != null && Yaw == orient1.Yaw && Pitch == orient1.Pitch && Roll == orient1.Roll;
    }

    public boolean epsilonEquals(com.maddox.il2.engine.Orient orient1, float f)
    {
        return java.lang.Math.abs(orient1.Yaw - Yaw) <= f && java.lang.Math.abs(orient1.Pitch - Pitch) <= f && java.lang.Math.abs(orient1.Roll - Roll) <= f;
    }

    public java.lang.String toString()
    {
        return "(" + Yaw + ", " + Pitch + ", " + Roll + ")";
    }

    public void orient(com.maddox.JGP.Vector3f vector3f)
    {
        VV.set(vector3f);
        orient(VV);
    }

    public void orient(com.maddox.JGP.Vector3d vector3d)
    {
        float f = (float)java.lang.Math.cos(com.maddox.il2.engine.Orient.DEG2RAD(Yaw));
        float f1 = (float)java.lang.Math.sin(com.maddox.il2.engine.Orient.DEG2RAD(Yaw));
        Vt.z = vector3d.z;
        Vt.x = vector3d.x * (double)f + vector3d.y * (double)f1;
        Vt.y = vector3d.y * (double)f - vector3d.x * (double)f1;
        setYPR(Yaw, -com.maddox.il2.engine.Orient.RAD2DEG((float)java.lang.Math.atan2(Vt.x, Vt.z)), com.maddox.il2.engine.Orient.RAD2DEG((float)java.lang.Math.asin(Vt.y)));
    }

    public void setAT0(com.maddox.JGP.Vector3f vector3f)
    {
        set(-com.maddox.il2.engine.Orient.RAD2DEG((float)java.lang.Math.atan2(vector3f.y, vector3f.x)), com.maddox.il2.engine.Orient.RAD2DEG((float)java.lang.Math.atan2(vector3f.z, java.lang.Math.sqrt(vector3f.x * vector3f.x + vector3f.y * vector3f.y))), 0.0F);
    }

    public void setAT0(com.maddox.JGP.Vector3d vector3d)
    {
        set(-com.maddox.il2.engine.Orient.RAD2DEG((float)java.lang.Math.atan2(vector3d.y, vector3d.x)), com.maddox.il2.engine.Orient.RAD2DEG((float)java.lang.Math.atan2(vector3d.z, java.lang.Math.sqrt(vector3d.x * vector3d.x + vector3d.y * vector3d.y))), 0.0F);
    }

    protected float Yaw;
    protected float Pitch;
    protected float Roll;
    protected static com.maddox.JGP.Matrix3d M_ = new Matrix3d();
    protected static com.maddox.JGP.Matrix3d Mi_ = new Matrix3d();
    protected static com.maddox.JGP.Matrix3d M_0 = new Matrix3d();
    protected static com.maddox.JGP.Matrix3d M_1 = new Matrix3d();
    protected static com.maddox.JGP.Quat4f Q_0 = new Quat4f();
    protected static com.maddox.JGP.Quat4f Q_1 = new Quat4f();
    protected static final float PI = 3.141593F;
    private static float tmpf[] = new float[3];
    private static double tmpd[] = new double[3];
    private static final com.maddox.il2.engine.Orient Tmp = new Orient();
    private static final float Circ = 360F;
    private static final float Circ_2 = 180F;
    private static final float Circ_4 = 90F;
    private static final com.maddox.JGP.Quat4f Q1 = new Quat4f();
    private static final com.maddox.JGP.Vector3d Vt = new Vector3d();
    private static final com.maddox.JGP.Vector3d VV = new Vector3d();

}
