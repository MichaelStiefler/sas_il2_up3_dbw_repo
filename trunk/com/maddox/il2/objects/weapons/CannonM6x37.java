package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonM6x37 extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.037F;
    localBulletProperties.massa = 0.87F;
    localBulletProperties.speed = 884.0F;

    paramGunProperties.sound = "weapon.Cannon45t";

    return 53.5F;
  }
}