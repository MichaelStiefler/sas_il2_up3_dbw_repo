// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_39D2.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_39, PaintSchemeFMPar02, NetAircraft, Aircraft

public class P_39D2 extends com.maddox.il2.objects.air.P_39
{

    public P_39D2()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.P_39D2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P39");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-39D-2(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/P-39D-2(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-39D.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitP_39N1.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8941F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 1, 9, 3, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_CANNON01", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunHispanoMkIki 60", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "m4", new java.lang.String[] {
            "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunM4ki 30", null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x500", new java.lang.String[] {
            "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunHispanoMkIki 60", "PylonP39PLN1", "BombGun500lbs 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x75dt", new java.lang.String[] {
            "MGunBrowning50si 200", "MGunBrowning50si 200", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunBrowning303kj 500", "MGunHispanoMkIki 60", "PylonP39PLN1", null, "FuelTankGun_Tank75gal"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
