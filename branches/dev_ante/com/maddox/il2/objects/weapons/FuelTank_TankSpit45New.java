package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_TankSpit45New extends FuelTank
{
  static
  {
    Class localClass = FuelTank_TankSpit45New.class;

    Property.set(localClass, "mesh", "3DO/Arms/Spit_DropTank_45gal/mono.sim");

    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 147.57001F);
  }
}