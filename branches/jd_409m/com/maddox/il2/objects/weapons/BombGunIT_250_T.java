package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunIT_250_T extends BombGun
{
  static
  {
    Class localClass = BombGunIT_250_T.class;
    Property.set(localClass, "bulletClass", BombIT_250_T.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.5F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}