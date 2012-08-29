package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class HurricaneMkIIbMod extends Hurricane
    implements TypeFighter, TypeStormovik
{

    public HurricaneMkIIbMod()
    {
    }

    static 
    {
        java.lang.Class class1 = HurricaneMkIIbMod.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Hurri");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/HurricaneMkIIbMod(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/HurricaneMkIIMod.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitHURRII.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.965F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 0, 0, 1, 1, 9, 2, 
            9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 
            3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalRock01", 
            "_ExternalDev02", "_ExternalRock02", "_ExternalDev03", "_ExternalRock03", "_ExternalDev04", "_ExternalRock04", "_ExternalDev05", "_ExternalRock05", "_ExternalDev06", "_ExternalRock06", 
            "_ExternalRock03", "_ExternalRock04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBk 100", "MGunUBk 100", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        Aircraft.weaponsRegister(class1, "4xubtest", new java.lang.String[] {
            "MGunUBk 100", "MGunUBk 100", null, null, "MGunUBk 100", "MGunUBk 100", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        Aircraft.weaponsRegister(class1, "4xrs82", new java.lang.String[] {
            "MGunUBk 100", "MGunUBk 100", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, 
            null, null, "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", 
            null, null
        });
        Aircraft.weaponsRegister(class1, "6xrs82", new java.lang.String[] {
            "MGunUBk 100", "MGunUBk 100", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, "PylonRO_82_1 1", "RocketGunRS82 1", 
            "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", 
            null, null
        });
        Aircraft.weaponsRegister(class1, "2fab100", new java.lang.String[] {
            "MGunUBk 100", "MGunUBk 100", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGunFAB100 1", "BombGunFAB100 1"
        });
        Aircraft.weaponsRegister(class1, "4x20", new java.lang.String[] {
            "MGunUBk 100", "MGunUBk 100", "MGunShVAKk 120", "MGunShVAKk 120", null, null, "MGunShVAKk 120", "MGunShVAKk 120", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        Aircraft.weaponsRegister(class1, "4x204r", new java.lang.String[] {
            "MGunUBk 100", "MGunUBk 100", "MGunShVAKk 120", "MGunShVAKk 120", null, null, "MGunShVAKk 120", "MGunShVAKk 120", null, null, 
            null, null, "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", 
            null, null
        });
        Aircraft.weaponsRegister(class1, "4x206r", new java.lang.String[] {
            "MGunUBk 100", "MGunUBk 100", "MGunShVAKk 120", "MGunShVAKk 120", null, null, "MGunShVAKk 120", "MGunShVAKk 120", "PylonRO_82_1 1", "RocketGunRS82 1", 
            "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", "PylonRO_82_1 1", "RocketGunRS82 1", 
            null, null
        });
        Aircraft.weaponsRegister(class1, "4x202b100", new java.lang.String[] {
            "MGunUBk 100", "MGunUBk 100", "MGunShVAKk 120", "MGunShVAKk 120", null, null, "MGunShVAKk 120", "MGunShVAKk 120", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "BombGunFAB100 1", "BombGunFAB100 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
