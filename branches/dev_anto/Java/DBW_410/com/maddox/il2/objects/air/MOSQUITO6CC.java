// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MOSQUITO6CC.java

package com.maddox.il2.objects.air;

// Referenced classes of package com.maddox.il2.objects.air:
//            MOSQUITO, PaintSchemeFMPar04, TypeFighter, TypeStormovik, 
//            NetAircraft, Aircraft

public class MOSQUITO6CC extends com.maddox.il2.objects.air.MOSQUITO
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeStormovik
{

    public MOSQUITO6CC()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Mosquito");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Mosquito_FB_MkVICC(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar04())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1944F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Mosquito-FBMkVI.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitMosquito6.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.6731F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 1, 1, 3, 3, 
            3, 3, 9, 9, 2, 2, 2, 2, 2, 2, 
            2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02", 
            "_BombSpawn01", "_BombSpawn02", "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalRock07", "_ExternalRock08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, 
            "BombGun250lbsE 1", "BombGun250lbsE 1", null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x250", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "BombGun250lbsE 1", "BombGun250lbsE 1", 
            "BombGun250lbsE 1", "BombGun250lbsE 1", null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, 
            "BombGun500lbsE 1", "BombGun500lbsE 1", null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x500", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "BombGun500lbsE 1", "BombGun500lbsE 1", 
            "BombGun500lbsE 1", "BombGun500lbsE 1", null, null, null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x60rock", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, 
            null, null, "PylonTEMPESTPLN3 1", "PylonTEMPESTPLN4 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", 
            "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x60rock+2x250", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, 
            "BombGun250lbsE 1", "BombGun250lbsE 1", "PylonTEMPESTPLN3 1", "PylonTEMPESTPLN4 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1", 
            "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x25rock", new java.lang.String[] {
        		"MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, 
        		null, null, "PylonTEMPESTPLN3 1", "PylonTEMPESTPLN4 1", "RocketGunHVAR5BEAU25AP", "RocketGunHVAR5BEAU25AP", "RocketGunHVAR5BEAU25AP", "RocketGunHVAR5BEAU25AP", "RocketGunHVAR5BEAU25AP", 
        		"RocketGunHVAR5BEAU25AP", "RocketGunHVAR5BEAU25AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x25rock+2x250", new java.lang.String[] {
        		"MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, 
        		"BombGun250lbsE 1", "BombGun250lbsE 1", "PylonTEMPESTPLN3 1", "PylonTEMPESTPLN4 1", "RocketGunHVAR5BEAU25AP", "RocketGunHVAR5BEAU25AP", "RocketGunHVAR5BEAU25AP", "RocketGunHVAR5BEAU25AP", "RocketGunHVAR5BEAU25AP", 
        		"RocketGunHVAR5BEAU25AP", "RocketGunHVAR5BEAU25AP"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x25ASrock", new java.lang.String[] {
        		"MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, 
        		null, null, "PylonTEMPESTPLN3 1", "PylonTEMPESTPLN4 1","RocketGunHVAR5BEAU25AS", "RocketGunHVAR5BEAU25AS", "RocketGunHVAR5BEAU25AS", "RocketGunHVAR5BEAU25AS", "RocketGunHVAR5BEAU25AS", 
        		"RocketGunHVAR5BEAU25AS", "RocketGunHVAR5BEAU25AS"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8x25ASrock+2x250", new java.lang.String[] {
        		"MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", "MGunHispanoMkIkpzl 150", null, null, 
        		"BombGun250lbsE 1", "BombGun250lbsE 1", "PylonTEMPESTPLN3 1", "PylonTEMPESTPLN4 1","RocketGunHVAR5BEAU25AS", "RocketGunHVAR5BEAU25AS", "RocketGunHVAR5BEAU25AS", "RocketGunHVAR5BEAU25AS", "RocketGunHVAR5BEAU25AS", 
        		"RocketGunHVAR5BEAU25AS", "RocketGunHVAR5BEAU25AS"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}