package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeBMPar04 extends PaintScheme
{
  public String typedNameNum(Class paramClass, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == PaintScheme.countryGermany) {
      if (paramInt3 < 1) paramInt3 = 1;
      return "" + paramRegiment.id() + " + " + (char)(65 + (paramInt3 % 26 - 1)) + PaintScheme.psGermanBomberLetter[i][paramInt1];
    }

    if (paramRegiment.country() == PaintScheme.countryNetherlands) {
      return "" + paramInt3;
    }

    if (paramRegiment.country() == PaintScheme.countryFinland) {
      return PaintScheme.psFinnishFighterString[1][paramInt1] + paramInt3;
    }

    if (paramRegiment.country() == PaintScheme.countryFrance) {
      return "o " + paramInt3;
    }

    if (paramRegiment.country() == PaintScheme.countryBritain) {
      paramInt3 = clampToLiteral(paramInt3);
      return "" + paramRegiment.id() + " - " + (char)(65 + (paramInt3 - 1));
    }

    if (paramRegiment.country() == PaintScheme.countryBritain) {
      return "" + paramRegiment.id() + " + " + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    }

    if (paramRegiment.country() == PaintScheme.countryItaly) {
      return "" + paramInt3;
    }

    if (paramRegiment.country() == PaintScheme.countryJapan) {
      return "" + paramInt3;
    }

    if (paramRegiment.country() == PaintScheme.countryPoland) {
      return "" + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    }

    if (paramRegiment.country() == PaintScheme.countryRomania) {
      return "+ " + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    }

    if (paramRegiment.country() == PaintScheme.countryRussia) {
      return "* " + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString());
    }

    if (paramRegiment.country() == PaintScheme.countryNewZealand) {
      paramInt3 = clampToLiteral(paramInt3);
      return "" + paramRegiment.id() + " - " + (char)(65 + (paramInt3 - 1));
    }

    if (paramRegiment.country() == PaintScheme.countrySlovakia) {
      return "" + paramInt3 + " +";
    }

    if (paramRegiment.country() == PaintScheme.countryUSA) {
      paramInt3 = clampToLiteral(paramInt3);
      return "" + paramRegiment.id() + " " + (char)(65 + (paramInt3 - 1));
    }

    return super.typedNameNum(paramClass, paramRegiment, paramInt1, paramInt2, paramInt3);
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == PaintScheme.countryGermany) {
      paramInt3 = clampToLiteral(paramInt3);
      changeMat(paramHierMesh, "Overlay1", "psBM04GERREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay3", "psBM04GERREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay2", "psBM04GERCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + (char)(65 + (paramInt3 - 1)) + ".tga", "German/" + PaintScheme.psGermanBomberLetter[i][paramInt1] + ".tga", PaintScheme.psGermanBomberColor[paramInt1][0], PaintScheme.psGermanBomberColor[paramInt1][1], PaintScheme.psGermanBomberColor[paramInt1][2], 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay4", "psBM04GERCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + (char)(65 + (paramInt3 - 1)) + ".tga", "German/" + PaintScheme.psGermanBomberLetter[i][paramInt1] + ".tga", PaintScheme.psGermanBomberColor[paramInt1][0], PaintScheme.psGermanBomberColor[paramInt1][1], PaintScheme.psGermanBomberColor[paramInt1][2], 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay6", "balken2", "German/balken2.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "balken3", "German/balken3.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay8", "haken2", "German/" + (World.cur().isHakenAllowed() ? "haken2.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);

      return;
    }

    if (paramRegiment.country() == PaintScheme.countryNetherlands) {
      changeMat(paramHierMesh, "Overlay6", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay1", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay4", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      return;
    }

    if (paramRegiment.country() == PaintScheme.countryFinland) {
      char c = (char)(48 + paramInt3 % 10);
      changeMat(paramHierMesh, "Overlay6", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay7", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);

      if (paramInt3 < 10) {
        changeMat(paramHierMesh, "Overlay8", "psBM04FINANUM" + i + paramInt1 + "0" + paramInt3, "Finnish/0" + paramInt3 + ".tga", PaintScheme.psFinnishFighterColor[paramInt1][0], PaintScheme.psFinnishFighterColor[paramInt1][1], PaintScheme.psFinnishFighterColor[paramInt1][2]);
      }
      else
      {
        changeMat(paramHierMesh, "Overlay8", "psBM04FINCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", PaintScheme.psFinnishFighterColor[paramInt1][0], PaintScheme.psFinnishFighterColor[paramInt1][1], PaintScheme.psFinnishFighterColor[paramInt1][2], PaintScheme.psFinnishFighterColor[paramInt1][0], PaintScheme.psFinnishFighterColor[paramInt1][1], PaintScheme.psFinnishFighterColor[paramInt1][2]);
      }

      String str = getFAFACCode(paramClass, paramInt1);
      changeMat(paramHierMesh, "Overlay2", "psBM04FINACOD" + str + c, "Finnish/" + str + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

      changeMat(paramHierMesh, "Overlay3", "psBM04FINACOD" + str + c, "Finnish/" + str + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

      return;
    }

    if (paramRegiment.country() == PaintScheme.countryFrance) {
      if (paramInt3 < 10) {
        changeMat(paramHierMesh, "Overlay2", "psFB04FRALNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFB04FRARNUM" + i + paramInt1 + paramInt3, "null.tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFB04FRACNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFB04FRACNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(paramHierMesh, "Overlay6", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
    }

    if (paramRegiment.country() == PaintScheme.countryBritain) {
      if (("ra".equals(paramRegiment.branch())) || ("rz".equals(paramRegiment.branch())) || ("rn".equals(paramRegiment.branch()))) {
        paramInt3 = clampToLiteral(paramInt3);
        changeMat(paramHierMesh, "Overlay1", "psBM04BRINAVYREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);

        changeMat(paramHierMesh, "Overlay4", "psBM04BRINAVYREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);

        changeMat(paramHierMesh, "Overlay2", "psBM04BRINAVYLNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psBM04BRINAVYRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);

        changeMat(paramHierMesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
      } else {
        paramInt3 = clampToLiteral(paramInt3);
        changeMat(paramHierMesh, "Overlay1", "psBM04BRIREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2], PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2]);

        changeMat(paramHierMesh, "Overlay4", "psBM04BRIREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2], PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2]);

        changeMat(paramHierMesh, "Overlay2", "psBM04BRILNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2], 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psBM04BRIRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2]);

        changeMat(paramHierMesh, "Overlay7", "britishroundel4cthin", "British/roundel4cthin.tga", 1.0F, 1.0F, 1.0F);
      }
      return;
    }

    if (paramRegiment.country() == PaintScheme.countryHungary) {
      changeMat(paramHierMesh, "Overlay1", "psBM04HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay3", "psBM04HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay2", "psBM04HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay4", "psBM04HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay6", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == PaintScheme.countryItaly) {
      if (paramInt3 < 10) {
        changeMat(paramHierMesh, "Overlay2", "psFB04ITALNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFB04ITARNUM" + i + paramInt1 + paramInt3, "null.tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFB04ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFB04ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(paramHierMesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
    }

    if (paramRegiment.country() == PaintScheme.countryJapan) {
      changeMat(paramHierMesh, "Overlay2", "psBM04JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2], PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2]);

      changeMat(paramHierMesh, "Overlay3", "psBM04JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2], PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2]);

      changeMat(paramHierMesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
    }

    if (paramRegiment.country() == PaintScheme.countryPoland) {
      changeMat(paramHierMesh, "Overlay1", "psBM04POLCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay4", "psBM04POLCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == PaintScheme.countryRomania) {
      changeMat(paramHierMesh, "Overlay8", "psFB04ROMCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == PaintScheme.countryRussia) {
      changeMat(paramHierMesh, "Overlay2", "psBM04RUSCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay3", "psBM04RUSCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      if ((paramClass.isAssignableFrom(P_39N.class)) || (paramClass.isAssignableFrom(P_39Q1.class)) || (paramClass.isAssignableFrom(P_39Q10.class)))
      {
        changeMat(paramHierMesh, "Overlay6", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
      }
      changeMat(paramHierMesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay8", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == PaintScheme.countryNewZealand) {
      paramInt3 = clampToLiteral(paramInt3);
      changeMat(paramHierMesh, "Overlay1", "psBM00RZREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);

      changeMat(paramHierMesh, "Overlay4", "psBM00RZREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);

      changeMat(paramHierMesh, "Overlay2", "psBM00RZLNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay3", "psBM00RZRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);

      changeMat(paramHierMesh, "Overlay6", "newzealand6", "NewZealand/newzealand6.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "newzealand7", "NewZealand/newzealand7.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == PaintScheme.countrySlovakia) {
      if (paramInt3 < 10) {
        changeMat(paramHierMesh, "Overlay4", "psBM04SLVKLNUM" + i + paramInt1 + "0" + paramInt3, "Finnish/" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay1", "psBM04SLVKRNUM" + i + paramInt1 + "0" + paramInt3, "null.tga", "Finnish/" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay1", "psBM04SLVKCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay4", "psBM04SLVKCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(paramHierMesh, "Overlay6", "slovakiancross1", "Slovakian/cross1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == PaintScheme.countryUSA) {
      paramInt3 = clampToLiteral(paramInt3);
      changeMat(paramHierMesh, "Overlay1", "psBM04USAREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", 0.960784F, 0.745098F, 0.145098F, 0.960784F, 0.745098F, 0.145098F);

      changeMat(paramHierMesh, "Overlay4", "psBM04USAREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", 0.960784F, 0.745098F, 0.145098F, 0.960784F, 0.745098F, 0.145098F);

      changeMat(paramHierMesh, "Overlay2", "psBM04USALNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", 0.960784F, 0.745098F, 0.145098F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay3", "psBM04USARNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, 0.960784F, 0.745098F, 0.145098F);

      changeMat(paramHierMesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
  }
}