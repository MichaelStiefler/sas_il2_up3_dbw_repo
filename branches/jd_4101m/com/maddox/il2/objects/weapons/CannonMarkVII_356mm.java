package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonMarkVII_356mm extends CannonLongrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 46630.0F;

    paramGunProperties.fireMesh = null;
    paramGunProperties.fire = "Effects/BigShip/GunFire350mm/Fire.eff";
    paramGunProperties.sprite = null;
    paramGunProperties.smoke = "Effects/BigShip/GunFire350mm/Burst.eff";
    paramGunProperties.shells = null;

    paramGunProperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
    paramGunProperties.emitI = 5.0F;
    paramGunProperties.emitR = 38.0F;
    paramGunProperties.emitTime = 0.6F;

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.power = 20.379999F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 200.0F;

    localBulletProperties.kalibr = 0.356F;
    localBulletProperties.massa = 747.79999F;
    localBulletProperties.speed = 732.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = 16843009;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 75.849998F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 220.0F;

    localBulletProperties.kalibr = 0.356F;
    localBulletProperties.massa = 747.79999F;
    localBulletProperties.speed = 732.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = 16843009;

    return 45.0F;
  }
}