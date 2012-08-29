package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunFAB1000 extends BombGun
{
  static
  {
    Class localClass = BombGunFAB1000.class;
    Property.set(localClass, "bulletClass", BombFAB1000.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.1F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}