package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class RocketROFS132 extends Rocket
{
  private static Point3d p = new Point3d();

  protected void doExplosion(Actor paramActor, String paramString)
  {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getTime(Time.current(), p);
    MsgExplosion.send(paramActor, paramString, p, getOwner(), 10.1F, 8.4F, 1, 1200.0F);
    super.doExplosion(paramActor, paramString);
  }
  protected void doExplosionAir() {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getTime(Time.current(), p);
    MsgExplosion.send(null, null, p, getOwner(), 10.1F, 8.4F, 1, 1200.0F);
    super.doExplosionAir();
  }

  static
  {
    Class localClass = RocketROFS132.class;
    Property.set(localClass, "mesh", "3do/arms/RS-132-45/mono.sim");

    Property.set(localClass, "sprite", "3do/effects/rocket/firesprite.eff");
    Property.set(localClass, "flame", "3do/effects/rocket/mono.sim");
    Property.set(localClass, "smoke", "3do/effects/rocket/rocketsmokewhite.eff");
    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
    Property.set(localClass, "emitLen", 75.0F);
    Property.set(localClass, "emitMax", 1.0F);

    Property.set(localClass, "sound", "weapon.rocket_132");

    Property.set(localClass, "radius", 112.0F);
    Property.set(localClass, "timeLife", 2.12F);
    Property.set(localClass, "timeFire", 4.0F);
    Property.set(localClass, "force", 1300.0F);

    Property.set(localClass, "power", 8.4F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.132F);
    Property.set(localClass, "massa", 32.0F);
    Property.set(localClass, "massaEnd", 19.0F);
  }
}