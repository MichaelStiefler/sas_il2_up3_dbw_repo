package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.rts.Property;

public class RocketHVAR2 extends Rocket
{
  static
  {
    Class localClass = RocketHVAR2.class;
    Property.set(localClass, "mesh", "3DO/Arms/HVAR-2inch/mono.sim");

    Property.set(localClass, "sprite", "3DO/Effects/Rocket/firesprite.eff");
    Property.set(localClass, "flame", "3DO/Effects/Rocket/mono.sim");
    Property.set(localClass, "smoke", "3DO/Effects/Rocket/rocketsmokewhite.eff");
    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
    Property.set(localClass, "emitLen", 25.0F);
    Property.set(localClass, "emitMax", 1.0F);

    Property.set(localClass, "sound", "weapon.rocket_82");

    Property.set(localClass, "radius", 20.0F);
    Property.set(localClass, "timeLife", 999.99902F);
    Property.set(localClass, "timeFire", 4.0F);
    Property.set(localClass, "force", 800.0F);

    Property.set(localClass, "power", 9.6F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.06985F);
    Property.set(localClass, "massa", 11.0F);
    Property.set(localClass, "massaEnd", 6.0F);
  }
}