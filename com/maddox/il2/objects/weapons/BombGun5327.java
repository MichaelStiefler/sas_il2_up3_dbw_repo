package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGun5327 extends TorpedoGun
{
  public void setBombDelay(float paramFloat)
  {
    this.jdField_bombDelay_of_type_Float = 0.0F;
    if (this.jdField_bomb_of_type_ComMaddoxIl2ObjectsWeaponsBomb != null)
      this.jdField_bomb_of_type_ComMaddoxIl2ObjectsWeaponsBomb.delayExplosion = this.jdField_bombDelay_of_type_Float;
  }

  static {
    Class localClass = BombGun5327.class;
    Property.set(localClass, "bulletClass", Bomb5327.class);
    Property.set(localClass, "bullets", 1);
    Property.set(localClass, "shotFreq", 0.01F);
    Property.set(localClass, "external", 0);
    Property.set(localClass, "sound", "weapon.bombgun_torpedo");
  }
}