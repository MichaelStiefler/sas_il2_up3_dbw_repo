// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunMG81s.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunMG81s extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunMG81s()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = false;
        gunproperties.fireMesh = "3DO/Effects/GunFire/7mm/mono.sim";
        gunproperties.fire = null;
        gunproperties.sprite = "3DO/Effects/GunFire/7mm/GunFlare.eff";
        gunproperties.smoke = null;
        gunproperties.shells = null;
        gunproperties.sound = "weapon.MGunMG81s";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = 1;
        gunproperties.maxDeltaAngle = 0.35F;
        gunproperties.shotFreq = 26.66667F;
        gunproperties.traceFreq = 2;
        gunproperties.bullets = 500;
        gunproperties.bulletsCluster = 2;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), 
            new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.0115F;
        gunproperties.bullet[0].kalibr = 2.900001E-005F;
        gunproperties.bullet[0].speed = 765F;
        gunproperties.bullet[0].power = 0.0F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = null;
        gunproperties.bullet[0].traceTrail = null;
        gunproperties.bullet[0].traceColor = 0;
        gunproperties.bullet[0].timeLife = 1.5F;
        gunproperties.bullet[1].massa = 0.0115F;
        gunproperties.bullet[1].kalibr = 2.900001E-005F;
        gunproperties.bullet[1].speed = 765F;
        gunproperties.bullet[1].power = 0.0F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.0F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = null;
        gunproperties.bullet[1].traceColor = 0;
        gunproperties.bullet[1].timeLife = 1.5F;
        gunproperties.bullet[2].massa = 0.0097F;
        gunproperties.bullet[2].kalibr = 2.900001E-005F;
        gunproperties.bullet[2].speed = 800F;
        gunproperties.bullet[2].power = 0.0002F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 0.0F;
        gunproperties.bullet[2].traceMesh = "3do/effects/tracers/20mmWhite/mono.sim";
        gunproperties.bullet[2].traceTrail = null;
        gunproperties.bullet[2].traceColor = 0xd2ffffff;
        gunproperties.bullet[2].timeLife = 1.0F;
        gunproperties.bullet[3].massa = 0.0097F;
        gunproperties.bullet[3].kalibr = 2.900001E-005F;
        gunproperties.bullet[3].speed = 800F;
        gunproperties.bullet[3].power = 0.0002F;
        gunproperties.bullet[3].powerType = 0;
        gunproperties.bullet[3].powerRadius = 0.0F;
        gunproperties.bullet[3].traceMesh = "3do/effects/tracers/20mmWhite/mono.sim";
        gunproperties.bullet[3].traceTrail = null;
        gunproperties.bullet[3].traceColor = 0xd2ffffff;
        gunproperties.bullet[3].timeLife = 1.0F;
        gunproperties.bullet[4].massa = 0.0101F;
        gunproperties.bullet[4].kalibr = 2.900001E-005F;
        gunproperties.bullet[4].speed = 795F;
        gunproperties.bullet[4].power = 0.0005F;
        gunproperties.bullet[4].powerType = 0;
        gunproperties.bullet[4].powerRadius = 0.0F;
        gunproperties.bullet[4].traceMesh = null;
        gunproperties.bullet[4].traceTrail = null;
        gunproperties.bullet[4].traceColor = 0;
        gunproperties.bullet[4].timeLife = 1.3F;
        gunproperties.bullet[5].massa = 0.0101F;
        gunproperties.bullet[5].kalibr = 2.900001E-005F;
        gunproperties.bullet[5].speed = 795F;
        gunproperties.bullet[5].power = 0.0005F;
        gunproperties.bullet[5].powerType = 0;
        gunproperties.bullet[5].powerRadius = 0.0F;
        gunproperties.bullet[5].traceMesh = null;
        gunproperties.bullet[5].traceTrail = null;
        gunproperties.bullet[5].traceColor = 0;
        gunproperties.bullet[5].timeLife = 1.3F;
        gunproperties.bullet[6].massa = 0.0115F;
        gunproperties.bullet[6].kalibr = 2.900001E-005F;
        gunproperties.bullet[6].speed = 765F;
        gunproperties.bullet[6].power = 0.0F;
        gunproperties.bullet[6].powerType = 0;
        gunproperties.bullet[6].powerRadius = 0.0F;
        gunproperties.bullet[6].traceMesh = null;
        gunproperties.bullet[6].traceTrail = null;
        gunproperties.bullet[6].traceColor = 0;
        gunproperties.bullet[6].timeLife = 1.5F;
        gunproperties.bullet[7].massa = 0.0115F;
        gunproperties.bullet[7].kalibr = 2.900001E-005F;
        gunproperties.bullet[7].speed = 765F;
        gunproperties.bullet[7].power = 0.0F;
        gunproperties.bullet[7].powerType = 0;
        gunproperties.bullet[7].powerRadius = 0.0F;
        gunproperties.bullet[7].traceMesh = null;
        gunproperties.bullet[7].traceTrail = null;
        gunproperties.bullet[7].traceColor = 0;
        gunproperties.bullet[7].timeLife = 1.5F;
        gunproperties.bullet[8].massa = 0.0097F;
        gunproperties.bullet[8].kalibr = 2.900001E-005F;
        gunproperties.bullet[8].speed = 800F;
        gunproperties.bullet[8].power = 0.0002F;
        gunproperties.bullet[8].powerType = 0;
        gunproperties.bullet[8].powerRadius = 0.0F;
        gunproperties.bullet[8].traceMesh = "3do/effects/tracers/20mmWhite/mono.sim";
        gunproperties.bullet[8].traceTrail = null;
        gunproperties.bullet[8].traceColor = 0xd2ffffff;
        gunproperties.bullet[8].timeLife = 1.0F;
        gunproperties.bullet[9].massa = 0.0097F;
        gunproperties.bullet[9].kalibr = 2.900001E-005F;
        gunproperties.bullet[9].speed = 800F;
        gunproperties.bullet[9].power = 0.0002F;
        gunproperties.bullet[9].powerType = 0;
        gunproperties.bullet[9].powerRadius = 0.0F;
        gunproperties.bullet[9].traceMesh = "3do/effects/tracers/20mmWhite/mono.sim";
        gunproperties.bullet[9].traceTrail = null;
        gunproperties.bullet[9].traceColor = 0xd2ffffff;
        gunproperties.bullet[9].timeLife = 1.0F;
        gunproperties.bullet[10].massa = 0.0101F;
        gunproperties.bullet[10].kalibr = 2.900001E-005F;
        gunproperties.bullet[10].speed = 795F;
        gunproperties.bullet[10].power = 0.0005F;
        gunproperties.bullet[10].powerType = 0;
        gunproperties.bullet[10].powerRadius = 0.0F;
        gunproperties.bullet[10].traceMesh = null;
        gunproperties.bullet[10].traceTrail = null;
        gunproperties.bullet[10].traceColor = 0;
        gunproperties.bullet[10].timeLife = 1.3F;
        gunproperties.bullet[11].massa = 0.0108F;
        gunproperties.bullet[11].kalibr = 2.900001E-005F;
        gunproperties.bullet[11].speed = 855F;
        gunproperties.bullet[11].power = 0.000375F;
        gunproperties.bullet[11].powerType = 0;
        gunproperties.bullet[11].powerRadius = 0.01F;
        gunproperties.bullet[11].traceMesh = null;
        gunproperties.bullet[11].traceTrail = null;
        gunproperties.bullet[11].traceColor = 0;
        gunproperties.bullet[11].timeLife = 1.3F;
        return gunproperties;
    }
}
