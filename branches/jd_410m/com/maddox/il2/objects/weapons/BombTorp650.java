package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorp650 extends Torpedo
{
  static
  {
    Class localClass = BombTorp650.class;
    Property.set(localClass, "mesh", "3DO/Arms/Torpedo650Kg/mono.sim");
    Property.set(localClass, "radius", 80.0F);
    Property.set(localClass, "power", 140.5F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.35F);
    Property.set(localClass, "massa", 650.0F);
    Property.set(localClass, "sound", "weapon.torpedo");
    Property.set(localClass, "velocity", 21.0D);
    Property.set(localClass, "traveltime", 97.181702F);
    Property.set(localClass, "startingspeed", 0.0F);
    Property.set(localClass, "impactAngleMin", 22.0F);
    Property.set(localClass, "impactAngleMax", 33.0F);
    Property.set(localClass, "impactSpeed", 140.0F);
    Property.set(localClass, "armingTime", 3.5F);
    Property.set(localClass, "dropAltitude", 100.0F);
    Property.set(localClass, "dropSpeed", 320.0F);
  }
}