package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeFMPar00 extends PaintScheme
{
  public String typedNameNum(Class paramClass, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramRegiment.gruppeNumber() - 1;
    if (paramRegiment.country() == PaintScheme.countryGermany) {
      if ((paramInt3 <= 1) && (paramInt1 == 0)) {
        return "< + " + PaintScheme.psGermanFighterGruppeChar[0][i];
      }
      return PaintScheme.psGermanFighterString[0][paramInt1] + paramInt3 + " + " + PaintScheme.psGermanFighterGruppeChar[0][i];
    }if (paramRegiment.country() == PaintScheme.countryNetherlands)
      return "" + paramInt3;
    if (paramRegiment.country() == PaintScheme.countryFinland) {
      if (paramInt1 == 3) {
        return PaintScheme.psFinnishFighterString[0][paramInt1] + (char)(65 + (paramInt3 % 10 - 1));
      }
      return PaintScheme.psFinnishFighterString[0][paramInt1] + paramInt3;
    }if (paramRegiment.country() == PaintScheme.countryFrance)
      return "o " + paramInt3;
    if (paramRegiment.country() == PaintScheme.countryBritain)
    {
      paramInt3 = clampToLiteral(paramInt3);
      return "" + paramRegiment.id() + " - " + (char)(65 + (paramInt3 - 1));
    }
    if (paramRegiment.country() == PaintScheme.countryBritain)
      return "" + paramRegiment.id() + " + " + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    if (paramRegiment.country() == PaintScheme.countryItaly)
      return "" + paramInt3;
    if (paramRegiment.country() == PaintScheme.countryJapan)
      return "" + paramInt3;
    if (paramRegiment.country() == PaintScheme.countryPoland)
      return "" + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    if (paramRegiment.country() == PaintScheme.countryRomania)
      return "+ " + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString());
    if (paramRegiment.country() == PaintScheme.countryRussia)
      return "" + paramInt3 + " *";
    if (paramRegiment.country() == PaintScheme.countryNewZealand)
    {
      paramInt3 = clampToLiteral(paramInt3);
      return "" + (char)(65 + (paramInt3 - 1));
    }
    if (paramRegiment.country() == PaintScheme.countrySlovakia)
      return "+ " + paramInt3;
    if (paramRegiment.country() == PaintScheme.countryUSA) {
      return "" + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()) + "*";
    }
    return super.typedNameNum(paramClass, paramRegiment, paramInt1, paramInt2, paramInt3);
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    super.prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    int i = paramRegiment.gruppeNumber() - 1;
    int j;
    if (paramRegiment.country() == PaintScheme.countryGermany)
    {
      j = paramInt1 + 1;
      if (j == 4)
        j = 0;
      int k = paramRegiment.gruppeNumber();
      changeMat(paramHierMesh, "Overlay6", "balken0", "German/balken0.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "balken1", "German/balken1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay8", "haken0", "German/" + (World.cur().isHakenAllowed() ? "haken0.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);
      if ((paramInt3 <= 1) && (paramInt1 == 0))
      {
        changeMat(paramHierMesh, "Overlay1", "psFM00GERCOML0000", "German/00c.tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay4", "psFM00GERCOMR0000", "German/00c2.tga", 1.0F, 1.0F, 1.0F);
        if ((k > 1) && (k < 5))
        {
          changeMat(paramHierMesh, "Overlay2", "psFM00GERCOMCGID" + k, "German/00cG" + k + ".tga", 1.0F, 1.0F, 1.0F);
          changeMat(paramHierMesh, "Overlay3", "psFM00GERCOMCGID" + k, "German/00cG" + k + ".tga", 1.0F, 1.0F, 1.0F);
        }
        return;
      }
      if (paramInt3 < 10)
      {
        changeMat(paramHierMesh, "Overlay1", "psFM00GERLNUM" + i + paramInt1 + paramInt3, "German/0" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay4", "psFM00GERRNUM" + i + paramInt1 + paramInt3, "null.tga", "German/0" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay1", "psFM00GERCNUM" + i + paramInt1 + paramInt3, "German/0" + j + paramInt3 / 10 + ".tga", "German/0" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay4", "psFM00GERCNUM" + i + paramInt1 + paramInt3, "German/0" + j + paramInt3 / 10 + ".tga", "German/0" + j + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      if ((k > 1) && (k < 5))
      {
        changeMat(paramHierMesh, "Overlay2", "psFM00GERGRUPP" + k + "S" + j, "German/0" + j + "0G" + k + ".tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFM00GERGRUPP" + k + "S" + j, "German/0" + j + "0G" + k + ".tga", 1.0F, 1.0F, 1.0F);
      }
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryNetherlands)
    {
      changeMat(paramHierMesh, "Overlay6", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay1", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay4", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryBelgium)
    {
      changeMat(paramHierMesh, "Overlay6", "Belgiumroundel", "Belgium/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "Belgiumroundel", "Belgium/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay1", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay4", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryGreece)
    {
      changeMat(paramHierMesh, "Overlay2", "BlackNum_" + i + paramInt1 + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()), "Default/1" + paramInt3 / 10 + ".tga", "Default/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay3", "BlackNum_" + i + paramInt1 + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()), "Default/1" + paramInt3 / 10 + ".tga", "Default/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "GRRoundel", "GR/GRRoundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "GRRoundel", "GR/GRRoundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay8", "GRTail", "GR/GRTail.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryEgypt)
    {
      changeMat(paramHierMesh, "Overlay2", "ArabicNum_" + i + paramInt1 + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()), "Arabic/1" + paramInt3 / 10 + ".tga", "Arabic/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay3", "ArabicNum_" + i + paramInt1 + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()), "Arabic/1" + paramInt3 / 10 + ".tga", "Arabic/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "Egyptroundel", "Egypt/EgyptRoundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "Egyptroundel", "Egypt/EgyptRoundel.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countrySyria)
    {
      changeMat(paramHierMesh, "Overlay2", "ArabicNum_" + i + paramInt1 + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()), "Arabic/1" + paramInt3 / 10 + ".tga", "Arabic/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay3", "ArabicNum_" + i + paramInt1 + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()), "Arabic/1" + paramInt3 / 10 + ".tga", "Arabic/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "Syriaroundel", "Syria/SyriaRoundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "Syriaroundel", "Syria/SyriaRoundel.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryIraq)
    {
      changeMat(paramHierMesh, "Overlay2", "ArabicNum_" + i + paramInt1 + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()), "Arabic/1" + paramInt3 / 10 + ".tga", "Arabic/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay3", "ArabicNum_" + i + paramInt1 + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()), "Arabic/1" + paramInt3 / 10 + ".tga", "Arabic/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "Iraqroundel", "Iraq/IraqRoundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "Iraqroundel", "Iraq/IraqRoundel.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryCzech)
    {
      changeMat(paramHierMesh, "Overlay6", "Czechroundel", "Czech/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "Czechroundel", "Czech/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay1", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay4", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryNSpain)
    {
      changeMat(paramHierMesh, "Overlay6", "NSpainroundel", "RSpain/spanishnatroundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "NSpainroundel", "RSpain/spanishnatroundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay1", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay4", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryFinland)
    {
      j = (char)(48 + paramInt3 % 10);
      changeMat(paramHierMesh, "Overlay6", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "FAFhaken", "Finnish/" + (World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
      if (paramInt1 == 3) {
        changeMat(paramHierMesh, "Overlay8", "psFM00FINCNUM" + i + paramInt1 + "_" + (char)(65 + (paramInt3 % 10 - 1)), "Finnish/" + (char)(65 + (paramInt3 % 10 - 1)) + ".tga", PaintScheme.psFinnishFighterColor[paramInt1][0], PaintScheme.psFinnishFighterColor[paramInt1][1], PaintScheme.psFinnishFighterColor[paramInt1][2]);
      }
      else if (paramInt3 < 10)
        changeMat(paramHierMesh, "Overlay8", "psFM00FINCNUM" + i + paramInt1 + "0" + paramInt3, "Finnish/0" + paramInt3 + ".tga", PaintScheme.psFinnishFighterColor[paramInt1][0], PaintScheme.psFinnishFighterColor[paramInt1][1], PaintScheme.psFinnishFighterColor[paramInt1][2]);
      else
        changeMat(paramHierMesh, "Overlay8", "psFM00FINCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", PaintScheme.psFinnishFighterColor[paramInt1][0], PaintScheme.psFinnishFighterColor[paramInt1][1], PaintScheme.psFinnishFighterColor[paramInt1][2], PaintScheme.psFinnishFighterColor[paramInt1][0], PaintScheme.psFinnishFighterColor[paramInt1][1], PaintScheme.psFinnishFighterColor[paramInt1][2]);
      String str = getFAFACCode(paramClass, paramInt1);
      changeMat(paramHierMesh, "Overlay2", "psFM00FINACID" + str + j, "Finnish/" + str + ".tga", "Finnish/sn" + j + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      changeMat(paramHierMesh, "Overlay3", "psFM00FINACID" + str + j, "Finnish/" + str + ".tga", "Finnish/sn" + j + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryFrance)
    {
      if (paramInt3 < 10)
      {
        changeMat(paramHierMesh, "Overlay2", "psFM00FRALNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFM00FRARNUM" + i + paramInt1 + paramInt3, "null.tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFM00FRACNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFM00FRACNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      changeMat(paramHierMesh, "Overlay6", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
    }
    if (paramRegiment.country() == PaintScheme.countryRSpain)
    {
      if (paramInt3 < 10)
      {
        changeMat(paramHierMesh, "Overlay2", "psFB00ITALNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFB00ITARNUM" + i + paramInt1 + paramInt3, "null.tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFB00ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFB00ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      changeMat(paramHierMesh, "Overlay6", "RepSpain", "RSpain/Repubicanroundel.tga", 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay7", "RepSpain", "RSpain/Republicanroundel.tga", 1.0F, 1.0F, 1.0F);
    }
    if (paramRegiment.country() == PaintScheme.countryBritain)
    {
      if (("ra".equals(paramRegiment.branch())) || ("rz".equals(paramRegiment.branch())) || ("rn".equals(paramRegiment.branch())))
      {
        paramInt3 = clampToLiteral(paramInt3);
        changeMat(paramHierMesh, "Overlay1", "psFM00BRINAVYREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);
        changeMat(paramHierMesh, "Overlay4", "psFM00BRINAVYREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);
        changeMat(paramHierMesh, "Overlay2", "psFM00BRINAVYLNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFM00BRINAVYRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);
        changeMat(paramHierMesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
      }
      else {
        paramInt3 = clampToLiteral(paramInt3);
        changeMat(paramHierMesh, "Overlay1", "psFM00BRIREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2], PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2]);
        changeMat(paramHierMesh, "Overlay3", "psFM00BRIREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2], PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2]);
        changeMat(paramHierMesh, "Overlay2", "psFM00BRILNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2], 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay4", "psFM00BRIRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2]);
        changeMat(paramHierMesh, "Overlay6", "britishroundel3c", "British/roundel3c.tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay7", "britishroundel3c", "British/roundel3c.tga", 1.0F, 1.0F, 1.0F);
      }
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryHungary)
    {
      changeMat(paramHierMesh, "Overlay1", "psFM00HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay3", "psFM00HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay2", "psFM00HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay4", "psFM00HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay6", "hungarianbalkenolder", "Hungarian/balkenolder.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "hungarianbalkenolder", "Hungarian/balkenolder.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countrySwitz)
    {
      changeMat(paramHierMesh, "Overlay1", "psBM00HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay3", "psBM00HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay2", "psBM00HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay4", "psBM00HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay6", "Swiss", "Swiss/emblem.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "Swiss", "Swiss/emblem.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryCroatia)
    {
      changeMat(paramHierMesh, "Overlay1", "psBM00HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay3", "psBM00HUNREGI" + paramRegiment.id(), "German/" + paramRegiment.aid()[0] + ".tga", "German/" + paramRegiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay2", "psBM00HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay4", "psBM00HUNCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay6", "CroatiaArms", "Croatia/croatiaArms.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay8", "CroatiaArms", "Croatia/croatiaArms.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryJapan)
    {
      changeMat(paramHierMesh, "Overlay2", "psFM00JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2], PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2]);
      changeMat(paramHierMesh, "Overlay3", "psFM00JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2], PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2]);
      changeMat(paramHierMesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
    }
    if (paramRegiment.country() == PaintScheme.countryChina)
    {
      changeMat(paramHierMesh, "Overlay2", "psBM00JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2], PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2]);
      changeMat(paramHierMesh, "Overlay3", "psBM00JAPCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2], PaintScheme.psRussianBomberColor[0][0], PaintScheme.psRussianBomberColor[0][1], PaintScheme.psRussianBomberColor[0][2]);
      changeMat(paramHierMesh, "Overlay6", "China", "China/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "China", "China/roundel.tga", 1.0F, 1.0F, 1.0F);
    }
    if (paramRegiment.country() == PaintScheme.countryNorthKorea)
    {
      changeMat(paramHierMesh, "Overlay8", "DefaultNum_" + i + paramInt1 + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()), "Default/1" + paramInt3 / 10 + ".tga", "Default/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countrySouthKorea)
    {
      changeMat(paramHierMesh, "Overlay1", "BlackNum_" + i + paramInt1 + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()), "Default/1" + paramInt3 / 10 + ".tga", "Default/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay4", "BlackNum_" + i + paramInt1 + (paramInt3 >= 10 ? "" + paramInt3 : new StringBuffer().append("0").append(paramInt3).toString()), "Default/1" + paramInt3 / 10 + ".tga", "Default/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryYugoslavia)
    {
      if (paramInt3 < 10)
      {
        changeMat(paramHierMesh, "Overlay2", "psFB00ITALNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFB00ITARNUM" + i + paramInt1 + paramInt3, "null.tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFB00ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFB00ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      changeMat(paramHierMesh, "Overlay6", "Yugoroundel", "Yugoslavia/roundel.tga", 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay7", "Yugoroundel", "Yugoslavia/roundel.tga", 1.0F, 1.0F, 1.0F);
    }
    if (paramRegiment.country() == PaintScheme.countryYugoPar)
    {
      if (paramInt3 < 10)
      {
        changeMat(paramHierMesh, "Overlay2", "psFB00ITALNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFB00ITARNUM" + i + paramInt1 + paramInt3, "null.tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFB00ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFB00ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      changeMat(paramHierMesh, "Overlay6", "Yugoroundel", "Yugoslavia/roundel.tga", 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay7", "Yugoroundel", "Yugoslavia/roundel.tga", 1.0F, 1.0F, 1.0F);
    }
    if (paramRegiment.country() == PaintScheme.countryItaly)
    {
      if (paramInt3 < 10)
      {
        changeMat(paramHierMesh, "Overlay2", "psFM00ITALNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFM00ITARNUM" + i + paramInt1 + paramInt3, "null.tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFM00ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFM00ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      changeMat(paramHierMesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
    }
    if (paramRegiment.country() == PaintScheme.countryANR)
    {
      if (paramInt3 < 10)
      {
        changeMat(paramHierMesh, "Overlay2", "psFM04ITALNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFM04ITARNUM" + i + paramInt1 + paramInt3, "null.tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFM04ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFM04ITACNUM" + i + paramInt1 + paramInt3, "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      changeMat(paramHierMesh, "Overlay6", "italian4", "Italian/roundel2.tga", 0.1F, 0.1F, 0.1F);
    }
    if (paramRegiment.country() == PaintScheme.countryICAF)
    {
      changeMat(paramHierMesh, "Overlay1", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay4", "psBM00DUTCNUM" + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "German/" + paramInt3 / 10 + ".tga", "German/" + paramInt3 % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
      changeMat(paramHierMesh, "Overlay6", "ICAFroundel2c", "British/ICAFroundel2c.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "ICAFroundel4cthin", "British/ICAFroundel4cthin.tga", 1.0F, 1.0F, 1.0F);
    }
    if (paramRegiment.country() == PaintScheme.countryPoland)
    {
      changeMat(paramHierMesh, "Overlay1", "psFM00POLCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay4", "psFM00POLCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryRomania)
    {
      changeMat(paramHierMesh, "Overlay8", "psFM00ROMCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryBulgaria)
    {
      changeMat(paramHierMesh, "Overlay8", "psFB00ROMCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "bulgaria", "Bulgaria/insignia.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "bulgaria", "Bulgaria/insignia.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryRomaniaAl)
    {
      changeMat(paramHierMesh, "Overlay8", "psFB00ROMCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "romaniaroundel", "Romanian/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "romaniaroundel", "Romanian/roundel.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryBulgariaAl)
    {
      changeMat(paramHierMesh, "Overlay8", "psFB00ROMCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "Russian/1" + paramInt3 / 10 + ".tga", "Russian/1" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "bulgariaroundel", "Bulgaria/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "bulgariaroundel", "Bulgaria/roundel.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryRussia)
    {
      float f = World.cur().camouflage == 1 ? 0.0F : 1.0F;
      if (paramInt3 < 10)
      {
        changeMat(paramHierMesh, "Overlay1", "psFM00RUSLNUM" + i + paramInt1 + "0" + paramInt3, "Russian/0" + paramInt3 + "2.tga", f, f, f);
        changeMat(paramHierMesh, "Overlay4", "psFM00RUSLNUM" + i + paramInt1 + "0" + paramInt3, "Russian/0" + paramInt3 + "2.tga", f, f, f);
      }
      else {
        changeMat(paramHierMesh, "Overlay1", "psFM00RUSCNUM" + i + paramInt1 + paramInt3, "Russian/0" + paramInt3 / 10 + ".tga", "Russian/0" + paramInt3 % 10 + ".tga", f, f, f, f, f, f);
        changeMat(paramHierMesh, "Overlay4", "psFM00RUSCNUM" + i + paramInt1 + paramInt3, "Russian/0" + paramInt3 / 10 + ".tga", "Russian/0" + paramInt3 % 10 + ".tga", f, f, f, f, f, f);
      }
      changeMat(paramHierMesh, "Overlay6", "redstar0", "Russian/redstar0.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "redstar0", "Russian/redstar0.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay8", "redstar0", "Russian/redstar0.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryNewZealand)
    {
      paramInt3 = clampToLiteral(paramInt3);
      changeMat(paramHierMesh, "Overlay2", "psFM00RZLNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay3", "psFM00RZRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishWhiteColor[0], PaintScheme.psBritishWhiteColor[1], PaintScheme.psBritishWhiteColor[2]);
      changeMat(paramHierMesh, "Overlay6", "newzealand6", "NewZealand/newzealand6.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "newzealand7", "NewZealand/newzealand7.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countrySouthAfrica)
    {
      paramInt3 = clampToLiteral(paramInt3);
      changeMat(paramHierMesh, "Overlay1", "psFM04BRIREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2], PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2]);
      changeMat(paramHierMesh, "Overlay3", "psFM04BRIREGI" + paramRegiment.id(), "British/" + paramRegiment.aid()[0] + ".tga", "British/" + paramRegiment.aid()[1] + ".tga", PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2], PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2]);
      changeMat(paramHierMesh, "Overlay2", "psFM04BRILNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "British/" + (char)(65 + paramInt3 - 1) + ".tga", "null.tga", PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2], 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay4", "psFM04BRIRNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "null.tga", "British/" + (char)(65 + paramInt3 - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishGrayColor[0], PaintScheme.psBritishGrayColor[1], PaintScheme.psBritishGrayColor[2]);
      changeMat(paramHierMesh, "Overlay6", "SAAFroundel2c", "British/SAAFroundel2c.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "SAAFroundel4cthin", "British/SAAFroundel4cthin.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countrySlovakia)
    {
      if (paramInt3 < 10)
      {
        changeMat(paramHierMesh, "Overlay2", "psFM00SLVKLNUM" + i + paramInt1 + "0" + paramInt3, "Finnish/" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFM00SLVKRNUM" + i + paramInt1 + "0" + paramInt3, "null.tga", "Finnish/" + paramInt3 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      else {
        changeMat(paramHierMesh, "Overlay2", "psFM00SLVKCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
        changeMat(paramHierMesh, "Overlay3", "psFM00SLVKCNUM" + i + paramInt1 + paramInt3, "Finnish/" + paramInt3 / 10 + ".tga", "Finnish/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      }
      changeMat(paramHierMesh, "Overlay6", "slovakiancross1", "Slovakian/cross1.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryIsrael)
    {
      changeMat(paramHierMesh, "Overlay1", "psBM00SKCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay4", "psBM00SKCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "StarDavid", "Israel/StarofDavid.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "StarDavid", "Israel/StarofDavid.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryBrazil)
    {
      changeMat(paramHierMesh, "Overlay1", "psBM00SKCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay4", "psBM00SKCNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "Brazilroundel", "Brazil/roundel.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "Brazilroundel", "Brazil/roundel.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
    if (paramRegiment.country() == PaintScheme.countryUSA)
    {
      changeMat(paramHierMesh, "Overlay1", "psBM00USACNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay4", "psBM00USACNUM" + i + paramInt1 + (paramInt3 < 10 ? "0" + paramInt3 : new StringBuffer().append("").append(paramInt3).toString()), "States/" + paramInt3 / 10 + ".tga", "States/" + paramInt3 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "whitestarEarly", "States/whitestarEarly.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "whitestarEarly", "States/whitestarEarly.tga", 1.0F, 1.0F, 1.0F);
      return;
    }
  }
}