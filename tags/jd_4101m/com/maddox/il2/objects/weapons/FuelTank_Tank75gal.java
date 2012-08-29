package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank75gal extends FuelTank
{
  static
  {
    Class localClass = FuelTank_Tank75gal.class;
    Property.set(localClass, "mesh", "3DO/Arms/Tank75gal/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 156.0F);
  }
}