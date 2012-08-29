package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class BLENHEIM4 extends BLENHEIM
{
  static
  {
    Class localClass = BLENHEIM4.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Blenheim");
    Property.set(localClass, "meshName", "3DO/Plane/BlenheimMkIV(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar00());

    Property.set(localClass, "yearService", 1938.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Blenheim_MkIV.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 10, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_BombSpawn01", "_BombSpawn02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303k 500", "MGunVikkersKt 2600", null, null });

    Aircraft.weaponsRegister(localClass, "4x250", new String[] { "MGunBrowning303k 500", "MGunVikkersKt 2600", "BombGun250lbs 2", "BombGun250lbs 2" });

    Aircraft.weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning303k 500", "MGunVikkersKt 2600", "BombGun500lbs 1", "BombGun500lbs 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}