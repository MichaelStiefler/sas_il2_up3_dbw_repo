package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonB_1_P extends CannonLongrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 36000.0F;

    paramGunProperties.fireMesh = null;
    paramGunProperties.fire = "Effects/Bigship/GunFire250mm/Fire.eff";
    paramGunProperties.sprite = null;
    paramGunProperties.smoke = "Effects/BigShip/GunFire250mm/Burst.eff";
    paramGunProperties.shells = null;

    paramGunProperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
    paramGunProperties.emitI = 5.0F;
    paramGunProperties.emitR = 18.0F;
    paramGunProperties.emitTime = 0.4F;

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.power = 1.82F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 200.0F;

    localBulletProperties.kalibr = 0.18F;
    localBulletProperties.massa = 97.5F;
    localBulletProperties.speed = 920.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = 16843009;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 7.52F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 200.0F;

    localBulletProperties.kalibr = 0.18F;
    localBulletProperties.massa = 97.5F;
    localBulletProperties.speed = 920.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = 16843009;

    return 56.0F;
  }
}