package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank100i16 extends FuelTank
{
  static
  {
    Class localClass = FuelTank_Tank100i16.class;
    Property.set(localClass, "mesh", "3DO/Arms/I-16-Tank100/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 87.5F);
  }
}