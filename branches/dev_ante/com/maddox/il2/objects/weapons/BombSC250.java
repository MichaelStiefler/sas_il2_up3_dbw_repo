package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombSC250 extends Bomb
{
  static
  {
    Class localClass = BombSC250.class;
    Property.set(localClass, "mesh", "3do/arms/SC-250/mono.sim");
    Property.set(localClass, "radius", 77.0F);
    Property.set(localClass, "power", 130.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.3683F);
    Property.set(localClass, "massa", 248.2F);
    Property.set(localClass, "sound", "weapon.bomb_std");
  }
}