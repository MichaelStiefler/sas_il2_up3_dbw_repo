package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombAmpoule extends Bomb
{
  static
  {
    Class localClass = BombAmpoule.class;
    Property.set(localClass, "mesh", "3DO/Arms/Ampoule/mono.sim");
    Property.set(localClass, "radius", 0.5F);
    Property.set(localClass, "power", 0.5F);
    Property.set(localClass, "powerType", 2);
    Property.set(localClass, "kalibr", 0.171F);
    Property.set(localClass, "massa", 1.2F);
    Property.set(localClass, "randomOrient", 1);
  }
}