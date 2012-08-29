package com.maddox.il2.objects.trains;

import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Spawn;

public class LocomotiveTrailorUSSR extends Wagon
  implements MsgCollisionRequestListener, MsgExplosionListener, MsgShotListener
{
  private static Class cls = LocomotiveTrailorUSSR.class;

  protected void explode(Actor paramActor) {
    new MsgAction(0.0D, this) {
      public void doAction(Object paramObject) { Wagon localWagon = (Wagon)paramObject;
        Eff3DActor.New(localWagon, new HookNamed(localWagon, "Select1"), null, 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", 12.0F);
      }
    };
    new MsgAction(7.0D, new Wagon.Pair(this, this, paramActor)) {
      public void doAction(Object paramObject) {
        Actor localActor = LocomotiveTrailorUSSR.this.getOwner();
        if (localActor != null) {
          ((Train)localActor).wagonDied(((Wagon.Pair)paramObject).victim, ((Wagon.Pair)paramObject).initiator);
        }
        LocomotiveTrailorUSSR.this.life = -1.0F;
        LocomotiveTrailorUSSR.this.ActivateMesh();
      } } ;
  }

  public LocomotiveTrailorUSSR(Train paramTrain) {
    super(paramTrain, getMeshName(0), getMeshName(1));
    this.life = 0.008F;
    this.ignoreTNT = 0.2F;
    this.killTNT = 1.1F;
    this.bodyMaterial = 2;
  }

  private static String getMeshName(int paramInt) {
    switch (World.cur().camouflage) {
    case 0:
    case 1:
    case 2:
    }
    return "3do/Trains/PrvzB" + (paramInt == 1 ? "_Dmg" : "") + "/hier.him";
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
      return new LocomotiveTrailorUSSR(paramTrain);
    }
  }
}