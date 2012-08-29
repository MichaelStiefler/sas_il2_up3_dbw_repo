package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpMk34 extends Torpedo
{
  static
  {
    Class localClass = BombTorpMk34.class;

    Property.set(localClass, "mesh", "3DO/Arms/Mk34_Torpedo/mono.sim");
    Property.set(localClass, "radius", 90.800003F);
    Property.set(localClass, "power", 160.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.569F);
    Property.set(localClass, "massa", 874.09998F);
    Property.set(localClass, "sound", "weapon.torpedo");
    Property.set(localClass, "velocity", 17.0F);
    Property.set(localClass, "traveltime", 150.0F);
    Property.set(localClass, "startingspeed", 0.0F);
  }
}