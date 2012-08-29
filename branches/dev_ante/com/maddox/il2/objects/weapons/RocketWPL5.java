package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.ActorLand;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class RocketWPL5 extends Rocket
{
  private long t1;
  private long t2;

  public void start(float paramFloat)
  {
    super.start(-1.0F);
    drawing(false);
    this.t1 = (Time.current() + 1850000L + World.Rnd().nextLong(0L, 850L));
    this.t2 = (Time.current() + 1850000L + World.Rnd().nextLong(0L, 3800L));
    Eff3DActor.New(this, null, new Loc(), 1.0F, "3DO/Effects/Rocket/rocketsmokewhite2.eff", (float)(this.t1 - Time.current()) / 1000.0F);
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if ((paramActor instanceof ActorLand)) {
      if (Time.current() <= (this.t2 + this.t1) / 2L) {
        Point3d localPoint3d = new Point3d();
        this.pos.getTime(Time.current(), localPoint3d);
        Class localClass = getClass();
        float f1 = Property.floatValue(localClass, "power", 0.0F);
        int i = Property.intValue(localClass, "powerType", 0);
        float f2 = Property.floatValue(localClass, "radius", 1.0F);
        MsgExplosion.send(paramActor, paramString2, localPoint3d, getOwner(), this.M, f1, i, f2);
        Vector3d localVector3d = new Vector3d();
        getSpeed(localVector3d);
        localVector3d.x *= 0.5D;
        localVector3d.y *= 0.5D;
        localVector3d.z = 1.0D;

        setSpeed(localVector3d);
      }
    }
    else super.msgCollision(paramActor, paramString1, paramString2);
  }

  static
  {
    Class localClass = RocketWPL5.class;

    Property.set(localClass, "mesh", "3DO/Arms/4andHalf-inchRocket/mono.sim");

    Property.set(localClass, "sprite", "3DO/Effects/Rocket/rocketsmokewhite.eff");
    Property.set(localClass, "flame", "3DO/Effects/Rocket/mono.sim");

    Property.set(localClass, "smoke", "3DO/Effects/Rocket/rocketsmokewhite2.eff");
    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
    Property.set(localClass, "emitLen", 50.0F);
    Property.set(localClass, "emitMax", 1.0F);
    Property.set(localClass, "sound", "weapon.rocket_132");
    Property.set(localClass, "radius", 0.0F);
    Property.set(localClass, "timeLife", 100.0F);
    Property.set(localClass, "timeFire", 4.0F);
    Property.set(localClass, "force", 0.0F);
    Property.set(localClass, "power", 0.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.08F);
    Property.set(localClass, "massa", 10.1F);
    Property.set(localClass, "massaEnd", 5.1F);
  }
}