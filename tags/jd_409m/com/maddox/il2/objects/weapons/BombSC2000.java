package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombSC2000 extends Bomb
{
  static
  {
    Class localClass = BombSC2000.class;
    Property.set(localClass, "mesh", "3DO/Arms/SC-2000/mono.sim");
    Property.set(localClass, "radius", 275.79999F);
    Property.set(localClass, "power", 975.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.6604F);
    Property.set(localClass, "massa", 1950.0F);
    Property.set(localClass, "sound", "weapon.bomb_big");
  }
}