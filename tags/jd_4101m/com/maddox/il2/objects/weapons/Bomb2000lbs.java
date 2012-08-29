package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb2000lbs extends Bomb
{
  static
  {
    Class localClass = Bomb2000lbs.class;
    Property.set(localClass, "mesh", "3DO/Arms/2000LbsBomb/mono.sim");
    Property.set(localClass, "radius", 250.0F);
    Property.set(localClass, "power", 500.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 1000.0F);
    Property.set(localClass, "sound", "weapon.bomb_big");
  }
}