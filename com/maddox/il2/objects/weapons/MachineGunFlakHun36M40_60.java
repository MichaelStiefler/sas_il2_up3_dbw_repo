package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class MachineGunFlakHun36M40_60 extends MGunAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 2300.0F;
    paramGunProperties.shotFreq = 2.0F;
    paramGunProperties.bulletsCluster = 1;
    paramGunProperties.sound = "weapon.zenitka_37";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 10.5F;
    localBulletProperties.addExplTime = 1.5F;

    localBulletProperties.power = 0.37F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 85.0F;

    localBulletProperties.kalibr = 0.04F;
    localBulletProperties.massa = 0.95F;
    localBulletProperties.speed = 800.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmCyan/mono.sim";
    localBulletProperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localBulletProperties.traceColor = -754974976;

    localBulletProperties.traceColor = 0;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 5.0F;

    localBulletProperties.power = 0.0F;

    localBulletProperties.kalibr = 0.04F;
    localBulletProperties.massa = 0.95F;
    localBulletProperties.speed = 850.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmCyan/mono.sim";
    localBulletProperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localBulletProperties.traceColor = -754974976;

    return 60.0F;
  }

  public Bullet createNextBullet(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    return new BulletAntiAirSmallGermany(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong, this.explAddTimeT);
  }
}