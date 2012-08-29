package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunHVARF84 extends RocketGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    this.timeLife = -1.0F;
  }

  static
  {
    Class localClass = RocketGunHVARF84.class;

    Property.set(localClass, "bulletClass", RocketHVARF84.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.0F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
  }
}