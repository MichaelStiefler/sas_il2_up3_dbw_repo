package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonMarkVI_254mm extends CannonLongrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 13530.0F;

    paramGunProperties.fireMesh = null;
    paramGunProperties.fire = "Effects/Bigship/GunFire250mm/Fire.eff";
    paramGunProperties.sprite = null;
    paramGunProperties.smoke = "Effects/BigShip/GunFire250mm/Burst.eff";
    paramGunProperties.shells = null;

    paramGunProperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
    paramGunProperties.emitI = 5.0F;
    paramGunProperties.emitR = 25.4F;
    paramGunProperties.emitTime = 0.4F;

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.power = 3.89F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 200.0F;

    localBulletProperties.kalibr = 0.254F;
    localBulletProperties.massa = 227.0F;
    localBulletProperties.speed = 810.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = 16843009;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 28.299999F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 200.0F;

    localBulletProperties.kalibr = 0.254F;
    localBulletProperties.massa = 227.0F;
    localBulletProperties.speed = 810.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = 16843009;

    return 46.759998F;
  }
}