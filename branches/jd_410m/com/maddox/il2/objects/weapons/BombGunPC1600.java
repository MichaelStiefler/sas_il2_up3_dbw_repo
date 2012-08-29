package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunPC1600 extends BombGun
{
  static
  {
    Class localClass = BombGunPC1600.class;
    Property.set(localClass, "bulletClass", BombPC1600.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}