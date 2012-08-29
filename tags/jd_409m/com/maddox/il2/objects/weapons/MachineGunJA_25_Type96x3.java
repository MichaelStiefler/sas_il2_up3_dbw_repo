package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class MachineGunJA_25_Type96x3 extends MGunAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 7500.0F;
    paramGunProperties.shotFreq = 13.0F;
    paramGunProperties.bulletsCluster = 4;

    paramGunProperties.sound = "weapon.zenitka_20";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 6.1F;
    localBulletProperties.addExplTime = 1.5F;

    localBulletProperties.power = 0.01F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 30.0F;

    localBulletProperties.kalibr = 0.025F;
    localBulletProperties.massa = 0.24F;
    localBulletProperties.speed = 900.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = -1291780097;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 8.3F;

    localBulletProperties.power = 0.0F;

    localBulletProperties.kalibr = 0.025F;
    localBulletProperties.massa = 0.26F;
    localBulletProperties.speed = 900.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = -1291780097;

    return 60.0F;
  }

  public Bullet createNextBullet(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong)
  {
    return new BulletAntiAirSmallUSSR(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong, this.explAddTimeT);
  }
}