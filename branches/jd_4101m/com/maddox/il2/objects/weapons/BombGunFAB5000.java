package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunFAB5000 extends BombGun
{
  static
  {
    Class localClass = BombGunFAB5000.class;
    Property.set(localClass, "bulletClass", BombFAB5000.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.05F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}