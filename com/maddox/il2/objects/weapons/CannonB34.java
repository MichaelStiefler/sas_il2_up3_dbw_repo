// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonB34.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonAntiAirGeneric, BulletAntiAirBigUSSR, Bullet

public class CannonB34 extends com.maddox.il2.objects.weapons.CannonAntiAirGeneric
{

    public CannonB34()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 22241F;
        gunproperties.sound = "weapon.Cannon100";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 11F;
        bulletproperties.power = 1.21F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 170F;
        bulletproperties.kalibr = 0.1F;
        bulletproperties.massa = 15.6F;
        bulletproperties.speed = 900F;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 1.25F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 170F;
        bulletproperties.kalibr = 0.1F;
        bulletproperties.massa = 15.8F;
        bulletproperties.speed = 895F;
        return 57.95F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(com.maddox.JGP.Vector3d vector3d, int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d1, long l)
    {
        return new BulletAntiAirBigUSSR(vector3d, i, gungeneric, loc, vector3d1, l, explodeAtHeight);
    }
}
