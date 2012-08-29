package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Orient;

public class CannonRocketSimpleRS150 extends CannonRocketSimple
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
    RocketSimpleRS150 localRocketSimpleRS150 = new RocketSimpleRS150(paramPoint3d, paramOrient, paramActor);
    localRocketSimpleRS150.start(paramFloat);
  }
}