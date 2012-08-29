package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb600kgJ extends Bomb
{
  static
  {
    Class localClass = Bomb600kgJ.class;
    Property.set(localClass, "mesh", "3DO/Arms/600kgBombJ/mono.sim");
    Property.set(localClass, "radius", 600.0F);
    Property.set(localClass, "power", 300.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 600.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}