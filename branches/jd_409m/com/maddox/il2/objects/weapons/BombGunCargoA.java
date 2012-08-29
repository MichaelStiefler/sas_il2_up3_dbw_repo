package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunCargoA extends BombGun
{
  static
  {
    Class localClass = BombGunCargoA.class;
    Property.set(localClass, "bulletClass", BombCargoA.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.75F);

    Property.set(localClass, "cassette", 1);
    Property.set(localClass, "sound", "weapon.bombgun_AO10");
  }
}