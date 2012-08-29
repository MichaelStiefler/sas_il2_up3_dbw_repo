package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombFAB100 extends Bomb
{
  static
  {
    Class localClass = BombFAB100.class;
    Property.set(localClass, "mesh", "3do/arms/fab-100/mono.sim");
    Property.set(localClass, "radius", 50.0F);
    Property.set(localClass, "power", 45.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 100.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}