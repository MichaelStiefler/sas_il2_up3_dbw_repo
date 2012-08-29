package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunFritzX extends RocketBombGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    this.timeLife = 30.0F;
  }

  static
  {
    Class localClass = RocketGunFritzX.class;
    Property.set(localClass, "bulletClass", RocketFritzX.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}