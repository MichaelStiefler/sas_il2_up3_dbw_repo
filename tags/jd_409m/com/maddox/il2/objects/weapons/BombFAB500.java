package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombFAB500 extends Bomb
{
  static
  {
    Class localClass = BombFAB500.class;
    Property.set(localClass, "mesh", "3do/arms/fab-500/mono.sim");

    Property.set(localClass, "radius", 250.0F);
    Property.set(localClass, "power", 275.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.678F);
    Property.set(localClass, "massa", 500.0F);
    Property.set(localClass, "sound", "weapon.bomb_std");
  }
}