// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   P_38J10LO.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_38, PaintSchemeFMPar04, NetAircraft, Aircraft

public class P_38J10LO extends com.maddox.il2.objects.air.P_38
{

    public P_38J10LO()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.P_38J10LO.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "P-38");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshNameDemo", "3DO/Plane/P-38J_10_15(USA)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/P-38J_10_15(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar04())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_us", "3DO/Plane/P-38J_10_15(USA)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_us", ((java.lang.Object) (new PaintSchemeFMPar04())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "noseart", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1943F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/P-38J-10-LO.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitP_38J.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.69215F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 9, 9, 3, 3, 9, 
            9, 2, 2, 2, 2, 2, 2, 9, 9, 2, 
            2, 2, 2, 2, 2, 9, 9, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 9, 9, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 9, 9, 1, 1, 1, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", 
            "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalDev03", "_ExternalDev04", "_ExternalRock07", 
            "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalDev05", "_ExternalDev06", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", 
            "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalDev07", "_ExternalDev08", "_ExternalRock23", 
            "_ExternalRock24", "_ExternalRock25", "_ExternalRock26", "_ExternalRock27", "_ExternalRock28", "_ExternalRock29", "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", 
            "_ExternalRock34", "_ExternalRock35", "_ExternalRock36", "_ExternalDev09", "_ExternalDev10", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "droptanks", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38", "FuelTankGun_TankP38", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "droptanks2x3", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38", "FuelTankGun_TankP38", null, null, "PylonP38RAIL3FL", 
            "PylonP38RAIL3FR", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "droptanks4x3", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38", "FuelTankGun_TankP38", null, null, "PylonP38RAIL3FL", 
            "PylonP38RAIL3FL", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "PylonP38RAIL3WL", "PylonP38RAIL3WR", "RocketGun4andHalfInch", 
            "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xdroptank1x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38", null, null, "BombGun500lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xdroptank1x1000", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38", null, null, "BombGun1000lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xdroptank1x1600", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38", null, null, "BombGun1600lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1000", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1600", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun1600lbs 1", "BombGun1600lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x175Napalm", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun175Napalm 1", "BombGun175Napalm 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x3", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, null, null, "PylonP38RAIL3FL", 
            "PylonP38RAIL3FR", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x3n2x500", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun500lbs 1", "BombGun500lbs 1", "PylonP38RAIL3FL", 
            "PylonP38RAIL3FR", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x3n2x1000", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", "PylonP38RAIL3FL", 
            "PylonP38RAIL3FR", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x3", new java.lang.String[] {
            "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunBrowning50kAPIT 500", "MGunHispanoMkIki 150", null, null, null, null, "PylonP38RAIL3FL", 
            "PylonP38RAIL3FL", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "PylonP38RAIL3WL", "PylonP38RAIL3WR", "RocketGun4andHalfInch", 
            "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
    }
}
