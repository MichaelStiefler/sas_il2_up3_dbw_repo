package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunX4 extends RocketGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    this.timeLife = 30.0F;
  }

  static
  {
    Class localClass = RocketGunX4.class;
    Property.set(localClass, "bulletClass", RocketX4.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
  }
}