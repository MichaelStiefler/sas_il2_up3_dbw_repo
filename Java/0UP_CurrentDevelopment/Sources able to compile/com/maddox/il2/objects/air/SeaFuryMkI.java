package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class SeaFuryMkI extends SeaFury
    implements TypeFighter, TypeStormovik
{

    public SeaFuryMkI()
    {
    }

    static 
    {
        java.lang.Class class1 = SeaFuryMkI.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "SeaFury");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SeaFuryMkI/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1946F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1955.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/SeaFuryMkI.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitSFURY.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7394F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 
            9, 9, 2, 2, 2, 2, 2, 2, 2, 2
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2 x 45gal tanks", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "FuelTankGun_TankTempest 1", "FuelTankGun_TankTempest 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2 x 90gal tanks", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", "PylonTEMPESTPLN1 1", "PylonTEMPESTPLN2 1", null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2 x 250lb bomb", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, "PylonTEMPESTPLN1 1", "PylonTEMPESTPLN2 1", "BombGun250lbsE 1", "BombGun250lbsE 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2 x 500lb bomb", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, "PylonTEMPESTPLN1 1", "PylonTEMPESTPLN2 1", "BombGun500lbsE 1", "BombGun500lbsE 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2 x 1000lb bomb", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, "PylonTEMPESTPLN1 1", "PylonTEMPESTPLN2 1", "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
