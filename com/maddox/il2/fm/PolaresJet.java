// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PolaresJet.java

package com.maddox.il2.fm;


// Referenced classes of package com.maddox.il2.fm:
//            FMMath

public class PolaresJet extends com.maddox.il2.fm.FMMath
{

    public PolaresJet()
    {
        Cx_Min = new float[3];
        AOA_0 = new float[3];
        AOA_C = new float[3];
        Cy_Crit = new float[3];
        Cx_Crit = new float[3];
        JET = false;
        CANARD = false;
        lastAOA = -1E+012F;
    }

    public float get_Cy(float f)
    {
        if(f == lastAOA)
        {
            return lastCy;
        } else
        {
            polares2(f);
            return lastCy;
        }
    }

    public float get_AOA_CRYT()
    {
        return AOA_C[2];
    }

    public float get_Cx(float f)
    {
        if(f == lastAOA)
        {
            return lastCx;
        } else
        {
            polares2(f);
            return lastCx;
        }
    }

    protected void polares2(float f)
    {
        float f1 = get_Cxa(f);
        float f2 = get_Cya(f);
        float f3 = (float)java.lang.Math.cos(com.maddox.il2.fm.PolaresJet.DEG2RAD(f));
        float f4 = (float)java.lang.Math.sin(com.maddox.il2.fm.PolaresJet.DEG2RAD(f));
        lastCx = f1 * f3 - f2 * f4;
        lastCy = f2 * f3 + f1 * f4;
        lastAOA = f;
    }

    public float get_Cya(float f)
    {
        f -= AOA_0[2];
        if(f > -90F && f < 90F)
        {
            f *= 0.01111111F;
            if(f >= 0.0F)
                f = (float)java.lang.Math.sqrt(f);
            else
                f = -(float)java.lang.Math.sqrt(-f);
            f *= 90F;
        }
        float f1 = com.maddox.il2.fm.PolaresJet.DEG2RAD(f);
        return (float)java.lang.Math.sin(f1 * 2.0F) * Cy_Crit[2];
    }

    public float get_Cxa(float f)
    {
        float f1 = com.maddox.il2.fm.PolaresJet.DEG2RAD(f);
        return (1.0F - (float)java.lang.Math.cos(f1 * 2.0F)) * 0.5F + Cx_Min[2];
    }

    public float get_Cz(float f)
    {
        return 0.3F * (float)java.lang.Math.sin(com.maddox.il2.fm.PolaresJet.DEG2RAD(f) * 2.0F);
    }

    float Cx_Min[];
    float AOA_0[];
    float AOA_C[];
    float Cy_Crit[];
    float Cx_Crit[];
    float CxA;
    float CxCurvature;
    float CxStraightness;
    public boolean JET;
    public boolean CANARD;
    protected float lastAOA;
    protected float lastCx;
    protected float lastCy;
    protected float Flaps;
}
