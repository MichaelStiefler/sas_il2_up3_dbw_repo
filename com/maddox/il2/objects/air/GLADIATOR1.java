package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class GLADIATOR1 extends GLADIATOR
{
  static
  {
    Class localClass = GLADIATOR1.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Gladiator");
    Property.set(localClass, "meshName", "3DO/Plane/GladiatorMkI(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00());

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1943.0F);

    Property.set(localClass, "FlightModel", "FlightModels/GladiatorMkI.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303sipzl 600", "MGunBrowning303sipzl 600", "MGunBrowning303k 400", "MGunBrowning303k 400" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}