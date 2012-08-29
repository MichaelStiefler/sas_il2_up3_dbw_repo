package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class MachineGun00762_1914 extends CannonAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 9500.0F;
    paramGunProperties.shotFreq = 0.5F;
    paramGunProperties.bulletsCluster = 1;

    paramGunProperties.sound = "weapon.zenitka_85";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 22.0F;

    localBulletProperties.power = 0.35F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 120.0F;

    localBulletProperties.kalibr = 0.0762F;
    localBulletProperties.massa = 6.5F;
    localBulletProperties.speed = 588.0F;

    localBulletProperties.traceColor = 0;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 15.0F;

    localBulletProperties.power = 0.0F;

    localBulletProperties.kalibr = 0.0762F;
    localBulletProperties.massa = 6.8F;
    localBulletProperties.speed = 588.0F;

    localBulletProperties.traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = -770532113;

    return 30.0F;
  }

  public Bullet createNextBullet(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong)
  {
    return new BulletAntiAirBigUSSR(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong, this.explodeAtHeight);
  }
}