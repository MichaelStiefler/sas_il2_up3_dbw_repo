// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Polares.java

package com.maddox.il2.fm;

import com.maddox.rts.HomePath;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

// Referenced classes of package com.maddox.il2.fm:
//            FMMath, Atmosphere

public class Polares extends com.maddox.il2.fm.FMMath
{

    public Polares()
    {
        lastAOA = -1E+012F;
        normP = new float[250];
        maxP = new float[250];
        AOA_crit = 17F;
        V_max = 153F;
        V_min = 35F;
        P_Vmax = 18000F;
        G = 26000F;
        S = 18F;
        K_max = 14F;
        Cy0_max = 0.18F;
        Tfac = 1.0F;
        Vyfac = 1.0F;
        FlapsMult = 0.16F;
        FlapsAngSh = 4F;
        Vz_climb = 21F;
        V_climb = 72F;
        T_turn = 19F;
        V_turn = 310F;
        V_maxFlaps = 180F;
        V_land = 144F;
        AOA_land = 12F;
        Ro = com.maddox.il2.fm.Atmosphere.ro0();
        R1000 = com.maddox.il2.fm.Atmosphere.density(1000F);
        sign = 1.0F;
        AOALineH = 13F;
        AOALineL = -13F;
        AOACritH = 16F;
        AOACritL = -16F;
        parabCyCoeffH = 0.01F;
        parabCyCoeffL = 0.01F;
        Cy0 = 0.0F;
        lineCyCoeff = 0.85F;
        declineCoeff = 0.007F;
        maxDistAng = 30F;
        parabAngle = 5F;
        CyCritH = 1.0F;
        CyCritL = -1F;
        AOAMinCx = -1F;
        AOAParabH = 6F;
        AOAParabL = -6F;
        CxMin = 0.02F;
        parabCxCoeff = 0.0008F;
        AOACritH_0 = 16F;
        AOACritL_0 = -16F;
        Cy0_0 = 0.0F;
        CyCritH_0 = 1.0F;
        CyCritL_0 = -1F;
        AOAMinCx_Shift = 0.0F;
        CxMin_0 = 0.02F;
        parabCxCoeff_0 = 0.0008F;
        AOACritH_1 = 16F;
        AOACritL_1 = -16F;
        Cy0_1 = 0.0F;
        CyCritH_1 = 1.0F;
        CyCritL_1 = -1F;
        CxMin_1 = 0.02F;
        parabCxCoeff_1 = 0.0008F;
    }

    public final float new_Cy(float f)
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

    public final float new_Cx(float f)
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

    protected final void polares2(float f)
    {
        float f1 = new_Cxa(f);
        float f2 = new_Cya(f);
        float f3 = (float)java.lang.Math.cos(com.maddox.il2.fm.FMMath.DEG2RAD(f));
        float f4 = (float)java.lang.Math.sin(com.maddox.il2.fm.FMMath.DEG2RAD(f));
        lastAOA = f;
        lastCx = f1 * f3 - f2 * f4;
        lastCy = f2 * f3 + f1 * f4;
    }

    public float get_AOA_CRYT()
    {
        return AOACritH;
    }

    public final void setFlaps(float f)
    {
        float f1 = 0.25F * f + 0.75F * (float)java.lang.Math.sqrt(f);
        Cy0 = Cy0_0 + (Cy0_1 - Cy0_0) * f1;
        CyCritH = CyCritH_0 + (CyCritH_1 - CyCritH_0) * f1;
        CyCritL = CyCritL_0 + (CyCritL_1 - CyCritL_0) * f1;
        AOACritH = AOACritH_0 + (AOACritH_1 - AOACritH_0) * f1;
        AOACritL = AOACritL_0 + (AOACritL_1 - AOACritL_0) * f1;
        AOALineH = (2.0F * (CyCritH - Cy0)) / lineCyCoeff - AOACritH;
        parabCyCoeffH = (0.5F * lineCyCoeff) / (AOACritH - AOALineH);
        AOALineL = (2.0F * (CyCritL - Cy0)) / lineCyCoeff - AOACritL;
        parabCyCoeffL = (0.5F * lineCyCoeff) / (AOALineL - AOACritL);
        CxMin = CxMin_0 + (CxMin_1 - CxMin_0) * f;
        parabCxCoeff = parabCxCoeff_0 + (parabCxCoeff_1 - parabCxCoeff_0) * f;
        AOAMinCx = AOAMinCx_Shift - Cy0 / lineCyCoeff;
    }

    public final void setCxMin_0()
    {
        AOAMinCx = AOAMinCx_Shift - Cy0_0 / lineCyCoeff;
        float f = S * Ro * V_max * V_max;
        float f1 = (2.0F * G) / f;
        float f2 = (2.0F * P_Vmax) / f;
        float f3 = (f1 - Cy0_0) / lineCyCoeff;
        float f4 = f3 - AOAMinCx;
        CxMin_0 = f2 - parabCxCoeff * f4 * f4;
    }

    public final void setCoeffs(float f, float f1)
    {
        lineCyCoeff = (CyCritH * f - Cy0) / AOACritH;
        CyCritL = (Cy0 + lineCyCoeff * AOACritL) / f;
        AOAMinCx = (-f1 * Cy0) / lineCyCoeff;
        float f2 = S * Ro * V_max * V_max;
        float f3 = (2.0F * G) / f2;
        float f4 = (2.0F * P_Vmax) / f2;
        float f5 = (f3 - Cy0) / lineCyCoeff;
        if(AOAMinCx > f5)
            AOAMinCx = f5;
        float f6 = f5 - AOAMinCx;
        AOAMinCx_Shift = AOAMinCx - -Cy0 / lineCyCoeff;
        CxMin = f4 - parabCxCoeff * f6 * f6;
        AOALineH = (2.0F * (CyCritH - Cy0)) / lineCyCoeff - AOACritH;
        parabCyCoeffH = (0.5F * lineCyCoeff) / (AOACritH - AOALineH);
        AOALineL = (2.0F * (CyCritL - Cy0)) / lineCyCoeff - AOACritL;
        parabCyCoeffL = (0.5F * lineCyCoeff) / (AOALineL - AOACritL);
    }

    public final void calcPolares()
    {
        CyCritH = (2.0F * G) / (S * Ro * V_min * V_min);
        AOACritH = AOA_crit;
        boolean flag = false;
        float f = 10000F;
        float f8 = 0.0F;
        float f9 = 0.0F;
        float f10 = 0.0F;
        float f11 = 0.0F;
        for(int i = 0; i <= 25; i++)
        {
            Cy0 = 0.05F + (float)i * 0.01F;
            if(Cy0 <= Cy0_max)
            {
                for(int j = 0; j < 200; j++)
                {
                    float f6 = 1.0F + (float)j * 0.006F;
                    for(int k = 0; k < 100; k++)
                    {
                        parabCxCoeff = 0.0003F + (float)k * 2E-005F;
                        for(int l = 0; l <= 20; l++)
                        {
                            float f7 = 1.0F - (float)l * 0.05F;
                            setCoeffs(f6, f7);
                            if(lineCyCoeff <= 0.12F && CxMin >= 0.005F && AOAMinCx <= 0.0F)
                            {
                                boolean flag1 = true;
                                float f1 = -10000F;
                                for(int i1 = 0; i1 < 20; i1++)
                                {
                                    float f12 = 0.5F * (float)i1;
                                    float f13 = new_Cya(f12) / new_Cxa(f12);
                                    if(f1 >= f13)
                                        break;
                                    f1 = f13;
                                }

                                if(f1 <= 1.3F * K_max && f1 >= 0.6F * K_max)
                                {
                                    byte byte0 = 2;
                                    float f2 = -10000F;
                                    float f3 = 300F;
                                    for(int j1 = 25; j1 < 200; j1++)
                                    {
                                        float f14 = S * Ro * (float)j1 * (float)j1;
                                        float f15 = (2.0F * G) / f14;
                                        float f18 = (f15 - Cy0) / lineCyCoeff;
                                        float f21 = f18 - AOAMinCx;
                                        float f24 = 0.5F * (CxMin + parabCxCoeff * f21 * f21) * f14;
                                        float f27 = ((float)j1 * (normP[j1] - f24)) / G;
                                        if(f2 >= f27)
                                            break;
                                        f2 = f27;
                                        f3 = j1;
                                    }

                                    if(f2 <= f2 + 3F && f2 >= f2 - 3F)
                                    {
                                        byte byte1 = 3;
                                        if(f3 <= 1.3F * V_climb && f3 >= 0.7F * V_climb)
                                        {
                                            byte byte2 = 4;
                                            float f4 = 10000F;
                                            float f5 = 300F;
                                            for(int k1 = 125; k1 > 40; k1--)
                                            {
                                                float f16 = S * R1000 * (float)k1 * (float)k1;
                                                float f19 = (2.0F * maxP[k1]) / f16;
                                                if(f19 < CxMin)
                                                    continue;
                                                float f22 = (float)java.lang.Math.sqrt((f19 - CxMin) / parabCxCoeff);
                                                float f25 = AOAMinCx + f22;
                                                if(f25 > 12.5F)
                                                    f25 = 12.5F;
                                                float f28 = (0.5F * new_Cya(f25) * f16) / G;
                                                float f30 = (float)java.lang.Math.sqrt(f28 * f28 - 1.0F);
                                                float f32 = (6.283185F * (float)k1) / (9.8F * f30);
                                                if(f32 > 60F)
                                                    continue;
                                                if(f4 <= f32)
                                                    break;
                                                f4 = f32;
                                                f5 = k1;
                                            }

                                            byte2 = 5;
                                            byte2 = 6;
                                            float f17 = K_max - f1;
                                            f17 = java.lang.Math.abs(f17);
                                            float f20 = Vz_climb - f2;
                                            f20 = java.lang.Math.abs(f20);
                                            float f23 = V_climb - f3;
                                            f23 = java.lang.Math.abs(f23);
                                            float f26 = T_turn - f4;
                                            f26 = java.lang.Math.abs(f26);
                                            float f29 = V_turn - f5;
                                            f29 = java.lang.Math.abs(f29);
                                            float f31 = 2.0F * f17 + 12F * f20 + 5F * f20 + 15F * f26 + 2.0F * f29;
                                            if(f > f31)
                                            {
                                                f = f31;
                                                f8 = f6;
                                                f9 = f7;
                                                f10 = parabCxCoeff;
                                                f11 = Cy0;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }

                }

            }
        }

        Cy0 = f11;
        parabCxCoeff = f10;
        setCoeffs(f8, f9);
        Cy0_0 = Cy0;
        AOACritH_0 = AOACritH;
        AOACritL_0 = AOACritL;
        CyCritH_0 = CyCritH;
        CyCritL_0 = CyCritL;
        CxMin_0 = CxMin;
        parabCxCoeff_0 = parabCxCoeff;
        calcFlaps();
        Cy0_1 = Cy0;
        AOACritH_1 = AOACritH;
        AOACritL_1 = AOACritL;
        CyCritH_1 = CyCritH;
        CyCritL_1 = CyCritL;
        CxMin_1 = CxMin;
        parabCxCoeff_1 = parabCxCoeff;
    }

    public final void calcFlaps()
    {
        float f = S * Ro * V_land * V_land;
        float f1 = (2.0F * G) / f;
        Cy0 = f1 - AOA_land * lineCyCoeff;
        AOAMinCx = -Cy0 / lineCyCoeff + AOAMinCx_Shift;
        parabCxCoeff = parabCxCoeff * FlapsMult;
        f = S * Ro * V_maxFlaps * V_maxFlaps;
        f1 = (2.0F * G) / f;
        float f2 = (2.0F * P_Vmax) / f;
        float f3 = (f1 - Cy0) / lineCyCoeff;
        float f4 = f3 - AOAMinCx;
        CxMin = f2 - parabCxCoeff * f4;
        AOACritH = AOACritH_0 - 4F;
        CyCritH = 0.85F * (Cy0 + AOACritH * lineCyCoeff);
        AOACritL = AOACritL_0 - FlapsAngSh;
        float f5 = -0.9F * (float)java.lang.Math.sin(-0.03926991F * AOACritL);
        CyCritL = java.lang.Math.min(-0.7F, f5);
        AOALineH = (2.0F * (CyCritH - Cy0)) / lineCyCoeff - AOACritH;
        parabCyCoeffH = (0.5F * lineCyCoeff) / (AOACritH - AOALineH);
        AOALineL = (2.0F * (CyCritL - Cy0)) / lineCyCoeff - AOACritL;
        parabCyCoeffL = (0.5F * lineCyCoeff) / (AOALineL - AOACritL);
    }

    public float getClimb(float f, float f1, float f2)
    {
        float f3 = com.maddox.il2.fm.Atmosphere.density(f1);
        float f4 = S * f3 * f * f;
        float f5 = (2.0F * G) / f4;
        float f6 = (f5 - Cy0) / lineCyCoeff;
        float f7 = f6 - AOAMinCx;
        float f8 = 0.5F * (CxMin + parabCxCoeff * f7 * f7) * f4;
        float f9 = (f * (f2 - f8)) / G;
        return f9;
    }

    public final void saveCoeffs()
    {
        try
        {
            java.io.PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter(com.maddox.rts.HomePath.toFileSystemName("Coeffs.txt", 0))));
            printwriter.println("; ==============================================================================");
            printwriter.println("[Polares]");
            printwriter.println("; ==============================================================================");
            printwriter.println("lineCyCoeff        = " + lineCyCoeff);
            printwriter.println("AOAMinCx_Shift     = " + AOAMinCx_Shift);
            printwriter.println("Cy0_0              = " + Cy0_0);
            printwriter.println("AOACritH_0         = " + AOACritH_0);
            printwriter.println("AOACritL_0         = " + AOACritL_0);
            printwriter.println("CyCritH_0          = " + CyCritH_0);
            printwriter.println("CyCritL_0          = " + CyCritL_0);
            printwriter.println("CxMin_0            = " + CxMin_0);
            printwriter.println("parabCxCoeff_0     = " + parabCxCoeff_0);
            printwriter.println("Cy0_1              = " + Cy0_1);
            printwriter.println("AOACritH_1         = " + AOACritH_1);
            printwriter.println("AOACritL_1         = " + AOACritL_1);
            printwriter.println("CyCritH_1          = " + CyCritH_1);
            printwriter.println("CyCritL_1          = " + CyCritL_1);
            printwriter.println("CxMin_1            = " + CxMin_1);
            printwriter.println("parabCxCoeff_1     = " + parabCxCoeff_1);
            printwriter.close();
        }
        catch(java.io.IOException ioexception)
        {
            java.lang.System.out.println("File save failed: " + ioexception.getMessage());
            ioexception.printStackTrace();
        }
    }

    public final void drawGraphs(java.lang.String s)
    {
        float f = -10000F;
        float f1 = 0.0F;
        float f3 = 0.0F;
        float f5 = 0.0F;
        float f7 = 0.0F;
        try
        {
            java.io.PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter(com.maddox.rts.HomePath.toFileSystemName("Polar.txt", 0))));
            for(int i = -90; i < 90; i++)
                printwriter.print(i + "\t");

            printwriter.println();
            for(int i2 = 0; i2 <= 5; i2++)
            {
                setFlaps((float)i2 * 0.2F);
                for(int j = -90; j < 90; j++)
                    printwriter.print(new_Cya(j) + "\t");

                printwriter.println();
                for(int k = -90; k < 90; k++)
                    printwriter.print(new_Cxa(k) + "\t");

                printwriter.println();
                if(i2 == 0)
                {
                    for(int l = -90; l < 90; l++)
                    {
                        float f9 = new_Cya(l) / new_Cxa(l);
                        printwriter.print(f9 * 0.1F + "\t");
                        if(f < f9)
                            f = f9;
                    }

                    printwriter.println();
                }
                for(int i1 = -90; i1 < 90; i1++)
                {
                    float f10 = Cy0 + lineCyCoeff * (float)i1;
                    if((double)f10 < 2D && (double)f10 > -2D)
                        printwriter.print(f10 + "\t");
                    else
                        printwriter.print("\t");
                }

                printwriter.println();
            }

            printwriter.close();
        }
        catch(java.io.IOException ioexception)
        {
            java.lang.System.out.println("File save failed: " + ioexception.getMessage());
            ioexception.printStackTrace();
        }
        try
        {
            java.io.PrintWriter printwriter1 = new PrintWriter(new BufferedWriter(new FileWriter(com.maddox.rts.HomePath.toFileSystemName(s, 0))));
            for(int j1 = 120; j1 < 620; j1 += 2)
                printwriter1.print(j1 + "\t");

            printwriter1.println();
            float f2 = -10000F;
            float f4 = 300F;
            float f6 = 10000F;
            float f8 = 300F;
            for(int j2 = 0; j2 <= 3; j2++)
            {
                switch(j2)
                {
                case 0: // '\0'
                    setFlaps(0.0F);
                    break;

                case 1: // '\001'
                    setFlaps(0.2F);
                    break;

                case 2: // '\002'
                    setFlaps(0.33F);
                    break;

                case 3: // '\003'
                    setFlaps(1.0F);
                    break;
                }
                if(j2 == 0)
                {
                    for(int k1 = 120; k1 < 620; k1 += 2)
                    {
                        float f11 = (float)k1 * 0.27778F;
                        float f13 = S * Ro * f11 * f11;
                        float f15 = (2.0F * G) / f13;
                        float f17 = getAoAbyCy(f15);
                        float f19 = f17 - AOAMinCx;
                        float f21 = 0.5F * (CxMin + parabCxCoeff * f19 * f19) * f13;
                        float f23 = (f11 * (normP[(int)f11] - f21)) / G;
                        if(j2 == 0 && f2 < f23)
                        {
                            f2 = f23;
                            f4 = f11;
                        }
                        if(f23 < -10F)
                            printwriter1.print("\t");
                        else
                            printwriter1.print(f23 * Vyfac + "\t");
                    }

                    printwriter1.println();
                }
                for(int l1 = 120; l1 < 620; l1 += 2)
                {
                    float f12 = (float)l1 * 0.27778F;
                    float f14 = S * R1000 * f12 * f12;
                    float f16 = (2.0F * maxP[(int)f12]) / f14;
                    float f18 = (float)java.lang.Math.sqrt((f16 - CxMin) / parabCxCoeff);
                    float f20 = AOAMinCx + f18;
                    if(f20 > AOACritH)
                        f20 = AOACritH;
                    float f22 = (0.5F * new_Cya(f20) * f14) / G;
                    float f24 = (float)java.lang.Math.sqrt(f22 * f22 - 1.0F);
                    float f25 = 0.0F;
                    if(f24 > 0.2F)
                        f25 = (6.283185F * f12) / (9.8F * f24);
                    if(f25 > 40F)
                        f25 = 0.0F;
                    if(f25 == 0.0F)
                        printwriter1.print("\t");
                    else
                        printwriter1.print(f25 * Tfac + "\t");
                    if(j2 == 0 && f25 > 0.0F && f6 > f25)
                    {
                        f6 = f25;
                        f8 = f12;
                    }
                }

                printwriter1.println();
            }

            printwriter1.println("M_takeoff:\t" + G / 9.8F);
            printwriter1.println("K_max:\t" + f);
            printwriter1.println("T_turn:\t" + f6 * Tfac);
            printwriter1.println("V_turn:\t" + f8 * 3.6F);
            printwriter1.println("Vz_climb:\t" + f2 * Vyfac);
            printwriter1.println("V_climb:\t" + f4 * 3.6F);
            printwriter1.close();
        }
        catch(java.io.IOException ioexception1)
        {
            java.lang.System.out.println("File save failed: " + ioexception1.getMessage());
            ioexception1.printStackTrace();
        }
    }

    public final float getAoAbyCy(float f)
    {
        if(f > CyCritH)
            return 90F;
        float f1 = (f - Cy0) / lineCyCoeff;
        if(f1 <= AOALineH)
            return f1;
        if(f1 >= AOACritH)
            return 90F;
        float f2 = AOALineH;
        float f3 = AOACritH;
        for(int i = 0; i < 1000; i++)
        {
            float f4 = new_Cya(f1);
            if((double)java.lang.Math.abs(f4 - f) < 0.0001D)
                return f1;
            if(f4 < f)
                f2 = f1;
            else
                f3 = f1;
            f1 = 0.5F * (f2 + f3);
        }

        return f1;
    }

    public final float new_Cya(float f)
    {
        if(f <= AOALineH && f >= AOALineL)
            return Cy0 + lineCyCoeff * f;
        if(f > 0.0F)
        {
            if(f <= AOACritH)
            {
                float f1 = AOACritH - f;
                f1 *= f1;
                return CyCritH - parabCyCoeffH * f1;
            }
            if(f <= 40F)
                if(f <= maxDistAng)
                {
                    float f2 = f - AOACritH;
                    if(f2 < parabAngle)
                    {
                        return CyCritH - declineCoeff * f2 * f2;
                    } else
                    {
                        float f10 = 0.9F * (float)java.lang.Math.sin(0.03926991F * maxDistAng);
                        float f12 = maxDistAng - parabAngle - AOACritH;
                        float f14 = CyCritH - declineCoeff * parabAngle * parabAngle - f10;
                        float f15 = f14 / (f12 * f12);
                        float f3 = maxDistAng - f;
                        return f10 + f15 * f3 * f3;
                    }
                } else
                {
                    return 0.9F * (float)java.lang.Math.sin(0.03926991F * f);
                }
            if(f <= 140F)
            {
                sign = 1.0F;
                if(f > 90F)
                {
                    sign = -1F;
                    f = 40F + (140F - f);
                }
                float f4 = 0.9F * (float)java.lang.Math.sin(1.570796F + 0.03141593F * (f - 40F));
                return f4 * sign;
            } else
            {
                sign = -1F;
                f = 180F - f;
                float f5 = 0.9F * (float)java.lang.Math.sin(0.03926991F * f);
                return f5 * sign;
            }
        }
        if(f >= AOACritL)
        {
            float f6 = f - AOACritL;
            f6 *= f6;
            return CyCritL + parabCyCoeffL * f6;
        }
        if(f >= -40F)
        {
            float f7 = AOACritL - f;
            float f11 = CyCritL + 0.007F * f7 * f7;
            float f13 = -0.9F * (float)java.lang.Math.sin(-0.03926991F * f);
            return java.lang.Math.min(f11, f13);
        }
        f = -f;
        if(f <= 140F)
        {
            sign = -1F;
            if(f > 90F)
            {
                sign = 1.0F;
                f = 40F + (140F - f);
            }
            float f8 = 0.9F * (float)java.lang.Math.sin(1.570796F + 0.03141593F * (f - 40F));
            return f8 * sign;
        } else
        {
            sign = 1.0F;
            f = 180F - f;
            float f9 = 0.9F * (float)java.lang.Math.sin(0.03926991F * f);
            return f9 * sign;
        }
    }

    public final float new_Cxa(float f)
    {
        float f1 = f - AOAMinCx;
        float f2 = CxMin + parabCxCoeff * f1 * f1;
        if(f <= AOAParabH && f >= AOAParabL)
            return f2;
        if(f >= AOACritH)
            f2 += 0.03F * (f - AOACritH);
        else
        if(f <= AOACritL)
            f2 += 0.03F * (AOACritL - f);
        float f3 = 0.2F + 1.2F * (float)java.lang.Math.abs(java.lang.Math.sin(com.maddox.il2.fm.FMMath.DEG2RAD(f)));
        return java.lang.Math.min(f2, f3);
    }

    public final float new_Cz(float f)
    {
        return 0.7F * (float)java.lang.Math.sin(com.maddox.il2.fm.FMMath.DEG2RAD(f));
    }

    public final float getFlaps()
    {
        return Flaps;
    }

    protected float lastAOA;
    protected float lastCx;
    protected float lastCy;
    protected float Flaps;
    float normP[];
    float maxP[];
    public float AOA_crit;
    public float V_max;
    public float V_min;
    public float P_Vmax;
    public float G;
    public float S;
    public float K_max;
    public float Cy0_max;
    public float Tfac;
    public float Vyfac;
    public float FlapsMult;
    public float FlapsAngSh;
    public float Vz_climb;
    public float V_climb;
    public float T_turn;
    public float V_turn;
    public float V_maxFlaps;
    public float V_land;
    public float AOA_land;
    private float Ro;
    private float R1000;
    private float sign;
    private float AOALineH;
    private float AOALineL;
    public float AOACritH;
    public float AOACritL;
    private float parabCyCoeffH;
    private float parabCyCoeffL;
    private float Cy0;
    public float lineCyCoeff;
    public float declineCoeff;
    public float maxDistAng;
    public float parabAngle;
    private float CyCritH;
    private float CyCritL;
    public float AOAMinCx;
    private float AOAParabH;
    private float AOAParabL;
    private float CxMin;
    private float parabCxCoeff;
    public float AOACritH_0;
    public float AOACritL_0;
    public float Cy0_0;
    public float CyCritH_0;
    public float CyCritL_0;
    public float AOAMinCx_Shift;
    public float CxMin_0;
    public float parabCxCoeff_0;
    public float AOACritH_1;
    public float AOACritL_1;
    public float Cy0_1;
    public float CyCritH_1;
    public float CyCritL_1;
    public float CxMin_1;
    public float parabCxCoeff_1;
}
