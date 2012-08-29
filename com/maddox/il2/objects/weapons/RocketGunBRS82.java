package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunBRS82 extends RocketGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    this.timeLife = -1.0F;
  }

  static
  {
    Class localClass = RocketGunBRS82.class;
    Property.set(localClass, "bulletClass", RocketBRS82.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
  }
}