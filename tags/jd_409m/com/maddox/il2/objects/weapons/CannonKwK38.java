package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonKwK38 extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.05F;
    localBulletProperties.massa = 2.05F;
    localBulletProperties.speed = 685.0F;

    paramGunProperties.sound = "weapon.Cannon45t";

    return 42.0F;
  }
}