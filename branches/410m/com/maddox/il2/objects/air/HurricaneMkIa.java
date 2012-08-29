// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HurricaneMkIa.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Hurricane, PaintSchemeFCSPar02, PaintSchemeFMPar00, NetAircraft

public class HurricaneMkIa extends com.maddox.il2.objects.air.Hurricane
{

    public HurricaneMkIa()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.HurricaneMkIa.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Hurri");
        com.maddox.rts.Property.set(class1, "meshName_fi", "3DO/Plane/HurricaneMkI(Finnish)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_fi", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/HurricaneMkI(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/HurricaneMkI.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitHURRI.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.965F);
        com.maddox.il2.objects.air.HurricaneMkIa.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 0, 0
        });
        com.maddox.il2.objects.air.HurricaneMkIa.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08"
        });
        com.maddox.il2.objects.air.HurricaneMkIa.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 356", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 308", "MGunBrowning303k 334"
        });
        com.maddox.il2.objects.air.HurricaneMkIa.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
