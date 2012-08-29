package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGun75GalNapalm extends BombGun
{
  static
  {
    Class localClass = BombGun75GalNapalm.class;

    Property.set(localClass, "bulletClass", Bomb75GalNapalm.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}