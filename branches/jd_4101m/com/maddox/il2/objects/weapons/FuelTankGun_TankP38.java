package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_TankP38 extends FuelTankGun
{
  static
  {
    Class localClass = FuelTankGun_TankP38.class;
    Property.set(localClass, "bulletClass", FuelTank_TankP38.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "external", 1);
  }
}