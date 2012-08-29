package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunM9s extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bCannon = true;
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.fireMesh = null;
    localGunProperties.fire = "3DO/Effects/GunFire/37mm/GunFire.eff";
    localGunProperties.sprite = null;
    localGunProperties.smoke = null;
    localGunProperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    localGunProperties.sound = "weapon.MGunM9s";
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = -1;
    localGunProperties.maxDeltaAngle = 0.0F;
    localGunProperties.shotFreq = 2.5F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 30;
    localGunProperties.bulletsCluster = 1;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.604F;
    localGunProperties.bullet[0].kalibr = 0.0009035401F;
    localGunProperties.bullet[0].speed = 820.0F;
    localGunProperties.bullet[0].power = 0.088F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 10.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -654299393;
    localGunProperties.bullet[0].timeLife = 10.0F;
    return localGunProperties;
  }
}