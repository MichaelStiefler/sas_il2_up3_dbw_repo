package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunMK214A extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bCannon = true;
    localGunProperties.bUseHookAsRel = true;

    localGunProperties.fireMesh = null;
    localGunProperties.fire = "3DO/Effects/GunFire/45mm/GunFire.eff";
    localGunProperties.sprite = null;
    localGunProperties.smoke = null;
    localGunProperties.shells = null;
    localGunProperties.sound = "weapon.air_cannon_75";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.5F);
    localGunProperties.emitI = 20.0F;
    localGunProperties.emitR = 6.0F;
    localGunProperties.emitTime = 0.06F;

    localGunProperties.aimMinDist = 100.0F;
    localGunProperties.aimMaxDist = 6000.0F;
    localGunProperties.weaponType = 2;
    localGunProperties.maxDeltaAngle = 0.11F;
    localGunProperties.shotFreq = 2.666667F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 21;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 1.973F;
    localGunProperties.bullet[0].kalibr = 0.0029F;
    localGunProperties.bullet[0].speed = 1167.0F;
    localGunProperties.bullet[0].power = 0.0F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -770532113;
    localGunProperties.bullet[0].timeLife = 60.0F;

    localGunProperties.bullet[1].massa = 1.519F;
    localGunProperties.bullet[1].kalibr = 0.0029F;
    localGunProperties.bullet[1].speed = 1100.0F;
    localGunProperties.bullet[1].power = 0.45F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 45.0F;
    localGunProperties.bullet[1].traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
    localGunProperties.bullet[1].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[1].traceColor = -770532113;
    localGunProperties.bullet[1].timeLife = 60.0F;

    return localGunProperties;
  }

  public void setConvDistance(float paramFloat1, float paramFloat2) {
    super.setConvDistance(paramFloat1, paramFloat2 - 0.0F);
  }
}