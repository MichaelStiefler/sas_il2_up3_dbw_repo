// Author:           Storebror
// Last Modified by: Storebror 2011-06-01
package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_Cougar150galB extends FuelTankGun {

  static {
    Class var_class = com.maddox.il2.objects.weapons.FuelTankGun_Cougar150galB.class;
    Property.set(var_class, "bulletClass", (Object) com.maddox.il2.objects.weapons.FuelTank_Cougar150galB.class);
    Property.set(var_class, "bullets", 1);
    Property.set(var_class, "shotFreq", 1.0F);
    Property.set(var_class, "external", 1);
  }
}