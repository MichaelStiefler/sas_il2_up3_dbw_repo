// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MachineGun2pdrMkVI.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAntiAirGeneric, BulletAntiAirSmallGermany, Bullet

public class MachineGun2pdrMkVI extends com.maddox.il2.objects.weapons.MGunAntiAirGeneric
{

    public MachineGun2pdrMkVI()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 4572F;
        gunproperties.shotFreq = 15.33333F;
        gunproperties.bulletsCluster = 4;
        gunproperties.sound = "weapon.zenitka_37";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 6.6F;
        bulletproperties.addExplTime = 1.2F;
        bulletproperties.power = 0.071F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 70F;
        bulletproperties.kalibr = 0.04F;
        bulletproperties.massa = 0.82F;
        bulletproperties.speed = 701F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmGreen/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0xb300ff00;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.timeLife = 6F;
        bulletproperties.addExplTime = 1.2F;
        bulletproperties.power = 0.071F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 70F;
        bulletproperties.kalibr = 0.04F;
        bulletproperties.massa = 0.91F;
        bulletproperties.speed = 585F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmGreen/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0xb300ff00;
        return 39F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(com.maddox.JGP.Vector3d vector3d, int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d1, long l)
    {
        return new BulletAntiAirSmallGermany(vector3d, i, gungeneric, loc, vector3d1, l, explAddTimeT);
    }
}
