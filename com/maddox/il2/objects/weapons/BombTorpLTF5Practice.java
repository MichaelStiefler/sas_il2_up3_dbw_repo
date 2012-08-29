package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpLTF5Practice extends Torpedo
{
  static
  {
    Class var_class = BombTorpLTF5Practice.class;

    Property.set(var_class, "mesh", "3do/arms/Practice/mono.sim");
    Property.set(var_class, "radius", 0.2F);
    Property.set(var_class, "power", 1.0F);
    Property.set(var_class, "powerType", 0);
    Property.set(var_class, "kalibr", 0.45F);
    Property.set(var_class, "massa", 725);
    Property.set(var_class, "sound", "weapon.torpedo");
    Property.set(var_class, "velocity", 20.58F);
    Property.set(var_class, "traveltime", 98.169998F);
    Property.set(var_class, "startingspeed", 0.0F);
  }
}