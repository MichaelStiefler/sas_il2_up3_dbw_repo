package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunVz30s extends MGunAircraftGeneric
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
    localGunProperties.shells = null;
    localGunProperties.sound = "weapon.mgun_07_900";
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 10.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = 1;
    localGunProperties.maxDeltaAngle = 0.09F;
    localGunProperties.shotFreq = 15.83F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 500;
    localGunProperties.bulletsCluster = 1;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.01F;
    localGunProperties.bullet[0].kalibr = 4.35483E-005F;
    localGunProperties.bullet[0].speed = 860.0F;
    localGunProperties.bullet[0].power = 0.0F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = null;
    localGunProperties.bullet[0].traceTrail = null;
    localGunProperties.bullet[0].traceColor = 0;
    localGunProperties.bullet[0].timeLife = 3.0F;

    localGunProperties.bullet[1].massa = 0.01F;
    localGunProperties.bullet[1].kalibr = 8.0E-005F;
    localGunProperties.bullet[1].speed = 860.0F;
    localGunProperties.bullet[1].power = 0.0F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.0F;
    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = 0;
    localGunProperties.bullet[1].timeLife = 3.0F;

    localGunProperties.bullet[2].massa = 0.01F;
    localGunProperties.bullet[2].kalibr = 4.35483E-005F;
    localGunProperties.bullet[2].speed = 860.0F;
    localGunProperties.bullet[2].power = 0.002F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 0.0F;
    localGunProperties.bullet[2].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
    localGunProperties.bullet[2].traceTrail = null;
    localGunProperties.bullet[2].traceColor = -771739905;
    localGunProperties.bullet[2].timeLife = 3.0F;

    return localGunProperties;
  }
}