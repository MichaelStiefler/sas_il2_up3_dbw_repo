package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombSD500 extends Bomb
{
  static
  {
    Class localClass = BombSD500.class;
    Property.set(localClass, "mesh", "3do/arms/SD-500/mono.sim");
    Property.set(localClass, "radius", 75.0F);
    Property.set(localClass, "power", 200.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.4445F);
    Property.set(localClass, "massa", 535.0F);
    Property.set(localClass, "sound", "weapon.bomb_std");
  }
}