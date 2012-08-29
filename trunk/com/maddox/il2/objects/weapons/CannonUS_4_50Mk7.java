package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonUS_4_50Mk7 extends CannonLongrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 14560.0F;

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.power = 1.88F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 168.0F;

    localBulletProperties.kalibr = 0.102F;
    localBulletProperties.massa = 17.5F;
    localBulletProperties.speed = 884.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = -16711681;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 2.1F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 180.0F;

    localBulletProperties.kalibr = 0.102F;
    localBulletProperties.massa = 17.5F;
    localBulletProperties.speed = 884.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = -16744193;

    return 50.0F;
  }
}