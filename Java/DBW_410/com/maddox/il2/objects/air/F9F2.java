// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 2/04/2011 11:06:29 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   F9F2.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            F9F, PaintSchemeFMPar06, Aircraft, NetAircraft

public class F9F2 extends F9F
{

    public F9F2()
    {
    }

    public static boolean bChangedPit = false;

    static 
    {
        Class class1 = com.maddox.il2.objects.air.F9F2.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "F9F2");
        Property.set(class1, "meshName", "3DO/Plane/F9F2/hier.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        Property.set(class1, "yearService", 1946.9F);
        Property.set(class1, "yearExpired", 1955.3F);
        Property.set(class1, "FlightModel", "FlightModels/F9F2.fmd");
        Property.set(class1, "cockpitClass", new Class[] {
            com.maddox.il2.objects.air.CockpitF9F2.class
        });
        weaponTriggersRegister(class1, new int[] {
                0, 0, 0, 0, 9, 9, 9, 9, 9, 9, 
                9, 9, 3, 3, 3, 3, 3, 3, 3, 3, 
                9, 9, 2, 2, 2, 2, 2, 2
            });
            weaponHooksRegister(class1, new String[] {
                "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", 
                "_ExternalDev07", "_ExternalDev08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", 
                "_ExternalDev09", "_ExternalDev10", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08"
            });
            weaponsRegister(class1, "default", new String[] {
                "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
                null, null, null, null, null, null, null, null, null, null, 
                null, null, null, null, null, null, null, null
            });
            weaponsRegister(class1, "06xHVAR_Rockets", new String[] {
                "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "PylonP51PLN2 1", "PylonP51PLN2 1", 
                "PylonP51PLN2 1", "PylonP51PLN2 1", null, null, null, null, null, null, null, null, 
                null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
            });
            weaponsRegister(class1, "02x500lbs_Bomb", new String[] {
                "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "PylonP51PLN2 1", "PylonP51PLN2 1", null, null, null, null, 
                null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, 
                null, null, null, null, null, null, null, null
            });
            weaponsRegister(class1, "06x250lbs_Bomb", new String[] {
                "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "PylonP51PLN2 1", "PylonP51PLN2 1", 
                "PylonP51PLN2 1", "PylonP51PLN2 1", null, null, "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", 
                null, null, null, null, null, null, null, null
            });
            weaponsRegister(class1, "02x500lbs_Bomb+06xHVAR_Rockets", new String[] {
                "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "PylonP51PLN2 1", "PylonP51PLN2 1", "PylonP51PLN2 1", "PylonP51PLN2 1", "PylonP51PLN2 1", "PylonP51PLN2 1", 
                "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, 
                null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
            });
            weaponsRegister(class1, "04x500lbs_Bomb", new String[] {
                "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "PylonP51PLN2 1", "PylonP51PLN2 1", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", 
                null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, null, 
                null, null, null, null, null, null, null, null
            });
            weaponsRegister(class1, "02x1000lbs_Bomb", new String[] {
                "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "PylonP51PLN2 1", "PylonP51PLN2 1", null, null, null, null, 
                null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, 
                null, null, null, null, null, null, null, null
            });
            weaponsRegister(class1, "02x500lbs_Bomb+06x250lbs_Bomb", new String[] {
                "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "PylonP51PLN2 1", "PylonP51PLN2 1", "PylonP51PLN2 1", "PylonP51PLN2 1", "PylonP51PLN2 1", "PylonP51PLN2 1", 
                "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", "BombGun250lbsE 1", 
                null, null, null, null, null, null, null, null
            });
            weaponsRegister(class1, "02x154Gal_Tank", new String[] {
                "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "PylonP51PLN2 1", "PylonP51PLN2 1", null, null, null, null, 
                null, null, null, null, null, null, null, null, null, null, 
                "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", null, null, null, null, null, null
            });
            weaponsRegister(class1, "02xTinyTim", new String[] {
                "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "PylonP51PLN2 1", "PylonP51PLN2 1", null, null, null, null, 
                null, null, "RocketGunTinyTim 1", "RocketGunTinyTim 1", null, null, null, null, null, null, 
                null, null, null, null, null, null, null, null
            });
            weaponsRegister(class1, "02x75Gal_Napalm", new String[] {
                "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "PylonP51PLN2 1", "PylonP51PLN2 1", null, null, null, null, 
                null, null, "BombGun75Napalm 1", "BombGun75Napalm 1", null, null, null, null, null, null, 
                null, null, null, null, null, null, null, null
            });
            weaponsRegister(class1, "none", new String[] {
                null, null, null, null, null, null, null, null, null, null, 
                null, null, null, null, null, null, null, null, null, null, 
                null, null, null, null, null, null, null, null
            });
        }
    }