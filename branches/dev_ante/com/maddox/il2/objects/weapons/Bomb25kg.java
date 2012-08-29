package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb25kg extends Bomb
{
  static
  {
    Class localClass = Bomb25kg.class;

    Property.set(localClass, "mesh", "3DO/Arms/30kgBombJ/mono.sim");
    Property.set(localClass, "radius", 20.0F);
    Property.set(localClass, "power", 15.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 25.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}