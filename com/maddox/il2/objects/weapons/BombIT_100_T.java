package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombIT_100_T extends Bomb
{
  static
  {
    Class var_class = BombIT_100_T.class;

    Property.set(var_class, "mesh", "3DO/Arms/100kgBombT/mono.sim");
    Property.set(var_class, "radius", 45.0F);
    Property.set(var_class, "power", 27.5F);
    Property.set(var_class, "powerType", 0);
    Property.set(var_class, "kalibr", 0.32F);
    Property.set(var_class, "massa", 131.0F);
    Property.set(var_class, "sound", "weapon.bomb_mid");
  }
}