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
        gunproperties.sound = "weapon.mgun_20_700";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 10F;
        gunproperties.emitR = 3F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = 3;
        gunproperties.maxDeltaAngle = 0.07F;
        gunproperties.shotFreq = 10.91667F;
        gunproperties.traceFreq = 2;
        gunproperties.bullets = 120;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.096F;
        gunproperties.bullet[0].kalibr = 4.4E-005F;
        gunproperties.bullet[0].speed = 800F;
        gunproperties.bullet[0].power = 0.001F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd200ffff;
        gunproperties.bullet[0].timeLife = 2.5F;
        gunproperties.bullet[1].massa = 0.096F;
        gunproperties.bullet[1].kalibr = 4.4E-005F;
        gunproperties.bullet[1].speed = 800F;
        gunproperties.bullet[1].power = 0.011088F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.25F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = null;
        gunproperties.bullet[1].traceColor = 0;
        gunproperties.bullet[1].timeLife = 2.5F;
        gunproperties.bullet[2].massa = 0.096F;
        gunproperties.bullet[2].kalibr = 4.4E-005F;
        gunproperties.bullet[2].speed = 800F;
        gunproperties.bullet[2].power = 0.001F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 0.0F;
        gunproperties.bullet[2].traceMesh = "3do/effects/tracers/20mmGreenBlue/mono.sim";
        gunproperties.bullet[2].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[2].traceColor = 0xd494c476;
        gunproperties.bullet[2].timeLife = 2.5F;
        gunproperties.bullet[3].massa = 0.096F;
        gunproperties.bullet[3].kalibr = 4.4E-005F;
        gunproperties.bullet[3].speed = 800F;
        gunproperties.bullet[3].power = 0.011088F;
        gunproperties.bullet[3].powerType = 0;
        gunproperties.bullet[3].powerRadius = 0.25F;
        gunproperties.bullet[3].traceMesh = null;
        gunproperties.bullet[3].traceTrail = null;
        gunproperties.bullet[3].traceColor = 0;
        gunproperties.bullet[3].timeLife = 2.5F;
        gunproperties.bullet[4].massa = 0.096F;
        gunproperties.bullet[4].kalibr = 4.4E-005F;
        gunproperties.bullet[4].speed = 800F;
        gunproperties.bullet[4].power = 0.001F;
        gunproperties.bullet[4].powerType = 0;
        gunproperties.bullet[4].powerRadius = 0.0F;
        gunproperties.bullet[4].traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
        gunproperties.bullet[4].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[4].traceColor = 0xd2129cef;
        gunproperties.bullet[4].timeLife = 2.5F;
        gunproperties.bullet[5].massa = 0.096F;
        gunproperties.bullet[5].kalibr = 4.4E-005F;
        gunproperties.bullet[5].speed = 800F;
        gunproperties.bullet[5].power = 0.011088F;
        gunproperties.bullet[5].powerType = 0;
        gunproperties.bullet[5].powerRadius = 0.25F;
        gunproperties.bullet[5].traceMesh = null;
        gunproperties.bullet[5].traceTrail = null;
        gunproperties.bullet[5].traceColor = 0;
        gunproperties.bullet[5].timeLife = 2.5F;
        gunproperties.bullet[6].massa = 0.096F;
        gunproperties.bullet[6].kalibr = 4.4E-005F;
        gunproperties.bullet[6].speed = 800F;
        gunproperties.bullet[6].power = 0.001F;
        gunproperties.bullet[6].powerType = 0;
        gunproperties.bullet[6].powerRadius = 0.0F;
        gunproperties.bullet[6].traceMesh = "3do/effects/tracers/20mmWhite/mono.sim";
        gunproperties.bullet[6].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[6].traceColor = 0xd2ffffff;
        gunproperties.bullet[6].timeLife = 2.5F;
        gunproperties.bullet[7].massa = 0.096F;
        gunproperties.bullet[7].kalibr = 4.4E-005F;
        gunproperties.bullet[7].speed = 800F;
        gunproperties.bullet[7].power = 0.011088F;
        gunproperties.bullet[7].powerType = 0;
        gunproperties.bullet[7].powerRadius = 0.25F;
        gunproperties.bullet[7].traceMesh = null;
        gunproperties.bullet[7].traceTrail = null;
        gunproperties.bullet[7].traceColor = 0;
        gunproperties.bullet[7].timeLife = 2.5F;
        gunproperties.bullet[8].massa = 0.096F;
        gunproperties.bullet[8].kalibr = 4.4E-005F;
        gunproperties.bullet[8].speed = 800F;
        gunproperties.bullet[8].power = 0.001F;
        gunproperties.bullet[8].powerType = 0;
        gunproperties.bullet[8].powerRadius = 0.0F;
        gunproperties.bullet[8].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        gunproperties.bullet[8].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[8].traceColor = 0xd9002eff;
        gunproperties.bullet[8].timeLife = 2.5F;
        gunproperties.bullet[9].massa = 0.096F;
        gunproperties.bullet[9].kalibr = 4.4E-005F;
        gunproperties.bullet[9].speed = 800F;
        gunproperties.bullet[9].power = 0.024288F;
        gunproperties.bullet[9].powerType = 0;
        gunproperties.bullet[9].powerRadius = 0.25F;
        gunproperties.bullet[9].traceMesh = null;
        gunproperties.bullet[9].traceTrail = null;
        gunproperties.bullet[9].traceColor = 0;
        gunproperties.bullet[9].timeLife = 2.5F;
        return gunproperties;
    }

    public int nextIndexBulletType()
    {
        int i = super.nextIndexBulletType();
        if(i % 2 == 0)
            i = (double)bulletNum <= (double)bullets() * 0.80000000000000004D ? com.maddox.il2.ai.World.Rnd().nextInt(0, 4) * 2 : 8;
        return i;
    }
}
