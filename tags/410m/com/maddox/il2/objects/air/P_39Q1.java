// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_39Q1.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_39, PaintSchemeFMPar04, NetAircraft

public class P_39Q1 extends com.maddox.il2.objects.air.P_39
{

    public P_39Q1()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.P_39Q1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P39");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/P-39Q-1/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-39Q-1.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitP_39Q1.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8941F);
        com.maddox.il2.objects.air.P_39Q1.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 3
        });
        com.maddox.il2.objects.air.P_39Q1.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_ExternalBomb01"
        });
        com.maddox.il2.objects.air.P_39Q1.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "MGunM4ki 30", null
        });
        com.maddox.il2.objects.air.P_39Q1.weaponsRegister(class1, "1xFAB250", new java.lang.String[] {
            "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "MGunM4ki 30", "BombGunFAB250 1"
        });
        com.maddox.il2.objects.air.P_39Q1.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}