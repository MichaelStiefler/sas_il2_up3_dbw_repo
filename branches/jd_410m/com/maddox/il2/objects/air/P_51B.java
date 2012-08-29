package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_51B extends P_51
{
  static
  {
    Class localClass = P_51B.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P-51");
    Property.set(localClass, "meshNameDemo", "3DO/Plane/P-51B(USA)/hier.him");
    Property.set(localClass, "meshName", "3DO/Plane/P-51B(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-51B(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFCSPar05());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1947.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-51B.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_51B.class);
    Property.set(localClass, "LOSElevation", 1.03F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 9, 9, 3, 3, 9, 9 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", null, null, null, null, null, null });

    weaponsRegister(localClass, "2x250", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "PylonP51PLN2", "PylonP51PLN2", "BombGun250lbs 1", "BombGun250lbs 1", null, null });

    weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "PylonP51PLN2", "PylonP51PLN2", "BombGun500lbs 1", "BombGun500lbs 1", null, null });

    weaponsRegister(localClass, "2x1000", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "PylonP51PLN2", "PylonP51PLN2", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null });

    weaponsRegister(localClass, "2xTank", new String[] { "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "PylonP51PLN2", "PylonP51PLN2", null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null });
  }
}