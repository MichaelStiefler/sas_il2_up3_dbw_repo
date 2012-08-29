package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombIT_250_T extends Bomb
{
  static
  {
    Class localClass = BombIT_250_T.class;
    Property.set(localClass, "mesh", "3DO/Arms/250kgBombT/mono.sim");
    Property.set(localClass, "radius", 90.0F);
    Property.set(localClass, "power", 106.5F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.38F);
    Property.set(localClass, "massa", 259.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}