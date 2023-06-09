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

public class LocomotiveTrailorVerm extends Wagon
  implements MsgCollisionRequestListener, MsgExplosionListener, MsgShotListener
{
  private static Class cls = LocomotiveTrailorVerm.class;

  protected void explode(Actor paramActor) {
    new MsgAction(0.0D, this) {
      public void doAction(Object paramObject) { Wagon localWagon = (Wagon)paramObject;

        Eff3DActor.New(localWagon, new HookNamed(localWagon, "Damage"), null, 1.0F, "Effects/Smokes/SmokeTrailorFC.eff", 12.0F);
      }
    };
    new MsgAction(7.0D, new Wagon.Pair(this, this, paramActor)) {
      public void doAction(Object paramObject) {
        Actor localActor = LocomotiveTrailorVerm.this.getOwner();
        if (localActor != null) {
          ((Train)localActor).wagonDied(((Wagon.Pair)paramObject).victim, ((Wagon.Pair)paramObject).initiator);
        }
        LocomotiveTrailorVerm.this.life = -1.0F;
        LocomotiveTrailorVerm.this.ActivateMesh();
      } } ;
  }

  public LocomotiveTrailorVerm(Train paramTrain) {
    super(paramTrain, getMeshName(0), getMeshName(1));

    this.life = 0.015F;
    this.ignoreTNT = 0.29F;
    this.killTNT = 1.9F;
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
    default:
      str = "summer";
    }

    return "3do/Trains/PrvzB_B" + (paramInt != 1 ? "" : "_Dmg") + "/" + str + "/hier.him";
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
      return new LocomotiveTrailorVerm(paramTrain);
    }
  }
}