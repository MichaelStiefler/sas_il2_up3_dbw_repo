package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunMGFFs extends MGunMG15120s
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bCannon = false;
    localGunProperties.bUseHookAsRel = true;

    localGunProperties.fireMesh = "3DO/Effects/GunFire/20mm/mono.sim";
    localGunProperties.fire = null;
    localGunProperties.sprite = "3DO/Effects/GunFire/20mm/GunFlare.eff";
    localGunProperties.smoke = "effects/smokes/MachineGun.eff";
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.sound = "weapon.mgun_20_700";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 10.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = 3;
    localGunProperties.maxDeltaAngle = 0.28F;
    localGunProperties.shotFreq = 8.333333F;
    localGunProperties.traceFreq = 3;
    localGunProperties.bullets = 250;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.115F;
    localGunProperties.bullet[0].kalibr = 0.00032F;
    localGunProperties.bullet[0].speed = 580.0F;
    localGunProperties.bullet[0].power = 0.0036F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -770532113;
    localGunProperties.bullet[0].timeLife = 3.3F;

    localGunProperties.bullet[1].massa = 0.115F;
    localGunProperties.bullet[1].kalibr = 0.000404F;
    localGunProperties.bullet[1].speed = 585.0F;
    localGunProperties.bullet[1].power = 0.00308F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.1F;
    localGunProperties.bullet[1].traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
    localGunProperties.bullet[1].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[1].traceColor = -770532113;
    localGunProperties.bullet[1].timeLife = 3.0F;

    localGunProperties.bullet[2].massa = 0.092F;
    localGunProperties.bullet[2].kalibr = 0.000404F;
    localGunProperties.bullet[2].speed = 690.0F;
    localGunProperties.bullet[2].power = 0.0186F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 0.2F;
    localGunProperties.bullet[2].traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
    localGunProperties.bullet[2].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[2].traceColor = -770532113;
    localGunProperties.bullet[2].timeLife = 2.0F;

    return localGunProperties;
  }
}