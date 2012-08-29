package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunCargo70 extends BombGun
{
  static
  {
    Class localClass = BombGunCargo70.class;

    Property.set(localClass, "bulletClass", BombCargo70.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.75F);
    Property.set(localClass, "sound", "weapon.bombgun_AO10");
  }
}