// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   KI_84_IA.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            KI_84, PaintSchemeFMPar05, PaintSchemeFCSPar05, NetAircraft, 
//            Aircraft

public class KI_84_IA extends com.maddox.il2.objects.air.KI_84
{

    public KI_84_IA()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
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
        java.lang.Class class1 = com.maddox.il2.objects.air.KI_84_IA.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Ki-84");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshNameDemo", "3DO/Plane/Ki-84-Ia(ja)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Ki-84-Ia(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_ja", "3DO/Plane/Ki-84-Ia(ja)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_ja", ((java.lang.Object) (new PaintSchemeFCSPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1943.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Ki-84-Ia.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitKI_84_IA.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.0985F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3, 9, 9, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHo103s 350", "MGunHo103s 350", "MGunHo5k 150", "MGunHo5k 150", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100", new java.lang.String[] {
            "MGunHo103s 350", "MGunHo103s 350", "MGunHo5k 150", "MGunHo5k 150", "BombGun100kgJ 1", "BombGun100kgJ 1", "PylonKI84PLN2", "PylonKI84PLN2", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunHo103s 350", "MGunHo103s 350", "MGunHo5k 150", "MGunHo5k 150", "BombGun250kgJ 1", "BombGun250kgJ 1", "PylonKI84PLN2", "PylonKI84PLN2", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2tank200", new java.lang.String[] {
            "MGunHo103s 350", "MGunHo103s 350", "MGunHo5k 150", "MGunHo5k 150", null, null, "PylonKI84PLN2", "PylonKI84PLN2", "FuelTankGun_TankKi84", "FuelTankGun_TankKi84"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
