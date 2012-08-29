package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class LA_7KOJEDUB extends LA_X
  implements TypeAcePlane
{
  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.Skill = 3;
  }

  static
  {
    Class localClass = LA_7KOJEDUB.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "La");
    Property.set(localClass, "meshName", "3DO/Plane/La-7(ofKojedub)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeSpecial());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/La-7.fmd");

    weaponTriggersRegister(localClass, new int[] { 1, 1, 3, 3, 9, 9 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02" });

    weaponsRegister(localClass, "default", new String[] { "MGunShVAKANTIMATTERs 340", "MGunShVAKANTIMATTERs 340", null, null, null, null });

    weaponsRegister(localClass, "2xFAB50", new String[] { "MGunShVAKANTIMATTERs 340", "MGunShVAKANTIMATTERs 340", "BombGunFAB50 1", "BombGunFAB50 1", null, null });

    weaponsRegister(localClass, "2xFAB100", new String[] { "MGunShVAKANTIMATTERs 340", "MGunShVAKANTIMATTERs 340", "BombGunFAB100 1", "BombGunFAB100 1", null, null });

    weaponsRegister(localClass, "2xDROPTANK", new String[] { "MGunShVAKANTIMATTERs 340", "MGunShVAKANTIMATTERs 340", null, null, "FuelTankGun_Tank80", "FuelTankGun_Tank80" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}