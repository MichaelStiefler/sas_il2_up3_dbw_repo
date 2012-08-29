package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombTIBlue extends Bomb
{
  private long t1;
  private int charge;

  public void start()
  {
    super.start();
    this.t1 = (Time.current() + 1000L * ()Math.max(this.delayExplosion, 4.21F) + World.Rnd().nextLong(-250L, 250L));

    this.charge = 0;
    setName("qqq");
  }

  public void interpolateTick() {
    super.interpolateTick();
    if (this.t1 < Time.current())
      doFireContaineds();
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2) {
    if (this.t1 > Time.current())
      doFireContaineds();
    super.msgCollision(paramActor, paramString1, paramString2);
  }

  private void doFireContaineds() {
    this.charge += 1;
    if (this.charge < 1.01D) {
      Actor localActor = getOwner();
      if (Actor.isValid(localActor)) {
        Point3d localPoint3d = new Point3d();
        Orient localOrient = new Orient();
        Vector3d localVector3d1 = new Vector3d();
        Vector3d localVector3d2 = new Vector3d();
        Loc localLoc1 = new Loc();
        Loc localLoc2 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        this.pos.getCurrent(localLoc1);
        findHook("_Spawn0" + this.charge).computePos(this, localLoc1, localLoc2);
        getSpeed(localVector3d2);
        for (int i = 0; i < 60; i++) {
          localLoc2.get(localPoint3d, localOrient);
          localOrient.increment(World.Rnd().nextFloat(-85.0F, 85.0F), World.Rnd().nextFloat(-85.5F, 85.5F), World.Rnd().nextFloat(-0.5F, 0.5F));

          localVector3d1.set(1.0D, 0.0D, 0.0D);
          localOrient.transform(localVector3d1);
          localVector3d1.scale(World.Rnd().nextDouble(5.0D, 12.199999999999999D));
          localVector3d1.add(localVector3d2);
          BombTiBu localBombTiBu = new BombTiBu();
          localBombTiBu.pos.setUpdateEnable(true);
          localBombTiBu.pos.setAbs(localPoint3d, localOrient);
          localBombTiBu.pos.reset();
          localBombTiBu.start();
          localBombTiBu.setOwner(localActor, false, false, false);
          localBombTiBu.setSpeed(localVector3d1);
        }
        this.t1 = (Time.current() + 1000L);
      }
    }
  }

  static
  {
    Class localClass = BombTIBlue.class;

    Property.set(localClass, "mesh", "3DO/Arms/Flare/mono.sim");
    Property.set(localClass, "radius", 0.0F);
    Property.set(localClass, "power", 0.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 125.0F);
    Property.set(localClass, "sound", "weapon.bomb_big");
  }
}