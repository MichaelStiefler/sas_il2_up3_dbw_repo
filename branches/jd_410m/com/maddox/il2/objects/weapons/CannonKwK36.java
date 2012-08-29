package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonKwK36 extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.088F;
    localBulletProperties.massa = 9.6F;
    localBulletProperties.speed = 810.0F;

    paramGunProperties.sound = "weapon.Cannon85t";

    return 56.0F;
  }
}