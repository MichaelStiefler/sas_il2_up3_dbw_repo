package com.maddox.il2.objects.trains;

import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.rts.Spawn;

public class Platform3 extends Wagon
  implements MsgCollisionRequestListener, MsgExplosionListener, MsgShotListener
{
  private static Class cls = Platform3.class;

  public Platform3(Train paramTrain) {
    super(paramTrain, getMeshName(0), getMeshName(1));
    this.life = 0.012F;
    this.ignoreTNT = 0.4F;
    this.killTNT = 3.0F;
    this.bodyMaterial = 2;
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

    return "3do/Trains/Platform3" + (paramInt != 1 ? "" : "_Dmg") + "/" + str + "/hier.him";
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
      return new Platform3(paramTrain);
    }
  }
}