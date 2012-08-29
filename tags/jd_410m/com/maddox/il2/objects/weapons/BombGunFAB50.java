package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunFAB50 extends BombGun
{
  static
  {
    Class localClass = BombGunFAB50.class;
    Property.set(localClass, "bulletClass", BombFAB50.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 3.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}