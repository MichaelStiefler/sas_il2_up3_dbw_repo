package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_TankSpit30 extends FuelTank
{
  static
  {
    Class localClass = FuelTank_TankSpit30.class;
    Property.set(localClass, "mesh", "3DO/Arms/Spit_DropTank_30gal/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 114.0F);
  }
}