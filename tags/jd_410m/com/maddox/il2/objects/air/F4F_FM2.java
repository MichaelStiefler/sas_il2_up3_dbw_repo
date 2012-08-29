package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class F4F_FM2 extends F4F
  implements TypeStormovik
{
  static
  {
    Class localClass = F4F_FM2.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "FM2");
    Property.set(localClass, "meshName", "3DO/Plane/FM-2(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar03());
    Property.set(localClass, "meshName_us", "3DO/Plane/FM-2(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar02());

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/FM-2.fmd");
    Property.set(localClass, "cockpitClass", CockpitF4F4.class);
    Property.set(localClass, "LOSElevation", 1.28265F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 9, 9, 9, 9, 9, 9, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalBomb01", "_ExternalBomb02" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x58dt", new String[] { "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "PylonF4FPLN1", "PylonF4FPLN1", "FuelTankGun_TankF4F", "FuelTankGun_TankF4F", null, null, null, null });

    weaponsRegister(localClass, "2x100", new String[] { "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", null, null, null, null, "PylonF4FPLN2", "PylonF4FPLN2", "BombGunFAB50 1", "BombGunFAB50 1" });

    weaponsRegister(localClass, "2x1002x58dt", new String[] { "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "PylonF4FPLN1", "PylonF4FPLN1", "FuelTankGun_TankF4F", "FuelTankGun_TankF4F", "PylonF4FPLN2", "PylonF4FPLN2", "BombGunFAB50 1", "BombGunFAB50 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null });
  }
}