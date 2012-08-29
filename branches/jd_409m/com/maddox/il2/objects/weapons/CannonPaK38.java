package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonPaK38 extends CannonMidrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 3000.0F;

    paramGunProperties.sound = "weapon.Cannon45";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.power = 0.0F;

    localBulletProperties.timeLife = 7.0F;
    localBulletProperties.kalibr = 0.05F;
    localBulletProperties.massa = 2.05F;
    localBulletProperties.speed = 835.0F;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 0.21F;
    localBulletProperties.powerType = 1;
    localBulletProperties.powerRadius = 90.0F;

    localBulletProperties.timeLife = 6.0F;
    localBulletProperties.kalibr = 0.05F;
    localBulletProperties.massa = 1.81F;
    localBulletProperties.speed = 550.0F;

    return 60.0F;
  }
}