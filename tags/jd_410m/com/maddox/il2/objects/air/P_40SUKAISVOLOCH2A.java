package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_40SUKAISVOLOCH2A extends P_40SUKAISVOLOCH
{
  static
  {
    Class localClass = P_40SUKAISVOLOCH2A.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P-40");
    Property.set(localClass, "meshName", "3DO/Plane/TomahawkMkIIa(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar03());
    Property.set(localClass, "meshName_gb", "3DO/Plane/TomahawkMkIIa(GB)/hier.him");
    Property.set(localClass, "PaintScheme_gb", new PaintSchemeFMPar03());

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-40B.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_40B.class);
    Property.set(localClass, "LOSElevation", 1.0728F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}