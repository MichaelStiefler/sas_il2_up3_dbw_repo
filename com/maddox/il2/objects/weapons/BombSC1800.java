package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombSC1800 extends Bomb
{
  static
  {
    Class localClass = BombSC1800.class;
    Property.set(localClass, "mesh", "3DO/Arms/SC-1800/mono.sim");
    Property.set(localClass, "radius", 229.89999F);
    Property.set(localClass, "power", 720.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.6604F);
    Property.set(localClass, "massa", 1780.0F);
    Property.set(localClass, "sound", "weapon.bomb_big");
  }
}