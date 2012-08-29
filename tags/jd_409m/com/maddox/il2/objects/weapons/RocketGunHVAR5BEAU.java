package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunHVAR5BEAU extends RocketGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    this.timeLife = -1.0F;
  }

  static
  {
    Class localClass = RocketGunHVAR5BEAU.class;
    Property.set(localClass, "bulletClass", RocketHVAR5BEAU.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 1.0F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
  }
}