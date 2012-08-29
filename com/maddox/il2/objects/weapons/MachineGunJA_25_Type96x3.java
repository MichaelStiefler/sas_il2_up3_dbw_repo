// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MachineGunJA_25_Type96x3.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAntiAirGeneric, BulletAntiAirSmallUSSR, Bullet

public class MachineGunJA_25_Type96x3 extends com.maddox.il2.objects.weapons.MGunAntiAirGeneric
{

    public MachineGunJA_25_Type96x3()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 7500F;
        gunproperties.shotFreq = 13F;
        gunproperties.bulletsCluster = 3;
        gunproperties.sound = "weapon.zenitka_20";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 6.1F;
        bulletproperties.addExplTime = 1.5F;
        bulletproperties.power = 0.01F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 30F;
        bulletproperties.kalibr = 0.025F;
        bulletproperties.massa = 0.24F;
        bulletproperties.speed = 900F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0xb300ffff;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.timeLife = 8.3F;
        bulletproperties.power = 0.0F;
        bulletproperties.kalibr = 0.025F;
        bulletproperties.massa = 0.26F;
        bulletproperties.speed = 900F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0xb300ffff;
        return 60F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        return ((com.maddox.il2.objects.weapons.Bullet) (new BulletAntiAirSmallUSSR(i, gungeneric, loc, vector3d, l, explAddTimeT)));
    }
}
