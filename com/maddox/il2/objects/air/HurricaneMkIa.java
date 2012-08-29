package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class HurricaneMkIa extends Hurricane
{
  static
  {
    Class localClass = HurricaneMkIa.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Hurri");
    Property.set(localClass, "meshName_fi", "3DO/Plane/HurricaneMkI(Finnish)/hier.him");
    Property.set(localClass, "PaintScheme_fi", new PaintSchemeFCSPar02());
    Property.set(localClass, "meshName", "3DO/Plane/HurricaneMkI(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00());

    Property.set(localClass, "yearService", 1938.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/HurricaneMkI.fmd");
    Property.set(localClass, "cockpitClass", CockpitHURRI.class);
    Property.set(localClass, "LOSElevation", 0.965F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 0, 0 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08" });
    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 356", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 308", "MGunBrowning303k 334" });
    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}