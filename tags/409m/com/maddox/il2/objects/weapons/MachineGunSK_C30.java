// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MachineGunSK_C30.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonAntiAirGeneric, BulletAntiAirBigGermany, Bullet

public class MachineGunSK_C30 extends com.maddox.il2.objects.weapons.CannonAntiAirGeneric
{

    public MachineGunSK_C30()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 8500F;
        gunproperties.sound = "weapon.zenitka_37c";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 10.5F;
        bulletproperties.addExplTime = 1.5F;
        bulletproperties.power = 0.365F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 80F;
        bulletproperties.kalibr = 0.037F;
        bulletproperties.massa = 0.742F;
        bulletproperties.speed = 1000F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0xd2ff0000;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 0.0F;
        bulletproperties.kalibr = 0.037F;
        bulletproperties.massa = 0.742F;
        bulletproperties.speed = 1000F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0xd2129cef;
        return 83F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        return new BulletAntiAirBigGermany(i, gungeneric, loc, vector3d, l, explodeAtHeight);
    }
}
