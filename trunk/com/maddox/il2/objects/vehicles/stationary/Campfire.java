package com.maddox.il2.objects.vehicles.stationary;

public abstract class Campfire
{
  static
  {
    new CampfireGeneric.SPAWN(CampfireAirfield.class);
  }

  public static class CampfireAirfield extends CampfireGeneric
  {
  }
}