package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunMG17s extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bCannon = false;
    localGunProperties.bUseHookAsRel = true;

    localGunProperties.fireMesh = "3DO/Effects/GunFire/7mm/mono.sim";
    localGunProperties.fire = null;
    localGunProperties.sprite = "3DO/Effects/GunFire/7mm/GunFlare.eff";
    localGunProperties.smoke = null;
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.sound = "weapon.mgun_07_900";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 10.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = 1;
    localGunProperties.maxDeltaAngle = 0.09F;
    localGunProperties.shotFreq = 15.0F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 500;
    localGunProperties.bulletsCluster = 3;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.01F;
    localGunProperties.bullet[0].kalibr = 4.35483E-005F;
    localGunProperties.bullet[0].speed = 810.0F;
    localGunProperties.bullet[0].power = 0.0F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;

    localGunProperties.bullet[0].traceMesh = null;
    localGunProperties.bullet[0].traceTrail = null;
    localGunProperties.bullet[0].traceColor = 0;

    localGunProperties.bullet[0].timeLife = 3.0F;

    localGunProperties.bullet[1].massa = 0.01F;
    localGunProperties.bullet[1].kalibr = 4.35483E-005F;
    localGunProperties.bullet[1].speed = 810.0F;
    localGunProperties.bullet[1].power = 0.0F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.0F;

    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = 0;

    localGunProperties.bullet[1].timeLife = 3.0F;

    localGunProperties.bullet[2].massa = 0.01F;
    localGunProperties.bullet[2].kalibr = 4.35483E-005F;
    localGunProperties.bullet[2].speed = 810.0F;
    localGunProperties.bullet[2].power = 0.0F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 0.0F;

    localGunProperties.bullet[2].traceMesh = null;
    localGunProperties.bullet[2].traceTrail = null;
    localGunProperties.bullet[2].traceColor = 0;

    localGunProperties.bullet[2].timeLife = 3.0F;

    localGunProperties.bullet[3].massa = 0.01F;
    localGunProperties.bullet[3].kalibr = 4.35483E-005F;
    localGunProperties.bullet[3].speed = 810.0F;
    localGunProperties.bullet[3].power = 0.0F;
    localGunProperties.bullet[3].powerType = 0;
    localGunProperties.bullet[3].powerRadius = 0.0F;

    localGunProperties.bullet[3].traceMesh = null;
    localGunProperties.bullet[3].traceTrail = null;
    localGunProperties.bullet[3].traceColor = 0;

    localGunProperties.bullet[3].timeLife = 3.0F;

    localGunProperties.bullet[4].massa = 0.01F;
    localGunProperties.bullet[4].kalibr = 4.35483E-005F;
    localGunProperties.bullet[4].speed = 810.0F;
    localGunProperties.bullet[4].power = 0.0F;
    localGunProperties.bullet[4].powerType = 0;
    localGunProperties.bullet[4].powerRadius = 0.0F;

    localGunProperties.bullet[4].traceMesh = null;
    localGunProperties.bullet[4].traceTrail = null;
    localGunProperties.bullet[4].traceColor = 0;

    localGunProperties.bullet[4].timeLife = 3.0F;

    localGunProperties.bullet[5].massa = 0.01F;
    localGunProperties.bullet[5].kalibr = 4.35483E-005F;
    localGunProperties.bullet[5].speed = 810.0F;
    localGunProperties.bullet[5].power = 0.002F;
    localGunProperties.bullet[5].powerType = 0;
    localGunProperties.bullet[5].powerRadius = 0.0F;

    localGunProperties.bullet[5].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[5].traceTrail = "Effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[5].traceColor = -771739905;

    localGunProperties.bullet[5].timeLife = 2.0F;

    localGunProperties.bullet[6].massa = 0.01F;
    localGunProperties.bullet[6].kalibr = 4.35483E-005F;
    localGunProperties.bullet[6].speed = 810.0F;
    localGunProperties.bullet[6].power = 0.002F;
    localGunProperties.bullet[6].powerType = 0;
    localGunProperties.bullet[6].powerRadius = 0.0F;

    localGunProperties.bullet[6].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[6].traceTrail = "Effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[6].traceColor = -771739905;

    localGunProperties.bullet[6].timeLife = 2.0F;

    localGunProperties.bullet[7].massa = 0.01F;
    localGunProperties.bullet[7].kalibr = 4.35483E-005F;
    localGunProperties.bullet[7].speed = 810.0F;
    localGunProperties.bullet[7].power = 0.002F;
    localGunProperties.bullet[7].powerType = 0;
    localGunProperties.bullet[7].powerRadius = 0.0F;

    localGunProperties.bullet[7].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[7].traceTrail = "Effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[7].traceColor = -771739905;

    localGunProperties.bullet[7].timeLife = 2.0F;

    localGunProperties.bullet[8].massa = 0.01F;
    localGunProperties.bullet[8].kalibr = 4.35483E-005F;
    localGunProperties.bullet[8].speed = 810.0F;
    localGunProperties.bullet[8].power = 0.002F;
    localGunProperties.bullet[8].powerType = 0;
    localGunProperties.bullet[8].powerRadius = 0.0F;

    localGunProperties.bullet[8].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[8].traceTrail = "Effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[8].traceColor = -771739905;

    localGunProperties.bullet[8].timeLife = 2.0F;

    localGunProperties.bullet[9].massa = 0.01F;
    localGunProperties.bullet[9].kalibr = 4.35483E-005F;
    localGunProperties.bullet[9].speed = 810.0F;
    localGunProperties.bullet[9].power = 0.002F;
    localGunProperties.bullet[9].powerType = 0;
    localGunProperties.bullet[9].powerRadius = 0.1F;

    localGunProperties.bullet[9].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[9].traceTrail = "Effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[9].traceColor = -771739905;

    localGunProperties.bullet[9].timeLife = 2.0F;

    return localGunProperties;
  }
}