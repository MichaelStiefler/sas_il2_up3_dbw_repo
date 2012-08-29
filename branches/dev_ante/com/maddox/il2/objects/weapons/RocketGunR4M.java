package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunR4M extends RocketGun
{
  public void setConvDistance(float paramFloat1, float paramFloat2)
  {
    super.setConvDistance(paramFloat1, paramFloat2 + 2.81F);
  }

  static
  {
    Class localClass = RocketGunR4M.class;
    Property.set(localClass, "bulletClass", RocketR4M.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 4.0F);
    Property.set(localClass, "sound", "weapon.rocketgun_132");
    Property.set(localClass, "cassette", 1);
  }
}