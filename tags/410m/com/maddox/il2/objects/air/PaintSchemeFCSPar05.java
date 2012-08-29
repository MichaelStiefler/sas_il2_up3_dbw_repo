// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PaintSchemeFCSPar05.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintSchemeFMPar05

public class PaintSchemeFCSPar05 extends com.maddox.il2.objects.air.PaintSchemeFMPar05
{

    public PaintSchemeFCSPar05()
    {
    }

    public java.lang.String typedNameNum(java.lang.Class class1, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == countryFrance)
            if(regiment.fileName().equals("PaintSchemes/Red/NN"))
                return "" + (k >= 10 ? "" + k : "0" + k) + " *";
            else
                return super.typedNameNum(class1, regiment, i, j, k);
        if(regiment.country() == countryRussia)
            return "" + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == countryUSA)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        } else
        {
            return super.typedNameNum(class1, regiment, i, j, k);
        }
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == countryFrance)
        {
            if(regiment.fileName().equals("PaintSchemes/Red/NN"))
            {
                changeMat(hiermesh, "Overlay1", "psFCS05FRACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psFCS05FRACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay6", "null", "null.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay8", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
            } else
            {
                if(class1.isAssignableFrom(com.maddox.il2.objects.air.YAK_1B.class) || class1.isAssignableFrom(com.maddox.il2.objects.air.YAK_3.class) || class1.isAssignableFrom(com.maddox.il2.objects.air.YAK_9T.class))
                    changeMat(hiermesh, "Overlay1", "psFCS05FRACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                return;
            }
            return;
        }
        if(regiment.country() == countryJapan)
        {
            changeMat(hiermesh, "Overlay1", "psFCS05JAPCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psFCS05JAPCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "JAR2", "Japanese/JAR2.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == countryRussia)
        {
            changeMat(hiermesh, "Overlay1", "psFCS05RUSCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/2" + k / 10 + ".tga", "Russian/2" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psFCS05RUSCNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "Russian/2" + k / 10 + ".tga", "Russian/2" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == countryUSA)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "psFCS05USAREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.21F * psBritishSkyColor[0], 0.21F * psBritishSkyColor[1], 0.21F * psBritishSkyColor[2], 0.21F * psBritishSkyColor[0], 0.21F * psBritishSkyColor[1], 0.21F * psBritishSkyColor[2]);
            changeMat(hiermesh, "Overlay4", "psFCS05USAREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.21F * psBritishSkyColor[0], 0.21F * psBritishSkyColor[1], 0.21F * psBritishSkyColor[2], 0.21F * psBritishSkyColor[0], 0.21F * psBritishSkyColor[1], 0.21F * psBritishSkyColor[2]);
            changeMat(hiermesh, "Overlay2", "psFCS05USALNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", 0.21F * psBritishSkyColor[0], 0.21F * psBritishSkyColor[1], 0.21F * psBritishSkyColor[2], 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "psFCS05USARNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, 0.21F * psBritishSkyColor[0], 0.21F * psBritishSkyColor[1], 0.21F * psBritishSkyColor[2]);
        }
    }
}
