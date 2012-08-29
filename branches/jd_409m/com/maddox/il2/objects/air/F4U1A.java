package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class F4U1A extends F4U
{
  static
  {
    Class localClass = F4U1A.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "F4U");
    Property.set(localClass, "meshName", "3DO/Plane/F4U-1A(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/F4U-1A(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_rz", "3DO/Plane/F4U-1A(RZ)/hier.him");
    Property.set(localClass, "PaintScheme_rz", new PaintSchemeFMPar06());

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/F4U-1A.fmd");
    Property.set(localClass, "cockpitClass", CockpitF4U1A.class);
    Property.set(localClass, "LOSElevation", 1.0585F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 3, 3, 3, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb02", "_ExternalBomb03" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x154dt", new String[] { "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal" });

    Aircraft.weaponsRegister(localClass, "1x178dt", new String[] { "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x178dt2x154dt", new String[] { "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal" });

    Aircraft.weaponsRegister(localClass, "2x1541x178dt", new String[] { "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", null, "BombGun154Napalm 1", "BombGun154Napalm 1", null, null });

    Aircraft.weaponsRegister(localClass, "1x5002x154dt", new String[] { "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun500lbs 1", null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal" });

    Aircraft.weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", null, "BombGun500lbs 1", "BombGun500lbs 1", null, null });

    Aircraft.weaponsRegister(localClass, "1x10002x154dt", new String[] { "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", "BombGun1000lbs 1", null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal" });

    Aircraft.weaponsRegister(localClass, "2x1000", new String[] { "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}