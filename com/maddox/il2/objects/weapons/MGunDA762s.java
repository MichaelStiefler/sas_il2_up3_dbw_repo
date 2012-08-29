package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunDA762s extends MGunAircraftGeneric
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
    localGunProperties.sound = "weapon.mgun_07_1500";

    localGunProperties.emitColor = new Color3f(1.0F, 0.8F, 0.5F);
    localGunProperties.emitI = 5.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1500.0F;
    localGunProperties.weaponType = 1;
    localGunProperties.maxDeltaAngle = 0.22F;
    localGunProperties.shotFreq = 9.166667F;
    localGunProperties.traceFreq = 5;
    localGunProperties.bullets = 750;
    localGunProperties.bulletsCluster = 2;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.009600001F;
    localGunProperties.bullet[0].kalibr = 2.845156E-005F;
    localGunProperties.bullet[0].speed = 848.0F;
    localGunProperties.bullet[0].power = 0.0005F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmGreenBlue/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -762002314;
    localGunProperties.bullet[0].timeLife = 4.5F;

    localGunProperties.bullet[1].massa = 0.009600001F;
    localGunProperties.bullet[1].kalibr = 2.845156E-005F;
    localGunProperties.bullet[1].speed = 848.0F;
    localGunProperties.bullet[1].power = 0.0005F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.0F;
    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = 0;
    localGunProperties.bullet[1].timeLife = 3.2F;

    localGunProperties.bullet[2].massa = 0.009600001F;
    localGunProperties.bullet[2].kalibr = 2.845156E-005F;
    localGunProperties.bullet[2].speed = 848.0F;
    localGunProperties.bullet[2].power = 0.0F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 0.0F;
    localGunProperties.bullet[2].traceMesh = null;
    localGunProperties.bullet[2].traceTrail = null;
    localGunProperties.bullet[2].traceColor = 0;
    localGunProperties.bullet[2].timeLife = 2.6F;

    localGunProperties.bullet[3].massa = 0.009600001F;
    localGunProperties.bullet[3].kalibr = 2.845156E-005F;
    localGunProperties.bullet[3].speed = 848.0F;
    localGunProperties.bullet[3].power = 0.0F;
    localGunProperties.bullet[3].powerType = 0;
    localGunProperties.bullet[3].powerRadius = 0.0F;
    localGunProperties.bullet[3].traceMesh = null;
    localGunProperties.bullet[3].traceTrail = null;
    localGunProperties.bullet[3].traceColor = 0;
    localGunProperties.bullet[3].timeLife = 2.6F;

    localGunProperties.bullet[4].massa = 0.009600001F;
    localGunProperties.bullet[4].kalibr = 2.845156E-005F;
    localGunProperties.bullet[4].speed = 848.0F;
    localGunProperties.bullet[4].power = 0.0F;
    localGunProperties.bullet[4].powerType = 0;
    localGunProperties.bullet[4].powerRadius = 0.0F;
    localGunProperties.bullet[4].traceMesh = null;
    localGunProperties.bullet[4].traceTrail = null;
    localGunProperties.bullet[4].traceColor = 0;
    localGunProperties.bullet[4].timeLife = 2.6F;

    return localGunProperties;
  }
}