// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LA_5F.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            LA_X, PaintSchemeFMPar03, NetAircraft

public class LA_5F extends com.maddox.il2.objects.air.LA_X
{

    public LA_5F()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.LA_5F.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "La");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/La-5F(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/La-5F.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitLA_5FN.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.750618F);
        com.maddox.il2.objects.air.LA_5F.weaponTriggersRegister(class1, new int[] {
            1, 1, 3, 3, 9, 9
        });
        com.maddox.il2.objects.air.LA_5F.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.LA_5F.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShVAKs 170", "MGunShVAKs 200", null, null, null, null
        });
        com.maddox.il2.objects.air.LA_5F.weaponsRegister(class1, "2xFAB50", new java.lang.String[] {
            "MGunShVAKs 170", "MGunShVAKs 200", "BombGunFAB50 1", "BombGunFAB50 1", null, null
        });
        com.maddox.il2.objects.air.LA_5F.weaponsRegister(class1, "2xFAB100", new java.lang.String[] {
            "MGunShVAKs 170", "MGunShVAKs 200", "BombGunFAB100 1", "BombGunFAB100 1", null, null
        });
        com.maddox.il2.objects.air.LA_5F.weaponsRegister(class1, "2xDROPTANK", new java.lang.String[] {
            "MGunShVAKs 170", "MGunShVAKs 200", null, null, "FuelTankGun_Tank80", "FuelTankGun_Tank80"
        });
        com.maddox.il2.objects.air.LA_5F.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}