package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class KI_61_IHEI extends KI_61
{
  static
  {
    Class localClass = KI_61_IHEI.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Ki-61");
    Property.set(localClass, "meshName", "3DO/Plane/Ki-61-I(Hei)(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar03());
    Property.set(localClass, "meshName_ja", "3DO/Plane/Ki-61-I(Hei)(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeFCSPar05());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1946.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Ki-61-IHei.fmd");
    Property.set(localClass, "cockpitClass", CockpitKI_61.class);
    Property.set(localClass, "LOSElevation", 0.81055F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 3, 3, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunHo103si 250", "MGunHo103si 250", "MGunMG15120k 120", "MGunMG15120k 120", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x250", new String[] { "MGunHo103si 250", "MGunHo103si 250", "MGunMG15120k 120", "MGunMG15120k 120", "BombGun250kgJ 1", "BombGun250kgJ 1", null, null });

    Aircraft.weaponsRegister(localClass, "2x150dt", new String[] { "MGunHo103si 250", "MGunHo103si 250", "MGunMG15120k 120", "MGunMG15120k 120", null, null, "FuelTankGun_TankKi61Underwing", "FuelTankGun_TankKi61Underwing" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}