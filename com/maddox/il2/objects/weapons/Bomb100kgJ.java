package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb100kgJ extends Bomb
{
  static
  {
    Class localClass = Bomb100kgJ.class;
    Property.set(localClass, "mesh", "3DO/Arms/100kgBombJ/mono.sim");
    Property.set(localClass, "radius", 100.0F);
    Property.set(localClass, "power", 50.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 100.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}