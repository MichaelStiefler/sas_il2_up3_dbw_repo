package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_40E extends P_40
{
  static
  {
    Class localClass = P_40E.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P-40");
    Property.set(localClass, "meshName", "3DO/Plane/P-40E(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar03());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-40E(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar03());

    Property.set(localClass, "noseart", 1);

    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-40E.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_40E.class);
    Property.set(localClass, "LOSElevation", 1.06965F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 3, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", null, null });

    Aircraft.weaponsRegister(localClass, "500lb", new String[] { "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "BombGun500lbs", null });

    Aircraft.weaponsRegister(localClass, "1000lb", new String[] { "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "BombGun1000lbs", null });

    Aircraft.weaponsRegister(localClass, "droptank", new String[] { "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", "MGunBrowning50k 300", "MGunBrowning50k 300", "MGunBrowning50k 240", null, "FuelTankGun_Tank75gal2" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}