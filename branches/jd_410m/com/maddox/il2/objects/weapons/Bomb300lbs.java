package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb300lbs extends Bomb
{
  static
  {
    Class localClass = Bomb300lbs.class;
    Property.set(localClass, "mesh", "3DO/Arms/250LbsBombE/mono.sim");
    Property.set(localClass, "radius", 75.0F);
    Property.set(localClass, "power", 78.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 150.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}