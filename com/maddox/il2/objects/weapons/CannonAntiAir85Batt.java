// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CannonAntiAir85Batt.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonAntiAirGeneric, BulletAntiAirBigUSSR, Bullet

public class CannonAntiAir85Batt extends com.maddox.il2.objects.weapons.CannonAntiAirGeneric
{

    public CannonAntiAir85Batt()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.maxDeltaAngle = 1.5F;
        gunproperties.aimMaxDist = 11000F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 20F;
        bulletproperties.power = 0.74F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 170F;
        bulletproperties.kalibr = 0.085F;
        bulletproperties.massa = 9.54F;
        bulletproperties.speed = 800F;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.timeLife = 6F;
        bulletproperties.power = 0.1F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 30F;
        bulletproperties.kalibr = 0.085F;
        bulletproperties.massa = 9.2F;
        bulletproperties.speed = 880F;
        return 56F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        return ((com.maddox.il2.objects.weapons.Bullet) (new BulletAntiAirBigUSSR(i, gungeneric, loc, vector3d, l, explodeAtHeight)));
    }
}
