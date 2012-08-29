package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_TankSpit45 extends FuelTankGun
{
  static
  {
    Class localClass = FuelTankGun_TankSpit45.class;
    Property.set(localClass, "bulletClass", FuelTank_TankSpit45.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "external", 1);
  }
}