package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class TEMPEST5 extends TEMPEST
{
  static
  {
    Class localClass = TEMPEST5.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Tempest");
    Property.set(localClass, "meshName", "3DO/Plane/TempestMkV(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_gb", "3DO/Plane/TempestMkV(GB)/hier.him");
    Property.set(localClass, "PaintScheme_gb", new PaintSchemeFMPar05());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1946.5F);

    Property.set(localClass, "FlightModel", "FlightModels/TempestMkV.fmd");
    Property.set(localClass, "cockpitClass", CockpitTemp5.class);
    Property.set(localClass, "LOSElevation", 0.93655F);

    weaponTriggersRegister(localClass, new int[] { 1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08" });

    weaponsRegister(localClass, "default", new String[] { "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xdt", new String[] { "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "FuelTankGun_TankTempest", "FuelTankGun_TankTempest", null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x1000", new String[] { "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, "PylonTEMPESTPLN1", "PylonTEMPESTPLN2", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "8x5", new String[] { "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, "PylonTEMPESTPLN3", "PylonTEMPESTPLN4", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU", "RocketGunHVAR5BEAU" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}