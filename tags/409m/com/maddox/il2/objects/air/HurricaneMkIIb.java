// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HurricaneMkIIb.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Hurricane, PaintSchemeFMPar01, NetAircraft, Aircraft

public class HurricaneMkIIb extends com.maddox.il2.objects.air.Hurricane
{

    public HurricaneMkIIb()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.HurricaneMkIIb.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Hurri");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/HurricaneMkIIb(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/HurricaneMkII.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitHURRII.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.965F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            0, 0, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", 
            "_MGUN11", "_MGUN12", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", "MGunBrowning303k 329", "MGunBrowning303k 361", 
            "MGunBrowning303k 334", "MGunBrowning303k 335", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250lb", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", "MGunBrowning303k 329", "MGunBrowning303k 361", 
            "MGunBrowning303k 334", "MGunBrowning303k 335", "BombGun250lbsE", "BombGun250lbsE"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xfab100", new java.lang.String[] {
            "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", "MGunBrowning303k 329", "MGunBrowning303k 361", 
            "MGunBrowning303k 334", "MGunBrowning303k 335", "BombGunFAB100", "BombGunFAB100"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
