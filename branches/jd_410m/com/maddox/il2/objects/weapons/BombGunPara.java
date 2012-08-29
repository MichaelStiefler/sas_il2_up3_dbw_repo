package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunPara extends BombGun
{
  static
  {
    Class localClass = BombGunPara.class;
    Property.set(localClass, "bulletClass", BombPara.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.5F);

    Property.set(localClass, "cassette", 1);
  }
}