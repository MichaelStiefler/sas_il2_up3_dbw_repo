package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class MachineGunFlakC30x4 extends MGunAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.traceFreq = 1;
    paramGunProperties.aimMaxDist = 2450.0F;
    paramGunProperties.shotFreq = 18.666666F;
    paramGunProperties.bulletsCluster = 4;
    paramGunProperties.sound = "weapon.zenitka_20";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 4.45F;
    localBulletProperties.addExplTime = 0.15F;

    localBulletProperties.power = 0.055F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 50.0F;

    localBulletProperties.kalibr = 0.02F;
    localBulletProperties.massa = 0.148F;
    localBulletProperties.speed = 800.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
    localBulletProperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localBulletProperties.traceColor = -755040256;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 3.0F;

    localBulletProperties.power = 0.0F;

    localBulletProperties.kalibr = 0.02F;
    localBulletProperties.massa = 0.134F;
    localBulletProperties.speed = 835.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
    localBulletProperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localBulletProperties.traceColor = -755040256;

    return 65.0F;
  }

  public Bullet createNextBullet(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    return new BulletAntiAirSmallGermany(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong, this.explAddTimeT);
  }
}