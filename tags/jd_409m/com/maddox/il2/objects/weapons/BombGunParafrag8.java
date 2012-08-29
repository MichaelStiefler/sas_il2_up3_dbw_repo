package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunParafrag8 extends BombGun
{
  static
  {
    Class localClass = BombGunParafrag8.class;
    Property.set(localClass, "bulletClass", BombParafrag8.class);
    Property.set(localClass, "bullets", 20);
    Property.set(localClass, "shotFreq", 3.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "cassette", 1);
    Property.set(localClass, "sound", "weapon.bombgun_AO10");
  }
}