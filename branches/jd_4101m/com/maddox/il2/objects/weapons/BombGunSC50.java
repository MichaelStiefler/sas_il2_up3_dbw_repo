package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunSC50 extends BombGun
{
  static
  {
    Class localClass = BombGunSC50.class;
    Property.set(localClass, "bulletClass", BombSC50.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 3.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}