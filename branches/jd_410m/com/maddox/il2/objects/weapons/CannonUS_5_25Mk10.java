package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

public class CannonUS_5_25Mk10 extends CannonAntiAirGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 13259.0F;

    paramGunProperties.fireMesh = null;
    paramGunProperties.fire = "Effects/Bigship/GunFire150mm/Fire.eff";
    paramGunProperties.sprite = null;
    paramGunProperties.smoke = "Effects/BigShip/GunFire150mm/Burst.eff";
    paramGunProperties.shells = null;

    paramGunProperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
    paramGunProperties.emitI = 5.0F;
    paramGunProperties.emitR = 13.0F;
    paramGunProperties.emitTime = 0.3F;

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.timeLife = 10.3F;

    localBulletProperties.power = 3.4F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 120.0F;

    localBulletProperties.kalibr = 0.127F;
    localBulletProperties.massa = 24.0F;
    localBulletProperties.speed = 808.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = 16843009;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 2.55F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 120.0F;

    localBulletProperties.kalibr = 0.127F;
    localBulletProperties.massa = 24.43F;
    localBulletProperties.speed = 808.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = 16843009;

    return 25.0F;
  }

  public Bullet createNextBullet(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    return new BulletAntiAirBigGermany(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong, this.explodeAtHeight);
  }
}