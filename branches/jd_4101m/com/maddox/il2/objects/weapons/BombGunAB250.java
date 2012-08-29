package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunAB250 extends BombGun
{
  static
  {
    Class localClass = BombGunAB250.class;
    Property.set(localClass, "bulletClass", BombAB250.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}