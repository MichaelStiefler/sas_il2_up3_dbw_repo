package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class IL_2I extends IL_2
  implements TypeFighter
{
  static
  {
    Class localClass = IL_2I.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "IL2");
    Property.set(localClass, "meshName", "3do/plane/Il-2I(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ru", "3do/plane/Il-2I/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar01());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Il-2I.fmd");
    Property.set(localClass, "cockpitClass", CockpitIL_2_1942.class);
    Property.set(localClass, "LOSElevation", 0.81F);
    Property.set(localClass, "Handicap", 1.1F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_Cannon01", "_Cannon02" });
    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunVYak 150", "MGunVYak 150" });
    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null });
  }
}