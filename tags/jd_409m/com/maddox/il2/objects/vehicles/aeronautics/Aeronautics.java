package com.maddox.il2.objects.vehicles.aeronautics;

public abstract class Aeronautics
{
  static
  {
    new AeroanchoredGeneric.SPAWN(BarrageBalloon_600m.class);

    new AeroanchoredGeneric.SPAWN(BarrageBalloon_1200m.class);

    new AeroanchoredGeneric.SPAWN(BarrageBalloon_1800m.class);

    new AeroanchoredGeneric.SPAWN(BarrageBalloon_2400m.class);

    new AeroanchoredGeneric.SPAWN(ObservBalloon_30m.class);

    new AeroanchoredGeneric.SPAWN(ObservBalloon_60m.class);

    new AeroanchoredGeneric.SPAWN(ObservBalloon_90m.class);
  }

  public static class ObservBalloon_90m extends AeroanchoredGeneric
  {
  }

  public static class ObservBalloon_60m extends AeroanchoredGeneric
  {
  }

  public static class ObservBalloon_30m extends AeroanchoredGeneric
  {
  }

  public static class BarrageBalloon_2400m extends AeroanchoredGeneric
  {
  }

  public static class BarrageBalloon_1800m extends AeroanchoredGeneric
  {
  }

  public static class BarrageBalloon_1200m extends AeroanchoredGeneric
  {
  }

  public static class BarrageBalloon_600m extends AeroanchoredGeneric
  {
  }
}