package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunNS45s extends MGunAircraftGeneric
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
    localGunProperties.sound = "weapon.MGunNS45s";
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = -1;
    localGunProperties.maxDeltaAngle = 0.21F;
    localGunProperties.shotFreq = 4.166667F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 250;
    localGunProperties.bulletsCluster = 1;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 1.065F;
    localGunProperties.bullet[0].kalibr = 0.00158F;
    localGunProperties.bullet[0].speed = 780.0F;
    localGunProperties.bullet[0].power = 0.052F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 3.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -770532113;
    localGunProperties.bullet[0].timeLife = 4.0F;
    localGunProperties.bullet[1].massa = 1.065F;
    localGunProperties.bullet[1].kalibr = 0.00158F;
    localGunProperties.bullet[1].speed = 780.0F;
    localGunProperties.bullet[1].power = 0.052F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 3.0F;
    localGunProperties.bullet[1].traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
    localGunProperties.bullet[1].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[1].traceColor = -770532113;
    localGunProperties.bullet[1].timeLife = 4.0F;
    return localGunProperties;
  }
}