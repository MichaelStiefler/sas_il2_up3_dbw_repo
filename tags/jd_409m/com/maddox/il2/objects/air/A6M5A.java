package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class A6M5A extends A6M
{
  static
  {
    Class localClass = A6M5A.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "A6M");
    Property.set(localClass, "meshNameDemo", "3DO/Plane/A6M5a(ja)/hier.him");
    Property.set(localClass, "meshName", "3DO/Plane/A6M5a(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(localClass, "meshName_ja", "3DO/Plane/A6M5a(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar02());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/A6M5a.fmd");
    Property.set(localClass, "cockpitClass", CockpitA6M5a.class);
    Property.set(localClass, "LOSElevation", 1.01885F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 9, 9, 3, 9, 9, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb03", "_ExternalBomb04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG15spzl 1000", "MGunMG15spzl 1000", "MGunMGFFk 125", "MGunMGFFk 125", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1xdt", new String[] { "MGunMG15spzl 1000", "MGunMG15spzl 1000", "MGunMGFFk 125", "MGunMGFFk 125", "FuelTankGun_Tank0", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1x250", new String[] { "MGunMG15spzl 1000", "MGunMG15spzl 1000", "MGunMGFFk 125", "MGunMGFFk 125", null, "PylonA6MPLN1", "BombGun250kg", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x60", new String[] { "MGunMG15spzl 1000", "MGunMG15spzl 1000", "MGunMGFFk 125", "MGunMGFFk 125", null, null, null, "PylonA6MPLN2", "PylonA6MPLN2", "BombGun50kg", "BombGun50kg" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null });
  }
}