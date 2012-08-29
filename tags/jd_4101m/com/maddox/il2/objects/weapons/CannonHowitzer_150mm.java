package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonHowitzer_150mm extends CannonMidrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 6000.0F;

    paramGunProperties.sound = "weapon.Cannon100";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.power = 0.0F;

    localBulletProperties.timeLife = 8.5F;
    localBulletProperties.kalibr = 0.15F;
    localBulletProperties.massa = 48.599998F;
    localBulletProperties.speed = 500.0F;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 6.0F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 170.0F;

    localBulletProperties.timeLife = 8.5F;
    localBulletProperties.kalibr = 0.15F;
    localBulletProperties.massa = 43.5F;
    localBulletProperties.speed = 520.0F;

    return 29.5F;
  }
}