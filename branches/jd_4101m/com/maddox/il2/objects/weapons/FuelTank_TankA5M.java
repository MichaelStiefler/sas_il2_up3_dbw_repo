package com.maddox.il2.objects.weapons;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class FuelTank_TankA5M extends FuelTank
{
  static
  {
    Class localClass = CLASS.THIS();
    Property.set(localClass, "mesh", "3DO/Arms/A5M_Droptank/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 174.5F);
  }
}