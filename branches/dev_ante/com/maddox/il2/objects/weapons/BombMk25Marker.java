package com.maddox.il2.objects.weapons;

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

public class BombMk25Marker extends Bomb
{
  private long t1;
  private long t2;

  public void start()
  {
    super.start();
    drawing(false);
    this.t1 = (Time.current() + 1850000L + World.Rnd().nextLong(0L, 850L));
    this.t2 = (Time.current() + 1850000L + World.Rnd().nextLong(0L, 3800L));
    Eff3DActor.New(this, null, new Loc(), 1.0F, "3DO/Effects/Smoke/OrangeSmoke.eff", (float)(this.t1 - Time.current()) / 1000.0F);
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
    Class localClass = BombMk25Marker.class;

    Property.set(localClass, "mesh", "3DO/Arms/Mk25_Marker/mono.sim");
    Property.set(localClass, "radius", 0.0F);
    Property.set(localClass, "power", 0.0F);
    Property.set(localClass, "powerType", 2);
    Property.set(localClass, "kalibr", 0.15F);
    Property.set(localClass, "massa", 10.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");
  }
}