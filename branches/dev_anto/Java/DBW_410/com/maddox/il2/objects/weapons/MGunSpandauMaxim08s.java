// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 16/05/2011 9:57:05 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MGunSpandauMaxim08s.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.*;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunSpandauMaxim08s extends MGunAircraftGeneric
{

    public MGunSpandauMaxim08s()
    {
    }

    public GunProperties createProperties()
    {
        GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = "3DO/Effects/GunFire/7mm/mono.sim";
        gunproperties.fire = null;
        gunproperties.sprite = "3DO/Effects/GunFire/7mm/GunFlare.eff";
        gunproperties.smoke = null;
        gunproperties.shells = "3DO/Effects/GunShells/WWIGunShells.eff";
        gunproperties.sound = "weapon.L5Gun_303";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 10F;
        gunproperties.emitR = 3F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = 1;
        gunproperties.maxDeltaAngle = 0.28F;
        gunproperties.shotFreq = 6.5F;
        gunproperties.traceFreq = 1;
        gunproperties.bullets = 500;
        gunproperties.bulletsCluster = 2;
        gunproperties.bullet = (new BulletProperties[] {
            new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.0122F;
        gunproperties.bullet[0].kalibr = 4.442131E-005F;
        gunproperties.bullet[0].speed = 840F;
        gunproperties.bullet[0].power = 0.002F;
        gunproperties.bullet[0].powerType = 1;
        gunproperties.bullet[0].powerRadius = 0.05F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmWhite/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd200ffff;
        gunproperties.bullet[0].timeLife = 1.8F;
        gunproperties.bullet[1].massa = 0.0122F;
        gunproperties.bullet[1].kalibr = 4.442131E-005F;
        gunproperties.bullet[1].speed = 840F;
        gunproperties.bullet[1].power = 0.0F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.05F;
        gunproperties.bullet[1].traceMesh = "3do/effects/tracers/20mmWhite/mono.sim";
        gunproperties.bullet[1].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[1].traceColor = 0xd200ffff;
        gunproperties.bullet[1].timeLife = 1.8F;
        return gunproperties;
    }
}