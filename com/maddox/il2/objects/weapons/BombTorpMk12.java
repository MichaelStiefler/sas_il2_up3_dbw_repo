package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpMk12 extends Torpedo
{
  static
  {
    Class localClass = BombTorpMk12.class;
    Property.set(localClass, "mesh", "3DO/Arms/Mk12_Torpedo/mono.sim");

    Property.set(localClass, "radius", 85.800003F);
    Property.set(localClass, "power", 176.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.45F);
    Property.set(localClass, "massa", 702.0F);
    Property.set(localClass, "sound", "weapon.torpedo");

    Property.set(localClass, "velocity", 20.549999F);
    Property.set(localClass, "traveltime", 68.120003F);
    Property.set(localClass, "startingspeed", 0.0F);

    Property.set(localClass, "impactAngleMin", 14.0F);
    Property.set(localClass, "impactAngleMax", 27.0F);
    Property.set(localClass, "impactSpeed", 90.0F);
    Property.set(localClass, "armingTime", 3.0F);

    Property.set(localClass, "dropAltitude", 40.0F);
    Property.set(localClass, "dropSpeed", 250.0F);
  }
}