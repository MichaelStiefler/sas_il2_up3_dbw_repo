// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MachineGunOerlikon20.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAntiAirGeneric, BulletAntiAirSmallUSSR, Bullet

public class MachineGunOerlikon20 extends com.maddox.il2.objects.weapons.MGunAntiAirGeneric
{

    public MachineGunOerlikon20()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 4389F;
        gunproperties.shotFreq = 7.5F;
        gunproperties.bulletsCluster = 2;
        gunproperties.sound = "weapon.zenitka_20";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 3.6F;
        bulletproperties.addExplTime = 1.5F;
        bulletproperties.power = 0.011F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 30F;
        bulletproperties.kalibr = 0.02F;
        bulletproperties.massa = 0.123F;
        bulletproperties.speed = 844F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0xb3ff0000;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.timeLife = 5.2F;
        bulletproperties.power = 0.0F;
        bulletproperties.kalibr = 0.02F;
        bulletproperties.massa = 0.122F;
        bulletproperties.speed = 844F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0xb3ff0000;
        return 70F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        return new BulletAntiAirSmallUSSR(i, gungeneric, loc, vector3d, l, explAddTimeT);
    }
}
