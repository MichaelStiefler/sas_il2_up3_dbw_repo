package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank150gal extends FuelTank
{
  static
  {
    Class localClass = FuelTank_Tank150gal.class;
    Property.set(localClass, "mesh", "3DO/Arms/F6F_Droptank/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 445.0F);
  }
}