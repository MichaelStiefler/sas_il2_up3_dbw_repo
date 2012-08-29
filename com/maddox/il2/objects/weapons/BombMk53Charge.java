package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombMk53Charge extends Bomb
{
  static
  {
    Class localClass = BombMk53Charge.class;

    Property.set(localClass, "mesh", "3DO/Arms/Mk53_Charge/mono.sim");
    Property.set(localClass, "radius", 90.0F);
    Property.set(localClass, "power", 90.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 148.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}