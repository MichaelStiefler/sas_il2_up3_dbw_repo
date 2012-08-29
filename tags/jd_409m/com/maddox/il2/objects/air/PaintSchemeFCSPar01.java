package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeFCSPar01 extends PaintSchemeFMPar01
{
  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == PaintScheme.countryItaly) {
      if (paramInt3 < 10) {
        changeMat(paramHierMesh, "Overlay2", "psFCS01ITALNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFCS01ITARNUM" + i + paramInt1 + paramInt3, "null.tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFCS01ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFCS01ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(paramHierMesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay7", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay8", "italian1", "Italian/roundel1.tga", 1.0F, 1.0F, 1.0F);
    }

    if (paramRegiment.country() == PaintScheme.countryPoland) {
      if (paramInt3 < 10) {
        changeMat(paramHierMesh, "Overlay1", "psFCS01POLCNUM" + i + paramInt1 + "0" + paramInt3, "Russian/1" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay1", "psFCS01POLCNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(paramHierMesh, "Overlay6", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == PaintScheme.countryRussia) {
      if (paramInt3 < 10) {
        changeMat(paramHierMesh, "Overlay1", "psFCS01RUSCNUM" + i + paramInt1 + "0" + paramInt3, "Russian/0" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay1", "psFCS01RUSCNUM" + i + paramInt1 + paramInt3, "Russian/0" + paramInt3 / 10 + ".tga", "Russian/0" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(paramHierMesh, "Overlay6", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay8", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay2", "psAVGRUSMARKcolor" + paramInt1, "mark.tga", PaintScheme.psRussianBomberColor[paramInt1][0], PaintScheme.psRussianBomberColor[paramInt1][1], PaintScheme.psRussianBomberColor[paramInt1][2]);

      return;
    }

    if (paramRegiment.country() == PaintScheme.countryUSA) {
      changeMat(paramHierMesh, "Overlay1", "psFCS01USACNUM" + paramInt3 % 10, "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);

      return;
    }
  }

  public void prepareNumOff(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramRegiment.country() == PaintScheme.countryRussia)
      changeMat(paramHierMesh, "Overlay2", "psAVGRUSMARKcolor" + paramInt1, "mark.tga", PaintScheme.psRussianBomberColor[paramInt1][0], PaintScheme.psRussianBomberColor[paramInt1][1], PaintScheme.psRussianBomberColor[paramInt1][2]);
  }
}