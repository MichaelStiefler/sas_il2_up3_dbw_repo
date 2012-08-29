package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunVYas extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bCannon = false;
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.fireMesh = null;
    localGunProperties.fire = "3DO/Effects/GunFire/30mm/GunFire.eff";
    localGunProperties.sprite = "3DO/Effects/GunFire/30mm/GunFlare.eff";
    localGunProperties.smoke = "effects/smokes/MachineGun.eff";
    localGunProperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    localGunProperties.sound = "weapon.MGunVYas";
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = 3;
    localGunProperties.maxDeltaAngle = 0.14F;
    localGunProperties.shotFreq = 10.0F;
    localGunProperties.traceFreq = 3;
    localGunProperties.bullets = 120;
    localGunProperties.bulletsCluster = 1;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.195F;
    localGunProperties.bullet[0].kalibr = 0.000317F;
    localGunProperties.bullet[0].speed = 890.0F;
    localGunProperties.bullet[0].power = 0.015163F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 1.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -654299393;
    localGunProperties.bullet[0].timeLife = 4.0F;
    localGunProperties.bullet[1].massa = 0.195F;
    localGunProperties.bullet[1].kalibr = 0.000317F;
    localGunProperties.bullet[1].speed = 890.0F;
    localGunProperties.bullet[1].power = 0.015163F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 1.0F;
    localGunProperties.bullet[1].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[1].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[1].traceColor = -654299393;
    localGunProperties.bullet[1].timeLife = 4.0F;
    localGunProperties.bullet[2].massa = 0.201F;
    localGunProperties.bullet[2].kalibr = 0.000317F;
    localGunProperties.bullet[2].speed = 890.0F;
    localGunProperties.bullet[2].power = 0.008F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 0.0F;
    localGunProperties.bullet[2].traceMesh = null;
    localGunProperties.bullet[2].traceTrail = null;
    localGunProperties.bullet[2].traceColor = 0;
    localGunProperties.bullet[2].timeLife = 4.0F;
    return localGunProperties;
  }
}