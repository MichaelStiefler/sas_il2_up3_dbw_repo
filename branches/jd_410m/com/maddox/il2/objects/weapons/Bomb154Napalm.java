package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb154Napalm extends Bomb
{
  static
  {
    Class localClass = Bomb154Napalm.class;
    Property.set(localClass, "mesh", "3DO/Arms/Tank154gal/mono.sim");
    Property.set(localClass, "radius", 77.0F);
    Property.set(localClass, "power", 154.0F);
    Property.set(localClass, "powerType", 2);
    Property.set(localClass, "kalibr", 0.408F);
    Property.set(localClass, "massa", 562.0F);
    Property.set(localClass, "sound", "weapon.bomb_std");
  }
}