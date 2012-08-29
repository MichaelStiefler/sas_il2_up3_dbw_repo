// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   F4UCORSAIR2.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            F4U, PaintSchemeFMPar02, NetAircraft

public class F4UCORSAIR2 extends com.maddox.il2.objects.air.F4U
{

    public F4UCORSAIR2()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.F4UCORSAIR2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "F4U");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/CorsairMkII(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/CorsairMkII(GB)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/F4U-1Aclipped.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitF4U1A.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.0585F);
        com.maddox.il2.objects.air.F4UCORSAIR2.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 
            3, 3, 3, 9, 9
        });
        com.maddox.il2.objects.air.F4UCORSAIR2.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb02", "_ExternalBomb03"
        });
        com.maddox.il2.objects.air.F4UCORSAIR2.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4UCORSAIR2.weaponsRegister(class1, "2x154dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal"
        });
        com.maddox.il2.objects.air.F4UCORSAIR2.weaponsRegister(class1, "1x178dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4UCORSAIR2.weaponsRegister(class1, "1x178dt2x154dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal"
        });
        com.maddox.il2.objects.air.F4UCORSAIR2.weaponsRegister(class1, "2x1541x178dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", 
            null, "BombGun154Napalm 1", "BombGun154Napalm 1", null, null
        });
        com.maddox.il2.objects.air.F4UCORSAIR2.weaponsRegister(class1, "1x5002x154dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun500lbsE 1", null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal"
        });
        com.maddox.il2.objects.air.F4UCORSAIR2.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, "BombGun500lbsE 1", "BombGun500lbsE 1", null, null
        });
        com.maddox.il2.objects.air.F4UCORSAIR2.weaponsRegister(class1, "1x10002x154dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun1000lbs 1", null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal"
        });
        com.maddox.il2.objects.air.F4UCORSAIR2.weaponsRegister(class1, "2x1000", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null
        });
        com.maddox.il2.objects.air.F4UCORSAIR2.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}
