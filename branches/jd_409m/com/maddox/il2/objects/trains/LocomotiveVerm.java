package com.maddox.il2.objects.trains;

import com.maddox.JGP.Point3d;
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

public class LocomotiveVerm extends Wagon
  implements MsgCollisionRequestListener, MsgExplosionListener, MsgShotListener
{
  private static Class cls = LocomotiveVerm.class;
  protected Eff3DActor pipe;

  protected void explode(Actor paramActor)
  {
    new MsgAction(0.0D) {
      public void doAction() { Point3d localPoint3d = new Point3d();
        LocomotiveVerm.this.pos.getAbs(localPoint3d);
        Explosions.ExplodeVagonArmor(localPoint3d, localPoint3d, 2.0F);
      }
    };
    new MsgAction(1.0D, new Wagon.Pair(this, this, paramActor)) {
      public void doAction(Object paramObject) {
        Actor localActor = LocomotiveVerm.this.getOwner();
        if (localActor != null) {
          ((Train)localActor).wagonDied(((Wagon.Pair)paramObject).victim, ((Wagon.Pair)paramObject).initiator);
        }
        LocomotiveVerm.this.life = -1.0F;
        LocomotiveVerm.this.ActivateMesh();
      } } ;
  }

  protected LocomotiveVerm(Train paramTrain, String paramString1, String paramString2) {
    super(paramTrain, paramString1, paramString2);
    this.life = 0.015F;
    this.ignoreTNT = 0.5F;
    this.killTNT = 3.5F;
    this.bodyMaterial = 2;
    this.pipe = Eff3DActor.New(this, new HookNamed(this, "Vapor"), null, 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", -1.0F);
  }

  public LocomotiveVerm(Train paramTrain)
  {
    this(paramTrain, getMeshName(0), getMeshName(1));
  }

  private static String getMeshName(int paramInt)
  {
    switch (World.cur().camouflage) {
    case 0:
    case 1:
    case 2:
    }
    return "3do/Trains/Prvz_B" + (paramInt == 1 ? "_Dmg" : "") + "/hier.him";
  }

  public static String getMeshNameForEditor()
  {
    return getMeshName(0);
  }

  void place(Point3d paramPoint3d1, Point3d paramPoint3d2, boolean paramBoolean1, boolean paramBoolean2)
  {
    super.place(paramPoint3d1, paramPoint3d2, paramBoolean1, paramBoolean2);

    if (this.pipe == null)
      return;
    float f;
    if (!IsDead()) {
      f = ((Train)getOwner()).getEngineSmokeKoef();
      if ((f == 0.0F) && (((Train)getOwner()).stoppedForever()))
        f = -1.0F;
    }
    else {
      f = -1.0F;
    }

    if (f >= 0.0F) {
      this.pipe._setIntesity(f);
    } else {
      this.pipe._finish();
      this.pipe = null;
    }
  }

  protected void ActivateMesh() {
    if ((IsDead()) && 
      (this.pipe != null)) {
      this.pipe._finish();
      this.pipe = null;
    }

    super.ActivateMesh();
  }

  static
  {
    Spawn.add(cls, new SPAWN());
  }

  public static class SPAWN implements WagonSpawn {
    public Wagon wagonSpawn(Train paramTrain) {
      return new LocomotiveVerm(paramTrain);
    }
  }
}