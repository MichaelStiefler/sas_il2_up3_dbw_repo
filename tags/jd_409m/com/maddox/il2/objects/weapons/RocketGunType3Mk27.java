package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunType3Mk27 extends RocketGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    if (paramFloat > 10.0F) {
      paramFloat = 10.0F;
    }
    this.timeLife = paramFloat;
  }

  static
  {
    Class localClass = RocketGunType3Mk27.class;
    Property.set(localClass, "bulletClass", RocketType3Mk27.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 2.0F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
  }
}