package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonJA_127_3rdY extends CannonLongrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 18380.0F;

    paramGunProperties.fireMesh = null;
    paramGunProperties.fire = "Effects/BigShip/GunFire150mm/Fire.eff";
    paramGunProperties.sprite = null;
    paramGunProperties.smoke = "Effects/BigShip/GunFire150mm/Burst.eff";
    paramGunProperties.shells = null;

    paramGunProperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
    paramGunProperties.emitI = 5.0F;
    paramGunProperties.emitR = 15.0F;
    paramGunProperties.emitTime = 0.3F;

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.power = 1.78F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 178.0F;

    localBulletProperties.kalibr = 0.127F;
    localBulletProperties.massa = 23.0F;
    localBulletProperties.speed = 915.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = 16843009;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 1.78F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 178.0F;

    localBulletProperties.kalibr = 0.127F;
    localBulletProperties.massa = 23.0F;
    localBulletProperties.speed = 915.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = 16843009;

    return 50.0F;
  }
}