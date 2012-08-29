// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Cannon21K.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonAntiAirGeneric, BulletAntiAirBigUSSR, Bullet

public class Cannon21K extends com.maddox.il2.objects.weapons.CannonAntiAirGeneric
{

    public Cannon21K()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 9200F;
        gunproperties.sound = "weapon.zenitka_37c";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 10.5F;
        bulletproperties.addExplTime = 1.5F;
        bulletproperties.power = 0.052F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 60F;
        bulletproperties.kalibr = 0.045F;
        bulletproperties.massa = 1.065F;
        bulletproperties.speed = 880F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0xd2ff0000;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 0.074F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 80F;
        bulletproperties.kalibr = 0.045F;
        bulletproperties.massa = 1.41F;
        bulletproperties.speed = 760F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0xd2129cef;
        return 46F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        return new BulletAntiAirBigUSSR(i, gungeneric, loc, vector3d, l, explodeAtHeight);
    }
}
