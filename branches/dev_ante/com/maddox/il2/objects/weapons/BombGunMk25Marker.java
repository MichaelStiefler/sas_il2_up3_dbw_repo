package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunMk25Marker extends BombGun
{
  public void setBombDelay(float paramFloat)
  {
    this.bombDelay = 0.0F;
    if (this.bomb != null)
      this.bomb.delayExplosion = this.bombDelay;
  }

  static
  {
    Class localClass = BombGunMk25Marker.class;

    Property.set(localClass, "bulletClass", BombMk25Marker.class);

    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 2.0F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "sound", "weapon.bombgun_phball");
  }
}