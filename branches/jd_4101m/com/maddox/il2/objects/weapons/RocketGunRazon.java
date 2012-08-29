package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunRazon extends RocketBombGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    this.timeLife = 30.0F;
  }

  static
  {
    Class localClass = RocketGunRazon.class;
    Property.set(localClass, "bulletClass", RocketRazon.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 2.0F);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}