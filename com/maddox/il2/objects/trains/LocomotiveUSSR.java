package com.maddox.il2.objects.trains;

import com.maddox.il2.ai.World;
import com.maddox.rts.Spawn;

public class LocomotiveUSSR extends LocomotiveVerm
{
  private static Class cls = LocomotiveUSSR.class;

  public LocomotiveUSSR(Train paramTrain) {
    super(paramTrain, getMeshName(0), getMeshName(1));
  }

  private static String getMeshName(int paramInt) {
    switch (World.cur().camouflage) {
    case 0:
    case 1:
    case 2:
    }
    return "3do/Trains/Prvz" + (paramInt == 1 ? "_Dmg" : "") + "/hier.him";
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