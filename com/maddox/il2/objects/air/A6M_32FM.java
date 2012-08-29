// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   A6M_32FM.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            JC_A6M, PaintSchemeFMPar02, PaintSchemeFCSPar02, NetAircraft, 
//            Aircraft

public class A6M_32FM extends com.maddox.il2.objects.air.JC_A6M
{

    public A6M_32FM()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.A6M_32FM.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "A6M");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/A6M-32FM(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar02())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_ja", "3DO/Plane/A6M-32FM(ja)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_ja", ((java.lang.Object) (new PaintSchemeFCSPar02())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1942.1F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1942.9F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/A6M3-32.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitA6M2.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 1.01885F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 9, 9, 3, 9, 9, 3, 
            3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb03", 
            "_ExternalBomb04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No2s 100", "MGunType99No2s 100", null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xdt", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No2s 100", "MGunType99No2s 100", "FuelTankGun_Tank0 1", null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x30", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No2s 100", "MGunType99No2s 100", null, null, null, "PylonA6MPLN4 1", "PylonA6MPLN4 1", "BombGun30kgJ2 1", 
            "BombGun30kgJ2 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xdt+2x30", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No2s 100", "MGunType99No2s 100", "FuelTankGun_Tank0 1", null, null, "PylonA6MPLN4 1", "PylonA6MPLN4 1", "BombGun30kgJ2 1", 
            "BombGun30kgJ2 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xType3", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No2s 100", "MGunType99No2s 100", null, null, null, "PylonA6MPLN4 1", "PylonA6MPLN4 1", "BombGunType3AntiAir 1", 
            "BombGunType3AntiAir 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xdt+2xType3", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No2s 100", "MGunType99No2s 100", "FuelTankGun_Tank0 1", null, null, "PylonA6MPLN4 1", "PylonA6MPLN4 1", "BombGunType3AntiAir 1", 
            "BombGunType3AntiAir 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x60", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No2s 100", "MGunType99No2s 100", null, null, null, "PylonA6MPLN2 1", "PylonA6MPLN2 1", "BombGun60kgJ2 1", 
            "BombGun60kgJ2 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
