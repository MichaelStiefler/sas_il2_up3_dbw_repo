// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   F_80A.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            X_80, PaintSchemeFMPar06, NetAircraft, Aircraft

public class F_80A extends com.maddox.il2.objects.air.P_80
{

    public F_80A()
    {
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.F_80A.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "F-80A");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/P-80A/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1946.9F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1955.3F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/F-80A.fmd");
		Property.set(class1, "cockpitClass", new Class[]{
            com.maddox.il2.objects.air.CockpitF_80A.class
        });
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 
            3, 3, 9, 9, 9, 9, 9, 9, 9, 9, 
            2, 2, 2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", 
            "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500lb", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1000lb", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", null, null, null, null, 
            null, null, "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xHVAR+2x250lb", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "LR-LongRange", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "LR-2x500lb", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "LR-2x1000lb", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "LR-8xHVAR", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", null, null, 
            null, null, "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "LR-8xHVAR+2x250lb", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "ExtraLongRange", new java.lang.String[] {
            "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "MGunBrowning50kAPIT 300", "FuelTankGun_Tank154gal 1", "FuelTankGun_Tank154gal 1", "PylonP51PLN2 1", "PylonP51PLN2 1", 
            "FuelTankGun_Tank75gal2 1", "FuelTankGun_Tank75gal2 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
    }
}