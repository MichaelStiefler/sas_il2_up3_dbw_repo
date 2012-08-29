package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class I_16TYPE24 extends I_16
  implements TypeTNBFighter
{
  static
  {
    Class localClass = I_16TYPE24.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "I-16");
    Property.set(localClass, "meshName", "3DO/Plane/I-16type24(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ru", "3DO/Plane/I-16type24/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar01());

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/I-16type24.fmd");
    Property.set(localClass, "cockpitClass", CockpitI_16TYPE24.class);
    Property.set(localClass, "LOSElevation", 0.82595F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab50", new String[] { "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2fab100", new String[] { "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2tank100", new String[] { "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "FuelTankGun_Tank100i16", "FuelTankGun_Tank100i16" });

    Aircraft.weaponsRegister(localClass, "6rs82", new String[] { "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", null, null });

    Aircraft.weaponsRegister(localClass, "6rs82tank", new String[] { "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "FuelTankGun_Tank100i16", "FuelTankGun_Tank100i16" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}