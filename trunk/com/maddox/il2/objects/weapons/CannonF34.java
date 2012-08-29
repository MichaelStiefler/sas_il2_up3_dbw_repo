package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonF34 extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.0762F;
    localBulletProperties.massa = 6.3F;
    localBulletProperties.speed = 655.0F;

    paramGunProperties.sound = "weapon.Cannon75t";

    return 42.0F;
  }
}