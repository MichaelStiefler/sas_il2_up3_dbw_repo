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
    if (!(this.bomb instanceof FuelTank)) return null;
    return (FuelTank)this.bomb;
  }

  static {
    Class localClass = FuelTankGun.class;
    Property.set(localClass, "sound", "weapon.bombgun_fueltank");
  }
}