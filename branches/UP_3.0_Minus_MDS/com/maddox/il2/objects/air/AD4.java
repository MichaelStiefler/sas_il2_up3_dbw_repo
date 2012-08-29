// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   AD4.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            AD, PaintSchemeFMPar05, NetAircraft, Aircraft

public class AD4 extends com.maddox.il2.objects.air.AD
{

    public AD4()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.AD4.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "AD4");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/AD4(multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1945F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/AD4.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitAD4.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 1.0585F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 
            3, 9, 9, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 9, 9, 9, 9, 9, 
            9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb02", "_ExternalBomb03", 
            "_ExternalBomb01", "_ExternalDev20", "_ExternalDev21", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", 
            "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", 
            "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalDev13", "_ExternalDev14", "_ExternalDev15", "_ExternalDev16", "_ExternalDev17", "_ExternalDev18", "_ExternalDev19", 
            "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", 
            "_ExternalBomb14", "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12xHVAR", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "FuelTankGun_TankF4U 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "FuelTankGun_TankF4U 1", "FuelTankGun_TankF4U 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "FuelTankGun_TankF4U 1", "FuelTankGun_TankF4U 1", "FuelTankGun_TankF4U 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x500_12x100", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "BombGun500lbs 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x500_6x250_6x100", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "BombGun500lbs 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x1000_6x500_6x250", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "BombGun1000lbs 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12x100", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12x250", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x500_6x250", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x1000_12x250", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "BombGun1000lbs 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x2000_2x1000_12x250", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", 
            "BombGun2000lbs 1", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x1000", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "BombGun1000lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x2000", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun2000lbs 1", "BombGun2000lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x2000", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun2000lbs 1", "BombGun2000lbs 1", 
            "BombGun2000lbs 1", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xDT_2x1000_12x100", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "FuelTankGun_TankF4U 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xDT_14x250", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun250lbs 1", "BombGun250lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "FuelTankGun_TankF4U 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xDT_1x1000_6x500", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "FuelTankGun_TankF4U 1", "FuelTankGun_TankF4U 1", "BombGun1000lbs 1", 
            null, null, null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xDT_1x2000_12x100", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            "BombGun2000lbs 1", null, null, "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "FuelTankGun_TankF4U 1", "FuelTankGun_TankF4U 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3xDT_12x100", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "FuelTankGun_TankF4U 1", "FuelTankGun_TankF4U 1", "FuelTankGun_TankF4U 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3xDT_12x250", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "FuelTankGun_TankF4U 1", "FuelTankGun_TankF4U 1", "FuelTankGun_TankF4U 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3xNapalm", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun75Napalm 1", "BombGun75Napalm 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "BombGun75Napalm 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3xNapalm_12x100", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun75Napalm 1", "BombGun75Napalm 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "BombGun75Napalm 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3xNapalm_12xHVAR", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun75Napalm 1", "BombGun75Napalm 1", 
            null, null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "BombGun75Napalm 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xTT_12xHVAR", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "RocketGunTinyTim 1", "RocketGunTinyTim 1", 
            null, null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xTT_12xHVAR_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "RocketGunTinyTim 1", "RocketGunTinyTim 1", 
            null, null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "FuelTankGun_TankF4U 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xTT_6x250_6xHVAR", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "RocketGunTinyTim 1", "RocketGunTinyTim 1", 
            null, null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, 
            null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            null, null, "BombGun250lbs 1", "BombGun250lbs 1", null, null, "BombGun250lbs 1", "BombGun250lbs 1", null, null, 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xTT_12x100_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "RocketGunTinyTim 1", "RocketGunTinyTim 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "FuelTankGun_TankF4U 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1000_6x250_6xHVAR", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, 
            null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            null, null, "BombGun250lbs 1", "BombGun250lbs 1", null, null, "BombGun250lbs 1", "BombGun250lbs 1", null, null, 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xTT_12x100", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "RocketGunTinyTim 1", "RocketGunTinyTim 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12xHVAR_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "FuelTankGun_TankF4U 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12xHVAR_2xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "FuelTankGun_TankF4U 1", "FuelTankGun_TankF4U 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12xHVAR_3xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "FuelTankGun_TankF4U 1", "FuelTankGun_TankF4U 1", "FuelTankGun_TankF4U 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12xHVAR_1xDT_2x1000", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "FuelTankGun_TankF4U 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12xHVAR_1xDT_2xNapalm", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun75Napalm 1", "BombGun75Napalm 1", 
            null, null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "FuelTankGun_TankF4U 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12xHVAR_2xNapalm", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun75Napalm 1", "BombGun75Napalm 1", 
            null, null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12xHVAR_1xTT_1x2000", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "RocketGunTinyTim 1", "RocketGunTinyTim 1", 
            "BombGun2000lbs 1", null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12x100_2xTT_1xNapalm", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "RocketGunTinyTim 1", "RocketGunTinyTim 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "BombGun75Napalm 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12x100_1xTT_1xNapalm_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "RocketGunTinyTim 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "FuelTankGun_TankF4U 1", null, "BombGun75Napalm 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", "BombGun100Lbs 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x500_1x1000_1xNapalm_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun1000lbs 1", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "FuelTankGun_TankF4U 1", null, "BombGun75Napalm 1", 
            null, null, null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500_8x250", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            null, null, null, null, "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500_12x250", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x500", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            null, null, null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "9x500", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "BombGun500lbs 1", 
            null, null, null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x250_2xNapalm", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun75Napalm 1", "BombGun75Napalm 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            null, null, null, null, "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x250_2xNapalm_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun75Napalm 1", "BombGun75Napalm 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "FuelTankGun_TankF4U 1", 
            null, null, null, null, "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12xHVAR_3x500", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", 
            null, null, null, "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, "BombGun500lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xNuke", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "BombGunMk7 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6xHVAR_6xFlare_1xDT", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "RocketGunHVAR5 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", 
            "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "PylonAD4PLN1 1", "Pylon_AD4Radar 1", null, "FuelTankGun_TankF4U 1", 
            "BombGunMk24Flare 1", "BombGunMk24Flare 1", "BombGunMk24Flare 1", "BombGunMk24Flare 1", "BombGunMk24Flare 1", "BombGunMk24Flare 1", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
