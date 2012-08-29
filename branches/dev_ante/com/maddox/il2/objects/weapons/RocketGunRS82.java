package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunRS82 extends RocketGun
{
  static
  {
    Class localClass = RocketGunRS82.class;
    Property.set(localClass, "bulletClass", RocketRS82.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
  }
}