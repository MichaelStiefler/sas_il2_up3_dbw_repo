package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb60kgJ extends Bomb
{
  static
  {
    Class localClass = Bomb60kgJ.class;
    Property.set(localClass, "mesh", "3DO/Arms/60kgBombJ/mono.sim");
    Property.set(localClass, "radius", 60.0F);
    Property.set(localClass, "power", 30.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 60.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}