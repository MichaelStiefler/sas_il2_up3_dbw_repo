package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunBredaSAFAT127s extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bCannon = false;
    localGunProperties.bUseHookAsRel = true;

    localGunProperties.fireMesh = "3DO/Effects/GunFire/12mm/mono.sim";
    localGunProperties.fire = null;
    localGunProperties.sprite = "3DO/Effects/GunFire/12mm/GunFlare.eff";
    localGunProperties.smoke = null;
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.sound = "weapon.mgun_20_500";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 10.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = -1;
    localGunProperties.maxDeltaAngle = 0.229F;
    localGunProperties.shotFreq = 11.666667F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 500;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.0354F;
    localGunProperties.bullet[0].kalibr = 0.00011968F;
    localGunProperties.bullet[0].speed = 760.0F;
    localGunProperties.bullet[0].power = 0.0F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = null;
    localGunProperties.bullet[0].traceTrail = null;
    localGunProperties.bullet[0].traceColor = 0;
    localGunProperties.bullet[0].timeLife = 6.5F;

    localGunProperties.bullet[1].massa = 0.0354F;
    localGunProperties.bullet[1].kalibr = 0.00011968F;
    localGunProperties.bullet[1].speed = 760.0F;
    localGunProperties.bullet[1].power = 0.0017F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.16F;
    localGunProperties.bullet[1].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = -654311169;
    localGunProperties.bullet[1].timeLife = 6.25F;

    localGunProperties.bullet[2].massa = 0.033F;
    localGunProperties.bullet[2].kalibr = 0.00012068F;
    localGunProperties.bullet[2].speed = 770.0F;
    localGunProperties.bullet[2].power = 0.0008F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 0.16F;
    localGunProperties.bullet[2].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[2].traceTrail = null;
    localGunProperties.bullet[2].traceColor = -654311169;
    localGunProperties.bullet[2].timeLife = 6.5F;

    return localGunProperties;
  }
}