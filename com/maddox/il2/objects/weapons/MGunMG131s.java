package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunMG131s extends MGunAircraftGeneric
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
    localGunProperties.sound = "weapon.mgun_13_800";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 10.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = 3;
    localGunProperties.maxDeltaAngle = 0.12F;
    localGunProperties.shotFreq = 14.0F;
    localGunProperties.traceFreq = 4;
    localGunProperties.bullets = 300;
    localGunProperties.bulletsCluster = 2;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.035F;
    localGunProperties.bullet[0].kalibr = 0.0001268F;
    localGunProperties.bullet[0].speed = 710.0F;
    localGunProperties.bullet[0].power = 0.00074F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.15F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -755040256;
    localGunProperties.bullet[0].timeLife = 3.0F;

    localGunProperties.bullet[1].massa = 0.034F;
    localGunProperties.bullet[1].kalibr = 0.0001268F;
    localGunProperties.bullet[1].speed = 750.0F;
    localGunProperties.bullet[1].power = 0.0F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.0F;
    localGunProperties.bullet[1].traceMesh = "3do/effects/tracers/20mmGreenBlue/mono.sim";
    localGunProperties.bullet[1].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[1].traceColor = -762002314;
    localGunProperties.bullet[1].timeLife = 3.0F;

    return localGunProperties;
  }
}