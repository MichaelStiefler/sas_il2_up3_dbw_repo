package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public abstract class CannonLongrangeGeneric extends CannonMidrangeGeneric
  implements BulletAimer
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.sound = "weapon.Cannon100";

    localGunProperties.aimMinDist = 20.0F;
    localGunProperties.aimMaxDist = 25000.0F;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties() };

    for (int i = 0; i < localGunProperties.bullet.length; i++) {
      localGunProperties.bullet[i].massa = 0.001F;
      localGunProperties.bullet[i].kalibr = 9.0F;
      localGunProperties.bullet[i].speed = 10.0F;

      localGunProperties.bullet[i].power = 0.0F;
      if (i == 0)
        localGunProperties.bullet[i].powerType = 1;
      else {
        localGunProperties.bullet[i].powerType = 0;
      }
      localGunProperties.bullet[i].powerRadius = 140.0F;
      localGunProperties.bullet[i].timeLife = 60.0F;

      localGunProperties.bullet[i].traceMesh = null;
      localGunProperties.bullet[i].traceTrail = null;
      localGunProperties.bullet[i].traceColor = 0;
    }

    float f1 = Specify(localGunProperties);

    CannonMidrangeGeneric.autocomputeSplintersRadiuses(localGunProperties.bullet);

    for (int j = 0; j < localGunProperties.bullet.length; j++) {
      float f2 = localGunProperties.aimMaxDist / (localGunProperties.bullet[j].speed * 0.707F);
      localGunProperties.bullet[j].timeLife = (f2 * 2.0F);
    }

    if (f1 > 0.0F) {
      if (f1 <= 20.0F) f1 = 20.0F;
      if (f1 >= 70.0F) f1 = 70.0F;
      f1 = (f1 - 20.0F) / 50.0F;
      localGunProperties.maxDeltaAngle = (0.3F - f1 * 0.2F);
    } else {
      localGunProperties.maxDeltaAngle = 0.2F;
    }

    return localGunProperties;
  }

  protected abstract float Specify(GunProperties paramGunProperties);
}