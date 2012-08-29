package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_TankSpit30 extends FuelTankGun
{
  static
  {
    Class localClass = FuelTankGun_TankSpit30.class;
    Property.set(localClass, "bulletClass", FuelTank_TankSpit30.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "external", 1);
  }
}