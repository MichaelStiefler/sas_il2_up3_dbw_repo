package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunAmpoule extends BombGun
{
  public void setBombDelay(float paramFloat)
  {
    this.jdField_bombDelay_of_type_Float = 0.0F;
    if (this.jdField_bomb_of_type_ComMaddoxIl2ObjectsWeaponsBomb != null)
      this.jdField_bomb_of_type_ComMaddoxIl2ObjectsWeaponsBomb.delayExplosion = this.jdField_bombDelay_of_type_Float;
  }

  static {
    Class localClass = BombGunAmpoule.class;
    Property.set(localClass, "bulletClass", BombAmpoule.class);
    Property.set(localClass, "bullets", 54);
    Property.set(localClass, "shotFreq", 33.299999F);
    Property.set(localClass, "external", 1);
    Property.set(localClass, "cassette", 1);
    Property.set(localClass, "sound", "weapon.bombgun_AO10");
  }
}