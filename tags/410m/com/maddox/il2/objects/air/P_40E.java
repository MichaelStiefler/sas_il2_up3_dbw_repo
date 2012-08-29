// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_40E.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_40, PaintSchemeFMPar03, NetAircraft

public class P_40E extends com.maddox.il2.objects.air.P_40
{

    public P_40E()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.P_40E.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-40");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-40E(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/P-40E(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "noseart", 1);
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-40E.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitP_40E.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.06965F);
        com.maddox.il2.objects.air.P_40E.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 3, 9
        });
        com.maddox.il2.objects.air.P_40E.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb01"
        });
        com.maddox.il2.objects.air.P_40E.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", null, null
        });
        com.maddox.il2.objects.air.P_40E.weaponsRegister(class1, "500lb", new java.lang.String[] {
            "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "BombGun500lbs", null
        });
        com.maddox.il2.objects.air.P_40E.weaponsRegister(class1, "1000lb", new java.lang.String[] {
            "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "BombGun1000lbs", null
        });
        com.maddox.il2.objects.air.P_40E.weaponsRegister(class1, "droptank", new java.lang.String[] {
            "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", null, "FuelTankGun_Tank75gal2"
        });
        com.maddox.il2.objects.air.P_40E.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
