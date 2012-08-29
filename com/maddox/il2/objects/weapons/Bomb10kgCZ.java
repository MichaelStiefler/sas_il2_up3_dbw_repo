package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb10kgCZ extends Bomb
{
  static
  {
    Class localClass = Bomb10kgCZ.class;
    Property.set(localClass, "mesh", "3do/arms/S328bomb-10kg/mono.sim");
    Property.set(localClass, "radius", 20.0F);
    Property.set(localClass, "power", 5.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.15F);
    Property.set(localClass, "massa", 10.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}