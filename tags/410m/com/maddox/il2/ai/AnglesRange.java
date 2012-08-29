// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AnglesRange.java

package com.maddox.il2.ai;

import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.ai:
//            AnglesFork

public final class AnglesRange
{

    public AnglesRange(float f, float f1)
    {
        set(f, f1);
    }

    public boolean fullcircle()
    {
        return maxA - minA >= 360F;
    }

    public boolean isInside(float f)
    {
        if(fullcircle())
            return true;
        else
            return f >= minA && f <= maxA;
    }

    public final void set(float f, float f1)
    {
        if(f1 - f >= 360F)
        {
            minA = -181F;
            maxA = 181F;
        } else
        if(f <= -360F || f1 >= 360F)
        {
            java.lang.System.out.println("*** err1: AnglesRange(" + f + "," + f1 + ")");
            minA = 0.0F;
            maxA = 0.0F;
        } else
        {
            minA = f;
            maxA = f1;
        }
    }

    public float transformIntoRangeSpace(float f)
    {
        if(fullcircle())
            if(f >= -180F && f <= 180F)
                return f;
            else
                return com.maddox.il2.ai.AnglesFork.signedAngleDeg(f);
        if(f >= minA && f <= maxA)
        {
            return f;
        } else
        {
            float f1 = (minA + maxA) * 0.5F;
            tmpfork.setDeg(f1, f);
            float f2 = tmpfork.getDiffDeg();
            return f1 + f2;
        }
    }

    public java.lang.String toString()
    {
        return "(" + minA + "," + maxA + ")";
    }

    private float minA;
    private float maxA;
    private static com.maddox.il2.ai.AnglesFork tmpfork = new AnglesFork();

}
