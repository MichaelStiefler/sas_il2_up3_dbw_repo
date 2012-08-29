package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeFMPar01bis extends PaintScheme
{
  public String typedNameNum(Class class1, Regiment regiment, int i, int j, int k)
  {
    int l = regiment.gruppeNumber() - 1;

    if (regiment.country() == PaintScheme.countryGermany) {
      if ((k <= 1) && (i == 0)) {
        return "< + " + PaintScheme.psGermanFighterGruppeChar[0][l];
      }
      return PaintScheme.psGermanFighterString[0][i] + k + " + " + PaintScheme.psGermanFighterGruppeChar[0][l];
    }

    if (regiment.country() == PaintScheme.countryNetherlands) {
      return k;
    }

    if (regiment.country() == PaintScheme.countryFinland) {
      if (i == 3) {
        return PaintScheme.psFinnishFighterString[0][i] + (char)(65 + (k % 10 - 1));
      }
      return PaintScheme.psFinnishFighterString[0][i] + k;
    }

    if (regiment.country() == PaintScheme.countryFrance) {
      return "o " + k;
    }

    if (regiment.country() == PaintScheme.countryBritain) {
      k = clampToLiteral(k);
      return regiment.id() + " - " + (char)(65 + (k - 1));
    }

    if (regiment.country() == PaintScheme.countryBritain) {
      return regiment.id() + " + " + (k < 10 ? "0" + k : new StringBuffer().append(k).toString());
    }

    if (regiment.country() == PaintScheme.countryItaly) {
      return k;
    }

    if (regiment.country() == PaintScheme.countryJapan) {
      return k;
    }

    if (regiment.country() == PaintScheme.countryPoland) {
      return k < 10 ? "0" + k : new StringBuffer().append(k).toString();
    }

    if (regiment.country() == PaintScheme.countryRomania) {
      return "+ " + (k < 10 ? "0" + k : new StringBuffer().append(k).toString());
    }

    if (regiment.country() == PaintScheme.countryRussia) {
      return k + " *";
    }

    if (regiment.country() == PaintScheme.countryNewZealand) {
      k = clampToLiteral(k);
      return (char)(65 + (k - 1));
    }

    if (regiment.country() == PaintScheme.countrySlovakia) {
      return "+ " + k;
    }

    if (regiment.country() == PaintScheme.countryUSA) {
      return (k < 10 ? "0" + k : new StringBuffer().append(k).toString()) + "*";
    }

    return super.typedNameNum(class1, regiment, i, j, k);
  }

  public void prepareNum(Class class1, HierMesh hiermesh, Regiment regiment, int i, int j, int k)
  {
    super.prepareNum(class1, hiermesh, regiment, i, j, k);
    int l = regiment.gruppeNumber() - 1;

    if (regiment.country() == PaintScheme.countryGermany) {
      int i1 = i + 1; if (i1 == 4) i1 = 0;
      int j1 = regiment.gruppeNumber();
      changeMat(hiermesh, "Overlay6", "balken0", "German/balken0.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay7", "balken1", "German/balken1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay8", "haken1", "German/" + (World.cur().isHakenAllowed() ? "haken1.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);

      if ((k <= 1) && (i == 0)) {
        changeMat(hiermesh, "Overlay1", "psFM01GERCOML0000", "German/00c.tga", 1.0F, 1.0F, 1.0F);
        changeMat(hiermesh, "Overlay4", "psFM01GERCOMR0000", "German/00c2.tga", 1.0F, 1.0F, 1.0F);
        if ((j1 > 1) && (j1 < 5)) {
          changeMat(hiermesh, "Overlay2", "psFM01GERCOMCGID" + j1, "German/00cG" + j1 + ".tga", 1.0F, 1.0F, 1.0F);

          changeMat(hiermesh, "Overlay3", "psFM01GERCOMCGID" + j1, "German/00cG" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
        }

        return;
      }
      if (k < 10) {
        changeMat(hiermesh, "Overlay1", "psFM01GERLNUM" + l + i + k, "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(hiermesh, "Overlay4", "psFM01GERRNUM" + l + i + k, "null.tga", "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(hiermesh, "Overlay1", "psFM01GERCNUM" + l + i + k, "German/0" + i1 + k / 10 + ".tga", "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(hiermesh, "Overlay4", "psFM01GERCNUM" + l + i + k, "German/0" + i1 + k / 10 + ".tga", "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      if ((j1 > 1) && (j1 < 5)) {
        changeMat(hiermesh, "Overlay2", "psFM00GERGRUPP" + j1 + "S" + i1, "German/0" + i1 + "0G" + j1 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(hiermesh, "Overlay3", "psFM00GERGRUPP" + j1 + "S" + i1, "German/0" + i1 + "0G" + j1 + ".tga", 1.0F, 1.0F, 1.0F);
      }

      return;
    }

    if (regiment.country() == PaintScheme.countryNetherlands) {
      changeMat(hiermesh, "Overlay6", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay7", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay1", "psBM00DUTCNUM" + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(hiermesh, "Overlay4", "psBM00DUTCNUM" + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      return;
    }

    if (regiment.country() == PaintScheme.countryFinland) {
      char c = (char)(48 + k % 10);
      changeMat(hiermesh, "Overlay6", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);

      changeMat(hiermesh, "Overlay7", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);

      if (i == 3) {
        changeMat(hiermesh, "Overlay8", "psFM01FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", PaintScheme.psFinnishFighterColor[i][0], PaintScheme.psFinnishFighterColor[i][1], PaintScheme.psFinnishFighterColor[i][2]);
      }
      else if (k < 10) {
        changeMat(hiermesh, "Overlay8", "psFM01FINCNUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", PaintScheme.psFinnishFighterColor[i][0], PaintScheme.psFinnishFighterColor[i][1], PaintScheme.psFinnishFighterColor[i][2]);
      }
      else
      {
        changeMat(hiermesh, "Overlay8", "psFM01FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", PaintScheme.psFinnishFighterColor[i][0], PaintScheme.psFinnishFighterColor[i][1], PaintScheme.psFinnishFighterColor[i][2], PaintScheme.psFinnishFighterColor[i][0], PaintScheme.psFinnishFighterColor[i][1], PaintScheme.psFinnishFighterColor[i][2]);
      }

      String s = getFAFACCode(class1, i);
      changeMat(hiermesh, "Overlay2", "psFM01FINACID" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

      changeMat(hiermesh, "Overlay3", "psFM01FINACID" + s + c, "Finnish/" + s + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

      return;
    }

    if (regiment.country() == PaintScheme.countryFrance) {
      if (k < 10) {
        changeMat(hiermesh, "Overlay2", "psFM01FRALNUM" + l + i + k, "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(hiermesh, "Overlay3", "psFM01FRARNUM" + l + i + k, "null.tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(hiermesh, "Overlay2", "psFM01FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(hiermesh, "Overlay3", "psFM01FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(hiermesh, "Overlay6", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay7", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
    }

    if (regiment.country() == PaintScheme.countryBritain) {
      if (("ra".equals(regiment.branch())) || ("rz".equals(regiment.branch())) || ("rn".equals(regiment.branch()))) {
        k = clampToLiteral(k);
        changeMat(hiermesh, "Overlay1", "psFM04BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);

        changeMat(hiermesh, "Overlay4", "psFM04BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);

        changeMat(hiermesh, "Overlay2", "psFM04BRINAVYLNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "British/" + (char)(65 + k - 1) + ".tga", "null.tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);

        changeMat(hiermesh, "Overlay3", "psFM04BRINAVYRNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "null.tga", "British/" + (char)(65 + k - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);

        changeMat(hiermesh, "Overlay6", "britishroundel5n", "null.tga", 1.0F, 1.0F, 1.0F);
        changeMat(hiermesh, "Overlay7", "britishroundel5n", "null.tga", 1.0F, 1.0F, 1.0F);
      } else {
        k = clampToLiteral(k);
        changeMat(hiermesh, "Overlay1", "psFM04BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2], PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2]);

        changeMat(hiermesh, "Overlay3", "psFM04BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2], PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2]);

        changeMat(hiermesh, "Overlay2", "psFM04BRILNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "British/" + (char)(65 + k - 1) + ".tga", "null.tga", PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2], 1.0F, 1.0F, 1.0F);

        changeMat(hiermesh, "Overlay4", "psFM04BRIRNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "null.tga", "British/" + (char)(65 + k - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2]);

        changeMat(hiermesh, "Overlay6", "britishroundel2c", "null.tga", 1.0F, 1.0F, 1.0F);
        changeMat(hiermesh, "Overlay7", "britishroundel3c", "null.tga", 1.0F, 1.0F, 1.0F);
      }
      return;
    }

    if (regiment.country() == PaintScheme.countryHungary) {
      changeMat(hiermesh, "Overlay1", "psFM01HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(hiermesh, "Overlay3", "psFM01HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(hiermesh, "Overlay2", "psFM01HUNCNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(hiermesh, "Overlay4", "psFM01HUNCNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);

      changeMat(hiermesh, "Overlay6", "hungarianbalkenolder", "Hungarian/balkenolder.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay7", "hungarianbalkenolder", "Hungarian/balkenolder.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (regiment.country() == PaintScheme.countryItaly) {
      if (k < 10) {
        changeMat(hiermesh, "Overlay2", "psFM01ITALNUM" + l + i + k, "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(hiermesh, "Overlay3", "psFM01ITARNUM" + l + i + k, "null.tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(hiermesh, "Overlay2", "psFM01ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(hiermesh, "Overlay3", "psFM01ITACNUM" + l + i + k, "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(hiermesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
    }

    if (regiment.country() == PaintScheme.countryJapan) {
      changeMat(hiermesh, "Overlay2", "psFM01JAPCNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2], PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2]);

      changeMat(hiermesh, "Overlay3", "psFM01JAPCNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2], PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2]);

      changeMat(hiermesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
    }

    if (regiment.country() == PaintScheme.countryPoland) {
      changeMat(hiermesh, "Overlay1", "psFM01POLCNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(hiermesh, "Overlay4", "psFM01POLCNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(hiermesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (regiment.country() == PaintScheme.countryRomania) {
      changeMat(hiermesh, "Overlay8", "psFM01ROMCNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(hiermesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (regiment.country() == PaintScheme.countryRussia) {
      if (k < 10) {
        changeMat(hiermesh, "Overlay1", "psFM01RUSLNUM" + l + i + "0" + k, "Russian/0" + k + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(hiermesh, "Overlay4", "psFM01RUSRNUM" + l + i + "0" + k, "null.tga", "Russian/0" + k + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(hiermesh, "Overlay1", "psFM01RUSCNUM" + l + i + k, "Russian/0" + k / 10 + ".tga", "Russian/0" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(hiermesh, "Overlay4", "psFM01RUSCNUM" + l + i + k, "Russian/0" + k / 10 + ".tga", "Russian/0" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(hiermesh, "Overlay6", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay7", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay8", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (regiment.country() == PaintScheme.countryNewZealand) {
      k = clampToLiteral(k);
      changeMat(hiermesh, "Overlay2", "psFM00RZLNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "British/" + (char)(65 + k - 1) + ".tga", "null.tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);

      changeMat(hiermesh, "Overlay3", "psFM00RZRNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "null.tga", "British/" + (char)(65 + k - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);

      changeMat(hiermesh, "Overlay6", "newzealand6", "NewZealand/newzealand6.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay7", "newzealand7", "NewZealand/newzealand7.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (regiment.country() == PaintScheme.countrySlovakia) {
      if (k < 10) {
        changeMat(hiermesh, "Overlay2", "psFM01SLVKLNUM" + l + i + "0" + k, "Finnish/" + k + ".tga", 1.0F, 1.0F, 1.0F);

        changeMat(hiermesh, "Overlay3", "psFM01SLVKRNUM" + l + i + "0" + k, "null.tga", "Finnish/" + k + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(hiermesh, "Overlay2", "psFM01SLVKCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

        changeMat(hiermesh, "Overlay3", "psFM01SLVKCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }

      changeMat(hiermesh, "Overlay6", "slovakiancross1", "Slovakian/cross1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay7", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
      return;
    }

    if (regiment.country() == PaintScheme.countryUSA) {
      changeMat(hiermesh, "Overlay1", "psBM00USACNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(hiermesh, "Overlay4", "psBM00USACNUM" + l + i + (k >= 10 ? k : new StringBuffer("0").append(k).toString()), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

      changeMat(hiermesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(hiermesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
  }
}