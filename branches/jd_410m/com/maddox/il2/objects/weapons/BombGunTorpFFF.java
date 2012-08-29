package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunTorpFFF extends ParaTorpedoGun
{
  public void setBombDelay(float paramFloat)
  {
    this.bombDelay = 0.0F;
    if (this.bomb != null)
      this.bomb.delayExplosion = this.bombDelay;
  }

  static
  {
    Class localClass = BombGunTorpFFF.class;
    Property.set(localClass, "bulletClass", BombTorpFFF.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.1F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun_torpedo");
  }
}