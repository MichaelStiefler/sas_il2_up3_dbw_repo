package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunShVAKANTIMATTERs extends MGunAircraftGeneric
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
    localGunProperties.shells = "3DO/Effects/GunShells/GunShells.eff";
    localGunProperties.sound = "weapon.MGunShVAKANTIMATTERs";
    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 2.5F;
    localGunProperties.emitR = 1.5F;
    localGunProperties.emitTime = 0.03F;
    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = 3;
    localGunProperties.maxDeltaAngle = 0.07F;
    localGunProperties.shotFreq = 10.91667F;
    localGunProperties.traceFreq = 2;
    localGunProperties.bullets = 120;
    localGunProperties.bulletsCluster = 1;
    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.096F;
    localGunProperties.bullet[0].kalibr = 4.4E-005F;
    localGunProperties.bullet[0].speed = 800.0F;
    localGunProperties.bullet[0].power = 0.001F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -771686401;
    localGunProperties.bullet[0].timeLife = 2.5F;
    localGunProperties.bullet[1].massa = 0.096F;
    localGunProperties.bullet[1].kalibr = 4.4E-005F;
    localGunProperties.bullet[1].speed = 800.0F;
    localGunProperties.bullet[1].power = 0.011088F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.25F;
    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = 0;
    localGunProperties.bullet[1].timeLife = 2.5F;
    localGunProperties.bullet[2].massa = 0.096F;
    localGunProperties.bullet[2].kalibr = 4.4E-005F;
    localGunProperties.bullet[2].speed = 800.0F;
    localGunProperties.bullet[2].power = 0.001F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 0.0F;
    localGunProperties.bullet[2].traceMesh = "3do/effects/tracers/20mmGreenBlue/mono.sim";
    localGunProperties.bullet[2].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[2].traceColor = -728447882;
    localGunProperties.bullet[2].timeLife = 2.5F;
    localGunProperties.bullet[3].massa = 0.096F;
    localGunProperties.bullet[3].kalibr = 4.4E-005F;
    localGunProperties.bullet[3].speed = 800.0F;
    localGunProperties.bullet[3].power = 0.011088F;
    localGunProperties.bullet[3].powerType = 0;
    localGunProperties.bullet[3].powerRadius = 0.25F;
    localGunProperties.bullet[3].traceMesh = null;
    localGunProperties.bullet[3].traceTrail = null;
    localGunProperties.bullet[3].traceColor = 0;
    localGunProperties.bullet[3].timeLife = 2.5F;
    localGunProperties.bullet[4].massa = 0.096F;
    localGunProperties.bullet[4].kalibr = 4.4E-005F;
    localGunProperties.bullet[4].speed = 800.0F;
    localGunProperties.bullet[4].power = 0.001F;
    localGunProperties.bullet[4].powerType = 0;
    localGunProperties.bullet[4].powerRadius = 0.0F;
    localGunProperties.bullet[4].traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
    localGunProperties.bullet[4].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[4].traceColor = -770532113;
    localGunProperties.bullet[4].timeLife = 2.5F;
    localGunProperties.bullet[5].massa = 0.096F;
    localGunProperties.bullet[5].kalibr = 4.4E-005F;
    localGunProperties.bullet[5].speed = 800.0F;
    localGunProperties.bullet[5].power = 0.011088F;
    localGunProperties.bullet[5].powerType = 0;
    localGunProperties.bullet[5].powerRadius = 0.25F;
    localGunProperties.bullet[5].traceMesh = null;
    localGunProperties.bullet[5].traceTrail = null;
    localGunProperties.bullet[5].traceColor = 0;
    localGunProperties.bullet[5].timeLife = 2.5F;
    localGunProperties.bullet[6].massa = 0.096F;
    localGunProperties.bullet[6].kalibr = 4.4E-005F;
    localGunProperties.bullet[6].speed = 800.0F;
    localGunProperties.bullet[6].power = 0.001F;
    localGunProperties.bullet[6].powerType = 0;
    localGunProperties.bullet[6].powerRadius = 0.0F;
    localGunProperties.bullet[6].traceMesh = "3do/effects/tracers/20mmWhite/mono.sim";
    localGunProperties.bullet[6].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[6].traceColor = -754974721;
    localGunProperties.bullet[6].timeLife = 2.5F;
    localGunProperties.bullet[7].massa = 0.096F;
    localGunProperties.bullet[7].kalibr = 4.4E-005F;
    localGunProperties.bullet[7].speed = 800.0F;
    localGunProperties.bullet[7].power = 0.011088F;
    localGunProperties.bullet[7].powerType = 0;
    localGunProperties.bullet[7].powerRadius = 0.25F;
    localGunProperties.bullet[7].traceMesh = null;
    localGunProperties.bullet[7].traceTrail = null;
    localGunProperties.bullet[7].traceColor = 0;
    localGunProperties.bullet[7].timeLife = 2.5F;
    localGunProperties.bullet[8].massa = 0.096F;
    localGunProperties.bullet[8].kalibr = 4.4E-005F;
    localGunProperties.bullet[8].speed = 800.0F;
    localGunProperties.bullet[8].power = 0.001F;
    localGunProperties.bullet[8].powerType = 0;
    localGunProperties.bullet[8].powerRadius = 0.0F;
    localGunProperties.bullet[8].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[8].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[8].traceColor = -654299393;
    localGunProperties.bullet[8].timeLife = 2.5F;
    localGunProperties.bullet[9].massa = 0.096F;
    localGunProperties.bullet[9].kalibr = 4.4E-005F;
    localGunProperties.bullet[9].speed = 800.0F;
    localGunProperties.bullet[9].power = 0.024288F;
    localGunProperties.bullet[9].powerType = 0;
    localGunProperties.bullet[9].powerRadius = 0.25F;
    localGunProperties.bullet[9].traceMesh = null;
    localGunProperties.bullet[9].traceTrail = null;
    localGunProperties.bullet[9].traceColor = 0;
    localGunProperties.bullet[9].timeLife = 2.5F;
    return localGunProperties;
  }

  public int nextIndexBulletType()
  {
    int i = super.nextIndexBulletType();
    if (i % 2 == 0)
      i = this.bulletNum > bullets() * 0.8D ? 8 : World.Rnd().nextInt(0, 4) * 2;
    return i;
  }
}