package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MachineGunUS_28_75Mk1 extends MGunAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 6767.0F;
    paramGunProperties.shotFreq = 10.0F;
    paramGunProperties.bulletsCluster = 3;

    paramGunProperties.sound = "weapon.zenitka_20";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 7.0F;
    localBulletProperties.addExplTime = 1.5F;

    localBulletProperties.power = 0.017F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 32.0F;

    localBulletProperties.kalibr = 0.028F;
    localBulletProperties.massa = 0.416F;
    localBulletProperties.speed = 823.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmMagenta/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = -1275133697;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 0.015F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 30.0F;

    localBulletProperties.kalibr = 0.028F;
    localBulletProperties.massa = 0.416F;
    localBulletProperties.speed = 823.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmMagenta/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = -1275133697;

    return 75.0F;
  }
}