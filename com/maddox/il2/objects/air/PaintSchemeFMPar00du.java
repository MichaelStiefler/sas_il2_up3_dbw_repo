// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeFMPar00du.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeFMPar00, PaintScheme

public class PaintSchemeFMPar00du extends com.maddox.il2.objects.air.PaintSchemeFMPar00
{

    public PaintSchemeFMPar00du()
    {
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNetherlands)
        {
            float f = 1.0F;
            changeMat(hiermesh, "Overlay6", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            int i1 = 344 + (k - 1) % 20;
            if(i1 >= 344 && i1 <= 363)
                changeMat(hiermesh, "Overlay1", "psBM00DUTCNUM" + l + i + k, "Dutch/CW-" + i1 + ".tga", "null.tga", f, f, f, f, f, f);
            return;
        } else
        {
            return;
        }
    }
}
