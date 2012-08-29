package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunParaFlareUK extends BombGun
{
  static
  {
    Class localClass = BombGunParaFlareUK.class;
    Property.set(localClass, "bulletClass", BombParaFlareUK.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 3.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun_AO10");
  }
}