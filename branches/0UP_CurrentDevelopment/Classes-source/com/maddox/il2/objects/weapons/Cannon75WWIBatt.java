// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Cannon75WWIBatt.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonAntiAirGeneric, BulletAntiAirBigGermany, Bullet

public class Cannon75WWIBatt extends com.maddox.il2.objects.weapons.CannonAntiAirGeneric
{

    public Cannon75WWIBatt()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.maxDeltaAngle = 20F;
        gunproperties.aimMinDist = 200F;
        gunproperties.aimMaxDist = 6000F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 22F;
        bulletproperties.power = 0.015F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 5F;
        bulletproperties.kalibr = 0.0075F;
        bulletproperties.massa = 0.01F;
        bulletproperties.speed = 500F;
        bulletproperties.traceColor = 0;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.timeLife = 15F;
        bulletproperties.power = 0.015F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 5F;
        bulletproperties.kalibr = 0.0075F;
        bulletproperties.massa = 0.01F;
        bulletproperties.speed = 500F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0xd2129cef;
        return 40F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(com.maddox.JGP.Vector3d vector3d, int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d1, long l)
    {
        return new BulletAntiAirBigGermany(vector3d, i, gungeneric, loc, vector3d1, l, explodeAtHeight);
    }
}
