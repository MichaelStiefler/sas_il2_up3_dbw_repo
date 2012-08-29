// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MGunNS23ki.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunVYas

public class MGunNS23ki extends com.maddox.il2.objects.weapons.MGunVYas
{

    public MGunNS23ki()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = null;
        gunproperties.fire = "3DO/Effects/GunFire/30mm/GunFire.eff";
        gunproperties.sprite = "3DO/Effects/GunFire/30mm/GunFlare.eff";
        gunproperties.smoke = "effects/smokes/MachineGun.eff";
        gunproperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
        gunproperties.sound = "weapon.mgun_20_700";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 10F;
        gunproperties.emitR = 3F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = 3;
        gunproperties.maxDeltaAngle = 0.28F;
        gunproperties.shotFreqDeviation = 0.03F;
        gunproperties.shotFreq = 9.2F;
        gunproperties.traceFreq = 3;
        gunproperties.bullets = 120;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.195F;
        gunproperties.bullet[0].kalibr = 0.0003174F;
        gunproperties.bullet[0].speed = 690F;
        gunproperties.bullet[0].power = 0.0151632F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 1.0F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd9002eff;
        gunproperties.bullet[0].timeLife = 4F;
        gunproperties.bullet[1].massa = 0.195F;
        gunproperties.bullet[1].kalibr = 0.0003174F;
        gunproperties.bullet[1].speed = 690F;
        gunproperties.bullet[1].power = 0.0151632F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 1.0F;
        gunproperties.bullet[1].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        gunproperties.bullet[1].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[1].traceColor = 0xd9002eff;
        gunproperties.bullet[1].timeLife = 4F;
        gunproperties.bullet[2].massa = 0.201F;
        gunproperties.bullet[2].kalibr = 0.0003174F;
        gunproperties.bullet[2].speed = 690F;
        gunproperties.bullet[2].power = 0.008F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 0.0F;
        gunproperties.bullet[2].traceMesh = null;
        gunproperties.bullet[2].traceTrail = null;
        gunproperties.bullet[2].traceColor = 0;
        gunproperties.bullet[2].timeLife = 4F;
        return gunproperties;
    }
}
