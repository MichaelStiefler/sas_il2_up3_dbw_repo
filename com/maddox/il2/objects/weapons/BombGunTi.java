package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunTi extends BombGun
{
  public void setBombDelay(float paramFloat)
  {
    this.bombDelay = 0.0F;
    if (this.bomb != null)
      this.bomb.delayExplosion = this.bombDelay;
  }

  static
  {
    Class localClass = BombGunTi.class;

    Property.set(localClass, "bulletClass", BombTi.class);

    Property.set(localClass, "bullets", 32);
    Property.set(localClass, "shotFreq", 8.0F);
    Property.set(localClass, "cassette", 1);
    Property.set(localClass, "sound", "weapon.bombgun_phball");
  }
}