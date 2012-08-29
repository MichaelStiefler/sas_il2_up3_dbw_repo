package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonBofors37mm extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.037F;
    localBulletProperties.massa = 0.758F;
    localBulletProperties.speed = 880.0F;

    paramGunProperties.sound = "weapon.Cannon45t";

    return 46.0F;
  }
}