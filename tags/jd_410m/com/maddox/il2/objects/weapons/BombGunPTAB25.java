package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunPTAB25 extends BombGun
{
  public void setBombDelay(float paramFloat)
  {
    this.bombDelay = 0.0F;
    if (this.bomb != null)
      this.bomb.delayExplosion = this.bombDelay;
  }

  static {
    Class localClass = BombGunPTAB25.class;
    Property.set(localClass, "bulletClass", BombPTAB25.class);
    Property.set(localClass, "bullets", 48);
    Property.set(localClass, "shotFreq", 32.0F);

    Property.set(localClass, "cassette", 1);
    Property.set(localClass, "sound", "weapon.bombgun_AO10");
  }
}