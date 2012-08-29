package com.maddox.il2.objects.trains;

import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.rts.Spawn;

public class Passenger extends Wagon
  implements MsgCollisionRequestListener, MsgExplosionListener, MsgShotListener
{
  private static Class cls = Passenger.class;

  public Passenger(Train paramTrain) {
    super(paramTrain, getMeshName(0), getMeshName(1));

    this.life = 0.015F;
    this.ignoreTNT = 0.42F;
    this.killTNT = 1.5F;
    this.bodyMaterial = 3;
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

    return "3do/Trains/Wagon" + (paramInt != 1 ? "" : "_Dmg") + "/" + str + "/hier.him";
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
      return new Passenger(paramTrain);
    }
  }
}