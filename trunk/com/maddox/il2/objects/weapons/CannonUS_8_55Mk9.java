package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonUS_8_55Mk9 extends CannonLongrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 29131.0F;

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

    localBulletProperties.power = 2.1F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 210.0F;

    localBulletProperties.kalibr = 0.203F;
    localBulletProperties.massa = 118.0F;
    localBulletProperties.speed = 853.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = 16843009;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 9.7F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 210.0F;

    localBulletProperties.kalibr = 0.203F;
    localBulletProperties.massa = 118.0F;
    localBulletProperties.speed = 914.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = 16843009;

    return 55.0F;
  }
}