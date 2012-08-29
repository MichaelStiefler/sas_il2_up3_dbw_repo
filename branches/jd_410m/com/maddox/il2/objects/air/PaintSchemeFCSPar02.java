package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeFCSPar02 extends PaintSchemeFMPar02
{
  public String typedNameNum(Class paramClass, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == countryFrance) {
      return "" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()) + " *";
    }

    return super.typedNameNum(paramClass, paramRegiment, paramInt1, paramInt2, paramInt3);
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == countryFinland) {
      char c = (char)(48 + paramInt3 % 10);
      changeMat(paramClass, paramHierMesh, "Overlay6", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay7", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);

      if (paramInt1 == 3) {
        changeMat(paramClass, paramHierMesh, "Overlay1", "psFCS02FINCNUM" + i + paramInt1 + "_" + (char)(65 + (paramInt3 % 10 - 1)), "Finnish/" + (char)(65 + (paramInt3 % 10 - 1)) + ".tga", psFinnishFighterColor[paramInt1][0], psFinnishFighterColor[paramInt1][1], psFinnishFighterColor[paramInt1][2]);

        changeMat(paramClass, paramHierMesh, "Overlay4", "psFCS02FINCNUM" + i + paramInt1 + "_" + (char)(65 + (paramInt3 % 10 - 1)), "Finnish/" + (char)(65 + (paramInt3 % 10 - 1)) + ".tga", psFinnishFighterColor[paramInt1][0], psFinnishFighterColor[paramInt1][1], psFinnishFighterColor[paramInt1][2]);
      }
      else if (paramInt3 < 10) {
        changeMat(paramHierMesh, "Overlay1", "psFCS02FINLNUM" + i + paramInt1 + "0" + paramInt3, "null.tga", psFinnishFighterPrefix[0][paramInt1] + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay4", "psFCS02FINRNUM" + i + paramInt1 + "0" + paramInt3, psFinnishFighterPrefix[0][paramInt1] + paramInt3 + ".tga", "null.tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else
      {
        changeMat(paramHierMesh, "Overlay1", "psFCS02FINCNUM" + i + paramInt1 + paramInt3, psFinnishFighterPrefix[0][paramInt1] + paramInt3 / 10 + ".tga", psFinnishFighterPrefix[0][paramInt1] + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay4", "psFCS02FINCNUM" + i + paramInt1 + paramInt3, psFinnishFighterPrefix[0][paramInt1] + paramInt3 / 10 + ".tga", psFinnishFighterPrefix[0][paramInt1] + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      String str = getFAFACCode(paramClass, paramInt1);
      changeMat(paramHierMesh, "Overlay2", "psFM05FINACID" + str + c, "Finnish/" + str + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

      changeMat(paramHierMesh, "Overlay3", "psFM05FINACID" + str + c, "Finnish/" + str + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

      return;
    }

    if (paramRegiment.country() == countryFrance) {
      changeMat(paramHierMesh, "Overlay1", "psFCS02FRACNUM" + i + paramInt1 + paramInt3, "Russian/0" + paramInt3 / 10 + ".tga", "Russian/0" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay6", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay8", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay2", "psAVGRUSMARKcolor" + paramInt1, "mark.tga", psRussianBomberColor[paramInt1][0], psRussianBomberColor[paramInt1][1], psRussianBomberColor[paramInt1][2]);

      return;
    }

    if (paramRegiment.country() == countryItaly) {
      if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay2", "psFCS02ITALNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 % 10 + ".tga", psGermanBomberColor[1][0], psGermanBomberColor[1][1], psGermanBomberColor[1][2]);

        changeMat(paramHierMesh, "Overlay3", "psFCS02ITARNUM" + i + paramInt1 + paramInt3, "null.tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, psGermanBomberColor[1][0], psGermanBomberColor[1][1], psGermanBomberColor[1][2]);
      }
      else
      {
        changeMat(paramHierMesh, "Overlay2", "psFCS02ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", psGermanBomberColor[1][0], psGermanBomberColor[1][1], psGermanBomberColor[1][2], psGermanBomberColor[1][0], psGermanBomberColor[1][1], psGermanBomberColor[1][2]);

        changeMat(paramHierMesh, "Overlay3", "psFCS02ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", psGermanBomberColor[1][0], psGermanBomberColor[1][1], psGermanBomberColor[1][2], psGermanBomberColor[1][0], psGermanBomberColor[1][1], psGermanBomberColor[1][2]);
      }

      changeMat(paramClass, paramHierMesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
      changeMat(paramClass, paramHierMesh, "Overlay8", "italian1", "Italian/roundel1.tga", 1.0F, 1.0F, 1.0F);
    }

    if (paramRegiment.country() == countryJapan) {
      changeMat(paramHierMesh, "Overlay1", "psFCS02JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", psRussianBomberColor[1][0], psRussianBomberColor[1][1], psRussianBomberColor[1][2], psRussianBomberColor[1][0], psRussianBomberColor[1][1], psRussianBomberColor[1][2]);

      changeMat(paramHierMesh, "Overlay4", "psFCS02JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", psRussianBomberColor[1][0], psRussianBomberColor[1][1], psRussianBomberColor[1][2], psRussianBomberColor[1][0], psRussianBomberColor[1][1], psRussianBomberColor[1][2]);

      changeMat(paramClass, paramHierMesh, "Overlay6", "JAR2", "Japanese/JAR2.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countryRussia) {
      changeMat(paramHierMesh, "Overlay1", "psFCS02RUSCNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay6", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay8", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay2", "psAVGRUSMARKcolor" + paramInt1, "mark.tga", psRussianBomberColor[paramInt1][0], psRussianBomberColor[paramInt1][1], psRussianBomberColor[paramInt1][2]);

      return;
    }

    if (paramRegiment.country() == countryUSA) {
      changeMat(paramHierMesh, "Overlay2", "psFCS02USABLNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay3", "psFCS02USAYELNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 0.960784F, 0.745098F, 0.145098F, 0.960784F, 0.745098F, 0.145098F);

      changeMat(paramHierMesh, "Overlay4", "psFCS02USARUSNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      return;
    }
  }
}