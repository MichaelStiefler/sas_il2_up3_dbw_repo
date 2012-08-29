package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank0Underwing extends FuelTank
{
  static
  {
    Class localClass = FuelTank_Tank0Underwing.class;
    Property.set(localClass, "mesh", "3DO/Arms/A6M5_150LUnderwingTank/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 118.8F);
  }
}