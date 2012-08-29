package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun extends BombGun
{
  public void shots(int paramInt)
  {
    if (paramInt != 0) super.shots(paramInt); 
  }

  public FuelTank getFuelTank()
  {
    if (!(this.jdField_bomb_of_type_ComMaddoxIl2ObjectsWeaponsBomb instanceof FuelTank)) return null;
    return (FuelTank)this.jdField_bomb_of_type_ComMaddoxIl2ObjectsWeaponsBomb;
  }

  static {
    Class localClass = FuelTankGun.class;
    Property.set(localClass, "sound", "weapon.bombgun_fueltank");
  }
}