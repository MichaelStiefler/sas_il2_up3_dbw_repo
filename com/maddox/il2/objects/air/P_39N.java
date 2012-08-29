// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_39N.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_39, PaintSchemeFMPar04, NetAircraft

public class P_39N extends com.maddox.il2.objects.air.P_39
{

    public P_39N()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.P_39N.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P39");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/P-39N/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-39N.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitP_39N1.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8941F);
        com.maddox.il2.objects.air.P_39N.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 1, 3
        });
        com.maddox.il2.objects.air.P_39N.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_CANNON01", "_ExternalBomb01"
        });
        com.maddox.il2.objects.air.P_39N.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunM4ki 30", null
        });
        com.maddox.il2.objects.air.P_39N.weaponsRegister(class1, "extra", new java.lang.String[] {
            "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303kj 1000", "MGunBrowning303kj 1000", "MGunBrowning303kj 1000", "MGunBrowning303kj 1000", "MGunM4ki 30", null
        });
        com.maddox.il2.objects.air.P_39N.weaponsRegister(class1, "1xFAB250", new java.lang.String[] {
            "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunM4ki 30", "BombGunFAB250 1"
        });
        com.maddox.il2.objects.air.P_39N.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
