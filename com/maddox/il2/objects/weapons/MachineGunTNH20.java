package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MachineGunTNH20 extends MGunTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.shotFreq = 13.333333F;
    paramGunProperties.bulletsCluster = 2;
    paramGunProperties.traceFreq = 2;

    paramGunProperties.sound = "weapon.mgun_tank_20x2";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.02F;
    localBulletProperties.massa = 0.096F;
    localBulletProperties.speed = 817.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = -654299393;

    return 1.4F / localBulletProperties.kalibr;
  }
}