package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public abstract class MGunTankGeneric extends CannonMidrangeGeneric
  implements BulletAimer
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.weaponType = 2;

    localGunProperties.bCannon = false;
    localGunProperties.bUseHookAsRel = false;

    localGunProperties.fireMesh = "3DO/Effects/GunFire/20mm/mono.sim";
    localGunProperties.fire = null;
    localGunProperties.sprite = "3DO/Effects/GunFire/20mm/GunFlare.eff";
    localGunProperties.smoke = "effects/smokes/MachineGun.eff";
    localGunProperties.shells = null;

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.5F);
    localGunProperties.emitI = 5.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.1F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 3200.0F;

    localGunProperties.shotFreq = 1.0F;
    localGunProperties.traceFreq = 2;

    localGunProperties.bullets = -1;
    localGunProperties.bulletsCluster = 3;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties() };

    localGunProperties.bullet[0].traceMesh = null;
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = 16843009;

    localGunProperties.bullet[0].massa = 0.001F;
    localGunProperties.bullet[0].kalibr = 9.0F;
    localGunProperties.bullet[0].speed = 10.0F;

    localGunProperties.bullet[0].power = 0.0F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].timeLife = 3.0F;

    float f1 = Specify(localGunProperties);

    CannonMidrangeGeneric.autocomputeSplintersRadiuses(localGunProperties.bullet);

    for (int i = 0; i < localGunProperties.bullet.length; i++) {
      float f2 = localGunProperties.aimMaxDist / (localGunProperties.bullet[i].speed * 0.707F);
      localGunProperties.bullet[i].timeLife = (f2 * 2.0F);
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