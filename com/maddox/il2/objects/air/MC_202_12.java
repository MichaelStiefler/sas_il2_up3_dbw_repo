package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class MC_202_12 extends MC_202xyz
{
  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "M.C.202");

    Property.set(localClass, "meshName_it", "3DO/Plane/MC-202_XII(it)/hier.him");
    Property.set(localClass, "PaintScheme_it", new PaintSchemeFCSPar02());
    Property.set(localClass, "meshName", "3DO/Plane/MC-202_XII(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/MC-202.fmd");
    Property.set(localClass, "cockpitClass", CockpitMC_202.class);
    Property.set(localClass, "LOSElevation", 0.81305F);

    weaponTriggersRegister(localClass, new int[] { 1, 1, 0, 0 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    weaponsRegister(localClass, "default", new String[] { "MGunBredaSAFAT127siMC202 370", "MGunBredaSAFAT127siMC202 370", "MGunBredaSAFAT77k 500", "MGunBredaSAFAT77k 500" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}