package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunSpezzoniera extends BombGun
{
  static
  {
    Class localClass = BombGunSpezzoniera.class;
    Property.set(localClass, "bulletClass", Bomblet2Kg.class);
    Property.set(localClass, "bullets", 22);
    Property.set(localClass, "shotFreq", 20.0F);
    Property.set(localClass, "external", 0);
    Property.set(localClass, "cassette", 1);
    Property.set(localClass, "sound", "weapon.bombgun_AO10");
  }
}