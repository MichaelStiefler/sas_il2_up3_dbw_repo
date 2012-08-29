package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunSC1000 extends BombGun
{
  static
  {
    Class localClass = BombGunSC1000.class;
    Property.set(localClass, "bulletClass", BombSC1000.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}