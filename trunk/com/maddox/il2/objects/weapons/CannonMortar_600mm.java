package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonMortar_600mm extends CannonLongrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 6640.0F;

    paramGunProperties.fireMesh = null;
    paramGunProperties.fire = "Effects/BigShip/GunFire350mm/Fire.eff";
    paramGunProperties.sprite = null;
    paramGunProperties.smoke = "Effects/BigShip/GunFire350mm/Burst.eff";
    paramGunProperties.shells = null;

    paramGunProperties.emitColor = new Color3f(0.8F, 0.2F, 0.0F);
    paramGunProperties.emitI = 4.0F;
    paramGunProperties.emitR = 32.0F;
    paramGunProperties.emitTime = 0.6F;

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.power = 280.0F;
    localBulletProperties.powerType = 2;
    localBulletProperties.powerRadius = 280.0F;

    localBulletProperties.kalibr = 0.6F;
    localBulletProperties.massa = 2170.0F;
    localBulletProperties.speed = 220.0F;

    localBulletProperties.traceMesh = "3do/Arms/600mmShell/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = 16843009;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 220.0F;
    localBulletProperties.powerType = 2;
    localBulletProperties.powerRadius = 220.0F;

    localBulletProperties.kalibr = 0.6F;
    localBulletProperties.massa = 1700.0F;
    localBulletProperties.speed = 283.0F;

    localBulletProperties.traceMesh = "3do/Arms/600mmShell/mono.sim";
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = 16843009;

    return 8.45F;
  }
}