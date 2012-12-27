package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class BF_110F2 extends BF_110
{

    public BF_110F2()
    {
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = BF_110F2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf-110");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Bf-110F-2/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-110Fvroeg.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitBF_110F2.class, CockpitBF_110E1_Gunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.66895F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 
            10, 9, 9, 9, 3, 3, 3, 3, 3, 3, 
            0, 0, 1, 9, 9, 9, 9, 2, 2, 2, 
            2, 9, 9, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", "_CANNON01", "_CANNON02", "_CANNON01", "_CANNON02", "_MGUN07", "_MGUN08", 
            "_MGUN05", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", 
            "_CANNON03", "_CANNON04", "_CANNON05", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", 
            "_ExternalRock04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFki 180", "MGunMGFFki 180", null, null, null, null, 
            "MGunMG15t 750", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x20mm", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFki 180", "MGunMGFFki 180", null, null, null, null, 
            "MGunMG15t 750", null, null, "PylonBF110R3", null, null, null, null, null, null, 
            "MGunMG15120k 200", "MGunMG15120k 200", null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "Mortier_2x20mm", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFki 180", "MGunMGFFki 180", null, null, null, null, 
            "MGunMG15t 750", null, null, "PylonBF110R3", null, null, null, null, null, null, 
            "MGunMG15120k 200", "MGunMG15120k 200", null, "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", 
            "RocketGunWfrGr21", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "Mortier", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFki 180", "MGunMGFFki 180", null, null, null, null, 
            "MGunMG15t 750", null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", 
            "RocketGunWfrGr21", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "Mortier_Tanks", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFki 180", "MGunMGFFki 180", null, null, null, null, 
            "MGunMG15t 750", null, null, null, null, null, null, null, null, null, 
            null, null, null, "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", 
            "RocketGunWfrGr21", "PylonETC250", "PylonETC250", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1"
        });
        Aircraft.weaponsRegister(class1, "Mortier_2x20mm_Tank", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFki 180", "MGunMGFFki 180", null, null, null, null, 
            "MGunMG15t 750", null, null, "PylonBF110R3", null, null, null, null, null, null, 
            "MGunMG15120k 200", "MGunMG15120k 200", null, "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", 
            "RocketGunWfrGr21", "PylonETC250", "PylonETC250", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1"
        });
        Aircraft.weaponsRegister(class1, "2sc250", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFki 180", "MGunMGFFki 180", null, null, null, null, 
            "MGunMG15t 750", null, null, null, "BombGunSC250 1", "BombGunSC250 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2sc500", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFki 180", "MGunMGFFki 180", null, null, null, null, 
            "MGunMG15t 750", null, null, null, "BombGunSC500 1", "BombGunSC500 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "Tanks_2sc250", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFki 180", "MGunMGFFki 180", null, null, null, null, 
            "MGunMG15t 750", null, null, null, "BombGunSC250 1", "BombGunSC250 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1"
        });
        Aircraft.weaponsRegister(class1, "Tanks_2sc500", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFki 180", "MGunMGFFki 180", null, null, null, null, 
            "MGunMG15t 750", null, null, null, "BombGunSC500 1", "BombGunSC500 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1"
        });
        Aircraft.weaponsRegister(class1, "Full", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFki 180", "MGunMGFFki 180", null, null, null, null, 
            "MGunMG15t 750", null, null, null, "BombGunSC500 1", "BombGunSC500 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1"
        });
        Aircraft.weaponsRegister(class1, "Tanks", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFki 180", "MGunMGFFki 180", null, null, null, null, 
            "MGunMG15t 750", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1"
        });
        Aircraft.weaponsRegister(class1, "Tanks_2x250_4x50", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029", "MGunMG17ki 1008", "MGunMGFFki 180", "MGunMGFFki 180", null, null, null, null, 
            "MGunMG15t 750", null, null, null, "BombGunSC250 1", "BombGunSC250 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D 1", "FuelTankGun_Type_D 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}