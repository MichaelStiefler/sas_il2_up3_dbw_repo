package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunIT_500_T extends BombGun
{
  static
  {
    Class localClass = BombGunIT_500_T.class;
    Property.set(localClass, "bulletClass", BombIT_500_T.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.9F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}