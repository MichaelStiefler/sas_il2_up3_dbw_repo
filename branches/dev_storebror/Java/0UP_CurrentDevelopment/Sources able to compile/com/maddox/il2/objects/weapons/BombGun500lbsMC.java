// Source File Name: BombGun500lbsMC.java
// Author:           
// Last Modified by: Storebror 2011-06-01
package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGun500lbsMC extends BombGun {

  public BombGun500lbsMC() {
  }

  static {
    Class class1 = com.maddox.il2.objects.weapons.BombGun500lbsMC.class;
    Property.set(class1, "bulletClass", (Object) com.maddox.il2.objects.weapons.Bomb500lbsMC.class);
    Property.set(class1, "bullets", 1);
    Property.set(class1, "shotFreq", 1.0F);
    Property.set(class1, "external", 1);
    Property.set(class1, "sound", "weapon.bombgun");
  }
}
