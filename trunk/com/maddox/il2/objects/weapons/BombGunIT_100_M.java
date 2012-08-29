package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunIT_100_M extends BombGun
{
  static
  {
    Class localClass = BombGunIT_100_M.class;
    Property.set(localClass, "bulletClass", BombIT_100_M.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 2.8F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}