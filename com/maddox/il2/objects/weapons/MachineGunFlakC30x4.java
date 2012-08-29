// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MachineGunFlakC30x4.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAntiAirGeneric, BulletAntiAirSmallGermany, Bullet

public class MachineGunFlakC30x4 extends com.maddox.il2.objects.weapons.MGunAntiAirGeneric
{

    public MachineGunFlakC30x4()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.traceFreq = 1;
        gunproperties.aimMaxDist = 2450F;
        gunproperties.shotFreq = 18.66667F;
        gunproperties.bulletsCluster = 4;
        gunproperties.sound = "weapon.zenitka_20";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 4.45F;
        bulletproperties.addExplTime = 0.15F;
        bulletproperties.power = 0.055F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 50F;
        bulletproperties.kalibr = 0.02F;
        bulletproperties.massa = 0.148F;
        bulletproperties.speed = 800F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
        bulletproperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
        bulletproperties.traceColor = 0xd2ff0000;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.timeLife = 3F;
        bulletproperties.power = 0.0F;
        bulletproperties.kalibr = 0.02F;
        bulletproperties.massa = 0.134F;
        bulletproperties.speed = 835F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
        bulletproperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
        bulletproperties.traceColor = 0xd2ff0000;
        return 65F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(com.maddox.JGP.Vector3d vector3d, int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d1, long l)
    {
        return new BulletAntiAirSmallGermany(vector3d, i, gungeneric, loc, vector3d1, l, explAddTimeT);
    }
}
