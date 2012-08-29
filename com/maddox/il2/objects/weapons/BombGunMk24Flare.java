package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunMk24Flare extends BombGun
{
  static
  {
    Class localClass = BombGunMk24Flare.class;

    Property.set(localClass, "bulletClass", BombMk24Flare.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 3.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun_AO10");
  }
}