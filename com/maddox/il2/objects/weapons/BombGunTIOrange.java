package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunTIOrange extends BombGun
{
  static
  {
    Class localClass = BombGunTIOrange.class;

    Property.set(localClass, "bulletClass", BombTIOrange.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}