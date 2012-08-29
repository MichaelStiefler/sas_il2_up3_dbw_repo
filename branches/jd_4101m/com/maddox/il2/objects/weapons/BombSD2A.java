package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombSD2A extends Bomb
{
  protected boolean haveSound()
  {
    return this.index % 10 == 0;
  }

  static
  {
    Class localClass = BombSD2A.class;
    Property.set(localClass, "mesh", "3DO/Arms/SD-2A/mono.sim");

    Property.set(localClass, "radius", 25.0F);
    Property.set(localClass, "power", 0.2126F);

    Property.set(localClass, "powerType", 1);
    Property.set(localClass, "kalibr", 0.0775F);
    Property.set(localClass, "massa", 2.0284F);
    Property.set(localClass, "sound", "weapon.bomb_cassette");
  }
}