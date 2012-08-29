package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.rts.Property;

public class RocketWfrGr21 extends Rocket
{
  static
  {
    Class localClass = RocketWfrGr21.class;
    Property.set(localClass, "mesh", "3DO/Arms/WfrGr21Rocket/mono.sim");

    Property.set(localClass, "sprite", "3do/effects/rocket/firesprite.eff");
    Property.set(localClass, "flame", "3do/effects/rocket/mono.sim");
    Property.set(localClass, "smoke", "3do/effects/rocket/rocketsmokewhite.eff");
    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
    Property.set(localClass, "emitLen", 50.0F);
    Property.set(localClass, "emitMax", 1.0F);

    Property.set(localClass, "sound", "weapon.rocket_132");

    Property.set(localClass, "radius", 790.0F);
    Property.set(localClass, "timeLife", 20.0F);
    Property.set(localClass, "timeFire", 1.8F);
    Property.set(localClass, "force", 1720.0F);

    Property.set(localClass, "power", 38.599998F);
    Property.set(localClass, "powerType", 1);
    Property.set(localClass, "kalibr", 0.21F);
    Property.set(localClass, "massa", 110.0F);
    Property.set(localClass, "massaEnd", 91.599998F);
  }
}