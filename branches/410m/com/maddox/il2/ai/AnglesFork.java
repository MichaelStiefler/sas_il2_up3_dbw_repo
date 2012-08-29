// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AnglesFork.java

package com.maddox.il2.ai;


public final class AnglesFork
{

    public static float signedAngleDeg(float f)
    {
        return (float)(-8.3819031715393066E-008D * (double)(int)(long)((double)f * -11930464.711111112D));
    }

    public static float signedAngleRad(float f)
    {
        return (float)(-1.4629180792671596E-009D * (double)(int)(long)((double)f * -683565275.57643163D));
    }

    public AnglesFork()
    {
        src = dst = 0;
    }

    public AnglesFork(float f)
    {
        setDeg(f);
    }

    public AnglesFork(float f, float f1)
    {
        setDeg(f, f1);
    }

    public final void set(com.maddox.il2.ai.AnglesFork anglesfork)
    {
        src = anglesfork.src;
        dst = anglesfork.dst;
    }

    public final void setDeg(float f)
    {
        src = dst = (int)(long)((double)f * -11930464.711111112D);
    }

    public final void setRad(float f)
    {
        src = dst = (int)(long)((double)f * -683565275.57643163D);
    }

    public final void setSrcDeg(float f)
    {
        src = (int)(long)((double)f * -11930464.711111112D);
    }

    public final void setSrcRad(float f)
    {
        src = (int)(long)((double)f * -683565275.57643163D);
    }

    public final void setDstDeg(float f)
    {
        dst = (int)(long)((double)f * -11930464.711111112D);
    }

    public final void setDstRad(float f)
    {
        dst = (int)(long)((double)f * -683565275.57643163D);
    }

    public final void setDeg(float f, float f1)
    {
        src = (int)(long)((double)f * -11930464.711111112D);
        dst = (int)(long)((double)f1 * -11930464.711111112D);
    }

    public final void setRad(float f, float f1)
    {
        src = (int)(long)((double)f * -683565275.57643163D);
        dst = (int)(long)((double)f1 * -683565275.57643163D);
    }

    public final void reverseSrc()
    {
        src = src + 0x80000000;
    }

    public final void reverseDst()
    {
        dst = dst + 0x80000000;
    }

    public final void rotateDeg(float f)
    {
        int i = (int)(long)((double)f * -11930464.711111112D);
        src += i;
        dst += i;
    }

    public final void makeSrcSameAsDst()
    {
        src = dst;
    }

    public final float getSrcDeg()
    {
        return (float)((double)src * -8.3819031715393066E-008D);
    }

    public final float getDstDeg()
    {
        return (float)((double)dst * -8.3819031715393066E-008D);
    }

    public final float getSrcRad()
    {
        return (float)((double)src * -1.4629180792671596E-009D);
    }

    public final float getDstRad()
    {
        return (float)((double)dst * -1.4629180792671596E-009D);
    }

    public final float getDeg(float f)
    {
        if(f <= 0.0F)
            return (float)((double)src * -8.3819031715393066E-008D);
        if(f >= 1.0F)
            return (float)((double)dst * -8.3819031715393066E-008D);
        else
            return (float)((double)(src + (int)((float)(dst - src) * f)) * -8.3819031715393066E-008D);
    }

    public final float getRad(float f)
    {
        if(f <= 0.0F)
            return (float)((double)src * -1.4629180792671596E-009D);
        if(f >= 1.0F)
            return (float)((double)dst * -1.4629180792671596E-009D);
        else
            return (float)((double)(src + (int)((float)(dst - src) * f)) * -1.4629180792671596E-009D);
    }

    public final float getAbsDiffDeg()
    {
        return (float)java.lang.Math.abs((double)(dst - src) * -8.3819031715393066E-008D);
    }

    public final float getAbsDiffRad()
    {
        return (float)java.lang.Math.abs((double)(dst - src) * -1.4629180792671596E-009D);
    }

    public final float getDiffDeg()
    {
        return (float)((double)(dst - src) * -8.3819031715393066E-008D);
    }

    public final float getDiffRad()
    {
        return (float)((double)(dst - src) * -1.4629180792671596E-009D);
    }

    public final boolean isInsideDeg(float f)
    {
        int i = (int)(long)((double)f * -11930464.711111112D);
        if(dst - src >= 0)
            return i - src >= 0 && dst - i >= 0;
        else
            return i - dst >= 0 && src - i >= 0;
    }

    public final boolean isInsideRad(float f)
    {
        int i = (int)(long)((double)f * -683565275.57643163D);
        if(dst - src >= 0)
            return i - src >= 0 && dst - i >= 0;
        else
            return i - dst >= 0 && src - i >= 0;
    }

    private int src;
    private int dst;
    private static final double halfCircle = -2147483648D;
    private static final double fromDeg = -11930464.711111112D;
    private static final double fromRad = -683565275.57643163D;
    private static final double toDeg = -8.3819031715393066E-008D;
    private static final double toRad = -1.4629180792671596E-009D;
}
