package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombPC1600 extends Bomb
{
  static
  {
    Class localClass = BombPC1600.class;
    Property.set(localClass, "mesh", "3DO/Arms/PC-1600/mono.sim");
    Property.set(localClass, "radius", 32.0F);
    Property.set(localClass, "power", 230.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.5333F);
    Property.set(localClass, "massa", 1600.0F);
    Property.set(localClass, "sound", "weapon.bomb_std");
  }
}