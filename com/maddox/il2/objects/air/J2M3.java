package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class J2M3 extends J2M
{
  static
  {
    Class localClass = J2M3.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "J2M");
    Property.set(localClass, "meshName", "3DO/Plane/J2M3(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ja", "3DO/Plane/J2M3(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar02());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/J2M3.fmd");
    Property.set(localClass, "cockpitClass", CockpitJ2M3.class);
    Property.set(localClass, "LOSElevation", 1.113F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 1, 1, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunHo5k 210", "MGunHo5k 190", "MGunHo5k 190", "MGunHo5k 210", null, null });

    Aircraft.weaponsRegister(localClass, "2x60", new String[] { "MGunHo5k 210", "MGunHo5k 190", "MGunHo5k 190", "MGunHo5k 210", "BombGun60kgJ 1", "BombGun60kgJ 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}