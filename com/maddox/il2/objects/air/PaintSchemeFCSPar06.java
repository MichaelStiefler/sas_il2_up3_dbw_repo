// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeFCSPar06.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeFMPar05, PaintScheme

public class PaintSchemeFCSPar06 extends com.maddox.il2.objects.air.PaintSchemeFMPar05
{

    public PaintSchemeFCSPar06()
    {
    }

    public java.lang.String typedNameNum(java.lang.Class class1, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
            return "" + (k >= 10 ? "" + k : "0" + k);
        else
            return super.typedNameNum(class1, regiment, i, j, k);
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
        {
            changeMat(hiermesh, "Overlay1", "psFCS06RUSCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/3" + k / 10 + ".tga", "Russian/3" + k % 10 + ".tga", 0.72549F, 0.17647F, 0.10196F, 0.72549F, 0.17647F, 0.10196F);
            changeMat(hiermesh, "Overlay4", "psFCS06RUSCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/3" + k / 10 + ".tga", "Russian/3" + k % 10 + ".tga", 0.72549F, 0.17647F, 0.10196F, 0.72549F, 0.17647F, 0.10196F);
            changeMat(hiermesh, "Overlay7", "redstar4", "Russian/redstar4.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "redstar4", "Russian/redstar4.tga", 1.0F, 1.0F, 1.0F);
            return;
        } else
        {
            return;
        }
    }
}
