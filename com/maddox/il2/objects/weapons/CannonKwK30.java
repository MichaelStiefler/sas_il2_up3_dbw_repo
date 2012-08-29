package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonKwK30 extends MGunTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.shotFreq = 6.666667F;

    paramGunProperties.sound = "weapon.mgun_tank_20";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.02F;
    localBulletProperties.massa = 0.148F;
    localBulletProperties.speed = 801.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmGreenBlue/mono.sim";
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = -728447882;

    return 97.099998F;
  }
}