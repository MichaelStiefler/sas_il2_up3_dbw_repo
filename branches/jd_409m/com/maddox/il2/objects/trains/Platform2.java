package com.maddox.il2.objects.trains;

import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.rts.Spawn;

public class Platform2 extends Wagon
  implements MsgCollisionRequestListener, MsgExplosionListener, MsgShotListener
{
  private static Class cls = Platform2.class;

  public Platform2(Train paramTrain) {
    super(paramTrain, getMeshName(0), getMeshName(1));
    this.life = 0.012F;
    this.ignoreTNT = 0.4F;
    this.killTNT = 3.0F;
    this.bodyMaterial = 2;
  }

  private static String getMeshName(int paramInt) {
    switch (World.cur().camouflage) {
    case 0:
    case 1:
    case 2:
    }
    return "3do/Trains/Platform2" + (paramInt == 1 ? "_Dmg" : "") + "/hier.him";
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
      return new Platform2(paramTrain);
    }
  }
}