// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonQFMkXII_120mm.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonAntiAirGeneric, BulletAntiAirBigGermany, Bullet

public class CannonQFMkXII_120mm extends com.maddox.il2.objects.weapons.CannonAntiAirGeneric
{

    public CannonQFMkXII_120mm()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 15545F;
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
        bulletproperties.kalibr = 0.12F;
        bulletproperties.massa = 23F;
        bulletproperties.speed = 808F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 0.9F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 122F;
        bulletproperties.kalibr = 0.12F;
        bulletproperties.massa = 22.7F;
        bulletproperties.speed = 808F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        return 38F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        return new BulletAntiAirBigGermany(i, gungeneric, loc, vector3d, l, explodeAtHeight);
    }
}
