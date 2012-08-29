package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb15kgJ extends Bomb
{
  static
  {
    Class localClass = Bomb15kgJ.class;
    Property.set(localClass, "mesh", "3DO/Arms/15kgFragJ/mono.sim");
    Property.set(localClass, "radius", 30.0F);
    Property.set(localClass, "power", 7.5F);
    Property.set(localClass, "powerType", 1);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 15.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}