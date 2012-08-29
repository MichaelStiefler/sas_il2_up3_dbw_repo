package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_51D5NT extends P_51
{
  static
  {
    Class localClass = P_51D5NT.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P-51");

    Property.set(localClass, "meshName", "3DO/Plane/P-51D-5NT(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-51D-5NT(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());

    Property.set(localClass, "noseart", 1);

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1947.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-51D-20.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_51D20N9.class);
    Property.set(localClass, "LOSElevation", 1.1188F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 9, 9, 3, 3, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x250", new String[] { "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "PylonP51PLN2", "PylonP51PLN2", "BombGun250lbs 1", "BombGun250lbs 1", null, null });

    Aircraft.weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "PylonP51PLN2", "PylonP51PLN2", "BombGun500lbs 1", "BombGun500lbs 1", null, null });

    Aircraft.weaponsRegister(localClass, "2x1000", new String[] { "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "PylonP51PLN2", "PylonP51PLN2", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null });

    Aircraft.weaponsRegister(localClass, "2xTank", new String[] { "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "PylonP51PLN2", "PylonP51PLN2", null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null });
  }
}