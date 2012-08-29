package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Orient;

public class CannonRocketSimpleRS82 extends CannonRocketSimple
{
  protected void Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.shotFreq = 0.5F;

    paramGunProperties.aimMinDist = 20.0F;
    paramGunProperties.aimMaxDist = 8000.0F;

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.speed = 480.0F;
  }

  public void launch(Point3d paramPoint3d, Orient paramOrient, float paramFloat, Actor paramActor) {
    RocketSimpleRS82 localRocketSimpleRS82 = new RocketSimpleRS82(paramPoint3d, paramOrient, paramActor);
    localRocketSimpleRS82.start(paramFloat);
  }
}