// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeFCSPar07.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeFCSPar01, PaintScheme

public class PaintSchemeFCSPar07 extends com.maddox.il2.objects.air.PaintSchemeFCSPar01
{

    public PaintSchemeFCSPar07()
    {
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
        {
            int i1 = com.maddox.il2.ai.World.cur().camouflage;
            float f;
            if(i1 == 1)
                f = 0.1F;
            else
                f = 0.9F;
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay1", "psFCS01RUSCNUM" + l + i + "0" + k + i1, "Russian/0" + k + ".tga", f, f, f);
            else
                changeMat(hiermesh, "Overlay1", "psFCS01RUSCNUM" + l + i + k + i1, "Russian/0" + k / 10 + ".tga", "Russian/0" + k % 10 + ".tga", f, f, f, f, f, f);
            changeMat(class1, hiermesh, "Overlay6", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "psAVGRUSMARKcolor" + i, "mark.tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[i][2]);
        } else
        if(regiment.country() != com.maddox.il2.objects.air.PaintScheme.countryNoName)
        {
            super.prepareNum(class1, hiermesh, regiment, i, j, k);
            int j1 = com.maddox.il2.ai.World.cur().camouflage;
            float f1;
            if(j1 == 1)
                f1 = 0.1F;
            else
                f1 = 0.9F;
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay8", "psFCS01RUSCNUM" + l + i + "0" + k + j1, "Russian/0" + k + ".tga", f1, f1, f1);
            else
                changeMat(hiermesh, "Overlay8", "psFCS01RUSCNUM" + l + i + k + j1, "Russian/0" + k / 10 + ".tga", "Russian/0" + k % 10 + ".tga", f1, f1, f1, f1, f1, f1);
        }
    }
}
