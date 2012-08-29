package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombSC70 extends Bomb
{
  static
  {
    Class localClass = BombSC70.class;
    Property.set(localClass, "mesh", "3do/arms/sc-70/mono.sim");
    Property.set(localClass, "radius", 25.0F);
    Property.set(localClass, "power", 32.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.22F);
    Property.set(localClass, "massa", 70.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}