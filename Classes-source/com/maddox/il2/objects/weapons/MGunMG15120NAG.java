// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunMG15120NAG.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunMG15120NAG extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunMG15120NAG()
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
        gunproperties.sound = "weapon.MGunMG15120MGs";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 0.0F;
        gunproperties.emitR = 0.0F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = 3;
        gunproperties.maxDeltaAngle = 0.28F;
        gunproperties.shotFreq = 11.5F;
        gunproperties.traceFreq = 5;
        gunproperties.bullets = 250;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.092F;
        gunproperties.bullet[0].kalibr = 0.000404F;
        gunproperties.bullet[0].speed = 800F;
        gunproperties.bullet[0].power = 0.01425F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.2F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
        gunproperties.bullet[0].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
        gunproperties.bullet[0].traceColor = 0xd2ff0000;
        gunproperties.bullet[0].timeLife = 2.0F;
        gunproperties.bullet[1].massa = 0.092F;
        gunproperties.bullet[1].kalibr = 0.000404F;
        gunproperties.bullet[1].speed = 800F;
        gunproperties.bullet[1].power = 0.01425F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.2F;
        gunproperties.bullet[1].traceMesh = "3do/effects/tracers/20mmCyan/mono.sim";
        gunproperties.bullet[1].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
        gunproperties.bullet[1].traceColor = 0xd2ffff00;
        gunproperties.bullet[1].timeLife = 2.0F;
        gunproperties.bullet[2].massa = 0.092F;
        gunproperties.bullet[2].kalibr = 0.000404F;
        gunproperties.bullet[2].speed = 800F;
        gunproperties.bullet[2].power = 0.01425F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 0.2F;
        gunproperties.bullet[2].traceMesh = "3do/effects/tracers/20mmCyan/mono.sim";
        gunproperties.bullet[2].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
        gunproperties.bullet[2].traceColor = 0xd2ffff00;
        gunproperties.bullet[2].timeLife = 2.0F;
        gunproperties.bullet[3].massa = 0.115F;
        gunproperties.bullet[3].kalibr = 0.000404F;
        gunproperties.bullet[3].speed = 710F;
        gunproperties.bullet[3].power = 0.0037F;
        gunproperties.bullet[3].powerType = 0;
        gunproperties.bullet[3].powerRadius = 0.1F;
        gunproperties.bullet[3].traceMesh = "3do/effects/tracers/20mmCyan/mono.sim";
        gunproperties.bullet[3].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
        gunproperties.bullet[3].traceColor = 0xd2ffff00;
        gunproperties.bullet[3].timeLife = 3F;
        gunproperties.bullet[4].massa = 0.115F;
        gunproperties.bullet[4].kalibr = 0.00032F;
        gunproperties.bullet[4].speed = 710F;
        gunproperties.bullet[4].power = 0.0036F;
        gunproperties.bullet[4].powerType = 0;
        gunproperties.bullet[4].powerRadius = 0.0F;
        gunproperties.bullet[4].traceMesh = "3do/effects/tracers/20mmCyan/mono.sim";
        gunproperties.bullet[4].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
        gunproperties.bullet[4].traceColor = 0xd2ffff00;
        gunproperties.bullet[4].timeLife = 3.3F;
        return gunproperties;
    }
}
