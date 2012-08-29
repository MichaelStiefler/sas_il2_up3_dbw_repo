package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomblet2Kg extends Bomb
{
  protected boolean haveSound()
  {
    return this.index % 16 == 0;
  }

  static {
    Class localClass = Bomblet2Kg.class;
    Property.set(localClass, "mesh", "3do/arms/2KgBomblet/mono.sim");
    Property.set(localClass, "radius", 4.0F);
    Property.set(localClass, "power", 0.12F);
    Property.set(localClass, "powerType", 1);
    Property.set(localClass, "kalibr", 0.1F);
    Property.set(localClass, "massa", 2.0F);
    Property.set(localClass, "randomOrient", 1);
    Property.set(localClass, "sound", "weapon.bomb_cassette");
  }
}