package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_TankC120gal extends FuelTank
{
  static
  {
    Class localClass = FuelTank_TankC120gal.class;
    Property.set(localClass, "mesh", "3DO/Arms/F86_120galCombatTank/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 366.5F);
  }
}