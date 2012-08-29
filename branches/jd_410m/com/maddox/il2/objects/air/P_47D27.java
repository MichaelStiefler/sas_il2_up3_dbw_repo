package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_47D27 extends P_47
{
  static
  {
    Class localClass = P_47D27.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P-47");
    Property.set(localClass, "meshName", "3DO/Plane/P-47D-27(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-47D-27(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());

    Property.set(localClass, "noseart", 1);

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1947.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-47D-27.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_47D25.class);
    Property.set(localClass, "LOSElevation", 1.1104F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 1, 1, 9, 3, 3, 3, 9, 9, 2, 2, 2, 2, 2, 2 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalDev01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb01", "_ExternalDev02", "_ExternalDev03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "overload", new String[] { "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", "MGunBrowning50k 425", null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "tank", new String[] { "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "FuelTankGun_Tank75gal", null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "tank2x500", new String[] { "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "FuelTankGun_Tank75gal", "BombGun500lbs", "BombGun500lbs", null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "tank6x45", new String[] { "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "FuelTankGun_Tank75gal", null, null, null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch" });

    weaponsRegister(localClass, "tank2x5006x45", new String[] { "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "FuelTankGun_Tank75gal", "BombGun500lbs", "BombGun500lbs", null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch" });

    weaponsRegister(localClass, "6x45", new String[] { "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, null, null, null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch" });

    weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, "BombGun500lbs", "BombGun500lbs", null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x5006x45", new String[] { "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, "BombGun500lbs", "BombGun500lbs", null, "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch" });

    weaponsRegister(localClass, "1x1000", new String[] { "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, null, null, "BombGun1000lbs", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1x10002x500", new String[] { "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, "BombGun500lbs", "BombGun500lbs", "BombGun1000lbs", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "1x10006x45", new String[] { "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, null, null, "BombGun1000lbs", "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch" });

    weaponsRegister(localClass, "1x10002x5006x45", new String[] { "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", "MGunBrowning50k 200", null, "BombGun500lbs", "BombGun500lbs", "BombGun1000lbs", "PylonRO_4andHalfInch_3", "PylonRO_4andHalfInch_3", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch", "RocketGun4andHalfInch" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}