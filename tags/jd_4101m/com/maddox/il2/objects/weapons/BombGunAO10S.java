package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunAO10S extends BombGun
{
  static
  {
    Class localClass = BombGunAO10S.class;
    Property.set(localClass, "bulletClass", BombAO10S.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 5.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun_AO10");
  }
}