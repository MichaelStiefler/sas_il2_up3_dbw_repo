package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class MIG_29 extends MIG_3
{
  static
  {
    Class localClass = MIG_29.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "MIG");
    Property.set(localClass, "meshName", "3do/plane/MIG-3U(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00());
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);

    Property.set(localClass, "FlightModel", "FlightModels/MiG-29.fmd");
    Property.set(localClass, "cockpitClass", CockpitMIG_3.class);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 3, 3, 3, 3, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN04", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShVAKs 250", "MGunShVAKs 250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xBK", new String[] { "MGunShVAKs 250", "MGunShVAKs 250", "MGunUBk 250", "MGunUBk 250", null, null, null, null, "PylonMiG_3_BK", "PylonMiG_3_BK", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}