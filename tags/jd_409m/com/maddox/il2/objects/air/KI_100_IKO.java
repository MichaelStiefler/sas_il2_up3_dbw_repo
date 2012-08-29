package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class KI_100_IKO extends KI_100
{
  static
  {
    Class localClass = KI_100_IKO.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Ki-100");
    Property.set(localClass, "meshName", "3DO/Plane/Ki-100-I(Ko)(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_ja", "3DO/Plane/Ki-100-I(Ko)(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeBCSPar01());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1947.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Ki-100-I.fmd");
    Property.set(localClass, "cockpitClass", CockpitKI_100.class);
    Property.set(localClass, "LOSElevation", 0.85935F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunHo103k 250", "MGunHo103k 250", "MGunHo5si 250", "MGunHo5si 250", null, null });

    Aircraft.weaponsRegister(localClass, "2x250", new String[] { "MGunHo103k 250", "MGunHo103k 250", "MGunHo5si 250", "MGunHo5si 250", "BombGun250kgJ 1", "BombGun250kgJ 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}