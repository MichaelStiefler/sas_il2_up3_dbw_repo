// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SB_2M103.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            SB, PaintSchemeBMPar00, PaintSchemeBCSPar01, NetAircraft

public class SB_2M103 extends com.maddox.il2.objects.air.SB
{

    public SB_2M103()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.SB_2M103.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "SB");
        com.maddox.rts.Property.set(class1, "meshNameDemo", "3DO/Plane/SB-2M-103(Russian)/hier.him");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SB-2M-103(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/SB-2M-103(Russian)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeBCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/SB-2M-103.fmd");
        com.maddox.il2.objects.air.SB_2M103.weaponTriggersRegister(class1, new int[] {
            10, 10, 11, 12, 3, 3, 3, 3, 3, 3, 
            3, 3, 3
        });
        com.maddox.il2.objects.air.SB_2M103.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", 
            "_BombSpawn07", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.SB_2M103.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASt 960", "MGunShKASt 960", "MGunShKASt 1000", "MGunShKASt 500", null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.SB_2M103.weaponsRegister(class1, "6xfab50", new java.lang.String[] {
            "MGunShKASt 960", "MGunShKASt 960", "MGunShKASt 1000", "MGunShKASt 500", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", 
            null, null, null
        });
        com.maddox.il2.objects.air.SB_2M103.weaponsRegister(class1, "6xfab100", new java.lang.String[] {
            "MGunShKASt 700", "MGunShKASt 700", "MGunShKASt 1000", "MGunShKASt 300", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", 
            null, null, null
        });
        com.maddox.il2.objects.air.SB_2M103.weaponsRegister(class1, "1xfab250", new java.lang.String[] {
            "MGunShKASt 960", "MGunShKASt 960", "MGunShKASt 1000", "MGunShKASt 500", null, null, null, null, null, null, 
            "BombGunFAB250", null, null
        });
        com.maddox.il2.objects.air.SB_2M103.weaponsRegister(class1, "2xfab250", new java.lang.String[] {
            "MGunShKASt 960", "MGunShKASt 960", "MGunShKASt 1000", "MGunShKASt 500", null, null, null, null, null, null, 
            null, "BombGunFAB250", "BombGunFAB250"
        });
        com.maddox.il2.objects.air.SB_2M103.weaponsRegister(class1, "2xfab500", new java.lang.String[] {
            "MGunShKASt 960", "MGunShKASt 960", "MGunShKASt 1000", "MGunShKASt 500", null, null, null, null, null, null, 
            null, "BombGunFAB500", "BombGunFAB500"
        });
        com.maddox.il2.objects.air.SB_2M103.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
