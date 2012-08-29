package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.ActorLand;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class BombPhBall extends Bomb
{
  private long t1;
  private long t2;

  protected boolean haveSound()
  {
    return this.jdField_index_of_type_Int % 8 == 0;
  }

  public void start() {
    super.start();
    drawing(false);

    this.t1 = (Time.current() + 1500L + World.Rnd().nextLong(0L, 850L));
    this.t2 = (Time.current() + 2200L + World.Rnd().nextLong(0L, 3800L));
    Eff3DActor.New(this, null, new Loc(), 1.0F, "3DO/Effects/Fireworks/PhosfourousBall.eff", (float)(this.t1 - Time.current()) / 1000.0F);
  }

  public void interpolateTick() {
    this.curTm += Time.tickLenFs();
    Ballistics.updateBomb(this, this.jdField_M_of_type_Float, this.S, this.J, this.DistFromCMtoStab);
    updateSound();
    if (this.t1 < Time.current()) {
      if (Config.isUSE_RENDER()) {
        Eff3DActor localEff3DActor = Eff3DActor.New(this, null, new Loc(), 1.0F, "3DO/Effects/Fireworks/PhosfourousFire.eff", (float)(this.t2 - this.t1) / 1000.0F);
        if (this.jdField_index_of_type_Int % 30 == 0) {
          LightPointActor localLightPointActor = new LightPointActor(new LightPointWorld(), new Point3d());
          localLightPointActor.light.setColor(1.0F, 1.0F, 0.0F);
          localLightPointActor.light.setEmit(1.0F, 300.0F);
          localEff3DActor.draw.lightMap().put("light", localLightPointActor);
        }
      }
      this.t1 = (this.t2 + 1L);
    }
    if (this.t2 < Time.current())
      postDestroy();
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if ((paramActor instanceof ActorLand)) {
      if (Time.current() > (this.t2 + this.t1) / 2L) return;
      Point3d localPoint3d = new Point3d();
      this.pos.getTime(Time.current(), localPoint3d);
      Class localClass = getClass();
      float f1 = Property.floatValue(localClass, "power", 0.0F);
      int i = Property.intValue(localClass, "powerType", 0);
      float f2 = Property.floatValue(localClass, "radius", 1.0F);
      MsgExplosion.send(paramActor, paramString2, localPoint3d, getOwner(), this.jdField_M_of_type_Float, f1, i, f2);
      Vector3d localVector3d = new Vector3d();
      getSpeed(localVector3d);
      localVector3d.x *= 0.5D;
      localVector3d.y *= 0.5D;
      localVector3d.z = 1.0D;
      setSpeed(localVector3d);
      return;
    }
    super.msgCollision(paramActor, paramString1, paramString2);
  }

  static
  {
    Class localClass = BombPhBall.class;
    Property.set(localClass, "mesh", "3DO/Arms/Null/mono.sim");

    Property.set(localClass, "radius", 5.5F);
    Property.set(localClass, "power", 10.0F);
    Property.set(localClass, "powerType", 2);
    Property.set(localClass, "kalibr", 0.1F);
    Property.set(localClass, "massa", 0.5F);
    Property.set(localClass, "sound", "weapon.bomb_phball");
  }
}