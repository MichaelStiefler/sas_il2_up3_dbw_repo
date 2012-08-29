package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunMk53Charge extends BombGun
{
  static
  {
    Class localClass = BombGunMk53Charge.class;

    Property.set(localClass, "bulletClass", BombMk53Charge.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 3.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}