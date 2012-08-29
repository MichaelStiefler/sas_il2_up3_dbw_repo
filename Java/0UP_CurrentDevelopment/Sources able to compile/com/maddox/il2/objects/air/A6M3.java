package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class A6M3 extends A6M
{

    public A6M3()
    {
    }

    static 
    {
        java.lang.Class class1 = A6M3.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "A6M");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/A6M3(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/A6M3(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1940.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/A6M3-32.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitA6M2.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.01885F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 9, 9, 3, 9, 9, 3, 
            3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb03", 
            "_ExternalBomb04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunMGFFk 100", "MGunType99No1s 100", null, null, null, null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "1xdt", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No1s 100", "MGunType99No1s 100", "FuelTankGun_Tank0 1", null, null, null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "2x30", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No1s 100", "MGunType99No1s 100", null, null, null, "PylonA6MPLN4 1", "PylonA6MPLN4 1", "BombGun30kgJ2 1", 
            "BombGun30kgJ2 1"
        });
        Aircraft.weaponsRegister(class1, "1xdt+2x30", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No1s 100", "MGunType99No1s 100", "FuelTankGun_Tank0 1", null, null, "PylonA6MPLN4 1", "PylonA6MPLN4 1", "BombGun30kgJ2 1", 
            "BombGun30kgJ2 1"
        });
        Aircraft.weaponsRegister(class1, "2xType3", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No1s 100", "MGunType99No1s 100", null, null, null, "PylonA6MPLN4 1", "PylonA6MPLN4 1", "BombGunType3AntiAir 1", 
            "BombGunType3AntiAir 1"
        });
        Aircraft.weaponsRegister(class1, "1xdt+2xType3", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No1s 100", "MGunType99No1s 100", "FuelTankGun_Tank0 1", null, null, "PylonA6MPLN4 1", "PylonA6MPLN4 1", "BombGunType3AntiAir 1", 
            "BombGunType3AntiAir 1"
        });
        Aircraft.weaponsRegister(class1, "2x60", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No1s 100", "MGunType99No1s 100", null, null, null, "PylonA6MPLN2 1", "PylonA6MPLN2 1", "BombGun60kgJ2 1", 
            "BombGun60kgJ2 1"
        });
        Aircraft.weaponsRegister(class1, "1x250", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No1s 60", "MGunType99No1s 60", null, "PylonA6MPLN1 1", "BombGun250kg 1", null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
