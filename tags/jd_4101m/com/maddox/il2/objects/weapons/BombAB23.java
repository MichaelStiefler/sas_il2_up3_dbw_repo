package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombAB23 extends Bomb
{
  private long t1;

  public void start()
  {
    super.start();
    this.t1 = (Time.current() + 1000L * ()Math.max(this.delayExplosion, 3.0F) + World.Rnd().nextLong(-250L, 250L));
  }

  public void interpolateTick()
  {
    super.interpolateTick();
    if (this.t1 < Time.current())
      doFireContaineds();
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2) {
    super.msgCollision(paramActor, paramString1, paramString2);
    if ((this.t1 > Time.current()) && (isFuseArmed()))
      doFireContaineds();
  }

  private void doFireContaineds() {
    Explosions.AirFlak(this.pos.getAbsPoint(), 1);
    Actor localActor = null;
    if (Actor.isValid(getOwner()))
      localActor = getOwner();
    Point3d localPoint3d = new Point3d(this.pos.getAbsPoint());
    Orient localOrient = new Orient();
    Vector3d localVector3d = new Vector3d();
    for (int i = 0; i < 23; i++) {
      localOrient.set(World.Rnd().nextFloat(0.0F, 360.0F), World.Rnd().nextFloat(-90.0F, 90.0F), World.Rnd().nextFloat(-180.0F, 180.0F));

      getSpeed(localVector3d);
      localVector3d.add(World.Rnd().nextDouble(-20.0D, 20.0D), World.Rnd().nextDouble(-20.0D, 20.0D), World.Rnd().nextDouble(-20.0D, 20.0D));

      BombSD2A localBombSD2A = new BombSD2A();
      localBombSD2A.pos.setUpdateEnable(true);
      localBombSD2A.pos.setAbs(localPoint3d, localOrient);
      localBombSD2A.pos.reset();
      localBombSD2A.start();
      localBombSD2A.setOwner(localActor, false, false, false);
      localBombSD2A.setSpeed(localVector3d);
    }
    postDestroy();
  }

  static
  {
    Class localClass = BombAB23.class;
    Property.set(localClass, "mesh", "3DO/Arms/AB-23/mono.sim");
    Property.set(localClass, "radius", 1.0F);
    Property.set(localClass, "power", 0.15F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.18F);
    Property.set(localClass, "massa", 46.0F);
    Property.set(localClass, "sound", "weapon.bomb_std");
  }
}