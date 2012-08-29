// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeFMPar00DXXI.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeFMPar01, PaintScheme

public class PaintSchemeFMPar00DXXI extends com.maddox.il2.objects.air.PaintSchemeFMPar01
{

    public PaintSchemeFMPar00DXXI()
    {
    }

    protected java.lang.String getFAFACCode(java.lang.Class class1, int i)
    {
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.DXXI_SARJA3_EARLY.class))
            return "FR-" + (i <= 1 ? "8" : "9");
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.DXXI_SARJA3_LATE.class))
            return "FR-" + (i <= 1 ? "9" : "10");
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.DXXI_SARJA4.class))
            return "FR-" + (i <= 1 ? "13" : "14");
        else
            return super.getFAFACCode(class1, i);
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.DXXI_DU.class))
            if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNetherlands)
            {
                float f = 1.0F;
                int i1 = 240;
                if(k > 20)
                    i1 = 220 + k / 20;
                else
                    i1 = 220 + k;
                if(i1 >= 221 && i1 <= 240)
                    changeMat(hiermesh, "Overlay1", "psBM00DUTCNUM" + l + i + k, "Dutch/" + i1 + ".tga", "null.tga", f, f, f, f, f, f);
                return;
            } else
            {
                changeMat(class1, hiermesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                return;
            }
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.DXXI_DK.class))
        {
            float f1 = 0.0F;
            int j1 = 52;
            if(k > 10)
                j1 = 42 + k % 10;
            else
                j1 = 42 + k;
            if(j1 >= 43 && j1 <= 52)
                changeMat(hiermesh, "Overlay1", "psBM00DUTCNUM" + l + i + k, "Danish/J-" + j1 + ".tga", "null.tga", f1, f1, f1, f1, f1, f1);
            else
                changeMat(class1, hiermesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
        } else
        if(class1.isAssignableFrom(com.maddox.il2.objects.air.DXXI_SARJA3_SARVANTO.class))
        {
            changeMat(class1, hiermesh, "Overlay8", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay2", "psFM01FINACID97", "Finnish/FR-9.tga", "Finnish/sn7.tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psFM01FINACID97", "Finnish/FR-9.tga", "Finnish/sn7.tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        } else
        if(regiment.country() != com.maddox.il2.objects.air.PaintScheme.countryFinland && regiment.country() != com.maddox.il2.objects.air.PaintScheme.countryNoName)
            if(i == 3)
                changeMat(class1, hiermesh, "Overlay8", "psFM01FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", 1.0F, 1.0F, 1.0F);
            else
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay8", "psFM01FINCNUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", 1.0F, 1.0F, 1.0F);
            else
                changeMat(hiermesh, "Overlay8", "psFM01FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
