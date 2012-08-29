package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeBCSPar02 extends PaintSchemeBMPar02
{
  public String typedNameNum(Class paramClass, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramRegiment.country() == countryBritain) {
      return "" + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    }

    return super.typedNameNum(paramClass, paramRegiment, paramInt1, paramInt2, paramInt3);
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == countryBritain)
    {
      changeMat(paramHierMesh, "Overlay1", "psBM00USACNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay4", "psBM00USACNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countryPoland) {
      changeMat(paramHierMesh, "Overlay1", "psBCS02POLCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay2", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countryRussia) {
      if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay1", "psBCS02RUSCNUM" + i + paramInt1 + "0" + paramInt3, "Russian/0" + paramInt3 + ".tga", psRussianBomberColor[paramInt1][0], psRussianBomberColor[paramInt1][1], psRussianBomberColor[paramInt1][2]);
      }
      else
      {
        changeMat(paramHierMesh, "Overlay1", "psBCS02RUSCNUM" + i + paramInt1 + paramInt3, "Russian/0" + paramInt3 / 10 + ".tga", "Russian/0" + paramInt3 % 10 + ".tga", psRussianBomberColor[paramInt1][0], psRussianBomberColor[paramInt1][1], psRussianBomberColor[paramInt1][2], psRussianBomberColor[paramInt1][0], psRussianBomberColor[paramInt1][1], psRussianBomberColor[paramInt1][2]);
      }

      changeMat(paramClass, paramHierMesh, "Overlay2", "psAVGRUSMARKcolor" + paramInt1, "mark.tga", psRussianBomberColor[paramInt1][0], psRussianBomberColor[paramInt1][1], psRussianBomberColor[paramInt1][2]);

      changeMat(paramClass, paramHierMesh, "Overlay6", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay8", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
  }

  public void prepareNumOff(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramRegiment.country() == countryRussia)
      changeMat(paramClass, paramHierMesh, "Overlay2", "psAVGRUSMARKcolor" + paramInt1, "mark.tga", psRussianBomberColor[paramInt1][0], psRussianBomberColor[paramInt1][1], psRussianBomberColor[paramInt1][2]);
  }
}