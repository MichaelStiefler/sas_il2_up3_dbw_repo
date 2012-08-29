// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonUS_5_38Mk12.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonAntiAirGeneric, BulletAntiAirBigGermany, Bullet

public class CannonUS_5_38Mk12 extends com.maddox.il2.objects.weapons.CannonAntiAirGeneric
{

    public CannonUS_5_38Mk12()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 15903F;
        gunproperties.fireMesh = null;
        gunproperties.fire = "Effects/Bigship/GunFire150mm/Fire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "Effects/BigShip/GunFire150mm/Burst.eff";
        gunproperties.shells = null;
        gunproperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 13F;
        gunproperties.emitTime = 0.3F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 15F;
        bulletproperties.power = 3.4F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 136F;
        bulletproperties.kalibr = 0.127F;
        bulletproperties.massa = 25F;
        bulletproperties.speed = 792F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 0.9F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 122F;
        bulletproperties.kalibr = 0.127F;
        bulletproperties.massa = 24.5F;
        bulletproperties.speed = 792F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        return 38F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(com.maddox.JGP.Vector3d vector3d, int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d1, long l)
    {
        return new BulletAntiAirBigGermany(vector3d, i, gungeneric, loc, vector3d1, l, explodeAtHeight);
    }
}
