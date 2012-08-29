// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonJA_75_Type88.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonAntiAirGeneric, BulletAntiAirBigGermany, Bullet

public class CannonJA_75_Type88 extends com.maddox.il2.objects.weapons.CannonAntiAirGeneric
{

    public CannonJA_75_Type88()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 9000F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 22F;
        bulletproperties.power = 0.35F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 120F;
        bulletproperties.kalibr = 0.075F;
        bulletproperties.massa = 6.5F;
        bulletproperties.speed = 700F;
        bulletproperties.traceColor = 0;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.timeLife = 15F;
        bulletproperties.power = 0.7F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 25F;
        bulletproperties.kalibr = 0.075F;
        bulletproperties.massa = 6.8F;
        bulletproperties.speed = 650F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0xd2129cef;
        return 40F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        return new BulletAntiAirBigGermany(i, gungeneric, loc, vector3d, l, explodeAtHeight);
    }
}
