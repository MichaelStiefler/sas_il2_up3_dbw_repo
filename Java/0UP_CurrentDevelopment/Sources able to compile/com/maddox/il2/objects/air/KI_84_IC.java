package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class KI_84_IC extends KI_84
{

    public KI_84_IC()
    {
    }

    static 
    {
        java.lang.Class class1 = KI_84_IC.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ki-84");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ki-84-Ic(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/Ki-84-Ic(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1945F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ki-84-Ia.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", CockpitKI_84_IB.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.0985F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3, 9, 9, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb01", "_ExternalBomb02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHo5s 150", "MGunHo5s 150", "MGunHo115k 65", "MGunHo115k 65", null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x100", new java.lang.String[] {
            "MGunHo5s 150", "MGunHo5s 150", "MGunHo115k 65", "MGunHo115k 65", "BombGun100kgJ 1", "BombGun100kgJ 1", "PylonKI84PLN2", "PylonKI84PLN2", null, null
        });
        Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunHo5s 150", "MGunHo5s 150", "MGunHo115k 65", "MGunHo115k 65", "BombGun250kgJ 1", "BombGun250kgJ 1", "PylonKI84PLN2", "PylonKI84PLN2", null, null
        });
        Aircraft.weaponsRegister(class1, "2tank200", new java.lang.String[] {
            "MGunHo5s 150", "MGunHo5s 150", "MGunHo115k 65", "MGunHo115k 65", null, null, "PylonKI84PLN2", "PylonKI84PLN2", "FuelTankGun_TankKi84", "FuelTankGun_TankKi84"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
