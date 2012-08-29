package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonChiHa extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.057F;
    localBulletProperties.massa = 2.5F;
    localBulletProperties.speed = 350.0F;

    paramGunProperties.sound = "weapon.Cannon45t";

    return 17.0F;
  }
}