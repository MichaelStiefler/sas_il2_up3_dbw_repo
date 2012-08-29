// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KI_100_IKO.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            KI_100, PaintSchemeFMPar05, PaintSchemeBCSPar01, NetAircraft, 
//            Aircraft

public class KI_100_IKO extends com.maddox.il2.objects.air.KI_100
{

    public KI_100_IKO()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.KI_100_IKO.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ki-100");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ki-100-I(Ko)(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/Ki-100-I(Ko)(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeBCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1947F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ki-100-I.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitKI_100.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.85935F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHo103k 250", "MGunHo103k 250", "MGunHo5si 250", "MGunHo5si 250", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunHo103k 250", "MGunHo103k 250", "MGunHo5si 250", "MGunHo5si 250", "BombGun250kgJ 1", "BombGun250kgJ 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
