// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeBMPar00s.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeBMPar00, PaintScheme

public class PaintSchemeBMPar00s extends com.maddox.il2.objects.air.PaintSchemeBMPar00
{

    public PaintSchemeBMPar00s()
    {
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySlovakia)
        {
            int i1 = com.maddox.il2.ai.World.cur().camouflage;
            float f = 0.85F;
            changeMat(hiermesh, "Overlay1", "psBM00SLVKLNUM" + l + i + k + i1, "Slovakian2/S.tga", "Slovakian2/-.tga", f, f, f, f, f, f);
            if(k > 9)
                changeMat(hiermesh, "Overlay2", "psBM00SLVKRNUM" + l + i + k + i1, "Slovakian2/" + k / 10 + ".tga", "Slovakian2/" + k % 10 + ".tga", f, f, f, f, f, f);
            else
                changeMat(hiermesh, "Overlay2", "psBM00SLVKRNUM" + l + i + k + i1, "Slovakian2/0.tga", "Slovakian2/" + k % 10 + ".tga", f, f, f, f, f, f);
            changeMat(hiermesh, "Overlay7", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGermany)
        {
            changeMat(hiermesh, "Overlay6", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay7", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "haken1", "German/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "haken1.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);
            return;
        } else
        {
            return;
        }
    }
}
