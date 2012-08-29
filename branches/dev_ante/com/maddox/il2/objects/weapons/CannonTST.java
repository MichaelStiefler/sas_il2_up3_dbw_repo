package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonTST extends Gun
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bCannon = true;
    localGunProperties.bUseHookAsRel = false;
    localGunProperties.fireMesh = null;
    localGunProperties.fire = "3DO/Effects/GunFire/88mm/CannonTank.eff";
    localGunProperties.sprite = null;
    localGunProperties.smoke = "effects/smokes/CannonTank.eff";
    localGunProperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    localGunProperties.sound = "weapon.Cannon75";
    localGunProperties.emitColor = new Color3f(1.0F, 0.5F, 0.0F);
    localGunProperties.emitI = 10.0F;
    localGunProperties.emitR = 4.0F;
    localGunProperties.emitTime = 0.1F;
    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.maxDeltaAngle = 0.0F;
    localGunProperties.shotFreq = 5.0F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 100;
    localGunProperties.bulletsCluster = 1;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties() };
    localGunProperties.bullet[0].massa = 5.2F;
    localGunProperties.bullet[0].kalibr = 0.075F;
    localGunProperties.bullet[0].speed = 800.0F;
    localGunProperties.bullet[0].power = 0.0F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].timeLife = 5.0F;
    localGunProperties.bullet[0].traceMesh = "3DO/Effects/Tracers/7mmYellow/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -771686401;
    return localGunProperties;
  }
}