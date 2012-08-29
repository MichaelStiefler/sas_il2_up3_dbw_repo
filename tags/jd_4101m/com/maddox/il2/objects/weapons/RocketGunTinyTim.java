package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunTinyTim extends RocketGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    this.timeLife = -1.0F;
  }

  static
  {
    Class localClass = RocketGunTinyTim.class;
    Property.set(localClass, "bulletClass", RocketTinyTim.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 4.0F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
  }
}