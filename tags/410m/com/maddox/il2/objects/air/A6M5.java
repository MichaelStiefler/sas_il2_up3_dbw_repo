// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   A6M5.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            A6M, PaintSchemeFMPar02, PaintSchemeFCSPar02, NetAircraft

public class A6M5 extends com.maddox.il2.objects.air.A6M
{

    public A6M5()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.A6M5.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "A6M");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/A6M5(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/A6M5(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/A6M5a.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitA6M5a.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.01885F);
        com.maddox.il2.objects.air.A6M5.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 9, 9, 3, 9, 9, 3, 
            3
        });
        com.maddox.il2.objects.air.A6M5.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb03", 
            "_ExternalBomb04"
        });
        com.maddox.il2.objects.air.A6M5.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15spzl 500", "MGunMG15spzl 500", "MGunMGFFk 100", "MGunMGFFk 100", null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.A6M5.weaponsRegister(class1, "1xdt", new java.lang.String[] {
            "MGunMG15spzl 500", "MGunMG15spzl 500", "MGunMGFFk 100", "MGunMGFFk 100", "FuelTankGun_Tank0", null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.A6M5.weaponsRegister(class1, "1x250", new java.lang.String[] {
            "MGunMG15spzl 500", "MGunMG15spzl 500", "MGunMGFFk 100", "MGunMGFFk 100", null, "PylonA6MPLN1", "BombGun250kg", null, null, null, 
            null
        });
        com.maddox.il2.objects.air.A6M5.weaponsRegister(class1, "2x60", new java.lang.String[] {
            "MGunMG15spzl 500", "MGunMG15spzl 500", "MGunMGFFk 100", "MGunMGFFk 100", null, null, null, "PylonA6MPLN2", "PylonA6MPLN2", "BombGun50kg", 
            "BombGun50kg"
        });
        com.maddox.il2.objects.air.A6M5.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}