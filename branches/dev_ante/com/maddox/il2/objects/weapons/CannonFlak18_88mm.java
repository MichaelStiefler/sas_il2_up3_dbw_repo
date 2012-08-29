package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class CannonFlak18_88mm extends CannonAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 11000.0F;

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 20.0F;

    localBulletProperties.power = 0.87F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 170.0F;

    localBulletProperties.kalibr = 0.088F;
    localBulletProperties.massa = 9.0F;
    localBulletProperties.speed = 820.0F;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.timeLife = 6.0F;

    localBulletProperties.power = 0.1F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 30.0F;

    localBulletProperties.kalibr = 0.088F;
    localBulletProperties.massa = 9.5F;
    localBulletProperties.speed = 795.0F;

    return 56.0F;
  }

  public Bullet createNextBullet(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong)
  {
    return new BulletAntiAirBigGermany(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong, this.explodeAtHeight);
  }
}