package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class Cannon34K extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.0762F;
    localBulletProperties.massa = 6.5F;
    localBulletProperties.speed = 816.0F;

    paramGunProperties.sound = "weapon.Cannon75t";

    return 55.0F;
  }
}