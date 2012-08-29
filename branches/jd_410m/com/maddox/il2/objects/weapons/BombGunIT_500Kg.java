package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunIT_500Kg extends BombGun
{
  static
  {
    Class localClass = BombGunIT_500Kg.class;
    Property.set(localClass, "bulletClass", BombIT_500Kg.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}