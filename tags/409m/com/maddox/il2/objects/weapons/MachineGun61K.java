// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MachineGun61K.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAntiAirGeneric, BulletAntiAirSmallUSSR, Bullet

public class MachineGun61K extends com.maddox.il2.objects.weapons.MGunAntiAirGeneric
{

    public MachineGun61K()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 4000F;
        gunproperties.shotFreq = 2.75F;
        gunproperties.bulletsCluster = 2;
        gunproperties.sound = "weapon.zenitka_37";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 10.5F;
        bulletproperties.addExplTime = 1.5F;
        bulletproperties.power = 0.35F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 80F;
        bulletproperties.kalibr = 0.037F;
        bulletproperties.massa = 0.732F;
        bulletproperties.speed = 880F;
        bulletproperties.traceColor = 0;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.timeLife = 5F;
        bulletproperties.power = 0.0F;
        bulletproperties.kalibr = 0.037F;
        bulletproperties.massa = 0.758F;
        bulletproperties.speed = 880F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0xd2129cef;
        return 62.6F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        return new BulletAntiAirSmallUSSR(i, gungeneric, loc, vector3d, l, explAddTimeT);
    }
}
