package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGun1600lbs extends BombGun
{
  static
  {
    Class localClass = BombGun1600lbs.class;
    Property.set(localClass, "bulletClass", Bomb1600lbs.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}