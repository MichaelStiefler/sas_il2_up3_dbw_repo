package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Orient;

public class CannonRocketSimpleRS132 extends CannonRocketSimple
{
  protected void Specify(GunProperties paramGunProperties)
  {
    paramGunProperties.shotFreq = 0.33F;

    paramGunProperties.aimMinDist = 20.0F;
    paramGunProperties.aimMaxDist = 8000.0F;

    BulletProperties localBulletProperties = paramGunProperties.bullet[0];
    localBulletProperties.speed = 400.0F;
  }

  public void launch(Point3d paramPoint3d, Orient paramOrient, float paramFloat, Actor paramActor) {
    RocketSimpleRS132 localRocketSimpleRS132 = new RocketSimpleRS132(paramPoint3d, paramOrient, paramActor);
    localRocketSimpleRS132.start(paramFloat);
  }
}