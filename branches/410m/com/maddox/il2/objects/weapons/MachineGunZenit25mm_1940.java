// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MachineGunZenit25mm_1940.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAntiAirGeneric, BulletAntiAirSmallUSSR, Bullet

public class MachineGunZenit25mm_1940 extends com.maddox.il2.objects.weapons.MGunAntiAirGeneric
{

    public MachineGunZenit25mm_1940()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 2300F;
        gunproperties.shotFreq = 4F;
        gunproperties.bulletsCluster = 2;
        gunproperties.sound = "weapon.zenitka_37";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 4.45F;
        bulletproperties.addExplTime = 0.15F;
        bulletproperties.power = 0.1F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 55F;
        bulletproperties.kalibr = 0.025F;
        bulletproperties.massa = 0.288F;
        bulletproperties.speed = 910F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        bulletproperties.traceTrail = "3DO/Effects/Tracers/TrailThin.eff";
        bulletproperties.traceColor = 0xd9002eff;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.timeLife = 3F;
        bulletproperties.power = 0.0F;
        bulletproperties.kalibr = 0.025F;
        bulletproperties.massa = 0.295F;
        bulletproperties.speed = 900F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        bulletproperties.traceTrail = "3DO/Effects/Tracers/TrailThin.eff";
        bulletproperties.traceColor = 0xd9002eff;
        return 76.6F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(com.maddox.JGP.Vector3d vector3d, int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d1, long l)
    {
        return new BulletAntiAirSmallUSSR(vector3d, i, gungeneric, loc, vector3d1, l, explAddTimeT);
    }
}
