// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_40SUKAISVOLOCHHAWKA2.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_40SUKAISVOLOCH, PaintSchemeFMPar01, PaintSchemeFCSPar02, NetAircraft

public class P_40SUKAISVOLOCHHAWKA2 extends com.maddox.il2.objects.air.P_40SUKAISVOLOCH
{

    public P_40SUKAISVOLOCHHAWKA2()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.P_40SUKAISVOLOCHHAWKA2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-40");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Hawk81A-2(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/Hawk81A-2(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-40C.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitHAWK.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.0728F);
        com.maddox.il2.objects.air.P_40SUKAISVOLOCHHAWKA2.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCHHAWKA2.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06"
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCHHAWKA2.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240"
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCHHAWKA2.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
