package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunLittleBoy extends BombGun
{
  static
  {
    Class localClass = BombGunLittleBoy.class;

    Property.set(localClass, "bulletClass", BombLittleBoy.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.05F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
    Property.set(localClass, "newEffect", 1);
  }
}