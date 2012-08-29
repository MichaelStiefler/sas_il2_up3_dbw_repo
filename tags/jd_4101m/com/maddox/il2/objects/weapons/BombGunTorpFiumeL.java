package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunTorpFiumeL extends TorpedoGun
{
  static
  {
    Class localClass = BombGunTorpFiumeL.class;
    Property.set(localClass, "bulletClass", BombTorpFiumeL.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.1F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun_torpedo");
  }
}