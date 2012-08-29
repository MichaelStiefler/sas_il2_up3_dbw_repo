package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpFiumeL extends Torpedo
{
  static
  {
    Class localClass = BombTorpFiumeL.class;
    Property.set(localClass, "mesh", "3DO/Arms/fiume/mono.sim");
    Property.set(localClass, "radius", 110.0F);
    Property.set(localClass, "power", 214.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.45F);
    Property.set(localClass, "massa", 905.0F);
    Property.set(localClass, "sound", "weapon.torpedo");
    Property.set(localClass, "velocity", 21.6F);
    Property.set(localClass, "traveltime", 138.88879F);
    Property.set(localClass, "startingspeed", 0.0F);

    Property.set(localClass, "impactAngleMin", 24.0F);
    Property.set(localClass, "impactAngleMax", 31.5F);
    Property.set(localClass, "impactSpeed", 107.0F);
    Property.set(localClass, "armingTime", 3.5F);
    Property.set(localClass, "dropAltitude", 100.0F);
    Property.set(localClass, "dropSpeed", 300.0F);
    Property.set(localClass, "spreadDirection", -1);
  }
}