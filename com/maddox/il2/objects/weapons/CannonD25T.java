package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonD25T extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.122F;
    localBulletProperties.massa = 25.0F;
    localBulletProperties.speed = 781.0F;

    paramGunProperties.sound = "weapon.Cannon100t";

    return 48.0F;
  }
}