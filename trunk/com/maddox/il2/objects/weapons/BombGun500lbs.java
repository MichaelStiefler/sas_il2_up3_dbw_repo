package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGun500lbs extends BombGun
{
  static
  {
    Class localClass = BombGun500lbs.class;
    Property.set(localClass, "bulletClass", Bomb500lbs.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 2.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}