package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Property;

public class RocketSimpleRS82 extends RocketSimple
{
  public RocketSimpleRS82(Point3d paramPoint3d, Orient paramOrient, Actor paramActor)
  {
    super(paramPoint3d, paramOrient, paramActor);
  }

  static
  {
    Class localClass = RocketSimpleRS82.class;
    Property.set(localClass, "mesh", "3do/arms/RS-82/mono.sim");

    Property.set(localClass, "sprite", "3do/effects/rocket/firesprite.eff");
    Property.set(localClass, "flame", "3do/effects/rocket/mono.sim");
    Property.set(localClass, "smoke", "3do/effects/rocket/rocketsmokewhite.eff");
    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
    Property.set(localClass, "emitLen", 50.0F);
    Property.set(localClass, "emitMax", 1.0F);

    Property.set(localClass, "sound", "weapon.rocket_132");

    Property.set(localClass, "radius", 24.0F);
    Property.set(localClass, "timeLife", 5.5F);
    Property.set(localClass, "timeFire", 4.0F);
    Property.set(localClass, "force", 1300.0F);

    Property.set(localClass, "power", 0.36F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.082F);
    Property.set(localClass, "massa", 6.7F);
    Property.set(localClass, "massaEnd", 2.9F);
  }
}