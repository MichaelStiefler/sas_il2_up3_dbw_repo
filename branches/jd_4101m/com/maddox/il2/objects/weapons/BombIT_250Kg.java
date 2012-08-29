package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombIT_250Kg extends Bomb
{
  static
  {
    Class localClass = BombIT_250Kg.class;
    Property.set(localClass, "mesh", "3DO/Arms/BombIT250Kg/mono.sim");
    Property.set(localClass, "radius", 150.0F);
    Property.set(localClass, "power", 106.5F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.38F);
    Property.set(localClass, "massa", 259.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}