package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonUS_3_50Mk10 extends CannonLongrangeGeneric
{
  protected float Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.aimMaxDist = 13350.0F;

    paramGunProperties.sound = "weapon.Cannon85t";

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];

    localBulletProperties.power = 0.58F;
    localBulletProperties.powerType = 0;
    localBulletProperties.powerRadius = 70.0F;

    localBulletProperties.kalibr = 0.0762F;
    localBulletProperties.massa = 5.9F;
    localBulletProperties.speed = 823.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = 16843009;

    localBulletProperties = paramGunProperties.bullet[1];

    localBulletProperties.power = 0.0F;

    localBulletProperties.kalibr = 0.0762F;
    localBulletProperties.massa = 5.8F;
    localBulletProperties.speed = 823.0F;

    localBulletProperties.traceMesh = null;
    localBulletProperties.traceTrail = null;
    localBulletProperties.traceColor = 16843009;

    return 50.0F;
  }
}