package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunTorpMK13_1 extends TorpedoGun
{
  static
  {
    Class var_class = BombGunTorpMK13_1.class;

    Property.set(
      var_class, "bulletClass", BombTorpMk13_1.class);

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