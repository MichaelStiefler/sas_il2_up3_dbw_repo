package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MachineGunM2HB extends MGunTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.shotFreq = 8.333333F;
    paramGunProperties.bulletsCluster = 2;

    paramGunProperties.sound = "weapon.mgun_tank_13";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.0127F;
    localBulletProperties.massa = 0.0485F;
    localBulletProperties.speed = 870.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmWhite/mono.sim";
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = -754974721;

    return 0.8F / localBulletProperties.kalibr;
  }
}