package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class I_16TYPE29 extends I_16
    implements TypeTNBFighter
{

    public I_16TYPE29()
    {
    }

    static 
    {
        java.lang.Class class1 = I_16TYPE29.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-16");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-16type29(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/I-16type29/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-16type29.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitI_16TYPE29.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.82595F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 3, 3, 9, 9, 9, 9, 9, 
            9, 2, 2, 2, 2, 2, 2, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", 
            "_ExternalDev06", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalDev07", "_ExternalDev08"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASk 475", "MGunShKASk 500", "MGunUBk 230", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2fab100", new java.lang.String[] {
            "MGunShKASk 475", "MGunShKASk 500", "MGunUBk 230", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6rs82", new java.lang.String[] {
            "MGunShKASk 475", "MGunShKASk 500", "MGunUBk 230", null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", null, null
        });
        Aircraft.weaponsRegister(class1, "2tank100", new java.lang.String[] {
            "MGunShKASk 475", "MGunShKASk 500", "MGunUBk 230", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "FuelTankGun_Tank100i16 1", "FuelTankGun_Tank100i16 1"
        });
        Aircraft.weaponsRegister(class1, "6rs82tank", new java.lang.String[] {
            "MGunShKASk 475", "MGunShKASk 500", "MGunUBk 230", null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "FuelTankGun_Tank100i16 1", "FuelTankGun_Tank100i16 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
    }
}