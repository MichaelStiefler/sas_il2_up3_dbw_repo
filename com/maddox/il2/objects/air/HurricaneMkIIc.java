package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class HurricaneMkIIc extends Hurricane
{
  static
  {
    Class localClass = HurricaneMkIIc.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Hurri");
    Property.set(localClass, "meshName", "3DO/Plane/HurricaneMkIIc(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());

    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/HurricaneMkII.fmd");
    Property.set(localClass, "cockpitClass", CockpitHURRII.class);
    Property.set(localClass, "LOSElevation", 0.965F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04" });
    weaponsRegister(localClass, "default", new String[] { "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91", "MGunHispanoMkIk 91" });
    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}