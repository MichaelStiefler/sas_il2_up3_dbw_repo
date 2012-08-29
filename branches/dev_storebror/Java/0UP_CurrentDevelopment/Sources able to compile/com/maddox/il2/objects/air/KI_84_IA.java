package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class KI_84_IA extends KI_84
{

    public KI_84_IA()
    {
    }

    static 
    {
        java.lang.Class class1 = KI_84_IA.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ki-84");
        com.maddox.rts.Property.set(class1, "meshNameDemo", "3DO/Plane/Ki-84-Ia(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ki-84-Ia(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/Ki-84-Ia(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1943.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ki-84-Ia.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", CockpitKI_84_IA.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.0985F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3, 9, 9, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb01", "_ExternalBomb02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHo103s 350", "MGunHo103s 350", "MGunHo5k 150", "MGunHo5k 150", null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x100", new java.lang.String[] {
            "MGunHo103s 350", "MGunHo103s 350", "MGunHo5k 150", "MGunHo5k 150", "BombGun100kgJ 1", "BombGun100kgJ 1", "PylonKI84PLN2", "PylonKI84PLN2", null, null
        });
        Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunHo103s 350", "MGunHo103s 350", "MGunHo5k 150", "MGunHo5k 150", "BombGun250kgJ 1", "BombGun250kgJ 1", "PylonKI84PLN2", "PylonKI84PLN2", null, null
        });
        Aircraft.weaponsRegister(class1, "2tank200", new java.lang.String[] {
            "MGunHo103s 350", "MGunHo103s 350", "MGunHo5k 150", "MGunHo5k 150", null, null, "PylonKI84PLN2", "PylonKI84PLN2", "FuelTankGun_TankKi84", "FuelTankGun_TankKi84"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
