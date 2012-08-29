// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Stabilizer.java

package com.maddox.il2.fm;


public class Stabilizer
{

    public Stabilizer()
    {
    }

    public void set(float f, float f1)
    {
        Hset = Hcur = Hold = f;
        A = f1;
        HDiff = Speed = OldSpeed = Accel = OldAccel = 0.0F;
        MaxSpeed = java.lang.Math.max(Hset * 0.0001F, 0.001F);
    }

    public float getOutput(float f)
    {
        Hold = Hcur;
        Hcur = f;
        OldAccel = Accel;
        OldSpeed = Speed;
        HDiff = Hcur - Hset;
        Speed = Hcur - Hold;
        Accel = Speed - OldSpeed;
        NeedSpeed = -HDiff * 0.01F;
        if(NeedSpeed > MaxSpeed)
            NeedSpeed = MaxSpeed;
        else
        if(NeedSpeed < -MaxSpeed)
            NeedSpeed = -MaxSpeed;
        NeedAccel = (NeedSpeed - Speed) * 0.01F;
        dAccel = Accel - OldAccel;
        if(NeedAccel - Accel > dAccel)
            A += 0.001F;
        else
            A -= 0.001F;
        if(A < -1F)
            A = -1F;
        else
        if(A > 1.0F)
            A = 1.0F;
        return A;
    }

    float Hset;
    float Hcur;
    float Hold;
    float HDiff;
    float MaxSpeed;
    float Speed;
    float OldSpeed;
    float Accel;
    float OldAccel;
    float dAccel;
    float NeedSpeed;
    float NeedAccel;
    float A;
}
