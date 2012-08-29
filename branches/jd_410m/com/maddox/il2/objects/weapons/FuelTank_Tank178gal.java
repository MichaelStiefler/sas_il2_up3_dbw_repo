package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank178gal extends FuelTank
{
  static
  {
    Class localClass = FuelTank_Tank178gal.class;
    Property.set(localClass, "mesh", "3DO/Arms/F4U_Droptank/mono.sim");
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 506.0F);
  }
}