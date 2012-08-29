// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunUBs.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunUBs extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunUBs()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = "3DO/Effects/GunFire/12mm/mono.sim";
        gunproperties.fire = null;
        gunproperties.sprite = "3DO/Effects/GunFire/12mm/GunFlare.eff";
        gunproperties.smoke = null;
        gunproperties.shells = "3DO/Effects/GunShells/GunShells.eff";
        gunproperties.sound = "weapon.mgun_13_800";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 10F;
        gunproperties.emitR = 3F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = 3;
        gunproperties.maxDeltaAngle = 0.1F;
        gunproperties.shotFreq = 15F;
        gunproperties.traceFreq = 4;
        gunproperties.bullets = 150;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.0448F;
        gunproperties.bullet[0].kalibr = 0.0001209675F;
        gunproperties.bullet[0].speed = 850F;
        gunproperties.bullet[0].power = 0.001F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmWhite/mono.sim";
        gunproperties.bullet[0].traceTrail = null;
        gunproperties.bullet[0].traceColor = 0xd2ffffff;
        gunproperties.bullet[0].timeLife = 3F;
        gunproperties.bullet[1].massa = 0.051F;
        gunproperties.bullet[1].kalibr = 0.0001209675F;
        gunproperties.bullet[1].speed = 850F;
        gunproperties.bullet[1].power = 0.0F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.0F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = null;
        gunproperties.bullet[1].traceColor = 0;
        gunproperties.bullet[1].timeLife = 3F;
        gunproperties.bullet[2].massa = 0.045F;
        gunproperties.bullet[2].kalibr = 0.0001258062F;
        gunproperties.bullet[2].speed = 850F;
        gunproperties.bullet[2].power = 0.002322F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 0.1F;
        gunproperties.bullet[2].traceMesh = null;
        gunproperties.bullet[2].traceTrail = null;
        gunproperties.bullet[2].traceColor = 0;
        gunproperties.bullet[2].timeLife = 2.0F;
        gunproperties.bullet[3].massa = 0.045F;
        gunproperties.bullet[3].kalibr = 0.0001258062F;
        gunproperties.bullet[3].speed = 850F;
        gunproperties.bullet[3].power = 0.002322F;
        gunproperties.bullet[3].powerType = 0;
        gunproperties.bullet[3].powerRadius = 0.1F;
        gunproperties.bullet[3].traceMesh = null;
        gunproperties.bullet[3].traceTrail = null;
        gunproperties.bullet[3].traceColor = 0;
        gunproperties.bullet[3].timeLife = 2.0F;
        return gunproperties;
    }
}
