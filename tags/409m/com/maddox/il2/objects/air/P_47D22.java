// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_47D22.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_47, PaintSchemeFMPar01, PaintSchemeFMPar06, NetAircraft, 
//            Aircraft

public class P_47D22 extends com.maddox.il2.objects.air.P_47
{

    public P_47D22()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.P_47D22.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-47");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-47D-22(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/P-47D-22(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1947.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-47D-22.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitP_47D10.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.9879F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 1, 1, 9, 3, 
            3, 3, 9, 9, 2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalDev01", "_ExternalBomb02", 
            "_ExternalBomb03", "_ExternalBomb01", "_ExternalDev02", "_ExternalDev03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "overload", new java.lang.String[] {
            "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "tank", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "FuelTankGun_Tank75gal", null, 
            null, null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "tank2x500", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "FuelTankGun_Tank75gal", "BombGun500lbs", 
            "BombGun500lbs", null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "tank6x45", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "FuelTankGun_Tank75gal", null, 
            null, null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "tank2x5006x45", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "FuelTankGun_Tank75gal", "BombGun500lbs", 
            "BombGun500lbs", null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6x45", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, null, 
            null, null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, "BombGun500lbs", 
            "BombGun500lbs", null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x5006x45", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, "BombGun500lbs", 
            "BombGun500lbs", null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x1000", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, null, 
            null, "BombGun1000lbs", null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x10002x500", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, "BombGun500lbs", 
            "BombGun500lbs", "BombGun1000lbs", null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x10006x45", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, null, 
            null, "BombGun1000lbs", "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x10002x5006x45", new java.lang.String[] {
            "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, "BombGun500lbs", 
            "BombGun500lbs", "BombGun1000lbs", "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
