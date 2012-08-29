package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGun20kgCZ extends BombGun
{
  static
  {
    Class localClass = BombGun20kgCZ.class;
    Property.set(localClass, "bulletClass", Bomb20kgCZ.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 3.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}