package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunHS_293 extends RocketBombGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    this.timeLife = 30.0F;
  }

  static
  {
    Class localClass = RocketGunHS_293.class;
    Property.set(localClass, "bulletClass", RocketHS_293.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}