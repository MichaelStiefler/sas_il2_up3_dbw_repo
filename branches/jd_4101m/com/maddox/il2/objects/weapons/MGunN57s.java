package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunN57s extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bCannon = true;
    localGunProperties.bUseHookAsRel = true;

    localGunProperties.fireMesh = null;
    localGunProperties.fire = "3DO/Effects/GunFire/45mm/GunFire.eff";
    localGunProperties.sprite = null;
    localGunProperties.smoke = "effects/smokes/CannonTank.eff";
    localGunProperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    localGunProperties.sound = "weapon.air_cannon_45";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 10.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 3000.0F;
    localGunProperties.weaponType = -1;
    localGunProperties.maxDeltaAngle = 0.21F;
    localGunProperties.shotFreq = 4.283333F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 11;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties() };

    localGunProperties.bullet[0].massa = 2.0F;
    localGunProperties.bullet[0].kalibr = 0.0021443F;
    localGunProperties.bullet[0].speed = 600.0F;
    localGunProperties.bullet[0].power = 0.2F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 4.0F;
    localGunProperties.bullet[0].traceMesh = "3DO/Effects/Tracers/20mmOrange/mono.sim";
    localGunProperties.bullet[0].traceTrail = "Effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -770532113;
    localGunProperties.bullet[0].timeLife = 8.0F;

    return localGunProperties;
  }
}