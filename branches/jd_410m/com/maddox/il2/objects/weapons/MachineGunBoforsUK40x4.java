package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class MachineGunBoforsUK40x4 extends MGunAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 9830.0F;
    paramGunProperties.shotFreq = 8.0F;
    paramGunProperties.bulletsCluster = 4;
    paramGunProperties.sound = "weapon.zenitka_37";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 7.7F;
    localBulletProperties.addExplTime = 1.5F;

    localBulletProperties.power = 0.092F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 90.0F;

    localBulletProperties.kalibr = 0.04F;
    localBulletProperties.massa = 0.894F;
    localBulletProperties.speed = 829.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = -1291845377;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 7.7F;

    localBulletProperties.power = 0.092F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 90.0F;

    localBulletProperties.kalibr = 0.04F;
    localBulletProperties.massa = 0.894F;
    localBulletProperties.speed = 829.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = -1291845377;

    return 56.299999F;
  }

  public Bullet createNextBullet(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    return new BulletAntiAirSmallGermany(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong, this.explAddTimeT);
  }
}