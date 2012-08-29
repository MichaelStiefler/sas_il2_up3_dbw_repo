package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_40EM105 extends P_40
{
  static
  {
    Class localClass = P_40EM105.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P-40");
    Property.set(localClass, "meshName", "3DO/Plane/P-40E-M-105(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-40E-M-105.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_40E.class);
    Property.set(localClass, "LOSElevation", 1.06965F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 2, 2, 2, 2, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalBomb01" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50k 312", "MGunBrowning50k 291", "MGunBrowning50k 240", "MGunBrowning50k 312", "MGunBrowning50k 291", "MGunBrowning50k 240", null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4rs82", new String[] { "MGunBrowning50k 312", "MGunBrowning50k 291", "MGunBrowning50k 240", "MGunBrowning50k 312", "MGunBrowning50k 291", "MGunBrowning50k 240", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null });

    weaponsRegister(localClass, "1fab250", new String[] { "MGunBrowning50k 312", "MGunBrowning50k 291", "MGunBrowning50k 240", "MGunBrowning50k 312", "MGunBrowning50k 291", "MGunBrowning50k 240", null, null, null, null, null, null, null, null, "BombGunFAB250" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}