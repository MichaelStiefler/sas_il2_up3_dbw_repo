// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SPITFIRE9EHF.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            SPITFIRE9, PaintSchemeFMPar04, NetAircraft, Aircraft

public class SPITFIRE9EHF extends com.maddox.il2.objects.air.SPITFIRE9
{

    public SPITFIRE9EHF()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.SPITFIRE9EHF.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Spit");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SpitfireMkIXe(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/SpitfireMkIXe(GB)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/SpitfireHF_IXC.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitSpit9C.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.5926F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 9, 9, 9, 3, 3, 9, 
            3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalDev08", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev01", 
            "_ExternalBomb01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "30gal", new java.lang.String[] {
            "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit30", null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "45gal", new java.lang.String[] {
            "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit45", null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "90gal", new java.lang.String[] {
            "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit90", null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "250lb", new java.lang.String[] {
            "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "250lb30gal", new java.lang.String[] {
            "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit30", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "250lb45gal", new java.lang.String[] {
            "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit45", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "250lb90gal", new java.lang.String[] {
            "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit90", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "500lb", new java.lang.String[] {
            "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", null, null, null, null, null, "PylonSpitC", 
            "BombGun500lbsE 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "500lb250lb", new java.lang.String[] {
            "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", "PylonSpitC", 
            "BombGun500lbsE 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
