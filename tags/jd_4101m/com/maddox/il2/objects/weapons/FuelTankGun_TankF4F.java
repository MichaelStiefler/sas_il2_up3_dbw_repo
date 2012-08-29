package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_TankF4F extends FuelTankGun
{
  static
  {
    Class localClass = FuelTankGun_TankF4F.class;
    Property.set(localClass, "bulletClass", FuelTank_TankF4F.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "external", 1);
  }
}