package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb100kg extends Bomb
{
  static
  {
    Class localClass = Bomb100kg.class;
    Property.set(localClass, "mesh", "3DO/Arms/bomb-100kg-it/mono.sim");
    Property.set(localClass, "radius", 125.0F);
    Property.set(localClass, "power", 50.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 100.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}