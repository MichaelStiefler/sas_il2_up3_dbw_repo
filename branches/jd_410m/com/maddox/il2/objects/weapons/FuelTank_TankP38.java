package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_TankP38 extends FuelTank
{
  static
  {
    Class localClass = FuelTank_TankP38.class;
    Property.set(localClass, "mesh", "3DO/Arms/P-38_tank/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 156.0F);
  }
}