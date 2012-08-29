package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class HurricaneMkIIb extends Hurricane
{
  static
  {
    Class localClass = HurricaneMkIIb.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Hurri");
    Property.set(localClass, "meshName", "3DO/Plane/HurricaneMkIIb(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/HurricaneMkII.fmd");
    Property.set(localClass, "cockpitClass", CockpitHURRII.class);
    Property.set(localClass, "LOSElevation", 0.965F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", "_MGUN11", "_MGUN12", "_ExternalBomb01", "_ExternalBomb02" });
    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", "MGunBrowning303k 329", "MGunBrowning303k 361", "MGunBrowning303k 334", "MGunBrowning303k 335", null, null });
    weaponsRegister(localClass, "2x250lb", new String[] { "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", "MGunBrowning303k 329", "MGunBrowning303k 361", "MGunBrowning303k 334", "MGunBrowning303k 335", "BombGun250lbsE", "BombGun250lbsE" });
    weaponsRegister(localClass, "2xfab100", new String[] { "MGunBrowning303k 333", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 341", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 336", "MGunBrowning303k 329", "MGunBrowning303k 361", "MGunBrowning303k 334", "MGunBrowning303k 335", "BombGunFAB100", "BombGunFAB100" });
    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}