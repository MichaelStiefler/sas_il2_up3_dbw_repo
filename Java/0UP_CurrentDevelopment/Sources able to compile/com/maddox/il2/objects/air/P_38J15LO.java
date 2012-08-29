package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_38J15LO extends P_38
{

    public P_38J15LO()
    {
    }

    static 
    {
        java.lang.Class class1 = P_38J15LO.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-38");
        com.maddox.rts.Property.set(class1, "meshNameDemo", "3DO/Plane/P-38J_10_15(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-38J_10_15(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/P-38J_10_15(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "noseart", 1);
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-38J-15-LO.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitP_38J.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.69215F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 9, 9, 3, 3, 9, 
            9, 2, 2, 2, 2, 2, 2, 9, 9, 2, 
            2, 2, 2, 2, 2, 9, 9, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 9, 9, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 9, 9, 1, 1, 1, 1, 9, 
            9, 3, 3, 9, 9, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", 
            "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalDev03", "_ExternalDev04", "_ExternalRock07", 
            "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalDev05", "_ExternalDev06", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", 
            "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalDev07", "_ExternalDev08", "_ExternalRock23", 
            "_ExternalRock24", "_ExternalRock25", "_ExternalRock26", "_ExternalRock27", "_ExternalRock28", "_ExternalRock29", "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", 
            "_ExternalRock34", "_ExternalRock35", "_ExternalRock36", "_ExternalDev09", "_ExternalDev10", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalBomb03", 
            "_ExternalBomb04", "_ExternalBomb03", "_ExternalBomb06", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb05", "_ExternalBomb04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "droptanks", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38 1", "FuelTankGun_TankP38 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1xdroptank1x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38 1", null, null, "BombGun500lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1xdroptank1x1000", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38 1", null, null, "BombGun1000lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1xdroptank1x1600", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38 1", null, null, "BombGun1600lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1xdroptank5x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38 1", null, null, "BombGun500lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonP38PLN2 1", 
            "PylonP38PLN2 1", "BombGun500lbs 1", "BombGun500lbs 1", "PylonP38PLN2 1", "PylonP38PLN2 1", "BombGun500lbs 1", "BombGun500lbs 1"
        });
        Aircraft.weaponsRegister(class1, "droptanks2x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38 1", "FuelTankGun_TankP38 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonP38PLN2 1", 
            null, "BombGun500lbs 1", "BombGun500lbs 1", null, "PylonP38PLN2 1", null, null
        });
        Aircraft.weaponsRegister(class1, "droptanks2x5002x250", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38 1", "FuelTankGun_TankP38 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonP38PLN2 1", 
            "PylonP38PLN2 1", "BombGun500lbs 1", "BombGun500lbs 1", "PylonP38PLN2 1", "PylonP38PLN2 1", "BombGun250lbs 1", "BombGun250lbs 1"
        });
        Aircraft.weaponsRegister(class1, "droptanks2x3", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38 1", "FuelTankGun_TankP38 1", null, null, "PylonP38RAIL3FL 1", 
            "PylonP38RAIL3FR 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "droptanks4x3", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38 1", "FuelTankGun_TankP38 1", null, null, "PylonP38RAIL3FL 1", 
            "PylonP38RAIL3FR 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "PylonP38RAIL3WL 1", "PylonP38RAIL3WR 1", "RocketGun4andHalfInch 1", 
            "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun250lbs 1", "BombGun250lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4x250", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun250lbs 1", "BombGun250lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP38PLN2 1", null, null, "PylonP38PLN2 1", null, "BombGun250lbs 1", "BombGun250lbs 1"
        });
        Aircraft.weaponsRegister(class1, "6x250", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun250lbs 1", "BombGun250lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonP38PLN2 1", 
            "PylonP38PLN2 1", "BombGun250lbs 1", "BombGun250lbs 1", "PylonP38PLN2 1", "PylonP38PLN2 1", "BombGun250lbs 1", "BombGun250lbs 1"
        });
        Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP38PLN2 1", null, null, "PylonP38PLN2 1", null, "BombGun500lbs 1", "BombGun500lbs 1"
        });
        Aircraft.weaponsRegister(class1, "6x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonP38PLN2 1", 
            "PylonP38PLN2 1", "BombGun500lbs 1", "BombGun500lbs 1", "PylonP38PLN2 1", "PylonP38PLN2 1", "BombGun500lbs 1", "BombGun500lbs 1"
        });
        Aircraft.weaponsRegister(class1, "2x1000", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x10002x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonP38PLN2 1", null, null, "PylonP38PLN2 1", null, "BombGun500lbs 1", "BombGun500lbs 1"
        });
        Aircraft.weaponsRegister(class1, "2x10004x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "PylonP38PLN2 1", 
            "PylonP38PLN2 1", "BombGun500lbs 1", "BombGun500lbs 1", "PylonP38PLN2 1", "PylonP38PLN2 1", "BombGun500lbs 1", "BombGun500lbs 1"
        });
        Aircraft.weaponsRegister(class1, "2x1600", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun1600lbs 1", "BombGun1600lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x154Napalm", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun154Napalm 1", "BombGun154Napalm 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x3", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, null, null, "PylonP38RAIL3FL 1", 
            "PylonP38RAIL3FR 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x3n2x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun500lbs 1", "BombGun500lbs 1", "PylonP38RAIL3FL 1", 
            "PylonP38RAIL3FR 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x3n2x1000", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", "PylonP38RAIL3FL 1", 
            "PylonP38RAIL3FR 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4x3", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, null, null, "PylonP38RAIL3FL 1", 
            "PylonP38RAIL3FR 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "PylonP38RAIL3WL 1", "PylonP38RAIL3WR 1", "RocketGun4andHalfInch 1", 
            "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", "RocketGun4andHalfInch 1", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
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
