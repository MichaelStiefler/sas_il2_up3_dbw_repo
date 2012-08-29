// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BEAU21.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            BEAU, PaintSchemeBMPar02, TypeFighter, TypeStormovik, 
//            NetAircraft

public class BEAU21 extends com.maddox.il2.objects.air.BEAU
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeStormovik
{

    public BEAU21()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.BEAU21.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Beaufighter");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/BeaufighterMk21(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/BeaufighterMk21(AU)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1965.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/BeaufighterMk21.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitBEAU21.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7394F);
        com.maddox.il2.objects.air.BEAU21.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 1, 1, 9, 9, 
            3, 3, 9, 3, 9, 9, 2, 2, 2, 2, 
            2, 2, 2, 2
        });
        com.maddox.il2.objects.air.BEAU21.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalDev02", "_ExternalDev03", 
            "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev04", "_ExternalDev05", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", 
            "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08"
        });
        com.maddox.il2.objects.air.BEAU21.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.BEAU21.weaponsRegister(class1, "8xrock", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, null, null, "PylonBEAUPLN2", "PylonBEAUPLN3", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", 
            "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU"
        });
        com.maddox.il2.objects.air.BEAU21.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "PylonBEAUPLN1", "PylonBEAUPLN1", 
            "BombGun250lbsE 1", "BombGun250lbsE 1", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.BEAU21.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "PylonBEAUPLN1", "PylonBEAUPLN1", 
            "BombGun500lbsE 1", "BombGun500lbsE 1", null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.BEAU21.weaponsRegister(class1, "1xtorp", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", "MGunHispanoMkIkpzl 250", null, null, 
            null, null, "PylonBEAUPLN4", "BombGunTorpMk13a", null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.BEAU21.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
