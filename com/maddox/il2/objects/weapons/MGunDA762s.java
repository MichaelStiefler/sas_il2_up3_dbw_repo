// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunDA762s.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunDA762s extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunDA762s()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = "3DO/Effects/GunFire/7mm/mono.sim";
        gunproperties.fire = null;
        gunproperties.sprite = "3DO/Effects/GunFire/7mm/GunFlare.eff";
        gunproperties.smoke = null;
        gunproperties.shells = "3DO/Effects/GunShells/GunShells.eff";
        gunproperties.sound = "weapon.mgun_07_1500";
        gunproperties.emitColor = new Color3f(1.0F, 0.8F, 0.5F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 3F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1500F;
        gunproperties.weaponType = 1;
        gunproperties.maxDeltaAngle = 0.22F;
        gunproperties.shotFreq = 9.166667F;
        gunproperties.traceFreq = 5;
        gunproperties.bullets = 750;
        gunproperties.bulletsCluster = 2;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.0096F;
        gunproperties.bullet[0].kalibr = 2.845156E-005F;
        gunproperties.bullet[0].speed = 848F;
        gunproperties.bullet[0].power = 0.0005F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmGreenBlue/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd294c476;
        gunproperties.bullet[0].timeLife = 4.5F;
        gunproperties.bullet[1].massa = 0.0096F;
        gunproperties.bullet[1].kalibr = 2.845156E-005F;
        gunproperties.bullet[1].speed = 848F;
        gunproperties.bullet[1].power = 0.0005F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.0F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = null;
        gunproperties.bullet[1].traceColor = 0;
        gunproperties.bullet[1].timeLife = 3.2F;
        gunproperties.bullet[2].massa = 0.0096F;
        gunproperties.bullet[2].kalibr = 2.845156E-005F;
        gunproperties.bullet[2].speed = 848F;
        gunproperties.bullet[2].power = 0.0F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 0.0F;
        gunproperties.bullet[2].traceMesh = null;
        gunproperties.bullet[2].traceTrail = null;
        gunproperties.bullet[2].traceColor = 0;
        gunproperties.bullet[2].timeLife = 2.6F;
        gunproperties.bullet[3].massa = 0.0096F;
        gunproperties.bullet[3].kalibr = 2.845156E-005F;
        gunproperties.bullet[3].speed = 848F;
        gunproperties.bullet[3].power = 0.0F;
        gunproperties.bullet[3].powerType = 0;
        gunproperties.bullet[3].powerRadius = 0.0F;
        gunproperties.bullet[3].traceMesh = null;
        gunproperties.bullet[3].traceTrail = null;
        gunproperties.bullet[3].traceColor = 0;
        gunproperties.bullet[3].timeLife = 2.6F;
        gunproperties.bullet[4].massa = 0.0096F;
        gunproperties.bullet[4].kalibr = 2.845156E-005F;
        gunproperties.bullet[4].speed = 848F;
        gunproperties.bullet[4].power = 0.0F;
        gunproperties.bullet[4].powerType = 0;
        gunproperties.bullet[4].powerRadius = 0.0F;
        gunproperties.bullet[4].traceMesh = null;
        gunproperties.bullet[4].traceTrail = null;
        gunproperties.bullet[4].traceColor = 0;
        gunproperties.bullet[4].timeLife = 2.6F;
        return gunproperties;
    }
}
