// Source File Name: RocketGunS5Salvo.java
// Author:           
// Last Modified by: Storebror 2011-06-01
package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunS5Salvo extends RocketGun {

  public RocketGunS5Salvo() {
  }

  public void setConvDistance(float f, float f1) {
    super.setConvDistance(f, f1 + 2.81F);
  }

  static {
    Class class1 = com.maddox.il2.objects.weapons.RocketGunS5Salvo.class;
    Property.set(class1, "bulletClass", (Object) com.maddox.il2.objects.weapons.RocketS5.class);
    Property.set(class1, "bullets", 1);
    Property.set(class1, "shotFreq", 1F);
    Property.set(class1, "sound", "weapon.rocketgun_132");
    Property.set(class1, "cassette", 1);
  }
}