// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IL_2MLate.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            IL_2, PaintSchemeBMPar03, PaintSchemeBCSPar03, NetAircraft

public class IL_2MLate extends com.maddox.il2.objects.air.IL_2
{

    public IL_2MLate()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.IL_2MLate.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "IL2");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Il-2MLate(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar03());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3do/plane/Il-2MLate/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeBCSPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1942.4F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Il-2MLate.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitIL_2_1942.class, com.maddox.il2.objects.air.CockpitIL2_GunnerOpen.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.81F);
        com.maddox.rts.Property.set(class1, "Handicap", 1.2F);
        com.maddox.il2.objects.air.IL_2MLate.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 
            3, 3, 3, 3, 9, 9, 9, 9, 9, 9, 
            3, 3, 10
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_Cannon01", "_Cannon02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalBomb01", "_ExternalBomb02", 
            "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", 
            "_BombSpawn01", "_BombSpawn02", "_MGUN03"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4xRS82", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4xBRS82", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4xRS132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4xROFS132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunROFS132", "RocketGunROFS132", "RocketGunROFS132", "RocketGunROFS132", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4xBRS132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4xM13", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "216xAJ-2", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", null, null, null, null, null, null, 
            "BombGunAmpoule", "BombGunAmpoule", "BombGunAmpoule", "BombGunAmpoule", "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB", null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "30xAO10", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", null, null, null, null, null, null, 
            "BombGunAO10 7", "BombGunAO10 7", "BombGunAO10 8", "BombGunAO10 8", "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB", null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "50xAO10", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", null, null, null, null, null, null, 
            "BombGunAO10 12", "BombGunAO10 12", "BombGunAO10 13", "BombGunAO10 13", "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB", null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4xFAB50", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", null, null, null, null, null, null, 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4xFAB50_4xRS82", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4xFAB50_4xRS132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", null, null, 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4xFAB50_4xROFS132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunROFS132", "RocketGunROFS132", "RocketGunROFS132", "RocketGunROFS132", null, null, 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "6xFAB50", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", null, null, null, null, "BombGunFAB50", "BombGunFAB50", 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "2fab100", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", null, null, null, null, "BombGunFAB100", "BombGunFAB100", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "2fab1004rs82", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "BombGunFAB100", "BombGunFAB100", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "2fab1004rs132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "BombGunFAB100", "BombGunFAB100", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "2fab1004rofs132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunROFS132", "RocketGunROFS132", "RocketGunROFS132", "RocketGunROFS132", "BombGunFAB100", "BombGunFAB100", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "2fab1004brs82", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "BombGunFAB100", "BombGunFAB100", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "2fab1004brs132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "BombGunFAB100", "BombGunFAB100", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "2fab1004m13", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", "BombGunFAB100", "BombGunFAB100", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4xFAB100", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", null, null, null, null, null, null, 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4fab1004rs82", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4fab1004rs132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", null, null, 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4fab1004rofs132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunROFS132", "RocketGunROFS132", "RocketGunROFS132", "RocketGunROFS132", null, null, 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4fab1004brs82", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", null, null, 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4fab1004brs132", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", null, null, 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "4fab1004m13", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", null, null, 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "6xFAB100", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", null, null, null, null, "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "2xFAB250", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", null, null, null, null, "BombGunFAB250", "BombGunFAB250", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "2xVAP250", new java.lang.String[] {
            "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 150", "MGunVYak 150", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonVAP250", "PylonVAP250", 
            "BombGunPhBall", "BombGunPhBall", "MGunUBt 150"
        });
        com.maddox.il2.objects.air.IL_2MLate.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
