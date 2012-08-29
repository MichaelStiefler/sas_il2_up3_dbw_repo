package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class Cannon52K extends CannonTankGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.kalibr = 0.085F;
    localBulletProperties.massa = 9.2F;
    localBulletProperties.speed = 800.0F;

    paramGunProperties.sound = "weapon.Cannon85t";

    return 52.0F;
  }
}