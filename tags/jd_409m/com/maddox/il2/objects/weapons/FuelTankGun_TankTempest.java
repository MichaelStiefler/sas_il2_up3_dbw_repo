package com.maddox.il2.objects.weapons;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class FuelTankGun_TankTempest extends FuelTankGun
{
  static
  {
    Class localClass = CLASS.THIS();
    Property.set(localClass, "bulletClass", FuelTank_TankTempest.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "external", 1);
  }
}