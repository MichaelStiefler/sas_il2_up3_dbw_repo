// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Pitot.java

package com.maddox.il2.fm;


// Referenced classes of package com.maddox.il2.fm:
//            Atmosphere

public class Pitot
{

    public Pitot()
    {
    }

    private static final float poly(float af[], float f)
    {
        return (((af[4] * f + af[3]) * f + af[2]) * f + af[1]) * f + af[0];
    }

    public static final float Indicator(float f, float f1)
    {
        f1 *= (float)java.lang.Math.sqrt(com.maddox.il2.fm.Atmosphere.density(f) / 1.225F);
        return f1;
    }

    private static final float pitot[] = {
        0.0F, 0.630378F, 0.00632175F, -3.07351E-005F, 4.47977E-008F
    };

}
