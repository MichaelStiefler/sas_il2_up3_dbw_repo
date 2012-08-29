package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank100gal extends FuelTank
{
  static
  {
    Class localClass = FuelTank_Tank100gal.class;

    Property.set(localClass, "mesh", "3DO/Arms/Tank100gal/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 195.0F);
  }
}