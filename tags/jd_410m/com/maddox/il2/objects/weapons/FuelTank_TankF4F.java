package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_TankF4F extends FuelTank
{
  static
  {
    Class localClass = FuelTank_TankF4F.class;
    Property.set(localClass, "mesh", "3DO/Arms/F4F_Droptank/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 174.5F);
  }
}