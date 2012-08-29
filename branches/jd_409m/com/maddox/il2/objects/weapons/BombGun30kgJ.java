package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGun30kgJ extends BombGun
{
  static
  {
    Class localClass = BombGun30kgJ.class;
    Property.set(localClass, "bulletClass", Bomb30kgJ.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 3.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}