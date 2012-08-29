package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank80 extends FuelTank
{
  static
  {
    Class localClass = FuelTank_Tank80.class;
    Property.set(localClass, "mesh", "3do/arms/Tank80/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 65.0F);
  }
}