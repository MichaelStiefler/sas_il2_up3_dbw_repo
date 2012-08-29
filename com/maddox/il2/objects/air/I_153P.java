// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   I_153P.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            I_153_M62, PaintSchemeFMPar00, NetAircraft, Aircraft

public class I_153P extends com.maddox.il2.objects.air.I_153_M62
{

    public I_153P()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.I_153P.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-153");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-153/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1939.2F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-153-M62.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 
            2, 2, 3, 3, 3, 3, 9, 9, 9, 9, 
            9, 9, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalRock07", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xAO10", new java.lang.String[] {
            "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, 
            null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xAO10_2xFAB50", new java.lang.String[] {
            "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, 
            null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunFAB50", "BombGunFAB50", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB50", new java.lang.String[] {
            "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunFAB50", "BombGunFAB50", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xFAB50", new java.lang.String[] {
            "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, 
            null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB100", new java.lang.String[] {
            "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunFAB100", "BombGunFAB100", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB50_2xFAB100", new java.lang.String[] {
            "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, 
            null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB100", "BombGunFAB100", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8xRS82", new java.lang.String[] {
            "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", 
            "RocketGunRS82", "RocketGunRS82", null, null, null, null, "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", 
            "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
