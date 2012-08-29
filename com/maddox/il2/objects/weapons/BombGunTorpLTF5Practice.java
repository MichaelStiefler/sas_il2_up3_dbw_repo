package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunTorpLTF5Practice extends TorpedoGun
{
  static
  {
    Class var_class = BombGunTorpLTF5Practice.class;

    Property.set(var_class, "bulletClass", BombTorpLTF5Practice.class);

    Property.set(var_class, "bullets", 1);
    Property.set(var_class, "shotFreq", 0.1F);
    Property.set(var_class, "external", 1);
    Property.set(var_class, "sound", "weapon.bombgun_torpedo");
  }

  public void setBombDelay(float f)
  {
    this.bombDelay = 0.0F;
    if (this.bomb != null)
      this.bomb.delayExplosion = this.bombDelay;
  }
}