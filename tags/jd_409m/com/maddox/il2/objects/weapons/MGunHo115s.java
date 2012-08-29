package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunHo115s extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties localGunProperties = super.createProperties();

    localGunProperties.bCannon = true;
    localGunProperties.bUseHookAsRel = true;

    localGunProperties.fireMesh = null;
    localGunProperties.fire = "3DO/Effects/GunFire/37mm/GunFire.eff";
    localGunProperties.sprite = null;
    localGunProperties.smoke = "effects/smokes/CannonTank.eff";
    localGunProperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    localGunProperties.sound = "weapon.air_cannon_37";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 10.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = -1;
    localGunProperties.maxDeltaAngle = 0.43F;
    localGunProperties.shotFreq = 10.0F;
    localGunProperties.traceFreq = 2;
    localGunProperties.bullets = 50;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.455F;
    localGunProperties.bullet[0].kalibr = 0.000567F;
    localGunProperties.bullet[0].speed = 700.0F;
    localGunProperties.bullet[0].power = 0.018F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 1.5F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -771686401;
    localGunProperties.bullet[0].timeLife = 2.0F;

    localGunProperties.bullet[1].massa = 0.33F;
    localGunProperties.bullet[1].kalibr = 0.000567F;
    localGunProperties.bullet[1].speed = 700.0F;
    localGunProperties.bullet[1].power = 0.054F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 1.5F;
    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = 0;
    localGunProperties.bullet[1].timeLife = 3.0F;

    return localGunProperties;
  }
}