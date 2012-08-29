// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunMadsen20.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunMadsen20 extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunMadsen20()
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
        gunproperties.smoke = null;
        gunproperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
        gunproperties.sound = "weapon.mgun_20_500";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = 3;
        gunproperties.maxDeltaAngle = 0.2F;
        gunproperties.shotFreq = 4.16667F;
        gunproperties.traceFreq = 2;
        gunproperties.bullets = 60;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.126F;
        gunproperties.bullet[0].kalibr = 0.000404F;
        gunproperties.bullet[0].speed = 840F;
        gunproperties.bullet[0].power = 0.01F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.15F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd200ffff;
        gunproperties.bullet[0].timeLife = 2.5F;
        gunproperties.bullet[1].massa = 0.154F;
        gunproperties.bullet[1].kalibr = 0.00032F;
        gunproperties.bullet[1].speed = 780F;
        gunproperties.bullet[1].power = 0.0F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.0F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = null;
        gunproperties.bullet[1].traceColor = 0;
        gunproperties.bullet[1].timeLife = 3F;
        return gunproperties;
    }
}
