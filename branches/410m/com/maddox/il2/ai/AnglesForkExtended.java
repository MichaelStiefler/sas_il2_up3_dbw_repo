// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AnglesForkExtended.java

package com.maddox.il2.ai;


public final class AnglesForkExtended
{

    public AnglesForkExtended(boolean flag)
    {
        setDeg(flag, 0.0F, 0.0F);
    }

    public AnglesForkExtended(boolean flag, float f, float f1)
    {
        setDeg(flag, f, f1);
    }

    public final void setDeg(boolean flag, float f, float f1)
    {
        fullcircle = flag;
        if(fullcircle)
        {
            src = (int)(long)((double)f * -11930464.711111112D);
            dst = (int)(long)((double)f1 * -11930464.711111112D);
        } else
        {
            src = (int)(long)((double)f * -5965232.3555555558D);
            dst = (int)(long)((double)f1 * -5965232.3555555558D);
        }
    }

    public final void setDeg(float f, float f1)
    {
        setDeg(fullcircle, f, f1);
    }

    public final void setDeg(float f)
    {
        setDeg(f, f);
    }

    public final void setSrcDeg(float f)
    {
        if(fullcircle)
            src = (int)(long)((double)f * -11930464.711111112D);
        else
            src = (int)(long)((double)f * -5965232.3555555558D);
    }

    public final void setDstDeg(boolean flag, float f)
    {
        if(fullcircle)
            dst = (int)(long)((double)f * -11930464.711111112D);
        else
            dst = (int)(long)((double)f * -5965232.3555555558D);
    }

    public final void rotateDeg(float f)
    {
        if(fullcircle)
        {
            int i = (int)(long)((double)f * -11930464.711111112D);
            src += i;
            dst += i;
        } else
        {
            int j = (int)(long)((double)f * -5965232.3555555558D);
            src += j;
            dst += j;
        }
    }

    public final void makeSrcSameAsDst()
    {
        src = dst;
    }

    public final float getSrcDeg()
    {
        if(fullcircle)
            return (float)((double)src * -8.3819031715393066E-008D);
        else
            return (float)((double)src * -1.6763806343078613E-007D);
    }

    public final float getDstDeg()
    {
        if(fullcircle)
            return (float)((double)dst * -8.3819031715393066E-008D);
        else
            return (float)((double)dst * -1.6763806343078613E-007D);
    }

    public final float getDeg(float f)
    {
        if(fullcircle)
        {
            if(f <= 0.0F)
                return (float)((double)src * -8.3819031715393066E-008D);
            if(f >= 1.0F)
                return (float)((double)dst * -8.3819031715393066E-008D);
            else
                return (float)((double)(src + (int)((double)(dst - src) * (double)f)) * -8.3819031715393066E-008D);
        }
        if(f <= 0.0F)
            return (float)((double)src * -1.6763806343078613E-007D);
        if(f >= 1.0F)
            return (float)((double)dst * -1.6763806343078613E-007D);
        else
            return (float)((double)(int)((long)src + (long)((double)((long)dst - (long)src) * (double)f)) * -1.6763806343078613E-007D);
    }

    public final float getAbsDiffDeg()
    {
        if(fullcircle)
            return (float)java.lang.Math.abs((double)(dst - src) * -8.3819031715393066E-008D);
        else
            return (float)java.lang.Math.abs((double)((long)dst - (long)src) * -1.6763806343078613E-007D);
    }

    public final float getDiffDeg()
    {
        if(fullcircle)
            return (float)((double)(dst - src) * -8.3819031715393066E-008D);
        else
            return (float)((double)((long)dst - (long)src) * -1.6763806343078613E-007D);
    }

    private int src;
    private int dst;
    private boolean fullcircle;
    private static final double halfCircle = -2147483648D;
    private static final double fromDeg = -11930464.711111112D;
    private static final double fromDeg2 = -5965232.3555555558D;
    private static final double toDeg = -8.3819031715393066E-008D;
    private static final double toDeg2 = -1.6763806343078613E-007D;
}
