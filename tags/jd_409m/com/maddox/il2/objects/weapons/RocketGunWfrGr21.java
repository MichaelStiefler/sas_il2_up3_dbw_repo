package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunWfrGr21 extends RocketGun
{
  static
  {
    Class localClass = RocketGunWfrGr21.class;
    Property.set(localClass, "bulletClass", RocketWfrGr21.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
  }
}