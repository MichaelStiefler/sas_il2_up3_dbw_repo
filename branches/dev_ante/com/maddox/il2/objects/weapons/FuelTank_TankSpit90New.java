package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_TankSpit90New extends FuelTank
{
  static
  {
    Class localClass = FuelTank_TankSpit90New.class;

    Property.set(localClass, "mesh", "3DO/Arms/Spit_DropTank_90gal/mono.sim");

    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 295.14001F);
  }
}