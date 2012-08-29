package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class MachineGunOerlikon20 extends MGunAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 4389.0F;
    paramGunProperties.shotFreq = 7.5F;
    paramGunProperties.bulletsCluster = 2;

    paramGunProperties.sound = "weapon.zenitka_20";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 3.6F;
    localBulletProperties.addExplTime = 1.5F;

    localBulletProperties.power = 0.011F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 30.0F;

    localBulletProperties.kalibr = 0.02F;
    localBulletProperties.massa = 0.123F;
    localBulletProperties.speed = 844.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = -1275133952;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 5.2F;

    localBulletProperties.power = 0.0F;

    localBulletProperties.kalibr = 0.02F;
    localBulletProperties.massa = 0.122F;
    localBulletProperties.speed = 844.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = -1275133952;

    return 70.0F;
  }

  public Bullet createNextBullet(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    return new BulletAntiAirSmallUSSR(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong, this.explAddTimeT);
  }
}