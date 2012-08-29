package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunPC1000RS extends RocketGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    this.timeLife = -1.0F;
  }

  static
  {
    Class localClass = RocketGunPC1000RS.class;

    Property.set(localClass, "bulletClass", RocketPC1000RS.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 4.0F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
  }
}