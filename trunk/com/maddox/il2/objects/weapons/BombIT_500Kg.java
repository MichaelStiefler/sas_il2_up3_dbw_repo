package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombIT_500Kg extends Bomb
{
  static
  {
    Class localClass = BombIT_500Kg.class;
    Property.set(localClass, "mesh", "3DO/Arms/BombIT500Kg/mono.sim");
    Property.set(localClass, "radius", 170.0F);
    Property.set(localClass, "power", 216.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.38F);
    Property.set(localClass, "massa", 508.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}