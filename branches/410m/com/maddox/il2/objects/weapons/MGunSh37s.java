// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunSh37s.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunSh37s extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunSh37s()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = true;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = null;
        gunproperties.fire = "3DO/Effects/GunFire/37mm/GunFire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "effects/smokes/CannonTank.eff";
        gunproperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
        gunproperties.sound = "weapon.air_cannon_37";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 10F;
        gunproperties.emitR = 3F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = -1;
        gunproperties.maxDeltaAngle = 0.42F;
        gunproperties.shotFreq = 3.333333F;
        gunproperties.traceFreq = 1;
        gunproperties.bullets = 20;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.77F;
        gunproperties.bullet[0].kalibr = 0.0006845F;
        gunproperties.bullet[0].speed = 890F;
        gunproperties.bullet[0].power = 0.0F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 1.5F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd9002eff;
        gunproperties.bullet[0].timeLife = 2.0F;
        gunproperties.bullet[1].massa = 0.73F;
        gunproperties.bullet[1].kalibr = 0.0006845F;
        gunproperties.bullet[1].speed = 890F;
        gunproperties.bullet[1].power = 0.02842F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 3.5F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = "3DO/Effects/Tracers/TrailThin.eff";
        gunproperties.bullet[1].traceColor = 0x1010101;
        gunproperties.bullet[1].timeLife = 3F;
        gunproperties.bullet[2].massa = 0.73F;
        gunproperties.bullet[2].kalibr = 0.0006845F;
        gunproperties.bullet[2].speed = 890F;
        gunproperties.bullet[2].power = 0.02842F;
        gunproperties.bullet[2].powerType = 1;
        gunproperties.bullet[2].powerRadius = 5.7F;
        gunproperties.bullet[2].traceMesh = "3do/effects/tracers/20mmGreenBlue/mono.sim";
        gunproperties.bullet[2].traceTrail = "3DO/Effects/Tracers/TrailThin.eff";
        gunproperties.bullet[2].traceColor = 0xd494c476;
        gunproperties.bullet[2].timeLife = 2.0F;
        return gunproperties;
    }
}
