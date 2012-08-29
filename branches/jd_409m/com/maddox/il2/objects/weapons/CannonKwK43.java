package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonKwK43 extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.088F;
    localBulletProperties.massa = 10.2F;
    localBulletProperties.speed = 1000.0F;

    paramGunProperties.sound = "weapon.Cannon85t";

    return 71.599998F;
  }
}