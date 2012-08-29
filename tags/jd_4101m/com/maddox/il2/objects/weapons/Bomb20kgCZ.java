package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb20kgCZ extends Bomb
{
  static
  {
    Class localClass = Bomb20kgCZ.class;
    Property.set(localClass, "mesh", "3do/arms/S328bomb-20kg/mono.sim");
    Property.set(localClass, "radius", 25.0F);
    Property.set(localClass, "power", 10.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.25F);
    Property.set(localClass, "massa", 20.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}