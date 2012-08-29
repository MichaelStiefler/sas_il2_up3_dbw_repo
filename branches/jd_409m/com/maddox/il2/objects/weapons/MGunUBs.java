package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunUBs extends MGunAircraftGeneric
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
    localGunProperties.maxDeltaAngle = 0.1F;
    localGunProperties.shotFreq = 15.0F;
    localGunProperties.traceFreq = 4;
    localGunProperties.bullets = 150;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.0448F;
    localGunProperties.bullet[0].kalibr = 0.000120968F;
    localGunProperties.bullet[0].speed = 850.0F;
    localGunProperties.bullet[0].power = 0.001F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmWhite/mono.sim";
    localGunProperties.bullet[0].traceTrail = null;
    localGunProperties.bullet[0].traceColor = -754974721;
    localGunProperties.bullet[0].timeLife = 3.0F;

    localGunProperties.bullet[1].massa = 0.051F;
    localGunProperties.bullet[1].kalibr = 0.000120968F;
    localGunProperties.bullet[1].speed = 850.0F;
    localGunProperties.bullet[1].power = 0.0F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.0F;
    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = 0;
    localGunProperties.bullet[1].timeLife = 3.0F;

    localGunProperties.bullet[2].massa = 0.045F;
    localGunProperties.bullet[2].kalibr = 0.000125806F;
    localGunProperties.bullet[2].speed = 850.0F;
    localGunProperties.bullet[2].power = 0.002322F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 0.1F;
    localGunProperties.bullet[2].traceMesh = null;
    localGunProperties.bullet[2].traceTrail = null;
    localGunProperties.bullet[2].traceColor = 0;
    localGunProperties.bullet[2].timeLife = 2.0F;

    localGunProperties.bullet[3].massa = 0.045F;
    localGunProperties.bullet[3].kalibr = 0.000125806F;
    localGunProperties.bullet[3].speed = 850.0F;
    localGunProperties.bullet[3].power = 0.002322F;
    localGunProperties.bullet[3].powerType = 0;
    localGunProperties.bullet[3].powerRadius = 0.1F;
    localGunProperties.bullet[3].traceMesh = null;
    localGunProperties.bullet[3].traceTrail = null;
    localGunProperties.bullet[3].traceColor = 0;
    localGunProperties.bullet[3].timeLife = 2.0F;

    return localGunProperties;
  }
}