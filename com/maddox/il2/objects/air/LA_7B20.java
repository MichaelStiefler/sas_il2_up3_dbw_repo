package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class LA_7B20 extends LA_X
{
  static
  {
    Class localClass = LA_7B20.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "La");
    Property.set(localClass, "meshName", "3DO/Plane/La-7(3xB20)(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/La-7.fmd");
    Property.set(localClass, "cockpitClass", CockpitLA_7B20.class);
    Property.set(localClass, "LOSElevation", 0.730618F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 1, 3, 3, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunB20s 130", "MGunB20s 150", "MGunB20s 150", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB50", new String[] { "MGunB20s 130", "MGunB20s 130", "MGunB20s 130", "BombGunFAB50 1", "BombGunFAB50 1", null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB100", new String[] { "MGunB20s 130", "MGunB20s 130", "MGunB20s 130", "BombGunFAB100 1", "BombGunFAB100 1", null, null });

    Aircraft.weaponsRegister(localClass, "2xDROPTANK", new String[] { "MGunB20s 130", "MGunB20s 130", "MGunB20s 130", null, null, "FuelTankGun_Tank80", "FuelTankGun_Tank80" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null });
  }
}