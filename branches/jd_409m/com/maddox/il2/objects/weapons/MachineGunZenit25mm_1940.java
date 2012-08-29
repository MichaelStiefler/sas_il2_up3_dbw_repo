package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class MachineGunZenit25mm_1940 extends MGunAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 2300.0F;
    paramGunProperties.shotFreq = 4.0F;
    paramGunProperties.bulletsCluster = 2;
    paramGunProperties.sound = "weapon.zenitka_37";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 4.45F;
    localBulletProperties.addExplTime = 0.15F;

    localBulletProperties.power = 0.1F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 55.0F;

    localBulletProperties.kalibr = 0.025F;
    localBulletProperties.massa = 0.288F;
    localBulletProperties.speed = 910.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localBulletProperties.traceTrail = "3DO/Effects/Tracers/TrailThin.eff";
    localBulletProperties.traceColor = -654299393;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 3.0F;

    localBulletProperties.power = 0.0F;

    localBulletProperties.kalibr = 0.025F;
    localBulletProperties.massa = 0.295F;
    localBulletProperties.speed = 900.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localBulletProperties.traceTrail = "3DO/Effects/Tracers/TrailThin.eff";
    localBulletProperties.traceColor = -654299393;

    return 76.599998F;
  }

  public Bullet createNextBullet(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong)
  {
    return new BulletAntiAirSmallUSSR(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong, this.explAddTimeT);
  }
}