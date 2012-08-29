package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank230gal extends FuelTank
{
  static
  {
    Class localClass = FuelTank_Tank230gal.class;

    Property.set(localClass, "mesh", "3DO/Arms/TankF84/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 545.0F);
  }
}