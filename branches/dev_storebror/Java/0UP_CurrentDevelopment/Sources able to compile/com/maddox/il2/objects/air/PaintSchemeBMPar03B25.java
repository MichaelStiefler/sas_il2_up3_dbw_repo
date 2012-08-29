package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeBMPar03B25 extends PaintSchemeBMPar03
{

    public PaintSchemeBMPar03B25()
    {
    }

    public java.lang.String typedNameNum(java.lang.Class class1, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        if(regiment.country() == PaintScheme.countryUSA)
            return "" + (k < 10 ? "0" + k : "" + k) + "*";
        if(regiment.country() == PaintScheme.countryUSABlue)
            return "" + (k < 10 ? "0" + k : "" + k) + "*";
        else
            return super.typedNameNum(class1, regiment, i, j, k);
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == PaintScheme.countryUSA)
        {
            changeMat(hiermesh, "Overlay1", "psBM0DUSACNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM0DUSACNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay4", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryUSABlue)
        {
            changeMat(hiermesh, "Overlay1", "psBM0DUSACNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM0DUSACNUM" + l + i + (k >= 10 ? "" + k : "0" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay4", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            return;
        } else
        {
            return;
        }
    }
}
