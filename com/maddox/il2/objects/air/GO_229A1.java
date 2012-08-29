package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class GO_229A1 extends GO_229
  implements TypeFighter, TypeBNZFighter
{
  static
  {
    Class localClass = GO_229A1.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Go-229");
    Property.set(localClass, "meshName", "3DO/Plane/Go-229A-1/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());

    Property.set(localClass, "yearService", 1946.5F);
    Property.set(localClass, "yearExpired", 1999.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Ho-229.fmd");

    Property.set(localClass, "cockpitClass", CockpitGO_229.class);
    Property.set(localClass, "LOSElevation", 0.51305F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMK103k 120", "MGunMK103k 120" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null });
  }
}