// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MachineGunBoforsUK40x2.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAntiAirGeneric, BulletAntiAirSmallGermany, Bullet

public class MachineGunBoforsUK40x2 extends com.maddox.il2.objects.weapons.MGunAntiAirGeneric
{

    public MachineGunBoforsUK40x2()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 9830F;
        gunproperties.shotFreq = 4F;
        gunproperties.bulletsCluster = 2;
        gunproperties.sound = "weapon.zenitka_37";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 7.7F;
        bulletproperties.addExplTime = 1.5F;
        bulletproperties.power = 0.092F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 90F;
        bulletproperties.kalibr = 0.04F;
        bulletproperties.massa = 0.894F;
        bulletproperties.speed = 829F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0xb30000ff;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.timeLife = 7.7F;
        bulletproperties.power = 0.092F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 90F;
        bulletproperties.kalibr = 0.04F;
        bulletproperties.massa = 0.894F;
        bulletproperties.speed = 829F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0xb30000ff;
        return 56.3F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(com.maddox.JGP.Vector3d vector3d, int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d1, long l)
    {
        return new BulletAntiAirSmallGermany(vector3d, i, gungeneric, loc, vector3d1, l, explAddTimeT);
    }
}
