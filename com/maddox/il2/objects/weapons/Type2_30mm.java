package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class Type2_30mm extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties gunproperties = super.createProperties();
    gunproperties.bCannon = true;
    gunproperties.bUseHookAsRel = true;
    gunproperties.fireMesh = null;
    gunproperties.fire = "3DO/Effects/GunFire/37mm/GunFire.eff";
    gunproperties.sprite = null;
    gunproperties.smoke = "effects/smokes/CannonTank.eff";
    gunproperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
    gunproperties.sound = "weapon.air_cannon_37";
    gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    gunproperties.emitI = 10.0F;
    gunproperties.emitR = 3.0F;
    gunproperties.emitTime = 0.03F;
    gunproperties.aimMinDist = 10.0F;
    gunproperties.aimMaxDist = 1000.0F;
    gunproperties.weaponType = -1;
    gunproperties.maxDeltaAngle = 0.43F;
    gunproperties.shotFreq = 8.33F;
    gunproperties.shotFreqDeviation = 0.02F;
    gunproperties.traceFreq = 2;
    gunproperties.bullets = 48;
    gunproperties.bulletsCluster = 1;
    gunproperties.bullet = new BulletProperties[] { 
      new BulletProperties(), new BulletProperties() };

    gunproperties.bullet[0].massa = 0.27F;
    gunproperties.bullet[0].kalibr = 0.000567F;
    gunproperties.bullet[0].speed = 710.0F;
    gunproperties.bullet[0].power = 0.018F;
    gunproperties.bullet[0].powerType = 0;
    gunproperties.bullet[0].powerRadius = 1.5F;
    gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
    gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    gunproperties.bullet[0].traceColor = -771686401;
    gunproperties.bullet[0].timeLife = 2.0F;
    gunproperties.bullet[1].massa = 0.27F;
    gunproperties.bullet[1].kalibr = 0.000567F;
    gunproperties.bullet[1].speed = 710.0F;
    gunproperties.bullet[1].power = 0.045F;
    gunproperties.bullet[1].powerType = 0;
    gunproperties.bullet[1].powerRadius = 1.5F;
    gunproperties.bullet[1].traceMesh = null;
    gunproperties.bullet[1].traceTrail = null;
    gunproperties.bullet[1].traceColor = 0;
    gunproperties.bullet[1].timeLife = 3.0F;
    return gunproperties;
  }
}