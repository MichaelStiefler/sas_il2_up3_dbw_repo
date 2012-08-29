// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MGunMolins_57AP.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunMolins_57AP extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunMolins_57AP()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = true;
        gunproperties.bUseHookAsRel = false;
        gunproperties.fireMesh = null;
        gunproperties.fire = "3DO/Effects/GunFire/45mm/GunFire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = null;
        gunproperties.shells = null;
        gunproperties.sound = "weapon.air_cannon_45";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 1.5F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = -1;
        gunproperties.maxDeltaAngle = 0.0F;
        gunproperties.shotFreq = 1.25F;
        gunproperties.traceFreq = 1;
        gunproperties.bullets = 21;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties()
        });
        gunproperties.bullet[0].massa = 2.8F;
        gunproperties.bullet[0].kalibr = 0.00182555F;
        gunproperties.bullet[0].speed = 890F;
        gunproperties.bullet[0].power = 0.0F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd9002eff;
        gunproperties.bullet[0].timeLife = 10F;
        return gunproperties;
    }
}
