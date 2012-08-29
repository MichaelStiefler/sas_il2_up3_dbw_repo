package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class YAK_9M extends YAK_9TX
{
  static
  {
    Class localClass = YAK_9M.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Yak");
    Property.set(localClass, "meshName", "3DO/Plane/Yak-9M(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "meshName_fr", "3DO/Plane/Yak-9M(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme_fr", new PaintSchemeFCSPar05());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1952.8F);

    Property.set(localClass, "FlightModel", "FlightModels/Yak-9M.fmd");
    Property.set(localClass, "cockpitClass", CockpitYAK_9T.class);
    Property.set(localClass, "LOSElevation", 0.661F);

    weaponTriggersRegister(localClass, new int[] { 0, 1, 1, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_CANNON01", "_CANNON02", "_ExternalDev01" });

    weaponsRegister(localClass, "default", new String[] { "MGunUBsi 200", "MGunShVAKki 120", null, null });

    weaponsRegister(localClass, "ns37", new String[] { "MGunUBsi 220", null, "MGunNS37ki 30", "PylonMG15120Internal" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}