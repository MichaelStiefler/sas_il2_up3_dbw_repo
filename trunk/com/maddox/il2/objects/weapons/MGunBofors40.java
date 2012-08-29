package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunBofors40 extends MGunAircraftGeneric
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
    localGunProperties.shells = null;
    localGunProperties.sound = "weapon.air_cannon_37";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 10.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = -1;
    localGunProperties.maxDeltaAngle = 0.11F;
    localGunProperties.shotFreq = 2.0F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 4;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 1.1F;
    localGunProperties.bullet[0].kalibr = 0.000336F;
    localGunProperties.bullet[0].speed = 811.0F;
    localGunProperties.bullet[0].power = 0.092F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 10.0F;
    localGunProperties.bullet[0].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[0].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localGunProperties.bullet[0].traceColor = -654299393;
    localGunProperties.bullet[0].timeLife = 15.0F;

    localGunProperties.bullet[1].massa = 1.3F;
    localGunProperties.bullet[1].kalibr = 0.000336F;
    localGunProperties.bullet[1].speed = 805.0F;
    localGunProperties.bullet[1].power = 0.0F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.0F;
    localGunProperties.bullet[1].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[1].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localGunProperties.bullet[1].traceColor = -654299393;
    localGunProperties.bullet[1].timeLife = 15.0F;

    return localGunProperties;
  }

  public void setConvDistance(float paramFloat1, float paramFloat2) {
    super.setConvDistance(paramFloat1, paramFloat2 - 1.2F);
  }
}