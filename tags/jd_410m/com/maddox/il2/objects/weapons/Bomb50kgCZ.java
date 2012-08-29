package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb50kgCZ extends Bomb
{
  static
  {
    Class localClass = Bomb50kgCZ.class;
    Property.set(localClass, "mesh", "3do/arms/S328bomb-50kg/mono.sim");
    Property.set(localClass, "radius", 75.0F);
    Property.set(localClass, "power", 25.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 50.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}