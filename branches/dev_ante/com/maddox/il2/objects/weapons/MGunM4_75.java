package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunM4_75 extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bCannon = true;
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.fireMesh = null;
    localGunProperties.fire = "3DO/Effects/GunFire/75mm/GunFire.eff";
    localGunProperties.sprite = null;
    localGunProperties.smoke = null;
    localGunProperties.shells = null;
    localGunProperties.sound = "weapon.MGunM4_75";
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.aimMinDist = 100.0F;
    localGunProperties.aimMaxDist = 5000.0F;
    localGunProperties.weaponType = -1;
    localGunProperties.maxDeltaAngle = 0.0F;
    localGunProperties.shotFreq = 0.08333334F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 25;
    localGunProperties.bulletsCluster = 1;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties() };

    localGunProperties.bullet[0].massa = 6.65F;
    localGunProperties.bullet[0].kalibr = 0.0050063F;
    localGunProperties.bullet[0].speed = 610.0F;
    localGunProperties.bullet[0].power = 0.675F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 15.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -654299393;
    localGunProperties.bullet[0].timeLife = 30.0F;
    return localGunProperties;
  }
}