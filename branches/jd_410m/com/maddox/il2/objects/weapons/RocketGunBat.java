package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunBat extends RocketBombGun
{
  public void setRocketTimeLife(float paramFloat)
  {
    this.timeLife = 30.0F;
  }

  static
  {
    Class localClass = RocketGunBat.class;
    Property.set(localClass, "bulletClass", RocketBat.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 2.0F);
    Property.set(localClass, "sound", "weapon.bombgun");
    Property.set(localClass, "dateOfUse", 19450401);
    Property.set(localClass, "dateOfUse_F4U1D", 19460101);
    Property.set(localClass, "dateOfUse_B_24J100", 19460101);
  }
}