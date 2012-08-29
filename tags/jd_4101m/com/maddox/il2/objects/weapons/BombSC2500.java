package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombSC2500 extends Bomb
{
  static
  {
    Class localClass = BombSC2500.class;
    Property.set(localClass, "mesh", "3DO/Arms/SC-2500/mono.sim");
    Property.set(localClass, "radius", 325.0F);
    Property.set(localClass, "power", 1200.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.8128F);
    Property.set(localClass, "massa", 2400.0F);
    Property.set(localClass, "sound", "weapon.bomb_big");
  }
}