package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Property;

public class RocketR4M extends Rocket
{
  public void start(float paramFloat, int paramInt)
  {
    setMesh("3DO/Arms/R4M-OPEN/mono.sim");
    super.start(paramFloat, paramInt);
    this.speed.normalize();
    this.speed.scale(525.0D);
    this.noGDelay = -1L;
  }

  static
  {
    Class localClass = RocketR4M.class;
    Property.set(localClass, "mesh", "3DO/Arms/R4M-CLOSED/mono.sim");

    Property.set(localClass, "sprite", "3DO/Effects/Tracers/GuidedRocket/Black.eff");
    Property.set(localClass, "flame", "3DO/Effects/Rocket/mono.sim");
    Property.set(localClass, "smoke", "3DO/Effects/Tracers/GuidedRocket/White.eff");
    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
    Property.set(localClass, "emitLen", 50.0F);
    Property.set(localClass, "emitMax", 1.0F);

    Property.set(localClass, "sound", "weapon.rocket_132");

    Property.set(localClass, "radius", 5.0F);
    Property.set(localClass, "timeLife", 60.0F);
    Property.set(localClass, "timeFire", 60.0F);
    Property.set(localClass, "force", 0.0F);

    Property.set(localClass, "power", 0.52F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.0825F);
    Property.set(localClass, "massa", 4.4F);
    Property.set(localClass, "massaEnd", 4.4F);

    Property.set(localClass, "maxDeltaAngle", 1.75F);
  }
}