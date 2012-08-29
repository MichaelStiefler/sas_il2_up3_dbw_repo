package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class CannonAntiAirSK_C33 extends CannonAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 17700.0F;

    paramGunProperties.sound = "weapon.Cannon100";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 14.0F;

    localBulletProperties.power = 0.87F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 170.0F;

    localBulletProperties.kalibr = 0.105F;
    localBulletProperties.massa = 15.1F;
    localBulletProperties.speed = 900.0F;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 0.1F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 30.0F;

    localBulletProperties.kalibr = 0.105F;
    localBulletProperties.massa = 15.8F;
    localBulletProperties.speed = 900.0F;

    return 65.0F;
  }

  public Bullet createNextBullet(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    return new BulletAntiAirBigGermany(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong, this.explodeAtHeight);
  }
}