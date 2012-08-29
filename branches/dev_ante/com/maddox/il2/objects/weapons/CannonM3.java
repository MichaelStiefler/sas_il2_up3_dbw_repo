package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonM3 extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.075F;
    localBulletProperties.massa = 6.32F;
    localBulletProperties.speed = 619.0F;

    paramGunProperties.sound = "weapon.Cannon75t";

    return 37.5F;
  }
}