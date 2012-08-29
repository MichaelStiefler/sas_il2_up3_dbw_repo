package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class A1H extends AD
{

    public A1H()
    {
    }

    static 
    {
        java.lang.Class class1 = A1H.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "A1H");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/A1H(multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1945F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/A1H.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitAD4.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.0585F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 
            3, 9, 9, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 9, 9, 9, 9, 9, 
            9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 2, 9, 2, 9, 2, 2, 2, 2, 
            2, 2, 2, 9, 2, 2, 2, 2, 2, 2, 
            2, 9, 9, 9, 9, 9, 9, 9, 0, 0, 
            9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb02", "_ExternalBomb03", 
            "_ExternalBomb01", "_ExternalDev20", "_ExternalDev21", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", 
            "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", 
            "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalDev13", "_ExternalDev14", "_ExternalDev15", "_ExternalDev16", "_ExternalDev17", "_ExternalDev18", "_ExternalDev19", 
            "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", 
            "_ExternalBomb14", "_ExternalBomb15", "_ExternalRock13", "_ExternalDev20", "_ExternalRock14", "_ExternalDev21", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", 
            "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalDev22", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", "_ExternalRock25", "_ExternalRock26", "_ExternalRock27", 
            "_ExternalRock28", "_ExternalDev23", "_ExternalDev24", "_ExternalDev25", "_ExternalDev26", "_ExternalDev27", "_ExternalDev28", "_ExternalDev29", "_MGun01", "_MGun02", 
            "_ExternalDev30", "_ExternalDev31", "_ExternalRock29", "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", "_ExternalRock34", "_ExternalRock35", "_ExternalRock36", 
            "_ExternalRock37", "_ExternalRock38", "_ExternalRock39", "_ExternalRock40", "_ExternalRock41", "_ExternalRock42", "_ExternalRock43", "_ExternalRock44"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xLAU32_8xMk81_2xMk82_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGunMk82 1", "BombGunMk82 1", 
            null, null, null, "RocketGunHYDRA 3", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, "FuelTankGun_TankAD4 1", 
            "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", null, null, 
            null, null, "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "Pylon_LAU3 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xMk82_2x750lbs_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun750lbs 1", "BombGun750lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, "FuelTankGun_TankAD4 1", 
            null, null, null, null, null, null, null, null, "BombGunMk82 1", "BombGunMk82 1", 
            "BombGunMk82 1", "BombGunMk82 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xMk82_4xBLU2_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGunBlu2Napalm 1", "BombGunBlu2Napalm 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, "FuelTankGun_TankAD4 1", 
            null, null, null, null, null, null, "BombGunBlu2Napalm 1", "BombGunBlu2Napalm 1", "BombGunMk82 1", "BombGunMk82 1", 
            "BombGunMk82 1", "BombGunMk82 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xMk82_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGunMk82 1", "BombGunMk82 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, "FuelTankGun_TankAD4 1", 
            null, null, null, null, null, null, null, null, "BombGunMk82 1", "BombGunMk82 1", 
            "BombGunMk82 1", "BombGunMk82 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xBLU2_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGunBlu2Napalm 1", "BombGunBlu2Napalm 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, "FuelTankGun_TankAD4 1", 
            null, null, null, null, null, null, null, null, "BombGunBlu2Napalm 1", "BombGunBlu2Napalm 1", 
            "BombGunBlu2Napalm 1", "BombGunBlu2Napalm 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x750_12xMk81_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun750lbs 1", "BombGun750lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, "FuelTankGun_TankAD4 1", 
            "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", 
            "BombGunMk81 1", "BombGunMk81 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xLAU32_4xMk81_2xMk82_3xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "FuelTankGun_TankAD4 1", "FuelTankGun_TankAD4 1", "FuelTankGun_TankAD4 1", 
            null, null, "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk82 1", "BombGunMk82 1", null, null, 
            null, null, "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xLAU32_2xMk82_8xMk81_2xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "FuelTankGun_TankAD4 1", "FuelTankGun_TankAD4 1", null, 
            "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk82 1", "BombGunMk82 1", 
            null, null, "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xLAU32_4xMk81_2xMk82_2xBLU2_3xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "FuelTankGun_TankAD4 1", "FuelTankGun_TankAD4 1", "FuelTankGun_TankAD4 1", 
            "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunBlu2Napalm 1", "BombGunBlu2Napalm 1", "BombGunMk82 1", "BombGunMk82 1", null, null, 
            null, null, "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "Pylon_LAU3 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xLAU32_6xMk81_2xMk82_2xMk83_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGunMk83 1", "BombGunMk83 1", 
            null, null, null, "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, "FuelTankGun_TankAD4 1", 
            "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk82 1", "BombGunMk82 1", null, null, 
            null, null, "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "Pylon_LAU3 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6xMk82_2xMk83_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGunMk83 1", "BombGunMk83 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, "FuelTankGun_TankAD4 1", 
            null, null, null, null, null, null, "BombGunMk82 1", "BombGunMk82 1", "BombGunMk82 1", "BombGunMk82 1", 
            "BombGunMk82 1", "BombGunMk82 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xLAU32_6xMk81_2xMk82_2xBLU2_2x750lbs_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun750lbs 1", "BombGun750lbs 1", 
            null, null, null, "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, "FuelTankGun_TankAD4 1", 
            "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk82 1", "BombGunMk82 1", "BombGunBlu2Napalm 1", "BombGunBlu2Napalm 1", 
            null, null, "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xLAU32_4xMk81_2xMk82_4xBLU2_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGunBlu2Napalm 1", "BombGunBlu2Napalm 1", 
            null, null, null, "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, "FuelTankGun_TankAD4 1", 
            "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk82 1", "BombGunMk82 1", "BombGunBlu2Napalm 1", "BombGunBlu2Napalm 1", 
            null, null, "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1xNuke", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            "BombGunMk7 1", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1xNuke_2xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            "BombGunMk7 1", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "FuelTankGun_TankAD4 1", "FuelTankGun_TankAD4 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xLAU32", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "Pylon_LAU3 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xLAU32_2x750_8xMk81", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun750lbs 1", "BombGun750lbs 1", 
            null, null, null, "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, null, 
            "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", null, null, 
            null, null, "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "Pylon_LAU3 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "12xMk82", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGunMk82 1", "BombGunMk82 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, null, 
            null, null, "BombGunMk82 1", "BombGunMk82 1", "BombGunMk82 1", "BombGunMk82 1", "BombGunMk82 1", "BombGunMk82 1", "BombGunMk82 1", "BombGunMk82 1", 
            "BombGunMk82 1", "BombGunMk82 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "12xHVAR_2xMk83_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGunMk83 1", "BombGunMk83 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonHVAR 1", "PylonHVAR 1", "PylonHVAR 1", "PylonHVAR 1", "PylonHVAR 1", 
            "PylonHVAR 1", "PylonHVAR 1", "PylonHVAR 1", "PylonHVAR 1", "PylonHVAR 1", "PylonHVAR 1", "PylonHVAR 1", null, null, "FuelTankGun_TankAD4 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xZuni", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "Pylon_Zuni 1", "Pylon_Zuni 1", "Pylon_Zuni 1", "Pylon_Zuni 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", 
            "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1"
        });
        Aircraft.weaponsRegister(class1, "4xZuni_6xMk81_4xMk82", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "Pylon_Zuni 1", "Pylon_Zuni 1", "Pylon_Zuni 1", "Pylon_Zuni 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, null, 
            "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", "BombGunMk81 1", null, null, "BombGunMk82 1", "BombGunMk82 1", 
            "BombGunMk82 1", "BombGunMk82 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", 
            "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1"
        });
        Aircraft.weaponsRegister(class1, "4xZuni_4xLAU32", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "Pylon_Zuni 1", "Pylon_Zuni 1", "Pylon_Zuni 1", "Pylon_Zuni 1", null, null, 
            null, null, null, "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "Pylon_LAU3 1", null, null, null, null, null, null, null, null, 
            null, null, "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", 
            "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1"
        });
        Aircraft.weaponsRegister(class1, "4xZuni_4xLAU32_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "Pylon_Zuni 1", "Pylon_Zuni 1", "Pylon_Zuni 1", "Pylon_Zuni 1", null, null, 
            null, null, null, "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", 
            "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", "PylonAD4PLN2 1", null, null, "FuelTankGun_TankAD4 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "Pylon_LAU3 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", "RocketGunHYDRA 1", 
            "RocketGunHYDRA 1", "Pylon_LAU3 1", null, null, null, null, null, null, null, null, 
            null, null, "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", 
            "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1", "RocketGun5inchZuni 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
    }
}
