// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   I_15BIS.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            I_15xyz, PaintSchemeFCSPar08, NetAircraft, PaintScheme, 
//            Aircraft

public class I_15BIS extends com.maddox.il2.objects.air.I_15xyz
{

    public I_15BIS()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.I_15BIS.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-15bis");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-15bis/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFCSPar08());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
        com.maddox.rts.Property.set(class1, "yearService", 1937F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1942F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-15bis.fmd");
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.84305F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 
            2, 2, 3, 3, 3, 3, 9, 9, 9, 9, 
            9, 9, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalRock07", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xAO10", new java.lang.String[] {
            "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, 
            null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xAO10_2xFAB50", new java.lang.String[] {
            "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, 
            null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunFAB50 1", "BombGunFAB50 1", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB50", new java.lang.String[] {
            "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, 
            null, null, null, null, "BombGunFAB50", "BombGunFAB50", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xRS82", new java.lang.String[] {
            "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", 
            null, null, null, null, null, null, null, null, "PylonRO_82_1", "PylonRO_82_1", 
            "PylonRO_82_1", "PylonRO_82_1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
