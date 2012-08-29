package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank154gal extends FuelTank
{
  static
  {
    Class localClass = FuelTank_Tank154gal.class;
    Property.set(localClass, "mesh", "3DO/Arms/Tank154gal/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 445.0F);
  }
}