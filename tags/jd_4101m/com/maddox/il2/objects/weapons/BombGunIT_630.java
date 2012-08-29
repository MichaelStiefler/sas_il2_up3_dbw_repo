package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunIT_630 extends BombGun
{
  static
  {
    Class localClass = BombGunIT_630.class;
    Property.set(localClass, "bulletClass", BombIT_630.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.9F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}