package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunPuW125 extends BombGun
{
  static
  {
    Class localClass = BombGunPuW125.class;
    Property.set(localClass, "bulletClass", BombPuW125.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 5.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "cassette", 1);
    Property.set(localClass, "sound", "weapon.bombgun_AO10");
  }
}