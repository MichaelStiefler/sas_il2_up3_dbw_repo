package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb250kg extends Bomb
{
  static
  {
    Class localClass = Bomb250kg.class;
    Property.set(localClass, "mesh", "3DO/Arms/250kgBomb/mono.sim");
    Property.set(localClass, "radius", 125.0F);
    Property.set(localClass, "power", 125.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 250.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}