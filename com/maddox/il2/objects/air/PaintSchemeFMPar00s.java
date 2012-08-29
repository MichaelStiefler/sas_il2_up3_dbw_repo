// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeFMPar00s.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeFMPar00, PaintScheme

public class PaintSchemeFMPar00s extends com.maddox.il2.objects.air.PaintSchemeFMPar00
{

    public PaintSchemeFMPar00s()
    {
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySlovakia)
        {
            float f = 0.85F;
            changeMat(hiermesh, "Overlay1", "psBM00SLVKLNUM" + l + i + k, "Slovakian2/S.tga", "Slovakian2/-.tga", f, f, f, f, f, f);
            if(k > 9)
                changeMat(hiermesh, "Overlay2", "psBM00SLVKRNUM" + l + i + k, "Slovakian2/" + k / 10 + ".tga", "Slovakian2/" + k % 10 + ".tga", f, f, f, f, f, f);
            else
                changeMat(hiermesh, "Overlay2", "psBM00SLVKRNUM" + l + i + k, "Slovakian2/0.tga", "Slovakian2/" + k % 10 + ".tga", f, f, f, f, f, f);
            changeMat(hiermesh, "Overlay7", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
        {
            if(!"ra".equals(regiment.branch()) && !"rz".equals(regiment.branch()) && !"rn".equals(regiment.branch()))
            {
                changeMat(hiermesh, "Overlay6", "britishroundel4c", "British/roundel4c.tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay7", "britishroundel3c", "British/roundel3c.tga", 1.0F, 1.0F, 1.0F);
            }
            changeMat(hiermesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
        } else
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNoName)
        {
            changeMat(hiermesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
        } else
        {
            int i1 = i + 1;
            if(i1 == 4)
                i1 = 0;
            changeMat(hiermesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            if(k >= 20)
                k %= 20;
            if(k < 10)
                changeMat(hiermesh, "Overlay2", "psFM00GERRNUM" + l + i + k, "null.tga", "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            else
                changeMat(hiermesh, "Overlay2", "psFM00GERRNUM" + l + i + k, "German/0" + i1 + k / 10 + ".tga", "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGermany)
                changeMat(hiermesh, "Overlay8", "haken1", "German/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "haken1.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);
            return;
        }
    }
}
