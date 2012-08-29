package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombAO10 extends Bomb
{
  protected boolean haveSound()
  {
    return this.index % 16 == 0;
  }

  static {
    Class localClass = BombAO10.class;
    Property.set(localClass, "mesh", "3do/arms/ao-10/mono.sim");
    Property.set(localClass, "radius", 25.0F);
    Property.set(localClass, "power", 1.01F);
    Property.set(localClass, "powerType", 1);
    Property.set(localClass, "kalibr", 0.14F);
    Property.set(localClass, "massa", 9.56F);
    Property.set(localClass, "randomOrient", 1);
    Property.set(localClass, "sound", "weapon.bomb_cassette");
  }
}