package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb800kgJ extends Bomb
{
  static
  {
    Class localClass = Bomb800kgJ.class;
    Property.set(localClass, "mesh", "3DO/Arms/800kgBombJ/mono.sim");
    Property.set(localClass, "radius", 400.0F);
    Property.set(localClass, "power", 400.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 800.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}