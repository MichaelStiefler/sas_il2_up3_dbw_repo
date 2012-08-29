package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombPuW125 extends Bomb
{
  static
  {
    Class localClass = BombPuW125.class;
    Property.set(localClass, "mesh", "3DO/Arms/PuW-12_5/mono.sim");
    Property.set(localClass, "radius", 25.0F);
    Property.set(localClass, "power", 1.01F);
    Property.set(localClass, "powerType", 1);
    Property.set(localClass, "kalibr", 0.14F);
    Property.set(localClass, "massa", 12.5F);
    Property.set(localClass, "randomOrient", 1);
    Property.set(localClass, "sound", "weapon.bomb_cassette");
  }
}