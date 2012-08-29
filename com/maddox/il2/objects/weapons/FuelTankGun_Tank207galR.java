package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_Tank207galR extends FuelTankGun
{
  static
  {
    Class localClass = FuelTankGun_Tank207galR.class;
    Property.set(localClass, "bulletClass", FuelTank_Tank207galR.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.0F);
    Property.set(localClass, "external", 1);
  }
}