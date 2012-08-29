// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonFlak18_88mm.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonAntiAirGeneric, BulletAntiAirBigGermany, Bullet

public class CannonFlak18_88mm extends com.maddox.il2.objects.weapons.CannonAntiAirGeneric
{

    public CannonFlak18_88mm()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 11000F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 20F;
        bulletproperties.power = 0.87F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 170F;
        bulletproperties.kalibr = 0.088F;
        bulletproperties.massa = 9F;
        bulletproperties.speed = 820F;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.timeLife = 6F;
        bulletproperties.power = 0.1F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 30F;
        bulletproperties.kalibr = 0.088F;
        bulletproperties.massa = 9.5F;
        bulletproperties.speed = 795F;
        return 56F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(com.maddox.JGP.Vector3d vector3d, int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d1, long l)
    {
        return new BulletAntiAirBigGermany(vector3d, i, gungeneric, loc, vector3d1, l, explodeAtHeight);
    }
}
