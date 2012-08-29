package com.maddox.il2.objects.weapons;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class FuelTank_TankKi61Underwing extends FuelTank
{
  static
  {
    Class localClass = CLASS.THIS();
    Property.set(localClass, "mesh", "3DO/Arms/Ki-61_UnderwingTank/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 207.3F);
  }
}