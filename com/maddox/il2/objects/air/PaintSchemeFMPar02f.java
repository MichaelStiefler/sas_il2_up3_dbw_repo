package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeFMPar02f extends PaintScheme
{
  public String typedNameNum(Class paramClass, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRegiment.gruppeNumber() - 1;
    if (paramRegiment.country() == PaintScheme.countryGermany) {
      if ((paramInt3 <= 1) && (paramInt1 == 0))
        return "< + " + PaintScheme.psGermanFighterGruppeChar[1][i];
      return PaintScheme.psGermanFighterString[0][paramInt1] + paramInt3 + " + " + PaintScheme.psGermanFighterGruppeChar[0][i];
    }

    if (paramRegiment.country() == PaintScheme.countryNetherlands)
      return "" + paramInt3;
    if (paramRegiment.country() == PaintScheme.countryFinland) {
      if (paramInt1 == 3) {
        return PaintScheme.psFinnishFighterString[0][paramInt1] + (char)(65 + (paramInt3 % 10 - 1));
      }
      return PaintScheme.psFinnishFighterString[0][paramInt1] + paramInt3;
    }
    if (paramRegiment.country() == PaintScheme.countryFrance)
      return "o " + paramInt3;
    if (paramRegiment.country() == PaintScheme.countryBritain) {
      paramInt3 = clampToLiteral(paramInt3);
      return "" + paramRegiment.id() + " - " + (char)(65 + (paramInt3 - 1));
    }
    if (paramRegiment.country() == PaintScheme.countryBritain) {
      return "" + paramRegiment.id() + " + " + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    }
    if (paramRegiment.country() == PaintScheme.countryItaly)
      return "" + paramInt3;
    if (paramRegiment.country() == PaintScheme.countryJapan)
      return "" + paramInt3;
    if (paramRegiment.country() == PaintScheme.countryPoland)
      return "" + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    if (paramRegiment.country() == PaintScheme.countryRomania)
      return "+ " + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    if (paramRegiment.country() == PaintScheme.countryRussia)
      return "* " + PaintScheme.psRussianBomberString[paramInt1] + " " + paramInt3;
    if (paramRegiment.country() == PaintScheme.countryNewZealand) {
      paramInt3 = clampToLiteral(paramInt3);
      return "" + (char)(65 + (paramInt3 - 1));
    }
    if (paramRegiment.country() == PaintScheme.countrySlovakia)
      return "+ " + paramInt3;
    if (paramRegiment.country() == PaintScheme.countryUSA)
      return "" + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()) + "*";
    return super.typedNameNum(paramClass, paramRegiment, paramInt1, paramInt2, paramInt3);
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    int i = paramRegiment.gruppeNumber() - 1;
    int j;
    if (paramRegiment.country() == PaintScheme.countryGermany) {
      j = paramInt1 + 1;
      if (j == 4)
        j = 0;
      int k = paramRegiment.gruppeNumber();
      changeMat(paramClass, paramHierMesh, "Overlay6", "balken0", "German/balken0.tga", 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay7", "balken1", "German/balken1.tga", 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay8", "haken1", "German/" + (World.cur().isHakenAllowed() ? "haken1.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);

      if ((paramInt3 <= 1) && (paramInt1 == 0)) {
        changeMat(paramClass, paramHierMesh, "Overlay1", "psFM02GERCOML0000", "German/00c.tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramClass, paramHierMesh, "Overlay4", "psFM02GERCOMR0000", "German/00c2.tga", 1.0F, 1.0F, 1.0F);

        if ((k > 1) && (k < 5)) {
          changeMat(paramClass, paramHierMesh, "Overlay2", "psFM02GERCOMCGID" + k, "German/00cG" + k + ".tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay3", "psFM02GERCOMCGID" + k, "German/00cG" + k + ".tga", 1.0F, 1.0F, 1.0F);
        }
      }
      else {
        if (paramInt3 < 10) {
          changeMat(paramClass, paramHierMesh, "Overlay1", "psFM02GERLNUM" + i + paramInt1 + paramInt3, "German/0" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramHierMesh, "Overlay4", "psFM02GERRNUM" + i + paramInt1 + paramInt3, "null.tga", "German/0" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
          changeMat(paramHierMesh, "Overlay1", "psFM02GERCNUM" + i + paramInt1 + paramInt3, "German/0" + j + paramInt3 / 10 + ".tga", "German/0" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

          changeMat(paramHierMesh, "Overlay4", "psFM02GERCNUM" + i + paramInt1 + paramInt3, "German/0" + j + paramInt3 / 10 + ".tga", "German/0" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        if ((k > 1) && (k < 5)) {
          changeMat(paramClass, paramHierMesh, "Overlay2", "psFM02GERGRUPP" + k + "S" + j, "German/0" + j + "0G" + k + ".tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay3", "psFM02GERGRUPP" + k + "S" + j, "German/0" + j + "0G" + k + ".tga", 1.0F, 1.0F, 1.0F);
        }

      }

    }
    else if (paramRegiment.country() == PaintScheme.countryNetherlands) {
      changeMat(paramClass, paramHierMesh, "Overlay6", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay7", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);

      changeMat(paramHierMesh, "Overlay1", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(paramHierMesh, "Overlay4", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
    }
    else if (paramRegiment.country() == PaintScheme.countryFinland) {
      j = (char)(48 + paramInt3 % 10);
      changeMat(paramClass, paramHierMesh, "Overlay6", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);

      changeMat(paramClass, paramHierMesh, "Overlay7", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);

      if (paramInt1 == 3) {
        changeMat(paramClass, paramHierMesh, "Overlay8", "psFM02FINCNUM" + i + paramInt1 + "_" + (char)(65 + (paramInt3 % 10 - 1)), "Finnish/" + (char)(65 + (paramInt3 % 10 - 1)) + ".tga", PaintScheme.psFinnishFighterColor[paramInt1][0], PaintScheme.psFinnishFighterColor[paramInt1][1], PaintScheme.psFinnishFighterColor[paramInt1][2]);
      }
      else if (paramInt3 < 10) {
        changeMat(paramClass, paramHierMesh, "Overlay8", "psFM02FINCNUM" + i + paramInt1 + "0" + paramInt3, "Finnish/0" + paramInt3 + ".tga", PaintScheme.psFinnishFighterColor[paramInt1][0], PaintScheme.psFinnishFighterColor[paramInt1][1], PaintScheme.psFinnishFighterColor[paramInt1][2]);
      }
      else
      {
        changeMat(paramHierMesh, "Overlay8", "psFM02FINCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", PaintScheme.psFinnishFighterColor[paramInt1][0], PaintScheme.psFinnishFighterColor[paramInt1][1], PaintScheme.psFinnishFighterColor[paramInt1][2], PaintScheme.psFinnishFighterColor[paramInt1][0], PaintScheme.psFinnishFighterColor[paramInt1][1], PaintScheme.psFinnishFighterColor[paramInt1][2]);
      }

      String str = getFAFACCode(paramClass, paramInt1);
      changeMat(paramHierMesh, "Overlay2", "psFM02FINACID" + str + j, "Finnish/" + str + ".tga", "Finnish/sn" + j + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

      changeMat(paramHierMesh, "Overlay3", "psFM02FINACID" + str + j, "Finnish/" + str + ".tga", "Finnish/sn" + j + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }
    else
    {
      if (paramRegiment.country() == PaintScheme.countryFrance) {
        if (paramInt3 < 10) {
          changeMat(paramClass, paramHierMesh, "Overlay2", "psFM02FRALNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramHierMesh, "Overlay3", "psFM02FRARNUM" + i + paramInt1 + paramInt3, "null.tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
          changeMat(paramHierMesh, "Overlay2", "psFM02FRACNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

          changeMat(paramHierMesh, "Overlay3", "psFM02FRACNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        changeMat(paramClass, paramHierMesh, "Overlay6", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramClass, paramHierMesh, "Overlay7", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
      }

      if (paramRegiment.country() == PaintScheme.countryBritain) {
        if (("ra".equals(paramRegiment.branch())) || ("rz".equals(paramRegiment.branch())) || ("rn".equals(paramRegiment.branch())))
        {
          paramInt3 = clampToLiteral(paramInt3);
          changeMat(paramHierMesh, "Overlay1", "psFM02BRINAVYREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);

          changeMat(paramHierMesh, "Overlay4", "psFM02BRINAVYREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);

          if (paramRegiment.aid()[0] != '_')
          {
            changeMat(paramHierMesh, "Overlay2", "psFM02BRINAVYLNUM" + paramRegiment.id() + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + paramRegiment.aid()[0] + "f.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 0.11F, 0.11F, 0.11F, 0.11F, 0.11F, 0.11F);

            changeMat(paramHierMesh, "Overlay3", "psFM02BRINAVYRNUM" + paramRegiment.id() + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + paramRegiment.aid()[0] + "f.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 0.11F, 0.11F, 0.11F, 0.11F, 0.11F, 0.11F);
          }
          else
          {
            changeMat(paramClass, paramHierMesh, "Overlay2", "psFM02BRINAVYLNUM" + paramRegiment.id() + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", 0.11F, 0.11F, 0.11F);

            changeMat(paramHierMesh, "Overlay3", "psFM02BRINAVYRNUM" + paramRegiment.id() + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + paramRegiment.aid()[0] + "f.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 0.11F, 0.11F, 0.11F, 0.11F, 0.11F, 0.11F);
          }

          changeMat(paramClass, paramHierMesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
        }
        else {
          paramInt3 = clampToLiteral(paramInt3);
          changeMat(paramHierMesh, "Overlay1", "psFM02BRIREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2], PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2]);

          changeMat(paramHierMesh, "Overlay3", "psFM02BRIREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2], PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2]);

          changeMat(paramHierMesh, "Overlay2", "psFM02BRILNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2], 1.0F, 1.0F, 1.0F);

          changeMat(paramHierMesh, "Overlay4", "psFM02BRIRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2]);

          changeMat(paramClass, paramHierMesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay7", "britishroundel4c", "British/roundel4c.tga", 1.0F, 1.0F, 1.0F);
        }
      }
      else if (paramRegiment.country() == PaintScheme.countryHungary) {
        changeMat(paramHierMesh, "Overlay1", "psFM02HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

        changeMat(paramHierMesh, "Overlay3", "psFM02HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

        changeMat(paramHierMesh, "Overlay2", "psFM02HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

        changeMat(paramHierMesh, "Overlay4", "psFM02HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

        changeMat(paramClass, paramHierMesh, "Overlay6", "hungarianbalkenolder", "Hungarian/balkenolder.tga", 1.0F, 1.0F, 1.0F);

        changeMat(paramClass, paramHierMesh, "Overlay7", "hungarianbalkenolder", "Hungarian/balkenolder.tga", 1.0F, 1.0F, 1.0F);
      }
      else {
        if (paramRegiment.country() == PaintScheme.countryItaly) {
          if (paramInt3 < 10) {
            changeMat(paramClass, paramHierMesh, "Overlay2", "psFM02ITALNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);

            changeMat(paramHierMesh, "Overlay3", "psFM02ITARNUM" + i + paramInt1 + paramInt3, "null.tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
          }
          else
          {
            changeMat(paramHierMesh, "Overlay2", "psFM02ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

            changeMat(paramHierMesh, "Overlay3", "psFM02ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
          }

          changeMat(paramClass, paramHierMesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
        }

        if (paramRegiment.country() == PaintScheme.countryJapan) {
          changeMat(paramHierMesh, "Overlay2", "psFM02JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2], PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2]);

          changeMat(paramHierMesh, "Overlay3", "psFM02JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2], PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2]);

          changeMat(paramClass, paramHierMesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
        }

        if (paramRegiment.country() == PaintScheme.countryPoland) {
          changeMat(paramHierMesh, "Overlay1", "psFM02POLCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

          changeMat(paramHierMesh, "Overlay4", "psFM02POLCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
        }
        else if (paramRegiment.country() == PaintScheme.countryRomania) {
          changeMat(paramHierMesh, "Overlay8", "psFM02ROMCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
        }
        else if (paramRegiment.country() == PaintScheme.countryRussia) {
          if (paramInt3 < 10) {
            changeMat(paramClass, paramHierMesh, "Overlay2", "psFM02RUSLNUM" + i + paramInt1 + "0" + paramInt3, "Russian/0" + paramInt3 + ".tga", PaintScheme.psRussianBomberColor[paramInt1][0], PaintScheme.psRussianBomberColor[paramInt1][1], PaintScheme.psRussianBomberColor[paramInt1][2]);

            changeMat(paramHierMesh, "Overlay3", "psFM02RUSRNUM" + i + paramInt1 + "0" + paramInt3, "null.tga", "Russian/0" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psRussianBomberColor[paramInt1][0], PaintScheme.psRussianBomberColor[paramInt1][1], PaintScheme.psRussianBomberColor[paramInt1][2]);
          }
          else
          {
            changeMat(paramHierMesh, "Overlay2", "psFM02RUSCNUM" + i + paramInt1 + paramInt3, "Russian/0" + paramInt3 / 10 + ".tga", "Russian/0" + paramInt3 % 10 + ".tga", PaintScheme.psRussianBomberColor[paramInt1][0], PaintScheme.psRussianBomberColor[paramInt1][1], PaintScheme.psRussianBomberColor[paramInt1][2], PaintScheme.psRussianBomberColor[paramInt1][0], PaintScheme.psRussianBomberColor[paramInt1][1], PaintScheme.psRussianBomberColor[paramInt1][2]);

            changeMat(paramHierMesh, "Overlay3", "psFM02RUSCNUM" + i + paramInt1 + paramInt3, "Russian/0" + paramInt3 / 10 + ".tga", "Russian/0" + paramInt3 % 10 + ".tga", PaintScheme.psRussianBomberColor[paramInt1][0], PaintScheme.psRussianBomberColor[paramInt1][1], PaintScheme.psRussianBomberColor[paramInt1][2], PaintScheme.psRussianBomberColor[paramInt1][0], PaintScheme.psRussianBomberColor[paramInt1][1], PaintScheme.psRussianBomberColor[paramInt1][2]);
          }

          changeMat(paramClass, paramHierMesh, "Overlay7", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay8", "redstar2", "Russian/redstar2.tga", 1.0F, 1.0F, 1.0F);
        }
        else if (paramRegiment.country() == PaintScheme.countryNewZealand)
        {
          paramInt3 = clampToLiteral(paramInt3);
          changeMat(paramHierMesh, "Overlay2", "psFM00RZLNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);

          changeMat(paramHierMesh, "Overlay3", "psFM00RZRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);

          changeMat(paramClass, paramHierMesh, "Overlay6", "newzealand6", "NewZealand/newzealand6.tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay7", "newzealand7", "NewZealand/newzealand7.tga", 1.0F, 1.0F, 1.0F);
        }
        else if (paramRegiment.country() == PaintScheme.countrySlovakia) {
          if (paramInt3 < 10) {
            changeMat(paramClass, paramHierMesh, "Overlay2", "psFM02SLVKLNUM" + i + paramInt1 + "0" + paramInt3, "Finnish/" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F);

            changeMat(paramHierMesh, "Overlay3", "psFM02SLVKRNUM" + i + paramInt1 + "0" + paramInt3, "null.tga", "Finnish/" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
          }
          else
          {
            changeMat(paramHierMesh, "Overlay2", "psFM02SLVKCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

            changeMat(paramHierMesh, "Overlay3", "psFM02SLVKCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
          }

          changeMat(paramClass, paramHierMesh, "Overlay6", "slovakiancross1", "Slovakian/cross1.tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay7", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
        }
        else if (paramRegiment.country() == PaintScheme.countryUSA) {
          changeMat(paramHierMesh, "Overlay1", "psBM00USACNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

          changeMat(paramHierMesh, "Overlay4", "psBM00USACNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);

          changeMat(paramClass, paramHierMesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
        }
      }
    }
  }
}