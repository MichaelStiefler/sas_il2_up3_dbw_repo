// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   F4U1C.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            F4U, PaintSchemeFMPar05, PaintSchemeFMPar06, NetAircraft, 
//            Aircraft

public class F4U1C extends com.maddox.il2.objects.air.F4U
{

    public F4U1C()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.F4U1C.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "F4U");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/F4U-1C(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/F4U-1C(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_rz", "3DO/Plane/F4U-1C(RZ)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_rz", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1945F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/F4U-1C.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitF4U1D.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.0585F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 
            3, 9, 9, 2, 2, 2, 2, 2, 2, 2, 
            2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb02", "_ExternalBomb03", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", 
            "_ExternalRock08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, null, null, null, null, 
            null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xhvarap", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, null, null, null, null, 
            null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x154dt", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, 
            null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x154dt8xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, 
            null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x154dt8xhvarap", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, 
            null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x178dt", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x178dt8xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", null, null, null, null, 
            null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x178dt2x154dt", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", null, null, 
            null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x178dt2x154dt8xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", null, null, 
            null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x178dt2x154dt8xhvarap", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", null, null, 
            null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1541x178dt", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", "BombGun154Napalm 1", "BombGun154Napalm 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1541x178dt8xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", "BombGun154Napalm 1", "BombGun154Napalm 1", 
            null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1541x178dt8xhvarap", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", "BombGun154Napalm 1", "BombGun154Napalm 1", 
            null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x5002x154dt", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, 
            "BombGun500lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x5002x154dt8xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, 
            "BombGun500lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x5002x154dt8xhvarap", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, 
            "BombGun500lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x5008xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", 
            null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x5008xhvarap", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", 
            null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x500", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", 
            "BombGun500lbs 1", null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x5008xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", 
            "BombGun500lbs 1", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x5008xhvarap", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", 
            "BombGun500lbs 1", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x10008xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, null, null, null, null, 
            "BombGun1000lbs 1", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x10008xhvarap", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, null, null, null, null, 
            "BombGun1000lbs 1", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x10002x154dt", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, 
            "BombGun1000lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x10002x154dt8xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, 
            "BombGun1000lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x10002x154dt8xhvarap", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, 
            "BombGun1000lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x10002x500", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", 
            "BombGun1000lbs 1", null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x10002x5008xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", 
            "BombGun1000lbs 1", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x10002x5008xhvarap", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", 
            "BombGun1000lbs 1", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1000", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x10008xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x10008xhvarap", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x1000", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", 
            "BombGun1000lbs 1", null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x10008xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", 
            "BombGun1000lbs 1", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3x10008xhvarap", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", 
            "BombGun1000lbs 1", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x2000", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, 
            "BombGun2000lbs 1", null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x20008xhvargp", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, null, null, null, null, 
            "BombGun2000lbs 1", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x20008xhvarap", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, null, null, null, null, 
            "BombGun2000lbs 1", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x20002x1000", new java.lang.String[] {
            "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", 
            "BombGun2000lbs 1", null, null, null, null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}