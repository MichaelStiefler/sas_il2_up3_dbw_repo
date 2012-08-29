package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class A6M_52NF extends A6M
{

    public A6M_52NF()
    {
    }

    static 
    {
        java.lang.Class class1 = A6M_52NF.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "A6M");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/A6M-52NF(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/A6M-52NF(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/A6M5a.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitA6M5nf.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.01885F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 9, 9, 3, 9, 9, 
            3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalBomb01", "_ExternalDev01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", 
            "_ExternalBomb03", "_ExternalBomb04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No2s 100", "MGunType99No2s 100", "MGunType99No2si 100", null, null, null, null, null, 
            null, null
        });
        Aircraft.weaponsRegister(class1, "1xdt", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No2s 100", "MGunType99No2s 100", "MGunType99No2si 100", "FuelTankGun_Tank0 1", null, null, null, null, 
            null, null
        });
        Aircraft.weaponsRegister(class1, "2xType3", new java.lang.String[] {
            "MGunMG15spzl 680", "MGunMG15spzl 680", "MGunType99No2s 100", "MGunType99No2s 100", "MGunType99No2si 100", null, null, null, "PylonA6MPLN4 1", "PylonA6MPLN4 1", 
            "BombGunType3AntiAir 1", "BombGunType3AntiAir 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
