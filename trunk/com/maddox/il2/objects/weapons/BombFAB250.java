package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombFAB250 extends Bomb
{
  static
  {
    Class localClass = BombFAB250.class;
    Property.set(localClass, "mesh", "3do/arms/fab-250/mono.sim");

    Property.set(localClass, "radius", 50.0F);
    Property.set(localClass, "power", 120.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.408F);
    Property.set(localClass, "massa", 250.0F);
    Property.set(localClass, "sound", "weapon.bomb_std");
  }
}