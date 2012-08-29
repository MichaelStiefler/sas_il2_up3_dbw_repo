package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_TankN1K1 extends FuelTankGun
{
  static
  {
    Class localClass = FuelTankGun_TankN1K1.class;
    Property.set(localClass, "bulletClass", FuelTank_TankN1K1.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "external", 1);
  }
}