package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonHun43M105_20 extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.105F;
    localBulletProperties.massa = 17.0F;
    localBulletProperties.speed = 560.0F;

    paramGunProperties.sound = "weapon.Cannon100t";

    return 20.5F;
  }
}