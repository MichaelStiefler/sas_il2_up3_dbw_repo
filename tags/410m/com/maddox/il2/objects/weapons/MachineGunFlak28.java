// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MachineGunFlak28.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAntiAirGeneric, BulletAntiAirSmallGermany, Bullet

public class MachineGunFlak28 extends com.maddox.il2.objects.weapons.MGunAntiAirGeneric
{

    public MachineGunFlak28()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 9600F;
        gunproperties.shotFreq = 2.0F;
        gunproperties.bulletsCluster = 1;
        gunproperties.sound = "weapon.zenitka_37";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 10.5F;
        bulletproperties.addExplTime = 1.5F;
        bulletproperties.power = 0.303F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 85F;
        bulletproperties.kalibr = 0.04F;
        bulletproperties.massa = 0.955F;
        bulletproperties.speed = 854F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmCyan/mono.sim";
        bulletproperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
        bulletproperties.traceColor = 0xd2ffff00;
        bulletproperties.traceColor = 0;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.timeLife = 12F;
        bulletproperties.power = 0.0F;
        bulletproperties.kalibr = 0.04F;
        bulletproperties.massa = 0.955F;
        bulletproperties.speed = 854F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmCyan/mono.sim";
        bulletproperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
        bulletproperties.traceColor = 0xd2ffff00;
        return 56F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(com.maddox.JGP.Vector3d vector3d, int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d1, long l)
    {
        return new BulletAntiAirSmallGermany(vector3d, i, gungeneric, loc, vector3d1, l, explAddTimeT);
    }
}
