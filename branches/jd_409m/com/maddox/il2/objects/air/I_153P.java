package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class I_153P extends I_153_M62
{
  static
  {
    Class localClass = I_153P.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "I-153");
    Property.set(localClass, "meshName", "3DO/Plane/I-153/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00());

    Property.set(localClass, "yearService", 1939.2F);
    Property.set(localClass, "yearExpired", 1944.0F);

    Property.set(localClass, "FlightModel", "FlightModels/I-153-M62.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xAO10", new String[] { "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xAO10_2xFAB50", new String[] { "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB50", new String[] { "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xFAB50", new String[] { "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB100", new String[] { "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB50_2xFAB100", new String[] { "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, null, null, null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "8xRS82", new String[] { "MGunShVAKsi 200", "MGunShVAKsi 250", null, null, "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, null, null, "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}