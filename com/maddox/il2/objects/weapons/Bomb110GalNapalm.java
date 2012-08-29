package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb110GalNapalm extends Bomb
{
  static
  {
    Class localClass = Bomb110GalNapalm.class;

    Property.set(localClass, "mesh", "3DO/Arms/Tank110galNapalm/mono.sim");
    Property.set(localClass, "radius", 113.0F);
    Property.set(localClass, "power", 110.0F);
    Property.set(localClass, "powerType", 2);
    Property.set(localClass, "kalibr", 0.6F);
    Property.set(localClass, "massa", 500.0F);
    Property.set(localClass, "sound", "weapon.bomb_std");
  }
}