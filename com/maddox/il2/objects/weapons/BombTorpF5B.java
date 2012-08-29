package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpF5B extends TorpedoLtfFiume
{
  static
  {
    Class class1 = BombTorpF5B.class;
    Property.set(class1, "mesh", "3DO/Arms/LTF5B/mono.sim");
    Property.set(class1, "radius", 95.0F);
    Property.set(class1, "power", 192.60001F);
    Property.set(class1, "powerType", 0);
    Property.set(class1, "kalibr", 0.45F);
    Property.set(class1, "massa", 725.0F);
    Property.set(class1, "sound", "weapon.torpedo");
    Property.set(class1, "velocity", 20.58F);
    Property.set(class1, "traveltime", 97.181702F);
    Property.set(class1, "startingspeed", 0.0F);
  }

  static Class _mthclass$(String s)
  {
    try
    {
      class1 = Class.forName(s);
    }
    catch (ClassNotFoundException classnotfoundexception)
    {
      Class class1;
      throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }
    Class class1;
    return class1;
  }
}