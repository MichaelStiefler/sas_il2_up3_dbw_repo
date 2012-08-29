package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class I_16TYPE18 extends I_16
  implements TypeTNBFighter
{
  static
  {
    Class localClass = I_16TYPE18.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "I-16");
    Property.set(localClass, "meshName", "3DO/Plane/I-16type18(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ru", "3DO/Plane/I-16type18/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar01());

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/I-16type18.fmd");
    Property.set(localClass, "cockpitClass", CockpitI_16TYPE18.class);
    Property.set(localClass, "LOSElevation", 0.82595F);

    weaponTriggersRegister(localClass, new int[] { 1, 1, 0, 0, 3, 3, 9, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev07", "_ExternalDev08" });

    weaponsRegister(localClass, "default", new String[] { "MGunShKASk 650", "MGunShKASk 650", "MGunShKASk 750", "MGunShKASk 750", null, null, null, null });

    weaponsRegister(localClass, "2fab50", new String[] { "MGunShKASk 650", "MGunShKASk 650", "MGunShKASk 750", "MGunShKASk 750", "BombGunFAB50", "BombGunFAB50", null, null });

    weaponsRegister(localClass, "2fab100", new String[] { "MGunShKASk 650", "MGunShKASk 650", "MGunShKASk 750", "MGunShKASk 750", "BombGunFAB100", "BombGunFAB100", null, null });

    weaponsRegister(localClass, "2tank100", new String[] { "MGunShKASk 650", "MGunShKASk 650", "MGunShKASk 750", "MGunShKASk 750", null, null, "FuelTankGun_Tank100i16", "FuelTankGun_Tank100i16" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}