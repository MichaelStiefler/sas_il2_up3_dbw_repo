// Source File Name: FuelTank_Tank207galL.java
// Author:           
// Last Modified by: Storebror 2011-06-01
package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Cougar150galB extends FuelTank {

  public FuelTank_Cougar150galB() {
  }

  static {
    Class class1 = com.maddox.il2.objects.weapons.FuelTank_Cougar150galB.class;
    Property.set(class1, "mesh", "3DO/Arms/Blue_Cougar150gal/mono.sim");
    Property.set(class1, "kalibr", 0.6F);
    Property.set(class1, "massa", 540F);
  }
}