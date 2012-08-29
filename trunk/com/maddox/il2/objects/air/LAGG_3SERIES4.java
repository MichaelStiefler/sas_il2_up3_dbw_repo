package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class LAGG_3SERIES4 extends LAGG_3
  implements TypeTNBFighter
{
  static
  {
    Class localClass = LAGG_3SERIES4.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "LaGG");
    Property.set(localClass, "meshName", "3do/plane/LaGG-3series4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1944.5F);

    Property.set(localClass, "FlightModel", "FlightModels/LaGG-3series4.fmd");
    Property.set(localClass, "cockpitClass", CockpitLAGG_3SERIES4.class);
    Property.set(localClass, "LOSElevation", 0.69445F);

    weaponTriggersRegister(localClass, new int[] { 1, 1, 0, 0, 1, 3, 3, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalRock01", "_ExternalDev02", "_ExternalRock02", "_ExternalDev03", "_ExternalRock03", "_ExternalDev04", "_ExternalRock04", "_ExternalDev05", "_ExternalRock05", "_ExternalDev06", "_ExternalRock06", "_ExternalDev07", "_ExternalRock07", "_ExternalDev08", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02" });

    weaponsRegister(localClass, "default", new String[] { "MGunUBs 220", "MGunUBs 220", "MGunShKASs 325", "MGunShKASs 325", "MGunShVAKk 120", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "8xRS82", new String[] { "MGunUBs 220", "MGunUBs 220", "MGunShKASs 325", "MGunShKASs 325", "MGunShVAKk 120", null, null, "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", "PylonRO_82_1", "RocketGunRS82", null, null });

    weaponsRegister(localClass, "2xFAB50", new String[] { "MGunUBs 220", "MGunUBs 220", "MGunShKASs 325", "MGunShKASs 325", "MGunShVAKk 120", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xDROPTANK", new String[] { "MGunUBs 220", "MGunUBs 220", "MGunShKASs 325", "MGunShKASs 325", "MGunShVAKk 120", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "FuelTankGun_Tank80", "FuelTankGun_Tank80" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}