// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Mass.java

package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.FuelTank;
import com.maddox.il2.objects.weapons.Pylon;
import com.maddox.il2.objects.weapons.PylonA5MPLN1;
import com.maddox.il2.objects.weapons.PylonA6MPLN1;
import com.maddox.il2.objects.weapons.PylonA6MPLN2;
import com.maddox.il2.objects.weapons.PylonB25PLN2;
import com.maddox.il2.objects.weapons.PylonB25RAIL;
import com.maddox.il2.objects.weapons.PylonB5NPLN0;
import com.maddox.il2.objects.weapons.PylonB5NPLN1;
import com.maddox.il2.objects.weapons.PylonB5NPLN2;
import com.maddox.il2.objects.weapons.PylonB5NPLN3;
import com.maddox.il2.objects.weapons.PylonB6NPLN1;
import com.maddox.il2.objects.weapons.PylonBEAUPLN1;
import com.maddox.il2.objects.weapons.PylonBEAUPLN2;
import com.maddox.il2.objects.weapons.PylonBEAUPLN3;
import com.maddox.il2.objects.weapons.PylonBEAUPLN4;
import com.maddox.il2.objects.weapons.PylonBF110R3;
import com.maddox.il2.objects.weapons.PylonBF110R4;
import com.maddox.il2.objects.weapons.PylonDer16TB3Fake;
import com.maddox.il2.objects.weapons.PylonETC250;
import com.maddox.il2.objects.weapons.PylonETC50;
import com.maddox.il2.objects.weapons.PylonETC501FW190;
import com.maddox.il2.objects.weapons.PylonETC50Bf109;
import com.maddox.il2.objects.weapons.PylonETC71;
import com.maddox.il2.objects.weapons.PylonETC900;
import com.maddox.il2.objects.weapons.PylonF4FPLN1;
import com.maddox.il2.objects.weapons.PylonF4FPLN2;
import com.maddox.il2.objects.weapons.PylonF4UPLN2;
import com.maddox.il2.objects.weapons.PylonF4UPLN3;
import com.maddox.il2.objects.weapons.PylonF6FPLN1;
import com.maddox.il2.objects.weapons.PylonF6FPLN2;
import com.maddox.il2.objects.weapons.PylonHS129BK37;
import com.maddox.il2.objects.weapons.PylonHS129BK75;
import com.maddox.il2.objects.weapons.PylonHS129MG17S;
import com.maddox.il2.objects.weapons.PylonHS129MK101;
import com.maddox.il2.objects.weapons.PylonHS129MK103;
import com.maddox.il2.objects.weapons.PylonHs129BombRackC250;
import com.maddox.il2.objects.weapons.PylonHs129BombRackC4x50;
import com.maddox.il2.objects.weapons.PylonHs129BombRackL;
import com.maddox.il2.objects.weapons.PylonHs129BombRackR;
import com.maddox.il2.objects.weapons.PylonKI43PLN1;
import com.maddox.il2.objects.weapons.PylonKI84PLN2;
import com.maddox.il2.objects.weapons.PylonKMB;
import com.maddox.il2.objects.weapons.PylonMG15120;
import com.maddox.il2.objects.weapons.PylonMG15120Internal;
import com.maddox.il2.objects.weapons.PylonMG15120x2;
import com.maddox.il2.objects.weapons.PylonMe262_R4M_Left;
import com.maddox.il2.objects.weapons.PylonMe262_R4M_Right;
import com.maddox.il2.objects.weapons.PylonMiG_3_BK;
import com.maddox.il2.objects.weapons.PylonMk103;
import com.maddox.il2.objects.weapons.PylonMk108;
import com.maddox.il2.objects.weapons.PylonN1K1PLN1;
import com.maddox.il2.objects.weapons.PylonP38GUNPOD;
import com.maddox.il2.objects.weapons.PylonP38RAIL3FL;
import com.maddox.il2.objects.weapons.PylonP38RAIL3FR;
import com.maddox.il2.objects.weapons.PylonP38RAIL3WL;
import com.maddox.il2.objects.weapons.PylonP38RAIL3WR;
import com.maddox.il2.objects.weapons.PylonP38RAIL5;
import com.maddox.il2.objects.weapons.PylonP39PLN1;
import com.maddox.il2.objects.weapons.PylonP51PLN2;
import com.maddox.il2.objects.weapons.PylonP63CGUNPOD;
import com.maddox.il2.objects.weapons.PylonP63CPLN2;
import com.maddox.il2.objects.weapons.PylonPE8_FAB100;
import com.maddox.il2.objects.weapons.PylonPE8_FAB250;
import com.maddox.il2.objects.weapons.PylonR5BombRackC;
import com.maddox.il2.objects.weapons.PylonR5BombRackL;
import com.maddox.il2.objects.weapons.PylonRO_4andHalfInch_3;
import com.maddox.il2.objects.weapons.PylonRO_82_1;
import com.maddox.il2.objects.weapons.PylonRO_82_3;
import com.maddox.il2.objects.weapons.PylonRO_WfrGr21;
import com.maddox.il2.objects.weapons.PylonRO_WfrGr21Dual;
import com.maddox.il2.objects.weapons.PylonS328;
import com.maddox.il2.objects.weapons.PylonSpitC;
import com.maddox.il2.objects.weapons.PylonSpitL;
import com.maddox.il2.objects.weapons.PylonSpitR;
import com.maddox.il2.objects.weapons.PylonSpitROCK;
import com.maddox.il2.objects.weapons.PylonTEMPESTPLN1;
import com.maddox.il2.objects.weapons.PylonTEMPESTPLN2;
import com.maddox.il2.objects.weapons.PylonTEMPESTPLN3;
import com.maddox.il2.objects.weapons.PylonTEMPESTPLN4;
import com.maddox.il2.objects.weapons.PylonVAP250;
import com.maddox.il2.objects.weapons.RocketBombGun;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.il2.fm:
//            RealFlightModel, FlightModelMain, Controls

public class Mass
{

    public Mass()
    {
        bFuelTanksLoaded = false;
        nitro = 0.0F;
        maxNitro = 1.0F;
        pylonCoeff = 0.0F;
        pylonCoeffB = 0.0F;
        pylonCoeffR = 0.0F;
        fuelCoeff = 0.0F;
    }

    public void load(com.maddox.rts.SectFile sectfile, com.maddox.il2.fm.FlightModelMain flightmodelmain)
    {
        java.lang.String s = "Mass";
        java.lang.String s1 = "Critical Mass in " + sectfile.toString();
        FM = flightmodelmain;
        float f = sectfile.get(s, "Empty", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        massEmpty = f;
        f = sectfile.get(s, "Oil", -1F);
        if(f == -1F)
            throw new RuntimeException(s1);
        massEmpty += f;
        f = sectfile.get("Aircraft", "Crew", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        massEmpty += f * 90F;
        referenceWeight = massEmpty;
        f = sectfile.get(s, "TakeOff", 0.0F);
        if(f == 0.0F)
            throw new RuntimeException(s1);
        maxWeight = f;
        f = sectfile.get(s, "Fuel", 0.0F);
        if(f == 0.0F)
        {
            throw new RuntimeException(s1);
        } else
        {
            maxFuel = f;
            mass = massEmpty;
            fuel = maxFuel;
            f = sectfile.get(s, "Nitro", 0.0F);
            maxNitro = nitro = f;
            referenceWeight += fuel + maxNitro;
            return;
        }
    }

    public void onFuelTanksChanged()
    {
        bFuelTanksLoaded = true;
        fuelTanks = new com.maddox.il2.objects.weapons.FuelTank[0];
    }

    public boolean requestFuel(float f)
    {
        mass = massEmpty + fuel + nitro + parasiteMass;
        if(!bFuelTanksLoaded)
        {
            fuelTanks = FM.CT.getFuelTanks();
            bFuelTanksLoaded = true;
        }
        if(fuelTanks.length != 0)
        {
            fuelCoeff = 1.0F;
            float f1 = 0.0F;
            for(int i = 0; i < fuelTanks.length; i++)
                f1 += fuelTanks[i].getFuel(f / (float)fuelTanks.length);

            if(f1 > 0.0F)
                return true;
        } else
        {
            fuelCoeff = 0.0F;
        }
        fuel -= f;
        if(fuel < 0.0F)
        {
            fuel = 0.0F;
            return false;
        } else
        {
            return true;
        }
    }

    public boolean requestNitro(float f)
    {
        mass = massEmpty + fuel + nitro + parasiteMass;
        nitro -= f;
        if(nitro < 0.0F)
        {
            nitro = 0.0F;
            return false;
        } else
        {
            return true;
        }
    }

    public float getFullMass()
    {
        return mass;
    }

    public void computeParasiteMass(com.maddox.il2.ai.BulletEmitter abulletemitter[][])
    {
        parasiteMass = 0.0F;
        parasiteJx = 0.0F;
        for(int i = 0; i < abulletemitter.length; i++)
        {
            if(abulletemitter[i] == null || abulletemitter[i].length <= 0)
                continue;
            for(int j = 0; j < abulletemitter[i].length; j++)
            {
                if(abulletemitter[i][j] instanceof com.maddox.il2.engine.GunGeneric)
                {
                    int k = abulletemitter[i][j].countBullets();
                    float f = abulletemitter[i][j].bulletMassa() * (float)(k < 0 ? 50 : k) * 3F;
                    float f3 = (float)((com.maddox.il2.engine.Actor)abulletemitter[i][j]).pos.getRelPoint().z;
                    float f6 = (float)((com.maddox.il2.engine.Actor)abulletemitter[i][j]).pos.getRelPoint().y;
                    parasiteJx += (f3 * f3 + f6 * f6) * f;
                    parasiteMass += f;
                }
                if((abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.BombGun) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.RocketGun) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.RocketBombGun))
                {
                    int l = abulletemitter[i][j].countBullets();
                    float f1 = abulletemitter[i][j].bulletMassa() * (float)(l < 0 ? 1 : l);
                    float f4 = 0.0F;
                    float f7 = 2.0F;
                    if(l > 0)
                        if(((abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.BombGun) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.RocketBombGun)) && abulletemitter[i][j].getHookName().startsWith("_External"))
                            pylonCoeffB = 1.5F;
                        else
                            pylonCoeffR = 1.0F;
                    pylonCoeff = pylonCoeffB + pylonCoeffR;
                    parasiteJx += (f4 * f4 + f7 * f7) * f1;
                    parasiteMass += f1;
                }
                if(!(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.Pylon))
                    continue;
                float f2;
                if((abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonRO_82_1) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonRO_82_3) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonPE8_FAB100) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonPE8_FAB250) || (abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonMG15120Internal))
                    f2 = 0.0F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonB25RAIL)
                    f2 = 5F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonBEAUPLN2)
                    f2 = 108F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonBEAUPLN3)
                    f2 = 108F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonF4FPLN1)
                    f2 = 10F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonF6FPLN2)
                    f2 = 5F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP38RAIL3FL)
                    f2 = 36.3F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP38RAIL3FR)
                    f2 = 36.3F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP38RAIL3WL)
                    f2 = 36.3F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP38RAIL3WR)
                    f2 = 36.3F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP38RAIL5)
                    f2 = 50F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonRO_4andHalfInch_3)
                    f2 = 36.3F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonRO_WfrGr21)
                    f2 = 37F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonRO_WfrGr21Dual)
                    f2 = 70F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonSpitL)
                    f2 = 10F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonSpitR)
                    f2 = 10F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonSpitROCK)
                    f2 = 5F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonTEMPESTPLN3)
                    f2 = 108F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonTEMPESTPLN4)
                    f2 = 108F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonA5MPLN1)
                    f2 = 10F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonA6MPLN2)
                    f2 = 10F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonETC71)
                    f2 = 10F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonF4FPLN2)
                    f2 = 10F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonS328)
                    f2 = 10F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonHs129BombRackL)
                    f2 = 10F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonHs129BombRackR)
                    f2 = 10F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonR5BombRackC)
                    f2 = 10F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonR5BombRackL)
                    f2 = 10F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonR5BombRackL)
                    f2 = 10F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonETC50)
                    f2 = 40F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonETC50Bf109)
                    f2 = 40F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonETC501FW190)
                    f2 = 60F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP51PLN2)
                    f2 = 9F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP63CPLN2)
                    f2 = 7F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonSpitC)
                    f2 = 23F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonA6MPLN1)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonB25PLN2)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonBEAUPLN1)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonETC250)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonETC900)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonF6FPLN1)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonKI43PLN1)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonKI84PLN2)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonKMB)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonMe262_R4M_Left)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonMe262_R4M_Right)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonN1K1PLN1)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP39PLN1)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonTEMPESTPLN1)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonTEMPESTPLN2)
                    f2 = 30F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonHs129BombRackC250)
                    f2 = 50F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonHs129BombRackC4x50)
                    f2 = 50F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonB5NPLN1)
                    f2 = 70F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonB5NPLN2)
                    f2 = 70F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonB5NPLN3)
                    f2 = 70F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonB6NPLN1)
                    f2 = 70F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonF4UPLN2)
                    f2 = 70F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonF4UPLN3)
                    f2 = 70F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonB5NPLN0)
                    f2 = 150F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonBEAUPLN4)
                    f2 = 150F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonDer16TB3Fake)
                    f2 = 150F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonVAP250)
                    f2 = 150F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonBF110R3)
                    f2 = 150F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonBF110R4)
                    f2 = 350F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonMG15120x2)
                    f2 = 100F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonHS129BK37)
                    f2 = 350F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonHS129MG17S)
                    f2 = 80F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonHS129MK101)
                    f2 = 250F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonHS129MK103)
                    f2 = 196F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonHS129BK75)
                {
                    if(FM.isPlayers() && ((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
                        f2 = 905F;
                    else
                        f2 = 300F;
                } else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonMG15120)
                    f2 = 61F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonMiG_3_BK)
                    f2 = 35F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonMk103)
                    f2 = 140F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonMk108)
                    f2 = 90F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP38GUNPOD)
                    f2 = 90F;
                else
                if(abulletemitter[i][j] instanceof com.maddox.il2.objects.weapons.PylonP63CGUNPOD)
                    f2 = 45F;
                else
                    f2 = 150F;
                float f5 = (float)((com.maddox.il2.engine.Actor)abulletemitter[i][j]).pos.getRelPoint().z;
                float f8 = (float)((com.maddox.il2.engine.Actor)abulletemitter[i][j]).pos.getRelPoint().y;
                parasiteJx += (f5 * f5 + f8 * f8) * f2;
                parasiteMass += f2;
            }

        }

        pylonCoeffB = 0.0F;
        pylonCoeffR = 0.0F;
    }

    public void computeFullJ(com.maddox.JGP.Vector3d vector3d, com.maddox.JGP.Vector3d vector3d1)
    {
        vector3d.scale(massEmpty, vector3d1);
        vector3d.x += parasiteJx;
    }

    private com.maddox.il2.fm.FlightModelMain FM;
    public float massEmpty;
    public float mass;
    public float maxWeight;
    private float parasiteMass;
    private float parasiteJx;
    public float fuel;
    public float maxFuel;
    private com.maddox.il2.objects.weapons.FuelTank fuelTanks[];
    private boolean bFuelTanksLoaded;
    public float nitro;
    public float maxNitro;
    public float referenceWeight;
    public float pylonCoeff;
    private float pylonCoeffB;
    private float pylonCoeffR;
    public float fuelCoeff;
}
