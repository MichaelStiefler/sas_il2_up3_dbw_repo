package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class Cannon20K extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.045F;
    localBulletProperties.massa = 1.425F;
    localBulletProperties.speed = 760.0F;

    paramGunProperties.sound = "weapon.Cannon45t";

    return 46.0F;
  }
}