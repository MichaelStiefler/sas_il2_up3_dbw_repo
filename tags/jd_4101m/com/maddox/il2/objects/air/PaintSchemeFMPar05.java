package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeFMPar05 extends PaintScheme
{
  public String typedNameNum(Class paramClass, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRegiment.gruppeNumber() - 1;

    if (paramRegiment.country() == countryGermany) {
      if ((paramInt3 <= 1) && (paramInt1 == 0)) {
        return "< + " + psGermanFighterGruppeChar[1][i];
      }
      return psGermanFighterString[1][paramInt1] + paramInt3 + " + " + psGermanFighterGruppeChar[1][i];
    }

    if (paramRegiment.country() == countryNetherlands) {
      return "" + paramInt3;
    }

    if (paramRegiment.country() == countryFinland) {
      if (paramInt1 == 3) {
        return psFinnishFighterString[0][paramInt1] + (char)(65 + (paramInt3 % 10 - 1));
      }
      return psFinnishFighterString[0][paramInt1] + paramInt3;
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
      return "" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()) + " *";
    }

    if (paramRegiment.country() == countryNewZealand) {
      return "" + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    }

    if (paramRegiment.country() == countrySlovakia) {
      return "" + paramInt3 + " +";
    }

    if (paramRegiment.country() == countryUSA) {
      return "" + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()) + "*";
    }

    return super.typedNameNum(paramClass, paramRegiment, paramInt1, paramInt2, paramInt3);
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    int i = paramRegiment.gruppeNumber() - 1;
    int j;
    if (paramRegiment.country() == countryGermany) {
      j = paramInt1 + 1; if (j == 4) j = 0;
      int k = paramRegiment.gruppeNumber();

      changeMat(paramClass, paramHierMesh, "Overlay6", "balken4", "German/balken4.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "balken2", "German/balken2.tga", 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay8", "haken3", "German/" + (World.cur().isHakenAllowed() ? "haken3.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);

      if ((paramInt3 <= 1) && (paramInt1 == 0)) {
        changeMat(paramClass, paramHierMesh, "Overlay1", "psFM05GERCOML0000", "German/00c.tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramClass, paramHierMesh, "Overlay4", "psFM05GERCOMR0000", "German/00c2.tga", 1.0F, 1.0F, 1.0F);
        if ((k == 1) || (k == 5)) {
          changeMat(paramClass, paramHierMesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
          changeMat(paramClass, paramHierMesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
        } else {
          changeMat(paramClass, paramHierMesh, "Overlay2", "psFM05GERCOMCGID" + k, "German/00cG" + k + ".tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay3", "psFM05GERCOMCGID" + k, "German/00cG" + k + ".tga", 1.0F, 1.0F, 1.0F);
        }

        return;
      }
      if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay1", "psFM05GERLNUM" + i + paramInt1 + "0" + paramInt3, "German/1" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay4", "psFM05GERRNUM" + i + paramInt1 + "0" + paramInt3, "null.tga", "German/1" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay1", "psFM05GERCNUM" + i + paramInt1 + paramInt3, "German/1" + j + paramInt3 / 10 + ".tga", "German/1" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay4", "psFM05GERCNUM" + i + paramInt1 + paramInt3, "German/1" + j + paramInt3 / 10 + ".tga", "German/1" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      if ((k == 1) || (k == 5)) {
        changeMat(paramClass, paramHierMesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramClass, paramHierMesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      } else {
        changeMat(paramClass, paramHierMesh, "Overlay2", "psFM05GERGRUPP" + k + "S" + j, "German/1" + j + "0G" + k + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramClass, paramHierMesh, "Overlay3", "psFM05GERGRUPP" + k + "S" + j, "German/1" + j + "0G" + k + ".tga", 1.0F, 1.0F, 1.0F);
      }

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
      j = (char)(48 + paramInt3 % 10);
      changeMat(paramClass, paramHierMesh, "Overlay6", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay7", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);

      if (paramInt1 == 3) {
        changeMat(paramClass, paramHierMesh, "Overlay8", "psFM05FINCNUM" + i + paramInt1 + "_" + (char)(65 + (paramInt3 % 10 - 1)), "Finnish/" + (char)(65 + (paramInt3 % 10 - 1)) + ".tga", psFinnishFighterColor[paramInt1][0], psFinnishFighterColor[paramInt1][1], psFinnishFighterColor[paramInt1][2]);
      }
      else if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay8", "psFM05FINCNUM" + i + paramInt1 + "0" + paramInt3, "Finnish/0" + paramInt3 + ".tga", psFinnishFighterColor[paramInt1][0], psFinnishFighterColor[paramInt1][1], psFinnishFighterColor[paramInt1][2]);
      }
      else
      {
        changeMat(paramHierMesh, "Overlay8", "psFM05FINCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", psFinnishFighterColor[paramInt1][0], psFinnishFighterColor[paramInt1][1], psFinnishFighterColor[paramInt1][2], psFinnishFighterColor[paramInt1][0], psFinnishFighterColor[paramInt1][1], psFinnishFighterColor[paramInt1][2]);
      }

      String str = getFAFACCode(paramClass, paramInt1);
      changeMat(paramHierMesh, "Overlay2", "psFM05FINACID" + str + j, "Finnish/" + str + ".tga", "Finnish/sn" + j + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

      changeMat(paramHierMesh, "Overlay3", "psFM05FINACID" + str + j, "Finnish/" + str + ".tga", "Finnish/sn" + j + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

      return;
    }

    if (paramRegiment.country() == countryFrance) {
      if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay2", "psFM05FRALNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFM05FRARNUM" + i + paramInt1 + paramInt3, "null.tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFM05FRACNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFM05FRACNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(paramClass, paramHierMesh, "Overlay6", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
    }

    if (paramRegiment.country() == countryBritain) {
      if (("ra".equals(paramRegiment.branch())) || ("rz".equals(paramRegiment.branch())) || ("rn".equals(paramRegiment.branch()))) {
        paramInt3 = clampToLiteral(paramInt3);
        changeMat(paramHierMesh, "Overlay1", "psFM05BRINAVYREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);

        changeMat(paramHierMesh, "Overlay4", "psFM05BRINAVYREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);

        changeMat(paramHierMesh, "Overlay2", "psFM05BRINAVYLNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFM05BRINAVYRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, psBritishWhiteColor[0], psBritishWhiteColor[1], psBritishWhiteColor[2]);

        changeMat(paramClass, paramHierMesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramClass, paramHierMesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
      } else {
        paramInt3 = clampToLiteral(paramInt3);
        changeMat(paramHierMesh, "Overlay1", "psFM05BRIREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2], psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2]);

        changeMat(paramHierMesh, "Overlay3", "psFM05BRIREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2], psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2]);

        changeMat(paramHierMesh, "Overlay2", "psFM05BRILNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2], 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay4", "psFM05BRIRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, psBritishGrayColor[0], psBritishGrayColor[1], psBritishGrayColor[2]);

        changeMat(paramClass, paramHierMesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramClass, paramHierMesh, "Overlay7", "britishroundel4cthin", "British/roundel4cthin.tga", 1.0F, 1.0F, 1.0F);
      }
      return;
    }

    if (paramRegiment.country() == countryHungary) {
      changeMat(paramHierMesh, "Overlay1", "psFM05HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay3", "psFM05HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay2", "psFM05HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay4", "psFM05HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramClass, paramHierMesh, "Overlay6", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "hungarianbalkennewer", "Hungarian/balkennewer.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countryItaly) {
      if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay2", "psFM05ITALNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFM05ITARNUM" + i + paramInt1 + paramInt3, "null.tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFM05ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay3", "psFM05ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(paramClass, paramHierMesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
    }

    if (paramRegiment.country() == countryJapan) {
      changeMat(paramHierMesh, "Overlay2", "psFM05JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2], psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2]);

      changeMat(paramHierMesh, "Overlay3", "psFM05JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2], psRussianBomberColor[0][0], psRussianBomberColor[0][1], psRussianBomberColor[0][2]);

      changeMat(paramClass, paramHierMesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
    }

    if (paramRegiment.country() == countryPoland) {
      changeMat(paramHierMesh, "Overlay1", "psFM05POLCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay4", "psFM05POLCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countryRomania) {
      changeMat(paramHierMesh, "Overlay8", "psFM05ROMCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countryRussia) {
      changeMat(paramHierMesh, "Overlay1", "psFM05RUSCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay4", "psFM05RUSCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay7", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay8", "redstar3", "Russian/redstar3.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countryNewZealand) {
      changeMat(paramHierMesh, "Overlay1", "psBM05RZCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay4", "psBM05RZCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay6", "newzealand6", "NewZealand/newzealand6.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "newzealand7", "NewZealand/newzealand7.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countrySlovakia) {
      if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay4", "psFM05SLVKLNUM" + i + paramInt1 + "0" + paramInt3, "Slovakian/" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay1", "psFM05SLVKRNUM" + i + paramInt1 + "0" + paramInt3, "null.tga", "Slovakian/" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay1", "psFM05SLVKCNUM" + i + paramInt1 + paramInt3, "Slovakian/" + paramInt3 / 10 + ".tga", "Slovakian/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(paramHierMesh, "Overlay4", "psFM05SLVKCNUM" + i + paramInt1 + paramInt3, "Slovakian/" + paramInt3 / 10 + ".tga", "Slovakian/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(paramClass, paramHierMesh, "Overlay6", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (paramRegiment.country() == countryUSA) {
      changeMat(paramHierMesh, "Overlay1", "psBM00USACNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay4", "psBM00USACNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramClass, paramHierMesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
  }
}