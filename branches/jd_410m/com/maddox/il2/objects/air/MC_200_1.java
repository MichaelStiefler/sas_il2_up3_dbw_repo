package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class MC_200_1 extends MC_200xyz
{
  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "M.C.200");

    Property.set(localClass, "meshName_it", "3DO/Plane/MC-200_I(it)/hier.him");
    Property.set(localClass, "PaintScheme_it", new PaintSchemeFCSPar02());
    Property.set(localClass, "meshName", "3DO/Plane/MC-200_I(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/MC-200.fmd");

    weaponTriggersRegister(localClass, new int[] { 0, 0 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02" });

    weaponsRegister(localClass, "default", new String[] { "MGunBredaSAFAT127siMC200 350", "MGunBredaSAFAT127siMC200 350" });

    weaponsRegister(localClass, "none", new String[] { null, null });
  }
}