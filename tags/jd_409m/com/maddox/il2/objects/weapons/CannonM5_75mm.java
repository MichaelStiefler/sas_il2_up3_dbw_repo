package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonM5_75mm extends CannonMidrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 4000.0F;

    paramGunProperties.sound = "weapon.Cannon75";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.power = 0.0F;

    localBulletProperties.timeLife = 6.0F;
    localBulletProperties.kalibr = 0.075F;
    localBulletProperties.massa = 6.3F;
    localBulletProperties.speed = 760.0F;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 0.65F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 110.0F;

    localBulletProperties.timeLife = 7.0F;
    localBulletProperties.kalibr = 0.0762F;
    localBulletProperties.massa = 6.2F;
    localBulletProperties.speed = 780.0F;

    return 40.0F;
  }
}