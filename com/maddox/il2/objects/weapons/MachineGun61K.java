package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class MachineGun61K extends MGunAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 4000.0F;
    paramGunProperties.shotFreq = 2.75F;
    paramGunProperties.bulletsCluster = 2;

    paramGunProperties.sound = "weapon.zenitka_37";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 10.5F;
    localBulletProperties.addExplTime = 1.5F;

    localBulletProperties.power = 0.35F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 80.0F;

    localBulletProperties.kalibr = 0.037F;
    localBulletProperties.massa = 0.732F;
    localBulletProperties.speed = 880.0F;

    localBulletProperties.traceColor = 0;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 5.0F;

    localBulletProperties.power = 0.0F;

    localBulletProperties.kalibr = 0.037F;
    localBulletProperties.massa = 0.758F;
    localBulletProperties.speed = 880.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = -770532113;

    return 62.599998F;
  }

  public Bullet createNextBullet(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong)
  {
    return new BulletAntiAirSmallUSSR(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong, this.explAddTimeT);
  }
}