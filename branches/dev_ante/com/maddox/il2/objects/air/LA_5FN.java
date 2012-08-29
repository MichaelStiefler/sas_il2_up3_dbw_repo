package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class LA_5FN extends LA_X
{
  static
  {
    Class localClass = LA_5FN.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "La");
    Property.set(localClass, "meshName", "3DO/Plane/La-5FN(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/La-5FN.fmd");
    Property.set(localClass, "cockpitClass", CockpitLA_5FN.class);
    Property.set(localClass, "LOSElevation", 0.750618F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 3, 3, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShVAKs 170", "MGunShVAKs 200", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB50", new String[] { "MGunShVAKs 170", "MGunShVAKs 200", "BombGunFAB50 1", "BombGunFAB50 1", null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB100", new String[] { "MGunShVAKs 170", "MGunShVAKs 200", "BombGunFAB100 1", "BombGunFAB100 1", null, null });

    Aircraft.weaponsRegister(localClass, "2xDROPTANK", new String[] { "MGunShVAKs 170", "MGunShVAKs 200", null, null, "FuelTankGun_Tank80", "FuelTankGun_Tank80" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}