// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   StrengthProperties.java

package com.maddox.il2.ai;

import com.maddox.rts.SectFile;
import java.io.PrintStream;

public final class StrengthProperties
{

    public StrengthProperties()
    {
        SHOT_MIN_ENERGY = -1F;
        SHOT_MAX_ENERGY = -1F;
        EXPLHIT_MIN_TNT = -1F;
        EXPLHIT_MAX_TNT = -1F;
        EXPLNEAR_MIN_TNT = -1F;
        EXPLNEAR_MAX_TNT = -1F;
    }

    private static float getF(java.lang.String s, com.maddox.rts.SectFile sectfile, java.lang.String s1, java.lang.String s2, float f, float f1)
    {
        float f2 = sectfile.get(s1, s2, -9865.345F);
        if(f2 == -9865.345F || f2 < f || f2 > f1)
        {
            if(f2 == -9865.345F)
                java.lang.System.out.println(s + ": Value of [" + s1 + "]:<" + s2 + "> " + "not found");
            else
                java.lang.System.out.println(s + ": Value of [" + s1 + "]:<" + s2 + "> (" + f2 + ")" + " is out of range (" + f + ";" + f1 + ")");
            throw new RuntimeException("Can't set property");
        } else
        {
            return f2;
        }
    }

    private static void tryToReadStrengthProperties(java.lang.String s, com.maddox.rts.SectFile sectfile, java.lang.String s1, float af[])
    {
        if(sectfile.exist(s1, "MinShotCaliber"))
            af[0] = com.maddox.il2.ai.StrengthProperties.getF(s, sectfile, s1, "MinShotCaliber", 0.0001F, 1.0F);
        if(sectfile.exist(s1, "NumShots"))
            af[1] = com.maddox.il2.ai.StrengthProperties.getF(s, sectfile, s1, "NumShots", 1.0F, 1000F);
        if(sectfile.exist(s1, "MinHitExplTNT"))
            af[2] = com.maddox.il2.ai.StrengthProperties.getF(s, sectfile, s1, "MinHitExplTNT", 0.01F, 1000F);
        if(sectfile.exist(s1, "NumHitExpl"))
            af[3] = com.maddox.il2.ai.StrengthProperties.getF(s, sectfile, s1, "NumHitExpl", 1.0F, 1000F);
        if(sectfile.exist(s1, "MinNearExplTNT"))
            af[4] = com.maddox.il2.ai.StrengthProperties.getF(s, sectfile, s1, "MinNearExplTNT", 0.01F, 1000F);
        if(sectfile.exist(s1, "NumNearExpl"))
            af[5] = com.maddox.il2.ai.StrengthProperties.getF(s, sectfile, s1, "NumNearExpl", 1.0F, 1000F);
    }

    public boolean read(java.lang.String s, com.maddox.rts.SectFile sectfile, java.lang.String s1, java.lang.String s2)
    {
        for(int i = 0; i < 6; i++)
            tmpf6[i] = -1F;

        if(s1 != null)
            com.maddox.il2.ai.StrengthProperties.tryToReadStrengthProperties(s, sectfile, s1, tmpf6);
        com.maddox.il2.ai.StrengthProperties.tryToReadStrengthProperties(s, sectfile, s2, tmpf6);
        for(int j = 0; j < 6; j++)
            if(tmpf6[j] < 0.0F)
            {
                java.lang.System.out.println(s + ": " + "Not enough strength data  in '" + s2 + "'");
                return false;
            }

        SHOT_MIN_ENERGY = -1F;
        for(int k = 0; k < caliber2energy.length; k += 2)
            if(caliber2energy[k] == tmpf6[0])
                SHOT_MIN_ENERGY = caliber2energy[k + 1];

        if(SHOT_MIN_ENERGY <= 0.0F)
        {
            java.lang.System.out.println(s + ": " + "Unknown shot caliber for '" + s2 + "'");
            return false;
        } else
        {
            SHOT_MAX_ENERGY = SHOT_MIN_ENERGY;
            SHOT_MAX_ENERGY *= tmpf6[1];
            EXPLHIT_MIN_TNT = tmpf6[2];
            EXPLHIT_MAX_TNT = EXPLHIT_MIN_TNT;
            EXPLHIT_MAX_TNT *= tmpf6[3];
            EXPLNEAR_MIN_TNT = tmpf6[4];
            EXPLNEAR_MAX_TNT = EXPLNEAR_MIN_TNT;
            EXPLNEAR_MAX_TNT *= tmpf6[5];
            return true;
        }
    }

    public float SHOT_MIN_ENERGY;
    public float SHOT_MAX_ENERGY;
    public float EXPLHIT_MIN_TNT;
    public float EXPLHIT_MAX_TNT;
    public float EXPLNEAR_MIN_TNT;
    public float EXPLNEAR_MAX_TNT;
    private static float tmpf6[] = new float[6];
    private static float caliber2energy[] = {
        0.004F, 511.225F, 0.00762F, 2453.88F, 0.0127F, 10140F, 0.02F, 23400F, 0.037F, 131400F, 
        0.045F, 252000F, 0.05F, 369000F, 0.075F, 1224000F, 0.1F, 3295500F, 0.203F, 5120000F
    };

}
