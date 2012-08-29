// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeSPIT2A.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintScheme

public class PaintSchemeSPIT2A extends com.maddox.il2.objects.air.PaintScheme
{

    public PaintSchemeSPIT2A()
    {
    }

    protected int clampToReality(int i)
    {
        if(i <= 1)
            return 76;
        if(i == 2)
            return 70;
        if(i == 3)
            return 85;
        if(i == 4)
            return 81;
        if(i == 5)
            return 77;
        if(i == 6)
            return 65;
        if(i == 7)
            return 80;
        if(i == 8)
            return 66;
        if(i == 9)
            return 82;
        if(i == 10)
            return 78;
        if(i == 11)
            return 73;
        if(i == 12)
            return 86;
        if(i == 13)
            return 87;
        if(i == 14)
            return 71;
        if(i == 15)
            return 68;
        if(i == 16)
            return 84;
        if(i == 17)
            return 69;
        if(i == 18)
            return 74;
        if(i == 19)
            return 72;
        if(i == 20)
            return 89;
        if(i == 21)
            return 88;
        if(i == 22)
            return 67;
        if(i == 23)
            return 79;
        if(i == 24)
            return 83;
        if(i == 25)
            return 75;
        if(i >= 26)
            return 90;
        else
            return i;
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
        {
            if("ra".equals(regiment.branch()) || "rz".equals(regiment.branch()) || "rn".equals(regiment.branch()))
            {
                k = clampToLiteral(k);
                changeMat(hiermesh, "Overlay1", "psSPIT1BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                changeMat(hiermesh, "Overlay4", "psSPIT1BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                changeMat(hiermesh, "Overlay2", "psSPIT1BRINAVYLNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psSPIT1BRINAVYRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                changeMat(class1, hiermesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
            } else
            {
                k = clampToReality(k);
                changeMat(hiermesh, "Overlay1", "psSPIT1BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.7765F, 0.7843F, 0.7059F, 0.7765F, 0.7843F, 0.7059F);
                changeMat(hiermesh, "Overlay3", "psSPIT1BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.7765F, 0.7843F, 0.7059F, 0.7765F, 0.7843F, 0.7059F);
                changeMat(hiermesh, "Overlay2", "psSPIT1BRILNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)k + ".tga", "null.tga", 0.7765F, 0.7843F, 0.7059F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psSPIT1BRIRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)k + ".tga", "null.tga", 0.7765F, 0.7843F, 0.7059F, 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "britishroundel4c", "British/roundel4c.tga", 1.0F, 1.0F, 1.0F);
            }
            return;
        } else
        {
            return;
        }
    }
}
