package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunROFS132 extends RocketGun
{
  static
  {
    Class localClass = RocketGunROFS132.class;
    Property.set(localClass, "bulletClass", RocketROFS132.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.0F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
  }
}