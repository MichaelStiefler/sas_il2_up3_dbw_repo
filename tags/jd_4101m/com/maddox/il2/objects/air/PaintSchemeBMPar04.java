package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeBMPar04 extends PaintScheme
{
  public String typedNameNum(Class paramClass, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == countryGermany) {
      if (paramInt3 < 1) paramInt3 = 1;
      return "" + paramRegiment.id() + " + " + (char)(65 + (paramInt3 % 26 - 1)) + psGermanBomberLetter[i][paramInt1];
    }

    if (paramRegiment.country() == countryNetherlands) {
      return "" + paramInt3;
    }

    if (paramRegiment.country() == countryFinland) {
      return psFinnishFighterString[1][paramInt1] + paramInt3;
    }

    if (paramRegiment.country() == countryFrance) {
      return "o " + paramInt3;
    }

    if (paramRegiment.country() == countryBritain) {
      paramInt3 = clampToLiteral(paramInt3);
      return "" + paramRegiment.id() + " - " + (char)(65 + (paramInt3 - 1));
    }

    if (paramRegiment.country() == countryBritain) {
      return "" + paramRegiment.id() + " + " + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    }

    if (paramRegiment.country() == countryItaly) {
      return "" + paramInt3;
    }

    if (paramRegiment.country() == countryJapan) {
      return "" + paramInt3;
    }

    if (paramRegiment.country() == countryPoland) {
      return "" + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    }

    if (paramRegiment.country() == countryRomania) {
      return "+ " + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    }

    if (paramRegiment.country() == countryRussia) {
      return "* " + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString());
    }

    if (paramRegiment.country() == countryNewZealand) {
      paramInt3 = clampToLiteral(paramInt3);
      return "" + paramRegiment.id() + " - " + (char)(65 + (paramInt3 - 1));
    }

    if (paramRegiment.country() == countrySlovakia) {
      return "" + paramInt3 + " +";
    }

    if (paramRegiment.country() == countryUSA) {
      paramInt3 = clampToLiteral(paramInt3);
      return "" + paramRegiment.id() + " " + (char)(65 + (paramInt3 - 1));
    }

    return super.typedNameNum(paramClass, paramRegiment, paramInt1, paramInt2, paramInt3);
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == countryGermany) {
      paramInt3 = clampToLiteral(paramInt3);
      changeMat(paramHierMesh, "Overlay1", "psBM04GERREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay3", "psBM04GERREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay2", "psBM04GERCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + (char)(65 + (paramInt3 - 1)) + ".tga", "German/" + psGermanBomberLetter[i][paramInt1] + ".tga", psGermanBomberColor[paramInt1][0], psGermanBomberColor[paramInt1][1], psGermanBomberColor[paramInt1][2], 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay4", "psBM04GERCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + (char)(65 + (paramInt3 - 1)) + ".tga", "German/" + psGermanBomberLetter[i][paramInt1] + ".tga", psGermanBomberColor[paramInt1][0], psGermanBomberColor[paramInt1][1], psGermanBomberColor[paramInt1][2], 0.1F, 0.1F, 0.1F);

      changeMat(paramClass, paramHierMesh, "Overlay6", "balken2", "German/balken2.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "balken3", "German/balken3.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay8", "haken2", "German/" + (World.cur().isHakenAllowed() ? "haken2.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);

      return;
    }

    if (paramRegiment.country() == countryNetherlands) {
      changeMat(paramClass, paramHierMesh, "Overlay6", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay1", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay4", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      return;
    }

    if (paramRegiment.country() == countryFinland) {
      char c = (char)(48 + paramInt3 % 10);
      changeMat(paramClass, paramHierMesh, "Overlay6", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay7", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);

      if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay8", "psBM04FINANUM" + i + paramInt1 + "0" + paramInt3, "Finnish/0" + paramInt3 + ".tga", psFinnishFighterColor[paramInt1][0], psFinnishFighterColor[paramInt1][1], psFinnishFighterColor[paramInt1][2]);
      }
      else
      {
        changeMat(paramHierMesh, "Overlay8", "psBM04FINCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", psFinnishFighterColor[paramInt1][0], psFinnishFighterColor[paramInt1][1], psFinnishFighterColor[paramInt1][2], psFinnishFighterColor[paramInt1][0], psFinnishFighterColor[paramInt1][1], psFinnishFighterColor[paramInt1][2]);
      }

      String str = getFAFACCode(paramClass, paramInt1);
      changeMat(paramHierMesh, "Overlay2", "psBM04FINACOD" + str + c, "Finnish/" + str + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

      changeMat(paramHierMesh, "Overlay3", "psBM04FINACOD" + str + c, "Finnish/" + str + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

      return;
    }

    if (paramRegiment.country() == countryFrance) {
      if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay2", "psFB04FRALNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFB04FRARNUM" + i + paramInt1 + paramInt3, "null.tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFB04FRACNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFB04FRACNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(paramClass, paramHierMesh, "Overlay6", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
    }

    if (paramRegiment.country() == countryBritain) {
      if (("ra".equals(paramRegiment.branch())) || ("rz".equals(paramRegiment.branch())) || ("rn".equals(paramRegiment.branch()))) {
        paramInt3 = clampToLiteral(paramInt3);
        changeMat(paramHierMesh, "Overlay1", "psBM04BRINAVYREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);

        changeMat(paramHierMesh, "Overlay4", "psBM04BRINAVYREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);

        changeMat(paramHierMesh, "Overlay2", "psBM04BRINAVYLNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psBM04BRINAVYRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);

        changeMat(paramClass, paramHierMesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramClass, paramHierMesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
      } else {
        paramInt3 = clampToLiteral(paramInt3);
        changeMat(paramHierMesh, "Overlay1", "psBM04BRIREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2], psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2]);

        changeMat(paramHierMesh, "Overlay4", "psBM04BRIREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2], psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2]);

        changeMat(paramHierMesh, "Overlay2", "psBM04BRILNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2], 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psBM04BRIRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2]);

        changeMat(paramClass, paramHierMesh, "Overlay7", "britishroundel4cthin", "British/roundel4cthin.tga", 1.0F, 1.0F, 1.0F);
      }
      return;
    }

    if (paramRegiment.country() == countryHungary) {
      changeMat(paramHierMesh, "Overlay1", "psBM04HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay3", "psBM04HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay2", "psBM04HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay4", "psBM04HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramClass, paramHierMesh, "Overlay6", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countryItaly) {
      if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay2", "psFB04ITALNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFB04ITARNUM" + i + paramInt1 + paramInt3, "null.tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFB04ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFB04ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(paramClass, paramHierMesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
    }

    if (paramRegiment.country() == countryJapan) {
      changeMat(paramHierMesh, "Overlay2", "psBM04JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2], psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2]);

      changeMat(paramHierMesh, "Overlay3", "psBM04JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2], psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2]);

      changeMat(paramClass, paramHierMesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
    }

    if (paramRegiment.country() == countryPoland) {
      changeMat(paramHierMesh, "Overlay1", "psBM04POLCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay4", "psBM04POLCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countryRomania) {
      changeMat(paramHierMesh, "Overlay8", "psFB04ROMCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countryRussia) {
      changeMat(paramHierMesh, "Overlay2", "psBM04RUSCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay3", "psBM04RUSCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      if ((paramClass.isAssignableFrom(P_39N.class)) || (paramClass.isAssignableFrom(P_39Q1.class)) || (paramClass.isAssignableFrom(P_39Q10.class)))
      {
        changeMat(paramClass, paramHierMesh, "Overlay6", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
      }
      changeMat(paramClass, paramHierMesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay8", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countryNewZealand) {
      paramInt3 = clampToLiteral(paramInt3);
      changeMat(paramHierMesh, "Overlay1", "psBM00RZREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);

      changeMat(paramHierMesh, "Overlay4", "psBM00RZREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);

      changeMat(paramHierMesh, "Overlay2", "psBM00RZLNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay3", "psBM00RZRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);

      changeMat(paramClass, paramHierMesh, "Overlay6", "newzealand6", "NewZealand/newzealand6.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "newzealand7", "NewZealand/newzealand7.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countrySlovakia) {
      if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay4", "psBM04SLVKLNUM" + i + paramInt1 + "0" + paramInt3, "Finnish/" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay1", "psBM04SLVKRNUM" + i + paramInt1 + "0" + paramInt3, "null.tga", "Finnish/" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay1", "psBM04SLVKCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay4", "psBM04SLVKCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(paramClass, paramHierMesh, "Overlay6", "slovakiancross1", "Slovakian/cross1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countryUSA) {
      paramInt3 = clampToLiteral(paramInt3);
      changeMat(paramHierMesh, "Overlay1", "psBM04USAREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", 0.960784F, 0.745098F, 0.145098F, 0.960784F, 0.745098F, 0.145098F);

      changeMat(paramHierMesh, "Overlay4", "psBM04USAREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", 0.960784F, 0.745098F, 0.145098F, 0.960784F, 0.745098F, 0.145098F);

      changeMat(paramHierMesh, "Overlay2", "psBM04USALNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", 0.960784F, 0.745098F, 0.145098F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay3", "psBM04USARNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, 0.960784F, 0.745098F, 0.145098F);

      changeMat(paramClass, paramHierMesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
  }
}