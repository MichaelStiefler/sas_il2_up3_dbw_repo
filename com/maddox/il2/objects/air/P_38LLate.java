package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class P_38LLate extends P_38
{
  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P-38");
    Property.set(localClass, "meshNameDemo", "3DO/Plane/P-38L(USA)/hier.him");
    Property.set(localClass, "meshName", "3DO/Plane/P-38L(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar03());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-38L(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());

    Property.set(localClass, "noseart", 1);

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-38LLate.fmd");

    Property.set(localClass, "cockpitClass", CockpitP_38J.class);
    Property.set(localClass, "LOSElevation", 0.69215F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 9, 9, 3, 3, 9, 9, 2, 2, 2, 2, 2, 2, 9, 9, 2, 2, 2, 2, 2, 2, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9, 9, 1, 1, 1, 1 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalDev03", "_ExternalDev04", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalDev05", "_ExternalDev06", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalDev07", "_ExternalDev08", "_ExternalRock23", "_ExternalRock24", "_ExternalRock25", "_ExternalRock26", "_ExternalRock27", "_ExternalRock28", "_ExternalRock29", "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", "_ExternalRock34", "_ExternalRock35", "_ExternalRock36", "_ExternalDev09", "_ExternalDev10", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunHispanoMkIki 150", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "gunpods", new String[] { "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunHispanoMkIki 150", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonP38GUNPOD", "PylonP38GUNPOD", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350" });

    weaponsRegister(localClass, "droptanks", new String[] { "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunHispanoMkIki 150", "FuelTankGun_TankP38", "FuelTankGun_TankP38", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x250", new String[] { "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunHispanoMkIki 150", null, null, "BombGun250lbs", "BombGun250lbs", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunHispanoMkIki 150", null, null, "BombGun500lbs", "BombGun500lbs", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2x1000", new String[] { "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunHispanoMkIki 150", null, null, "BombGun1000lbs", "BombGun1000lbs", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xtree", new String[] { "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunHispanoMkIki 150", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonP38RAIL5", "PylonP38RAIL5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xtreen2x500", new String[] { "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunHispanoMkIki 150", null, null, "BombGun500lbs", "BombGun500lbs", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonP38RAIL5", "PylonP38RAIL5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xtreen2x1000", new String[] { "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunBrowning50k 500", "MGunHispanoMkIki 150", null, null, "BombGun1000lbs", "BombGun1000lbs", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonP38RAIL5", "PylonP38RAIL5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}