package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGun90 extends RocketGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    this.timeLife = -1.0F;
  }

  static
  {
    Class localClass = RocketGun90.class;

    Property.set(localClass, "bulletClass", Rocket90.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.0F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
  }
}