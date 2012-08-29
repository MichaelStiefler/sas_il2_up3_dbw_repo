// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Atmosphere.java

package com.maddox.il2.fm;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Engine;

public class Atmosphere
{

    public Atmosphere()
    {
        g = 9.8F;
        P0 = 101300F;
        T0 = 288.16F;
        ro0 = 1.225F;
        Mu0 = 1.825E-006F;
    }

    public static final float g()
    {
        return com.maddox.il2.ai.World.cur().Atm.g;
    }

    public static final float P0()
    {
        return com.maddox.il2.ai.World.cur().Atm.P0;
    }

    public static final float T0()
    {
        return com.maddox.il2.ai.World.cur().Atm.T0;
    }

    public static final float ro0()
    {
        return com.maddox.il2.ai.World.cur().Atm.ro0;
    }

    public static final float Mu0()
    {
        return com.maddox.il2.ai.World.cur().Atm.Mu0;
    }

    private static final float poly(float af[], float f)
    {
        return (((af[4] * f + af[3]) * f + af[2]) * f + af[1]) * f + af[0];
    }

    public static final void set(float f, float f1)
    {
        f *= 133.2895F;
        f1 += 273.16F;
        if(com.maddox.il2.engine.Engine.cur == null || com.maddox.il2.engine.Engine.cur.world == null)
        {
            return;
        } else
        {
            com.maddox.il2.ai.World.cur().Atm.P0 = f;
            com.maddox.il2.ai.World.cur().Atm.T0 = f1;
            com.maddox.il2.ai.World.cur().Atm.ro0 = 1.225F * (f / 101300F) * (288.16F / f1);
            return;
        }
    }

    public static final float pressure(float f)
    {
        if(f > 18300F)
            return (18300F / f) * com.maddox.il2.fm.Atmosphere.P0() * com.maddox.il2.fm.Atmosphere.poly(Pressure, 18300F);
        else
            return com.maddox.il2.fm.Atmosphere.P0() * com.maddox.il2.fm.Atmosphere.poly(Pressure, f);
    }

    public static final float temperature(float f)
    {
        if(f > 18300F)
            f = 18300F;
        return com.maddox.il2.fm.Atmosphere.T0() * com.maddox.il2.fm.Atmosphere.poly(Temperature, f);
    }

    public static final float sonicSpeed(float f)
    {
        return 20.1F * (float)java.lang.Math.sqrt(com.maddox.il2.fm.Atmosphere.temperature(f));
    }

    public static final float density(float f)
    {
        if(f > 18300F)
            return (18300F / f) * com.maddox.il2.fm.Atmosphere.ro0() * com.maddox.il2.fm.Atmosphere.poly(Density, 18300F);
        else
            return com.maddox.il2.fm.Atmosphere.ro0() * com.maddox.il2.fm.Atmosphere.poly(Density, f);
    }

    public static final float viscosity(float f)
    {
        return com.maddox.il2.fm.Atmosphere.Mu0() * (float)java.lang.Math.pow(com.maddox.il2.fm.Atmosphere.temperature(f) / com.maddox.il2.fm.Atmosphere.T0(), 0.76000000000000001D);
    }

    public static final float kineticViscosity(float f)
    {
        return com.maddox.il2.fm.Atmosphere.viscosity(f) / com.maddox.il2.fm.Atmosphere.density(f);
    }

    private float g;
    private float P0;
    private float T0;
    private float ro0;
    private float Mu0;
    private static final float Density[] = {
        1.0F, -9.59387E-005F, 3.53118E-009F, -5.83556E-014F, 2.28719E-019F
    };
    private static final float Pressure[] = {
        1.0F, -0.000118441F, 5.6763E-009F, -1.3738E-013F, 1.60373E-018F
    };
    private static final float Temperature[] = {
        1.0F, -2.27712E-005F, 2.18069E-010F, -5.71104E-014F, 3.97306E-018F
    };

}
