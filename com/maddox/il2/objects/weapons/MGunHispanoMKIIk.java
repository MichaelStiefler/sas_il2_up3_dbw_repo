package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunHispanoMKIIk extends MGunHispanoMkIs
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();
    localGunProperties.bCannon = false;
    localGunProperties.bUseHookAsRel = true;
    localGunProperties.fireMesh = "3DO/Effects/GunFire/20mm/mono.sim";
    localGunProperties.fire = null;
    localGunProperties.sprite = "3DO/Effects/GunFire/20mm/GunFlare.eff";
    localGunProperties.smoke = "effects/smokes/Gun.eff";
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.sound = "weapon.MGunHispanoMkIs";
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = 3;
    localGunProperties.maxDeltaAngle = 0.234F;
    localGunProperties.shotFreqDeviation = 0.08F;
    localGunProperties.shotFreq = 11.66F;
    localGunProperties.traceFreq = 5;
    localGunProperties.bullets = 250;
    localGunProperties.bulletsCluster = 3;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.129F;
    localGunProperties.bullet[0].kalibr = 0.00032F;
    localGunProperties.bullet[0].speed = 860.0F;
    localGunProperties.bullet[0].power = 0.0104F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.34F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -771686401;
    localGunProperties.bullet[0].timeLife = 1.5F;
    localGunProperties.bullet[1].massa = 0.124F;
    localGunProperties.bullet[1].kalibr = 0.00026F;
    localGunProperties.bullet[1].speed = 860.0F;
    localGunProperties.bullet[1].power = 0.0052F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.0F;
    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = 0;
    localGunProperties.bullet[1].timeLife = 1.5F;
    return localGunProperties;
  }
}