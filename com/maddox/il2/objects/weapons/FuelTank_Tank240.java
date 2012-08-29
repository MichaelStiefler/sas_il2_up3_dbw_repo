package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank240 extends FuelTank
{
  static
  {
    Class localClass = FuelTank_Tank240.class;
    Property.set(localClass, "mesh", "3DO/Arms/Tank240/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 184.0F);
  }
}