package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombFAB50 extends Bomb
{
  static
  {
    Class localClass = BombFAB50.class;
    Property.set(localClass, "mesh", "3do/arms/fab-50/mono.sim");
    Property.set(localClass, "radius", 25.0F);
    Property.set(localClass, "power", 24.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.2505F);
    Property.set(localClass, "massa", 50.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}