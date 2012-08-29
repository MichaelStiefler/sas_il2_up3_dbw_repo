package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public abstract class CannonTankGeneric extends CannonMidrangeGeneric
  implements BulletAimer
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.weaponType = 4;

    localGunProperties.bCannon = true;
    localGunProperties.bUseHookAsRel = false;

    localGunProperties.fireMesh = null;
    localGunProperties.fire = "3DO/Effects/GunFire/88mm/CannonTank.eff";
    localGunProperties.sprite = null;
    localGunProperties.smoke = "effects/smokes/CannonTank.eff";
    localGunProperties.shells = null;

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.6F);
    localGunProperties.emitI = 5.0F;
    localGunProperties.emitR = 4.0F;
    localGunProperties.emitTime = 0.3F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 3000.0F;

    localGunProperties.shotFreq = 999.0F;

    localGunProperties.traceFreq = 1;

    localGunProperties.bullets = -1;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties() };

    for (int i = 0; i < localGunProperties.bullet.length; i++) {
      localGunProperties.bullet[i].massa = 0.001F;
      localGunProperties.bullet[i].kalibr = 9.0F;
      localGunProperties.bullet[i].speed = 10.0F;

      localGunProperties.bullet[i].power = 0.0F;
      localGunProperties.bullet[i].powerType = 0;
      localGunProperties.bullet[i].powerRadius = 0.0F;
      localGunProperties.bullet[i].timeLife = 5.0F;

      localGunProperties.bullet[i].traceMesh = null;
      localGunProperties.bullet[i].traceTrail = "effects/Smokes/ShellTrail.eff";
      localGunProperties.bullet[i].traceColor = 16843009;
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