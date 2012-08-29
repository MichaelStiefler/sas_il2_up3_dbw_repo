package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonM2A1_105mm extends CannonMidrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 6000.0F;

    paramGunProperties.sound = "weapon.Cannon100";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.power = 0.0F;

    localBulletProperties.timeLife = 8.5F;
    localBulletProperties.kalibr = 0.105F;
    localBulletProperties.massa = 15.6F;
    localBulletProperties.speed = 500.0F;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 0.87F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 100.0F;

    localBulletProperties.timeLife = 8.5F;
    localBulletProperties.kalibr = 0.105F;
    localBulletProperties.massa = 15.6F;
    localBulletProperties.speed = 520.0F;

    return 25.0F;
  }
}