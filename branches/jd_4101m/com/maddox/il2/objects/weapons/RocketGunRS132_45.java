package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunRS132_45 extends RocketGun
{
  static
  {
    Class localClass = RocketGunRS132_45.class;
    Property.set(localClass, "bulletClass", RocketRS132_45.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
  }
}