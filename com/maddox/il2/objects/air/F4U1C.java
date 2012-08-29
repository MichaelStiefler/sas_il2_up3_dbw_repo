package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class F4U1C extends F4U
{
  static
  {
    Class localClass = F4U1C.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "F4U");
    Property.set(localClass, "meshName", "3DO/Plane/F4U-1C(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/F4U-1C(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_rz", "3DO/Plane/F4U-1C(RZ)/hier.him");
    Property.set(localClass, "PaintScheme_rz", new PaintSchemeFMPar06());

    Property.set(localClass, "yearService", 1945.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/F4U-1C.fmd");
    Property.set(localClass, "cockpitClass", CockpitF4U1D.class);
    Property.set(localClass, "LOSElevation", 1.0585F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 3, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "8xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, null, null, null, null, null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "8xhvarap", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, null, null, null, null, null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP" });

    Aircraft.weaponsRegister(localClass, "2x154dt", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x154dt8xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "2x154dt8xhvarap", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP" });

    Aircraft.weaponsRegister(localClass, "1x178dt", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x178dt8xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", null, null, null, null, null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "1x178dt2x154dt", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x178dt2x154dt8xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "1x178dt2x154dt8xhvarap", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP" });

    Aircraft.weaponsRegister(localClass, "2x1541x178dt", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", "BombGun154Napalm 1", "BombGun154Napalm 1", null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x1541x178dt8xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", "BombGun154Napalm 1", "BombGun154Napalm 1", null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "2x1541x178dt8xhvarap", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", "BombGun154Napalm 1", "BombGun154Napalm 1", null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP" });

    Aircraft.weaponsRegister(localClass, "1x5002x154dt", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, "BombGun500lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x5002x154dt8xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, "BombGun500lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "1x5002x154dt8xhvarap", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, "BombGun500lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP" });

    Aircraft.weaponsRegister(localClass, "2x500", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x5008xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "2x5008xhvarap", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP" });

    Aircraft.weaponsRegister(localClass, "3x500", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "3x5008xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "3x5008xhvarap", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP" });

    Aircraft.weaponsRegister(localClass, "1x10008xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, null, null, null, null, "BombGun1000lbs 1", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "1x10008xhvarap", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, null, null, null, null, "BombGun1000lbs 1", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP" });

    Aircraft.weaponsRegister(localClass, "1x10002x154dt", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, "BombGun1000lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x10002x154dt8xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, "BombGun1000lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "1x10002x154dt8xhvarap", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, "BombGun1000lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP" });

    Aircraft.weaponsRegister(localClass, "1x10002x500", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x10002x5008xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun1000lbs 1", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "1x10002x5008xhvarap", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun1000lbs 1", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP" });

    Aircraft.weaponsRegister(localClass, "2x1000", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x10008xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "2x10008xhvarap", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", null, null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP" });

    Aircraft.weaponsRegister(localClass, "3x1000", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "3x10008xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "3x10008xhvarap", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP" });

    Aircraft.weaponsRegister(localClass, "1x2000", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, "BombGun2000lbs 1", null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x20008xhvargp", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, null, null, null, null, "BombGun2000lbs 1", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5" });

    Aircraft.weaponsRegister(localClass, "1x20008xhvarap", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, null, null, null, null, "BombGun2000lbs 1", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP" });

    Aircraft.weaponsRegister(localClass, "1x20002x1000", new String[] { "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "MGunHispanoMkIkWF 250", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun2000lbs 1", null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}