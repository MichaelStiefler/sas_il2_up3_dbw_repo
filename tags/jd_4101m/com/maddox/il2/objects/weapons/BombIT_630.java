package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombIT_630 extends Bomb
{
  static
  {
    Class localClass = BombIT_630.class;
    Property.set(localClass, "mesh", "3DO/Arms/Bomb630Kg/mono.sim");
    Property.set(localClass, "radius", 190.0F);
    Property.set(localClass, "power", 250.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.38F);
    Property.set(localClass, "massa", 630.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}