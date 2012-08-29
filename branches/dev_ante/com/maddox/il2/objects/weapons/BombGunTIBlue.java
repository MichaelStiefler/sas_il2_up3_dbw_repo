package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunTIBlue extends BombGun
{
  static
  {
    Class localClass = BombGunTIBlue.class;

    Property.set(localClass, "bulletClass", BombTIBlue.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}