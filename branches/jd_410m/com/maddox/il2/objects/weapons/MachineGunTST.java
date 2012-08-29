package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MachineGunTST extends Gun
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bCannon = false;
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.fireMesh = "3DO/Effects/GunFire/20mm/mono.sim";
    localGunProperties.fire = null;
    localGunProperties.sprite = "3DO/Effects/GunFire/20mm/GunFlare.eff";
    localGunProperties.smoke = "effects/smokes/MachineGun.eff";
    localGunProperties.shells = null;
    localGunProperties.sound = "weapon.mgun_07_900";
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 10.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.maxDeltaAngle = 0.0F;
    localGunProperties.shotFreq = 30.0F;
    localGunProperties.traceFreq = 1;
    localGunProperties.bullets = 1000;
    localGunProperties.bulletsCluster = 3;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties() };
    localGunProperties.bullet[0].massa = 0.009600001F;
    localGunProperties.bullet[0].kalibr = 0.00762F;
    localGunProperties.bullet[0].speed = 800.0F;
    localGunProperties.bullet[0].power = 0.0F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].timeLife = 3.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/7mmGreen/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -16711936;
    return localGunProperties;
  }
}