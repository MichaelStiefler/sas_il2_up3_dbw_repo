package com.maddox.il2.objects.trains;

import com.maddox.il2.ai.World;
import com.maddox.rts.Spawn;

public class LocomotiveUSSR extends LocomotiveVerm
{
  private static Class cls = LocomotiveUSSR.class;

  public LocomotiveUSSR(Train paramTrain) {
    super(paramTrain, getMeshName(0), getMeshName(1));
  }

  private static String getMeshName(int paramInt)
  {
    String str;
    switch (World.cur().camouflage)
    {
    case 0:
      str = "summer";
      break;
    case 1:
      str = "winter";
      break;
    case 2:
      str = "desert";
      break;
    default:
      str = "summer";
    }

    return "3do/Trains/Prvz" + (paramInt != 1 ? "" : "_Dmg") + "/" + str + "/hier.him";
  }

  public static String getMeshNameForEditor()
  {
    return getMeshName(0);
  }

  static {
    Spawn.add(cls, new SPAWN());
  }

  public static class SPAWN implements WagonSpawn {
    public Wagon wagonSpawn(Train paramTrain) {
      return new LocomotiveUSSR(paramTrain);
    }
  }
}