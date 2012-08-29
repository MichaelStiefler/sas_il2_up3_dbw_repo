package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb50kgIncJ extends Bomb
{
  static
  {
    Class localClass = Bomb50kgIncJ.class;
    Property.set(localClass, "mesh", "3DO/Arms/50kgIncJ/mono.sim");
    Property.set(localClass, "radius", 20.0F);
    Property.set(localClass, "power", 25.0F);
    Property.set(localClass, "powerType", 2);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 50.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}