package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class HurricaneMkIIbMod extends Hurricane
{
  static
  {
    Class localClass = HurricaneMkIIbMod.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Hurri");
    Property.set(localClass, "meshName", "3DO/Plane/HurricaneMkIIbMod(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar03());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/HurricaneMkIIMod.fmd");
    Property.set(localClass, "cockpitClass", CockpitHURRII.class);
    Property.set(localClass, "LOSElevation", 0.965F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02" });
    weaponsRegister(localClass, "default", new String[] { "MGunUBk 100", "MGunUBk 100", "MGunShVAKk 120", "MGunShVAKk 120" });
    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}