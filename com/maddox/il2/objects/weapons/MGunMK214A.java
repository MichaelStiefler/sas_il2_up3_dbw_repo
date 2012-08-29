// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunMK214A.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunMK214A extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunMK214A()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = true;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = null;
        gunproperties.fire = "3DO/Effects/GunFire/45mm/GunFire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = null;
        gunproperties.shells = null;
        gunproperties.sound = "weapon.air_cannon_75";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.5F);
        gunproperties.emitI = 20F;
        gunproperties.emitR = 6F;
        gunproperties.emitTime = 0.06F;
        gunproperties.aimMinDist = 100F;
        gunproperties.aimMaxDist = 6000F;
        gunproperties.weaponType = 2;
        gunproperties.maxDeltaAngle = 0.11F;
        gunproperties.shotFreq = 2.666667F;
        gunproperties.traceFreq = 1;
        gunproperties.bullets = 21;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 1.973F;
        gunproperties.bullet[0].kalibr = 0.0029F;
        gunproperties.bullet[0].speed = 1167F;
        gunproperties.bullet[0].power = 0.0F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd2129cef;
        gunproperties.bullet[0].timeLife = 60F;
        gunproperties.bullet[1].massa = 1.519F;
        gunproperties.bullet[1].kalibr = 0.0029F;
        gunproperties.bullet[1].speed = 1100F;
        gunproperties.bullet[1].power = 0.45F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 45F;
        gunproperties.bullet[1].traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
        gunproperties.bullet[1].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[1].traceColor = 0xd2129cef;
        gunproperties.bullet[1].timeLife = 60F;
        return gunproperties;
    }

    public void setConvDistance(float f, float f1)
    {
        super.setConvDistance(f, f1 - 0.0F);
    }
}
