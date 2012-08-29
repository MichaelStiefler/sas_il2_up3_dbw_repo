// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonAntiAirSK_C33.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonAntiAirGeneric, BulletAntiAirBigGermany, Bullet

public class CannonAntiAirSK_C33 extends com.maddox.il2.objects.weapons.CannonAntiAirGeneric
{

    public CannonAntiAirSK_C33()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 17700F;
        gunproperties.sound = "weapon.Cannon100";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 14F;
        bulletproperties.power = 0.87F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 170F;
        bulletproperties.kalibr = 0.105F;
        bulletproperties.massa = 15.1F;
        bulletproperties.speed = 900F;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 0.1F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 30F;
        bulletproperties.kalibr = 0.105F;
        bulletproperties.massa = 15.8F;
        bulletproperties.speed = 900F;
        return 65F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        return new BulletAntiAirBigGermany(i, gungeneric, loc, vector3d, l, explodeAtHeight);
    }
}
