package com.maddox.il2.objects.vehicles.lights;

import com.maddox.il2.ai.ground.TgtFlak;
import com.maddox.il2.objects.vehicles.artillery.AAA;

public abstract class Searchlight
{
  static
  {
    new SearchlightGeneric.SPAWN(SL_ManualBlue.class);
  }

  public static class SL_ManualBlue extends SearchlightGeneric
    implements TgtFlak, AAA
  {
  }
}