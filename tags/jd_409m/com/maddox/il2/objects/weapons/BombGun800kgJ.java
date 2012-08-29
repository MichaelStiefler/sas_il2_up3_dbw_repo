package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGun800kgJ extends BombGun
{
  static
  {
    Class localClass = BombGun800kgJ.class;
    Property.set(localClass, "bulletClass", Bomb800kgJ.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 3.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}