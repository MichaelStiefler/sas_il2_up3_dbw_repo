package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpType91Late extends Torpedo
{
  static
  {
    Class localClass = BombTorpType91Late.class;
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
    Property.set(localClass, "impactAngle", 20.0F);
    Property.set(localClass, "impactAngleMin", 13.5F);
    Property.set(localClass, "impactAngleMax", 26.5F);
    Property.set(localClass, "impactSpeed", 117.0F);
    Property.set(localClass, "armingTime", 4.0F);
    Property.set(localClass, "dropAltitude", 60.0F);
    Property.set(localClass, "dropSpeed", 330.0F);
  }
}