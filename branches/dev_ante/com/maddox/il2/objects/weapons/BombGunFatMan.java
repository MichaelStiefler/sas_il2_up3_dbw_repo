package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunFatMan extends BombGun
{
  static
  {
    Class localClass = BombGunFatMan.class;

    Property.set(localClass, "bulletClass", BombFatMan.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.05F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}