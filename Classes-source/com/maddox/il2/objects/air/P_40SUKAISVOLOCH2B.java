// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_40SUKAISVOLOCH2B.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_40SUKAISVOLOCH, PaintSchemeFMPar03, NetAircraft

public class P_40SUKAISVOLOCH2B extends com.maddox.il2.objects.air.P_40SUKAISVOLOCH
{

    public P_40SUKAISVOLOCH2B()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.P_40SUKAISVOLOCH2B.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-40");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/TomahawkMkIIb(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/TomahawkMkIIb(GB)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-40C.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitP_40C.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.0728F);
        com.maddox.il2.objects.air.P_40SUKAISVOLOCH2B.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 3, 9
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCH2B.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02"
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCH2B.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240", null, null, null
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCH2B.weaponsRegister(class1, "1x75dt", new java.lang.String[] {
            "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240", "PylonP39PLN1", null, "FuelTankGun_Tank75gal"
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCH2B.weaponsRegister(class1, "1x100", new java.lang.String[] {
            "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240", "PylonP39PLN1", "BombGun100Lbs 1", null
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCH2B.weaponsRegister(class1, "1x250", new java.lang.String[] {
            "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240", "PylonP39PLN1", "BombGun250lbs 1", null
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCH2B.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
