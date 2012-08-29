package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunBK37 extends MGunAircraftGeneric
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
    localGunProperties.sound = "weapon.MGunBK37";
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = -1;
    localGunProperties.maxDeltaAngle = 0.11F;
    localGunProperties.shotFreq = 1.25F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 12;
    localGunProperties.bulletsCluster = 1;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.68F;
    localGunProperties.bullet[0].kalibr = 0.000219F;
    localGunProperties.bullet[0].speed = 1170.0F;
    localGunProperties.bullet[0].power = 0.0F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[0].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localGunProperties.bullet[0].traceColor = -654299393;
    localGunProperties.bullet[0].timeLife = 15.0F;
    localGunProperties.bullet[1].massa = 0.628F;
    localGunProperties.bullet[1].kalibr = 0.000219F;
    localGunProperties.bullet[1].speed = 812.0F;
    localGunProperties.bullet[1].power = 0.096F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 10.0F;
    localGunProperties.bullet[1].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[1].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localGunProperties.bullet[1].traceColor = -654299393;
    localGunProperties.bullet[1].timeLife = 15.0F;
    localGunProperties.bullet[2].massa = 0.55F;
    localGunProperties.bullet[2].kalibr = 0.000219F;
    localGunProperties.bullet[2].speed = 860.0F;
    localGunProperties.bullet[2].power = 0.09F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 10.0F;
    localGunProperties.bullet[2].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[2].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
    localGunProperties.bullet[2].traceColor = -654299393;
    localGunProperties.bullet[2].timeLife = 15.0F;
    return localGunProperties;
  }

  public void setConvDistance(float paramFloat1, float paramFloat2)
  {
    super.setConvDistance(paramFloat1, paramFloat2 - 1.2F);
  }
}