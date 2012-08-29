package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.air.Chute;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombCargoA extends Bomb
{
  private Chute chute = null;
  private boolean bOnChute = false;
  private static Orient or = new Orient();

  private static Vector3d v3d = new Vector3d();
  private float ttcurTM;

  protected boolean haveSound()
  {
    return false;
  }
  public void start() {
    super.start();
    this.ttcurTM = World.Rnd().nextFloat(0.5F, 1.75F);
  }

  public void interpolateTick()
  {
    super.interpolateTick();
    getSpeed(v3d);

    or.setAT0(v3d);
    this.pos.setAbs(or);

    if (this.bOnChute) {
      v3d.scale(0.99D);
      if (v3d.z < -5.0D) {
        v3d.z += 1.1F * Time.tickConstLenFs();
      }
      setSpeed(v3d);
    } else if (this.curTm > this.ttcurTM) {
      this.bOnChute = true;
      this.chute = new Chute(this);
      this.chute.collide(false);
      this.chute.mesh().setScale(1.5F);
      this.chute.pos.setRel(new Point3d(2.0D, 0.0D, 0.0D), new Orient(0.0F, 90.0F, 0.0F));
    }
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2) {
    if ((paramActor instanceof ActorLand)) {
      if (this.chute != null) {
        this.chute.landing();
      }

      Loc localLoc = new Loc();
      this.pos.getAbs(localLoc);
      localLoc.getPoint().z = Engine.land().HQ(localLoc.getPoint().x, localLoc.getPoint().y);
      if (!Engine.land().isWater(localLoc.getPoint().x, localLoc.getPoint().y)) {
        localLoc.getOrient().set(localLoc.getOrient().getAzimut(), -90.0F, 0.0F);
        ActorSimpleMesh localActorSimpleMesh = new ActorSimpleMesh("3DO/Arms/Cargo-TypeA/mono.sim", localLoc);
        localActorSimpleMesh.collide(false);
        localActorSimpleMesh.postDestroy(150000L);
      }

    }
    else if (this.chute != null) {
      this.chute.destroy();
    }

    destroy();
  }

  static {
    Class localClass = BombCargoA.class;
    Property.set(localClass, "mesh", "3DO/Arms/Cargo-TypeA/mono.sim");
    Property.set(localClass, "radius", 1.0F);
    Property.set(localClass, "power", 6.0F);
    Property.set(localClass, "powerType", 1);
    Property.set(localClass, "kalibr", 1.0F);
    Property.set(localClass, "massa", 500.0F);
    Property.set(localClass, "sound", (String)null);
  }
}