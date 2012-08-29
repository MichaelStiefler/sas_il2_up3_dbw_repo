package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb1600lbs extends Bomb
{
  static
  {
    Class localClass = Bomb1600lbs.class;
    Property.set(localClass, "mesh", "3DO/Arms/1600LbsBomb/mono.sim");
    Property.set(localClass, "radius", 250.0F);
    Property.set(localClass, "power", 400.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 800.0F);
    Property.set(localClass, "sound", "weapon.bomb_big");
  }
}