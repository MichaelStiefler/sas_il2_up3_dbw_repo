package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombB22EZ extends Bomb
{
  protected boolean haveSound()
  {
    return false;
  }

  static
  {
    Class localClass = BombB22EZ.class;
    Property.set(localClass, "mesh", "3DO/Arms/B22EZ/mono.sim");
    Property.set(localClass, "radius", 1.0F);
    Property.set(localClass, "power", 0.6F);
    Property.set(localClass, "powerType", 2);
    Property.set(localClass, "kalibr", 0.0508F);
    Property.set(localClass, "massa", 2.2F);
    Property.set(localClass, "sound", "weapon.bomb_cassette");
  }
}