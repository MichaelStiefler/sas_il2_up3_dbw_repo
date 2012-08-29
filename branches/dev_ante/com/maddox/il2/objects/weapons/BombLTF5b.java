package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombLTF5b extends Torpedo
{
  static
  {
    Class localClass = BombLTF5b.class;

    Property.set(localClass, "mesh", "3do/arms/45-12/mono.sim");
    Property.set(localClass, "radius", 20.0F);
    Property.set(localClass, "power", 160.8F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.533F);
    Property.set(localClass, "massa", 766.0F);
    Property.set(localClass, "sound", "weapon.torpedo");
    Property.set(localClass, "velocity", 25.200001F);
    Property.set(localClass, "traveltime", 270.20001F);
    Property.set(localClass, "startingspeed", 0.0F);
  }
}