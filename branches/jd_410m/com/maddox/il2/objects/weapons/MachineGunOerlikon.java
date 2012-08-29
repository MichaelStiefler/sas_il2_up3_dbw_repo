package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MachineGunOerlikon extends MGunTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 2300.0F;
    paramGunProperties.shotFreq = 8.666667F;

    paramGunProperties.sound = "weapon.mgun_tank_20";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.timeLife = 7.0F;
    localBulletProperties.kalibr = 0.02F;
    localBulletProperties.massa = 0.124F;
    localBulletProperties.speed = 570.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
    localBulletProperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localBulletProperties.traceColor = -755040256;

    return 115.0F;
  }
}