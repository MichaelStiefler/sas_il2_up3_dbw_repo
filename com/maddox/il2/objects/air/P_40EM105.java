// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_40EM105.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_40, PaintSchemeFMPar04, NetAircraft, Aircraft

public class P_40EM105 extends com.maddox.il2.objects.air.P_40
{

    public P_40EM105()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.P_40EM105.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-40");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-40E-M-105(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-40E-M-105.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitP_40E.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.06965F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 
            2, 2, 2, 2, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalBomb01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50k 312", "MGunBrowning50k 291", "MGunBrowning50k 240", "MGunBrowning50k 312", "MGunBrowning50k 291", "MGunBrowning50k 240", null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4rs82", new java.lang.String[] {
            "MGunBrowning50k 312", "MGunBrowning50k 291", "MGunBrowning50k 240", "MGunBrowning50k 312", "MGunBrowning50k 291", "MGunBrowning50k 240", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", 
            "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1fab250", new java.lang.String[] {
            "MGunBrowning50k 312", "MGunBrowning50k 291", "MGunBrowning50k 240", "MGunBrowning50k 312", "MGunBrowning50k 291", "MGunBrowning50k 240", null, null, null, null, 
            null, null, null, null, "BombGunFAB250"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}
