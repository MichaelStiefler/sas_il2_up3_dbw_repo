package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class PylonP38PLN2 extends Pylon
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
    Property.set(PylonP38PLN2.class, "mesh", "3DO/Arms/P38_Pylon2/mono.sim");
  }
}