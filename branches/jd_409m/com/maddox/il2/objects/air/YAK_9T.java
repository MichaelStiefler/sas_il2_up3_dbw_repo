package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class YAK_9T extends YAK_9TX
{
  static
  {
    Class localClass = YAK_9T.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-9T(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar03());
    Property.set(localClass, "meshName_fr", "3DO/Plane/Yak-9T(fr)/hier.him");
    Property.set(localClass, "PaintScheme_fr", new PaintSchemeFCSPar05());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Yak-9T.fmd");
    Property.set(localClass, "cockpitClass", CockpitYAK_9T.class);
    Property.set(localClass, "LOSElevation", 0.661F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_CANNON01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunUBsi 220", "MGunNS37ki 30" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null });
  }
}