package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonHun41M40_51 extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.04F;
    localBulletProperties.massa = 0.9F;
    localBulletProperties.speed = 800.0F;

    paramGunProperties.sound = "weapon.Cannon45t";

    return 51.0F;
  }
}