package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonStuk40 extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.075F;
    localBulletProperties.massa = 6.8F;
    localBulletProperties.speed = 770.0F;

    paramGunProperties.sound = "weapon.Cannon75t";

    return 48.0F;
  }
}