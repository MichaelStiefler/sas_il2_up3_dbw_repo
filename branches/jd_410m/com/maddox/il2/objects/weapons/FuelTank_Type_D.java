package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Type_D extends FuelTank
{
  static
  {
    Class localClass = FuelTank_Type_D.class;
    Property.set(localClass, "mesh", "3do/arms/ETC-900TypeD/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 230.0F);
  }
}