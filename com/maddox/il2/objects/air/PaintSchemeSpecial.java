// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeSpecial.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeFMPar04

public class PaintSchemeSpecial extends com.maddox.il2.objects.air.PaintSchemeFMPar04
{

    public PaintSchemeSpecial()
    {
    }

    public java.lang.String typedNameNum(java.lang.Class class1, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.BF_109G6GRAF.class))
            return "+ 1";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.BF_109G6HARTMANN.class))
            return "1 + -";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.BF_109G6HEPPES.class))
            return "V8 + 10";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.BF_109G6KOVACS.class))
            return "V-3 + 74";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.BF_109G6MOLNAR.class))
            return "+ 66";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.BF_109G10FABIAN.class))
            return "5 +";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.I_16TYPE24SAFONOV.class))
            return "* 11";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.JU_87G2RUDEL.class))
            return "< + -";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.LA_7KOJEDUB.class))
            return "27";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.ME_262A1ANOWOTNY.class))
            return "8 +";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.MIG_3POKRYSHKIN.class))
            return "* 5";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.MXY_7.class))
            return "o";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.P_39NPOKRYSHKIN.class))
            return "* 100";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.P_39Q15RECHKALOV.class))
            return "*";
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.YAK_9TALBERT.class))
            return "* 42";
        else
            return super.typedNameNum(class1, regiment, i, j, k);
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.P_39NPOKRYSHKIN.class) || class1.isAssignableFrom(com.maddox.il2.objects.air.P_39Q15RECHKALOV.class))
        {
            changeMat(hiermesh, "Overlay6", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
        }
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.LA_7KOJEDUB.class))
        {
            changeMat(hiermesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
        }
    }
}
