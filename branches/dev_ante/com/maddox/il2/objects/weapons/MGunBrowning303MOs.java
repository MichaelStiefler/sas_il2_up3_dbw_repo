package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunBrowning303MOs extends MGunAircraftGeneric
{
  public GunProperties createProperties()
  {
    GunProperties gunproperties = super.createProperties(); gunproperties.bCannon = false;
    gunproperties.bUseHookAsRel = true;

    gunproperties.fireMesh = "3DO/Effects/GunFire/7mm/mono.sim";
    gunproperties.fire = null;
    gunproperties.sprite = "3DO/Effects/GunFire/7mm/GunFlare.eff";
    gunproperties.smoke = "effects/smokes/SmallGun.eff";
    gunproperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    gunproperties.sound = "weapon.MGunMG17s";

    gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    gunproperties.emitI = 10.0F;
    gunproperties.emitR = 3.0F;
    gunproperties.emitTime = 0.03F;

    gunproperties.aimMinDist = 10.0F;
    gunproperties.aimMaxDist = 1000.0F;
    gunproperties.weaponType = 1;
    gunproperties.maxDeltaAngle = 0.32F;
    gunproperties.shotFreqDeviation = 0.027F;
    gunproperties.shotFreq = 20.0F;
    gunproperties.traceFreq = 4;
    gunproperties.bullets = 1000;
    gunproperties.bulletsCluster = 3;

    gunproperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties() };

    gunproperties.bullet[0].massa = 0.0113F;
    gunproperties.bullet[0].kalibr = 2.742131E-005F;
    gunproperties.bullet[0].speed = 762.0F;
    gunproperties.bullet[0].power = 0.0F;
    gunproperties.bullet[0].powerType = 0;
    gunproperties.bullet[0].powerRadius = 0.0F;
    gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    gunproperties.bullet[0].traceTrail = "3DO/Effects/TEXTURES/fumeefine.eff";
    gunproperties.bullet[0].traceColor = -654311169;
    gunproperties.bullet[0].timeLife = 1.0F;

    gunproperties.bullet[1].massa = 0.0098F;
    gunproperties.bullet[1].kalibr = 2.942131E-005F;
    gunproperties.bullet[1].speed = 747.0F;
    gunproperties.bullet[1].power = 0.0012F;
    gunproperties.bullet[1].powerType = 0;
    gunproperties.bullet[1].powerRadius = 0.0F;
    gunproperties.bullet[1].traceMesh = null;
    gunproperties.bullet[1].traceTrail = null;
    gunproperties.bullet[1].traceColor = 0;
    gunproperties.bullet[1].timeLife = 2.5F;

    gunproperties.bullet[2].massa = 0.0098F;
    gunproperties.bullet[2].kalibr = 2.942131E-005F;
    gunproperties.bullet[2].speed = 747.0F;
    gunproperties.bullet[2].power = 0.0012F;
    gunproperties.bullet[2].powerType = 0;
    gunproperties.bullet[2].powerRadius = 0.0F;
    gunproperties.bullet[2].traceMesh = null;
    gunproperties.bullet[2].traceTrail = null;
    gunproperties.bullet[2].traceColor = 0;
    gunproperties.bullet[2].timeLife = 2.5F;

    gunproperties.bullet[3].massa = 0.0113F;
    gunproperties.bullet[3].kalibr = 2.742131E-005F;
    gunproperties.bullet[3].speed = 762.0F;
    gunproperties.bullet[3].power = 0.0F;
    gunproperties.bullet[3].powerType = 0;
    gunproperties.bullet[3].powerRadius = 0.0F;
    gunproperties.bullet[3].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    gunproperties.bullet[3].traceTrail = "3DO/Effects/TEXTURES/fumeefine.eff";
    gunproperties.bullet[3].traceColor = -654311169;
    gunproperties.bullet[3].timeLife = 1.0F;

    gunproperties.bullet[4].massa = 0.0098F;
    gunproperties.bullet[4].kalibr = 2.942131E-005F;
    gunproperties.bullet[4].speed = 747.0F;
    gunproperties.bullet[4].power = 0.0012F;
    gunproperties.bullet[4].powerType = 0;
    gunproperties.bullet[4].powerRadius = 0.0F;
    gunproperties.bullet[4].traceMesh = null;
    gunproperties.bullet[4].traceTrail = null;
    gunproperties.bullet[4].traceColor = 0;
    gunproperties.bullet[4].timeLife = 2.5F;

    gunproperties.bullet[5].massa = 0.0098F;
    gunproperties.bullet[5].kalibr = 2.942131E-005F;
    gunproperties.bullet[5].speed = 747.0F;
    gunproperties.bullet[5].power = 0.0012F;
    gunproperties.bullet[5].powerType = 0;
    gunproperties.bullet[5].powerRadius = 0.0F;
    gunproperties.bullet[5].traceMesh = null;
    gunproperties.bullet[5].traceTrail = null;
    gunproperties.bullet[5].traceColor = 0;
    gunproperties.bullet[5].timeLife = 2.5F;

    return gunproperties;
  }
}