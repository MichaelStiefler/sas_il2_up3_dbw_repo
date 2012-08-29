// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   I_16TYPE18.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            I_16, PaintSchemeFMPar01, PaintSchemeFCSPar01, TypeTNBFighter, 
//            NetAircraft, Aircraft

public class I_16TYPE18 extends com.maddox.il2.objects.air.I_16
    implements com.maddox.il2.objects.air.TypeTNBFighter
{

    public I_16TYPE18()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.I_16TYPE18.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-16");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-16type18(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/I-16type18/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-16type18.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitI_16TYPE18.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.82595F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 0, 0, 3, 3, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev07", "_ExternalDev08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASk 650", "MGunShKASk 650", "MGunShKASk 750", "MGunShKASk 750", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab50", new java.lang.String[] {
            "MGunShKASk 650", "MGunShKASk 650", "MGunShKASk 750", "MGunShKASk 750", "BombGunFAB50", "BombGunFAB50", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2fab100", new java.lang.String[] {
            "MGunShKASk 650", "MGunShKASk 650", "MGunShKASk 750", "MGunShKASk 750", "BombGunFAB100", "BombGunFAB100", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2tank100", new java.lang.String[] {
            "MGunShKASk 650", "MGunShKASk 650", "MGunShKASk 750", "MGunShKASk 750", null, null, "FuelTankGun_Tank100i16", "FuelTankGun_Tank100i16"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
