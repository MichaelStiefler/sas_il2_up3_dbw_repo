// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SPITFIRE9M63.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            SPITFIRE9, PaintSchemeFMPar04, NetAircraft

public class SPITFIRE9M63 extends com.maddox.il2.objects.air.SPITFIRE9
{

    public SPITFIRE9M63()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.SPITFIRE9M63.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Spit");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SpitfireMkIXc(Multi1)/hier_early.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/SpitfireMkIXc(GB)/hier_early.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Spitfire-F-IXc-M63-18.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitSpit9C.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.5926F);
        com.maddox.il2.objects.air.SPITFIRE9M63.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 9, 9, 9, 3, 
            3, 9, 3
        });
        com.maddox.il2.objects.air.SPITFIRE9M63.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_ExternalDev08", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb02", 
            "_ExternalBomb03", "_ExternalDev01", "_ExternalBomb01"
        });
        com.maddox.il2.objects.air.SPITFIRE9M63.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.SPITFIRE9M63.weaponsRegister(class1, "30gal", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit30", null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.SPITFIRE9M63.weaponsRegister(class1, "45gal", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit45", null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.SPITFIRE9M63.weaponsRegister(class1, "90gal", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit90", null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.SPITFIRE9M63.weaponsRegister(class1, "250lb", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", null, null
        });
        com.maddox.il2.objects.air.SPITFIRE9M63.weaponsRegister(class1, "250lb30gal", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit30", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", null, null
        });
        com.maddox.il2.objects.air.SPITFIRE9M63.weaponsRegister(class1, "250lb45gal", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit45", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", null, null
        });
        com.maddox.il2.objects.air.SPITFIRE9M63.weaponsRegister(class1, "250lb90gal", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit90", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", null, null
        });
        com.maddox.il2.objects.air.SPITFIRE9M63.weaponsRegister(class1, "500lb", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, null, 
            null, "PylonSpitC", "BombGun500lbsE 1"
        });
        com.maddox.il2.objects.air.SPITFIRE9M63.weaponsRegister(class1, "500lb250lb", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", "PylonSpitC", "BombGun500lbsE 1"
        });
        com.maddox.il2.objects.air.SPITFIRE9M63.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
