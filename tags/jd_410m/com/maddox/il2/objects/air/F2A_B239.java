package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class F2A_B239 extends F2A
{
  static
  {
    Class localClass = F2A_B239.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "B-239");
    Property.set(localClass, "meshNameDemo", "3DO/Plane/B-239/hier.him");
    Property.set(localClass, "meshName", "3DO/Plane/B-239/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar01());
    Property.set(localClass, "originCountry", PaintScheme.countryFinland);

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/F2A-1.fmd");
    Property.set(localClass, "cockpitClass", CockpitF2A1.class);
    Property.set(localClass, "LOSElevation", 1.032F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning50k 250", "MGunBrowning50k 250" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}