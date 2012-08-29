package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombSC50 extends Bomb
{
  static
  {
    Class localClass = BombSC50.class;
    Property.set(localClass, "mesh", "3do/arms/sc-50/mono.sim");
    Property.set(localClass, "radius", 25.0F);
    Property.set(localClass, "power", 24.4F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.2F);
    Property.set(localClass, "massa", 55.5F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}