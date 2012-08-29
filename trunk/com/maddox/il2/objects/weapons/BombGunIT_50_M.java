package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunIT_50_M extends BombGun
{
  static
  {
    Class localClass = BombGunIT_50_M.class;
    Property.set(localClass, "bulletClass", BombIT_50_M.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 2.8F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}