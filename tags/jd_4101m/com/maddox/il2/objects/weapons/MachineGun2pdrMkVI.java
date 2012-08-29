package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class MachineGun2pdrMkVI extends MGunAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 4572.0F;
    paramGunProperties.shotFreq = 15.333333F;
    paramGunProperties.bulletsCluster = 4;
    paramGunProperties.sound = "weapon.zenitka_37";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 6.6F;
    localBulletProperties.addExplTime = 1.2F;

    localBulletProperties.power = 0.071F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 70.0F;

    localBulletProperties.kalibr = 0.04F;
    localBulletProperties.massa = 0.82F;
    localBulletProperties.speed = 701.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmGreen/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = -1291780352;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 6.0F;
    localBulletProperties.addExplTime = 1.2F;

    localBulletProperties.power = 0.071F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 70.0F;

    localBulletProperties.kalibr = 0.04F;
    localBulletProperties.massa = 0.91F;
    localBulletProperties.speed = 585.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmGreen/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = -1291780352;

    return 39.0F;
  }

  public Bullet createNextBullet(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    return new BulletAntiAirSmallGermany(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong, this.explAddTimeT);
  }
}