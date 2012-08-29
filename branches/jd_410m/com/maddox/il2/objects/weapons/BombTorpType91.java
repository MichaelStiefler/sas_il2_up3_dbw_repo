package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpType91 extends Torpedo
{
  static
  {
    Class localClass = BombTorpType91.class;
    Property.set(localClass, "mesh", "3DO/Arms/Mk13_Torpedo/mono.sim");
    Property.set(localClass, "radius", 90.800003F);
    Property.set(localClass, "power", 181.89999F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.569F);
    Property.set(localClass, "massa", 874.09998F);
    Property.set(localClass, "sound", "weapon.torpedo");
    Property.set(localClass, "velocity", 17.25F);
    Property.set(localClass, "traveltime", 333.95361F);
    Property.set(localClass, "startingspeed", 0.0F);
    Property.set(localClass, "impactAngleMin", 15.0F);
    Property.set(localClass, "impactAngleMax", 25.0F);
    Property.set(localClass, "impactSpeed", 86.0F);
    Property.set(localClass, "armingTime", 3.0F);
    Property.set(localClass, "dropAltitude", 30.0F);
    Property.set(localClass, "dropSpeed", 240.0F);
  }
}