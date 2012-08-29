package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class UTI_4B extends I_16
    implements TypeFighter, TypeTNBFighter
{

    public UTI_4B()
    {
    }

    static 
    {
        java.lang.Class class1 = UTI_4B.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-16");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/UTI-4B/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-16type15.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitUTI_4B.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.82595F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 3, 3, 9, 9, 9, 9, 9, 9, 
            2, 2, 2, 2, 2, 2
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", 
            "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBk 250", "MGunUBk 250", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2fab50", new java.lang.String[] {
            "MGunUBk 250", "MGunUBk 250", "BombGunFAB50 1", "BombGunFAB50 1", null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2fab100", new java.lang.String[] {
            "MGunUBk 250", "MGunUBk 250", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6rs82", new java.lang.String[] {
            "MGunUBk 250", "MGunUBk 250", null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}
