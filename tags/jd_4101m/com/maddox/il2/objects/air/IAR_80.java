package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class IAR_80 extends IAR_8X
  implements TypeFighter
{
  static
  {
    Class localClass = IAR_80.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "IAR 80");
    Property.set(localClass, "meshName", "3DO/Plane/IAR-80/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1939.5F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/IAR-80.fmd");
    Property.set(localClass, "cockpitClass", CockpitIAR80.class);
    Property.set(localClass, "LOSElevation", 0.8323F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303k 1000", "MGunBrowning303k 1000", "MGunBrowning303k 1000", "MGunBrowning303k 1000" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}