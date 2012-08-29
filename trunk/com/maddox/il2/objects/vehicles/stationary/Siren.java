package com.maddox.il2.objects.vehicles.stationary;

public abstract class Siren
{
  static
  {
    new SirenGeneric.SPAWN(SirenCity.class);
  }

  public static class SirenCity extends SirenGeneric
  {
  }
}