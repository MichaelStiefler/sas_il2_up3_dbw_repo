// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   J2M3.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            J2M, PaintSchemeFMPar01, PaintSchemeFCSPar02, NetAircraft

public class J2M3 extends com.maddox.il2.objects.air.J2M
{

    public J2M3()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.J2M3.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "J2M");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/J2M3(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/J2M3(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/J2M3.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitJ2M3.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.113F);
        com.maddox.il2.objects.air.J2M3.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1, 3, 3
        });
        com.maddox.il2.objects.air.J2M3.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.J2M3.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHo5k 210", "MGunHo5k 190", "MGunHo5k 190", "MGunHo5k 210", null, null
        });
        com.maddox.il2.objects.air.J2M3.weaponsRegister(class1, "2x60", new java.lang.String[] {
            "MGunHo5k 210", "MGunHo5k 190", "MGunHo5k 190", "MGunHo5k 210", "BombGun60kgJ 1", "BombGun60kgJ 1"
        });
        com.maddox.il2.objects.air.J2M3.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
