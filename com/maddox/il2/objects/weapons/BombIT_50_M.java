package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombIT_50_M extends Bomb
{
  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try
    {
      localClass = Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
    return localClass;
  }

  static
  {
    Class localClass = BombIT_50_M.class;
    Property.set(localClass, "mesh", "3DO/Arms/50kgBombM/mono.sim");
    Property.set(localClass, "radius", 40.0F);
    Property.set(localClass, "power", 18.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.28F);
    Property.set(localClass, "massa", 59.310001F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}