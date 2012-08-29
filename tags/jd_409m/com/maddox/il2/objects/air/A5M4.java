package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class A5M4 extends A5M
{
  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "A5M");

    Property.set(localClass, "meshName", "3DO/Plane/A5M4(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ja", "3DO/Plane/A5M4(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar05());

    Property.set(localClass, "yearService", 1938.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/A5M4.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalDev02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunVikkersKs 500", "MGunVikkersKs 500", null, null });

    Aircraft.weaponsRegister(localClass, "1xdt", new String[] { "MGunVikkersKs 500", "MGunVikkersKs 500", "PylonA5MPLN1", "FuelTankGun_TankA5M" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}