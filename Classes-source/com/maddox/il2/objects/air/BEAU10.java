// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BEAU10.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            BEAU, PaintSchemeBMPar02, TypeFighter, TypeStormovik, 
//            NetAircraft, Aircraft

public class BEAU10 extends com.maddox.il2.objects.air.BEAU
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeStormovik
{

    public BEAU10()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.BEAU10.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Beaufighter");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/BeaufighterMk10(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/BeaufighterMk10(GB)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1965.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/BeaufighterMkX.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitBEAU21.class, com.maddox.il2.objects.air.CockpitBEAU10Gun.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7394F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 1, 1, 9, 9, 
            3, 3, 9, 3, 9, 9, 2, 2, 2, 2, 
            2, 2, 2, 2, 9, 3, 9, 3, 10, 0, 
            0
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalDev02", "_ExternalDev03", 
            "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev04", "_ExternalDev05", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", 
            "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalDev06", "_ExternalBomb04", "_ExternalDev07", "_ExternalBomb05", "_MGUN09", "_MGUN10", 
            "_MGUN11"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xfuse250", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonSpitC", "BombGun250lbsE 1", "PylonSpitC", "BombGun250lbsE 1", "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xfuse500", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonSpitC", "BombGun500lbsE 1", "PylonSpitC", "BombGun500lbsE 1", "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250f2x250w", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "PylonBEAUPLN1", "PylonBEAUPLN1", 
            "BombGun250lbsE 1", "BombGun250lbsE 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonSpitC", "BombGun250lbsE 1", "PylonSpitC", "BombGun250lbsE 1", "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500f2x250w", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "PylonBEAUPLN1", "PylonBEAUPLN1", 
            "BombGun250lbsE 1", "BombGun250lbsE 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonSpitC", "BombGun500lbsE 1", "PylonSpitC", "BombGun500lbsE 1", "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500f2x500w", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "PylonBEAUPLN1", "PylonBEAUPLN1", 
            "BombGun500lbsE 1", "BombGun500lbsE 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonSpitC", "BombGun500lbsE 1", "PylonSpitC", "BombGun500lbsE 1", "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x60rock", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, null, null, "PylonBEAUPLN2", "PylonBEAUPLN3", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", 
            "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", null, null, null, null, "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x90rock", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, null, null, "PylonBEAUPLN2", "PylonBEAUPLN3", "RocketGun90", "RocketGun90", "RocketGun90", "RocketGun90", 
            "RocketGun90", "RocketGun90", "RocketGun90", "RocketGun90", null, null, null, null, "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xfuse2508x60rock", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, null, null, "PylonBEAUPLN2", "PylonBEAUPLN3", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", 
            "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "PylonSpitC", "BombGun250lbsE 1", "PylonSpitC", "BombGun250lbsE 1", "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xfuse5008x60rock", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, null, null, "PylonBEAUPLN2", "PylonBEAUPLN3", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", 
            "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "PylonSpitC", "BombGun500lbsE 1", "PylonSpitC", "BombGun500lbsE 1", "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xfuse2508x90rock", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, null, null, "PylonBEAUPLN2", "PylonBEAUPLN3", "RocketGun90", "RocketGun90", "RocketGun90", "RocketGun90", 
            "RocketGun90", "RocketGun90", "RocketGun90", "RocketGun90", "PylonSpitC", "BombGun250lbsE 1", "PylonSpitC", "BombGun250lbsE 1", "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xtorp", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, "PylonBEAUPLN4", "BombGunTorpMk13Brit 1", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xtorp2x250", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "PylonBEAUPLN1", "PylonBEAUPLN1", 
            "BombGun250lbsE 1", "BombGun250lbsE 1", "PylonBEAUPLN4", "BombGunTorpMk13Brit 1", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xtorp8x60rock", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, "PylonBEAUPLN4", "BombGunTorpMk13Brit 1", "PylonBEAUPLN2", "PylonBEAUPLN3", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", 
            "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", null, null, null, null, "MGunVikkersKt 1500", "MGunBrowning303k 350", 
            "MGunBrowning303k 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
