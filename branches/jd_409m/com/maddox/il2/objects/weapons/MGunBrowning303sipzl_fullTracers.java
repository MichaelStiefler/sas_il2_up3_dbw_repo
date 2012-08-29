package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunBrowning303sipzl_fullTracers extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bCannon = false;
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.fireMesh = "3DO/Effects/GunFire/7mm/mono.sim";
    localGunProperties.fire = null;
    localGunProperties.sprite = "3DO/Effects/GunFire/7mm/GunFlare.eff";
    localGunProperties.smoke = null;
    localGunProperties.shells = null;
    localGunProperties.sound = "weapon.mgun_07_900";
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 0.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = 1;
    localGunProperties.maxDeltaAngle = 0.32F;
    localGunProperties.shotFreq = 15.0F;
    localGunProperties.traceFreq = 0;
    localGunProperties.bullets = 500;
    localGunProperties.bulletsCluster = 2;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.01066849F;
    localGunProperties.bullet[0].kalibr = 4.442132E-005F;
    localGunProperties.bullet[0].speed = 835.0F;
    localGunProperties.bullet[0].power = 0.0018F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[0].traceTrail = null;
    localGunProperties.bullet[0].traceColor = -654311169;
    localGunProperties.bullet[0].timeLife = 2.5F;

    localGunProperties.bullet[1].massa = 0.01066849F;
    localGunProperties.bullet[1].kalibr = 4.442132E-005F;
    localGunProperties.bullet[1].speed = 835.0F;
    localGunProperties.bullet[1].power = 0.0F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.0F;
    localGunProperties.bullet[1].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = -654311169;
    localGunProperties.bullet[1].timeLife = 2.5F;

    localGunProperties.bullet[2].massa = 0.01066849F;
    localGunProperties.bullet[2].kalibr = 4.442132E-005F;
    localGunProperties.bullet[2].speed = 835.0F;
    localGunProperties.bullet[2].power = 0.0F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 0.0F;
    localGunProperties.bullet[2].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[2].traceTrail = null;
    localGunProperties.bullet[2].traceColor = -654311169;
    localGunProperties.bullet[2].timeLife = 2.5F;

    localGunProperties.bullet[3].massa = 0.01066849F;
    localGunProperties.bullet[3].kalibr = 4.442132E-005F;
    localGunProperties.bullet[3].speed = 835.0F;
    localGunProperties.bullet[3].power = 0.0018F;
    localGunProperties.bullet[3].powerType = 0;
    localGunProperties.bullet[3].powerRadius = 0.0F;
    localGunProperties.bullet[3].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[3].traceTrail = null;
    localGunProperties.bullet[3].traceColor = -654311169;
    localGunProperties.bullet[3].timeLife = 2.5F;

    localGunProperties.bullet[4].massa = 0.01066849F;
    localGunProperties.bullet[4].kalibr = 4.442132E-005F;
    localGunProperties.bullet[4].speed = 835.0F;
    localGunProperties.bullet[4].power = 0.0018F;
    localGunProperties.bullet[4].powerType = 0;
    localGunProperties.bullet[4].powerRadius = 0.0F;
    localGunProperties.bullet[4].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[4].traceTrail = null;
    localGunProperties.bullet[4].traceColor = -654311169;
    localGunProperties.bullet[4].timeLife = 2.5F;

    localGunProperties.bullet[5].massa = 0.01066849F;
    localGunProperties.bullet[5].kalibr = 4.442132E-005F;
    localGunProperties.bullet[5].speed = 835.0F;
    localGunProperties.bullet[5].power = 0.0018F;
    localGunProperties.bullet[5].powerType = 0;
    localGunProperties.bullet[5].powerRadius = 0.0F;
    localGunProperties.bullet[5].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[5].traceTrail = null;
    localGunProperties.bullet[5].traceColor = -654311169;
    localGunProperties.bullet[5].timeLife = 2.5F;

    return localGunProperties;
  }
}