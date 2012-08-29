package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpMk13a extends Torpedo
{
  static
  {
    Class localClass = BombTorpMk13a.class;
    Property.set(localClass, "mesh", "3DO/Arms/Beau_torpedo/mono.sim");

    Property.set(localClass, "radius", 90.800003F);
    Property.set(localClass, "power", 181.89999F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.569F);
    Property.set(localClass, "massa", 874.09998F);
    Property.set(localClass, "sound", "weapon.torpedo");

    Property.set(localClass, "velocity", 17.25F);
    Property.set(localClass, "traveltime", 333.95361F);
    Property.set(localClass, "startingspeed", 0.0F);
  }
}