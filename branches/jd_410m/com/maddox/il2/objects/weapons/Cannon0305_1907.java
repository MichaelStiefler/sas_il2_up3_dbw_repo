package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class Cannon0305_1907 extends CannonLongrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 24620.0F;

    paramGunProperties.fireMesh = null;
    paramGunProperties.fire = "Effects/Bigship/GunFire350mm/Fire.eff";
    paramGunProperties.sprite = null;
    paramGunProperties.smoke = "Effects/BigShip/GunFire350mm/Burst.eff";
    paramGunProperties.shells = null;

    paramGunProperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
    paramGunProperties.emitI = 5.0F;
    paramGunProperties.emitR = 30.5F;
    paramGunProperties.emitTime = 0.6F;

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.power = 12.96F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 200.0F;

    localBulletProperties.kalibr = 0.305F;
    localBulletProperties.massa = 470.89999F;
    localBulletProperties.speed = 762.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = 16843009;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 61.5F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 200.0F;

    localBulletProperties.kalibr = 0.305F;
    localBulletProperties.massa = 470.89999F;
    localBulletProperties.speed = 762.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = 16843009;

    return 52.0F;
  }
}