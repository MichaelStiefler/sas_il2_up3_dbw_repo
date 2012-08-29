// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonQFMkI_134mm.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonAntiAirGeneric, BulletAntiAirBigGermany, Bullet

public class CannonQFMkI_134mm extends com.maddox.il2.objects.weapons.CannonAntiAirGeneric
{

    public CannonQFMkI_134mm()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 21397F;
        gunproperties.fireMesh = null;
        gunproperties.fire = "Effects/Bigship/GunFire150mm/Fire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "Effects/BigShip/GunFire150mm/Burst.eff";
        gunproperties.shells = null;
        gunproperties.sound = "weapon.Cannon100";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 18.85F;
        bulletproperties.power = 1.47F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 133.4F;
        bulletproperties.kalibr = 0.134F;
        bulletproperties.massa = 36.3F;
        bulletproperties.speed = 792F;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 0.1F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 40F;
        bulletproperties.kalibr = 0.134F;
        bulletproperties.massa = 36.3F;
        bulletproperties.speed = 792F;
        return 50F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        return new BulletAntiAirBigGermany(i, gungeneric, loc, vector3d, l, explodeAtHeight);
    }
}
