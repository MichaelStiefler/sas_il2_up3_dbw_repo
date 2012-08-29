package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGun4andHalfInch extends RocketGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    this.timeLife = -1.0F;
  }

  static
  {
    Class localClass = RocketGun4andHalfInch.class;
    Property.set(localClass, "bulletClass", Rocket4andHalfInch.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
  }
}