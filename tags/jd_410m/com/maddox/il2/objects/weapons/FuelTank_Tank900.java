package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank900 extends FuelTank
{
  static
  {
    Class localClass = FuelTank_Tank900.class;
    Property.set(localClass, "mesh", "3do/arms/FuelTank-900/mono.sim");
    Property.set(localClass, "kalibr", 0.9F);
    Property.set(localClass, "massa", 690);
  }
}