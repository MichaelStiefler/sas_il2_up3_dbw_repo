package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_Tank100gal extends FuelTankGun
{
  static
  {
    Class localClass = FuelTankGun_Tank100gal.class;

    Property.set(localClass, "bulletClass", FuelTank_Tank100gal.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "external", 1);
  }
}