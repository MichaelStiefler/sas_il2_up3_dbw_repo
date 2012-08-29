package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;

public class RocketType3Mk27 extends Rocket
{
  protected void doExplosion(Actor paramActor, String paramString)
  {
    doFireContaineds();
  }
  protected void doExplosionAir() {
    doFireContaineds();
  }

  private final void doFireContaineds()
  {
    Explosions.AirFlak(this.pos.getAbsPoint(), 1);

    Actor localActor = null;
    if (Actor.isValid(getOwner())) {
      localActor = getOwner();
    }

    Point3d localPoint3d = new Point3d(this.pos.getAbsPoint());
    Orient localOrient = new Orient();
    Vector3d localVector3d = new Vector3d();
    for (int i = 0; i < 145; i++) {
      localOrient.set(World.Rnd().nextFloat(-180.0F, 180.0F), World.Rnd().nextFloat(-180.0F, 180.0F), World.Rnd().nextFloat(-180.0F, 180.0F));
      getSpeed(localVector3d);
      localVector3d.scale(0.2D);
      localVector3d.add(World.Rnd().nextDouble(-75.0D, 75.0D), World.Rnd().nextDouble(-75.0D, 75.0D), World.Rnd().nextDouble(-75.0D, 75.0D));
      RocketPhBall localRocketPhBall = new RocketPhBall();
      localRocketPhBall.start(-1.0F);
      localRocketPhBall.pos.setUpdateEnable(true);
      localRocketPhBall.pos.setAbs(localPoint3d, localOrient);
      localRocketPhBall.pos.reset();

      localRocketPhBall.setOwner(localActor, false, false, false);
      localRocketPhBall.setSpeed(localVector3d);
    }
    postDestroy();
  }

  static
  {
    Class localClass = RocketType3Mk27.class;
    Property.set(localClass, "mesh", "3DO/Arms/Type3No6Mk27B(R)Model1/mono.sim");

    Property.set(localClass, "sprite", "3DO/Effects/Tracers/GuidedRocket/Black.eff");
    Property.set(localClass, "flame", "3DO/Effects/Rocket/mono.sim");
    Property.set(localClass, "smoke", "3DO/Effects/Tracers/GuidedRocket/White.eff");
    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
    Property.set(localClass, "emitLen", 50.0F);
    Property.set(localClass, "emitMax", 1.0F);
    Property.set(localClass, "sound", "weapon.rocket_132");

    Property.set(localClass, "radius", 40.0F);
    Property.set(localClass, "timeLife", 10.0F);
    Property.set(localClass, "timeFire", 10.0F);
    Property.set(localClass, "force", 10000.0F);

    Property.set(localClass, "power", 2.2275F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.3F);
    Property.set(localClass, "massa", 58.806F);
    Property.set(localClass, "massaEnd", 49.896F);
  }
}