package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunMK103s extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bCannon = false;
    localGunProperties.bUseHookAsRel = true;

    localGunProperties.fireMesh = null;
    localGunProperties.fire = "3DO/Effects/GunFire/30mm/GunFire.eff";
    localGunProperties.sprite = "3DO/Effects/GunFire/30mm/GunFlare.eff";
    localGunProperties.smoke = "effects/smokes/MachineGun.eff";
    localGunProperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    localGunProperties.sound = "weapon.mgun_30_500";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 10.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = -1;
    localGunProperties.maxDeltaAngle = 0.35F;
    localGunProperties.shotFreq = 7.083334F;
    localGunProperties.traceFreq = 4;
    localGunProperties.bullets = 50;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.502F;
    localGunProperties.bullet[0].kalibr = 0.000468F;
    localGunProperties.bullet[0].speed = 752.0F;
    localGunProperties.bullet[0].power = 0.0F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -771686401;
    localGunProperties.bullet[0].timeLife = 4.0F;

    localGunProperties.bullet[1].massa = 0.33F;
    localGunProperties.bullet[1].kalibr = 0.000567F;
    localGunProperties.bullet[1].speed = 900.0F;
    localGunProperties.bullet[1].power = 0.0504F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 1.5F;
    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = 0;
    localGunProperties.bullet[1].timeLife = 3.0F;

    localGunProperties.bullet[2].massa = 0.33F;
    localGunProperties.bullet[2].kalibr = 0.000567F;
    localGunProperties.bullet[2].speed = 900.0F;
    localGunProperties.bullet[2].power = 0.0504F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 1.5F;
    localGunProperties.bullet[2].traceMesh = null;
    localGunProperties.bullet[2].traceTrail = null;
    localGunProperties.bullet[2].traceColor = 0;
    localGunProperties.bullet[2].timeLife = 3.0F;

    localGunProperties.bullet[3].massa = 0.455F;
    localGunProperties.bullet[3].kalibr = 0.000567F;
    localGunProperties.bullet[3].speed = 800.0F;
    localGunProperties.bullet[3].power = 0.0168F;
    localGunProperties.bullet[3].powerType = 0;
    localGunProperties.bullet[3].powerRadius = 1.5F;
    localGunProperties.bullet[3].traceMesh = null;
    localGunProperties.bullet[3].traceTrail = null;
    localGunProperties.bullet[3].traceColor = 0;
    localGunProperties.bullet[3].timeLife = 2.0F;

    return localGunProperties;
  }
}