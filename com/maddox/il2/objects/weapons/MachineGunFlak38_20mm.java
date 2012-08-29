package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class MachineGunFlak38_20mm extends MGunAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 2450.0F;
    paramGunProperties.shotFreq = 8.0F;
    paramGunProperties.bulletsCluster = 3;
    paramGunProperties.sound = "weapon.zenitka_20";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 4.45F;
    localBulletProperties.addExplTime = 0.15F;

    localBulletProperties.power = 0.055F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 50.0F;

    localBulletProperties.kalibr = 0.02F;
    localBulletProperties.massa = 0.148F;
    localBulletProperties.speed = 830.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmCyan/mono.sim";
    localBulletProperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localBulletProperties.traceColor = -754974976;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 3.0F;

    localBulletProperties.power = 0.0F;

    localBulletProperties.kalibr = 0.02F;
    localBulletProperties.massa = 0.148F;
    localBulletProperties.speed = 900.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmCyan/mono.sim";
    localBulletProperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localBulletProperties.traceColor = -754974976;

    return 115.0F;
  }

  public Bullet createNextBullet(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong)
  {
    return new BulletAntiAirSmallGermany(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong, this.explAddTimeT);
  }
}