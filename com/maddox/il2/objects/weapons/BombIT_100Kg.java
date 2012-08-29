package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombIT_100Kg extends Bomb
{
  static
  {
    Class localClass = BombIT_100Kg.class;
    Property.set(localClass, "mesh", "3DO/Arms/BombIT100kg/mono.sim");
    Property.set(localClass, "radius", 110.0F);
    Property.set(localClass, "power", 49.5F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 139.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}