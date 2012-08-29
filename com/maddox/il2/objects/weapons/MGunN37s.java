package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunN37s extends MGunAircraftGeneric
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
    localGunProperties.sound = "weapon.MGunN37s";
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 3000.0F;
    localGunProperties.weaponType = -1;
    localGunProperties.maxDeltaAngle = 0.32F;
    localGunProperties.shotFreq = 3.333333F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 41;
    localGunProperties.bulletsCluster = 1;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.765F;
    localGunProperties.bullet[0].kalibr = 0.0006845F;
    localGunProperties.bullet[0].speed = 675.0F;
    localGunProperties.bullet[0].power = 0.0F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[0].traceTrail = "Effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -654299393;
    localGunProperties.bullet[0].timeLife = 7.75F;
    localGunProperties.bullet[1].massa = 0.722F;
    localGunProperties.bullet[1].kalibr = 0.0006845F;
    localGunProperties.bullet[1].speed = 690.0F;
    localGunProperties.bullet[1].power = 0.0406F;
    localGunProperties.bullet[1].powerType = 1;
    localGunProperties.bullet[1].powerRadius = 3.0F;
    localGunProperties.bullet[1].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[1].traceTrail = "Effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[1].traceColor = -654299393;
    localGunProperties.bullet[1].timeLife = 7.75F;
    return localGunProperties;
  }
}