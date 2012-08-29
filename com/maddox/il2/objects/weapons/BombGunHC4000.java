package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunHC4000 extends BombGun
{
  static
  {
    Class localClass = BombGunHC4000.class;

    Property.set(localClass, "bulletClass", BombHC4000.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.05F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}