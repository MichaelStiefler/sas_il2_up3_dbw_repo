package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.engine.HierMesh;

public class PaintSchemeControlFM extends PaintScheme
{

    public PaintSchemeControlFM()
    {
    }

    public java.lang.String typedNameNum(java.lang.Class class1, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        if(regiment.country() == PaintScheme.countryVietnamNorth)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryIndonesia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryMalaysia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countrySingapore)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryGermanyBlue)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryVietnamSouth)
            return "" + (k >= 10 ? "" + k : "0" + k) + "*";
        if(regiment.country() == PaintScheme.countryAustralia)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == PaintScheme.countryNewZeeland)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == PaintScheme.countryBritainBlue)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == PaintScheme.countryBritainBlue)
            return "" + regiment.id() + " + " + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == PaintScheme.countryItaly)
            return "" + k;
        if(regiment.country() == PaintScheme.countryJapan)
            return "" + k;
        if(regiment.country() == PaintScheme.countryPoland)
            return "" + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == PaintScheme.countryRomania)
            return "+ " + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == PaintScheme.countryRussia)
            return "* " + PaintScheme.psRussianBomberString[i] + " " + k;
        if(regiment.country() == PaintScheme.countryNewZealand)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == PaintScheme.countryUSABlue)
            return "" + (k >= 10 ? "" + k : "0" + k) + "*";
        if(regiment.country() == PaintScheme.countrySwitzerland)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryChina)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countrySpainRep)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countrySpainNat)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryIsrael)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryIsraelBlue)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryCanadaBlue)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == PaintScheme.countryItalyAllied)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryRomaniaAllied)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryFinlandAllied)
            return PaintScheme.psFinnishFighterString[1][i] + k;
        if(regiment.country() == PaintScheme.countryBelgium)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryBulgaria)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryNorway)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryGreece)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryBrazil)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countrySweden)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countrySouthAfrica)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == PaintScheme.countryYugoslavia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryYugoPar)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryDenmark)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryPhilippines)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryCroatia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryBulgariaAllied)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryThailandBlue)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryThailand)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryGDR)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryHungaryAllied)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryEgypt)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryEgyptRed)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryCzechoslovakia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryIran)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryChinaRed)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryNorthKorea)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countrySouthKorea)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryNorthKoreaRed)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countrySouthKoreaBlue)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countrySyria)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countrySyriaRed)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryItalyANR)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == PaintScheme.countryVichyFrance)
            return "" + (k < 10 ? "0" + k : "" + k);
        else
            return super.typedNameNum(class1, regiment, i, j, k);
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == PaintScheme.countryVietnamNorth)
        {
            changeMat(hiermesh, "Overlay4", "psBM00NVGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", PaintScheme.psBritishRedColor[0], PaintScheme.psBritishRedColor[1], PaintScheme.psBritishRedColor[2], PaintScheme.psBritishRedColor[0], PaintScheme.psBritishRedColor[1], PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay1", "psBM00NVGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", PaintScheme.psBritishRedColor[0], PaintScheme.psBritishRedColor[1], PaintScheme.psBritishRedColor[2], PaintScheme.psBritishRedColor[0], PaintScheme.psBritishRedColor[1], PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "VietnamNorthWing", "VietnamNorth/NVWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "VietnamNorthRoundel", "VietnamNorth/NVRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "VietnamNorthTail", "VietnamNorth/NVTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryIndonesia)
        {
            changeMat(hiermesh, "Overlay3", "psBM00DOGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00DOGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "IndonesiaWing", "Indonesia/DOWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "IndonesiaRoundel", "Indonesia/DORoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "IndonesiaTail", "Indonesia/DOTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryMalaysia)
        {
            changeMat(hiermesh, "Overlay3", "psBM00MYGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00MYGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "MalaysiaWing", "Malaysia/MYWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "MalaysiaRoundel", "Malaysia/MYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "MalaysiaTail", "Malaysia/MYTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countrySingapore)
        {
            changeMat(hiermesh, "Overlay3", "psBM00SIGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00SIGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "SingaporeWing", "Singapore/SIWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SingaporeRoundel", "Singapore/SIRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "SingaporeTail", "Singapore/SITail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryGermanyBlue)
        {
            changeMat(hiermesh, "Overlay1", "psFM03GPNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "psFM03GPNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psFM03GPCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psFM03GPCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "GermanyPostWings", "GermanyPost/GPWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "GermanyPostRoundel", "GermanyPost/GPRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "GermanyPostTail", "GermanyPost/GPTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryVietnamSouth)
        {
            changeMat(hiermesh, "Overlay1", "psBM0JVNACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM0JVNACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "VietnamSouthWing", "VietnamSouth/VNWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "VietnamSouthRoundel", "VietnamSouth/VNRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "VietnamSouthTail", "VietnamSouth/VNTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryAustralia)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "SAASRegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "SAASRegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "ABritishLnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "ABritishRnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "AustraliaWings", "Australia/ASWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "AustraliaRoundel", "Australia/ASRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "AustraliaTail", "Australia/ASTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryNewZeeland)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "NZSARegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "NZSARegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "NZBritishLnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "NZBritishRnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "NewZeelandWings", "NewZeeland/NZWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "NewZeelandRoundel", "NewZeeland/NZRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "NewZeelandTail", "NewZeeland/NZTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryBritainBlue)
        {
            k = clampToLiteral(k);
            changeMat(class1, hiermesh, "Overlay8", "BBRitTail", "British/CATail.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay1", "psbBM00BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psbBM00BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psbBM00BRILNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "psbBM00BRIRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "britishroundel4c", "British/roundel4c.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryUSABlue)
        {
            changeMat(hiermesh, "Overlay1", "psBM00SKCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM00SKCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countrySwitzerland)
        {
            changeMat(class1, hiermesh, "Overlay6", "CHWings", "CH/CHWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CHRoundel", "CH/CHRoundel.tga", 1.0F, 1.0F, 1.0F);
            java.lang.String s = getCHFMCode(class1);
            changeMat(hiermesh, "Overlay2", "CHCode_" + s + (k >= 10 ? "" + k : "0" + k), "Swiss/" + s + ".tga", "Swiss/" + k % 10 + ".tga", 0.95F, 0.95F, 0.95F, 0.95F, 0.95F, 0.95F);
            changeMat(hiermesh, "Overlay3", "CHCode_" + s + (k >= 10 ? "" + k : "0" + k), "Swiss/" + s + ".tga", "Swiss/" + k % 10 + ".tga", 0.95F, 0.95F, 0.95F, 0.95F, 0.95F, 0.95F);
            return;
        }
        if(regiment.country() == PaintScheme.countryChina)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "CNRoundel", "CN/CNRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CNRoundel", "CN/CNRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countrySpainRep)
        {
            if(k > 9)
            {
                if(k > 19)
                {
                    changeMat(class1, hiermesh, "Overlay2", "ESNum_" + regiment.id() + 17 + k % 10, "Spanish/" + regiment.id() + 17 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay2", "ESNum_" + regiment.id() + 17 + k % 10, "Spanish/" + regiment.id() + 17 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                } else
                {
                    changeMat(class1, hiermesh, "Overlay2", "ESNum_" + regiment.id() + 16 + k % 10, "Spanish/" + regiment.id() + 16 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay3", "ESNum_" + regiment.id() + 16 + k % 10, "Spanish/" + regiment.id() + 16 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                }
            } else
            {
                changeMat(class1, hiermesh, "Overlay2", "ESNum_" + regiment.id() + 15 + k, "Spanish/" + regiment.id() + 15 + k + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay3", "ESNum_" + regiment.id() + 15 + k, "Spanish/" + regiment.id() + 15 + k + ".tga", 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "ESRoundel", "ES/ESRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ESRoundel", "ES/ESRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countrySpainNat)
        {
            int i1 = java.lang.Integer.parseInt(regiment.id());
            changeMat(hiermesh, "Overlay1", "BlackNum_00" + (i1 >= 10 ? "" + i1 : "0" + i1), "Default/1" + i1 / 10 + ".tga", "Default/1" + i1 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BlackNum_00" + (i1 >= 10 ? "" + i1 : "0" + i1), "Default/1" + i1 / 10 + ".tga", "Default/1" + i1 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "SPWings", "SP/SPWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SPRoundel", "SP/SPRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryIsrael)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "ISRoundel", "IS/ISRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ISRoundel", "IS/ISRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryIsraelBlue)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "ISRoundel", "IS/ISRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ISRoundel", "IS/ISRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryCanadaBlue)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "CABRegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "CABRegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "CBritishLnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "CBritishRnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "CARoundel", "CA/CARoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CARoundel", "CA/CARoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "CATail", "CA/CATail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryItalyAllied)
        {
            changeMat(hiermesh, "Overlay1", "ITLDefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "IA/1" + k / 10 + ".tga", "IA/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "ITLDefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "IA/1" + k / 10 + ".tga", "IA/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "ILRoundel", "IL/ILRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ILRoundel", "IL/ILRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryRomaniaAllied)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "RLRoundel", "RL/RLRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "RLRoundel", "RL/RLRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryFinlandAllied)
        {
            char c = (char)(48 + k % 10);
            changeMat(class1, hiermesh, "Overlay6", "FLRoundel", "FL/FLRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "FLRoundel", "FL/FLRoundel.tga", 1.0F, 1.0F, 1.0F);
            if(i == 3)
                changeMat(class1, hiermesh, "Overlay8", "psFM00FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", PaintScheme.psFinnishFighterColor[i][0], PaintScheme.psFinnishFighterColor[i][1], PaintScheme.psFinnishFighterColor[i][2]);
            else
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay8", "psFM00FINCNUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", PaintScheme.psFinnishFighterColor[i][0], PaintScheme.psFinnishFighterColor[i][1], PaintScheme.psFinnishFighterColor[i][2]);
            else
                changeMat(hiermesh, "Overlay8", "psFM00FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", PaintScheme.psFinnishFighterColor[i][0], PaintScheme.psFinnishFighterColor[i][1], PaintScheme.psFinnishFighterColor[i][2], PaintScheme.psFinnishFighterColor[i][0], PaintScheme.psFinnishFighterColor[i][1], PaintScheme.psFinnishFighterColor[i][2]);
            java.lang.String s1 = getFAFACCode(class1, i);
            changeMat(hiermesh, "Overlay2", "psFM00FINACID" + s1 + c, "Finnish/" + s1 + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psFM00FINACID" + s1 + c, "Finnish/" + s1 + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryBelgium)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "BERoundel", "BE/BERoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BERoundel", "BE/BERoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "BETail", "BE/BETail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryBulgaria)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "BGRoundelEarly", "BG/BGRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BGRoundelEarly", "BG/BGRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryNorway)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "NORoundelEarly", "NO/NORoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "NORoundelEarly", "NO/NORoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryGreece)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "GRRoundel", "GR/GRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "GRRoundel", "GR/GRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "GRTail", "GR/GRTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryBrazil)
        {
            changeMat(class1, hiermesh, "Overlay6", "BRRoundel", "BR/BRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BRRoundel", "BR/BRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countrySweden)
        {
            int j1 = java.lang.Integer.parseInt(regiment.id());
            changeMat(hiermesh, "Overlay2", "BlackNum_00" + (j1 >= 10 ? "" + j1 : "0" + j1), "Default/1" + j1 / 10 + ".tga", "Default/1" + j1 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_00" + (j1 >= 10 ? "" + j1 : "0" + j1), "Default/1" + j1 / 10 + ".tga", "Default/1" + j1 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "SWRoundel", "SW/SWRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SWRoundel", "SW/SWRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countrySouthAfrica)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "SARegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2], PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2]);
            changeMat(hiermesh, "Overlay3", "SARegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2], PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2]);
            changeMat(hiermesh, "Overlay2", "BritishLnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2], 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BritishRnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, PaintScheme.psBritishSkyColor[0], PaintScheme.psBritishSkyColor[1], PaintScheme.psBritishSkyColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "SAWings", "SA/SAWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SARoundel", "SA/SARoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "SATail", "SA/SATail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryYugoslavia)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "YURoundel", "YU/YURoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "YURoundel", "YU/YURoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "YUTail", "YU/YUTailEarly.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryYugoPar)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "YUPRoundel", "YU/YURoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "YUPARoundel", "YU/YURoundelAlt.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "YUPATail", "YU/YUTailAlternative.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryDenmark)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "DKRoundel", "DK/DKRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "DKRoundel", "DK/DKRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "DKTail", "DK/DKTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryPhilippines)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "PHRoundelEarly", "PH/PHRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "PHRoundelEarly", "PH/PHRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryCroatia)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "HRRoundel", "HR/HRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "HRRoundel", "HR/HRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "HRTail", "HR/HRTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryBulgariaAllied)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BLRoundel", "BL/BLRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryThailand)
        {
            changeMat(class1, hiermesh, "Overlay1", "THArt", "TH/THArt.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay4", "THArt", "TH/THArt.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "THRoundelEarly", "TH/THRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "THRoundelEarly", "TH/THRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryThailandBlue)
        {
            changeMat(class1, hiermesh, "Overlay1", "THArt", "TH/THArt.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay4", "THArt", "TH/THArt.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "THRoundel", "TH/THRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "THRoundel", "TH/THRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryGDR)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "DDRoundel", "DD/DDRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "DDRoundel", "DD/DDRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "DDRoundel", "DD/DDRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryHungaryAllied)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "HYRoundel", "HY/HYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "HYRoundel", "HY/HYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "HYTail", "HY/HYRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryEgypt)
        {
            changeMat(hiermesh, "Overlay2", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "EGRoundel", "EG/EGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "EGRoundel", "EG/EGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "EGTail", "EG/EGTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryEgyptRed)
        {
            changeMat(hiermesh, "Overlay2", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "EGRoundel", "EG/EGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "EGRoundel", "EG/EGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "EGTail", "EG/EGTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryCzechoslovakia)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay7", "SAASczLRegi_" + regiment.id(), "CZ/" + regiment.aid()[0] + ".tga", "CZ/" + regiment.aid()[1] + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay1", "DefaultNumCZ_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "DefaultNumCZ_" + l + i + (k >= 10 ? "" + k : "0" + k), "Slovakian/" + k / 10 + ".tga", "Slovakian/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "CZRoundel", "CZ/CZRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "CZTail", "CZ/CZTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryIran)
        {
            changeMat(hiermesh, "Overlay2", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "IRRoundel", "IR/IRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "IRRoundel", "IR/IRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "IRTail", "IR/IRTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryChinaRed)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "CCRoundel", "CC/CCRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CCRoundel", "CC/CCRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryNorthKorea)
        {
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countrySouthKorea)
        {
            changeMat(hiermesh, "Overlay1", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryNorthKoreaRed)
        {
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countrySouthKoreaBlue)
        {
            changeMat(hiermesh, "Overlay1", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countrySyria)
        {
            changeMat(class1, hiermesh, "Overlay6", "SYRoundel", "SY/SYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SYRoundel", "SY/SYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "SYTailNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countrySyriaRed)
        {
            changeMat(class1, hiermesh, "Overlay6", "SYRoundel", "SY/SYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SYRoundel", "SY/SYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "SYTailNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryItalyANR)
        {
            changeMat(hiermesh, "Overlay1", "ANRDefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "IA/1" + k / 10 + ".tga", "IA/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "ANRDefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "IA/1" + k / 10 + ".tga", "IA/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "IATail1", "IA/IATail.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "IATail1", "IA/IATail.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "IAWings", "IA/IAWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "IARoundel", "IA/IARoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "IATail2", "IA/IATail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == PaintScheme.countryVichyFrance)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "FRRoundel", "FR/FRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "FRRoundel", "FR/FRRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        } else
        {
            return;
        }
    }
}
