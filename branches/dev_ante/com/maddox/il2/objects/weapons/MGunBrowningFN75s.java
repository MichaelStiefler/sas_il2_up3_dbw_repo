package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunBrowningFN75s extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bCannon = false;
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.fireMesh = "3DO/Effects/GunFire/7mm/mono.sim";
    localGunProperties.fire = null;
    localGunProperties.sprite = "3DO/Effects/GunFire/7mm/GunFlare.eff";
    localGunProperties.smoke = "effects/smokes/SmallGun.eff";
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.sound = "weapon.mgun_07_900";
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = 1;
    localGunProperties.maxDeltaAngle = 0.32F;
    localGunProperties.shotFreqDeviation = 0.031F;
    localGunProperties.shotFreq = 15.0F;
    localGunProperties.traceFreq = 3;
    localGunProperties.bullets = 500;
    localGunProperties.bulletsCluster = 2;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.0106685F;
    localGunProperties.bullet[0].kalibr = 4.442131E-005F;
    localGunProperties.bullet[0].speed = 835.0F;
    localGunProperties.bullet[0].power = 0.0018F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[0].traceTrail = null;
    localGunProperties.bullet[0].traceColor = -654311169;
    localGunProperties.bullet[0].timeLife = 2.5F;
    localGunProperties.bullet[1].massa = 0.0111F;
    localGunProperties.bullet[1].kalibr = 4.442131E-005F;
    localGunProperties.bullet[1].speed = 835.0F;
    localGunProperties.bullet[1].power = 0.0F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.0F;
    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = 0;
    localGunProperties.bullet[1].timeLife = 1.7F;
    localGunProperties.bullet[3].massa = 0.0106685F;
    localGunProperties.bullet[3].kalibr = 4.442131E-005F;
    localGunProperties.bullet[3].speed = 835.0F;
    localGunProperties.bullet[3].power = 0.0018F;
    localGunProperties.bullet[3].powerType = 0;
    localGunProperties.bullet[3].powerRadius = 0.0F;
    localGunProperties.bullet[3].traceMesh = "3do/effects/tracers/20mmwhite/mono.sim";
    localGunProperties.bullet[3].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[3].traceColor = -654311169;
    localGunProperties.bullet[3].timeLife = 2.5F;
    localGunProperties.bullet[4].massa = 0.0106685F;
    localGunProperties.bullet[4].kalibr = 4.442131E-005F;
    localGunProperties.bullet[4].speed = 835.0F;
    localGunProperties.bullet[4].power = 0.0018F;
    localGunProperties.bullet[4].powerType = 0;
    localGunProperties.bullet[4].powerRadius = 0.0F;
    localGunProperties.bullet[4].traceMesh = null;
    localGunProperties.bullet[4].traceTrail = null;
    localGunProperties.bullet[4].traceColor = 0;
    localGunProperties.bullet[4].timeLife = 1.0F;
    localGunProperties.bullet[5].massa = 0.0106685F;
    localGunProperties.bullet[5].kalibr = 4.442131E-005F;
    localGunProperties.bullet[5].speed = 835.0F;
    localGunProperties.bullet[5].power = 0.0018F;
    localGunProperties.bullet[5].powerType = 0;
    localGunProperties.bullet[5].powerRadius = 0.0F;
    localGunProperties.bullet[5].traceMesh = null;
    localGunProperties.bullet[5].traceTrail = null;
    localGunProperties.bullet[5].traceColor = 0;
    localGunProperties.bullet[5].timeLife = 1.0F;
    return localGunProperties;
  }
}