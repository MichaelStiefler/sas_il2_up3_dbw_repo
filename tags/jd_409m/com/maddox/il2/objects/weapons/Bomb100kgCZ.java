package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb100kgCZ extends Bomb
{
  static
  {
    Class localClass = Bomb100kgCZ.class;
    Property.set(localClass, "mesh", "3do/arms/S328bomb-100kgCZ/mono.sim");
    Property.set(localClass, "radius", 125.0F);
    Property.set(localClass, "power", 50.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 100.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}