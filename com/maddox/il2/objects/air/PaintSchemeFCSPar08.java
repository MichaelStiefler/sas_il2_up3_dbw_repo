// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeFCSPar08.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeFCSPar01, PaintScheme

public class PaintSchemeFCSPar08 extends com.maddox.il2.objects.air.PaintSchemeFCSPar01
{

    public PaintSchemeFCSPar08()
    {
    }

    public void prepareNumOff(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
            changeMat(hiermesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
        changeMat(hiermesh, "Overlay4", "null", "null.tga", 1.0F, 1.0F, 1.0F);
        changeMat(hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
        changeMat(hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
        {
            changeMat(hiermesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            int j1 = com.maddox.il2.ai.World.cur().camouflage;
            float f;
            if(j1 == 1)
                f = 0.1F;
            else
                f = 0.9F;
            if(k < 10)
                changeMat(hiermesh, "Overlay8", "psFCS01RUSCNUM" + l + i + "0" + k + j1, "Russian/0" + k + ".tga", f, f, f);
            else
                changeMat(hiermesh, "Overlay8", "psFCS01RUSCNUM" + l + i + k + j1, "Russian/0" + k / 10 + ".tga", "Russian/0" + k % 10 + ".tga", f, f, f, f, f, f);
        } else
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinland)
        {
            super.prepareNum(class1, hiermesh, regiment, i, j, k);
            int i1 = k;
            if(i1 > 5)
                i1 = 1;
            java.lang.String s = "VH-" + i1;
            changeMat(hiermesh, "Overlay2", "psFM01FINACID" + s, "Finnish/" + s + ".tga", 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psFM01FINACID" + s, "Finnish/" + s + ".tga", 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay8", "null", "null.tga", 1.0F, 1.0F, 1.0F);
        } else
        {
            super.prepareNum(class1, hiermesh, regiment, i, j, k);
        }
    }
}
