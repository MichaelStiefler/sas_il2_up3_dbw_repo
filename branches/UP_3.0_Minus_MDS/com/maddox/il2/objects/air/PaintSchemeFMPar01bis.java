// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   PaintSchemeFMPar01bis.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;

// Referenced classes of package com.maddox.il2.objects.air:
//            PaintScheme

public class PaintSchemeFMPar01bis extends com.maddox.il2.objects.air.PaintScheme
{

    public PaintSchemeFMPar01bis()
    {
    }

    public java.lang.String typedNameNum(java.lang.Class class1, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGermany)
            if(k <= 1 && i == 0)
                return "< + " + com.maddox.il2.objects.air.PaintScheme.psGermanFighterGruppeChar[0][l];
            else
                return com.maddox.il2.objects.air.PaintScheme.psGermanFighterString[0][i] + k + " + " + com.maddox.il2.objects.air.PaintScheme.psGermanFighterGruppeChar[0][l];
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNetherlands)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySouthAfricaS)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySouthAfricaB)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryAngola)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryMozambique)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGenericBlue)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGenericRed)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryAlbania)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCuba)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryVietnamNorth)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryChile)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryMyanmar)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNigeria)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryElSalvador)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryAlgeria)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCambodia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryEritrea)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGuatemala)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryHonduras)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIndonesia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIreland)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryLibya)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryMalaysia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySingapore)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryVenezuala)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGermanyBlue)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryEthiopia)
            return "" + (k >= 10 ? "" + k : "0" + k) + "*";
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryMorocco)
            return "" + (k >= 10 ? "" + k : "0" + k) + "*";
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNicaragua)
            return "" + (k >= 10 ? "" + k : "0" + k) + "*";
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPanama)
            return "" + (k >= 10 ? "" + k : "0" + k) + "*";
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryVietnamSouth)
            return "" + (k >= 10 ? "" + k : "0" + k) + "*";
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJordan)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryLebanon)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySaudiArabia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBiafra)
            return "o " + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBorduria)
            return "o " + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCostaRica)
            return "o " + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCostaVerde)
            return "o " + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySylvadia)
            return "o " + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryAustralia)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNewZeeland)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBotswana)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUganda)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinland)
            if(i == 3)
                return com.maddox.il2.objects.air.PaintScheme.psFinnishFighterString[0][i] + (char)(65 + (k % 10 - 1));
            else
                return com.maddox.il2.objects.air.PaintScheme.psFinnishFighterString[0][i] + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFrance)
            return "o " + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritainBlue)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
            return "" + regiment.id() + " + " + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritainBlue)
            return "" + regiment.id() + " + " + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItaly)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJapan)
            return "" + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPoland)
            return "" + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRomania)
            return "+ " + (k >= 10 ? "" + k : "0" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
            return "" + k + " *";
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNewZealand)
        {
            k = clampToLiteral(k);
            return "" + (char)(65 + (k - 1));
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySlovakia)
            return "+ " + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSA)
            return "" + (k >= 10 ? "" + k : "0" + k) + "*";
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSABlue)
            return "" + (k >= 10 ? "" + k : "0" + k) + "*";
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySwitzerland)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryChina)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySpainRep)
            return "" + regiment.id() + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryManagua)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySpainNat)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIsrael)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIsraelBlue)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCanada)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCanadaBlue)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItalyAllied)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRomaniaAllied)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinlandAllied)
            if(i == 3)
                return com.maddox.il2.objects.air.PaintScheme.psFinnishFighterString[0][i] + (char)(65 + (k % 10 - 1));
            else
                return com.maddox.il2.objects.air.PaintScheme.psFinnishFighterString[0][i] + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBelgium)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBulgaria)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNorway)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGreece)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBrazil)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySweden)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySouthAfrica)
        {
            k = clampToLiteral(k);
            return "" + regiment.id() + " - " + (char)(65 + (k - 1));
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryYugoslavia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryYugoPar)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryDenmark)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPhilippines)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCroatia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBulgariaAllied)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryThailand)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryThailandBlue)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussianLiberationArmy)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryManchukuo)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussianEmpire)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGDR)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBolivia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryAbyssinia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryMexico)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUruguay)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryHungaryAllied)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryTurkey)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIraq)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryEgypt)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryEgyptRed)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryParaguay)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryArgentina)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCzechoslovakia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPolandAxis)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPortugal)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIran)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPRC)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryChinaRed)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNorthKorea)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySouthKorea)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNorthKoreaRed)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySouthKoreaBlue)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryEstonia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryLatvia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryLithuania)
            return com.maddox.il2.objects.air.PaintScheme.psFinnishFighterString[1][i] + k;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPeru)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryColombia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySyria)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySyriaRed)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryAustria)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItalyANR)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryVichyFrance)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIndia)
            return "" + (k < 10 ? "0" + k : "" + k);
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPakistan)
            return "" + (k < 10 ? "0" + k : "" + k);
        else
            return super.typedNameNum(class1, regiment, i, j, k);
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        super.prepareNum(class1, hiermesh, regiment, i, j, k);
        int l = regiment.gruppeNumber() - 1;
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGermany)
        {
            int i1 = i + 1;
            if(i1 == 4)
                i1 = 0;
            int l1 = regiment.gruppeNumber();
            changeMat(class1, hiermesh, "Overlay6", "balken0", "German/balken0.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "balken1", "German/balken1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "haken1", "German/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "haken1.tga" : "hakenfake.tga"), 1.0F, 1.0F, 1.0F);
            if(k <= 1 && i == 0)
            {
                changeMat(class1, hiermesh, "Overlay1", "psFM01GERCOML0000", "German/00c.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay4", "psFM01GERCOMR0000", "German/00c2.tga", 1.0F, 1.0F, 1.0F);
                if(l1 > 1 && l1 < 5)
                {
                    changeMat(class1, hiermesh, "Overlay2", "psFM01GERCOMCGID" + l1, "German/00cG" + l1 + ".tga", 1.0F, 1.0F, 1.0F);
                    changeMat(class1, hiermesh, "Overlay3", "psFM01GERCOMCGID" + l1, "German/00cG" + l1 + ".tga", 1.0F, 1.0F, 1.0F);
                }
                return;
            }
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay1", "psFM01GERLNUM" + l + i + k, "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psFM01GERRNUM" + l + i + k, "null.tga", "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay1", "psFM01GERCNUM" + l + i + k, "German/0" + i1 + k / 10 + ".tga", "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psFM01GERCNUM" + l + i + k, "German/0" + i1 + k / 10 + ".tga", "German/0" + i1 + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            if(l1 > 1 && l1 < 5)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFM00GERGRUPP" + l1 + "S" + i1, "German/0" + i1 + "0G" + l1 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay3", "psFM00GERGRUPP" + l1 + "S" + i1, "German/0" + i1 + "0G" + l1 + ".tga", 1.0F, 1.0F, 1.0F);
            }
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNetherlands)
        {
            changeMat(class1, hiermesh, "Overlay6", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "DutchTriangle", "Dutch/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay1", "psBM0PDUTCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBM0PDUTCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySouthAfricaS)
        {
            changeMat(class1, hiermesh, "Overlay6", "SSWings", "SA/SSWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SSRoundel", "SA/SSRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "SATail", "SA/SATail.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "psFM01SASSNUM" + (k < 10 ? "0" + k : "" + k), "SAPRE/" + k / 10 + ".tga", "SA/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psFM01SASSNUM" + (k < 10 ? "0" + k : "" + k), "SAPRE/" + k / 10 + ".tga", "SA/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySouthAfricaB)
        {
            changeMat(class1, hiermesh, "Overlay6", "SBWing", "SA/SBWing.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SBRoundel", "SA/SBRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "psFM01SASBNUM" + (k < 10 ? "0" + k : "" + k), "SAPRE/" + k / 10 + ".tga", "SA/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psFM01SASBNUM" + (k < 10 ? "0" + k : "" + k), "SAPRE/" + k / 10 + ".tga", "SA/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryAngola)
        {
            changeMat(hiermesh, "Overlay3", "psFM01ANGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay2", "psFM01ANGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "AngolaWing", "AN/AngolaWing.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "AngolaRoundel", "AN/AngolaRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "AngolaTail", "AN/AngolaTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryMozambique)
        {
            changeMat(hiermesh, "Overlay3", "psFM01MZBCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay2", "psFM01MZBCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "MozWing", "MZ/MozWing.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "MozRoundel", "MZ/MozRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "MozTail", "MZ/MozTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryAlbania)
        {
            changeMat(hiermesh, "Overlay4", "psBM00ALGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay1", "psBM00ALGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "AlbaniaWing", "Albania/ALWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "AlbaniaRoundel", "Albania/ALRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "AlbaniaTail", "Albania/ALTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCuba)
        {
            changeMat(hiermesh, "Overlay4", "psBM00CUGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay1", "psBM00CUGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "CubaWing", "Cuba/CUWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CubaRoundel", "Cuba/CURoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "CubaTail", "Cuba/CUTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryVietnamNorth)
        {
            changeMat(hiermesh, "Overlay4", "psBM00NVGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay1", "psBM00NVGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "VietnamNorthWing", "VietnamNorth/NVWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "VietnamNorthRoundel", "VietnamNorth/NVRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "VietnamNorthTail", "VietnamNorth/NVTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryChile)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "ChileRoundel", "Chile/CIWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ChileRoundel", "Chile/CIRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "ChileTail", "Chile/CITail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryElSalvador)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "ElSalvadorRoundel", "ElSalvador/ELWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ElSalvadorRoundel", "ElSalvador/ELRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "ElSalvadorTail", "ElSalvador/ELTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryMyanmar)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "MyanmarRoundel", "Myanmar/MMWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "MyanmarRoundel", "Myanmar/MMRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "MyanmarTail", "Myanmar/MMTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNigeria)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "NigeriaRoundel", "Nigeria/NGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "NigeriaRoundel", "Nigeria/NGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "NigeriaTail", "Nigeria/NGTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryAlgeria)
        {
            changeMat(hiermesh, "Overlay3", "psBM00AGGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00AGGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "AlgeriaWing", "Algeria/AGWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "AlgeriaRoundel", "Algeria/AGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "AlgeriaTail", "Algeria/AGTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCambodia)
        {
            changeMat(hiermesh, "Overlay3", "psBM00CMGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00CMGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "CambodiaWing", "Cambodia/CMWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CambodiaRoundel", "Cambodia/CMRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "CambodiaTail", "Cambodia/CMTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryEritrea)
        {
            changeMat(hiermesh, "Overlay3", "psBM00ETGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00ETGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "EritreaWing", "Eritrea/ETWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "EritreaRoundel", "Eritrea/ETRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "EritreaTail", "Eritrea/ETTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGuatemala)
        {
            changeMat(hiermesh, "Overlay3", "psBM00GLGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00GLGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "GuatemalaWing", "Guatemala/GLWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "GuatemalaRoundel", "Guatemala/GLRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "GuatemalaTail", "Guatemala/GLTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryHonduras)
        {
            changeMat(hiermesh, "Overlay3", "psBM00HOGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00HOGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "HondurasWing", "Honduras/HOWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "HondurasRoundel", "Honduras/HORoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "HondurasTail", "Honduras/HOTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIndonesia)
        {
            changeMat(hiermesh, "Overlay3", "psBM00DOGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00DOGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "IndonesiaWing", "Indonesia/DOWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "IndonesiaRoundel", "Indonesia/DORoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "IndonesiaTail", "Indonesia/DOTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIreland)
        {
            changeMat(hiermesh, "Overlay3", "psBM00ADGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00ADGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "IrelandWing", "Ireland/ADWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "IrelandRoundel", "Ireland/ADRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "IrelandTail", "Ireland/ADTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryLibya)
        {
            changeMat(hiermesh, "Overlay3", "psBM00LYGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00LYGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "LibyaWing", "Libya/LYWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "LibyaRoundel", "Libya/LYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "LibyaTail", "Libya/LYTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryMalaysia)
        {
            changeMat(hiermesh, "Overlay3", "psBM00MYGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00MYGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "MalaysiaWing", "Malaysia/MYWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "MalaysiaRoundel", "Malaysia/MYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "MalaysiaTail", "Malaysia/MYTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySingapore)
        {
            changeMat(hiermesh, "Overlay3", "psBM00SIGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00SIGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "SingaporeWing", "Singapore/SIWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SingaporeRoundel", "Singapore/SIRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "SingaporeTail", "Singapore/SITail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryVenezuala)
        {
            changeMat(hiermesh, "Overlay3", "psBM00VZGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00VZGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "VenezualaWing", "Venezuela/VZWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "VenezualaRoundel", "Venezuela/VZRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "VenezualaTail", "Venezuela/VZTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGermanyBlue)
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
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryEthiopia)
        {
            changeMat(hiermesh, "Overlay1", "psBM0JEHACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBM0JEHACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "EthiopiaWing", "Ethiopia/EHWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "EthiopiaRoundel", "Ethiopia/EHRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "EthiopiaTail", "Ethiopia/EHTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryMorocco)
        {
            changeMat(hiermesh, "Overlay1", "psBM0JMRACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBM0JMRACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "MoroccoWing", "Morocco/MRWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "MoroccoRoundel", "Morocco/MRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "MoroccoTail", "Morocco/MRTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNicaragua)
        {
            changeMat(hiermesh, "Overlay1", "psBM0JNIACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM0JNIACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "NicaraguaWing", "Nicaragua/NIWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "NicaraguaRoundel", "Nicaragua/NIRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "NicaraguaTail", "Nicaragua/NITail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPanama)
        {
            changeMat(hiermesh, "Overlay1", "psBM0JPMACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM0JPMACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "PanamaWing", "Panama/PMWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "PanamaRoundel", "Panama/PMRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "PanamaTail", "Panama/PMTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryVietnamSouth)
        {
            changeMat(hiermesh, "Overlay1", "psBM0JVNACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM0JVNACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "VietnamSouthWing", "VietnamSouth/VNWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "VietnamSouthRoundel", "VietnamSouth/VNRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "VietnamSouthTail", "VietnamSouth/VNTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJordan)
        {
            changeMat(hiermesh, "Overlay2", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "JordanRoundel", "Jordan/JORoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "JordanRoundel", "Jordan/JORoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "JordanTail", "Jordan/JOTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryLebanon)
        {
            changeMat(hiermesh, "Overlay2", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "LebanonRoundel", "Lebanon/LERoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "LebanonRoundel", "Lebanon/LERoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "LebanonTail", "Lebanon/LETail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySaudiArabia)
        {
            changeMat(hiermesh, "Overlay2", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "SaudiRoundel", "Saudi/SURoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SaudiRoundel", "Saudi/SURoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "SaudiTail", "Saudi/SUTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBiafra)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFM03BIALNUM" + l + i + k, "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM03BIARNUM" + l + i + k, "null.tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFM03BIACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM03BIACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "BiafraWings", "Biafra/BIWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BiafraRoundel", "Biafra/BIRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "BiafraTail", "Biafra/BITail.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBorduria)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFM03BDALNUM" + l + i + k, "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM03BDARNUM" + l + i + k, "null.tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFM03BDACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM03BDACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "BorduriaWings", "Borduria/BDWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BorduriaRoundel", "Borduria/BDRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "BorduriaTail", "Borduria/BDTail.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCostaRica)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFM03CRALNUM" + l + i + k, "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM03CRARNUM" + l + i + k, "null.tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFM03CRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM03CRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "CostaRicaWings", "CostaRica/CRWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CostaRicaRoundel", "CostaRica/CRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "CostaRicaTail", "CostaRica/CRTail.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCostaVerde)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFM03CVALNUM" + l + i + k, "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM03CVARNUM" + l + i + k, "null.tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFM03CVACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM03CVACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "CostaVerdeWings", "CostaVerde/CVWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CostaVerdeRoundel", "CostaVerde/CVRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "CostaVerdeTail", "CostaVerde/CVTail.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySylvadia)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFM03SLALNUM" + l + i + k, "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM03SLARNUM" + l + i + k, "null.tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFM03SLACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM03SLACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "SylvadiaWings", "Sylvadia/SLWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SylvadiaRoundel", "Sylvadia/SLRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "SylvadiaTail", "Sylvadia/SLTail.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryAustralia)
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
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNewZeeland)
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
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBotswana)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "BTSARegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "BTSARegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "BTBritishLnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "BTBritishRnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "BotswanaWings", "Botswana/BTWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BotswanaRoundel", "Botswana/BTRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "BotswanaTail", "Botswana/BTTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUganda)
        {
            changeMat(hiermesh, "Overlay4", "psBM00UGGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishOrangeColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishOrangeColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishOrangeColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishOrangeColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishOrangeColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishOrangeColor[2]);
            changeMat(hiermesh, "Overlay1", "psBM00UGGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishOrangeColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishOrangeColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishOrangeColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishOrangeColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishOrangeColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishOrangeColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "UgandaWing", "Uganda/UGWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "UgandaRoundel", "Uganda/UGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "UgandaTail", "Uganda/UGTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinland)
        {
            char c = (char)(48 + k % 10);
            changeMat(class1, hiermesh, "Overlay6", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "FAFhaken", "Finnish/" + (com.maddox.il2.ai.World.cur().isHakenAllowed() ? "FAFhaken.tga" : "FAFroundel.tga"), 1.0F, 1.0F, 1.0F);
            if(i == 3)
                changeMat(class1, hiermesh, "Overlay8", "psFM01FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay8", "psFM01FINCNUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
                changeMat(hiermesh, "Overlay8", "psFM01FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            java.lang.String s1 = getFAFACCode(class1, i);
            changeMat(hiermesh, "Overlay2", "psFM01FINACID" + s1 + c, "Finnish/" + s1 + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psFM01FINACID" + s1 + c, "Finnish/" + s1 + ".tga", "Finnish/sn" + c + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFrance)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFM01FRALNUM" + l + i + k, "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM01FRARNUM" + l + i + k, "null.tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFM01FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM01FRACNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "frenchroundel", "French/roundel.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritain)
        {
            if("ra".equals(((java.lang.Object) (regiment.branch()))) || "rz".equals(((java.lang.Object) (regiment.branch()))) || "rn".equals(((java.lang.Object) (regiment.branch()))))
            {
                k = clampToLiteral(k);
                changeMat(hiermesh, "Overlay1", "psFM01BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                changeMat(hiermesh, "Overlay4", "psFM01BRINAVYREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                changeMat(hiermesh, "Overlay2", "psFM01BRINAVYLNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM01BRINAVYRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
                changeMat(class1, hiermesh, "Overlay6", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "britishroundel5n", "British/roundel5N.tga", 1.0F, 1.0F, 1.0F);
            } else
            {
                k = clampToLiteral(k);
                changeMat(hiermesh, "Overlay1", "psFM01BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
                changeMat(hiermesh, "Overlay3", "psFM01BRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
                changeMat(hiermesh, "Overlay2", "psFM01BRILNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psFM01BRIRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
                changeMat(class1, hiermesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);
                changeMat(class1, hiermesh, "Overlay7", "britishroundel3c", "British/roundel3c.tga", 1.0F, 1.0F, 1.0F);
            }
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBritainBlue)
        {
            k = clampToLiteral(k);
            changeMat(class1, hiermesh, "Overlay8", "BBRitTail", "British/CATail.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay1", "psBBBBRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "psBBBBRIREGI" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBBBBRILNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psBBBBRIRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "britishroundel2c", "British/roundel2c.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "britishroundel3c", "British/roundel3c.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryHungary)
        {
            changeMat(hiermesh, "Overlay1", "psFM01HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay3", "psFM01HUNREGI" + regiment.id(), "German/" + regiment.aid()[0] + ".tga", "German/" + regiment.aid()[1] + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psFM01HUNCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay4", "psFM01HUNCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "hungarianbalkenolder", "Hungarian/balkenolder.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "hungarianbalkenolder", "Hungarian/balkenolder.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItaly)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFM01ITALNUM" + l + i + k, "Italian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM01ITARNUM" + l + i + k, "null.tga", "Italian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFM01ITACNUM" + l + i + k, "Italian/1" + k / 10 + ".tga", "Italian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM01ITACNUM" + l + i + k, "Italian/1" + k / 10 + ".tga", "Italian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "italian3", "Italian/roundel0.tga", 0.1F, 0.1F, 0.1F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryJapan)
        {
            changeMat(hiermesh, "Overlay2", "psFM01JAPCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2]);
            changeMat(hiermesh, "Overlay3", "psFM01JAPCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][0], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][1], com.maddox.il2.objects.air.PaintScheme.psRussianBomberColor[0][2]);
            changeMat(class1, hiermesh, "Overlay6", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "JAR1", "Japanese/JAR.tga", 1.0F, 1.0F, 1.0F);
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPoland)
        {
            changeMat(hiermesh, "Overlay1", "psFM01POLCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psFM01POLCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "polishcheckerboard", "Polish/checkerboard.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRomania)
        {
            changeMat(hiermesh, "Overlay8", "psFM01ROMCNUM" + l + i + (k < 10 ? "0" + k : "" + k), "Russian/1" + k / 10 + ".tga", "Russian/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "romaniancross", "Romanian/insignia.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussia)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay1", "psFM01RUSLNUM" + l + i + "0" + k, "Russian/0" + k + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psFM01RUSRNUM" + l + i + "0" + k, "null.tga", "Russian/0" + k + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay1", "psFM01RUSCNUM" + l + i + k, "Russian/0" + k / 10 + ".tga", "Russian/0" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay4", "psFM01RUSCNUM" + l + i + k, "Russian/0" + k / 10 + ".tga", "Russian/0" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "redstar1", "Russian/redstar1.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNewZealand)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay2", "psFM00RZLNUM" + l + i + (k < 10 ? "0" + k : "" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2], 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "psFM00RZRNUM" + l + i + (k < 10 ? "0" + k : "" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishWhiteColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "newzealand6", "NewZealand/newzealand6.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "newzealand7", "NewZealand/newzealand7.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySlovakia)
        {
            if(k < 10)
            {
                changeMat(class1, hiermesh, "Overlay2", "psFM01SLVKLNUM" + l + i + "0" + k, "Finnish/" + k + ".tga", 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM01SLVKRNUM" + l + i + "0" + k, "null.tga", "Finnish/" + k + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            } else
            {
                changeMat(hiermesh, "Overlay2", "psFM01SLVKCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                changeMat(hiermesh, "Overlay3", "psFM01SLVKCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            changeMat(class1, hiermesh, "Overlay6", "slovakiancross1", "Slovakian/cross1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "slovakiancross2", "Slovakian/cross2.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSA)
        {
            changeMat(hiermesh, "Overlay1", "psBM0PUSACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM0PUSACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUSABlue)
        {
            changeMat(hiermesh, "Overlay1", "psBM0PUSACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "psBM0PUSACNUM" + l + i + (k < 10 ? "0" + k : "" + k), "States/" + k / 10 + ".tga", "States/" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "whitestar1", "States/whitestar1.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySwitzerland)
        {
            changeMat(class1, hiermesh, "Overlay6", "CHWings", "CH/CHWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CHRoundel", "CH/CHRoundel.tga", 1.0F, 1.0F, 1.0F);
            java.lang.String s = getCHFMCode(class1);
            changeMat(hiermesh, "Overlay2", "CHCode_" + s + (k >= 10 ? "" + k : "0" + k), "Swiss/" + s + ".tga", "Swiss/" + k % 10 + ".tga", 0.95F, 0.95F, 0.95F, 0.95F, 0.95F, 0.95F);
            changeMat(hiermesh, "Overlay3", "CHCode_" + s + (k >= 10 ? "" + k : "0" + k), "Swiss/" + s + ".tga", "Swiss/" + k % 10 + ".tga", 0.95F, 0.95F, 0.95F, 0.95F, 0.95F, 0.95F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryChina)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "CNRoundel", "CN/CNRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CNRoundel", "CN/CNRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySpainRep)
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
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryManagua)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "MGRoundel", "MG/MGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "MGRoundel", "MG/MGRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySpainNat)
        {
            int j1 = java.lang.Integer.parseInt(regiment.id());
            changeMat(hiermesh, "Overlay1", "BlackNum_00" + (j1 >= 10 ? "" + j1 : "0" + j1), "Default/1" + j1 / 10 + ".tga", "Default/1" + j1 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BlackNum_00" + (j1 >= 10 ? "" + j1 : "0" + j1), "Default/1" + j1 / 10 + ".tga", "Default/1" + j1 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "SPWings", "SP/SPWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SPRoundel", "SP/SPRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIsrael)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "ISRoundel", "IS/ISRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ISRoundel", "IS/ISRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIsraelBlue)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "ISRoundel", "IS/ISRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ISRoundel", "IS/ISRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCanada)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "CARegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
            changeMat(hiermesh, "Overlay3", "CARegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
            changeMat(hiermesh, "Overlay2", "BritishLnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BritishRnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "CARoundel", "CA/CARoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CARoundel", "CA/CARoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "CATail", "CA/CATail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCanadaBlue)
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
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItalyAllied)
        {
            changeMat(hiermesh, "Overlay1", "ITLDefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "IA/1" + k / 10 + ".tga", "IA/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "ITLDefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "IA/1" + k / 10 + ".tga", "IA/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "ILRoundel", "IL/ILRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ILRoundel", "IL/ILRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRomaniaAllied)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "RLRoundel", "RL/RLRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "RLRoundel", "RL/RLRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryFinlandAllied)
        {
            char c1 = (char)(48 + k % 10);
            changeMat(class1, hiermesh, "Overlay6", "FLRoundel", "FL/FLRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "FLRoundel", "FL/FLRoundel.tga", 1.0F, 1.0F, 1.0F);
            if(i == 3)
                changeMat(class1, hiermesh, "Overlay8", "psFM01FINCNUM" + l + i + "_" + (char)(65 + (k % 10 - 1)), "Finnish/" + (char)(65 + (k % 10 - 1)) + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
            if(k < 10)
                changeMat(class1, hiermesh, "Overlay8", "psFM01FINCNUM" + l + i + "0" + k, "Finnish/0" + k + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            else
                changeMat(hiermesh, "Overlay8", "psFM01FINCNUM" + l + i + k, "Finnish/" + k / 10 + ".tga", "Finnish/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][0], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][1], com.maddox.il2.objects.air.PaintScheme.psFinnishFighterColor[i][2]);
            java.lang.String s2 = getFAFACCode(class1, i);
            changeMat(hiermesh, "Overlay2", "psFM01FINACID" + s2 + c1, "Finnish/" + s2 + ".tga", "Finnish/sn" + c1 + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psFM01FINACID" + s2 + c1, "Finnish/" + s2 + ".tga", "Finnish/sn" + c1 + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBelgium)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "BERoundel", "BE/BERoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BERoundel", "BE/BERoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "BETail", "BE/BETail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBulgaria)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "BGRoundelEarly", "BG/BGRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BGRoundelEarly", "BG/BGRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNorway)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "NORoundelEarly", "NO/NORoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "NORoundelEarly", "NO/NORoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGreece)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "GRRoundel", "GR/GRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "GRRoundel", "GR/GRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "GRTail", "GR/GRTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBrazil)
        {
            changeMat(class1, hiermesh, "Overlay6", "BRRoundel", "BR/BRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BRRoundel", "BR/BRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySweden)
        {
            int k1 = java.lang.Integer.parseInt(regiment.id());
            changeMat(hiermesh, "Overlay2", "BlackNum_00" + (k1 >= 10 ? "" + k1 : "0" + k1), "Default/1" + k1 / 10 + ".tga", "Default/1" + k1 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_00" + (k1 >= 10 ? "" + k1 : "0" + k1), "Default/1" + k1 / 10 + ".tga", "Default/1" + k1 % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "SWRoundel", "SW/SWRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SWRoundel", "SW/SWRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySouthAfrica)
        {
            k = clampToLiteral(k);
            changeMat(hiermesh, "Overlay1", "SARegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
            changeMat(hiermesh, "Overlay3", "SARegi_" + regiment.id(), "British/" + regiment.aid()[0] + ".tga", "British/" + regiment.aid()[1] + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
            changeMat(hiermesh, "Overlay2", "BritishLnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "British/" + (char)((65 + k) - 1) + ".tga", "null.tga", com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2], 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BritishRnum_" + l + i + (k >= 10 ? "" + k : "0" + k), "null.tga", "British/" + (char)((65 + k) - 1) + ".tga", 1.0F, 1.0F, 1.0F, com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishSkyColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "SAWings", "SA/SAWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SARoundel", "SA/SARoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "SATail", "SA/SATail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryYugoslavia)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "YURoundel", "YU/YURoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "YURoundel", "YU/YURoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "YUTail", "YU/YUTailEarly.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryYugoPar)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "YUPRoundel", "YU/YURoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "YUPARoundel", "YU/YURoundelAlt.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "YUPATail", "YU/YUTailAlternative.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryDenmark)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "DKRoundel", "DK/DKRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "DKRoundel", "DK/DKRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "DKTail", "DK/DKTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPhilippines)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "PHRoundelEarly", "PH/PHRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "PHRoundelEarly", "PH/PHRoundelEarly.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCroatia)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "HRRoundel", "HR/HRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "HRRoundel", "HR/HRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "HRTail", "HR/HRTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBulgariaAllied)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BLRoundel", "BL/BLRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryThailand)
        {
            changeMat(class1, hiermesh, "Overlay1", "THArt", "TH/THArt.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay4", "THArt", "TH/THArt.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "THWings", "TH/THWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "THWings", "TH/THWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryThailandBlue)
        {
            changeMat(class1, hiermesh, "Overlay1", "THArt", "TH/THArt.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay4", "THArt", "TH/THArt.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "THRoundel", "TH/THRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "THRoundel", "TH/THRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussianLiberationArmy)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "RBRoundel", "RB/RBRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "RBRoundel", "RB/RBRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "RBTail", "RB/RBRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryManchukuo)
        {
            changeMat(class1, hiermesh, "Overlay2", "MKMarkings", "MK/MKMarkings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "MKMarkings", "MK/MKMarkings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "MKRoundel", "MK/MKRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryRussianEmpire)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "RERoundel", "RE/RERoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "RERoundel", "RE/RERoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGDR)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "DDRoundel", "DD/DDRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "DDRoundel", "DD/DDRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "DDRoundel", "DD/DDRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryBolivia)
        {
            changeMat(class1, hiermesh, "Overlay6", "BORoundel", "BO/BORoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "BORoundel", "BO/BORoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryAbyssinia)
        {
            changeMat(hiermesh, "Overlay1", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay2", "ABRoundel", "AB/ABRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay3", "ABRoundel", "AB/ABRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "ABWings", "AB/ABWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "ABTail", "AB/ABTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryMexico)
        {
            changeMat(hiermesh, "Overlay1", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "MXRoundel", "MX/MXRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "MXRoundel", "MX/MXRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryUruguay)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "URRoundel", "UR/URRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "URRoundel", "UR/URRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryHungaryAllied)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "HYRoundel", "HY/HYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "HYRoundel", "HY/HYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "HYTail", "HY/HYRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryTurkey)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "TKRoundel", "TK/TKRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "TKRoundel", "TK/TKRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "TKTail", "TK/TKTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIraq)
        {
            changeMat(hiermesh, "Overlay2", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "IKRoundel", "IK/IKRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "IKRoundel", "IK/IKRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "IKTail", "IK/IKTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryEgypt)
        {
            changeMat(hiermesh, "Overlay2", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "EGRoundel", "EG/EGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "EGRoundel", "EG/EGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "EGTail", "EG/EGTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryEgyptRed)
        {
            changeMat(hiermesh, "Overlay2", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "EGRoundel", "EG/EGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "EGRoundel", "EG/EGRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "EGTail", "EG/EGTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGenericBlue)
        {
            changeMat(hiermesh, "Overlay3", "psBM00BNGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(hiermesh, "Overlay2", "psBM00BNGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
            changeMat(class1, hiermesh, "Overlay6", "GenBlueWing", "BU/BlueWing.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "GenBlueRoundel", "BU/BlueRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "GenBlueTail", "BU/BlueTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryGenericRed)
        {
            changeMat(hiermesh, "Overlay3", "psBM00RNGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(hiermesh, "Overlay2", "psBM00RNGCNUM" + (k < 10 ? "0" + k : "" + k), "German/" + k / 10 + ".tga", "German/" + k % 10 + ".tga", com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[0], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[1], com.maddox.il2.objects.air.PaintScheme.psBritishRedColor[2]);
            changeMat(class1, hiermesh, "Overlay6", "GenredWing", "RD/RedWing.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "GenredRoundel", "RD/RedRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "AGenredTail", "RD/RedTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryParaguay)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "PARoundel", "PA/PARoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "PATail", "PA/PATail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryArgentina)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "ARRoundel", "AR/ARRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ARRoundel", "AR/ARRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "ARTail", "AR/ARTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryCzechoslovakia)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "CZRoundel", "CZ/CZRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "CZTail", "CZ/CZTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPolandAxis)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "PNRoundel", "PN/PNRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "PNRoundel", "PN/PNRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPortugal)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "PTWings", "PT/PTWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "PTRoundel", "PT/PTRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "PTTail", "PT/PTTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIran)
        {
            changeMat(hiermesh, "Overlay2", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "ArabicNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "IRRoundel", "IR/IRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "IRRoundel", "IR/IRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "IRTail", "IR/IRTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPRC)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "CCRoundel", "CC/CCRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CCRoundel", "CC/CCRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryChinaRed)
        {
            changeMat(hiermesh, "Overlay1", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "CCRoundel", "CC/CCRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "CCRoundel", "CC/CCRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNorthKorea)
        {
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySouthKorea)
        {
            changeMat(hiermesh, "Overlay1", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryNorthKoreaRed)
        {
            changeMat(hiermesh, "Overlay8", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "KPRoundel", "KP/KPRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySouthKoreaBlue)
        {
            changeMat(hiermesh, "Overlay1", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay4", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "KRRoundel", "KR/KRRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryEstonia)
        {
            changeMat(class1, hiermesh, "Overlay1", "EERoundelL", "EE/EERoundelL.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay4", "EERoundelR", "EE/EERoundelR.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "EEWings", "EE/EEWings.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryLatvia)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "LVWings", "LV/LVWings.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "LVRoundel", "LV/LVRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryLithuania)
        {
            char c2 = (char)(48 + k % 10);
            java.lang.String s3 = getFAFACCode(class1, i);
            changeMat(hiermesh, "Overlay2", "psFM01FINACID" + s3 + c2, "Finnish/" + s3 + ".tga", "Finnish/sn" + c2 + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(hiermesh, "Overlay3", "psFM01FINACID" + s3 + c2, "Finnish/" + s3 + ".tga", "Finnish/sn" + c2 + ".tga", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            changeMat(class1, hiermesh, "Overlay6", "LTRoundel", "LT/LTRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "LTTail", "LT/LTRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPeru)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "PERoundel", "PE/PERoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "PERoundel", "PE/PERoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryColombia)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "CORoundel", "CO/CORoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "COTail", "CO/CORoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySyria)
        {
            changeMat(class1, hiermesh, "Overlay6", "SYRoundel", "SY/SYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SYRoundel", "SY/SYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "SYTailNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countrySyriaRed)
        {
            changeMat(class1, hiermesh, "Overlay6", "SYRoundel", "SY/SYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "SYRoundel", "SY/SYRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay8", "SYTailNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Arabic/1" + k / 10 + ".tga", "Arabic/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryAustria)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "ATRoundel", "AT/ATRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "ATRoundel", "AT/ATRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryItalyANR)
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
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryVichyFrance)
        {
            changeMat(hiermesh, "Overlay2", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "DefaultNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "FRRoundel", "FR/FRRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "FRRoundel", "FR/FRRoundel.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryIndia)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "IDRoundel", "ID/IDRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "IDRoundel", "ID/IDRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "IDTail", "ID/IDTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        }
        if(regiment.country() == com.maddox.il2.objects.air.PaintScheme.countryPakistan)
        {
            changeMat(hiermesh, "Overlay2", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(hiermesh, "Overlay3", "BlackNum_" + l + i + (k >= 10 ? "" + k : "0" + k), "Default/1" + k / 10 + ".tga", "Default/1" + k % 10 + ".tga", 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay6", "PKRoundel", "PK/PKRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay7", "PKRoundel", "PK/PKRoundel.tga", 1.0F, 1.0F, 1.0F);
            changeMat(class1, hiermesh, "Overlay8", "PKTail", "PK/PKTail.tga", 1.0F, 1.0F, 1.0F);
            return;
        } else
        {
            return;
        }
    }
}
