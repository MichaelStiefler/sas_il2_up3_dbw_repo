package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class KI_84_IC extends KI_84
{
  static
  {
    Class localClass = KI_84_IC.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Ki-84");
    Property.set(localClass, "meshName", "3DO/Plane/Ki-84-Ic(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_ja", "3DO/Plane/Ki-84-Ic(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar05());

    Property.set(localClass, "yearService", 1945.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Ki-84-Ia.fmd");
    Property.set(localClass, "cockpitClass", CockpitKI_84_IB.class);
    Property.set(localClass, "LOSElevation", 0.0985F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 3, 3, 9, 9, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb01", "_ExternalBomb02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunHo5s 150", "MGunHo5s 150", "MGunHo115k 65", "MGunHo115k 65", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x100", new String[] { "MGunHo5s 150", "MGunHo5s 150", "MGunHo115k 65", "MGunHo115k 65", "BombGun100kgJ 1", "BombGun100kgJ 1", "PylonKI84PLN2", "PylonKI84PLN2", null, null });

    Aircraft.weaponsRegister(localClass, "2x250", new String[] { "MGunHo5s 150", "MGunHo5s 150", "MGunHo115k 65", "MGunHo115k 65", "BombGun250kgJ 1", "BombGun250kgJ 1", "PylonKI84PLN2", "PylonKI84PLN2", null, null });

    Aircraft.weaponsRegister(localClass, "2tank200", new String[] { "MGunHo5s 150", "MGunHo5s 150", "MGunHo115k 65", "MGunHo115k 65", null, null, "PylonKI84PLN2", "PylonKI84PLN2", "FuelTankGun_TankKi84", "FuelTankGun_TankKi84" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null });
  }
}