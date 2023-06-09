package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunShVAKs extends MGunAircraftGeneric
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
    localGunProperties.sound = "weapon.mgun_20_700";

    localGunProperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
    localGunProperties.emitI = 10.0F;
    localGunProperties.emitR = 3.0F;
    localGunProperties.emitTime = 0.03F;

    localGunProperties.aimMinDist = 10.0F;
    localGunProperties.aimMaxDist = 1000.0F;
    localGunProperties.weaponType = 3;
    localGunProperties.maxDeltaAngle = 0.14F;
    localGunProperties.shotFreq = 10.833333F;
    localGunProperties.traceFreq = 3;
    localGunProperties.bullets = 120;
    localGunProperties.bulletsCluster = 1;

    localGunProperties.bullet = new BulletProperties[] { new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties() };

    localGunProperties.bullet[0].massa = 0.096F;
    localGunProperties.bullet[0].kalibr = 0.000248F;
    localGunProperties.bullet[0].speed = 800.0F;
    localGunProperties.bullet[0].power = 0.001F;
    localGunProperties.bullet[0].powerType = 0;
    localGunProperties.bullet[0].powerRadius = 0.0F;
    localGunProperties.bullet[0].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
    localGunProperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[0].traceColor = -771686401;
    localGunProperties.bullet[0].timeLife = 2.5F;

    localGunProperties.bullet[1].massa = 0.096F;
    localGunProperties.bullet[1].kalibr = 0.000248F;
    localGunProperties.bullet[1].speed = 800.0F;
    localGunProperties.bullet[1].power = 0.004488F;
    localGunProperties.bullet[1].powerType = 0;
    localGunProperties.bullet[1].powerRadius = 0.25F;
    localGunProperties.bullet[1].traceMesh = null;
    localGunProperties.bullet[1].traceTrail = null;
    localGunProperties.bullet[1].traceColor = 0;
    localGunProperties.bullet[1].timeLife = 2.5F;

    localGunProperties.bullet[2].massa = 0.096F;
    localGunProperties.bullet[2].kalibr = 0.000248F;
    localGunProperties.bullet[2].speed = 800.0F;
    localGunProperties.bullet[2].power = 0.004488F;
    localGunProperties.bullet[2].powerType = 0;
    localGunProperties.bullet[2].powerRadius = 0.25F;
    localGunProperties.bullet[2].traceMesh = null;
    localGunProperties.bullet[2].traceTrail = null;
    localGunProperties.bullet[2].traceColor = 0;
    localGunProperties.bullet[2].timeLife = 2.5F;

    localGunProperties.bullet[3].massa = 0.096F;
    localGunProperties.bullet[3].kalibr = 0.000248F;
    localGunProperties.bullet[3].speed = 800.0F;
    localGunProperties.bullet[3].power = 0.004488F;
    localGunProperties.bullet[3].powerType = 0;
    localGunProperties.bullet[3].powerRadius = 0.25F;
    localGunProperties.bullet[3].traceMesh = null;
    localGunProperties.bullet[3].traceTrail = null;
    localGunProperties.bullet[3].traceColor = 0;
    localGunProperties.bullet[3].timeLife = 2.5F;

    localGunProperties.bullet[4].massa = 0.096F;
    localGunProperties.bullet[4].kalibr = 0.000248F;
    localGunProperties.bullet[4].speed = 800.0F;
    localGunProperties.bullet[4].power = 0.001F;
    localGunProperties.bullet[4].powerType = 0;
    localGunProperties.bullet[4].powerRadius = 0.0F;
    localGunProperties.bullet[4].traceMesh = "3do/effects/tracers/20mmGreenBlue/mono.sim";
    localGunProperties.bullet[4].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[4].traceColor = -728447882;
    localGunProperties.bullet[4].timeLife = 2.5F;

    localGunProperties.bullet[5].massa = 0.096F;
    localGunProperties.bullet[5].kalibr = 0.000248F;
    localGunProperties.bullet[5].speed = 800.0F;
    localGunProperties.bullet[5].power = 0.004488F;
    localGunProperties.bullet[5].powerType = 0;
    localGunProperties.bullet[5].powerRadius = 0.25F;
    localGunProperties.bullet[5].traceMesh = null;
    localGunProperties.bullet[5].traceTrail = null;
    localGunProperties.bullet[5].traceColor = 0;
    localGunProperties.bullet[5].timeLife = 2.5F;

    localGunProperties.bullet[6].massa = 0.096F;
    localGunProperties.bullet[6].kalibr = 0.000248F;
    localGunProperties.bullet[6].speed = 800.0F;
    localGunProperties.bullet[6].power = 0.004488F;
    localGunProperties.bullet[6].powerType = 0;
    localGunProperties.bullet[6].powerRadius = 0.25F;
    localGunProperties.bullet[6].traceMesh = null;
    localGunProperties.bullet[6].traceTrail = null;
    localGunProperties.bullet[6].traceColor = 0;
    localGunProperties.bullet[6].timeLife = 2.5F;

    localGunProperties.bullet[7].massa = 0.096F;
    localGunProperties.bullet[7].kalibr = 0.000248F;
    localGunProperties.bullet[7].speed = 800.0F;
    localGunProperties.bullet[7].power = 0.004488F;
    localGunProperties.bullet[7].powerType = 0;
    localGunProperties.bullet[7].powerRadius = 0.25F;
    localGunProperties.bullet[7].traceMesh = null;
    localGunProperties.bullet[7].traceTrail = null;
    localGunProperties.bullet[7].traceColor = 0;
    localGunProperties.bullet[7].timeLife = 2.5F;

    localGunProperties.bullet[8].massa = 0.096F;
    localGunProperties.bullet[8].kalibr = 0.000248F;
    localGunProperties.bullet[8].speed = 800.0F;
    localGunProperties.bullet[8].power = 0.001F;
    localGunProperties.bullet[8].powerType = 0;
    localGunProperties.bullet[8].powerRadius = 0.0F;
    localGunProperties.bullet[8].traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
    localGunProperties.bullet[8].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[8].traceColor = -770532113;
    localGunProperties.bullet[8].timeLife = 2.5F;

    localGunProperties.bullet[9].massa = 0.096F;
    localGunProperties.bullet[9].kalibr = 0.000248F;
    localGunProperties.bullet[9].speed = 800.0F;
    localGunProperties.bullet[9].power = 0.004488F;
    localGunProperties.bullet[9].powerType = 0;
    localGunProperties.bullet[9].powerRadius = 0.25F;
    localGunProperties.bullet[9].traceMesh = null;
    localGunProperties.bullet[9].traceTrail = null;
    localGunProperties.bullet[9].traceColor = 0;
    localGunProperties.bullet[9].timeLife = 2.5F;

    localGunProperties.bullet[10].massa = 0.096F;
    localGunProperties.bullet[10].kalibr = 0.000248F;
    localGunProperties.bullet[10].speed = 800.0F;
    localGunProperties.bullet[10].power = 0.004488F;
    localGunProperties.bullet[10].powerType = 0;
    localGunProperties.bullet[10].powerRadius = 0.25F;
    localGunProperties.bullet[10].traceMesh = null;
    localGunProperties.bullet[10].traceTrail = null;
    localGunProperties.bullet[10].traceColor = 0;
    localGunProperties.bullet[10].timeLife = 2.5F;

    localGunProperties.bullet[11].massa = 0.096F;
    localGunProperties.bullet[11].kalibr = 0.000248F;
    localGunProperties.bullet[11].speed = 800.0F;
    localGunProperties.bullet[11].power = 0.004488F;
    localGunProperties.bullet[11].powerType = 0;
    localGunProperties.bullet[11].powerRadius = 0.25F;
    localGunProperties.bullet[11].traceMesh = null;
    localGunProperties.bullet[11].traceTrail = null;
    localGunProperties.bullet[11].traceColor = 0;
    localGunProperties.bullet[11].timeLife = 2.5F;

    localGunProperties.bullet[12].massa = 0.096F;
    localGunProperties.bullet[12].kalibr = 0.000248F;
    localGunProperties.bullet[12].speed = 800.0F;
    localGunProperties.bullet[12].power = 0.001F;
    localGunProperties.bullet[12].powerType = 0;
    localGunProperties.bullet[12].powerRadius = 0.0F;
    localGunProperties.bullet[12].traceMesh = "3do/effects/tracers/20mmWhite/mono.sim";
    localGunProperties.bullet[12].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[12].traceColor = -754974721;
    localGunProperties.bullet[12].timeLife = 2.5F;

    localGunProperties.bullet[13].massa = 0.096F;
    localGunProperties.bullet[13].kalibr = 0.000248F;
    localGunProperties.bullet[13].speed = 800.0F;
    localGunProperties.bullet[13].power = 0.004488F;
    localGunProperties.bullet[13].powerType = 0;
    localGunProperties.bullet[13].powerRadius = 0.25F;
    localGunProperties.bullet[13].traceMesh = null;
    localGunProperties.bullet[13].traceTrail = null;
    localGunProperties.bullet[13].traceColor = 0;
    localGunProperties.bullet[13].timeLife = 2.5F;

    localGunProperties.bullet[14].massa = 0.096F;
    localGunProperties.bullet[14].kalibr = 0.000248F;
    localGunProperties.bullet[14].speed = 800.0F;
    localGunProperties.bullet[14].power = 0.004488F;
    localGunProperties.bullet[14].powerType = 0;
    localGunProperties.bullet[14].powerRadius = 0.25F;
    localGunProperties.bullet[14].traceMesh = null;
    localGunProperties.bullet[14].traceTrail = null;
    localGunProperties.bullet[14].traceColor = 0;
    localGunProperties.bullet[14].timeLife = 2.5F;

    localGunProperties.bullet[15].massa = 0.096F;
    localGunProperties.bullet[15].kalibr = 0.000248F;
    localGunProperties.bullet[15].speed = 800.0F;
    localGunProperties.bullet[15].power = 0.004488F;
    localGunProperties.bullet[15].powerType = 0;
    localGunProperties.bullet[15].powerRadius = 0.25F;
    localGunProperties.bullet[15].traceMesh = null;
    localGunProperties.bullet[15].traceTrail = null;
    localGunProperties.bullet[15].traceColor = 0;
    localGunProperties.bullet[15].timeLife = 2.5F;

    localGunProperties.bullet[16].massa = 0.096F;
    localGunProperties.bullet[16].kalibr = 0.000248F;
    localGunProperties.bullet[16].speed = 800.0F;
    localGunProperties.bullet[16].power = 0.001F;
    localGunProperties.bullet[16].powerType = 0;
    localGunProperties.bullet[16].powerRadius = 0.0F;
    localGunProperties.bullet[16].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
    localGunProperties.bullet[16].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
    localGunProperties.bullet[16].traceColor = -654299393;
    localGunProperties.bullet[16].timeLife = 2.5F;

    localGunProperties.bullet[17].massa = 0.096F;
    localGunProperties.bullet[17].kalibr = 0.000248F;
    localGunProperties.bullet[17].speed = 800.0F;
    localGunProperties.bullet[17].power = 0.004488F;
    localGunProperties.bullet[17].powerType = 0;
    localGunProperties.bullet[17].powerRadius = 0.25F;
    localGunProperties.bullet[17].traceMesh = null;
    localGunProperties.bullet[17].traceTrail = null;
    localGunProperties.bullet[17].traceColor = 0;
    localGunProperties.bullet[17].timeLife = 2.5F;

    localGunProperties.bullet[18].massa = 0.096F;
    localGunProperties.bullet[18].kalibr = 0.000248F;
    localGunProperties.bullet[18].speed = 800.0F;
    localGunProperties.bullet[18].power = 0.004488F;
    localGunProperties.bullet[18].powerType = 0;
    localGunProperties.bullet[18].powerRadius = 0.25F;
    localGunProperties.bullet[18].traceMesh = null;
    localGunProperties.bullet[18].traceTrail = null;
    localGunProperties.bullet[18].traceColor = 0;
    localGunProperties.bullet[18].timeLife = 2.5F;

    localGunProperties.bullet[19].massa = 0.096F;
    localGunProperties.bullet[19].kalibr = 0.000248F;
    localGunProperties.bullet[19].speed = 800.0F;
    localGunProperties.bullet[19].power = 0.004488F;
    localGunProperties.bullet[19].powerType = 0;
    localGunProperties.bullet[19].powerRadius = 0.25F;
    localGunProperties.bullet[19].traceMesh = null;
    localGunProperties.bullet[19].traceTrail = null;
    localGunProperties.bullet[19].traceColor = 0;
    localGunProperties.bullet[19].timeLife = 2.5F;

    return localGunProperties;
  }

  public int nextIndexBulletType() {
    int i = super.nextIndexBulletType();
    if (i % 4 == 0) i = this.bulletNum > bullets() * 0.8D ? 16 : World.Rnd().nextInt(0, 4) * 4;
    return i;
  }
}