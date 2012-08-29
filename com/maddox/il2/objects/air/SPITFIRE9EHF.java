package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class SPITFIRE9EHF extends SPITFIRE9
{
  static
  {
    Class localClass = SPITFIRE9EHF.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Spit");

    Property.set(localClass, "meshName", "3DO/Plane/SpitfireMkIXe(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "meshName_gb", "3DO/Plane/SpitfireMkIXe(GB)/hier.him");
    Property.set(localClass, "PaintScheme_gb", new PaintSchemeFMPar04());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1946.5F);

    Property.set(localClass, "FlightModel", "FlightModels/SpitfireHF_IXC.fmd");
    Property.set(localClass, "cockpitClass", CockpitSpit9C.class);
    Property.set(localClass, "LOSElevation", 0.5926F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 9, 9, 9, 3, 3, 9, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalDev08", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev01", "_ExternalBomb01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "30gal", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit30", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "45gal", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit45", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "90gal", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit90", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "250lb", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null });

    Aircraft.weaponsRegister(localClass, "250lb30gal", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit30", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null });

    Aircraft.weaponsRegister(localClass, "250lb45gal", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit45", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null });

    Aircraft.weaponsRegister(localClass, "250lb90gal", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit90", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null });

    Aircraft.weaponsRegister(localClass, "500lb", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", null, null, null, null, null, "PylonSpitC", "BombGun500lbsE 1" });

    Aircraft.weaponsRegister(localClass, "500lb250lb", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", "PylonSpitC", "BombGun500lbsE 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null });
  }
}