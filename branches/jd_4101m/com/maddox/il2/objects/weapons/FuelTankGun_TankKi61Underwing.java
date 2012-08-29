package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_TankKi61Underwing extends FuelTankGun
{
  static
  {
    Class localClass = FuelTankGun_TankKi61Underwing.class;
    Property.set(localClass, "bulletClass", FuelTank_TankKi61Underwing.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "external", 1);
  }
}