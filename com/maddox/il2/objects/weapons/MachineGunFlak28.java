package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class MachineGunFlak28 extends MGunAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 9600.0F;
    paramGunProperties.shotFreq = 2.0F;
    paramGunProperties.bulletsCluster = 1;
    paramGunProperties.sound = "weapon.zenitka_37";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 10.5F;
    localBulletProperties.addExplTime = 1.5F;

    localBulletProperties.power = 0.303F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 85.0F;

    localBulletProperties.kalibr = 0.04F;
    localBulletProperties.massa = 0.955F;
    localBulletProperties.speed = 854.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmCyan/mono.sim";
    localBulletProperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localBulletProperties.traceColor = -754974976;

    localBulletProperties.traceColor = 0;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 12.0F;

    localBulletProperties.power = 0.0F;

    localBulletProperties.kalibr = 0.04F;
    localBulletProperties.massa = 0.955F;
    localBulletProperties.speed = 854.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmCyan/mono.sim";
    localBulletProperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localBulletProperties.traceColor = -754974976;

    return 56.0F;
  }

  public Bullet createNextBullet(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong)
  {
    return new BulletAntiAirSmallGermany(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong, this.explAddTimeT);
  }
}