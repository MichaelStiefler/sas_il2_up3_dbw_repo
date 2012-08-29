package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGun10kgCZ extends BombGun
{
  static
  {
    Class localClass = BombGun10kgCZ.class;
    Property.set(localClass, "bulletClass", Bomb10kgCZ.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 3.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}