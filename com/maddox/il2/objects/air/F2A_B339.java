package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class F2A_B339 extends F2A
{
  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Buffalo");

    Property.set(localClass, "meshName", "3DO/Plane/BuffaloMkI(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar01());
    Property.set(localClass, "originCountry", PaintScheme.countryBritain);

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/F2A-1.fmd");
    Property.set(localClass, "cockpitClass", CockpitB339.class);
    Property.set(localClass, "LOSElevation", 1.032F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50si 500", "MGunBrowning50si 500", "MGunBrowning50k 250", "MGunBrowning50k 250", null, null });

    weaponsRegister(localClass, "2x100", new String[] { "MGunBrowning50si 500", "MGunBrowning50si 500", "MGunBrowning50k 250", "MGunBrowning50k 250", "BombGunFAB50 1", "BombGunFAB50 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}