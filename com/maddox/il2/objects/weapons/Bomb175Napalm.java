package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb175Napalm extends Bomb
{
  static
  {
    Class localClass = Bomb175Napalm.class;
    Property.set(localClass, "mesh", "3DO/Arms/F4U_Napalm/mono.sim");
    Property.set(localClass, "radius", 78.680496F);
    Property.set(localClass, "power", 175.0F);
    Property.set(localClass, "powerType", 2);
    Property.set(localClass, "kalibr", 0.408F);
    Property.set(localClass, "massa", 526.0F);
    Property.set(localClass, "sound", "weapon.bomb_std");
  }
}