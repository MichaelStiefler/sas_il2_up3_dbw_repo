// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunBredaSAFAT127re.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunBredaSAFAT127re extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunBredaSAFAT127re()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = false;
        gunproperties.fireMesh = "3DO/Effects/GunFire/12mm/mono.sim";
        gunproperties.fire = null;
        gunproperties.sprite = "3DO/Effects/GunFire/12mm/GunFlare.eff";
        gunproperties.smoke = null;
        gunproperties.shells = null;
        gunproperties.sound = "weapon.mgun_20_500";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 10F;
        gunproperties.emitR = 3F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = -1;
        gunproperties.maxDeltaAngle = 0.229F;
        gunproperties.shotFreq = 10F;
        gunproperties.traceFreq = 1;
        gunproperties.bullets = 500;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.037F;
        gunproperties.bullet[0].kalibr = 0.000125675F;
        gunproperties.bullet[0].speed = 770F;
        gunproperties.bullet[0].power = 0.00134F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = null;
        gunproperties.bullet[0].traceTrail = null;
        gunproperties.bullet[0].traceColor = 0;
        gunproperties.bullet[0].timeLife = 6.5F;
        gunproperties.bullet[1].massa = 0.037F;
        gunproperties.bullet[1].kalibr = 0.000125675F;
        gunproperties.bullet[1].speed = 770F;
        gunproperties.bullet[1].power = 0.00134F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.0F;
        gunproperties.bullet[1].traceMesh = "3DO/Effects/Tracers/20mmWhite/mono.sim";
        gunproperties.bullet[1].traceTrail = "Effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[1].traceColor = 0xd2ffffff;
        gunproperties.bullet[1].timeLife = 6.25F;
        gunproperties.bullet[2].massa = 0.0375F;
        gunproperties.bullet[2].kalibr = 0.000125675F;
        gunproperties.bullet[2].speed = 760F;
        gunproperties.bullet[2].power = 0.000825F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 0.15F;
        gunproperties.bullet[2].traceMesh = "3DO/Effects/Tracers/20mmWhite/mono.sim";
        gunproperties.bullet[2].traceTrail = null;
        gunproperties.bullet[2].traceColor = 0xd2ffffff;
        gunproperties.bullet[2].timeLife = 6.5F;
        return gunproperties;
    }
}
