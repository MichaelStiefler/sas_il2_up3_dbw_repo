package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class SPITFIRE5BLF extends SPITFIRE
{
  static
  {
    Class localClass = SPITFIRE5BLF.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Spit");

    Property.set(localClass, "meshName", "3DO/Plane/SpitfireMkVb(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "meshName_gb", "3DO/Plane/SpitfireMkVb(GB)/hier.him");
    Property.set(localClass, "PaintScheme_gb", new PaintSchemeFMPar04());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1946.5F);

    Property.set(localClass, "FlightModel", "FlightModels/SpitfireLFVB.fmd");
    Property.set(localClass, "cockpitClass", CockpitSpit5B.class);
    Property.set(localClass, "LOSElevation", 0.5926F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02" });
    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 60", "MGunHispanoMkIki 60" });
    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}