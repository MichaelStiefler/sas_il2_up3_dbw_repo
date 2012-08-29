package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunHVAR2 extends RocketGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    this.timeLife = -1.0F;
  }

  static
  {
    Class localClass = RocketGunHVAR2.class;
    Property.set(localClass, "bulletClass", RocketHVAR2.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 5.0F);
    Property.set(localClass, "sound", "weapon.rocketgun_82");
  }
}