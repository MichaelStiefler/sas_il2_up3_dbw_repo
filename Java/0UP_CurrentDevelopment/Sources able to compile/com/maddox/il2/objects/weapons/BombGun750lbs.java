// Source File Name: BombGun750lbs.java
// Author:           
// Last Modified by: Storebror 2011-06-01
package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGun750lbs extends BombGun {

  public BombGun750lbs() {
  }

  static {
    Class class1 = com.maddox.il2.objects.weapons.BombGun750lbs.class;
    Property.set(class1, "bulletClass", (Object) com.maddox.il2.objects.weapons.Bomb750lbs.class);
    Property.set(class1, "bullets", 1);
    Property.set(class1, "shotFreq", 3F);
    Property.set(class1, "external", 1);
    Property.set(class1, "sound", "weapon.bombgun");
  }
}