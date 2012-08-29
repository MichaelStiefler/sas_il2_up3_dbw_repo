package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class CannonAntiAir85 extends CannonAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 11000.0F;

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 20.0F;

    localBulletProperties.power = 0.74F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 170.0F;

    localBulletProperties.kalibr = 0.085F;
    localBulletProperties.massa = 9.54F;
    localBulletProperties.speed = 800.0F;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 6.0F;

    localBulletProperties.power = 0.1F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 30.0F;

    localBulletProperties.kalibr = 0.085F;
    localBulletProperties.massa = 9.2F;
    localBulletProperties.speed = 880.0F;

    return 56.0F;
  }

  public Bullet createNextBullet(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    return new BulletAntiAirBigUSSR(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong, this.explodeAtHeight);
  }
}