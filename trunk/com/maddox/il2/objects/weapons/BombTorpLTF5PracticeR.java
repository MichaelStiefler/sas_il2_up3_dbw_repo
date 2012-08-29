package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpLTF5PracticeR extends Torpedo
{
  static
  {
    Class localClass = BombTorpLTF5PracticeR.class;
    Property.set(localClass, "mesh", "3do/arms/Practice/mono.sim");
    Property.set(localClass, "radius", 0.2F);
    Property.set(localClass, "power", 1.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.45F);
    Property.set(localClass, "massa", 725);
    Property.set(localClass, "sound", "weapon.torpedo");
    Property.set(localClass, "velocity", 20.58F);
    Property.set(localClass, "traveltime", 98.169998F);
    Property.set(localClass, "startingspeed", 0.0F);
    Property.set(localClass, "impactAngleMin", 18.0F);
    Property.set(localClass, "impactAngleMax", 27.0F);
    Property.set(localClass, "impactSpeed", 88.0F);
    Property.set(localClass, "armingTime", 2.0F);
    Property.set(localClass, "dropAltitude", 40.0F);
    Property.set(localClass, "dropSpeed", 250.0F);
    Property.set(localClass, "spreadDirection", 1);
  }
}