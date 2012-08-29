package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class P_51CM extends P_51
{
  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Mustang");
    Property.set(localClass, "meshName", "3DO/Plane/MustangMkIII(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_gb", "3DO/Plane/MustangMkIII(GB)/hier.him");
    Property.set(localClass, "PaintScheme_gb", new PaintSchemeFMPar05());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1947.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-51CM.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_51C.class);
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