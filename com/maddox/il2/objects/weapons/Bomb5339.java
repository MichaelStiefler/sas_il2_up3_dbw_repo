package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb5339 extends Torpedo
{
  static
  {
    Class localClass = Bomb5339.class;
    Property.set(localClass, "mesh", "3do/arms/45-12/mono.sim");

    Property.set(localClass, "radius", 20.0F);
    Property.set(localClass, "power", 317.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.533F);
    Property.set(localClass, "massa", 1750.0F);
    Property.set(localClass, "sound", "weapon.torpedo");

    Property.set(localClass, "velocity", 20.66F);
    Property.set(localClass, "traveltime", 387.09F);
    Property.set(localClass, "startingspeed", 40.0F);
  }
}