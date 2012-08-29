// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunBrowning303k_NoTracers.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunBrowning303k_NoTracers extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunBrowning303k_NoTracers()
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
        gunproperties.sound = "weapon.mgun_07_900";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 0.0F;
        gunproperties.emitR = 3F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = 1;
        gunproperties.shotFreq = 16.66667F;
        gunproperties.maxDeltaAngle = 0.44F;
        gunproperties.shotFreqDeviation = 0.04F;
        gunproperties.traceFreq = 0;
        gunproperties.bullets = 500;
        gunproperties.bulletsCluster = 2;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.01066849F;
        gunproperties.bullet[0].kalibr = 4.442131E-005F;
        gunproperties.bullet[0].speed = 835F;
        gunproperties.bullet[0].power = 0.0018F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = null;
        gunproperties.bullet[0].traceTrail = null;
        gunproperties.bullet[0].traceColor = 0;
        gunproperties.bullet[0].timeLife = 1.0F;
        gunproperties.bullet[1].massa = 0.01066849F;
        gunproperties.bullet[1].kalibr = 4.442131E-005F;
        gunproperties.bullet[1].speed = 835F;
        gunproperties.bullet[1].power = 0.0F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.0F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = null;
        gunproperties.bullet[1].traceColor = 0;
        gunproperties.bullet[1].timeLife = 1.0F;
        gunproperties.bullet[2].massa = 0.01066849F;
        gunproperties.bullet[2].kalibr = 4.442131E-005F;
        gunproperties.bullet[2].speed = 835F;
        gunproperties.bullet[2].power = 0.0F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 0.0F;
        gunproperties.bullet[2].traceMesh = null;
        gunproperties.bullet[2].traceTrail = null;
        gunproperties.bullet[2].traceColor = 0;
        gunproperties.bullet[2].timeLife = 1.0F;
        gunproperties.bullet[3].massa = 0.01066849F;
        gunproperties.bullet[3].kalibr = 4.442131E-005F;
        gunproperties.bullet[3].speed = 835F;
        gunproperties.bullet[3].power = 0.0018F;
        gunproperties.bullet[3].powerType = 0;
        gunproperties.bullet[3].powerRadius = 0.0F;
        gunproperties.bullet[3].traceMesh = null;
        gunproperties.bullet[3].traceTrail = null;
        gunproperties.bullet[3].traceColor = 0;
        gunproperties.bullet[3].timeLife = 1.0F;
        gunproperties.bullet[4].massa = 0.01066849F;
        gunproperties.bullet[4].kalibr = 4.442131E-005F;
        gunproperties.bullet[4].speed = 835F;
        gunproperties.bullet[4].power = 0.0018F;
        gunproperties.bullet[4].powerType = 0;
        gunproperties.bullet[4].powerRadius = 0.0F;
        gunproperties.bullet[4].traceMesh = null;
        gunproperties.bullet[4].traceTrail = null;
        gunproperties.bullet[4].traceColor = 0;
        gunproperties.bullet[4].timeLife = 1.0F;
        gunproperties.bullet[5].massa = 0.01066849F;
        gunproperties.bullet[5].kalibr = 4.442131E-005F;
        gunproperties.bullet[5].speed = 835F;
        gunproperties.bullet[5].power = 0.0018F;
        gunproperties.bullet[5].powerType = 0;
        gunproperties.bullet[5].powerRadius = 0.0F;
        gunproperties.bullet[5].traceMesh = null;
        gunproperties.bullet[5].traceTrail = null;
        gunproperties.bullet[5].traceColor = 0;
        gunproperties.bullet[5].timeLife = 1.0F;
        return gunproperties;
    }
}
