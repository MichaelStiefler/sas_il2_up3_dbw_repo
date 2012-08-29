package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunTorpFiumeR extends TorpedoGun
{
  static
  {
    Class localClass = BombGunTorpFiumeR.class;
    Property.set(localClass, "bulletClass", BombTorpFiumeR.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.1F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun_torpedo");
  }
}