package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb5327 extends Torpedo
{
  static
  {
    Class localClass = Bomb5327.class;
    Property.set(localClass, "mesh", "3do/arms/45-12/mono.sim");

    Property.set(localClass, "radius", 20.0F);
    Property.set(localClass, "power", 265.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.533F);
    Property.set(localClass, "massa", 1600.0F);
    Property.set(localClass, "sound", "weapon.torpedo");

    Property.set(localClass, "velocity", 23.25F);
    Property.set(localClass, "traveltime", 159.13F);
    Property.set(localClass, "startingspeed", 40.0F);

    Property.set(localClass, "impactAngleMin", 14.0F);
    Property.set(localClass, "impactAngleMax", 24.0F);
    Property.set(localClass, "impactSpeed", 74.0F);
    Property.set(localClass, "armingTime", 3.0F);
  }
}