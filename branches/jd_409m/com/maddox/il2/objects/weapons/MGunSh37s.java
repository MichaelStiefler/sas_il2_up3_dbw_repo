package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunSh37s extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bCannon = true;
    localGunProperties.bUseHookAsRel = true;

    localGunProperties.fireMesh = null;
    localGunProperties.fire = "3DO/Effects/GunFire/37mm/GunFire.eff";
    localGunProperties.sprite = null;
    localGunProperties.smoke = "effects/smokes/CannonTank.eff";
    localGunProperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    localGunProperties.sound = "weapon.air_cannon_37";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 10.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = -1;
    localGunProperties.maxDeltaAngle = 0.42F;
    localGunProperties.shotFreq = 3.333333F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 20;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.77F;
    localGunProperties.bullet[0].kalibr = 0.0006845F;
    localGunProperties.bullet[0].speed = 890.0F;
    localGunProperties.bullet[0].power = 0.0F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 1.5F;

    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -654299393;

    localGunProperties.bullet[0].timeLife = 2.0F;

    localGunProperties.bullet[1].massa = 0.73F;
    localGunProperties.bullet[1].kalibr = 0.0006845F;
    localGunProperties.bullet[1].speed = 890.0F;
    localGunProperties.bullet[1].power = 0.02842F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 3.5F;

    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = "3DO/Effects/Tracers/TrailThin.eff";
    localGunProperties.bullet[1].traceColor = 16843009;

    localGunProperties.bullet[1].timeLife = 3.0F;

    localGunProperties.bullet[2].massa = 0.73F;
    localGunProperties.bullet[2].kalibr = 0.0006845F;
    localGunProperties.bullet[2].speed = 890.0F;
    localGunProperties.bullet[2].power = 0.02842F;
    localGunProperties.bullet[2].powerType = 1;
    localGunProperties.bullet[2].powerRadius = 5.7F;

    localGunProperties.bullet[2].traceMesh = "3do/effects/tracers/20mmGreenBlue/mono.sim";
    localGunProperties.bullet[2].traceTrail = "3DO/Effects/Tracers/TrailThin.eff";
    localGunProperties.bullet[2].traceColor = -728447882;

    localGunProperties.bullet[2].timeLife = 2.0F;

    return localGunProperties;
  }
}