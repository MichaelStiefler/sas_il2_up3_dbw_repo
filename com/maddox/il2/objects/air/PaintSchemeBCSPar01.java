package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeBCSPar01 extends PaintSchemeBMPar01
{
  public String typedNameNum(Class paramClass, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramRegiment.country() == countryBritain) {
      return "" + paramInt3 % 10 + paramRegiment.aid()[1];
    }

    return super.typedNameNum(paramClass, paramRegiment, paramInt1, paramInt2, paramInt3);
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == countryBritain) {
      changeMat(paramHierMesh, "Overlay1", "psBCS01BRINUM" + paramInt3 % 10 + paramRegiment.aid()[1], "British/" + paramInt3 % 10 + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2], psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2]);
    }

    if (paramRegiment.country() == countryJapan) {
      changeMat(paramHierMesh, "Overlay1", "psBCS01JAPREDNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.690196F, 0.1568628F, 0.1098039F, 0.690196F, 0.1568628F, 0.1098039F);

      changeMat(paramHierMesh, "Overlay2", "psBCS01JAPWHTNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.95F, 0.95F, 0.95F, 0.95F, 0.95F, 0.95F);
    }

    if (paramRegiment.country() == countryRussia) {
      changeMat(paramHierMesh, "Overlay1", "psBCS01RUSCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", psRussianBomberColor[paramInt1][0], psRussianBomberColor[paramInt1][1], psRussianBomberColor[paramInt1][2], psRussianBomberColor[paramInt1][0], psRussianBomberColor[paramInt1][1], psRussianBomberColor[paramInt1][2]);

      changeMat(paramClass, paramHierMesh, "Overlay2", "psAVGRUSMARKcolor" + paramInt1, "mark.tga", psRussianBomberColor[paramInt1][0], psRussianBomberColor[paramInt1][1], psRussianBomberColor[paramInt1][2]);

      changeMat(paramClass, paramHierMesh, "Overlay6", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay8", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
  }

  public void prepareNumOff(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramRegiment.country() == countryRussia)
      changeMat(paramClass, paramHierMesh, "Overlay2", "psAVGRUSMARKcolor" + paramInt1, "mark.tga", psRussianBomberColor[paramInt1][0], psRussianBomberColor[paramInt1][1], psRussianBomberColor[paramInt1][2]);
  }
}