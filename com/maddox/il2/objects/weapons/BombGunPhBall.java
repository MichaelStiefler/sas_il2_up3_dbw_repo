package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunPhBall extends BombGun
{
  public void setBombDelay(float paramFloat)
  {
    this.jdField_bombDelay_of_type_Float = 0.0F;
    if (this.jdField_bomb_of_type_ComMaddoxIl2ObjectsWeaponsBomb != null)
      this.jdField_bomb_of_type_ComMaddoxIl2ObjectsWeaponsBomb.delayExplosion = this.jdField_bombDelay_of_type_Float;
  }

  static {
    Class localClass = BombGunPhBall.class;
    Property.set(localClass, "bulletClass", BombPhBall.class);
    Property.set(localClass, "bullets", 32);
    Property.set(localClass, "shotFreq", 8.0F);

    Property.set(localClass, "cassette", 1);
    Property.set(localClass, "sound", "weapon.bombgun_phball");
  }
}