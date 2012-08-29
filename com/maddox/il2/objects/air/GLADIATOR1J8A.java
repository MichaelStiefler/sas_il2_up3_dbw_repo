package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class GLADIATOR1J8A extends GLADIATOR
{
  static
  {
    Class localClass = GLADIATOR1J8A.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Gladiator");
    Property.set(localClass, "meshName", "3DO/Plane/GladiatorJ8A(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00());

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1943.0F);

    Property.set(localClass, "FlightModel", "FlightModels/GladiatorMkI.fmd");
    Property.set(localClass, "cockpitClass", CockpitJ8A.class);
    Property.set(localClass, "LOSElevation", 0.8472F);

    Property.set(localClass, "originCountry", PaintScheme.countryFinland);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303sipzl 600", "MGunBrowning303sipzl 600", "MGunBrowning303k 400", "MGunBrowning303k 400" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}