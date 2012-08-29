package com.maddox.il2.objects.trains;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Spawn;

public class OilTank extends Wagon
  implements MsgCollisionRequestListener, MsgExplosionListener, MsgShotListener
{
  private static Class cls = OilTank.class;

  protected void explode(Actor paramActor)
  {
    new MsgAction(0.0D) {
      public void doAction() { Point3d localPoint3d = new Point3d();
        OilTank.this.pos.getAbs(localPoint3d);
        Explosions.ExplodeVagonFuel(localPoint3d, localPoint3d, 2.0F);
      }
    };
    new MsgAction(1.0D, new Wagon.Pair(this, this, paramActor)) {
      public void doAction(Object paramObject) {
        Actor localActor = OilTank.this.getOwner();
        if (localActor != null) {
          ((Train)localActor).wagonDied(((Wagon.Pair)paramObject).victim, ((Wagon.Pair)paramObject).initiator);
        }
        OilTank.this.life = -1.0F;
        OilTank.this.ActivateMesh();
      }
    };
    new Wagon.MyMsgAction(1.82D, this, paramActor) {
      public void doAction(Object paramObject) { Point3d localPoint3d = new Point3d();
        OilTank.this.pos.getAbs(localPoint3d);
        float f1 = 180.0F;
        int i = 0;
        float f2 = 120.0F;
        MsgExplosion.send((Actor)paramObject, "Body", localPoint3d, (Actor)this.obj2, 0.0F, f1, i, f2);
      }
    };
    new MsgAction(2.2D, this)
    {
      public void doAction(Object paramObject)
      {
        Wagon localWagon = (Wagon)paramObject;

        Eff3DActor.New(localWagon, new HookNamed(localWagon, "Damage"), null, 1.0F, "Effects/Smokes/SmokeOilTank1.eff", 30.0F);

        Eff3DActor.New(localWagon, new HookNamed(localWagon, "Damage"), null, 1.0F, "Effects/Smokes/SmokeOilTank3.eff", 35.0F);
      }
    };
    new MsgAction(34.0D, this)
    {
      public void doAction(Object paramObject)
      {
        Wagon localWagon = (Wagon)paramObject;
        Eff3DActor.New(localWagon, new HookNamed(localWagon, "Damage"), null, 1.0F, "Effects/Smokes/SmokeOilTank2.eff", 156.0F);
      }
    };
  }

  public OilTank(Train paramTrain)
  {
    super(paramTrain, getMeshName(0), getMeshName(1));

    this.life = 0.02F;
    this.ignoreTNT = 0.35F;
    this.killTNT = 2.8F;
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

    return "3do/Trains/OilTank" + (paramInt != 1 ? "" : "_Dmg") + "/" + str + "/hier.him";
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
      return new OilTank(paramTrain);
    }
  }
}