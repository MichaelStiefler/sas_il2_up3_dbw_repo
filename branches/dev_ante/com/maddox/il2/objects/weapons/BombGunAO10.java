package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunAO10 extends BombGun
{
  static
  {
    Class localClass = BombGunAO10.class;
    Property.set(localClass, "bulletClass", BombAO10.class);
    Property.set(localClass, "bullets", 15);
    Property.set(localClass, "shotFreq", 5.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "cassette", 1);
    Property.set(localClass, "sound", "weapon.bombgun_AO10");
  }
}