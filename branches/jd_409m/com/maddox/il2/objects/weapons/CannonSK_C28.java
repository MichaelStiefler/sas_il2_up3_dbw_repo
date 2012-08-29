package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonSK_C28 extends CannonLongrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 23000.0F;

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

    localBulletProperties.power = 0.885F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 160.0F;

    localBulletProperties.kalibr = 0.15F;
    localBulletProperties.massa = 45.299999F;
    localBulletProperties.speed = 875.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = 16843009;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 3.89F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 180.0F;

    localBulletProperties.kalibr = 0.15F;
    localBulletProperties.massa = 45.299999F;
    localBulletProperties.speed = 875.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localBulletProperties.traceColor = 16843009;

    return 54.860001F;
  }
}