// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunShVAKANTIMATTERs.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunShVAKANTIMATTERs extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunShVAKANTIMATTERs()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = "3DO/Effects/GunFire/20mm/mono.sim";
        gunproperties.fire = null;
        gunproperties.sprite = "3DO/Effects/GunFire/20mm/GunFlare.eff";
        gunproperties.smoke = "effects/smokes/MachineGun.eff";
        gunproperties.shells = "3DO/Effects/GunShells/GunShells.eff";
        gunproperties.sound = "weapon.MGunShVAKANTIMATTERs";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = 3;
        gunproperties.maxDeltaAngle = 0.14F;
        gunproperties.shotFreq = 11.66F;
        gunproperties.traceFreq = 2;
        gunproperties.bullets = 120;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), 
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.096F;
        gunproperties.bullet[0].kalibr = 0.000268F;
        gunproperties.bullet[0].speed = 750F;
        gunproperties.bullet[0].power = 0.001F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd9002eff;
        gunproperties.bullet[0].timeLife = 2.5F;
        gunproperties.bullet[1].massa = 0.095F;
        gunproperties.bullet[1].kalibr = 0.000404F;
        gunproperties.bullet[1].speed = 790F;
        gunproperties.bullet[1].power = 0.0045F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.15F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = null;
        gunproperties.bullet[1].traceColor = 0;
        gunproperties.bullet[1].timeLife = 2.5F;
        gunproperties.bullet[2].massa = 0.095F;
        gunproperties.bullet[2].kalibr = 0.000404F;
        gunproperties.bullet[2].speed = 790F;
        gunproperties.bullet[2].power = 0.0045F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 0.15F;
        gunproperties.bullet[2].traceMesh = null;
        gunproperties.bullet[2].traceTrail = null;
        gunproperties.bullet[2].traceColor = 0;
        gunproperties.bullet[2].timeLife = 2.5F;
        gunproperties.bullet[3].massa = 0.095F;
        gunproperties.bullet[3].kalibr = 0.000404F;
        gunproperties.bullet[3].speed = 790F;
        gunproperties.bullet[3].power = 0.0045F;
        gunproperties.bullet[3].powerType = 0;
        gunproperties.bullet[3].powerRadius = 0.15F;
        gunproperties.bullet[3].traceMesh = null;
        gunproperties.bullet[3].traceTrail = null;
        gunproperties.bullet[3].traceColor = 0;
        gunproperties.bullet[3].timeLife = 2.5F;
        gunproperties.bullet[4].massa = 0.096F;
        gunproperties.bullet[4].kalibr = 0.000268F;
        gunproperties.bullet[4].speed = 750F;
        gunproperties.bullet[4].power = 0.001F;
        gunproperties.bullet[4].powerType = 0;
        gunproperties.bullet[4].powerRadius = 0.0F;
        gunproperties.bullet[4].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        gunproperties.bullet[4].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[4].traceColor = 0xd9002eff;
        gunproperties.bullet[4].timeLife = 2.5F;
        gunproperties.bullet[5].massa = 0.095F;
        gunproperties.bullet[5].kalibr = 0.000404F;
        gunproperties.bullet[5].speed = 790F;
        gunproperties.bullet[5].power = 0.0045F;
        gunproperties.bullet[5].powerType = 0;
        gunproperties.bullet[5].powerRadius = 0.15F;
        gunproperties.bullet[5].traceMesh = null;
        gunproperties.bullet[5].traceTrail = null;
        gunproperties.bullet[5].traceColor = 0;
        gunproperties.bullet[5].timeLife = 2.5F;
        gunproperties.bullet[6].massa = 0.095F;
        gunproperties.bullet[6].kalibr = 0.000404F;
        gunproperties.bullet[6].speed = 790F;
        gunproperties.bullet[6].power = 0.0045F;
        gunproperties.bullet[6].powerType = 0;
        gunproperties.bullet[6].powerRadius = 0.15F;
        gunproperties.bullet[6].traceMesh = null;
        gunproperties.bullet[6].traceTrail = null;
        gunproperties.bullet[6].traceColor = 0;
        gunproperties.bullet[6].timeLife = 2.5F;
        gunproperties.bullet[7].massa = 0.095F;
        gunproperties.bullet[7].kalibr = 0.000404F;
        gunproperties.bullet[7].speed = 790F;
        gunproperties.bullet[7].power = 0.0045F;
        gunproperties.bullet[7].powerType = 0;
        gunproperties.bullet[7].powerRadius = 0.15F;
        gunproperties.bullet[7].traceMesh = null;
        gunproperties.bullet[7].traceTrail = null;
        gunproperties.bullet[7].traceColor = 0;
        gunproperties.bullet[7].timeLife = 2.5F;
        gunproperties.bullet[8].massa = 0.096F;
        gunproperties.bullet[8].kalibr = 0.000268F;
        gunproperties.bullet[8].speed = 750F;
        gunproperties.bullet[8].power = 0.001F;
        gunproperties.bullet[8].powerType = 0;
        gunproperties.bullet[8].powerRadius = 0.0F;
        gunproperties.bullet[8].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        gunproperties.bullet[8].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[8].traceColor = 0xd9002eff;
        gunproperties.bullet[8].timeLife = 2.5F;
        gunproperties.bullet[9].massa = 0.095F;
        gunproperties.bullet[9].kalibr = 0.000404F;
        gunproperties.bullet[9].speed = 790F;
        gunproperties.bullet[9].power = 0.0045F;
        gunproperties.bullet[9].powerType = 0;
        gunproperties.bullet[9].powerRadius = 0.15F;
        gunproperties.bullet[9].traceMesh = null;
        gunproperties.bullet[9].traceTrail = null;
        gunproperties.bullet[9].traceColor = 0;
        gunproperties.bullet[9].timeLife = 2.5F;
        gunproperties.bullet[10].massa = 0.095F;
        gunproperties.bullet[10].kalibr = 0.000404F;
        gunproperties.bullet[10].speed = 790F;
        gunproperties.bullet[10].power = 0.0045F;
        gunproperties.bullet[10].powerType = 0;
        gunproperties.bullet[10].powerRadius = 0.15F;
        gunproperties.bullet[10].traceMesh = null;
        gunproperties.bullet[10].traceTrail = null;
        gunproperties.bullet[10].traceColor = 0;
        gunproperties.bullet[10].timeLife = 2.5F;
        gunproperties.bullet[11].massa = 0.095F;
        gunproperties.bullet[11].kalibr = 0.000404F;
        gunproperties.bullet[11].speed = 790F;
        gunproperties.bullet[11].power = 0.0045F;
        gunproperties.bullet[11].powerType = 0;
        gunproperties.bullet[11].powerRadius = 0.15F;
        gunproperties.bullet[11].traceMesh = null;
        gunproperties.bullet[11].traceTrail = null;
        gunproperties.bullet[11].traceColor = 0;
        gunproperties.bullet[11].timeLife = 2.5F;
        gunproperties.bullet[12].massa = 0.096F;
        gunproperties.bullet[12].kalibr = 0.000268F;
        gunproperties.bullet[12].speed = 750F;
        gunproperties.bullet[12].power = 0.001F;
        gunproperties.bullet[12].powerType = 0;
        gunproperties.bullet[12].powerRadius = 0.0F;
        gunproperties.bullet[12].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        gunproperties.bullet[12].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[12].traceColor = 0xd9002eff;
        gunproperties.bullet[12].timeLife = 2.5F;
        gunproperties.bullet[13].massa = 0.095F;
        gunproperties.bullet[13].kalibr = 0.000404F;
        gunproperties.bullet[13].speed = 790F;
        gunproperties.bullet[13].power = 0.0045F;
        gunproperties.bullet[13].powerType = 0;
        gunproperties.bullet[13].powerRadius = 0.15F;
        gunproperties.bullet[13].traceMesh = null;
        gunproperties.bullet[13].traceTrail = null;
        gunproperties.bullet[13].traceColor = 0;
        gunproperties.bullet[13].timeLife = 2.5F;
        gunproperties.bullet[14].massa = 0.095F;
        gunproperties.bullet[14].kalibr = 0.000404F;
        gunproperties.bullet[14].speed = 790F;
        gunproperties.bullet[14].power = 0.0045F;
        gunproperties.bullet[14].powerType = 0;
        gunproperties.bullet[14].powerRadius = 0.15F;
        gunproperties.bullet[14].traceMesh = null;
        gunproperties.bullet[14].traceTrail = null;
        gunproperties.bullet[14].traceColor = 0;
        gunproperties.bullet[14].timeLife = 2.5F;
        gunproperties.bullet[15].massa = 0.095F;
        gunproperties.bullet[15].kalibr = 0.000404F;
        gunproperties.bullet[15].speed = 790F;
        gunproperties.bullet[15].power = 0.0045F;
        gunproperties.bullet[15].powerType = 0;
        gunproperties.bullet[15].powerRadius = 0.15F;
        gunproperties.bullet[15].traceMesh = null;
        gunproperties.bullet[15].traceTrail = null;
        gunproperties.bullet[15].traceColor = 0;
        gunproperties.bullet[15].timeLife = 2.5F;
        gunproperties.bullet[16].massa = 0.096F;
        gunproperties.bullet[16].kalibr = 0.000268F;
        gunproperties.bullet[16].speed = 750F;
        gunproperties.bullet[16].power = 0.001F;
        gunproperties.bullet[16].powerType = 0;
        gunproperties.bullet[16].powerRadius = 0.0F;
        gunproperties.bullet[16].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        gunproperties.bullet[16].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[16].traceColor = 0xd9002eff;
        gunproperties.bullet[16].timeLife = 2.5F;
        gunproperties.bullet[17].massa = 0.095F;
        gunproperties.bullet[17].kalibr = 0.000404F;
        gunproperties.bullet[17].speed = 790F;
        gunproperties.bullet[17].power = 0.0045F;
        gunproperties.bullet[17].powerType = 0;
        gunproperties.bullet[17].powerRadius = 0.15F;
        gunproperties.bullet[17].traceMesh = null;
        gunproperties.bullet[17].traceTrail = null;
        gunproperties.bullet[17].traceColor = 0;
        gunproperties.bullet[17].timeLife = 2.5F;
        gunproperties.bullet[18].massa = 0.095F;
        gunproperties.bullet[18].kalibr = 0.000404F;
        gunproperties.bullet[18].speed = 790F;
        gunproperties.bullet[18].power = 0.0045F;
        gunproperties.bullet[18].powerType = 0;
        gunproperties.bullet[18].powerRadius = 0.15F;
        gunproperties.bullet[18].traceMesh = null;
        gunproperties.bullet[18].traceTrail = null;
        gunproperties.bullet[18].traceColor = 0;
        gunproperties.bullet[18].timeLife = 2.5F;
        gunproperties.bullet[19].massa = 0.095F;
        gunproperties.bullet[19].kalibr = 0.000404F;
        gunproperties.bullet[19].speed = 790F;
        gunproperties.bullet[19].power = 0.0045F;
        gunproperties.bullet[19].powerType = 0;
        gunproperties.bullet[19].powerRadius = 0.15F;
        gunproperties.bullet[19].traceMesh = null;
        gunproperties.bullet[19].traceTrail = null;
        gunproperties.bullet[19].traceColor = 0;
        gunproperties.bullet[19].timeLife = 2.5F;
        return gunproperties;
    }

    public int nextIndexBulletType()
    {
        int i = super.nextIndexBulletType();
        if(i % 4 == 0)
            i = (double)bulletNum > (double)bullets() * 0.80000000000000004D ? 16 : com.maddox.il2.ai.World.Rnd().nextInt(0, 4) * 4;
        return i;
    }
}
