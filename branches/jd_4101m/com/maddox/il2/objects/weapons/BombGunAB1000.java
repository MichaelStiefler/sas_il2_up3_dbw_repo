package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunAB1000 extends BombGun
{
  static
  {
    Class localClass = BombGunAB1000.class;
    Property.set(localClass, "bulletClass", BombAB1000.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}