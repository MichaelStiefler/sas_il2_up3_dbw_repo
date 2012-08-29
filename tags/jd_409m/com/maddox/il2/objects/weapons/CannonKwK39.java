package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonKwK39 extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.05F;
    localBulletProperties.massa = 2.05F;
    localBulletProperties.speed = 835.0F;

    paramGunProperties.sound = "weapon.Cannon45t";

    return 60.0F;
  }
}