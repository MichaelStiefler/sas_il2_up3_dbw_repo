package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGun300lbs extends BombGun
{
  static
  {
    Class localClass = BombGun300lbs.class;
    Property.set(localClass, "bulletClass", Bomb300lbs.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 3.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}