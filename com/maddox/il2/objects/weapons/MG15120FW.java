package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MG15120FW extends MGunAircraftGeneric
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
    localGunProperties.sound = "weapon.mgun_20_700";
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = 3;
    localGunProperties.maxDeltaAngle = 0.27F;
    localGunProperties.shotFreqDeviation = 0.027F;
    localGunProperties.shotFreq = 11.5F;
    localGunProperties.traceFreq = 3;
    localGunProperties.bullets = 250;
    localGunProperties.bulletsCluster = 1;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.115F;
    localGunProperties.bullet[0].kalibr = 0.00026F;
    localGunProperties.bullet[0].speed = 710.0F;
    localGunProperties.bullet[0].power = 0.0036F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = null;
    localGunProperties.bullet[0].traceTrail = null;
    localGunProperties.bullet[0].traceColor = -755040256;
    localGunProperties.bullet[0].timeLife = 3.3F;
    localGunProperties.bullet[1].massa = 0.092F;
    localGunProperties.bullet[1].kalibr = 0.000404F;
    localGunProperties.bullet[1].speed = 785.0F;
    localGunProperties.bullet[1].power = 0.0199F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.65F;
    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = -754974976;
    localGunProperties.bullet[1].timeLife = 2.0F;
    localGunProperties.bullet[2].massa = 0.0115F;
    localGunProperties.bullet[2].kalibr = 0.00026F;
    localGunProperties.bullet[2].speed = 775.0F;
    localGunProperties.bullet[2].power = 0.0F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 0.65F;
    localGunProperties.bullet[2].traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
    localGunProperties.bullet[2].traceTrail = "3DO/Effects/TEXTURES/fumeefine.eff";
    localGunProperties.bullet[2].traceColor = -754974976;
    localGunProperties.bullet[2].timeLife = 2.0F;
    localGunProperties.bullet[3].massa = 0.092F;
    localGunProperties.bullet[3].kalibr = 0.000404F;
    localGunProperties.bullet[3].speed = 775.0F;
    localGunProperties.bullet[3].power = 0.0199F;
    localGunProperties.bullet[3].powerType = 0;
    localGunProperties.bullet[3].powerRadius = 0.65F;
    localGunProperties.bullet[3].traceMesh = null;
    localGunProperties.bullet[3].traceTrail = null;
    localGunProperties.bullet[3].traceColor = -754974976;
    localGunProperties.bullet[3].timeLife = 2.0F;
    localGunProperties.bullet[0].massa = 0.115F;
    localGunProperties.bullet[0].kalibr = 0.00026F;
    localGunProperties.bullet[0].speed = 710.0F;
    localGunProperties.bullet[0].power = 0.0036F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = null;
    localGunProperties.bullet[0].traceTrail = null;
    localGunProperties.bullet[0].traceColor = -755040256;
    localGunProperties.bullet[0].timeLife = 3.3F;
    return localGunProperties;
  }
}