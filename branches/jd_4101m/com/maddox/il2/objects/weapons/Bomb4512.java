package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb4512 extends Torpedo
{
  static
  {
    Class localClass = Bomb4512.class;
    Property.set(localClass, "mesh", "3do/arms/45-12/mono.sim");

    Property.set(localClass, "radius", 20.0F);
    Property.set(localClass, "power", 99.800003F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.45F);
    Property.set(localClass, "massa", 802.0F);
    Property.set(localClass, "sound", "weapon.torpedo");

    Property.set(localClass, "velocity", 22.200001F);
    Property.set(localClass, "traveltime", 270.20001F);
    Property.set(localClass, "startingspeed", 0.0F);

    Property.set(localClass, "impactAngleMin", 14.0F);
    Property.set(localClass, "impactAngleMax", 24.0F);
    Property.set(localClass, "impactSpeed", 74.0F);
    Property.set(localClass, "armingTime", 3.0F);
    Property.set(localClass, "dropAltitude", 30.0F);
    Property.set(localClass, "dropSpeed", 205.0F);
  }
}