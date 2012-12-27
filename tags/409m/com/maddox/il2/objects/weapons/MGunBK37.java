// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunBK37.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunBK37 extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunBK37()
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
        gunproperties.smoke = null;
        gunproperties.shells = null;
        gunproperties.sound = "weapon.air_cannon_37";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 10F;
        gunproperties.emitR = 3F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = -1;
        gunproperties.maxDeltaAngle = 0.11F;
        gunproperties.shotFreq = 1.25F;
        gunproperties.traceFreq = 1;
        gunproperties.bullets = 12;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.68F;
        gunproperties.bullet[0].kalibr = 0.00021904F;
        gunproperties.bullet[0].speed = 1170F;
        gunproperties.bullet[0].power = 0.0F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
        gunproperties.bullet[0].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
        gunproperties.bullet[0].traceColor = 0xd9002eff;
        gunproperties.bullet[0].timeLife = 15F;
        gunproperties.bullet[1].massa = 0.628F;
        gunproperties.bullet[1].kalibr = 0.00021904F;
        gunproperties.bullet[1].speed = 812F;
        gunproperties.bullet[1].power = 0.096F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 10F;
        gunproperties.bullet[1].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
        gunproperties.bullet[1].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
        gunproperties.bullet[1].traceColor = 0xd9002eff;
        gunproperties.bullet[1].timeLife = 15F;
        gunproperties.bullet[2].massa = 0.55F;
        gunproperties.bullet[2].kalibr = 0.00021904F;
        gunproperties.bullet[2].speed = 860F;
        gunproperties.bullet[2].power = 0.09F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 10F;
        gunproperties.bullet[2].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
        gunproperties.bullet[2].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
        gunproperties.bullet[2].traceColor = 0xd9002eff;
        gunproperties.bullet[2].timeLife = 15F;
        return gunproperties;
    }

    public void setConvDistance(float f, float f1)
    {
        super.setConvDistance(f, f1 - 1.2F);
    }
}