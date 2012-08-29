package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.rts.Property;

public class Rocket132 extends Rocket
{
  static
  {
    Class localClass = Rocket132.class;
    Property.set(localClass, "mesh", "3do/arms/rs-132-41/mono.sim");
    Property.set(localClass, "sprite", "3do/effects/rocket/firesprite.eff");
    Property.set(localClass, "flame", "3do/effects/rocket/mono.sim");
    Property.set(localClass, "smoke", "3do/effects/rocket/rocketsmokewhite.eff");
    Property.set(localClass, "sound", "weapon.rocket_132");
    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
    Property.set(localClass, "emitLen", 50.0F);
    Property.set(localClass, "emitMax", 1.0F);

    Property.set(localClass, "radius", 25.0F);

    Property.set(localClass, "timeLife", 5.5F);
    Property.set(localClass, "timeFire", 4.0F);
    Property.set(localClass, "force", 1300.0F);

    Property.set(localClass, "power", 2.1F);

    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.132F);
    Property.set(localClass, "massa", 23.1F);

    Property.set(localClass, "massaEnd", 10.1F);
  }
}