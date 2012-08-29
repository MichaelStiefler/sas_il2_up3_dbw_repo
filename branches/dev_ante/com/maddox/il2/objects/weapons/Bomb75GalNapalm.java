package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb75GalNapalm extends Bomb
{
  static
  {
    Class localClass = Bomb75GalNapalm.class;

    Property.set(localClass, "mesh", "3DO/Arms/Tank75galNapalm/mono.sim");
    Property.set(localClass, "radius", 77.0F);
    Property.set(localClass, "power", 75.0F);
    Property.set(localClass, "powerType", 2);
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 340.0F);
    Property.set(localClass, "sound", "weapon.bomb_std");
  }
}