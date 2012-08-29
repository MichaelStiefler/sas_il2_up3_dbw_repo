package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class MachineGunFlak25mm_JA extends MGunAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 2300.0F;
    paramGunProperties.shotFreq = 7.333334F;
    paramGunProperties.bulletsCluster = 2;
    paramGunProperties.sound = "weapon.zenitka_37";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 4.45F;
    localBulletProperties.addExplTime = 0.15F;

    localBulletProperties.power = 0.1F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 55.0F;

    localBulletProperties.kalibr = 0.025F;
    localBulletProperties.massa = 0.24F;
    localBulletProperties.speed = 900.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localBulletProperties.traceTrail = "3DO/Effects/Tracers/TrailThin.eff";
    localBulletProperties.traceColor = -654299393;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 3.2F;

    localBulletProperties.power = 0.0F;

    localBulletProperties.kalibr = 0.025F;
    localBulletProperties.massa = 0.26F;
    localBulletProperties.speed = 875.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localBulletProperties.traceTrail = "3DO/Effects/Tracers/TrailThin.eff";
    localBulletProperties.traceColor = -654299393;

    return 54.0F;
  }

  public Bullet createNextBullet(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    return new BulletAntiAirSmallUSSR(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong, this.explAddTimeT);
  }
}