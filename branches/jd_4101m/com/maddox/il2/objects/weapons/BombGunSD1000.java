package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunSD1000 extends BombGun
{
  static
  {
    Class localClass = BombGunSD1000.class;
    Property.set(localClass, "bulletClass", BombSD1000.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.25F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun");
  }
}