package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class CannonMkIII_114mm extends CannonAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 18970.0F;

    paramGunProperties.fireMesh = null;
    paramGunProperties.fire = "Effects/Bigship/GunFire150mm/Fire.eff";
    paramGunProperties.sprite = null;
    paramGunProperties.smoke = "Effects/BigShip/GunFire150mm/Burst.eff";
    paramGunProperties.shells = null;

    paramGunProperties.sound = "weapon.Cannon100";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 16.75F;

    localBulletProperties.power = 3.0F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 170.0F;

    localBulletProperties.kalibr = 0.114F;
    localBulletProperties.massa = 24.950001F;
    localBulletProperties.speed = 746.0F;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 0.1F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 40.0F;

    localBulletProperties.kalibr = 0.114F;
    localBulletProperties.massa = 23.0F;
    localBulletProperties.speed = 746.0F;

    return 45.0F;
  }

  public Bullet createNextBullet(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong)
  {
    return new BulletAntiAirBigGermany(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong, this.explodeAtHeight);
  }
}