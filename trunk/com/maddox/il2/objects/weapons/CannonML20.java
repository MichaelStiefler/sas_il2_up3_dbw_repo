package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonML20 extends CannonMidrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 6000.0F;

    paramGunProperties.sound = "weapon.Cannon100";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.power = 0.0F;

    localBulletProperties.timeLife = 7.0F;
    localBulletProperties.kalibr = 0.152F;
    localBulletProperties.massa = 48.799999F;
    localBulletProperties.speed = 600.0F;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 6.25F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 170.0F;

    localBulletProperties.timeLife = 7.0F;
    localBulletProperties.kalibr = 0.152F;
    localBulletProperties.massa = 43.599998F;
    localBulletProperties.speed = 630.0F;

    return 29.0F;
  }
}